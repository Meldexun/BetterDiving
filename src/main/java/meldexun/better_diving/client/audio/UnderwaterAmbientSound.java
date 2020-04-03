package meldexun.better_diving.client.audio;

import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.init.ModSounds;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UnderwaterAmbientSound extends MovingSound {

	private EntityPlayer player;
	private int tick = 0;

	public UnderwaterAmbientSound(EntityPlayer player) {
		super(ModSounds.UNDERWATER_AMBIENCE, SoundCategory.AMBIENT);
		this.repeat = true;
		this.repeatDelay = 0;
		this.volume = 1.0F;
		this.pitch = 1.0F;
		this.player = player;
	}

	@Override
	public void update() {
		if (this.player.isDead) {
			this.donePlaying = true;
		} else {
			if (BetterDivingConfig.CLIENT_SETTINGS.underWaterAmbience) {
				this.xPosF = (float) this.player.posX;
				this.yPosF = (float) this.player.posY;
				this.zPosF = (float) this.player.posZ;

				if (this.player.isInsideOfMaterial(Material.WATER)) {
					this.tick = Math.min(this.tick + 1, 60);
				} else {
					this.tick = Math.max(this.tick - 10, 0);
				}

				this.volume = 0.6F * MathHelper.clamp((float) this.tick / 60.0F, 0.0F, 1.0F);
				if (this.player.getRidingEntity() instanceof EntitySeamoth) {
					this.volume *= 0.5F;
				}
			} else {
				this.tick = 0;
				this.volume = 0.0F;
			}
		}
	}

}
