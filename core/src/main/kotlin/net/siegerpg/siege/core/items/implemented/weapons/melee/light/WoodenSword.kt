package net.siegerpg.siege.core.items.implemented.weapons.melee.light

import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.blocks.Stick
import net.siegerpg.siege.core.items.recipes.recipes
import net.siegerpg.siege.core.items.types.weapons.CustomMeleeWeapon
import net.siegerpg.siege.core.utils.Utils
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class WoodenSword() : CustomMeleeWeapon(
    name = "Wooden Sword",
    customModelData = 110007,
    description = listOf("A classic weapon in recruits"),
    levelRequirement = 25,
    material = Material.WOODEN_SWORD,
    baseStats = CustomItemUtils.statMap(strength = 28.0),
    recipeList = recipes {
        recipe {
            shaped = true
            s1(Stick.tier(1))
            s4(Stick.tier(1))
            item { player, b ->
                val newItem = Twig(if (b) 50 else Utils.randRarity())
                newItem.updateMeta(b)
                newItem
            }
        }
    },
    attackSpeed = 1.6
) {

    constructor(quality: Int): this() {
        this.quality = quality
        this.rarity = Rarity.getFromInt(quality)
        this.serialize()
    }

    constructor(item: ItemStack): this() {
        this.item = item
        deserialize()
    }

}