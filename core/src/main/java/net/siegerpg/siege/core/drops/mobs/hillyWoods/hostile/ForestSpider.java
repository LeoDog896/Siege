package net.siegerpg.siege.core.drops.mobs.hillyWoods.hostile;

import net.siegerpg.siege.core.drops.MobDropTable;
import net.siegerpg.siege.core.drops.Reward;
import net.siegerpg.siege.core.items.implemented.misc.food.*;
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.blocks.*;
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.mobs.*;
import net.siegerpg.siege.core.items.implemented.misc.wands.*;
import net.siegerpg.siege.core.utils.Utils;

public class ForestSpider extends MobDropTable {
    public ForestSpider() {
        super("ForestSpider", 13, 16, 19, 22, new Reward[]{
                new Reward(Vine.Companion.tier(1).getUpdatedItem(false), 100.0),
                new Reward(Vine.Companion.tier(2).getUpdatedItem(false), 10.0),
                new Reward(new SusStew(Utils.randRarity()).getUpdatedItem(false), 1.0)
        });
    }
}
