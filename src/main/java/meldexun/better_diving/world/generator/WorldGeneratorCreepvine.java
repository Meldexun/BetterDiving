package meldexun.better_diving.world.generator;

import java.util.Random;

import meldexun.better_diving.block.AbstractBlockCreepvine;
import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGeneratorCreepvine extends WorldGeneratorOcean {

	public WorldGeneratorCreepvine() {
		super(ModBlocks.CREEPVINE.getDefaultState(), BetterDivingConfig.PLANTS.creepvine);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if (worldIn.getBlockState(position.down()).getBlock() instanceof AbstractBlockCreepvine) {
			return false;
		}

		if (ModBlocks.CREEPVINE.canPlaceBlockAt(worldIn, position)) {
			int maxHeight = AbstractBlockCreepvine.MAX_HEIGHT - rand.nextInt(6);
			boolean generateSeeds = rand.nextInt(1000) < 250;

			for (int i = 0; i < maxHeight && ModBlocks.CREEPVINE.canPlaceBlockAt(worldIn, position.up(i)); i++) {
				ModBlocks.CREEPVINE.setCreepvine(worldIn, position.up(i), 2, maxHeight, generateSeeds);
			}

			ModBlocks.CREEPVINE_TOP.setCreepvine(worldIn, position.up(maxHeight), 2, maxHeight, generateSeeds);

			if (BetterDivingConfig.PLANTS.shouldGenerateCreepvineSeedCluster && generateSeeds) {
				ModBlocks.CREEPVINE_SEED.setCreepvine(worldIn, position.up(maxHeight / 2), 2, maxHeight, generateSeeds);
			}

			return true;
		}

		return false;
	}

}
