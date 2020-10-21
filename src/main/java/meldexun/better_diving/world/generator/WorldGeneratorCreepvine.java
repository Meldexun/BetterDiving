package meldexun.better_diving.world.generator;

import java.util.Random;

import meldexun.better_diving.block.AbstractBlockCreepvine;
import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGeneratorCreepvine extends WorldGeneratorOcean {

	public WorldGeneratorCreepvine() {
		super(ModBlocks.CREEPVINE.getDefaultState(), BetterDivingConfig.getInstance().plants.creepvine);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if (worldIn.getBlockState(position.down()).getBlock() instanceof AbstractBlockCreepvine) {
			return false;
		}

		if (ModBlocks.CREEPVINE.canPlaceBlockAt(worldIn, position)) {
			int maxHeight = this.getMaxHeight(worldIn, rand, position);
			boolean generateSeeds = rand.nextInt(1000) < 250;

			int i = 0;
			while (i < maxHeight - 1) {
				ModBlocks.CREEPVINE.setCreepvine(worldIn, position.up(i), 18, maxHeight, generateSeeds);
				i++;
			}

			ModBlocks.CREEPVINE_TOP.setCreepvine(worldIn, position.up(i), 18, maxHeight, generateSeeds);

			if (BetterDivingConfig.getInstance().plants.shouldGenerateCreepvineSeedCluster && generateSeeds) {
				ModBlocks.CREEPVINE_SEED.setCreepvine(worldIn, position.up((int) (maxHeight * 0.5D)), 18, maxHeight, generateSeeds);
			}

			return true;
		}

		return false;
	}

	private int getMaxHeight(World worldIn, Random rand, BlockPos pos) {
		int maxHeight = AbstractBlockCreepvine.MAX_HEIGHT - rand.nextInt(5);
		int i = 1;
		while (i < maxHeight && this.checkTopAndSides(worldIn, pos.up(i))) {
			i++;
		}
		return i;
	}

	private boolean checkTopAndSides(World worldIn, BlockPos pos) {
		if (!ModBlocks.CREEPVINE.checkTop(worldIn, pos)) {
			return false;
		}
		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
			if (!ModBlocks.CREEPVINE.isFullCubeOrWater(worldIn.getBlockState(pos.offset(facing)))) {
				return false;
			}
		}
		return true;
	}

}
