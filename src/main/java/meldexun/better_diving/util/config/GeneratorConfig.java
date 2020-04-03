package meldexun.better_diving.util.config;

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
	@Config.RangeInt(min = 0, max = 256)
	public int minHeight;
	@Config.RangeInt(min = 0, max = 256)
	public int maxHeight;
	@Config.Comment("Biomes in which this plant/ore can generate. For Mod-Biomes: modid:biome_name. Definitely supported mods: BiomesOPlenty.")
	public String[] biomes;
	@Config.Comment("Dimensions in which this plant/ore can generate.")
	public int[] dimensions;

	public GeneratorConfig(boolean shouldGenerate, int chance, int amount, int minHeight, int maxHeight, String[] biomes, int[] dimensions) {
		this.shouldGenerate = shouldGenerate;
		this.chance = chance;
		this.amount = amount;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.biomes = biomes;
		this.dimensions = dimensions;
	}

}
