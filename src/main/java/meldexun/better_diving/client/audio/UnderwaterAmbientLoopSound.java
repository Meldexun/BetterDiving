package meldexun.better_diving.client.audio;

import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.init.ModSounds;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UnderwaterAmbientLoopSound extends MovingSound {

	private EntityPlayer player;
	private int tick = 0;

	public UnderwaterAmbientLoopSound(EntityPlayer player) {
		super(ModSounds.UNDERWATER_AMBIENCE, SoundCategory.AMBIENT);
		this.player = player;
		this.repeat = true;
		this.repeatDelay = 0;
		this.volume = 1.0F;
		this.pitch = 1.0F;
	}

	@Override
	public void update() {
		if (this.player.isDead || this.tick < 0) {
			this.donePlaying = true;
		} else {
			this.xPosF = (float) this.player.posX;
			this.yPosF = (float) this.player.posY;
			this.zPosF = (float) this.player.posZ;

			if (this.player.isInsideOfMaterial(Material.WATER)) {
				this.tick = Math.min(this.tick + 1, 40);
			} else {
				this.tick -= 2;
			}

			this.volume = MathHelper.clamp((float) this.tick / 40.0F, 0.0F, 1.0F);
			this.pitch = 1.0F;

			if (this.player.getRidingEntity() instanceof EntitySeamoth) {
				this.volume *= 0.6F;
				this.pitch *= 0.85F;
			}
		}
	}

}
