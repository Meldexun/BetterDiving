package meldexun.better_diving.capability.item.inventory;

import meldexun.better_diving.capability.BasicCapabilityProvider;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class CapabilityItemHandlerItemProvider extends BasicCapabilityProvider<IItemHandler> {

	public CapabilityItemHandlerItemProvider(Capability<IItemHandler> capability, IItemHandler instance) {
		super(capability, instance);
	}

	public static CapabilityItemHandlerItemProvider createProvider(ItemStack stack, int slots) {
		return new CapabilityItemHandlerItemProvider(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, new CapabilityItemHandlerItem(stack, slots));
	}

}
