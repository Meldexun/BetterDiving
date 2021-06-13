package meldexun.better_diving.entity;

import java.util.List;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.ClientBetterDiving;
import meldexun.better_diving.client.audio.SeamothEngineLoopSound;
import meldexun.better_diving.client.audio.SeamothStartSound;
import meldexun.better_diving.client.util.BetterDivingMouseHelper;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.init.BetterDivingItems;
import meldexun.better_diving.init.BetterDivingSounds;
import meldexun.better_diving.item.ItemEnergyStorage;
import meldexun.better_diving.item.ItemPowerCell;
import meldexun.better_diving.network.packet.client.CPacketSyncSeamothInput;
import meldexun.better_diving.network.packet.server.SPacketSyncSeamothEnergy;
import meldexun.better_diving.network.packet.server.SPacketSyncSeamothPowerCell;
import meldexun.better_diving.util.BetterDivingHelper;
import meldexun.better_diving.util.reflection.ReflectionMethod;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class EntitySeamoth extends Entity implements IEntityAdditionalSpawnData {

	private static final ReflectionMethod<?> PLAYER_ENTITY_UPDATE_PLAYER_POSE = new ReflectionMethod<>(PlayerEntity.class, "func_213832_dB", "updatePlayerPose");

	private int damage = 0;

	private boolean controlled = false;
	private boolean prevControlled = false;
	private boolean prevSteered = false;

	public boolean inputForward = false;
	public boolean inputRight = false;
	public boolean inputBack = false;
	public boolean inputLeft = false;
	public boolean inputUp = false;
	public boolean inputDown = false;

	public float yaw = 0.0F;
	public float pitch = 0.0F;
	public float partialTicks = 0.0F;

	public boolean insideWater = false;

	private int prevEnergy;

	@OnlyIn(Dist.CLIENT)
	public SeamothStartSound startSound;
	@OnlyIn(Dist.CLIENT)
	public SeamothEngineLoopSound engineLoopSound;

	public EntitySeamoth(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Override
	protected void defineSynchedData() {

	}
	
	@Override
	protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {

	}
	
	@Override
	protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {

	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeItemStack(this.getPowerCell(), false);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		this.setPowerCell(additionalData.readItem());
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return sizeIn.height * 0.5F;
	}

	@OnlyIn(Dist.CLIENT)
	public void updateEngineSound() {
		Minecraft mc = Minecraft.getInstance();

		if (mc.options.getSoundSourceVolume(SoundCategory.MASTER) > 0.0F) {
			SoundHandler soundHandler = mc.getSoundManager();

			if (!soundHandler.isActive(this.startSound)) {
				if (!this.prevSteered && this.isPlayerSteering() && this.hasEnergy() && !soundHandler.isActive(this.engineLoopSound)) {
					this.startSound = new SeamothStartSound(this);
					soundHandler.play(this.startSound);
				} else if (this.startSound != null) {
					this.startSound = null;
				}
			}

			if (!soundHandler.isActive(this.engineLoopSound)) {
				if (this.isPlayerSteering() && this.hasEnergy()) {
					int tick = this.engineLoopSound != null && this.engineLoopSound.getTick() > 0 ? this.engineLoopSound.getTick() : 0;
					this.engineLoopSound = new SeamothEngineLoopSound(this);
					this.engineLoopSound.setTick(tick);
					soundHandler.play(this.engineLoopSound);
				} else if (this.engineLoopSound != null) {
					this.engineLoopSound = null;
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void updateControls() {
		Minecraft mc = Minecraft.getInstance();

		this.prevControlled = this.controlled;
		this.controlled = this.getControllingPassenger() == mc.player;
		this.prevSteered = this.isPlayerSteering();

		if (this.controlled) {
			GameSettings settings = mc.options;

			this.inputForward = settings.keyUp.isDown();
			this.inputRight = settings.keyRight.isDown();
			this.inputBack = settings.keyDown.isDown();
			this.inputLeft = settings.keyLeft.isDown();
			this.inputUp = settings.keyJump.isDown();
			this.inputDown = ClientBetterDiving.KEY_BIND_DESCEND.isDown();

			BetterDiving.NETWORK.sendToServer(new CPacketSyncSeamothInput(this));
		} else if (this.prevControlled) {
			this.inputForward = false;
			this.inputRight = false;
			this.inputBack = false;
			this.inputLeft = false;
			this.inputUp = false;
			this.inputDown = false;

			BetterDiving.NETWORK.sendToServer(new CPacketSyncSeamothInput(this));
		}
	}

	@Override
	public void tick() {
		if (this.level.isClientSide() && this.getControllingPassenger() instanceof PlayerEntity && ((PlayerEntity) this.getControllingPassenger()).isLocalPlayer()) {
			this.setPacketCoordinates(this.position());
		}

		super.tick();

		this.onGround = false;
		this.insideWater = this.isEyeInFluid(FluidTags.WATER);

		if (this.damage > 0) {
			this.damage--;
		}

		if (this.level.isClientSide()) {
			this.updateControls();
			this.updateEngineSound();
		}

		this.updateMotion();

		this.move(MoverType.SELF, this.getDeltaMovement());

		if (!this.level.isClientSide()) {
			if (this.getEnergy() != this.prevEnergy) {
				this.syncEnergy();
			}
			this.prevEnergy = this.getEnergy();
		}
	}

	@Override
	protected boolean isMovementNoisy() {
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (!this.level.isClientSide() && !this.removed && source.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) source.getEntity();

			if (player.isCreative()) {
				this.kill();
				return true;
			} else if (!this.isVehicle()) {
				this.damage += 20;
				if (this.damage > 30) {
					this.kill();

					ItemStack seamoth = this.toItemStack();
					if (!player.addItem(seamoth)) {
						this.spawnAtLocation(seamoth, 0.0F);
					}
				}
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean canBeCollidedWith() {
		return !this.removed;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public ActionResultType interact(PlayerEntity player, Hand hand) {
		if (this.isVehicle()) {
			return ActionResultType.FAIL;
		}
		if (!this.level.isClientSide()) {
			player.startRiding(this);
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	protected void addPassenger(Entity passenger) {
		super.addPassenger(passenger);
		passenger.yRot = this.yRot;
		passenger.xRot = this.xRot;
		passenger.setPose(Pose.STANDING);
		if (passenger instanceof PlayerEntity) {
			PLAYER_ENTITY_UPDATE_PLAYER_POSE.invoke(passenger);
		}
		passenger.refreshDimensions();
		if (!this.level.isClientSide()) {
			this.syncPowerCell();
		} else if (passenger instanceof PlayerEntity) {
			this.level.playSound((PlayerEntity) passenger, this.blockPosition(), BetterDivingSounds.SEAMOTH_ENTER.get(), this.getSoundSource(), 1.0F, 1.0F);
		}
	}

	@Override
	public double getPassengersRidingOffset() {
		return 0.36D;
	}

	@Override
	public boolean shouldRiderSit() {
		return true;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public boolean canBeRiddenInWater(Entity rider) {
		return true;
	}

	@Override
	public Entity getControllingPassenger() {
		List<Entity> list = this.getPassengers();
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void onPassengerTurned(Entity entityToUpdate) {
		this.updateRotation();

		entityToUpdate.yRotO = this.yRot;
		entityToUpdate.yRot = this.yRot;
		entityToUpdate.xRotO = this.xRot;
		entityToUpdate.xRot = this.xRot;
		if (entityToUpdate instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) entityToUpdate;
			entity.yHeadRotO = this.yRot;
			entity.yHeadRot = this.yRot;
			entity.yBodyRotO = this.yRot;
			entity.yBodyRot = this.yRot;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void updateRotation() {
		Minecraft mc = Minecraft.getInstance();
		if (this.getControllingPassenger() == mc.player) {
			float f = mc.getFrameTime() - this.partialTicks;
			if (f < 0.0F) {
				f++;
			}
			this.partialTicks = mc.getFrameTime();

			if (this.insideWater && this.hasEnergy()) {
				double d = mc.options.sensitivity * 0.6D + 0.2D;
				double d1 = d * d * d * 8.0D;
				double deltaX = BetterDivingMouseHelper.deltaX;
				double deltaY = BetterDivingMouseHelper.deltaY;
				double d2 = MathHelper.clamp(deltaX * d1 * 0.05D, -40.0D * f, 40.0D * f);
				double d3 = MathHelper.clamp(deltaY * d1 * 0.05D, -40.0D * f, 40.0D * f);
				if (mc.options.invertYMouse) {
					d3 *= -1.0D;
				}

				this.yRot += (float) d2 * 0.5F;
				this.xRot += (float) d3 * 0.5F;
				this.yaw += (float) d2 * 0.5F;
				this.pitch += (float) d3 * 0.5F;
			}

			if (Math.abs(this.yaw) > 0.01F) {
				this.yRot += this.yaw * 0.5F * f;
				this.yaw *= 1.0F - 0.2F * f;
			} else {
				this.yaw = 0.0F;
			}
			if (Math.abs(this.pitch) > 0.01F) {
				this.xRot += this.pitch * 0.5F * f;
				this.pitch *= 1.0F - 0.2F * f;
			} else {
				this.pitch = 0.0F;
			}

			// this.yaw = MathHelper.wrapDegrees(this.yaw);
			this.xRot = MathHelper.clamp(this.xRot, -90.0F, 90.0F);
		}
	}

	public void updateMotion() {
		if (this.insideWater) {
			this.setDeltaMovement(this.getDeltaMovement().scale(0.95D));
		} else {
			this.setDeltaMovement(this.getDeltaMovement().scale(0.99D));
		}

		if (Math.abs(this.getDeltaMovement().x) < 0.001D) {
			this.setDeltaMovement(new Vector3d(0.0D, this.getDeltaMovement().y, this.getDeltaMovement().z));
		}
		if (Math.abs(this.getDeltaMovement().y) < 0.001D) {
			this.setDeltaMovement(new Vector3d(this.getDeltaMovement().x, 0.0D, this.getDeltaMovement().z));
		}
		if (Math.abs(this.getDeltaMovement().z) < 0.001D) {
			this.setDeltaMovement(new Vector3d(this.getDeltaMovement().x, this.getDeltaMovement().y, 0.0D));
		}

		if (!this.insideWater) {
			this.setDeltaMovement(this.getDeltaMovement().subtract(0.0D, 0.015D, 0.0D));
		}

		if (this.getControllingPassenger() instanceof PlayerEntity && this.isPlayerSteering() && this.hasEnergy()) {
			if (!this.level.isClientSide()) {
				this.extractEnergy(BetterDivingConfig.SERVER_CONFIG.seamoth.seamothEnergyUsage.get());
			}

			if (this.insideWater) {
				double speed = BetterDivingConfig.SERVER_CONFIG.seamoth.seamothSpeed.get();

				double forward = 0.0D;
				double up = 0.0D;
				double strafe = 0.0D;

				if (this.inputForward) {
					forward += 1.0D;
				}
				if (this.inputBack) {
					forward -= 1.0D;
				}
				if (this.inputUp) {
					up += 1.0D;
				}
				if (this.inputDown) {
					up -= 1.0D;
				}
				if (this.inputRight) {
					strafe -= 1.0D;
				}
				if (this.inputLeft) {
					strafe += 1.0D;
				}

				if (forward < 0.0D) {
					if (up == 0.0D) {
						if (strafe == 0.0D) {
							speed *= 0.5D;
						} else {
							speed *= 0.7071D;
						}
					} else {
						if (strafe == 0.0D) {
							speed *= 0.7071D;
						} else {
							speed *= 0.8409D;
						}
					}
				}

				Vector3d vec = BetterDivingHelper.getSeamothMoveVec(forward, up, strafe, speed, this.yRot, this.xRot);
				this.setDeltaMovement(this.getDeltaMovement().add(vec));
			}
		}
	}

	public boolean isPlayerSteering() {
		return this.inputForward != this.inputBack || this.inputLeft != this.inputRight || this.inputUp != this.inputDown;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean isNoGravity() {
		return true;
	}

	@Override
	protected boolean canRide(Entity p_184228_1_) {
		return true;
	}

	@Override
	public void onAboveBubbleCol(boolean p_203002_1_) {
		// do nothing
	}

	@Override
	public void onInsideBubbleColumn(boolean p_203004_1_) {
		// do nothing
	}

	public ItemStack toItemStack() {
		ItemStack stack = new ItemStack(BetterDivingItems.SEAMOTH.get());
		stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(c -> {
			this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(c1 -> {
				c.insertItem(0, c1.getStackInSlot(0).copy(), false);
			});
		});
		return stack;
	}

	public ItemStack getPowerCell() {
		LazyOptional<IItemHandler> optionalItemHandler = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if (!optionalItemHandler.isPresent()) {
			return ItemStack.EMPTY;
		}
		IItemHandler itemHandler = optionalItemHandler.orElseThrow(NullPointerException::new);
		return itemHandler.getStackInSlot(0);
	}

	public void setPowerCell(ItemStack stack) {
		this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(c -> {
			((ItemStackHandler) c).setStackInSlot(0, stack);
		});
	}

	public boolean hasEnergy() {
		ItemStack powerCell = this.getPowerCell();
		if (powerCell.getItem() instanceof ItemPowerCell) {
			return ItemEnergyStorage.hasEnergy(powerCell);
		}
		return false;
	}

	public int getEnergy() {
		ItemStack powerCell = this.getPowerCell();
		if (powerCell.getItem() instanceof ItemPowerCell) {
			return ItemEnergyStorage.getEnergy(powerCell);
		}
		return 0;
	}

	public boolean setEnergy(int energy) {
		ItemStack powerCell = this.getPowerCell();
		if (powerCell.getItem() instanceof ItemPowerCell) {
			return ItemEnergyStorage.setEnergy(powerCell, energy);
		}
		return false;
	}

	public int getEnergyCapacity() {
		ItemStack powerCell = this.getPowerCell();
		if (powerCell.getItem() instanceof ItemPowerCell) {
			return ItemEnergyStorage.getEnergyCapacity(powerCell);
		}
		return 0;
	}

	public int receiveEnergy(int amount) {
		if (amount > 0) {
			ItemStack powerCell = this.getPowerCell();
			if (powerCell.getItem() instanceof ItemPowerCell) {
				return ItemEnergyStorage.receiveEnergy(powerCell, amount);
			}
		}
		return 0;
	}

	public int extractEnergy(int amount) {
		if (amount > 0) {
			ItemStack powerCell = this.getPowerCell();
			if (powerCell.getItem() instanceof ItemPowerCell) {
				return ItemEnergyStorage.extractEnergy(powerCell, amount);
			}
		}
		return 0;
	}

	public void syncPowerCell() {
		if (!this.level.isClientSide() && this.getControllingPassenger() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) this.getControllingPassenger();
			BetterDiving.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SPacketSyncSeamothPowerCell(this));
		}
	}

	public void syncEnergy() {
		if (!this.level.isClientSide() && this.getControllingPassenger() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) this.getControllingPassenger();
			BetterDiving.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SPacketSyncSeamothEnergy(this));
		}
	}

}
