package meldexun.better_diving.oxygenprovider;

import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class OxygenProviderItem extends AbstractDivingGear {

	public final int oxygenCapacity;
	public final boolean needsDivingMask;

	public OxygenProviderItem(ResourceLocation registryName, Set<EquipmentSlotType> slots, int oxygenCapacity, boolean needsDivingMask) {
		super(registryName, slots);
		this.oxygenCapacity = oxygenCapacity;
		this.needsDivingMask = needsDivingMask;
	}

	@Nullable
	public static OxygenProviderItem parse(String config) {
		try {
			String[] configArray = config.split(",");
			for (int i = 0; i < configArray.length; i++) {
				configArray[i] = configArray[i].trim();
			}
			ResourceLocation registryName = new ResourceLocation(configArray[0]);
			Set<EquipmentSlotType> slots = parseValidSlots(configArray, 1);
			int oxygenCapacity = Integer.parseInt(configArray[7]);
			boolean needsDivingMask = Boolean.parseBoolean(configArray[8]);
			return new OxygenProviderItem(registryName, slots, oxygenCapacity, needsDivingMask);
		} catch (Exception e) {
			return null;
		}
	}

}
