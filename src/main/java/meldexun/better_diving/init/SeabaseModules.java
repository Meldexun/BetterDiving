package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.registry.ModuleRegistry;
import meldexun.better_diving.structure.modules.BasicCompartment;
import net.minecraft.util.ResourceLocation;

public class SeabaseModules {

	private SeabaseModules() {

	}

	public static void registerModules() {
		ModuleRegistry.getInstance().register(new ResourceLocation(BetterDiving.MOD_ID, "basic_compartment"), BasicCompartment.class);
	}

}
