package meldexun.better_diving.client.gui;

import org.lwjgl.opengl.GL11;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.util.GuiHelper;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSeamoth extends Gui {

	public void render() {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaled = new ScaledResolution(mc);
		FontRenderer fontRenderer = mc.fontRenderer;
		BetterDivingConfig config = BetterDivingConfig.getInstance();
		EntitySeamoth seamoth = ((EntitySeamoth) mc.player.getRidingEntity());

		boolean blend = GL11.glGetBoolean(GL11.GL_BLEND);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);

		int i = MathHelper.clamp(scaled.getScaleFactor(), 1, 9);
		int width = 86;
		int height = 36;
		int offsetX = GuiHelper.getAnchorX(width, config.client.guiSeamothConfig);
		int offsetY = GuiHelper.getAnchorY(height, config.client.guiSeamothConfig);

		mc.getTextureManager().bindTexture(new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/seamoth_background_" + i + ".png"));
		GuiHelper.drawTexture(offsetX, offsetY, 0.0D, 0.0D, width, height, 1.0D, 1.0D);

		int energy = Math.round((float) seamoth.getEnergy() / (float) seamoth.getEnergyCapacity() * 100);
		int health = 100;
		int temperature = Math.round(20.0F * mc.player.world.getBiome(mc.player.getPosition()).getTemperature(mc.player.getPosition()));

		GlStateManager.pushMatrix();
		float scale = 4.0F / 6.0F;
		GlStateManager.scale(scale, scale, 1.0F);
		this.drawCenteredString(fontRenderer, Integer.toString(energy), Math.round((float) (offsetX + 64) / scale), Math.round((float) (offsetY + 7) / scale), Integer.parseInt("FFFFFF", 16));
		this.drawCenteredString(fontRenderer, Integer.toString(health), Math.round((float) (offsetX + 26) / scale), Math.round((float) (offsetY + 14) / scale), Integer.parseInt("FFFFFF", 16));
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		float scale2 = 3.0F / 6.0F;
		GlStateManager.scale(scale2, scale2, 1.0F);
		this.drawCenteredString(fontRenderer, Integer.toString(temperature), Math.round((float) (offsetX + 61) / scale2), Math.round((float) (offsetY + 26) / scale2), Integer.parseInt("FFFFFF", 16));
		this.drawCenteredString(fontRenderer, I18n.format("gui.temperature"), Math.round((float) (offsetX + 67) / scale2), Math.round((float) (offsetY + 26) / scale2), Integer.parseInt("F6DC47", 16));
		GlStateManager.popMatrix();

		if (!blend) {
			GL11.glDisable(GL11.GL_BLEND);
		}
		GL11.glPopMatrix();
	}

}
