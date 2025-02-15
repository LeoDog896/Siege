package net.siegerpg.siege.core.levelReward

import net.siegerpg.siege.core.listeners.GoldExpListener
import net.siegerpg.siege.core.utils.Utils
import net.siegerpg.siege.core.utils.VaultHook
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player

class Reward29 : LevelReward {
    override fun giveReward(player: Player, level: Short) {
        Bukkit.getServer().broadcast(Utils.lore(""))
        Bukkit.getServer().broadcastMessage(Utils.tacc("&b&l" + player.name + "&r &7has reached level &d" + level + "!"))
        Bukkit.getServer().broadcastMessage(Utils.tacc("&a/pv 3 &7is available"))
        Bukkit.getServer().broadcast(Utils.lore(""))

        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 5.0f, 5.0f)
        player.sendTitle(Utils.tacc(""), Utils.tacc("&d${level-1} &7\u21E2 &5$level"), 10, 80, 10)
        player.sendMessage(Utils.lore(""))
        player.sendMessage(Utils.lore("<gray>You leveled up to level $level!"))
        player.sendMessage(Utils.lore("<gold><bold>Level Reward"))
        player.sendMessage(Utils.lore("<yellow>+ 20,000 gold"))
        player.sendMessage(Utils.lore("<red>+ 2 HP"))
        player.sendMessage(Utils.lore("<red>+ 1 Player Vault"))
        player.sendMessage(Utils.lore(""))

        GoldExpListener.giveGold(player, 20000)
        val highestPV = Utils.getHighestPV(player)
        VaultHook.perms.playerAdd("global", player, "cosmicvaults.amount."+(highestPV+1))

    }
}