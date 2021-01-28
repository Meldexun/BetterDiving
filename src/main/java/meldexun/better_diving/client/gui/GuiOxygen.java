package meldexun.better_diving.client.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

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

	private static final ResourceLocation BACKGROUND = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_alternative_background.png");
	private static final ResourceLocation BAR = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_alternative_bar.png");
	private static final ResourceLocation FRAME = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_alternative_frame.png");

	private static final ResourceLocation OXYGEN_BAR = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_bar.png");
	private static final ResourceLocation OXYGEN_BUBBLES = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_bubbles.png");

	private float partialTicks = 0.0F;
	private float prevPartialTicks = 0.0F;

	public void render() {
		this.updatePartialTicks();
		Minecraft mc = Minecraft.getMinecraft();
		TextureManager textureManager = mc.getTextureManager();
		ScaledResolution scaled = new ScaledResolution(mc);
		FontRenderer fontRenderer = mc.fontRenderer;
		BetterDivingConfig config = BetterDivingConfig.getInstance();
		ICapabilityDivingAttributes idiving = mc.player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);

		if (config.client.guiOxygenAlternative) {
			int air = (int) Math.round(idiving.getOxygenFromPlayer() / 20.0D / 3.0D) * 3;
			double percent = idiving.getOxygenFromPlayerInPercent();
			int x = GuiHelper.getAnchorX(102, config.client.guiOxygenConfig);
			int y = GuiHelper.getAnchorY(21, config.client.guiOxygenConfig);
			double offset;

			boolean blend = GL11.glGetBoolean(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_BLEND);
			GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

			textureManager.bindTexture(GuiOxygen.BACKGROUND);
			GuiHelper.drawTexture(x, y, 0.0D, 0.0D, 102, 21, 1.0D, 1.0D);

			textureManager.bindTexture(GuiOxygen.BAR);
			GuiHelper.drawTexture(x + 1.0D + 80.0D * (1.0D - percent), y + 7.0D, (1.0D - percent), 0.0D, 80.0D * percent, 7.0D, percent, 1.0D);

			textureManager.bindTexture(GuiOxygen.OXYGEN_BUBBLES);
			offset = ((2.0D * this.partialTicks) % 128) / 128.0D;
			this.drawBubbles(x + 1.0D, y + 7.0D, 0.0D, offset, percent);
			offset = ((2.5D * this.partialTicks) % 128) / 128.0D;
			this.drawBubbles(x + 1.0D, y + 7.0D, 20.0D, offset + 0.45D, percent);
			offset = ((1.5D * this.partialTicks) % 128) / 128.0D;
			this.drawBubbles(x + 1.0D, y + 7.0D, 35.0D, offset + 0.12D, percent);
			offset = ((2.0D * this.partialTicks) % 128) / 128.0D;
			this.drawBubbles(x + 1.0D, y + 7.0D, 55.0D, offset + 0.68D, percent);

			textureManager.bindTexture(GuiOxygen.FRAME);
			GuiHelper.drawTexture(x, y, 0.0D, 0.0D, 102, 21, 1.0D, 1.0D);

			if (!blend) {
				GL11.glDisable(GL11.GL_BLEND);
			}

			this.drawCenteredString(fontRenderer, Integer.toString(air), x + 91, y + 11, Integer.parseInt("FFFFFF", 16));
			this.drawCenteredString(fontRenderer, "O\u2082", x + 91, y + 2, Integer.parseInt("FFFFFF", 16));
		} else {
			int size = 37;
			int x = GuiHelper.getAnchorX(size, config.client.guiOxygenConfig);
			int y = GuiHelper.getAnchorY(size, config.client.guiOxygenConfig);
			int i = MathHelper.clamp(scaled.getScaleFactor(), 1, 9);

			boolean blend = GL11.glGetBoolean(GL11.GL_BLEND);
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);

			GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
			textureManager.bindTexture(new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_background_" + i + ".png"));
			// GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			// GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GuiHelper.drawTexture(x, y, 0.0D, 0.0D, size, size, 1.0D, 1.0D);
			textureManager.bindTexture(new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_center_" + i + ".png"));
			// GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			// GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GuiHelper.drawTexture(x, y, 0.0D, 0.0D, size, size, 1.0D, 1.0D);
			textureManager.bindTexture(new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/oxygen/oxygen_foreground_" + i + ".png"));
			// GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			// GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GuiHelper.drawTexture(x, y, 0.0D, 0.0D, size, size, 1.0D, 1.0D);

			double percent = idiving.getOxygenFromPlayerInPercent();

			double xm = x + size / 2.0D;
			double ym = y + size / 2.0D;
			double sinP = Math.sin(percent * GuiHelper.TWO_PI);
			double cosP = Math.cos(percent * GuiHelper.TWO_PI);
			double innerRadius = 10.4D;
			double outerRadius = 14.2D;
			double middleRadius = (innerRadius + outerRadius) / 2.0D;
			double smallRadius = outerRadius - middleRadius;
			double texInnerRadius = innerRadius / size;
			double texOuterRadius = outerRadius / size;
			double texMiddleRadius = (texInnerRadius + texOuterRadius) / 2.0D;
			double texSmallRadius = texOuterRadius - texMiddleRadius;

			GL11.glDisable(GL11.GL_TEXTURE_2D);
			// GL11.glColor4d(0.32D, 0.85D, 0.78D, 1.0D);
			GL11.glColor4d(0.2D, 0.65D, 0.1D, 1.0D);
			GL11.glLineWidth(1.0F);
			GuiOxygen.drawSmoothCircle(xm, ym - middleRadius, smallRadius, 45, 0.5D, 0.5D);
			GuiOxygen.drawSmoothCircle(xm + sinP * middleRadius, ym - cosP * middleRadius, smallRadius, 45, 0.5D, percent);
			GL11.glEnable(GL11.GL_TEXTURE_2D);

			GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
			textureManager.bindTexture(GuiOxygen.OXYGEN_BAR);
			GuiOxygen.drawTexturedCircle(xm, ym - middleRadius, smallRadius, 0.5D, 0.5D - texMiddleRadius, texSmallRadius, 45, 0.5D, -0.5D);
			GuiOxygen.drawTexturedCircle(xm + sinP * middleRadius, ym - cosP * middleRadius, smallRadius, 0.5D + sinP * texMiddleRadius, 0.5D - cosP * texMiddleRadius, texSmallRadius, 45, 0.5D, -percent);
			GuiOxygen.drawTexturedRing(xm, ym, innerRadius, outerRadius, 0.5D, 0.5D, texInnerRadius, texOuterRadius, 180, percent, 0.0D);

			GL11.glDisable(GL11.GL_TEXTURE_2D);
			// GL11.glColor4d(0.32D, 0.85D, 0.78D, 1.0D);
			GL11.glColor4d(0.2D, 0.65D, 0.1D, 1.0D);
			GL11.glLineWidth(1.0F);
			GuiOxygen.drawSmoothCircle(xm, ym, innerRadius, 180, percent, 0.0D);
			GuiOxygen.drawSmoothCircle(xm, ym, outerRadius, 180, percent, 0.0D);
			GL11.glEnable(GL11.GL_TEXTURE_2D);

			double offset = ((4.0D * this.partialTicks) % 128.0D) / 128.0D;
			double d1 = innerRadius * 8.0D / 128.0D;
			double d2 = outerRadius * 8.0D / 128.0D;
			double d3 = (d2 + d1) / 2.0D;
			double d4 = d2 - d3;

			GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D - Math.pow(percent, 4));
			textureManager.bindTexture(GuiOxygen.OXYGEN_BUBBLES);
			GuiOxygen.drawTexturedCircle(xm, ym - middleRadius, smallRadius, d2, d2 - d3 + offset, d4, 45, 0.5D, -0.5D);
			GuiOxygen.drawTexturedCircle(xm + sinP * middleRadius, ym - cosP * middleRadius, smallRadius, d2 + sinP * d3, d2 - cosP * d3 + offset, d4, 45, 0.5D, -percent);
			GuiOxygen.drawTexturedRing(xm, ym, innerRadius, outerRadius, d2, d2 + offset, d1, d2, 180, percent, 0.0D);

			int air = (int) Math.round(idiving.getOxygenFromPlayer() / 20.0D / 3.0D) * 3;

			GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
			GL11.glPushMatrix();
			double scale = 0.75D;
			GL11.glScaled(scale, scale, scale);
			GL11.glTranslated(xm / scale, ym / scale, 0);
			this.drawCenteredString(fontRenderer, Integer.toString(air), 0, 0, Integer.parseInt("FFFFFF", 16));
			this.drawCenteredString(fontRenderer, "O\u2082", 0, -9, Integer.parseInt("FFFFFF", 16));
			GL11.glPopMatrix();

			if (!blend) {
				GL11.glDisable(GL11.GL_BLEND);
			}
			GL11.glPopMatrix();
		}
	}

	public void updatePartialTicks() {
		Minecraft mc = Minecraft.getMinecraft();
		float f = mc.getRenderPartialTicks() - this.prevPartialTicks;
		if (f <= 0.0F) {
			f++;
		}
		this.partialTicks += f;
		this.prevPartialTicks = mc.getRenderPartialTicks();
	}

	public void drawBubbles(double x, double y, double xOffset, double vOffset, double percent) {
		double width = 128.0D / 6.0D;
		double height = 128.0D / 16.0D;
		xOffset = MathHelper.clamp(xOffset, 0.0D, 80.0D - width);
		percent = MathHelper.clamp(percent * 80.0D / width - (80.0D - width - xOffset) / width, 0.0D, 1.0D);
		GuiHelper.drawTexture(x + xOffset + width * (1.0D - percent), y, 1.0D - percent, vOffset, width * percent, height, percent, 0.375D);
	}

	public static void drawTexturedRing(double x, double y, double innerRadius, double outerRadius, double u, double v, double texInnerRadius, double texOuterRadius, int sides, double percent, double startAngle) {
		double rad;
		double sin;
		double cos;

		GL11.glBegin(GL11.GL_QUAD_STRIP);

		for (int i = 0; i < percent * sides; i++) {
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
		double rad;
		double sin;
		double cos;

		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glTexCoord2d(u, v);
		GL11.glVertex2d(x, y);

		for (int i = 0; i < percent * sides; i++) {
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
		boolean lineSmooth = GL11.glGetBoolean(GL11.GL_LINE_SMOOTH);

		double rad;
		double sin;
		double cos;

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glBegin(GL11.GL_LINE_STRIP);

		for (int i = 0; i < percent * sides; i++) {
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
		if (!lineSmooth) {
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
		}
		if (!blend) {
			GL11.glDisable(GL11.GL_BLEND);
		}
		GL11.glPopMatrix();
	}

}
