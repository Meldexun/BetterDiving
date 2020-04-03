package meldexun.better_diving.client.audio;

import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.init.ModSounds;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SeamothEngineLoopSound extends MovingSound {

	private EntitySeamoth seamoth;
	private int tick = 0;

	public SeamothEngineLoopSound(EntitySeamoth seamoth) {
		super(ModSounds.SEAMOTH_ENGINE_LOOP, SoundCategory.NEUTRAL);
		this.repeat = true;
		this.repeatDelay = 0;
		this.volume = 1.0F;
		this.pitch = 1.0F;
		this.seamoth = seamoth;
	}

	@Override
	public void update() {
		if (this.seamoth.isDead) {
			this.donePlaying = true;
		} else {
			this.xPosF = (float) this.seamoth.posX;
			this.yPosF = (float) this.seamoth.posY;
			this.zPosF = (float) this.seamoth.posZ;

			if (this.seamoth.isPlayerSteering() && this.seamoth.getEnergy() > 0) {
				this.tick = Math.min(this.tick + 1, 60);
			} else {
				this.tick = Math.max(this.tick - 1, 0);
			}

			this.pitch = 0.4F + MathHelper.clamp((float) this.tick / 60.0F * 0.6F, 0.0F, 0.6F);
			this.volume = 0.16F * MathHelper.clamp((float) this.tick / 60.0F, 0.0F, 1.0F);
		}
	}

}
