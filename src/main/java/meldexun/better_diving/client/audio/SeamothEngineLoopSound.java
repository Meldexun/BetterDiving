package meldexun.better_diving.client.audio;

import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.init.BetterDivingSounds;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SeamothEngineLoopSound extends TickableSound {

	private EntitySeamoth seamoth;
	private int tick;

	public SeamothEngineLoopSound(EntitySeamoth seamoth) {
		super(BetterDivingSounds.SEAMOTH_ENGINE_LOOP.get(), seamoth.getSoundSource());
		this.seamoth = seamoth;
		this.looping = true;
		this.delay = 0;
		this.volume = 1.0F;
		this.pitch = 1.0F;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		if (this.seamoth.removed || this.tick < 0) {
			this.stop();;
		} else {
			this.x = this.seamoth.getX();
			this.y = this.seamoth.getY();
			this.z = this.seamoth.getZ();

			int i = 100;
			if (this.seamoth.isPlayerSteering() && this.seamoth.hasEnergy()) {
				this.tick = Math.min(this.tick + 1, i);
			} else {
				this.tick -= 3;
			}

			this.pitch = 0.5F + MathHelper.clamp((float) this.tick / (float) i * 0.55F, 0.0F, 0.55F);
			this.volume = 0.1F * MathHelper.clamp((float) this.tick / (float) i, 0.0F, 1.0F);
		}
	}

	public int getTick() {
		return this.tick;
	}

	public void setTick(int tick) {
		this.tick = tick;
	}

}
