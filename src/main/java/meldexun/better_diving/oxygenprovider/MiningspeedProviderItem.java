package meldexun.better_diving.oxygenprovider;

import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class MiningspeedProviderItem extends AbstractDivingGear {

	public final double miningspeed;

	public MiningspeedProviderItem(ResourceLocation registryName, Set<EquipmentSlotType> slots, float miningspeed) {
		super(registryName, slots);
		this.miningspeed = miningspeed;
	}

	@Nullable
	public static MiningspeedProviderItem parse(String config) {
		try {
			String[] configArray = config.split(",");
			for (int i = 0; i < configArray.length; i++) {
				configArray[i] = configArray[i].trim();
			}
			ResourceLocation registryName = new ResourceLocation(configArray[0]);
			Set<EquipmentSlotType> slots = parseValidSlots(configArray, 1);
			float miningspeed = Float.parseFloat(configArray[7]);
			return new MiningspeedProviderItem(registryName, slots, miningspeed);
		} catch (Exception e) {
			return null;
		}
	}

}
