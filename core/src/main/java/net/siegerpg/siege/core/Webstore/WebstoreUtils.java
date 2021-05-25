package net.siegerpg.siege.core.Webstore;

import net.kyori.adventure.text.Component;
import net.siegerpg.siege.core.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WebstoreUtils {
    public static double expMultiplier = 1.0;
    public static ItemStack getExpBoosterItem(int amount, double multiplier, int ticks) {
        ItemStack item = new ItemStack(Material.PAPER, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Utils.lore("<light_purple>EXP Booster"));
        itemMeta.lore(new ArrayList<>(){
            {
                add(Utils.lore("  <gray>Duration: "));
                add(Utils.lore("  <yellow>Multiplier: " + multiplier + "x EXP"));
            }
        });
        item.setItemMeta(itemMeta);
        return item;
    }
}
