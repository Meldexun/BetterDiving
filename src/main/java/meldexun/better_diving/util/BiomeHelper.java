package meldexun.better_diving.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public class BiomeHelper {

	public static List<Biome> getBiomes(String[] biomes) {
		ArrayList<Biome> biomeList = new ArrayList<Biome>();
		for (String biomeName : biomes) {
			Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(biomeName));
			if (biome != null) {
				biomeList.add(biome);
			}
		}
		return biomeList;
	}

	public static List<Integer> getDimensions(int[] dimensions) {
		List<Integer> dimList = new ArrayList<>(dimensions.length);
		for (int i : dimensions) {
			dimList.add(i);
		}
		return dimList;
	}

}
