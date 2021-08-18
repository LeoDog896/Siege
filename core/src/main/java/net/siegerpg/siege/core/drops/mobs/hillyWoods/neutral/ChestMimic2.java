package net.siegerpg.siege.core.drops.mobs.hillyWoods.neutral;

import net.siegerpg.siege.core.drops.MobDropTable;
import net.siegerpg.siege.core.drops.Reward;
import net.siegerpg.siege.core.fishing.baits.BaitCore;
import net.siegerpg.siege.core.items.implemented.misc.food.Drumstick;
import net.siegerpg.siege.core.items.implemented.misc.food.Sugar;
import net.siegerpg.siege.core.items.implemented.misc.keys.HillyWoodsDungeonKey;
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.blocks.Pebble;
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.blocks.Stick;
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.blocks.Vine;
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.mobs.Bone;
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.mobs.Leather;
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.mobs.Magma;
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.mobs.Slime;

public class ChestMimic2 extends MobDropTable {
    public ChestMimic2() {
        super("ChestMimic2", 850, 900, 150, 200, new Reward[]{
                new Reward(Magma.Companion.tier(2).getUpdatedItem(false).asQuantity(8), 25.0),
                new Reward(Bone.Companion.tier(2).getUpdatedItem(false).asQuantity(8), 25.0),
                new Reward(Pebble.Companion.tier(2).getUpdatedItem(false).asQuantity(8), 25.0),

                new Reward(Magma.Companion.tier(2).getUpdatedItem(false).asQuantity(12), 15.0),
                new Reward(Bone.Companion.tier(2).getUpdatedItem(false).asQuantity(12), 15.0),
                new Reward(Pebble.Companion.tier(2).getUpdatedItem(false).asQuantity(12), 15.0),

                new Reward(Magma.Companion.tier(3).getUpdatedItem(false).asQuantity(8), 15.0),
                new Reward(Bone.Companion.tier(3).getUpdatedItem(false).asQuantity(8), 15.0),
                new Reward(Pebble.Companion.tier(3).getUpdatedItem(false).asQuantity(8), 15.0),

                new Reward(new Sugar(100).getUpdatedItem(false).asQuantity(4), 50.0),
                new Reward(new HillyWoodsDungeonKey(0).getUpdatedItem(false), 30.0),
                new Reward(new HillyWoodsDungeonKey(0).getUpdatedItem(false), 30.0),
                new Reward(new HillyWoodsDungeonKey(0).getUpdatedItem(false), 30.0),
        });
    }
}