package meldexun.better_diving.integration;

import ic2.core.item.tool.ItemDrill;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.Optional;

public class IndustrialCraft {

	private static boolean loaded = false;

	private IndustrialCraft() {

	}

	public static void setLoaded() {
		for (ModContainer modContainer : Loader.instance().getActiveModList()) {
			if (modContainer.getModId().equals("ic2") && modContainer.getName().equals("IndustrialCraft 2")) {
				IndustrialCraft.loaded = true;
				break;
			}
		}
	}

	public static boolean isDrilling(EntityLivingBase entity) {
		if (!IndustrialCraft.loaded) {
			return false;
		}
		return IndustrialCraft.isEntityDrilling(entity);
	}

	@Optional.Method(modid = "ic2")
	private static boolean isEntityDrilling(EntityLivingBase entity) {
		return entity.getHeldItemMainhand().getItem() instanceof ItemDrill;
	}

}
