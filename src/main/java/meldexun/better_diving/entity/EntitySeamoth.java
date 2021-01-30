package meldexun.better_diving.entity;

import meldexun.better_diving.BetterDiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

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
		return new SSpawnObjectPacket(this);
	}

	@Override
	public void tick() {
		super.tick();

		this.onGround = false;
	
		this.move(MoverType.SELF, Vector3d.ZERO);

		BetterDiving.LOGGER.info("test");
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.removed;
	}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

}
