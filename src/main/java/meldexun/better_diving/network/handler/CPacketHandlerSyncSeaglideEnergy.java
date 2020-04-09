package meldexun.better_diving.network.handler;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.item.ItemSeaglide;
import meldexun.better_diving.item.ItemTool;
import meldexun.better_diving.network.packet.SPacketSyncSeaglideEnergy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketHandlerSyncSeaglideEnergy implements IMessageHandler<SPacketSyncSeaglideEnergy, IMessage> {

	@Override
	public IMessage onMessage(SPacketSyncSeaglideEnergy message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
			if (ctx.side.isClient()) {
				EntityPlayer player = BetterDiving.proxy.getPlayer(ctx);
				ItemStack stack = player.getHeldItem(message.getHand());
				if (stack.getItem() instanceof ItemSeaglide) {
					ItemTool.setEnergy(stack, message.getEnergy());
				}
			}
		});
		return null;
	}

}
