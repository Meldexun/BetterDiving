package meldexun.better_diving.world.generator;

import java.util.List;
import java.util.Random;

import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.BiomeHelper;
import meldexun.better_diving.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class WorldGeneratorSandLayer extends WorldGeneratorOcean {

	public WorldGeneratorSandLayer(BetterDivingConfig.Ores.SandLayer config) {
		this(config.shouldGenerate, config.minHeight, config.maxHeight, BiomeHelper.getBiomes(config.biomes), BiomeHelper.getDimensions(config.dimensions));
	}

	public WorldGeneratorSandLayer(boolean shouldGenerate, int minHeight, int maxHeight, List<Biome> biomes, List<Integer> dimensions) {
		super(null, shouldGenerate, 0, 0, minHeight, maxHeight, biomes, dimensions);
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				BlockPos pos = new BlockPos(chunkX * 16 + 1 + x, world.getSeaLevel() - 1, chunkZ * 16 + 1 + z);

				if (this.biomes.contains(world.getBiome(pos))) {
					pos = BlockHelper.getSeaBed(world, pos).up();

					if (pos.getY() >= this.minHeight && pos.getY() <= this.maxHeight) {
						this.generate(world, world.rand, pos);
					}
				}
			}
		}
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		Block block = worldIn.getBlockState(position).getBlock();
		if (block == Blocks.GRAVEL || block == Blocks.DIRT || block == Blocks.STONE || BlockHelper.isOreDictionaried(new String[] { "gravel", "dirt", "stone" }, new ItemStack(block))) {
			return worldIn.setBlockState(position, Blocks.SAND.getDefaultState(), 2);
		}
		return false;
	}

}
