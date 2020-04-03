package meldexun.better_diving.entity;

import meldexun.better_diving.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityBoomerang extends AbstractEntityFish {

	public EntityBoomerang(World worldIn) {
		super(worldIn);
	}

	@Override
	protected Item getDropItem() {
		return ModItems.BOOMERANG;
	}

}
