package meldexun.better_diving.client.event;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.util.BetterDivingGuiHelper;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiSeamoth {

	public static void render(MatrixStack matrixStack) {
		Minecraft mc = Minecraft.getInstance();
		TextureManager textureManager = mc.getTextureManager();
		FontRenderer fontRenderer = mc.fontRenderer;
		EntitySeamoth seamoth = ((EntitySeamoth) mc.player.getRidingEntity());

		boolean blend = GL11.glGetBoolean(GL11.GL_BLEND);
		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.00390625F);

		GL11.glPushMatrix();

		int width = 86;
		int height = 36;
		int offsetX = BetterDivingGuiHelper.getAnchorX(width, BetterDivingConfig.CLIENT_CONFIG.seamothGuiAnchor.get(), BetterDivingConfig.CLIENT_CONFIG.seamothGuiOffsetX.get());
		int offsetY = BetterDivingGuiHelper.getAnchorY(height, BetterDivingConfig.CLIENT_CONFIG.seamothGuiAnchor.get(), BetterDivingConfig.CLIENT_CONFIG.seamothGuiOffsetY.get());

		textureManager.bindTexture(new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/seamoth_background.png"));
		BetterDivingGuiHelper.drawTexture(offsetX, offsetY, 0.0D, 0.0D, width, height, 1.0D, 1.0D);

		int energy = Math.round((float) seamoth.getEnergy() / (float) seamoth.getEnergyCapacity() * 100);
		int health = 100;
		int temperature = Math.round(20.0F * mc.player.world.getBiome(mc.player.getPosition()).getTemperature(mc.player.getPosition()));

		GlStateManager.pushMatrix();
		float scale = 4.0F / 6.0F;
		GlStateManager.scale(scale, scale, 1.0F);
		this.drawCenteredString(fontRenderer, Integer.toString(energy), Math.round((offsetX + 64) / scale), Math.round((offsetY + 7) / scale), Integer.parseInt("FFFFFF", 16));
		this.drawCenteredString(fontRenderer, Integer.toString(health), Math.round((offsetX + 26) / scale), Math.round((offsetY + 14) / scale), Integer.parseInt("FFFFFF", 16));
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		float scale2 = 3.0F / 6.0F;
		GlStateManager.scale(scale2, scale2, 1.0F);
		this.drawCenteredString(fontRenderer, Integer.toString(temperature), Math.round((offsetX + 61) / scale2), Math.round((offsetY + 26) / scale2), Integer.parseInt("FFFFFF", 16));
		this.drawCenteredString(fontRenderer, I18n.format("gui.temperature"), Math.round((offsetX + 67) / scale2), Math.round((offsetY + 26) / scale2), Integer.parseInt("F6DC47", 16));
		GlStateManager.popMatrix();

		GL11.glPopMatrix();

		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		if (!blend) {
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

}
