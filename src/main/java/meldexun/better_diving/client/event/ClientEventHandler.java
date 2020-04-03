package meldexun.better_diving.client.event;

import org.lwjgl.opengl.GL11;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.item.AbstractItemDivingGear;
import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.EntityHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Side.CLIENT)
public class ClientEventHandler {

	@SubscribeEvent
	public static void hideWaterOverlay(RenderBlockOverlayEvent event) {
		if (BetterDivingConfig.MODULES.visionUnderWater && BetterDivingConfig.CLIENT_SETTINGS.hideWaterOverlay && event.getBlockForOverlay().getBlock() == Blocks.WATER) {
			EntityPlayer player = event.getPlayer();
			ItemStack helm = player.inventory.armorInventory.get(3);
			if (player.inventory.armorInventory.get(3).getItem() instanceof AbstractItemDivingGear || player.getRidingEntity() instanceof EntitySeamoth) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void setupFog(EntityViewRenderEvent.FogDensity event) {
		if (BetterDivingConfig.MODULES.visionUnderWater && event.getState().getMaterial() == Material.WATER && event.getEntity() instanceof EntityPlayer && !((EntityPlayer) event.getEntity()).isPotionActive(Potion.getPotionById(15))) {
			long time = (event.getEntity().world.getWorldTime() + 1450L) % 24000L;
			int blocksUnderwater = EntityHelper.blocksUnderWater(event.getEntity());

			if (BetterDivingConfig.CLIENT_SETTINGS.fogSettings.fogColorEnabled) {
				setupFogColor((double) time, (double) blocksUnderwater);
			}
			if (BetterDivingConfig.CLIENT_SETTINGS.fogSettings.fogDensityEnabled) {
				setupFogDensity((double) time, (double) blocksUnderwater);
			}
		}
	}

	public static void setupFogColor(double time, double blocksUnderwater) {
		double[] color = BetterDivingConfig.CLIENT_SETTINGS.fogSettings.fogColor;
		double[] colorNight = BetterDivingConfig.CLIENT_SETTINGS.fogSettings.fogColorNight;
		double[] colorBlocksUnderWater = BetterDivingConfig.CLIENT_SETTINGS.fogSettings.fogColorBlocksUnderWater;
		double red = color[0];
		double green = color[1];
		double blue = color[2];
		double d = 0.0D;

		if (time >= 0.0D && time <= 1900.0D) {
			// Morning ticks 0 -> 1900
			d = 1.0D - time / 1900.0D;
		} else if (time > 1900.0D && time <= 13067.0D) {
			// Day ticks 1900 -> 13067
			d = 0.0D;
		} else if (time > 13067.0D && time <= 15250.0D) {
			// Evening ticks 13067 -> 15250
			d = (time - 13067.0D) / 2183.0D;
		} else if (time > 15250.0D && time <= 24000.0D) {
			// Night ticks 15250 -> 24000
			d = 1.0D;
		}

		red *= 1.0D + colorNight[0] * d;
		green *= 1.0D + colorNight[1] * d;
		blue *= 1.0D + colorNight[2] * d;

		red *= 1.0D + colorBlocksUnderWater[0] * blocksUnderwater;
		green *= 1.0D + colorBlocksUnderWater[1] * blocksUnderwater;
		blue *= 1.0D + colorBlocksUnderWater[2] * blocksUnderwater;

		red = MathHelper.clamp(color[0], 0.0D, 1.0D);
		green = MathHelper.clamp(color[1], 0.0D, 1.0D);
		blue = MathHelper.clamp(color[2], 0.0D, 1.0D);
		GL11.glFog(GL11.GL_FOG_COLOR, RenderHelper.setColorBuffer((float) red, (float) green, (float) blue, 1.0F));
	}

	public static void setupFogDensity(double time, double blocksUnderwater) {
		double density = BetterDivingConfig.CLIENT_SETTINGS.fogSettings.fogDensity;
		double densityNight = BetterDivingConfig.CLIENT_SETTINGS.fogSettings.fogDensityNight;
		double densityBlocksUnderWater = BetterDivingConfig.CLIENT_SETTINGS.fogSettings.fogDensityBlocksUnderWater;
		double d = 0.0D;

		if (time >= 0.0D && time <= 1900.0D) {
			// Morning ticks 0 -> 1900
			d = 1.0D - time / 1900.0D;
		} else if (time > 1900.0D && time <= 13067.0D) {
			// Day ticks 1900 -> 13067
			d = 0.0D;
		} else if (time > 13067.0D && time <= 15250.0D) {
			// Evening ticks 13067 -> 15250
			d = (time - 13067.0D) / 2183.0D;
		} else if (time > 15250.0D && time <= 24000.0D) {
			// Night ticks 15250 -> 24000
			d = 1.0D;
		}

		density *= 1.0D + densityNight * d;
		density *= 1.0D + densityBlocksUnderWater * blocksUnderwater;

		density = Math.max(density, 0.0001D);
		GL11.glFogf(GL11.GL_FOG_DENSITY, (float) density);
	}

}
