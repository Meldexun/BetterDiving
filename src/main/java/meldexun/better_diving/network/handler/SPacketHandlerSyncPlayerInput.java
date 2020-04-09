package meldexun.better_diving.network.handler;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.diving.ICapabilityDivingAttributes;
import meldexun.better_diving.network.packet.CPacketSyncPlayerInput;
import meldexun.better_diving.network.packet.SPacketSyncPlayerInput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketHandlerSyncPlayerInput implements IMessageHandler<CPacketSyncPlayerInput, IMessage> {

	@Override
	public IMessage onMessage(CPacketSyncPlayerInput message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
			if (ctx.side.isServer()) {
				EntityPlayer player = BetterDiving.proxy.getPlayer(ctx);
				ICapabilityDivingAttributes idiving = player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);
				idiving.setPrevIsDiving(idiving.isDiving());
				idiving.setPrevDivingTick(idiving.getDivingTick());
				idiving.setPrevDivingTickHorizontal(idiving.getDivingTickHorizontal());
				idiving.setPrevDivingTickVertical(idiving.getDivingTickVertical());
				idiving.setIsDiving(message.isDiving());
				idiving.setDivingTick(message.getDivingTick());
				idiving.setDivingTickHorizontal(message.getDivingTickHorizontal());
				idiving.setDivingTickVertical(message.getDivingTickVertical());
				BetterDiving.network.sendToAllTracking(new SPacketSyncPlayerInput(player.getEntityId(), message.isDiving(), message.getDivingTick(), message.getDivingTickHorizontal(), message.getDivingTickVertical()), player);
			}
		});
		return null;
	}

}
