package meldexun.better_diving.api.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;

/**
 * Use this event to allow/disallow a player to breath.<br>
 * For example could be used to disallow a player to breath while in a moon dimension.
 */
public class PlayerCanBreathEvent extends Event {

	private final PlayerEntity player;
	private boolean canBreath;

	public PlayerCanBreathEvent(PlayerEntity player, boolean canBreath) {
		this.player = player;
		this.canBreath = canBreath;
	}

	public PlayerEntity getPlayer() {
		return this.player;
	}

	public boolean canBreath() {
		return this.canBreath;
	}

	public void setCanBreath(boolean canBreath) {
		this.canBreath = canBreath;
	}

}
