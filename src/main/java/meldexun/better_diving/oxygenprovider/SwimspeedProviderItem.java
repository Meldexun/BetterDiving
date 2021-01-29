package meldexun.better_diving.oxygenprovider;

import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class SwimspeedProviderItem extends AbstractDivingGear {

	public final double swimspeed;

	public SwimspeedProviderItem(ResourceLocation registryName, Set<EquipmentSlotType> slots, double swimspeed) {
		super(registryName, slots);
		this.swimspeed = swimspeed;
	}

	@Nullable
	public static SwimspeedProviderItem parse(String config) {
		try {
			String[] configArray = config.split(",");
			for (int i = 0; i < configArray.length; i++) {
				configArray[i] = configArray[i].trim();
			}
			ResourceLocation registryName = new ResourceLocation(configArray[0]);
			Set<EquipmentSlotType> slots = parseValidSlots(configArray, 1);
			double swimspeed = Double.parseDouble(configArray[7]);
			return new SwimspeedProviderItem(registryName, slots, swimspeed);
		} catch (Exception e) {
			return null;
		}
	}

}
