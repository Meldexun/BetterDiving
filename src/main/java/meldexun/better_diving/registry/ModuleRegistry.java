package meldexun.better_diving.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.structure.modules.SeabaseModule;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ModuleRegistry {

	private static final ModuleRegistry instance = new ModuleRegistry();
	private final Map<ResourceLocation, Class<? extends SeabaseModule>> modules = new HashMap();

	public static ModuleRegistry getInstance() {
		return ModuleRegistry.instance;
	}

	public void register(ResourceLocation registryLocation, Class<? extends SeabaseModule> moduleClass) {
		if (registryLocation != null && !this.modules.containsKey(registryLocation)) {
			this.modules.put(registryLocation, moduleClass);
		}
	}

	public Map<ResourceLocation, Class<? extends SeabaseModule>> getRegistryMap() {
		return new HashMap(this.modules);
	}

	public Class<? extends SeabaseModule> getModuleClass(ResourceLocation moduleLocation) {
		return this.modules.get(moduleLocation);
	}

	public ResourceLocation getResourceLocation(Class<? extends SeabaseModule> moduleClass) {
		for (Entry<ResourceLocation, Class<? extends SeabaseModule>> entry : this.modules.entrySet()) {
			if (entry.getValue().equals(moduleClass)) {
				return entry.getKey();
			}
		}
		return new ResourceLocation(BetterDiving.MOD_ID, "");
	}

	public SeabaseModule createModuleFromResourceLocation(World world, ResourceLocation registryLocation) {
		if (world != null && registryLocation != null) {
			Class<? extends SeabaseModule> moduleClass = this.modules.get(registryLocation);
			if (moduleClass != null) {
				SeabaseModule module = null;
				try {
					module = moduleClass.getDeclaredConstructor(World.class).newInstance(world);
				} catch (Exception e) {
					BetterDiving.logger.error("Failed to create seabase module from resource location " + registryLocation, e);
				}
				return module;
			}
		}
		return null;
	}

}
