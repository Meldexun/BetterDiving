package meldexun.better_diving.util.config;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;

public class EntityConfig {

	public boolean enabled;
	@Config.RangeInt(min = 1, max = 1000)
	public int weight;
	@Config.Ignore
	public boolean biomeOverride;
	public String[] biomeTypes;
	public String[] biomes;
	public int[] dimensions;

	public EntityConfig(boolean enabled, int weight, boolean biomeOverride, String[] biomeTypes, String[] biomes, int[] dimensions) {
		this.enabled = enabled;
		this.weight = weight;
		this.biomeOverride = biomeOverride;
		this.biomeTypes = biomeTypes;
		this.biomes = biomes;
		this.dimensions = dimensions;
	}

	public boolean isEnabled() {
		if (this.dimensions.length == 0) {
			return false;
		}
		if (!this.biomeOverride && this.biomeTypes.length == 0 && this.biomes.length == 0) {
			return false;
		}
		return this.enabled;
	}

	public ResourceLocation[] getBiomes() {
		ResourceLocation[] biomeRegistryNames = new ResourceLocation[this.biomes.length];
		for (int i = 0; i < this.biomes.length; i++) {
			biomeRegistryNames[i] = new ResourceLocation(this.biomes[i]);
		}
		return biomeRegistryNames;
	}

}
