package meldexun.better_diving.integration;

import net.minecraft.world.World;

public class BeyondEarth {

	public static boolean isSpace(World level) {
		return net.mrscauthd.boss_tools.events.Config.PlayerOxygenSystem
				&& net.mrscauthd.boss_tools.events.Methodes.isSpaceWorld(level);
	}

}
