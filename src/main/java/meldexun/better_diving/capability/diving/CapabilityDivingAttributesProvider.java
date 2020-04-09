package meldexun.better_diving.capability.diving;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.BasicCapabilityProviderSerializable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityDivingAttributesProvider extends BasicCapabilityProviderSerializable<ICapabilityDivingAttributes> {

	public static final ResourceLocation LOCATION_DIVING_ATTRIBUTES = new ResourceLocation(BetterDiving.MOD_ID, "diving_attributes");

	@CapabilityInject(ICapabilityDivingAttributes.class)
	public static final Capability<ICapabilityDivingAttributes> DIVING_ATTRIBUTES = null;

	public CapabilityDivingAttributesProvider(Capability<ICapabilityDivingAttributes> capability, ICapabilityDivingAttributes instance) {
		super(capability, instance);
	}

	public static void register() {
		CapabilityManager.INSTANCE.register(ICapabilityDivingAttributes.class, new CapabilityDivingAttributesStorage(), CapabilityDivingAttributes::new);
	}

	public static CapabilityDivingAttributesProvider createProvider(EntityPlayer player) {
		return new CapabilityDivingAttributesProvider(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, new CapabilityDivingAttributes(player));
	}

}
