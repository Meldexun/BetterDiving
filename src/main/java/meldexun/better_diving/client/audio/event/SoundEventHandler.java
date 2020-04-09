package meldexun.better_diving.client.audio.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.audio.UnderwaterAmbientLoopSound;
import meldexun.better_diving.client.audio.UnderwaterAmbientSound;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.init.ModSounds;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Side.CLIENT)
public class SoundEventHandler {

	private SoundEventHandler() {

	}

	private static UnderwaterAmbientLoopSound underwaterSound;
	private static boolean prevInsideWater;

	@SubscribeEvent
	public static void updateUnderwaterAmbience(ClientTickEvent event) {
		if (event.phase == Phase.START) {
			Minecraft mc = Minecraft.getMinecraft();
			SoundHandler soundHandler = mc.getSoundHandler();
			World world = mc.world;
			EntityPlayer player = mc.player;

			if (world != null && mc.gameSettings.getSoundLevel(SoundCategory.MASTER) > 0.0F) {
				if (BetterDivingConfig.getInstance().client.underWaterAmbience) {
					if (player.isInsideOfMaterial(Material.WATER)) {
						if (!soundHandler.isSoundPlaying(SoundEventHandler.underwaterSound)) {
							SoundEventHandler.underwaterSound = new UnderwaterAmbientLoopSound(mc.player);
							soundHandler.playSound(SoundEventHandler.underwaterSound);
						}

						float f = world.rand.nextFloat();
						if (f < 0.0001F) {
							soundHandler.playSound(new UnderwaterAmbientSound(player, ModSounds.UNDERWATER_AMBIENCE_ADDITIONS_ULTRA_RARE));
						} else if (f < 0.001F) {
							soundHandler.playSound(new UnderwaterAmbientSound(player, ModSounds.UNDERWATER_AMBIENCE_ADDITIONS_RARE));
						} else if (f < 0.01F) {
							soundHandler.playSound(new UnderwaterAmbientSound(player, ModSounds.UNDERWATER_AMBIENCE_ADDITIONS));
						}
					}
				}

				if (!SoundEventHandler.prevInsideWater && player.isInsideOfMaterial(Material.WATER) && !(player.getRidingEntity() instanceof EntitySeamoth)) {
					soundHandler.playSound(new UnderwaterAmbientSound(player, ModSounds.UNDERWATER_ENTER));
				} else if (SoundEventHandler.prevInsideWater && !player.isInsideOfMaterial(Material.WATER) && !(player.getRidingEntity() instanceof EntitySeamoth)) {
					soundHandler.playSound(new UnderwaterAmbientSound(player, ModSounds.UNDERWATER_EXIT));
				}
				SoundEventHandler.prevInsideWater = player.isInsideOfMaterial(Material.WATER) && !(player.getRidingEntity() instanceof EntitySeamoth);
			}
		}
	}

}
