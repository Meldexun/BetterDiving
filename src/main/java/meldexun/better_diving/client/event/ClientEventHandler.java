package meldexun.better_diving.client.event;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.oxygen.item.CapabilityOxygenItemProvider;
import meldexun.better_diving.client.util.BetterDivingGuiHelper;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.oxygenprovider.DivingGearManager;
import meldexun.better_diving.oxygenprovider.DivingMaskProviderItem;
import meldexun.better_diving.oxygenprovider.MiningspeedProviderItem;
import meldexun.better_diving.oxygenprovider.SwimspeedProviderItem;
import meldexun.better_diving.util.OxygenPlayerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {

	@SubscribeEvent
	public static void onRenderGameOverlayEventPre(RenderGameOverlayEvent.Pre event) {
		if (!BetterDivingConfig.CLIENT_CONFIG.oxygenGuiEnabled.get()) {
			return;
		}
		if (event.getType() == ElementType.AIR) {
			event.setCanceled(true);

			Minecraft mc = Minecraft.getInstance();
			if (OxygenPlayerHelper.getOxygenRespectEquipment(mc.player) < OxygenPlayerHelper.getOxygenCapacityRespectEquipment(mc.player)) {
				renderOxygenOverlay(event.getMatrixStack());
			}
		}
	}

	private static final ResourceLocation BACKGROUND = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_background.png");
	private static final ResourceLocation BAR = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_bar.png");
	private static final ResourceLocation BUBBLES = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_bubbles.png");
	private static final ResourceLocation FRAME = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_frame.png");

	private static int tick = 0;
	private static float partialTicks = 0.0F;
	private static float prevPartialTicks = 0.0F;

	@SuppressWarnings("deprecation")
	private static void renderOxygenOverlay(MatrixStack matrixStack) {
		updatePartialTicks();
		Minecraft mc = Minecraft.getInstance();
		TextureManager textureManager = mc.getTextureManager();
		FontRenderer fontRenderer = mc.fontRenderer;

		int oxygen = (int) Math.round(OxygenPlayerHelper.getOxygenRespectEquipment(mc.player) / 20.0D / 3.0D) * 3;
		double percent = (int) (OxygenPlayerHelper.getOxygenRespectEquipmentInPercent(mc.player) * 80.0D) / 80.0D;
		int x = BetterDivingGuiHelper.getAnchorX(102, BetterDivingConfig.CLIENT_CONFIG.oxygenGuiAnchor.get(), BetterDivingConfig.CLIENT_CONFIG.oxygenGuiOffsetX.get());
		int y = BetterDivingGuiHelper.getAnchorY(21, BetterDivingConfig.CLIENT_CONFIG.oxygenGuiAnchor.get(), BetterDivingConfig.CLIENT_CONFIG.oxygenGuiOffsetY.get());
		double offset;

		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.00390625F);

		textureManager.bindTexture(BACKGROUND);
		BetterDivingGuiHelper.drawTexture(x, y, 0.0D, 0.0D, 102, 21, 1.0D, 1.0D);

		textureManager.bindTexture(BAR);
		BetterDivingGuiHelper.drawTexture(x + 1.0D + 80.0D * (1.0D - percent), y + 7.0D, 1.0D - percent, mc.world.getGameTime() * 9.0D / 576.0D, 80.0D * percent, 7.0D, percent, 9.0D / 576.0D);

		textureManager.bindTexture(BUBBLES);
		offset = 2.0D * (tick + partialTicks) % 128 / 128.0D;
		drawBubbles(x + 1.0D, y + 7.0D, 0.0D, offset, percent);
		offset = 2.5D * (tick + partialTicks) % 128 / 128.0D;
		drawBubbles(x + 1.0D, y + 7.0D, 20.0D, offset + 0.45D, percent);
		offset = 1.5D * (tick + partialTicks) % 128 / 128.0D;
		drawBubbles(x + 1.0D, y + 7.0D, 35.0D, offset + 0.12D, percent);
		offset = 2.0D * (tick + partialTicks) % 128 / 128.0D;
		drawBubbles(x + 1.0D, y + 7.0D, 55.0D, offset + 0.68D, percent);

		textureManager.bindTexture(FRAME);
		BetterDivingGuiHelper.drawTexture(x, y, 0.0D, 0.0D, 102, 21, 1.0D, 1.0D);

		String s1 = Integer.toString(oxygen);
		fontRenderer.drawStringWithShadow(matrixStack, s1, x + 91 - fontRenderer.getStringWidth(s1) / 2, y + 11, 0xFFFFFF);
		String s2 = "O\u2082";
		fontRenderer.drawStringWithShadow(matrixStack, s2, x + 91 - fontRenderer.getStringWidth(s2) / 2, y + 2, 0xFFFFFF);

		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.disableBlend();
	}

	private static void updatePartialTicks() {
		Minecraft mc = Minecraft.getInstance();
		float f = mc.getRenderPartialTicks() - prevPartialTicks;
		if (f <= 0.0F) {
			f++;
		}
		partialTicks += f;
		tick += partialTicks / 1.0F;
		partialTicks = partialTicks % 1.0F;
		prevPartialTicks = mc.getRenderPartialTicks();
	}

	private static void drawBubbles(double x, double y, double xOffset, double vOffset, double percent) {
		double width = 128.0D / 6.0D;
		double height = 128.0D / 16.0D;
		xOffset = MathHelper.clamp(xOffset, 0.0D, 80.0D - width);
		percent = MathHelper.clamp(percent * 80.0D / width - (80.0D - width - xOffset) / width, 0.0D, 1.0D);
		BetterDivingGuiHelper.drawTexture(x + xOffset + width * (1.0D - percent), y, 1.0D - percent, vOffset, width * percent, height, percent, 0.375D);
	}

	@SubscribeEvent
	public static void onFOVModifierEvent(EntityViewRenderEvent.FOVModifier event) {
		if (!event.getInfo().getFluidState().isEmpty()) {
			event.setFOV(event.getFOV() * 7.0D / 6.0D);
		}
	}

	@SubscribeEvent
	public static void onEntitySizeEvent(EntityEvent.Size event) {
		if (event.getEntity() instanceof PlayerEntity && event.getEntity().getRidingEntity() instanceof EntitySeamoth) {
			event.setNewEyeHeight(event.getNewEyeHeight() * 1.164375F / 1.62F);
		}
	}

	@SubscribeEvent
	public static void onItemTooltipEvent(ItemTooltipEvent event) {
		List<ITextComponent> tooltip = event.getToolTip();
		ItemStack stack = event.getItemStack();

		DivingMaskProviderItem divingMaskProvider = DivingGearManager.getDivingMaskProviderItem(stack);
		if (divingMaskProvider != null) {
			tooltip.add(1, new StringTextComponent(TextFormatting.GRAY + String.format("Max Diving Depth %d", divingMaskProvider.maxDivingDepth)));
		}

		stack.getCapability(CapabilityOxygenItemProvider.OXYGEN).ifPresent(c -> {
			tooltip.add(1, new StringTextComponent(TextFormatting.GRAY + String.format("Oxygen %d/%d", c.getOxygen(), c.getOxygenCapacity())));
		});

		MiningspeedProviderItem miningSpeedProvider = DivingGearManager.getMiningspeedProviderItem(stack);
		if (miningSpeedProvider != null) {
			tooltip.add(1, new StringTextComponent(TextFormatting.GRAY + String.format("Mining Speed %.2f%%", miningSpeedProvider.miningspeed * 100)));
		}

		SwimspeedProviderItem swimspeedProvider = DivingGearManager.getSwimspeedProviderItem(stack);
		if (swimspeedProvider != null) {
			tooltip.add(1, new StringTextComponent(TextFormatting.GRAY + String.format("Swim Speed %.2f%%", swimspeedProvider.swimspeed * 100)));
		}
	}

}
