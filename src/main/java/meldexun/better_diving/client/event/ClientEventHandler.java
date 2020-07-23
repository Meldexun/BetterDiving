package meldexun.better_diving.client.event;

import org.lwjgl.opengl.GL11;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.diving.ICapabilityDivingAttributes;
import meldexun.better_diving.client.renderer.entity.RenderPlayerCustom;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.init.ModItems;
import meldexun.better_diving.integration.ArtemisLib;
import meldexun.better_diving.item.AbstractItemDivingGear;
import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.EntityHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Side.CLIENT)
public class ClientEventHandler {

	private ClientEventHandler() {

	}

	@SubscribeEvent
	public static void hideWaterOverlay(RenderBlockOverlayEvent event) {
		EntityPlayer player = event.getPlayer();

		if (BetterDivingConfig.getInstance().modules.visionUnderWater && BetterDivingConfig.getInstance().client.hideWaterOverlay && event.getBlockForOverlay().getBlock() == Blocks.WATER
				&& (player.inventory.armorInventory.get(3).getItem() instanceof AbstractItemDivingGear || player.getRidingEntity() instanceof EntitySeamoth)) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void setupFog(EntityViewRenderEvent.FogDensity event) {
		Entity entity = event.getEntity();
		if (BetterDivingConfig.getInstance().modules.visionUnderWater && event.getState().getMaterial() == Material.WATER && event.getEntity() instanceof EntityPlayer && !((EntityPlayer) event.getEntity()).isPotionActive(MobEffects.BLINDNESS)) {
			long time = (entity.world.getWorldTime() + 1450L) % 24000L;
			int blocksUnderwater = EntityHelper.blocksUnderWater(event.getEntity());

			if (BetterDivingConfig.getInstance().client.fogSettings.fogColorEnabled) {
				ClientEventHandler.setupFogColor(entity.world, time, blocksUnderwater);
			}
			if (BetterDivingConfig.getInstance().client.fogSettings.fogDensityEnabled) {
				ClientEventHandler.setupFogDensity(entity.world, time, blocksUnderwater);
			}
		}
	}

	public static void setupFogColor(World world, double time, double blocksUnderwater) {
		double[] color = BetterDivingConfig.getInstance().client.fogSettings.fogColor;
		double[] colorNight = BetterDivingConfig.getInstance().client.fogSettings.fogColorNight;
		double[] colorBlocksUnderWater = BetterDivingConfig.getInstance().client.fogSettings.fogColorBlocksUnderWater;
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

		red = MathHelper.clamp(red, 0.0D, 1.0D);
		green = MathHelper.clamp(green, 0.0D, 1.0D);
		blue = MathHelper.clamp(blue, 0.0D, 1.0D);
		GL11.glFog(GL11.GL_FOG_COLOR, RenderHelper.setColorBuffer((float) red, (float) green, (float) blue, 1.0F));
	}

	public static void setupFogDensity(World world, double time, double blocksUnderwater) {
		double density = BetterDivingConfig.getInstance().client.fogSettings.fogDensity;
		double densityNight = BetterDivingConfig.getInstance().client.fogSettings.fogDensityNight;
		double densityBlocksUnderWater = BetterDivingConfig.getInstance().client.fogSettings.fogDensityBlocksUnderWater;
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

	private static float prevRotationPitch;
	private static float rotationPitch;
	private static float prevRenderYawOffset;
	private static float renderYawOffset;
	private static boolean movementInputSneak;

	private static RenderPlayer playerRenderer;
	private static RenderPlayer playerRendererSlim;

	public static void registerRenderers() {
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		ClientEventHandler.playerRenderer = new RenderPlayerCustom(renderManager, false);
		ClientEventHandler.playerRendererSlim = new RenderPlayerCustom(renderManager, true);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onRenderPlayerEventPre(RenderPlayerEvent.Pre event) {
		if (!BetterDivingConfig.getInstance().modules.visionUnderWater || !BetterDivingConfig.getInstance().client.customPlayerModel) {
			return;
		}
		double x = event.getX();
		double y = event.getY();
		double z = event.getZ();
		float partialTicks = event.getPartialRenderTick();
		EntityPlayer player = event.getEntityPlayer();
		ICapabilityDivingAttributes idiving = player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);

		if (player.getRidingEntity() instanceof EntitySeamoth) {
			double sinYaw = Math.sin(Math.toRadians(player.rotationYaw));
			double cosYaw = Math.cos(Math.toRadians(player.rotationYaw));
			GL11.glPushMatrix();
			if (ArtemisLib.loaded) {
				GL11.glTranslated(x, y + 0.8D, z);
				GL11.glRotated(player.rotationPitch, cosYaw, 0.0D, sinYaw);
				GL11.glTranslated(-x, -y - 0.8D - 0.4D * ArtemisLib.getHeightScale(player), -z);
				ArtemisLib.rescale(player);
			} else {
				GL11.glTranslated(x, y + 0.8D, z);
				GL11.glRotated(player.rotationPitch, cosYaw, 0.0D, sinYaw);
				GL11.glTranslated(-x, -y - 1.2D, -z);
			}
			ClientEventHandler.prevRotationPitch = player.prevRotationPitch;
			ClientEventHandler.rotationPitch = player.rotationPitch;
			ClientEventHandler.prevRenderYawOffset = player.prevRenderYawOffset;
			ClientEventHandler.renderYawOffset = player.renderYawOffset;
			player.setSprinting(false);
			player.setSneaking(false);
			player.prevRotationPitch = 0.0F;
			player.rotationPitch = 0.0F;
			player.prevRenderYawOffset = MathHelper.clamp(player.prevRenderYawOffset, player.prevRotationYaw - 10.0F, player.prevRotationYaw + 10.0F);
			player.renderYawOffset = MathHelper.clamp(player.renderYawOffset, player.rotationYaw - 10.0F, player.rotationYaw + 10.0F);
			if (player instanceof EntityPlayerSP) {
				ClientEventHandler.movementInputSneak = ((EntityPlayerSP) player).movementInput.sneak;
				((EntityPlayerSP) player).movementInput.sneak = false;
			}
		} else if (idiving.getDivingTick() > 0.0F || player.getHeldItemMainhand().getItem() == ModItems.SEAGLIDE) {
			double divingTick = MathHelper.clampedLerp(idiving.getPrevDivingTick(), idiving.getDivingTick(), partialTicks);
			double divingTickHorizontal = MathHelper.clampedLerp(idiving.getPrevDivingTickHorizontal(), idiving.getDivingTickHorizontal(), partialTicks);
			double divingTickVertical = MathHelper.clampedLerp(idiving.getPrevDivingTickVertical(), idiving.getDivingTickVertical(), partialTicks);
			float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
			if (divingTickVertical > 0.0D) {
				pitch += (-90.0F - pitch) * 0.5F * (float) divingTickVertical;
			} else if (divingTickVertical < 0.0D) {
				pitch += (90.0F - pitch) * 0.5F * (float) -divingTickVertical;
			}
			pitch += idiving.isDiving() ? 90.0F : 60.0F;
			float yaw = (float) divingTickHorizontal * 45.0F;
			double sinYaw = Math.sin(Math.toRadians(player.rotationYaw));
			double cosYaw = Math.cos(Math.toRadians(player.rotationYaw));
			GL11.glPushMatrix();
			if (ArtemisLib.loaded) {
				GL11.glTranslated(x, y + divingTick * 0.3D * ArtemisLib.getHeightScale(player), z);
				GL11.glRotated(divingTick * pitch, cosYaw, 0.0D, sinYaw);
				GL11.glRotated(divingTick * yaw, sinYaw, 0.0D, -cosYaw);
				GL11.glTranslated(-x, -y - divingTick * 0.9D * ArtemisLib.getHeightScale(player), -z);
				ArtemisLib.rescale(player);
			} else {
				GL11.glTranslated(x, y + divingTick * 0.3D, z);
				GL11.glRotated(divingTick * pitch, cosYaw, 0.0D, sinYaw);
				GL11.glRotated(divingTick * yaw, sinYaw, 0.0D, -cosYaw);
				GL11.glTranslated(-x, -y - divingTick * 0.9D, -z);
			}
			ClientEventHandler.prevRotationPitch = player.prevRotationPitch;
			ClientEventHandler.rotationPitch = player.rotationPitch;
			ClientEventHandler.prevRenderYawOffset = player.prevRenderYawOffset;
			ClientEventHandler.renderYawOffset = player.renderYawOffset;
			player.setSprinting(false);
			player.setSneaking(false);
			player.prevRotationPitch += (float) divingTick * (-60.0F - player.prevRotationPitch);
			player.rotationPitch += (float) divingTick * (-60.0F - player.rotationPitch);
			float f = 75.0F - 60.0F * (float) divingTick;
			player.prevRenderYawOffset = MathHelper.clamp(player.prevRenderYawOffset, player.prevRotationYaw - f, player.prevRotationYaw + f);
			player.renderYawOffset = MathHelper.clamp(player.renderYawOffset, player.rotationYaw - f, player.rotationYaw + f);
			if (player instanceof EntityPlayerSP) {
				ClientEventHandler.movementInputSneak = ((EntityPlayerSP) player).movementInput.sneak;
				((EntityPlayerSP) player).movementInput.sneak = false;
			}
		}
	}

	private static Render getPlayerRender(AbstractClientPlayer player) {
		return player.getSkinType().equals("slim") ? ClientEventHandler.playerRendererSlim : ClientEventHandler.playerRenderer;
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onRenderPlayerEventPost(RenderPlayerEvent.Post event) {
		if (!BetterDivingConfig.getInstance().modules.visionUnderWater || !BetterDivingConfig.getInstance().client.customPlayerModel) {
			return;
		}
		EntityPlayer player = event.getEntityPlayer();
		ICapabilityDivingAttributes idiving = player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);

		if (player.getRidingEntity() instanceof EntitySeamoth || idiving.getDivingTick() > 0.0F || player.getHeldItemMainhand().getItem() == ModItems.SEAGLIDE) {
			GL11.glPopMatrix();
			player.prevRotationPitch = ClientEventHandler.prevRotationPitch;
			player.rotationPitch = ClientEventHandler.rotationPitch;
			player.prevRenderYawOffset = ClientEventHandler.prevRenderYawOffset;
			player.renderYawOffset = ClientEventHandler.renderYawOffset;
			if (player instanceof EntityPlayerSP) {
				((EntityPlayerSP) player).movementInput.sneak = ClientEventHandler.movementInputSneak;
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerTickEvent(PlayerTickEvent event) {
		if (!BetterDivingConfig.getInstance().modules.visionUnderWater || !BetterDivingConfig.getInstance().client.customPlayerModel) {
			return;
		}
		if (event.phase == Phase.END && event.player.isInWater() && !event.player.isRiding()) {
			double d1 = event.player.posX - event.player.prevPosX;
			double d2 = event.player.posZ - event.player.prevPosZ;
			double d3 = event.player.posY - event.player.prevPosY;
			float f2 = MathHelper.sqrt(d1 * d1 + d2 * d2 + d3 * d3) * 4.0F;

			if (f2 > 1.0F) {
				f2 = 1.0F;
			}

			event.player.limbSwingAmount = event.player.prevLimbSwingAmount;
			event.player.limbSwing -= event.player.prevLimbSwingAmount;

			event.player.limbSwingAmount += (f2 - event.player.limbSwingAmount) * 0.4F;
			event.player.limbSwing += event.player.limbSwingAmount;
		}
	}

}
