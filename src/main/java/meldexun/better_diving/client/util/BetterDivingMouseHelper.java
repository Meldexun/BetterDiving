package meldexun.better_diving.client.util;

import meldexun.better_diving.BetterDiving;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Dist.CLIENT)
public class BetterDivingMouseHelper {

	public static double deltaX;
	public static double deltaY;
	private static boolean ignoreFirstMove;
	private static double previousX;
	private static double previousY;

	@SubscribeEvent
	public static void onRenderTickEvent(RenderTickEvent event) {
		if (event.phase != Phase.END) {
			return;
		}
		Minecraft mc = Minecraft.getInstance();
		boolean flag = mc.mouseHandler.isMouseGrabbed() && mc.isWindowActive();
		if (flag) {
			if (ignoreFirstMove) {
				deltaX = 0.0D;
				deltaY = 0.0D;
				ignoreFirstMove = false;
			} else {
				deltaX = mc.mouseHandler.xpos() - previousX;
				deltaY = mc.mouseHandler.ypos() - previousY;
			}
		} else {
			deltaX = 0.0D;
			deltaY = 0.0D;
			ignoreFirstMove = true;
		}
		previousX = mc.mouseHandler.xpos();
		previousY = mc.mouseHandler.ypos();
	}

}
