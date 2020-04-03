package meldexun.better_diving.entity;

import meldexun.better_diving.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityPeeper extends AbstractEntityFish {

	public EntityPeeper(World worldIn) {
		super(worldIn);
	}

	@Override
	protected Item getDropItem() {
		return ModItems.PEEPER;
	}

}
