package meldexun.better_diving.entity;

import meldexun.better_diving.BetterDiving;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntitySeamoth extends Entity {

	public EntitySeamoth(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Override
	protected void registerData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		// TODO Auto-generated method stub

	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	/*
	@Override
	public void tick() {
		super.tick();

		this.onGround = false;

		this.setMotion(this.getMotion().add(0.0D, -0.01D, 0.0D));
		this.move(MoverType.SELF, this.getMotion());
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}
	*/

	@Override
	public boolean canBeCollidedWith() {
		return !this.removed;
	}

	/*
	@Override
	public boolean hasNoGravity() {
		return true;
	}
	*/

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	@Override
	public boolean canBeRiddenInWater(Entity rider) {
		// TODO Auto-generated method stub
		return true;
	}
	*/

	@Override
	public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
		player.startRiding(this);
		return ActionResultType.SUCCESS;
	}

	/*
	@Override
	public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
		// TODO Auto-generated method stub
		return super.applyPlayerInteraction(player, vec, hand);
	}

	@Override
	public void applyOrientationToEntity(Entity entityToUpdate) {
		this.updateRotation();

		entityToUpdate.prevRotationYaw = this.yaw;
		entityToUpdate.rotationYaw = this.yaw;
		entityToUpdate.prevRotationPitch = this.pitch;
		entityToUpdate.rotationPitch = this.pitch;
		if (entityToUpdate instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) entityToUpdate;
			entity.prevRotationYawHead = this.yaw;
			entity.rotationYawHead = this.yaw;
			entity.prevRenderYawOffset = this.yaw;
			entity.renderYawOffset = this.yaw;
		}
	}

	private float partialTicks;
	public float yaw;
	public float pitch;
	private double x;
	private double y;

	@OnlyIn(Dist.CLIENT)
	public void updateRotation() {
		Minecraft mc = Minecraft.getInstance();
		if (true) {
			float f = mc.getRenderPartialTicks() - this.partialTicks;
			if (f <= 0.0F) {
				f++;
			}
			this.partialTicks = mc.getRenderPartialTicks();

			if (true) {
				double d = mc.gameSettings.mouseSensitivity * 0.6D + 0.2D;
				double d1 = d * d * d * 8.0D;
				double deltaX = mc.mouseHelper.getMouseX() - x;
				double deltaY = mc.mouseHelper.getMouseY() - y;
				x = mc.mouseHelper.getMouseX();
				y = mc.mouseHelper.getMouseY();
				BetterDiving.LOGGER.info("{}", mc.player.getPosY());
				double d2 = MathHelper.clamp(deltaX * d1 * 0.06D, -24.0D * f, 24.0D * f);
				double d3 = MathHelper.clamp(deltaY * d1 * 0.06D, -24.0D * f, 24.0D * f);
				if (mc.gameSettings.invertMouse) {
					d3 *= -1.0D;
				}

				this.rotationYaw += (float) d2 * 0.0F;
				this.rotationPitch += (float) d3 * 0.0F;
				this.yaw += (float) (deltaX * d1 * 0.15D);
				this.pitch += (float) (deltaY * d1 * 0.15D);
				// new ReflectionField<>(Entity.class, "eyeHeight", "eyeHeight").set(mc.player, 1.164375F);
			}

			
			  if (Math.abs(this.yaw) > 0.01F) {
			  this.rotationYaw += this.yaw * 0.5F * f;
			  this.yaw *= 1.0F - 0.2F * f;
			  } else {
			  this.yaw = 0.0F;
			  }
			  if (Math.abs(this.pitch) > 0.01F) {
			  this.rotationPitch += this.pitch * 0.5F * f;
			  this.pitch *= 1.0F - 0.2F * f;
			  } else {
			  this.pitch = 0.0F;
			  }
			 

			// this.yaw = MathHelper.wrapDegrees(this.yaw);
			this.pitch = MathHelper.clamp(this.pitch, -90.0F, 90.0F);
		}
	}

	@Override
	public double getMountedYOffset() {
		// TODO Auto-generated method stub

		return 0.36D;
	}
	*/

}
