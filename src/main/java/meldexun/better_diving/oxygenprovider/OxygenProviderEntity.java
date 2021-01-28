package meldexun.better_diving.oxygenprovider;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class OxygenProviderEntity {

	private final ResourceLocation registryName;
	private final int oxygenCapacity;
	private final boolean isValid;

	public OxygenProviderEntity(ResourceLocation registryName, int oxygenCapacity) {
		this.registryName = registryName;
		this.oxygenCapacity = Math.max(oxygenCapacity, 0);
		this.isValid = this.registryName != null && this.oxygenCapacity > 0;
	}

	public OxygenProviderEntity(String config) {
		String[] configArray = config.split(",");
		for (int i = 0; i < configArray.length; i++) {
			configArray[i] = configArray[i].trim();
		}
		String entityConfig = "";
		int oxygenCapacityConfig = 0;
		boolean flag = true;
		try {
			entityConfig = configArray[0];
			oxygenCapacityConfig = Integer.parseInt(configArray[1]);
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			flag = false;
		}
		if (flag) {
			this.registryName = new ResourceLocation(entityConfig);
			if (ForgeRegistries.ENTITIES.containsKey(this.registryName)) {
				this.oxygenCapacity = Math.max(oxygenCapacityConfig, 0);
				this.isValid = this.oxygenCapacity > 0;
			} else {
				this.oxygenCapacity = 0;
				this.isValid = false;
			}
		} else {
			this.registryName = null;
			this.oxygenCapacity = 0;
			this.isValid = false;
		}
	}

	public ResourceLocation getRegistryName() {
		return this.registryName;
	}

	public int getOxygenCapacity() {
		return this.oxygenCapacity;
	}

	public boolean isValid() {
		return this.isValid;
	}

}
