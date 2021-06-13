package meldexun.better_diving.api.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * Use this event to prevent a player from taking damage when he has no oxygen.<br>
 * For example could be cancelled when a player suffocates in a moon dimension<br>
 * because by default drowning damage is dealt which makes the underwater bubble sound.
 */
@Deprecated
@Cancelable
public class PlayerSuffocateEvent extends Event {

	private final PlayerEntity player;

	public PlayerSuffocateEvent(PlayerEntity player) {
		this.player = player;
	}

	public PlayerEntity getPlayer() {
		return this.player;
	}

}
