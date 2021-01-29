package meldexun.better_diving.capability.oxygen.item;

import java.util.EnumSet;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.api.capability.ICapabilityOxygenItem;
import meldexun.better_diving.capability.BasicCapabilityProvider;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.NonNullSupplier;

public class CapabilityOxygenItemProvider extends BasicCapabilityProvider<ICapabilityOxygenItem> {

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(BetterDiving.MOD_ID, "oxygen");

	@CapabilityInject(ICapabilityOxygenItem.class)
	public static final Capability<ICapabilityOxygenItem> OXYGEN = null;

	public CapabilityOxygenItemProvider(NonNullSupplier<ICapabilityOxygenItem> supplier) {
		super(OXYGEN, supplier);
	}

	public static void register() {
		CapabilityManager.INSTANCE.register(ICapabilityOxygenItem.class, new CapabilityOxygenItemStorage(), () -> new CapabilityOxygenItem(ItemStack.EMPTY, 0, EnumSet.noneOf(EquipmentSlotType.class), false));
	}

}
