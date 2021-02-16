package meldexun.better_diving.capability.inventory;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.BasicCapabilityProviderSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class CapabilityItemHandlerProvider extends BasicCapabilityProviderSerializable<IItemHandler> {

	public static final ResourceLocation LOCATION_ITEM_STACK_HANDLER = new ResourceLocation(BetterDiving.MOD_ID, "item_stack_handler");

	public CapabilityItemHandlerProvider(NonNullSupplier<IItemHandler> instanceSupplier) {
		super(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, instanceSupplier);
	}

}
