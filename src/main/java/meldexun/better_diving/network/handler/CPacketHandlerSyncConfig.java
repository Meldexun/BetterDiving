package meldexun.better_diving.network.handler;

import meldexun.better_diving.network.packet.SPacketSyncConfig;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketHandlerSyncConfig implements IMessageHandler<SPacketSyncConfig, IMessage> {

	@Override
	public IMessage onMessage(SPacketSyncConfig message, MessageContext ctx) {
		return null;
	}

}
