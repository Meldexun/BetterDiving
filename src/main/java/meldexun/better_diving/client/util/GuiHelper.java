package meldexun.better_diving.client.util;

import org.lwjgl.opengl.GL11;

import meldexun.better_diving.util.config.GuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHelper {

	private GuiHelper() {

	}

	public static final double TWO_PI = 2.0D * Math.PI;

	public static int getAnchorX(int width, GuiConfig config) {
		ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());

		if (config.anchor == 1 || config.anchor == 4) {
			// top mid or bot mid
			return config.offsetX + (scaled.getScaledWidth() - width) / 2;
		} else if (config.anchor == 2 || config.anchor == 3) {
			// top right or bot right
			return config.offsetX + scaled.getScaledWidth() - width;
		}

		return config.offsetX;
	}

	public static int getAnchorY(int height, GuiConfig config) {
		ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());

		if (config.anchor == 3 || config.anchor == 4 || config.anchor == 5) {
			// bot right or bot mid or bot left
			return config.offsetY + scaled.getScaledHeight() - height;
		}

		return config.offsetY;
	}

	public static void drawTexture(double x, double y, double u, double v, double width, double height, double texWidth, double texHeight) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(u, v + texHeight);
		GL11.glVertex2d(x, y + height);

		GL11.glTexCoord2d(u + texWidth, v + texHeight);
		GL11.glVertex2d(x + width, y + height);

		GL11.glTexCoord2d(u + texWidth, v);
		GL11.glVertex2d(x + width, y);

		GL11.glTexCoord2d(u, v);
		GL11.glVertex2d(x, y);
		GL11.glEnd();
	}

	public static void drawEntity(EntityLivingBase entity, int x, int y, int scale, float mouseX, float mouseY) {
		GuiInventory.drawEntityOnScreen(x, y, scale, (float) x - mouseX, (float) y - (float) scale * entity.getEyeHeight() - mouseY, entity);
	}

}
