﻿package net.siegerpg.siege.core.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.siegerpg.siege.core.Core
import net.siegerpg.siege.core.database.DatabaseManager
import net.siegerpg.siege.core.levelReward.*
import net.siegerpg.siege.core.parties.Party
import net.siegerpg.siege.core.utils.cache.LevelEXPStorage
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.sql.ResultSet
import java.util.*
import kotlin.math.pow

object Levels {

    private val levelRewards: ArrayList<LevelReward> = arrayListOf(
        Reward1(), Reward2(), Reward3(), Reward4(), Reward5(),
        Reward6(), Reward7(), Reward8(), Reward9(), Reward10(),
        Reward11(), Reward12(), Reward13(), Reward14(), Reward15(),
        Reward16(), Reward17(), Reward18(), Reward19(), Reward20(),
        Reward21(), Reward22(), Reward23(), Reward24(), Reward25(),
        Reward26(), Reward27(), Reward28(), Reward29(), Reward30(),
        Reward31(), Reward32(), Reward33(), Reward34(), Reward35(),
        Reward36(), Reward37(), Reward38(), Reward39()
    )

    fun calculateRequiredExperience(level: Short): Int {
        return (level + 3.0).pow(3).toInt()
    }

    fun getExpLevel(player: OfflinePlayer): Pair<Short, Int> {
        val connection = DatabaseManager.getConnection()
        connection!!.use {
            val stmt = connection.prepareStatement(
                "SELECT level,experience FROM userData WHERE uuid=?",
                ResultSet.TYPE_SCROLL_SENSITIVE
            )
            stmt.setString(1, player.uniqueId.toString())
            val query = stmt.executeQuery();
            if (!query.isBeforeFirst) return Pair(0, 0)
            query.next()
            return Pair(query.getShort("level"), query.getInt("experience"))
        }
    }

