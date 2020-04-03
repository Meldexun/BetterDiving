package meldexun.better_diving.client.audio.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.audio.UnderwaterAmbientSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Side.CLIENT)
public class SoundEventHandler {

	public static UnderwaterAmbientSound underwaterSound;

	@SubscribeEvent
	public static void updateUnderwaterAmbience(ClientTickEvent event) {
		if (event.phase == Phase.START) {
			Minecraft mc = Minecraft.getMinecraft();

			if (mc.world != null && mc.gameSettings.getSoundLevel(SoundCategory.MASTER) > 0.0F) {
				SoundHandler soundHandler = mc.getSoundHandler();

				if (!soundHandler.isSoundPlaying(SoundEventHandler.underwaterSound)) {
					SoundEventHandler.underwaterSound = new UnderwaterAmbientSound(mc.player);
					soundHandler.playSound(SoundEventHandler.underwaterSound);
				}
			}
		}
	}

}
