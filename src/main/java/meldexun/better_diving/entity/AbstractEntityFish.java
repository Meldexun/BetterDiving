package meldexun.better_diving.entity;

import meldexun.better_diving.entity.ai.EntityAIFishWander;
import meldexun.better_diving.util.EntityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractEntityFish extends EntityWaterMob {

	public AbstractEntityFish(World worldIn) {
		super(worldIn);
		this.setSize(0.4F, 0.4F);
	}

	@Override
	public void onUpdate() {
		if (!this.inWater) {
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
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.036D);
		this.getEntityAttribute(SWIM_SPEED).setBaseValue(0.036D);
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
		double speed = this.getEntityAttribute(SWIM_SPEED).getAttributeValue();
		EntityPlayer player = this.world.getClosestPlayerToEntity(this, 4.0D);
		if (player != null) {
			speed *= 1.1D;
		}
		if (!this.world.isDaytime()) {
			speed *= 0.8D;
		}
		return speed;
	}

	public static boolean canSpawnAt(World world, BlockPos pos) {
		return pos.getY() > 5 && pos.getY() < world.getSeaLevel() && EntityHelper.blocksToSeafloor(world, pos) < 32;
	}

}
