package meldexun.better_diving.integration;

import de.teamlapen.vampirism.util.Helper;
import net.minecraft.entity.player.EntityPlayer;

public class Vampirism {

	public static boolean loaded = false;

	public static boolean isVampire(EntityPlayer player) {
		return Helper.isVampire(player);
	}

}
