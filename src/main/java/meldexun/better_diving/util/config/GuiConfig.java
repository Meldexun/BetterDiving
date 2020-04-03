package meldexun.better_diving.util.config;

import net.minecraftforge.common.config.Config;

public class GuiConfig {

	@Config.Comment("Enable/Disable this gui")
	public boolean enabled;
	@Config.Comment("0: top-left, 1: top-middle, 2: top-right, 3: bottom-right, 4: bottom-middle, 5: bottom-left")
	@Config.RangeInt(min = 0, max = 5)
	public int anchor;
	@Config.Comment("X-offset of this gui")
	@Config.RangeInt(min = -1000, max = 1000)
	public int offsetX;
	@Config.Comment("Y-offset of this gui")
	@Config.RangeInt(min = -1000, max = 1000)
	public int offsetY;

	public GuiConfig(boolean enabled, int anchor, int offsetX, int offsetY) {
		this.enabled = enabled;
		this.anchor = anchor;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

}
