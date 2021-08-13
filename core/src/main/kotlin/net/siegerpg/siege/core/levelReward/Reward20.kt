package net.siegerpg.siege.core.levelReward

import net.siegerpg.siege.core.items.implemented.weapons.melee.light.daggers.StrongDagger
import net.siegerpg.siege.core.listeners.GoldExpListener
import net.siegerpg.siege.core.utils.Utils
import org.bukkit.Sound
import org.bukkit.entity.Player

class Reward20 : LevelReward {
    override fun giveReward(player: Player, level: Short) {
        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP,5.0f, 5.0f)
        player.sendTitle(Utils.tacc(""), Utils.tacc("&d${level-1} &7\u21E2 &5$level"), 10, 80, 10)
        player.sendMessage(Utils.lore(""))
        player.sendMessage(Utils.lore("<gray>You leveled up to level $level!"))
        player.sendMessage(Utils.lore("<gold><bold>Level Reward"))
        player.sendMessage(Utils.lore("<yellow>+ 3,500 gold"))
        player.sendMessage(Utils.lore("<red>+ 2 HP"))
        player.sendMessage(Utils.lore(""))

        GoldExpListener().giveGold(player, 3500)
        Utils.giveItem(player, StrongDagger(Utils.randRarity()).getUpdatedItem(false))
    }
}