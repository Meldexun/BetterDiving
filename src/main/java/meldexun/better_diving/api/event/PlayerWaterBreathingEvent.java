package meldexun.better_diving.api.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public class PlayerWaterBreathingEvent extends Event {

	private final PlayerEntity player;
	private boolean hasWaterBreathing;

	public PlayerWaterBreathingEvent(PlayerEntity player, boolean hasWaterBreathing) {
		this.player = player;
		this.hasWaterBreathing = hasWaterBreathing;
	}

	public PlayerEntity getPlayer() {
		return this.player;
	}

	public boolean hasWaterBreathing() {
		return this.hasWaterBreathing;
	}

	public void setHasWaterBreathing(boolean hasWaterBreathing) {
		this.hasWaterBreathing = hasWaterBreathing;
	}

}
