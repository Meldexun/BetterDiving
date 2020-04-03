package meldexun.better_diving.network.handler;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.network.packet.CPacketSyncSeamoth;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketHandlerSyncSeamoth implements IMessageHandler<CPacketSyncSeamoth, IMessage> {

	@Override
	public IMessage onMessage(CPacketSyncSeamoth message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
			if (ctx.side.isServer()) {
				EntityPlayer player = BetterDiving.proxy.getPlayer(ctx);
				if (player.getRidingEntity() instanceof EntitySeamoth) {
					EntitySeamoth seamoth = (EntitySeamoth) player.getRidingEntity();

					seamoth.inputForward = message.getInputForward();
					seamoth.inputRight = message.getInputRight();
					seamoth.inputBack = message.getInputBack();
					seamoth.inputLeft = message.getInputLeft();
					seamoth.inputUp = message.getInputUp();
					seamoth.inputDown = message.getInputDown();
					seamoth.rotationYaw = message.getYaw();
					seamoth.rotationPitch = message.getPitch();
				}
			}
		});
		return null;
	}

}
