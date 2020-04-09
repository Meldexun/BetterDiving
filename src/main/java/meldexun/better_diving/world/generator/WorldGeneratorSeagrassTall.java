package meldexun.better_diving.world.generator;

import java.util.Random;

import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGeneratorSeagrassTall extends WorldGeneratorOcean {

	public WorldGeneratorSeagrassTall() {
		super(ModBlocks.SEAGRASS_TALL_BOTTOM.getDefaultState(), BetterDivingConfig.getInstance().plants.seagrassTall);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if (ModBlocks.SEAGRASS_TALL_BOTTOM.canPlaceBlockAt(worldIn, position)) {
			worldIn.setBlockState(position, ModBlocks.SEAGRASS_TALL_BOTTOM.getDefaultState(), 2);
			worldIn.setBlockState(position.up(), ModBlocks.SEAGRASS_TALL_TOP.getDefaultState(), 2);
			return true;
		}
		return false;
	}

}
