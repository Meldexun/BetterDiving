package meldexun.better_diving.capability.inventory;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.BasicCapabilityProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityItemHandlerProvider extends BasicCapabilityProvider<IItemHandler> {

	public static final ResourceLocation LOCATION_ITEM_STACK_HANDLER = new ResourceLocation(BetterDiving.MOD_ID, "item_stack_handler");

	public CapabilityItemHandlerProvider(Capability<IItemHandler> capability, IItemHandler instance) {
		super(capability, instance);
	}

	public static CapabilityItemHandlerProvider createProvider(int slots) {
		return new CapabilityItemHandlerProvider(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, new ItemStackHandler(slots));
	}

}
