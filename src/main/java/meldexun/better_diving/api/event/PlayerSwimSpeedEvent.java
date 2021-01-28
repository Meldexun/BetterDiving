package meldexun.better_diving.api.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public class PlayerSwimSpeedEvent extends Event {

	private final PlayerEntity player;
	private final double originalSwimSpeed;
	private double newSwimSpeed;

	public PlayerSwimSpeedEvent(PlayerEntity player, double originalSwimSpeed) {
		this.player = player;
		this.originalSwimSpeed = originalSwimSpeed;
		this.newSwimSpeed = originalSwimSpeed;
	}

	public PlayerEntity getPlayer() {
		return this.player;
	}

	public double getOriginalSwimSpeed() {
		return this.originalSwimSpeed;
	}

	public double getNewSwimSpeed() {
		return this.newSwimSpeed;
	}

	public void setNewSwimSpeed(double newSwimSpeed) {
		this.newSwimSpeed = newSwimSpeed;
	}

}