    fun getExpLevel(players: ArrayList<OfflinePlayer>): HashMap<UUID, Pair<Short, Int>> {
        val connection = DatabaseManager.getConnection()
        val playerIDs = players.map { p -> p.uniqueId }.toTypedArray()
        val map = HashMap<UUID, Pair<Short, Int>>()
        connection!!.use {
            val stmt = connection.prepareStatement(
                "SELECT level,experience FROM userData WHERE uuid IN ?",
                ResultSet.TYPE_SCROLL_SENSITIVE
            )
            stmt.setArray(1, connection.createArrayOf("VARCHAR", playerIDs))
            val resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                val uuid = UUID.fromString(resultSet.getString("uuid"))
                map[uuid] = Pair(resultSet.getShort("level"), resultSet.getInt("experience"))
            }
            return map
        }
    }

    /**
     * Gets the exp and level of every player (sorted from highest level to lowest)
     * @param limit: Instead of getting exp&level of each single player you can choose how many players to get it from. Choose a number <= 0 to get everyone's level.
     */
    fun getAllExpLevel(limit: Int): ArrayList<Triple<UUID, Short, Int>> {
        val connection = DatabaseManager.getConnection()
        val arrayList = arrayListOf<Triple<UUID, Short, Int>>()
        val limitStr = if (limit <= 0) {
            ""
        } else {
            "LIMIT $limit"
        }
        connection!!.use {
            val stmt =
                connection.prepareStatement("SELECT level,experience,uuid FROM userData ORDER BY level DESC $limitStr")
            val query = stmt.executeQuery();
            if (!query.isBeforeFirst) return arrayList
            while (query.next()) {
                val triple = Triple(
                    UUID.fromString(query.getString("uuid")),
                    query.getShort("level"),
                    query.getInt("experience")
                )
                arrayList.add(triple)
            }
            return arrayList
        }
    }

    fun setLevel(player: OfflinePlayer, level: Short) {
        GlobalScope.launch(Dispatchers.IO) {
            val connection = DatabaseManager.getConnection()
            connection!!.use {
                val stmt = connection.prepareStatement("UPDATE userData SET level=? WHERE uuid=?");
                stmt.setShort(1, level)
                stmt.setString(2, player.uniqueId.toString())
                stmt.executeUpdate()
                if (player.isOnline) {
                    (player as Player).level = level.toInt()
                }
            }
        }
    }

    fun addLevel(player: OfflinePlayer, level: Short) {
        GlobalScope.launch(Dispatchers.IO) {
            val connection = DatabaseManager.getConnection()
            connection!!.use {
                val stmt = connection.prepareStatement("UPDATE userData SET level=level+? WHERE uuid=?");
                stmt.setShort(1, level)
                stmt.setString(2, player.uniqueId.toString())
                stmt.executeUpdate()
                if (player.isOnline) {
                    val p = player as Player
                    p.giveExpLevels(level.toInt())
                }
            }
        }
    }

    /**
     * Returns the new user level based on a level and exp
     */
    fun calculateExpLevel(level: Short, experience: Int, player: OfflinePlayer): Pair<Short, Int> {
        var exp = experience;
        var lvl = level;
        while (calculateRequiredExperience(lvl) <= exp) {
            exp -= calculateRequiredExperience(lvl)
            lvl = (lvl + 1).toShort()
            if (!player.isOnline) continue
            if (levelRewards.size < lvl + 2) continue //ensure that the level reward is set in the array list

            val reward: LevelReward = levelRewards[lvl.toInt() - 2]
            object : BukkitRunnable() {
                override fun run() {
                    reward.giveReward(player as Player, lvl)
                }
            }.runTask(Core.plugin())
        }
        return Pair(lvl, exp)
    }

    fun setExpLevel(player: OfflinePlayer, levelExp: Pair<Short, Int>) {
        GlobalScope.launch(Dispatchers.IO) {
            val connection = DatabaseManager.getConnection()
            connection!!.use {
                val stmt = connection.prepareStatement("UPDATE userData SET level=?,experience=? WHERE uuid=?");
                stmt.setShort(1, levelExp.first)
                stmt.setInt(2, levelExp.second)
                stmt.setString(3, player.uniqueId.toString())
                stmt.executeUpdate()
                if (player.isOnline) {
                    val p = (player as Player)
                    val lvl = levelExp.first.toInt()
                    val exp = levelExp.second
                    val expPercent = exp / calculateRequiredExperience(levelExp.first).toFloat()
                    p.level = lvl
                    p.exp = expPercent
                    LevelEXPStorage.playerLevel[player] = lvl.toShort()
                    LevelEXPStorage.playerExperience[player] = exp

                }
            }
        }
    }

    fun setExpLevel(data: HashMap<UUID, Pair<Short, Int>>) {
        GlobalScope.launch(Dispatchers.IO) {
            val connection = DatabaseManager.getConnection()
            connection!!.use {
                val stm = connection.createStatement()
                // We batch the sql queries together for speed (it will only make one request instead of multiple)
                data.forEach { (uuid, data) ->
                    // We prepare the query
                    val stmt = connection.prepareStatement("UPDATE userData SET level=?,experience=? WHERE uuid=?");
                    stmt.setShort(1, data.first)
                    stmt.setInt(2, data.second)
                    stmt.setString(3, uuid.toString())
                    // We add it to the batch
                    stm.addBatch(stmt.toString())

                    // Same process as in the setExpLevel method above
                    val player = Bukkit.getOfflinePlayer(uuid)
                    if (player.isOnline) {
                        val p = (player as Player)
                        val lvl = data.first.toInt()
                        val exp = data.second
                        val expPercent = exp / calculateRequiredExperience(data.first).toFloat()
                        p.level = lvl
                        p.exp = expPercent
                        LevelEXPStorage.playerLevel[player] = lvl.toShort()
                        LevelEXPStorage.playerExperience[player] = exp
                    }
                }
                // We execute the batch
                stm.executeBatch()
            }
        }
    }

    /**
     * Sets a player's exp
     */
    fun setExp(player: OfflinePlayer, exp: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val level = getExpLevel(player).first
            val new = calculateExpLevel(level, exp, player as Player)
            setExpLevel(player, new)
        }
    }

    /**
     * Adds experience (and levels up automatically) for one player
     */
    fun addExp(player: OfflinePlayer, exp: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val levelExp = getExpLevel(player)
            val new = calculateExpLevel(levelExp.first, levelExp.second + exp, player as Player)
            setExpLevel(player, new)
        }
    }

    /**
     * Adds the same experience to multiple players
     */
    fun addExp(players: ArrayList<OfflinePlayer>, exp: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val levelExp = getExpLevel(players)
            levelExp.forEach { (uuid, data) ->
                // Updates the data in levelExp for each player to reflect the new exp and level
                val new = calculateExpLevel(data.first, data.second + exp, Bukkit.getOfflinePlayer(uuid))
                levelExp[uuid] = Pair(new.first, new.second)
            }
            // Finally sets the new exp and level for all the players in question
            setExpLevel(levelExp)
        }
    }

    /**
     * Adds 100% of the experience to one player and 10% to all their party members
     */
    fun addExpShared(player: OfflinePlayer, exp: Int) {
        addExp(player, exp)
        val teamMembers = ArrayList<OfflinePlayer>()
        val party = Party.getPlayerParty(player)
            ?: return
        // Gets all members apart from the player
        party.getMembers().forEach { m ->
            if (m != player) {
                teamMembers.add(m)
            }
        }
        // Adds 1/10th of the exp to all the team members
        addExp(teamMembers, Math.floorDiv(exp, 10))
    }
}