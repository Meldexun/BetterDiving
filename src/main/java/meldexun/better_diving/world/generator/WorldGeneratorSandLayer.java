package meldexun.better_diving.world.generator;

import java.util.Random;
import java.util.Set;

import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;

public class WorldGeneratorSandLayer extends WorldGenerator {

	protected final IBlockState blockToSpawn;
	protected final BetterDivingConfig.Ores.SandLayer config;

	public WorldGeneratorSandLayer(IBlockState blockToSpawn, BetterDivingConfig.Ores.SandLayer config) {
		this.blockToSpawn = blockToSpawn;
		this.config = config;
	}

	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		if (this.config.isEnabled() && this.canGenerateInDimension(world.provider.getDimension())) {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					int seaLevel = Math.max(world.getSeaLevel() - 1, 1);
					BlockPos pos = new BlockPos(chunkX * 16 + 8 + x, seaLevel, chunkZ * 16 + 8 + z);
					Biome biome = world.getBiomeProvider().getBiome(pos);

					if (this.canGenerateInBiome(biome)) {
						pos = BlockHelper.getSeaBed(world, pos).down();

						if (world.getBlockState(pos.up()).getMaterial() == Material.WATER && this.isHeightValid(seaLevel, pos.getY())) {
							this.generate(world, rand, pos);
						}
					}
				}
			}
		}
	}

	protected boolean isHeightValid(int seaLevel, int y) {
		if (this.config.seaLevelRelative) {
			return y <= seaLevel - this.config.minHeight && y >= seaLevel - this.config.maxHeight;
		}
		return y >= this.config.minHeight && y <= this.config.maxHeight;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		Block block = worldIn.getBlockState(position).getBlock();
		if (block == Blocks.GRAVEL || block == Blocks.DIRT || block == Blocks.STONE || BlockHelper.isOreDictionaried(new String[] { "gravel", "dirt", "stone" }, new ItemStack(block))) {
			return worldIn.setBlockState(position, this.blockToSpawn, 18);
		}
		return false;
	}

	public boolean canGenerateInDimension(int dimension) {
		for (int dim : this.config.dimensions) {
			if (dim == dimension) {
				return true;
			}
		}
		return false;
	}

	public boolean canGenerateInBiome(Biome biome) {
		if (this.config.biomeOverride) {
			return true;
		}
		Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);
		for (String biomeTypeName : this.config.biomeTypes) {
			BiomeDictionary.Type biomeType = this.getBiomeTypeByName(biomeTypeName);
			if (biomeType != null && biomeTypes.contains(biomeType)) {
				return true;
			}
		}
		ResourceLocation biomeRegistryName = biome.getRegistryName();
		for (ResourceLocation biomeName : this.config.getBiomes()) {
			if (biomeName.equals(biomeRegistryName)) {
				return true;
			}
		}
		return false;
	}

	private BiomeDictionary.Type getBiomeTypeByName(String biomeTypeName) {
		for (BiomeDictionary.Type biomeType : BiomeDictionary.Type.getAll()) {
			if (biomeType.getName().equals(biomeTypeName)) {
				return biomeType;
			}
		}
		return null;
	}

}
