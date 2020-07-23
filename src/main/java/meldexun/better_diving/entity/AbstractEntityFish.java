package meldexun.better_diving.entity;

import meldexun.better_diving.entity.ai.EntityAIFishWander;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class AbstractEntityFish extends EntityWaterMob {

	public AbstractEntityFish(World worldIn) {
		super(worldIn);
		this.setSize(0.4F, 0.4F);
	}

	@Override
	public void onUpdate() {
		if (!this.isInsideOfMaterial(Material.WATER)) {
			this.motionY -= 0.02D;
		}

		super.onUpdate();
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAIFishWander(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.032D);
		this.getEntityAttribute(EntityLivingBase.SWIM_SPEED).setBaseValue(0.032D);
	}

	@Override
	public float getEyeHeight() {
		return this.height * 0.5F;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

	@Override
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 128 * 128;
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {

	}

	@Override
	protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
		if (source != DamageSource.CRAMMING && source != DamageSource.IN_WALL && source != DamageSource.DROWN) {
			this.entityDropItem(new ItemStack(this.getDropItem()), 0.0F);
		}
	}

	public double getSwimSpeed() {
		double speed = this.getEntityAttribute(EntityLivingBase.SWIM_SPEED).getAttributeValue();
		if (this.world.getClosestPlayerToEntity(this, 4.0D) != null) {
			speed *= 1.1D;
		}
		if (!this.world.isDaytime()) {
			speed *= 0.75D;
		}
		return speed;
	}

	@Override
	public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount) {
		return false;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	public void travel(float strafe, float vertical, float forward) {
		if (!this.world.isRemote) {
			if (this.isInWater()) {
				this.moveRelative(strafe, vertical, forward, 0.02F);
				this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
				this.motionX *= 0.8D;
				this.motionY *= 0.8D;
				this.motionZ *= 0.8D;
			} else if (this.isInLava()) {
				this.moveRelative(strafe, vertical, forward, 0.02F);
				this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
				this.motionX *= 0.5D;
				this.motionY *= 0.5D;
				this.motionZ *= 0.5D;
			} else {
				float f6 = 0.91F;
				BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(this.posX, this.getEntityBoundingBox().minY - 1.0D, this.posZ);

				if (this.onGround) {
					IBlockState underState = this.world.getBlockState(blockpos$pooledmutableblockpos);
					f6 = underState.getBlock().getSlipperiness(underState, this.world, blockpos$pooledmutableblockpos, this) * 0.91F;
				}

				float f7 = 0.16277136F / (f6 * f6 * f6);
				float f8;

				if (this.onGround) {
					f8 = this.getAIMoveSpeed() * f7;
				} else {
					f8 = this.jumpMovementFactor;
				}

				this.moveRelative(strafe, vertical, forward, f8);
				f6 = 0.91F;

				if (this.onGround) {
					IBlockState underState = this.world.getBlockState(blockpos$pooledmutableblockpos.setPos(this.posX, this.getEntityBoundingBox().minY - 1.0D, this.posZ));
					f6 = underState.getBlock().getSlipperiness(underState, this.world, blockpos$pooledmutableblockpos, this) * 0.91F;
				}

				if (this.isOnLadder()) {
					this.motionX = MathHelper.clamp(this.motionX, -0.15000000596046448D, 0.15000000596046448D);
					this.motionZ = MathHelper.clamp(this.motionZ, -0.15000000596046448D, 0.15000000596046448D);
					this.fallDistance = 0.0F;

					if (this.motionY < -0.15D) {
						this.motionY = -0.15D;
					}

					boolean flag = this.isSneaking();

					if (flag && this.motionY < 0.0D) {
						this.motionY = 0.0D;
					}
				}

				this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

				if (this.collidedHorizontally && this.isOnLadder()) {
					this.motionY = 0.2D;
				}

				if (this.isPotionActive(MobEffects.LEVITATION)) {
					this.motionY += (0.05D * (this.getActivePotionEffect(MobEffects.LEVITATION).getAmplifier() + 1) - this.motionY) * 0.2D;
				} else {
					blockpos$pooledmutableblockpos.setPos(this.posX, 0.0D, this.posZ);

					if (!this.world.isRemote || this.world.isBlockLoaded(blockpos$pooledmutableblockpos) && this.world.getChunk(blockpos$pooledmutableblockpos).isLoaded()) {
						if (!this.hasNoGravity()) {
							this.motionY -= 0.08D;
						}
					} else if (this.posY > 0.0D) {
						this.motionY = -0.1D;
					} else {
						this.motionY = 0.0D;
					}
				}

				this.motionY *= 0.9800000190734863D;
				this.motionX *= f6;
				this.motionZ *= f6;
				blockpos$pooledmutableblockpos.release();
			}
		}

		this.prevLimbSwingAmount = this.limbSwingAmount;
		double d5 = this.posX - this.prevPosX;
		double d7 = this.posZ - this.prevPosZ;
		double d9 = this.posY - this.prevPosY;
		float f10 = MathHelper.sqrt(d5 * d5 + d9 * d9 + d7 * d7) * 4.0F;

		if (f10 > 1.0F) {
			f10 = 1.0F;
		}

		this.limbSwingAmount += (f10 - this.limbSwingAmount) * 0.4F;
		this.limbSwing += this.limbSwingAmount;
	}

}
