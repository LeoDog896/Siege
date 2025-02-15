package net.siegerpg.siege.core.Webstore;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import net.siegerpg.siege.core.items.CustomItemUtils;
import net.siegerpg.siege.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WebstoreUtils {

    public static double expMultiplier = 1.0;
    public static double goldMultiplier = 1.0;

    public static ItemStack getExpBoosterItem(int amount, double multiplier, int seconds) {
        ItemStack item = new ItemStack(Material.PAPER, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Utils.lore("<light_purple>EXP Booster"));
        itemMeta.lore(new ArrayList<>(){
            {
                add(Utils.lore("  <gray>Duration: <white>" + Utils.convertSecondsToTime(seconds)));
                add(Utils.lore("  <yellow>Multiplier: +" + ((multiplier*100)-100.0) + "% EXP"));
                add(Utils.lore(""));
                add(Utils.lore("<green><bold>CLICK TO REDEEM"));
            }
        });

        item.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setInteger("seconds", seconds);
        nbtItem.setDouble("multiplier", multiplier);
        return nbtItem.getItem();
    }
    public static ItemStack getGoldBoosterItem(int amount, double multiplier, int seconds) {
        ItemStack item = new ItemStack(Material.PAPER, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Utils.lore("<yellow>Gold Booster"));
        itemMeta.lore(new ArrayList<>(){
            {
                add(Utils.lore("  <gray>Duration: <white>" + Utils.convertSecondsToTime(seconds)));
                add(Utils.lore("  <yellow>Multiplier: +" + ((multiplier*100)-100.0) + "% Gold"));
                add(Utils.lore(""));
                add(Utils.lore("<green><bold>CLICK TO REDEEM"));
            }
        });

        item.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setInteger("seconds", seconds);
        nbtItem.setDouble("multiplier", multiplier);
        return nbtItem.getItem();
    }
    public static ItemStack getBooster(int amount, double multiplier, int seconds, String booster) {
        ItemStack item;
        if (booster.equals("EXP")) {
            item = WebstoreUtils.getExpBoosterItem(amount, multiplier, seconds);
        } else if (booster.equals("GOLD")) {
            item = WebstoreUtils.getGoldBoosterItem(amount, multiplier, seconds);
        } else {
            Bukkit.getLogger().info(Utils.tacc("&cError checking booster value for either GOLD or EXP"));
            return null;
        }
        return item;
    }
    public static void giveItemToPlayer(OfflinePlayer player, ItemStack item) {
        final boolean fullInv = ((Player)player).getInventory().firstEmpty() == -1;
        final boolean fullEnderChest = ((Player)player).getEnderChest().firstEmpty() == -1;
        if (!fullInv) {
            ((Player)player).getInventory().addItem(item);
        } else if (!fullEnderChest) {
            ((Player)player).getEnderChest().addItem(item);
        } else {
            ((Player)player).getWorld().dropItemNaturally(((Player)player).getLocation(), item);
        }
    }
}
