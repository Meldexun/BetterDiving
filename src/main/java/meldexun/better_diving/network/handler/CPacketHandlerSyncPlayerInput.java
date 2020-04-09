package meldexun.better_diving.network.handler;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.diving.ICapabilityDivingAttributes;
import meldexun.better_diving.network.packet.SPacketSyncPlayerInput;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketHandlerSyncPlayerInput implements IMessageHandler<SPacketSyncPlayerInput, IMessage> {

	@Override
	public IMessage onMessage(SPacketSyncPlayerInput message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
			if (ctx.side.isClient()) {
				World world = BetterDiving.proxy.getWorld(ctx);
				Entity entity = world.getEntityByID(message.getEntityId());
				if (entity instanceof EntityPlayer && entity != Minecraft.getMinecraft().player) {
					ICapabilityDivingAttributes idiving = entity.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);
					idiving.setPrevIsDiving(idiving.isDiving());
					idiving.setPrevDivingTick(idiving.getDivingTick());
					idiving.setPrevDivingTickHorizontal(idiving.getDivingTickHorizontal());
					idiving.setPrevDivingTickVertical(idiving.getDivingTickVertical());
					idiving.setIsDiving(message.isDiving());
					idiving.setDivingTick(message.getDivingTick());
					idiving.setDivingTickHorizontal(message.getDivingTickHorizontal());
					idiving.setDivingTickVertical(message.getDivingTickVertical());
				}
			}
		});
		return null;
	}

}
