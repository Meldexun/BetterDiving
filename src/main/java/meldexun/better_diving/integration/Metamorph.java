package meldexun.better_diving.integration;

import mchorse.metamorph.api.EntityUtils;
import mchorse.metamorph.api.abilities.IAbility;
import mchorse.metamorph.api.morphs.AbstractMorph;
import mchorse.vanilla_pack.abilities.WaterBreath;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

public class Metamorph {

	private static boolean loaded = false;

	private Metamorph() {

	}

	public static void setLoaded() {
		Metamorph.loaded = Loader.isModLoaded("metamorph");
	}

	public static boolean hasWaterBreathing(EntityLivingBase entity) {
		if (!Metamorph.loaded) {
			return false;
		}
		return Metamorph.hasEntityWaterBreathing(entity);
	}

	@Optional.Method(modid = "metamorph")
	private static boolean hasEntityWaterBreathing(EntityLivingBase entity) {
		AbstractMorph morph = EntityUtils.getMorph(entity);
		if (morph != null) {
			for (IAbility ability : morph.settings.abilities) {
				if (ability instanceof WaterBreath) {
					return true;
				}
			}
		}
		return false;
	}

}
