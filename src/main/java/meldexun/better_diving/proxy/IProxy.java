package meldexun.better_diving.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface IProxy {

	void preInit();

	void init();

	void postInit();

	EntityPlayer getPlayer(MessageContext ctx);

	World getWorld(MessageContext ctx);

}
