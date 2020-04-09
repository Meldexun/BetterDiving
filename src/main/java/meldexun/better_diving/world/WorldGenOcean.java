package meldexun.better_diving.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.world.generator.WorldGeneratorCreepvine;
import meldexun.better_diving.world.generator.WorldGeneratorOcean;
import meldexun.better_diving.world.generator.WorldGeneratorSandLayer;
import meldexun.better_diving.world.generator.WorldGeneratorSeagrassTall;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenOcean implements IWorldGenerator {

	private static WorldGeneratorSandLayer sandLayerGenerator;
	private static List<WorldGeneratorOcean> oreGenerators = new ArrayList<>();
	private static List<WorldGeneratorOcean> plantGenerators = new ArrayList<>();

	static {
		WorldGenOcean.sandLayerGenerator = new WorldGeneratorSandLayer(Blocks.SAND.getDefaultState(), BetterDivingConfig.getInstance().ores.sandLayer);

		WorldGenOcean.oreGenerators.add(new WorldGeneratorOcean(ModBlocks.LIMESTONE_OUTCROP.getDefaultState(), BetterDivingConfig.getInstance().ores.limestone));
		WorldGenOcean.oreGenerators.add(new WorldGeneratorOcean(ModBlocks.SANDSTONE_OUTCROP.getDefaultState(), BetterDivingConfig.getInstance().ores.sandstone));

		WorldGenOcean.plantGenerators.add(new WorldGeneratorCreepvine());
		WorldGenOcean.plantGenerators.add(new WorldGeneratorOcean(ModBlocks.ACID_MUSHROOM.getDefaultState(), BetterDivingConfig.getInstance().plants.acidMushroom));
		WorldGenOcean.plantGenerators.add(new WorldGeneratorSeagrassTall());
		WorldGenOcean.plantGenerators.add(new WorldGeneratorOcean(ModBlocks.SEAGRASS.getDefaultState(), BetterDivingConfig.getInstance().plants.seagrass));
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		WorldGenOcean.sandLayerGenerator.generate(world, random, chunkX, chunkZ);

		if (BetterDivingConfig.getInstance().modules.oreGeneration) {
			for (WorldGeneratorOcean generator : WorldGenOcean.oreGenerators) {
				generator.generate(world, random, chunkX, chunkZ);
			}
		}

		if (BetterDivingConfig.getInstance().modules.plantGeneration) {
			for (WorldGeneratorOcean generator : WorldGenOcean.plantGenerators) {
				generator.generate(world, random, chunkX, chunkZ);
			}
		}
	}

}
