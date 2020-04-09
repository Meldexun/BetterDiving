package meldexun.better_diving.network.handler;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.network.packet.CPacketCraftRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketHandlerCraftRecipe implements IMessageHandler<CPacketCraftRecipe, IMessage> {

	@Override
	public IMessage onMessage(CPacketCraftRecipe message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
			if (ctx.side.isServer()) {
				EntityPlayer player = BetterDiving.proxy.getPlayer(ctx);
				message.getRecipe().tryCraft(player);
			}
		});
		return null;
	}

}
