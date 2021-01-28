package meldexun.better_diving.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ClientBetterDiving {

	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static PlayerEntity getPlayer() {
		return Minecraft.getInstance().player;
	}

}
