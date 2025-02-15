package net.siegerpg.siege.core.listeners;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import net.siegerpg.siege.core.Core;
import net.siegerpg.siege.core.items.CustomItem;
import net.siegerpg.siege.core.items.CustomItemUtils;
import net.siegerpg.siege.core.items.types.misc.StatGemType;
import net.siegerpg.siege.core.utils.Scoreboard;
import net.siegerpg.siege.core.utils.Levels;
import net.siegerpg.siege.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class GoldExpListener implements Listener{

    @EventHandler
    public void goldPickUp(PlayerAttemptPickupItemEvent e) {
        ItemStack item = e.getItem().getItemStack();
        if (e.isCancelled()) return;
        if (!item.getType().equals(Material.SUNFLOWER)) return;
        if (!item.getItemMeta().getDisplayName().contains("Gold Coin")) return;
        Player player = e.getPlayer();
        e.setCancelled(true);
        e.getItem().remove();
        int goldAmount = e.getItem().getItemStack().getAmount();
        giveGold(player, goldAmount);
    }

    public static void giveGold(Player player, int goldAmount) {
        net.siegerpg.siege.core.utils.VaultHook.econ.depositPlayer(player, goldAmount);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        player.sendActionBar(Utils.parse("<yellow>+ " + goldAmount + " <yellow>Gold"));
        Bukkit.getServer().getScheduler().runTaskLater(Core.plugin(), new Runnable() {
            public void run() {
                Scoreboard.updateScoreboard(player);
            }
        }, 20);
    }

    @EventHandler
    public void expPickUp(PlayerPickupExperienceEvent e) {
        if (e.getExperienceOrb().getName().contains("EXP")) {
            Player player = e.getPlayer();
            int exp = e.getExperienceOrb().getExperience();
            Levels.INSTANCE.addExpShared(player, exp);
            player.sendActionBar(Utils.parse("<dark_purple>+ " + exp + " <dark_purple>EXP"));

            Bukkit.getServer().getScheduler().runTaskLater(Core.plugin(), new Runnable() {
                public void run() {
                    Scoreboard.updateScoreboard(player);
                }
            }, 20);
        }
        e.getExperienceOrb().setExperience(0);
    }

    @EventHandler
    public void goldMerge(ItemMergeEvent e) {
        ItemStack item = e.getEntity().getItemStack();
        Item source = e.getEntity();
        Item target = e.getTarget();
        int total = source.getItemStack().getAmount() + target.getItemStack().getAmount();
        if (e.isCancelled()) return;
        if (item.getType().equals(Material.SUNFLOWER) && item.getItemMeta().getDisplayName().contains("Gold Coin")) {
            e.getTarget().setCustomName(Utils.tacc("&e+" + total + " Gold"));
        } else if (CustomItemUtils.INSTANCE.getCustomItem(item) != null){
            e.getTarget().setCustomName(Utils.tacc( "&e" + total + "x &r" + item.getItemMeta().getDisplayName()));
        }
        e.getTarget().setCustomNameVisible(true);
    }

    @EventHandler
    public void setDroppedItemName(ItemSpawnEvent e) {
        ItemStack item = e.getEntity().getItemStack();
        CustomItem CusItem = CustomItemUtils.INSTANCE.getCustomItem(item);
        if (CusItem == null) return;

        e.getEntity().setCustomName(Utils.tacc( "&e" + item.getAmount() + "x &r" + item.getItemMeta().getDisplayName()));
        e.getEntity().setCustomNameVisible(true);
    }

}
