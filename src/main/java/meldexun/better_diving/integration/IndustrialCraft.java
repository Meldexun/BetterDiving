package meldexun.better_diving.integration;

import ic2.core.item.tool.ItemDrill;
import net.minecraft.entity.player.EntityPlayer;

public class IndustrialCraft {

	public static boolean loaded = false;

	public static boolean isPlayerDrilling(EntityPlayer player) {
		return player.getHeldItemMainhand().getItem() instanceof ItemDrill;
	}

}
