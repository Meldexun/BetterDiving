package meldexun.better_diving.plugin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.MathHelper;

public class ClientPlayerEntityHook {

	public static float getWaterBrightness(ClientPlayerEntity player) {
		if (!player.isEyeInFluid(FluidTags.WATER)) {
			return 0.0F;
		}
		Minecraft mc = Minecraft.getInstance();
		return MathHelper.clamp((player.waterVisionTime + mc.getFrameTime()) / 60.0F, 0.0F, 1.0F);
	}

}
