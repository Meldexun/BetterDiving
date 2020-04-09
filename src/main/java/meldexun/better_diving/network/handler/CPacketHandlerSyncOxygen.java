package meldexun.better_diving.network.handler;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.diving.ICapabilityDivingAttributes;
import meldexun.better_diving.capability.item.oxygen.CapabilityOxygenProvider;
import meldexun.better_diving.capability.item.oxygen.ICapabilityOxygen;
import meldexun.better_diving.network.packet.SPacketSyncOxygen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketHandlerSyncOxygen implements IMessageHandler<SPacketSyncOxygen, IMessage> {

	@Override
	public IMessage onMessage(SPacketSyncOxygen message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
			if (ctx.side.isClient()) {
				EntityPlayer player = BetterDiving.proxy.getPlayer(ctx);
				ICapabilityDivingAttributes idiving = player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);

				idiving.setOxygen(message.getOxygenPlayer());

				ICapabilityOxygen ioxygen = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getCapability(CapabilityOxygenProvider.OXYGEN, null);
				if (ioxygen != null) {
					ioxygen.setOxygen(message.getOxygenTank());
				}
			}
		});
		return null;
	}

}
