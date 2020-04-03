package meldexun.better_diving.client.util;

import java.awt.Toolkit;

import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.config.GuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHelper {

	public static final double TWO_PI = 2.0D * Math.PI;

	public static ResourceLocation getTexture(ResourceLocation texture1080, ResourceLocation texture1440, ResourceLocation texture2160) {
		int config = BetterDivingConfig.CLIENT_SETTINGS.autoResolution;

		if (config == 0) {
			int height = Toolkit.getDefaultToolkit().getScreenSize().height;

			if (height == 1080) {
				return texture1080;
			} else if (height == 1440) {
				return texture1440;
			} else {
				return texture2160;
			}
		} else if (config == 1) {
			return texture1080;
		} else if (config == 2) {
			return texture1440;
		} else {
			return texture2160;
		}
	}

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

}
