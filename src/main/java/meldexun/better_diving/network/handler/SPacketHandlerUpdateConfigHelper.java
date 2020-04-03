package meldexun.better_diving.network.handler;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.network.packet.CPacketUpdateConfigHelper;
import meldexun.better_diving.network.packet.SPacketSyncDivingCapability;
import meldexun.better_diving.util.BetterDivingConfigClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketHandlerUpdateConfigHelper implements IMessageHandler<CPacketUpdateConfigHelper, IMessage> {

	@Override
	public IMessage onMessage(CPacketUpdateConfigHelper message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
			if (ctx.side.isServer()) {
				BetterDivingConfigClient.update();
				EntityPlayer player = BetterDiving.proxy.getPlayer(ctx);
				BetterDiving.CONNECTION.sendTo(new SPacketSyncDivingCapability(player), (EntityPlayerMP) player);
			}
		});
		return null;
	}

}
