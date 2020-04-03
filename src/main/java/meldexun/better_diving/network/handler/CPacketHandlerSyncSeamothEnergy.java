package meldexun.better_diving.network.handler;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.network.packet.SPacketSyncSeamothEnergy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketHandlerSyncSeamothEnergy implements IMessageHandler<SPacketSyncSeamothEnergy, IMessage> {

	@Override
	public IMessage onMessage(SPacketSyncSeamothEnergy message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
			if (ctx.side.isClient()) {
				EntityPlayer player = BetterDiving.proxy.getPlayer(ctx);
				if (player.getRidingEntity() instanceof EntitySeamoth) {
					EntitySeamoth seamoth = (EntitySeamoth) player.getRidingEntity();

					seamoth.setEnergy(message.getEnergy());
				}
			}
		});
		return null;
	}

}
