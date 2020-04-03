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
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenOcean implements IWorldGenerator {

	public static List<WorldGeneratorOcean> oreGenerators = new ArrayList<WorldGeneratorOcean>();
	public static List<WorldGeneratorOcean> plantGenerators = new ArrayList<WorldGeneratorOcean>();

	static {
		oreGenerators.add(new WorldGeneratorSandLayer(BetterDivingConfig.ORES.sandLayer));
		oreGenerators.add(new WorldGeneratorOcean(ModBlocks.LIMESTONE_OUTCROP.getDefaultState(), BetterDivingConfig.ORES.limestone));
		oreGenerators.add(new WorldGeneratorOcean(ModBlocks.SANDSTONE_OUTCROP.getDefaultState(), BetterDivingConfig.ORES.sandstone));
		plantGenerators.add(new WorldGeneratorCreepvine());
		plantGenerators.add(new WorldGeneratorOcean(ModBlocks.ACID_MUSHROOM.getDefaultState(), BetterDivingConfig.PLANTS.acidMushroom));
		plantGenerators.add(new WorldGeneratorSeagrassTall());
		plantGenerators.add(new WorldGeneratorOcean(ModBlocks.SEAGRASS.getDefaultState(), BetterDivingConfig.PLANTS.seagrass));
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (BetterDivingConfig.MODULES.oreGeneration) {
			for (WorldGeneratorOcean generator : oreGenerators) {
				generator.generate(world, random, chunkX, chunkZ);
			}
		}
		if (BetterDivingConfig.MODULES.plantGeneration) {
			for (WorldGeneratorOcean generator : plantGenerators) {
				generator.generate(world, random, chunkX, chunkZ);
			}
		}
	}

}
