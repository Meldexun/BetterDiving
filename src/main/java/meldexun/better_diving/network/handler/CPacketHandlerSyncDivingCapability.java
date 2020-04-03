package meldexun.better_diving.network.handler;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.diving.ICapabilityDivingAttributes;
import meldexun.better_diving.network.packet.SPacketSyncDivingCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketHandlerSyncDivingCapability implements IMessageHandler<SPacketSyncDivingCapability, IMessage> {

	@Override
	public IMessage onMessage(SPacketSyncDivingCapability message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
			if (ctx.side.isClient()) {
				EntityPlayer player = BetterDiving.proxy.getPlayer(ctx);
				ICapabilityDivingAttributes idiving = player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);

				idiving.setOxygenCapacity(message.getMaxAir());
				idiving.setSwimSpeedBase(message.getSwimBaseSpeed());
				idiving.setSwimSpeedBonus(message.getSwimBonusSpeed());
				idiving.setBreakSpeed(message.getBreakSpeed());
			}
		});
		return null;
	}

}
