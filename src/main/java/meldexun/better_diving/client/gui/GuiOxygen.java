package meldexun.better_diving.client.gui;

import org.lwjgl.opengl.GL11;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.diving.ICapabilityDivingAttributes;
import meldexun.better_diving.client.util.GuiHelper;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiOxygen extends Gui {

	private static final ResourceLocation GUI_OXYGEN_BACKGROUND_1080 = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/gui_oxygen_background_1080.png");
	private static final ResourceLocation GUI_OXYGEN_BACKGROUND_1440 = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/gui_oxygen_background_1440.png");
	private static final ResourceLocation GUI_OXYGEN_BACKGROUND_2160 = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/gui_oxygen_background_2160.png");
	private static final ResourceLocation GUI_OXYGEN_BAR = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/gui_oxygen_bar.png");
	private static final ResourceLocation GUI_OXYGEN_BUBBLES = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/gui_oxygen_bubbles.png");
	private static final ResourceLocation GUI_OXYGEN_LINE = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/gui_oxygen_line.png");

	public void render() {
		Minecraft mc = Minecraft.getMinecraft();
		TextureManager texManager = mc.getTextureManager();
		ScaledResolution scaled = new ScaledResolution(mc);
		FontRenderer fontRenderer = mc.fontRenderer;

		ICapabilityDivingAttributes idiving = mc.player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);

		int size = 54;
		int x = GuiHelper.getAnchorX(size, BetterDivingConfig.CLIENT_SETTINGS.guiOxygenConfig);
		int y = GuiHelper.getAnchorY(size, BetterDivingConfig.CLIENT_SETTINGS.guiOxygenConfig);

		texManager.bindTexture(GuiHelper.getTexture(GUI_OXYGEN_BACKGROUND_1080, GUI_OXYGEN_BACKGROUND_1440, GUI_OXYGEN_BACKGROUND_2160));
		drawScaledCustomSizeModalRect(x, y, 0.0F, 0.0F, 1, 1, size, size, 1.0F, 1.0F);

		double d = (double) idiving.getPrevOxygen() + (double) (idiving.getOxygenFromPlayer(mc.player) - idiving.getPrevOxygen()) * (double) mc.getRenderPartialTicks();
		double percent = MathHelper.clamp(d / (double) idiving.getOxygenCapacityFromPlayer(mc.player), 0.0D, 1.0D);

		double xm = (double) x + (double) size / 2.0D;
		double ym = (double) y + (double) size / 2.0D;
		double sinP = Math.sin(percent * GuiHelper.TWO_PI);
		double cosP = Math.cos(percent * GuiHelper.TWO_PI);
		double innerRadius = 14.0D;
		double outerRadius = 19.0D;
		double middleRadius = (innerRadius + outerRadius) / 2.0D;
		double smallRadius = outerRadius - middleRadius;
		double texInnerRadius = innerRadius / size;
		double texOuterRadius = outerRadius / size;
		double texMiddleRadius = (texInnerRadius + texOuterRadius) / 2.0D;
		double texSmallRadius = texOuterRadius - texMiddleRadius;

		texManager.bindTexture(GUI_OXYGEN_LINE);
		GL11.glLineWidth(1.6F);
		drawSmoothCircle(xm, ym - middleRadius, smallRadius, 90, 0.5D, 0.5D);
		drawSmoothCircle(xm + sinP * middleRadius, ym - cosP * middleRadius, smallRadius, 90, 0.5D, percent);

		texManager.bindTexture(GUI_OXYGEN_BAR);
		drawTexturedCircle(xm, ym - middleRadius, smallRadius, 0.5D, 0.5D - texMiddleRadius, texSmallRadius, 90, 0.5D, -0.5D);
		drawTexturedCircle(xm + sinP * middleRadius, ym - cosP * middleRadius, smallRadius, 0.5D + sinP * texMiddleRadius, 0.5D - cosP * texMiddleRadius, texSmallRadius, 90, 0.5D, -percent);
		drawTexturedRing(xm, ym, innerRadius, outerRadius, 0.5D, 0.5D, innerRadius / size, outerRadius / size, 360, percent, 0.0D);

		texManager.bindTexture(GUI_OXYGEN_LINE);
		GL11.glLineWidth(1.0F);
		drawSmoothCircle(xm, ym, innerRadius, 360, percent, 0.0D);
		drawSmoothCircle(xm, ym, outerRadius, 360, percent, 0.0D);

		GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D - Math.pow(percent, 4));
		texManager.bindTexture(GUI_OXYGEN_BUBBLES);
		double offset = ((4.0D * (double) (mc.world.getTotalWorldTime() + mc.getRenderPartialTicks())) % 128.0D) / 128.0D;
		double d1 = innerRadius * 6.0D / 128.0D;
		double d2 = outerRadius * 6.0D / 128.0D;
		double d3 = (d2 + d1) / 2.0D;
		double d4 = d2 - d3;
		drawTexturedCircle(xm, ym - middleRadius, smallRadius, d2, d2 - d3 + offset, d4, 90, 0.5D, -0.5D);
		drawTexturedCircle(xm + sinP * middleRadius, ym - cosP * middleRadius, smallRadius, d2 + sinP * d3, d2 - cosP * d3 + offset, d4, 90, 0.5D, -percent);
		drawTexturedRing(xm, ym, innerRadius, outerRadius, d2, d2 + offset, d1, d2, 360, percent, 0.0D);
		GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);

		int air = (int) Math.round(d / 20.0D / 3.0D) * 3;
		this.drawCenteredString(fontRenderer, Integer.toString(air), x + 27, y + 27, Integer.parseInt("FFFFFF", 16));
		this.drawCenteredString(fontRenderer, "O\u2082", x + 27, y + 17, Integer.parseInt("FFFFFF", 16));

	}

	public static void drawTexturedRing(double x, double y, double innerRadius, double outerRadius, double u, double v, double texInnerRadius, double texOuterRadius, int sides, double percent, double startAngle) {
		double rad = 0.0D;
		double sin = 0.0D;
		double cos = -1.0D;

		GL11.glBegin(GL11.GL_QUAD_STRIP);

		for (int i = 0; i < percent * (double) sides; i++) {
			rad = GuiHelper.TWO_PI * ((double) i / (double) sides + startAngle);
			sin = Math.sin(rad);
			cos = -Math.cos(rad);

			GL11.glTexCoord2d(u + sin * texOuterRadius, v + cos * texOuterRadius);
			GL11.glVertex2d(x + sin * outerRadius, y + cos * outerRadius);

			GL11.glTexCoord2d(u + sin * texInnerRadius, v + cos * texInnerRadius);
			GL11.glVertex2d(x + sin * innerRadius, y + cos * innerRadius);
		}

		rad = GuiHelper.TWO_PI * (percent + startAngle);
		sin = Math.sin(rad);
		cos = -Math.cos(rad);

		GL11.glTexCoord2d(u + sin * texOuterRadius, v + cos * texOuterRadius);
		GL11.glVertex2d(x + sin * outerRadius, y + cos * outerRadius);

		GL11.glTexCoord2d(u + sin * texInnerRadius, v + cos * texInnerRadius);
		GL11.glVertex2d(x + sin * innerRadius, y + cos * innerRadius);

		GL11.glEnd();
	}

	public static void drawTexturedCircle(double x, double y, double radius, double u, double v, double texRadius, int sides, double percent, double startAngle) {
		double rad = 0.0D;
		double sin = 0.0D;
		double cos = 1.0D;

		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glTexCoord2d(u, v);
		GL11.glVertex2d(x, y);

		for (int i = 0; i < percent * (double) sides; i++) {
			rad = GuiHelper.TWO_PI * ((double) i / (double) sides + startAngle);
			sin = Math.sin(rad);
			cos = Math.cos(rad);

			GL11.glTexCoord2d(u + sin * texRadius, v + cos * texRadius);
			GL11.glVertex2d(x + sin * radius, y + cos * radius);
		}

		rad = GuiHelper.TWO_PI * (percent + startAngle);
		sin = Math.sin(rad);
		cos = Math.cos(rad);

		GL11.glTexCoord2d(u + sin * texRadius, v + cos * texRadius);
		GL11.glVertex2d(x + sin * radius, y + cos * radius);

		GL11.glEnd();
	}

	public static void drawSmoothCircle(double x, double y, double radius, int sides, double percent, double startAngle) {
		boolean blend = GL11.glGetBoolean(GL11.GL_BLEND);
		boolean smooth = GL11.glGetBoolean(GL11.GL_LINE_SMOOTH);

		double rad = 0.0D;
		double sin = 0.0D;
		double cos = -1.0D;

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glBegin(GL11.GL_LINE_STRIP);

		for (int i = 0; i < percent * (double) sides; i++) {
			rad = GuiHelper.TWO_PI * ((double) i / (double) sides + startAngle);
			sin = Math.sin(rad);
			cos = -Math.cos(rad);

			GL11.glVertex2d(x + sin * radius, y + cos * radius);
		}

		rad = GuiHelper.TWO_PI * (percent + startAngle);
		sin = Math.sin(rad);
		cos = -Math.cos(rad);

		GL11.glVertex2d(x + sin * radius, y + cos * radius);

		GL11.glEnd();
		if (!smooth) {
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
		}
		if (!blend) {
			GL11.glDisable(GL11.GL_BLEND);
		}
		GL11.glPopMatrix();
	}

}
