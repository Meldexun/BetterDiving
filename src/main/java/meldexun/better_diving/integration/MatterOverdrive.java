package meldexun.better_diving.integration;

import matteroverdrive.entity.android_player.AndroidPlayer;
import matteroverdrive.init.OverdriveBioticStats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

public class MatterOverdrive {

	private static boolean loaded = false;

	private MatterOverdrive() {

	}

	public static void setLoaded() {
		MatterOverdrive.loaded = Loader.isModLoaded("matteroverdrive");
	}

	public static boolean hasWaterBreathing(EntityLivingBase entity) {
		if (!MatterOverdrive.loaded) {
			return false;
		}
		return MatterOverdrive.hasEntityWaterBreathing(entity);
	}

	@Optional.Method(modid = "matteroverdrive")
	private static boolean hasEntityWaterBreathing(EntityLivingBase entity) {
		AndroidPlayer android = entity.getCapability(AndroidPlayer.CAPABILITY, null);
		if (android == null) {
			return false;
		}
		int unlockedLevel = android.getUnlockedLevel(OverdriveBioticStats.oxygen);
		return unlockedLevel > 0 && OverdriveBioticStats.oxygen.isEnabled(android, unlockedLevel);
	}

}
