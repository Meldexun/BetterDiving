package meldexun.better_diving.init;

import meldexun.better_diving.world.WorldGenOcean;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModWorldGenerators {

	public static void registerWorldGenerators() {
		GameRegistry.registerWorldGenerator(new WorldGenOcean(), 0);
	}

}
