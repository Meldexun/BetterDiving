package meldexun.better_diving.util.config;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;

public class GeneratorConfig {

	@Config.Comment("Enable/Disable the generation of this plant/ore.")
	public boolean shouldGenerate;
	@Config.Comment("Every x chunks this plant/ore will be generated.")
	@Config.RangeInt(min = 1, max = 1000)
	public int chance;
	@Config.Comment("Amount of this plant/ore per chunk.")
	@Config.RangeInt(min = 1, max = 128)
	public int amount;
	@Config.Comment("Usage changes depending on seaLevelRelative.")
	@Config.RangeInt(min = 0, max = 256)
	public int minHeight;
	@Config.Comment("Usage changes depending on seaLevelRelative.")
	@Config.RangeInt(min = 0, max = 256)
	public int maxHeight;
	@Config.Comment("When enabled minHeight defines how many blocks below sea level you have to at least be to find this plant/ore and maxHeight defines how many blocks below sea level you have to at most be to find this plant/ore.")
	public boolean seaLevelRelative;
	@Config.Comment("Set this to true to generate this plant/ore in every biome.")
	@Config.Ignore
	public boolean biomeOverride;
	@Config.Comment("Biome types in which this plant/ore can generate.")
	public String[] biomeTypes;
	@Config.Comment("Biomes in which this plant/ore can generate. For modded biomes: modid:biome_name.")
	public String[] biomes;
	@Config.Comment("Dimensions in which this plant/ore can generate.")
	public int[] dimensions;

	public GeneratorConfig(boolean shouldGenerate, int chance, int amount, int minHeight, int maxHeight, boolean seaLevelRelative, boolean biomeOverride, String[] biomeTypes, String[] biomes, int[] dimensions) {
		this.shouldGenerate = shouldGenerate;
		this.chance = chance;
		this.amount = amount;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.seaLevelRelative = seaLevelRelative;
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
		return this.shouldGenerate;
	}

	public ResourceLocation[] getBiomes() {
		ResourceLocation[] biomeRegistryNames = new ResourceLocation[this.biomes.length];
		for (int i = 0; i < this.biomes.length; i++) {
			biomeRegistryNames[i] = new ResourceLocation(this.biomes[i]);
		}
		return biomeRegistryNames;
	}

}
