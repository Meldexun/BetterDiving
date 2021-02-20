package meldexun.better_diving.client.gui;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.util.BetterDivingGuiHelper;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.util.OxygenPlayerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiOxygen {

	private static final ResourceLocation BACKGROUND = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_background.png");
	private static final ResourceLocation BAR = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_bar.png");
	private static final ResourceLocation BUBBLES = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_bubbles.png");
	private static final ResourceLocation FRAME = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_frame.png");

	private static int tick = 0;
	private static float partialTicks = 0.0F;
	private static float prevPartialTicks = 0.0F;

	@SuppressWarnings("deprecation")
	public static void render(MatrixStack matrixStack) {
		updatePartialTicks();
		Minecraft mc = Minecraft.getInstance();
		TextureManager textureManager = mc.getTextureManager();
		FontRenderer fontRenderer = mc.fontRenderer;

		int oxygen = (int) Math.round(OxygenPlayerHelper.getOxygenRespectEquipment(mc.player) / 20.0D / 3.0D) * 3;
		double percent = (int) (OxygenPlayerHelper.getOxygenRespectEquipmentInPercent(mc.player) * 80.0D) / 80.0D;

		int width = 105;
		int height = 25;
		int x = BetterDivingGuiHelper.getAnchorX(width, BetterDivingConfig.CLIENT_CONFIG.oxygenGuiAnchor.get(), BetterDivingConfig.CLIENT_CONFIG.oxygenGuiOffsetX.get());
		int y = BetterDivingGuiHelper.getAnchorY(height, BetterDivingConfig.CLIENT_CONFIG.oxygenGuiAnchor.get(), BetterDivingConfig.CLIENT_CONFIG.oxygenGuiOffsetY.get());

		boolean blend = GL11.glGetBoolean(GL11.GL_BLEND);
		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.00390625F);

		textureManager.bindTexture(BACKGROUND);
		BetterDivingGuiHelper.drawTexture(x, y, 0.0D, 0.0D, width, height, width / 128.0D, height / 32.0D);

		textureManager.bindTexture(BAR);
		BetterDivingGuiHelper.drawTexture(x + 1.0D + 80.0D * (1.0D - percent), y + 9.0D, 1.0D - percent, mc.world.getGameTime() * 9.0D / 576.0D, 80.0D * percent, 7.0D, percent, 9.0D / 576.0D);

		textureManager.bindTexture(BUBBLES);
		double bubbleOffset = 2.0D * (tick + partialTicks) % 128 / 128.0D;
		drawBubbles(x + 1.0D, y + 9.0D, 0.0D, bubbleOffset, percent);
		bubbleOffset = 2.5D * (tick + partialTicks) % 128 / 128.0D;
		drawBubbles(x + 1.0D, y + 9.0D, 20.0D, bubbleOffset + 0.45D, percent);
		bubbleOffset = 1.5D * (tick + partialTicks) % 128 / 128.0D;
		drawBubbles(x + 1.0D, y + 9.0D, 35.0D, bubbleOffset + 0.12D, percent);
		bubbleOffset = 2.0D * (tick + partialTicks) % 128 / 128.0D;
		drawBubbles(x + 1.0D, y + 9.0D, 55.0D, bubbleOffset + 0.68D, percent);

		textureManager.bindTexture(FRAME);
		BetterDivingGuiHelper.drawTexture(x, y, 0.0D, 0.0D, width, height, width / 128.0D, height / 32.0D);

		String s1 = Integer.toString(oxygen);
		fontRenderer.drawString(matrixStack, s1, x + 93 - fontRenderer.getStringWidth(s1) / 2, y + 14, 0xFFFFFF);
		String s2 = "O\u2082";
		fontRenderer.drawString(matrixStack, s2, x + 93 - fontRenderer.getStringWidth(s2) / 2, y + 4, 0xFFFFFF);

		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		if (!blend) {
			GL11.glDisable(GL11.GL_BLEND);
		}
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

}
