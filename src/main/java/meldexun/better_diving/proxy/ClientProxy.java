package meldexun.better_diving.proxy;

import org.lwjgl.input.Keyboard;

import meldexun.better_diving.client.event.ClientEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy implements IProxy {

	public static KeyBinding keyBindSeamothDescend = new KeyBinding("Seamoth descend", Keyboard.KEY_C, "Better Diving");
	// public static KeyBinding keyBindSwapBattery = new KeyBinding("Swap battery", Keyboard.KEY_R, "Better Diving");

	@Override
	public void preInit() {

	}

	@Override
	public void init() {
		ClientRegistry.registerKeyBinding(ClientProxy.keyBindSeamothDescend);
		// ClientRegistry.registerKeyBinding(keyBindSwapBattery);

		ClientEventHandler.registerRenderers();
	}

	@Override
	public void postInit() {

	}

	@Override
	public EntityPlayer getPlayer(MessageContext ctx) {
		if (ctx.side.isClient()) {
			return Minecraft.getMinecraft().player;
		} else {
			return ctx.getServerHandler().player;
		}
	}

	@Override
	public World getWorld(MessageContext ctx) {
		if (ctx.side.isClient()) {
			return Minecraft.getMinecraft().world;
		} else {
			return ctx.getServerHandler().player.world;
		}
	}

}
