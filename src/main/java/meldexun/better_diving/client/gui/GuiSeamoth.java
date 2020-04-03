package meldexun.better_diving.client.gui;

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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSeamoth extends Gui {

	private static final ResourceLocation GUI_SEAMOTH_1080 = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/gui_seamoth_1080.png");
	private static final ResourceLocation GUI_SEAMOTH_1440 = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/gui_seamoth_1440.png");
	private static final ResourceLocation GUI_SEAMOTH_2160 = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/gui_seamoth_2160.png");

	public void render() {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaled = new ScaledResolution(mc);
		FontRenderer fontRenderer = mc.fontRenderer;

		EntitySeamoth seamoth = ((EntitySeamoth) mc.player.getRidingEntity());

		int width = 90;
		int height = 45;
		int offsetX = GuiHelper.getAnchorX(width, BetterDivingConfig.CLIENT_SETTINGS.guiSeamothConfig);
		int offsetY = GuiHelper.getAnchorY(height, BetterDivingConfig.CLIENT_SETTINGS.guiSeamothConfig);

		mc.getTextureManager().bindTexture(GuiHelper.getTexture(GUI_SEAMOTH_1080, GUI_SEAMOTH_1440, GUI_SEAMOTH_2160));
		drawScaledCustomSizeModalRect(offsetX, offsetY, 0.0F, 0.0F, 1, 1, width, height, 1.0F, 1.0F);

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

	}

}
