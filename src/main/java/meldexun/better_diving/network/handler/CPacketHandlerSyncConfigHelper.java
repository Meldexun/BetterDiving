package meldexun.better_diving.network.handler;

import meldexun.better_diving.network.packet.SPacketSyncConfigHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketHandlerSyncConfigHelper implements IMessageHandler<SPacketSyncConfigHelper, IMessage> {

	@Override
	public IMessage onMessage(SPacketSyncConfigHelper message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
			if (ctx.side.isClient()) {

			}
		});
		return null;
	}

}
