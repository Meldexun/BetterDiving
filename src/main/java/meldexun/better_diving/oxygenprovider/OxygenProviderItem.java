package meldexun.better_diving.oxygenprovider;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class OxygenProviderItem {

	private final Item item;
	private final int oxygenCapacity;
	private final Set<EquipmentSlotType> equipmentSlots;
	private final int maxDivingDepth;
	private final boolean isValid;

	public OxygenProviderItem(Item item, int oxygenCapacity, Set<EquipmentSlotType> equipmentSlots, int maxDivingDepth) {
		this.item = item;
		this.oxygenCapacity = Math.max(oxygenCapacity, 0);
		this.equipmentSlots = new HashSet<>(equipmentSlots);
		this.maxDivingDepth = Math.max(maxDivingDepth, 0);
		this.isValid = this.item != null && (this.oxygenCapacity > 0 || this.maxDivingDepth > 0) && !this.equipmentSlots.isEmpty();
	}

	public OxygenProviderItem(String config) {
		String[] configArray = config.split(",");
		for (int i = 0; i < configArray.length; i++) {
			configArray[i] = configArray[i].trim();
		}
		String itemConfig = "";
		int oxygenCapacityConfig = 0;
		int equipmentSlotsConfig = 0;
		int maxDivingDepthConfig = 0;
		boolean flag = true;
		try {
			itemConfig = configArray[0];
			oxygenCapacityConfig = Integer.parseInt(configArray[1]);
			equipmentSlotsConfig = Integer.parseInt(configArray[2]);
			maxDivingDepthConfig = Integer.parseInt(configArray[3]);
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			flag = false;
		}
		if (flag) {
			this.item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemConfig));
			this.oxygenCapacity = Math.max(oxygenCapacityConfig, 0);
			this.equipmentSlots = new HashSet<>();
			for (EquipmentSlotType slot : EquipmentSlotType.values()) {
				if (((equipmentSlotsConfig >> slot.ordinal()) & 1) == 1) {
					this.equipmentSlots.add(slot);
				}
			}
			this.maxDivingDepth = Math.max(maxDivingDepthConfig, 0);
			this.isValid = this.item != null && (this.oxygenCapacity > 0 || this.maxDivingDepth > 0) && !this.equipmentSlots.isEmpty();
		} else {
			this.item = null;
			this.oxygenCapacity = 0;
			this.equipmentSlots = Collections.emptySet();
			this.maxDivingDepth = 0;
			this.isValid = false;
		}
	}

	public Item getItem() {
		return this.item;
	}

	public int getOxygenCapacity() {
		return this.oxygenCapacity;
	}

	public Set<EquipmentSlotType> getEquipmentSlots() {
		return this.equipmentSlots;
	}

	public int getMaxDivingDepth() {
		return this.maxDivingDepth;
	}

	public boolean isValid() {
		return this.isValid;
	}

}
