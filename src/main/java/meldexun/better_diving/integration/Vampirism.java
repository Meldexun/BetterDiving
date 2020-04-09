package meldexun.better_diving.integration;

import de.teamlapen.vampirism.util.Helper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

public class Vampirism {

	private static boolean loaded = false;

	private Vampirism() {

	}

	public static void setLoaded() {
		Vampirism.loaded = Loader.isModLoaded("vampirism");
	}

	public static boolean hasWaterBreathing(EntityLivingBase entity) {
		if (!Vampirism.loaded) {
			return false;
		}
		return Vampirism.hasEntityWaterBreathing(entity);
	}

	@Optional.Method(modid = "vampirism")
	private static boolean hasEntityWaterBreathing(EntityLivingBase entity) {
		return Helper.isVampire(entity);
	}

}
