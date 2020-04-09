package meldexun.better_diving.network.handler;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.network.packet.SPacketSyncSeamothInput;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketHandlerSyncSeamothInput implements IMessageHandler<SPacketSyncSeamothInput, IMessage> {

	@Override
	public IMessage onMessage(SPacketSyncSeamothInput message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
			if (ctx.side.isClient()) {
				EntityPlayer player = BetterDiving.proxy.getPlayer(ctx);
				World world = BetterDiving.proxy.getWorld(ctx);
				Entity entity = world.getEntityByID(message.getEntityId());

				if (entity instanceof EntitySeamoth) {
					EntitySeamoth seamoth = (EntitySeamoth) entity;

					if (seamoth != player.getRidingEntity()) {
						seamoth.inputForward = message.getInputForward();
						seamoth.inputRight = message.getInputRight();
						seamoth.inputBack = message.getInputBack();
						seamoth.inputLeft = message.getInputLeft();
						seamoth.inputUp = message.getInputUp();
						seamoth.inputDown = message.getInputDown();
					}
				}
			}
		});
		return null;
	}

}
