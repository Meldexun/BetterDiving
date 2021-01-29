package meldexun.better_diving.oxygenprovider;

import java.util.EnumSet;
import java.util.Set;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractDivingGear {

	public final ResourceLocation registryName;
	public final Set<EquipmentSlotType> slots;

	public AbstractDivingGear(ResourceLocation registryName, Set<EquipmentSlotType> slots) {
		this.registryName = registryName;
		this.slots = EnumSet.copyOf(slots);
	}

	public boolean isValidSlot(EquipmentSlotType slot) {
		return this.slots.contains(slot);
	}

	protected static Set<EquipmentSlotType> parseValidSlots(String[] configArray, int offset) {
		Set<EquipmentSlotType> slots = EnumSet.noneOf(EquipmentSlotType.class);
		for (int i = 0; i < EquipmentSlotType.values().length; i++) {
			if (Boolean.parseBoolean(configArray[i + offset])) {
				slots.add(EquipmentSlotType.values()[i]);
			}
		}
		return slots;
	}

}
