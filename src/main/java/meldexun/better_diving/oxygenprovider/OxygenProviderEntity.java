package meldexun.better_diving.oxygenprovider;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;

public class OxygenProviderEntity {

	public final ResourceLocation registryName;
	public final int oxygenCapacity;

	public OxygenProviderEntity(ResourceLocation registryName, int oxygenCapacity) {
		this.registryName = registryName;
		this.oxygenCapacity = Math.max(oxygenCapacity, 0);
	}

	@Nullable
	public static OxygenProviderEntity parse(String config) {
		try {
			String[] configArray = config.split(",");
			for (int i = 0; i < configArray.length; i++) {
				configArray[i] = configArray[i].trim();
			}
			ResourceLocation registryName = new ResourceLocation(configArray[0]);
			int oxygenCapacity = Integer.parseInt(configArray[1]);
			return new OxygenProviderEntity(registryName, oxygenCapacity);
		} catch (Exception e) {
			return null;
		}
	}

}
