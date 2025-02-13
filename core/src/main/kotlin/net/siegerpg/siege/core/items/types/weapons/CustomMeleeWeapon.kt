package net.siegerpg.siege.core.items.types.weapons

import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.ItemTypes
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.enums.StatTypes
import net.siegerpg.siege.core.items.recipes.CustomRecipeList
import net.siegerpg.siege.core.items.statgems.StatGem
import net.siegerpg.siege.core.items.types.subtypes.CustomWeapon
import net.siegerpg.siege.core.utils.lore
import net.siegerpg.siege.core.utils.name
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

abstract class CustomMeleeWeapon(
    override val name: String,
    override val customModelData: Int? = null,
    override val levelRequirement: Int? = null,
    override val description: List<String>,
    override val material: Material,
    final override var quality: Int = -1,
    override var item: ItemStack = ItemStack(material),
    override val baseStats: HashMap<StatTypes, Double>,
    override val type: ItemTypes = ItemTypes.MELEEWEAPON,
    override val recipeList: CustomRecipeList? = null,
    val attackSpeed: Double,
    override var statGem: StatGem? = null
) : CustomWeapon {

    override var rarity: Rarity = Rarity.COMMON

    init {
        this.rarity = Rarity.getFromInt(quality)
    }

    override fun updateMeta(hideRarity: Boolean): ItemStack {
        super.updateMeta(hideRarity)
        val meta = item.itemMeta
        meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED)
        val modifier =  AttributeModifier( "generic.attackSpeed", (-4.0+attackSpeed), AttributeModifier.Operation.ADD_NUMBER)
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier)

        val shownRarity = if (hideRarity) Rarity.UNCOMMON else rarity

        meta.name(if (shownRarity == Rarity.SPECIAL) "<r><rainbow><b>$name</b></rainbow>" else "<r>${shownRarity.color}$name")

        if (meta.hasLore()) meta.lore(mutableListOf())

        if (hideRarity || quality < 0) {
            meta.lore("<r><yellow>Rarity <gray>1-100%")
        } else {
            meta.lore(if (shownRarity == Rarity.SPECIAL) "<r><rainbow><b>${shownRarity.id}</b></rainbow> <gray>${quality}%" else "<r>${shownRarity.color}${shownRarity.id} <gray>${quality}%")
        }
        statGem?.let {
            meta.lore(" ")
            meta.lore("<r><color:#F67DF6>+${it.amount} <light_purple>${it.type.stylizedName}")
        }
        if (baseStats.size != 0) {
            meta.lore(" ")
            val realStats = CustomItemUtils.getStats(this, addGem = false, addRarity = true)
            baseStats.keys.forEach {
                if (realStats[it]!! < 0.0) {
                    if (hideRarity || quality < 0) meta.lore("<r><red>${baseStats[it]?.times(0.5)}. . .${baseStats[it]?.times(1.5)} <gray>${it.stylizedName}")
                    else meta.lore("<r><red>${realStats[it]} <gray>${it.stylizedName}")
                } else {
                    if (hideRarity || quality < 0) meta.lore("<r><green>+${baseStats[it]?.times(0.5)}. . .${baseStats[it]?.times(1.5)} <gray>${it.stylizedName}")
                    else meta.lore("<r><green>+${realStats[it]} <gray>${it.stylizedName}")
                } // TODO: Make special items work with rarity multiplier
            }
        }
        meta.lore("<r><gray>${attackSpeed} Atk Speed")
        meta.lore(" ")
        description.forEach {
            meta.lore("<r><dark_gray>$it")
        }
        meta.lore(" ")
        meta.lore("<r><gray>Level: $levelRequirement")

        meta.isUnbreakable = true
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
        item.itemMeta = meta
        return item
    }

    override fun equals(other: Any?): Boolean {
        other?.let { return false }
        if (this::class.qualifiedName != other!!::class.qualifiedName) return false
        return true
    }

}