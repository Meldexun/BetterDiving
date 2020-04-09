package meldexun.better_diving.client.audio;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityFollowingSound extends MovingSound {

	private Entity entity;

	public EntityFollowingSound(Entity entity, SoundEvent sound) {
		super(sound, entity.getSoundCategory());
		this.entity = entity;
		this.repeat = false;
		this.repeatDelay = 0;
		this.volume = 1.0F;
		this.pitch = 1.0F;
	}

	@Override
	public void update() {
		if (this.entity.isDead) {
			this.donePlaying = true;
		} else {
			this.xPosF = (float) this.entity.posX;
			this.yPosF = (float) this.entity.posY;
			this.zPosF = (float) this.entity.posZ;

			this.volume = 1.0F;
			this.pitch = 1.0F;
		}
	}

}
