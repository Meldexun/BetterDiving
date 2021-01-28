package meldexun.better_diving.client.util;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiHelper {

	public static final double TWO_PI = 2.0D * Math.PI;

	private GuiHelper() {

	}

	public static int getAnchorX(int width, int anchor, int offsetX) {
		MainWindow mainWindow = Minecraft.getInstance().getMainWindow();

		if (anchor == 1 || anchor == 4) {
			// top mid or bot mid
			return offsetX + (mainWindow.getScaledWidth() - width) / 2;
		} else if (anchor == 2 || anchor == 3) {
			// top right or bot right
			return offsetX + mainWindow.getScaledWidth() - width;
		}

		return offsetX;
	}

	public static int getAnchorY(int height, int anchor, int offsetY) {
		MainWindow mainWindow = Minecraft.getInstance().getMainWindow();

		if (anchor == 3 || anchor == 4 || anchor == 5) {
			// bot right or bot mid or bot left
			return offsetY + mainWindow.getScaledHeight() - height;
		}

		return offsetY;
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

}
