package meldexun.better_diving.entity.ai;

import java.util.Random;

import meldexun.better_diving.entity.AbstractEntityFish;
import meldexun.better_diving.util.EntityHelper;
import meldexun.better_diving.util.MovementHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityAIFishWander extends EntityAIBase {

	protected final Random rand = new Random();
	protected final AbstractEntityFish entity;
	private Vec3d pos;
	private int tick;

	public EntityAIFishWander(AbstractEntityFish entity) {
		this.entity = entity;
		this.setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		return this.entity.isInsideOfMaterial(Material.WATER);
	}

	@Override
	public boolean shouldContinueExecuting() {
		return this.entity.isInsideOfMaterial(Material.WATER) && this.entity.getDistance(this.pos.x, this.pos.y, this.pos.z) > 0.1D && this.tick < 120 && !this.checkStuck();
	}

	@Override
	public void startExecuting() {
		this.tick = 0;

		double x = this.entity.posX - 8.0D + this.rand.nextDouble() * 16.0D;
		double y = this.entity.posY - 4.0D + this.rand.nextDouble() * 8.0D;
		double z = this.entity.posZ - 8.0D + this.rand.nextDouble() * 16.0D;

		for (int i = 1; i <= 10 && this.entity.world.getBlockState(new BlockPos(x, y, z)).getMaterial() != Material.WATER; i++) {
			double d0 = 1.0D - 0.025D * i;
			double d1 = 4.0D * d0;
			double d2 = 8.0D * d0;
			double d3 = 16.0D * d0;
			x = this.entity.posX - d2 + this.rand.nextDouble() * d3;
			y = this.entity.posY - d1 + this.rand.nextDouble() * d2;
			z = this.entity.posZ - d2 + this.rand.nextDouble() * d3;
		}

		if (EntityHelper.blocksToSeafloor(this.entity.world, new BlockPos(x, y, z)) > 16.0D) {
			y -= this.rand.nextDouble() * 4.0D;
		}

		y = MathHelper.clamp(y, MathHelper.floor(this.entity.posY - EntityHelper.blocksToSeafloor(this.entity)), MathHelper.floor(this.entity.posY + EntityHelper.blocksUnderWater(this.entity)));
		this.pos = new Vec3d(x, y, z);
	}

	@Override
	public void updateTask() {
		this.tick++;
		double x = this.pos.x - this.entity.posX;
		double y = this.pos.y - this.entity.posY;
		double z = this.pos.z - this.entity.posZ;
		double d = Math.sqrt(x * x + z * z);

		float f = (float) (Math.toDegrees(Math.atan2(-x, z)));
		float f1 = -(float) (Math.toDegrees(Math.atan2(y, d)));

		if (this.rand.nextBoolean()) {
			f += 1;
			f1 += 1;
		}

		this.entity.rotationYaw += MathHelper.clamp(MathHelper.wrapDegrees(f - this.entity.rotationYaw), -90.0F, 90.0F);
		this.entity.rotationYaw = MathHelper.wrapDegrees(this.entity.rotationYaw);
		this.entity.rotationPitch += MathHelper.clamp(MathHelper.wrapDegrees(f1 - this.entity.rotationPitch), -90.0F, 90.0F);
		this.entity.rotationPitch = MathHelper.clamp(this.entity.rotationPitch, -90.0F, 90.0F);
		this.entity.rotationYawHead = this.entity.rotationYaw;

		MovementHelper.move3D(this.entity, 0.0D, 0.0D, 1.0D, this.entity.getSwimSpeed(), this.entity.rotationYaw, this.entity.rotationPitch);
	}

	public boolean checkStuck() {
		return false;
	}

}
