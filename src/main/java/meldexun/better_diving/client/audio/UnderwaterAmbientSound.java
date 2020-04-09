package meldexun.better_diving.client.audio;

import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UnderwaterAmbientSound extends MovingSound {

	private EntityPlayer player;

	public UnderwaterAmbientSound(EntityPlayer player, SoundEvent sound) {
		super(sound, SoundCategory.AMBIENT);
		this.player = player;
		this.repeat = false;
		this.repeatDelay = 0;
		this.volume = 1.0F;
		this.pitch = 1.0F;
	}

	@Override
	public void update() {
		if (this.player.isDead) {
			this.donePlaying = true;
		} else {
			this.xPosF = (float) this.player.posX;
			this.yPosF = (float) this.player.posY;
			this.zPosF = (float) this.player.posZ;

			if (this.player.getRidingEntity() instanceof EntitySeamoth) {
				this.volume = 0.6F;
				this.pitch = 0.85F;
			} else {
				this.volume = 1.0F;
				this.pitch = 1.0F;
			}
		}
	}

}
