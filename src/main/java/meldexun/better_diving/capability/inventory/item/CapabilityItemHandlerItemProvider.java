package meldexun.better_diving.capability.inventory.item;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.BasicCapabilityProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class CapabilityItemHandlerItemProvider extends BasicCapabilityProvider<IItemHandler> {

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(BetterDiving.MOD_ID, "item_stack_handler");

	public CapabilityItemHandlerItemProvider(NonNullSupplier<IItemHandler> instanceSupplier) {
		super(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, instanceSupplier);
	}

}
