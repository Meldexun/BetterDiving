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
		boolean flag = mc.mouseHelper.isMouseGrabbed() && mc.isGameFocused();
		if (flag) {
			if (ignoreFirstMove) {
				deltaX = 0.0D;
				deltaY = 0.0D;
				ignoreFirstMove = false;
			} else {
				deltaX = mc.mouseHelper.getMouseX() - previousX;
				deltaY = mc.mouseHelper.getMouseY() - previousY;
			}
		} else {
			deltaX = 0.0D;
			deltaY = 0.0D;
			ignoreFirstMove = true;
		}
		previousX = mc.mouseHelper.getMouseX();
		previousY = mc.mouseHelper.getMouseY();
	}

}
