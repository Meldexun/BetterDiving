package meldexun.better_diving.world.generator;

import java.util.List;
import java.util.Random;

import meldexun.better_diving.util.BiomeHelper;
import meldexun.better_diving.util.BlockHelper;
import meldexun.better_diving.util.config.GeneratorConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGeneratorOcean extends WorldGenerator {

	protected IBlockState blockToSpawn;
	protected boolean shouldGenerate;
	protected int chance;
	protected int amount;
	protected int minHeight;
	protected int maxHeight;
	protected List<Biome> biomes;
	protected List<Integer> dimensions;

	public WorldGeneratorOcean(IBlockState blockToSpawn, GeneratorConfig config) {
		this(blockToSpawn, config.shouldGenerate, config.chance, config.amount, config.minHeight, config.maxHeight, BiomeHelper.getBiomes(config.biomes), BiomeHelper.getDimensions(config.dimensions));
	}

	public WorldGeneratorOcean(IBlockState blockToSpawn, boolean shouldGenerate, int chance, int amount, int minHeight, int maxHeight, List<Biome> biomes, List<Integer> dimensions) {
		this.blockToSpawn = blockToSpawn;
		this.shouldGenerate = shouldGenerate;
		this.chance = chance;
		this.amount = amount;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.biomes = biomes;
		this.dimensions = dimensions;
		if (this.biomes.isEmpty() || this.dimensions.isEmpty() || minHeight > maxHeight) {
			this.shouldGenerate = false;
		}
	}

	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		if (this.shouldGenerate && this.dimensions.contains(world.provider.getDimension())) {
			if (rand.nextInt(this.chance) == 0) {
				for (int i = 0; i < this.amount; i++) {
					BlockPos pos = new BlockPos(rand.nextInt(16) + chunkX * 16 + 1, Math.max(world.getSeaLevel() - 1, 1), rand.nextInt(16) + chunkZ * 16 + 1);

					if (this.biomes.contains(world.getBiome(pos))) {
						pos = BlockHelper.getSeaBed(world, pos);

						if (pos.getY() >= this.minHeight && pos.getY() <= this.maxHeight) {
							this.generate(world, rand, pos);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if (this.blockToSpawn.getBlock().canPlaceBlockAt(worldIn, position)) {
			worldIn.setBlockState(position, this.blockToSpawn, 2);
			return true;
		}
		return false;
	}

}
