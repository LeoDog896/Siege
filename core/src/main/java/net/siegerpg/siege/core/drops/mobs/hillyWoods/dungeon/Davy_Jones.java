package net.siegerpg.siege.core.drops.mobs.hillyWoods.dungeon;

import net.siegerpg.siege.core.drops.MobDropTable;
import net.siegerpg.siege.core.drops.Reward;
import net.siegerpg.siege.core.items.implemented.misc.keys.hillyWoods.DavyJonesKey;
import net.siegerpg.siege.core.items.implemented.misc.statgems.healthGems.SimpleHealthGem;
import net.siegerpg.siege.core.items.implemented.misc.statgems.luckGems.SimpleLuckGem;
import net.siegerpg.siege.core.items.implemented.misc.statgems.strengthGems.SimpleStrengthGem;
import net.siegerpg.siege.core.items.implemented.misc.statgems.toughGems.SimpleToughGem;
import net.siegerpg.siege.core.items.implemented.weapons.melee.heavy.*;
import net.siegerpg.siege.core.items.implemented.weapons.ranged.*;
import net.siegerpg.siege.core.utils.Utils;

public class Davy_Jones extends MobDropTable {
    public Davy_Jones() {
        super("Davy_Jones", 800, 900, 1500, 1700, new Reward[]{
                new Reward(new IronAxe(Utils.randRarity()).getUpdatedItem(false), 100.0),
                new Reward(new Trident(Utils.randRarity()).getUpdatedItem(false), 100.0),
                new Reward(new IronAxe(Utils.randRarity()).getUpdatedItem(false), 50.0),
                new Reward(new Trident(Utils.randRarity()).getUpdatedItem(false), 50.0),
                new Reward(new SimpleHealthGem(Utils.randRarity()).getUpdatedItem(false), 20.0),
                new Reward(new SimpleToughGem(Utils.randRarity()).getUpdatedItem(false), 20.0),
                new Reward(new SimpleStrengthGem(Utils.randRarity()).getUpdatedItem(false), 20.0),
                new Reward(new SimpleLuckGem(Utils.randRarity()).getUpdatedItem(false), 20.0),
                new Reward(new DavyJonesKey(0).getUpdatedItem(false), 10.0)

        });
    }
}