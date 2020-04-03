package meldexun.better_diving.util.config;

import net.minecraftforge.common.config.Config;

public class EntityConfig {

	public boolean enabled;
	@Config.RangeInt(min = 1, max = 1000)
	public int weight;
	public String[] biomes;
	public int[] dimensions;

	public EntityConfig(boolean enabled, int weight, String[] biomes, int[] dimensions) {
		this.enabled = enabled;
		this.weight = weight;
		this.biomes = biomes;
		this.dimensions = dimensions;
	}

}
