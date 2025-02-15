package net.siegerpg.siege.core.listeners.tasks;

import net.siegerpg.siege.core.Core;
import net.siegerpg.siege.core.utils.cache.LevelEXPStorage;
import net.siegerpg.siege.core.utils.cache.PlayerData;
import net.siegerpg.siege.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class HelpfulTips implements Listener {

    /**
     * TIPS ARE SUBJECT TO CHANGE WITH NEW UPDATES
     */

    public static ArrayList<String> tips = new ArrayList<>(){
        {
            add(Utils.tacc("&eKill chickens, pigs, birds, sheep, and cows for food."));
            add(Utils.tacc("&eClick on weapon/armor vendors to craft stronger weapons and armor pieces."));
            add(Utils.tacc("&eClick on material vendors and then either block drops or mob drops to upgrade your materials."));
            add(Utils.tacc("&eClick and drag stat gems onto a weapon or armor piece to apply it."));
            add(Utils.tacc("&eMobs are often found hiding in corners and along walls."));
            add(Utils.tacc("&eArmor stats do not apply if you are not the appropriate level."));
            add(Utils.tacc("&eYou can still wear armor if you are not the appropriate level."));
            add(Utils.tacc("&eWeapon stats will not apply if you are not the appropriate level."));
            add(Utils.tacc("&eWeapons will only deal 1 damage if you are not the appropriate level."));
            add(Utils.tacc("&eBuy armor/weapons from Smoky at spawn."));
            add(Utils.tacc("&eUse Mera to teleport to different locations around Hilly Woods."));
            add(Utils.tacc("&eVisit our webstore using the /webstore command!"));
            add(Utils.tacc("&eJoin our discord using the /discord command!"));
            add(Utils.tacc("&eHerbert the Scrapper will buy your items (but he's cheap about it)."));
            add(Utils.tacc("&eHerbert does not like tier 1 materials, so you need to convert them to at least tier 2 for him to buy them."));
            add(Utils.tacc("&eUse the /hub command to teleport to the hub."));
            add(Utils.tacc("&eUse the /spawn command to teleport to spawn 3 seconds after sending it."));
            add(Utils.tacc("&eUse Hilly Woods keys to join Hilly Woods dungeons!"));
            add(Utils.tacc("&eLooking to partner with us? DM Wrys#8935 to speak with the owner."));
            add(Utils.tacc("&eMost plants can be broken for a chance to get gold or experience."));
            add(Utils.tacc("&eWarrior rank gives you 9 more slots per player vault."));
            add(Utils.tacc("&eGladiator rank gives you 18 more slots per player vault."));
            add(Utils.tacc("&eHero rank gives you 27 more slots per player vault."));
            add(Utils.tacc("&eRank gives you extra daily rewards!."));

            add(Utils.tacc("&eYou will gain a player vault for every 10 levels you rank up."));
            add(Utils.tacc("&eAn Ogre can spawn at night, he's terrifying."));
            add(Utils.tacc("&eGoblins and Wild Foxes can steal your money and run away with it."));
            add(Utils.tacc("&eZombified Diggers can duplicate when they hit you."));
            add(Utils.tacc("&eA sweeping effect is played every time a player damages a mob."));
            add(Utils.tacc("&eYou can put your money in the bank at the village on the opposite corner of the map."));
            add(Utils.tacc("&eYou can buy food from the butcher at the village on the opposite corner of the map."));
            add(Utils.tacc("&eYou can gamble for money with Bart at the village on the opposite corner of the map."));
        }
    };

    public void broadcastTasks() {
        tipsTask();
        webstoreDiscordTask();
    }

    public void tipsTask() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.plugin(), () -> {
            int randNum = (int)(Math.random()*tips.size());
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!PlayerData.broadcastTips.get(p)) continue;
                p.sendMessage(Utils.tacc("\n&6&lTIP &r"+tips.get(randNum)+"&r\n&7To disable tips type /tips disable.\n\n "));
            }
        }, 6000, 6000);
    }
    public void webstoreDiscordTask() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.plugin(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (LevelEXPStorage.playerLevel.get(p) > 10) continue;
                p.sendMessage(Utils.parse(""));
                p.sendMessage(Utils.parse("  <aqua><bold>Join our <light_purple>discord<aqua> here!<reset>"));
                p.sendMessage(Utils.tacc("  https://discord.siegerpg.net"));
                p.sendMessage(Utils.parse(""));
                p.sendMessage(Utils.parse("  <aqua><bold>Visit our <green>webstore<aqua> here!<reset>"));
                p.sendMessage(Utils.tacc("  https://store.siegerpg.net/"));
                p.sendMessage(Utils.parse(""));
            }
        }, 12000, 6000);
    }
}
