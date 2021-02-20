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

	private static final ReflectionMethod<?> METHOD_UPDATE_POSE = new ReflectionMethod<>(PlayerEntity.class, "", "updatePose");

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
	protected void registerData() {

	}

	@Override
	protected void readAdditional(CompoundNBT compound) {

	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {

	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeItemStack(this.getPowerCell(), false);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		this.setPowerCell(additionalData.readItemStack());
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return sizeIn.height * 0.5F;
	}

	@OnlyIn(Dist.CLIENT)
	public void updateEngineSound() {
		Minecraft mc = Minecraft.getInstance();

		if (mc.gameSettings.getSoundLevel(SoundCategory.MASTER) > 0.0F) {
			SoundHandler soundHandler = mc.getSoundHandler();

			if (!soundHandler.isPlaying(this.startSound)) {
				if (!this.prevSteered && this.isPlayerSteering() && this.hasEnergy() && !soundHandler.isPlaying(this.engineLoopSound)) {
					this.startSound = new SeamothStartSound(this);
					soundHandler.play(this.startSound);
				} else if (this.startSound != null) {
					this.startSound = null;
				}
			}

			if (!soundHandler.isPlaying(this.engineLoopSound)) {
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
			GameSettings settings = mc.gameSettings;

			this.inputForward = settings.keyBindForward.isKeyDown();
			this.inputRight = settings.keyBindRight.isKeyDown();
			this.inputBack = settings.keyBindBack.isKeyDown();
			this.inputLeft = settings.keyBindLeft.isKeyDown();
			this.inputUp = settings.keyBindJump.isKeyDown();
			this.inputDown = ClientBetterDiving.KEY_BIND_SEAMOTH_DESCEND.isKeyDown();

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
		if (this.world.isRemote && this.getControllingPassenger() instanceof PlayerEntity && ((PlayerEntity) this.getControllingPassenger()).isUser()) {
			this.func_242277_a(this.getPositionVec());
		}

		super.tick();

		this.onGround = false;
		this.insideWater = this.areEyesInFluid(FluidTags.WATER);

		if (this.damage > 0) {
			this.damage--;
		}

		if (this.world.isRemote) {
			this.updateControls();
			this.updateEngineSound();
		}

		this.updateMotion();

		this.move(MoverType.SELF, this.getMotion());

		if (!this.world.isRemote) {
			if (this.getEnergy() != this.prevEnergy) {
				this.syncEnergy();
			}
			this.prevEnergy = this.getEnergy();
		}
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!this.world.isRemote && !this.removed && source.getTrueSource() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) source.getTrueSource();

			if (player.isCreative()) {
				this.setDead();
				return true;
			} else if (!this.isBeingRidden()) {
				this.damage += 20;
				if (this.damage > 30) {
					this.setDead();

					ItemStack seamoth = this.toItemStack();
					if (!player.addItemStackToInventory(seamoth)) {
						this.entityDropItem(seamoth, 0.0F);
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean func_241845_aY() {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean canBeCollidedWith() {
		return !this.removed;
	}

	@Override
	public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
		if (this.isBeingRidden()) {
			return ActionResultType.FAIL;
		}
		if (!this.world.isRemote) {
			player.startRiding(this);
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	protected void addPassenger(Entity passenger) {
		super.addPassenger(passenger);
		passenger.rotationYaw = this.rotationYaw;
		passenger.rotationPitch = this.rotationPitch;
		passenger.setPose(Pose.STANDING);
		if (passenger instanceof PlayerEntity) {
			METHOD_UPDATE_POSE.invoke(passenger);
		}
		passenger.recalculateSize();
		if (!this.world.isRemote) {
			this.syncPowerCell();
		} else if (passenger instanceof PlayerEntity) {
			this.world.playSound((PlayerEntity) passenger, this.getPosition(), BetterDivingSounds.SEAMOTH_ENTER.get(), this.getSoundCategory(), 1.0F, 1.0F);
		}
	}

	@Override
	public double getMountedYOffset() {
		return 0.36D;
	}

	@Override
	public boolean shouldRiderSit() {
		return true;
	}

	@Override
	public boolean isPushedByWater() {
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
	public void applyOrientationToEntity(Entity entityToUpdate) {
		this.updateRotation();

		entityToUpdate.prevRotationYaw = this.rotationYaw;
		entityToUpdate.rotationYaw = this.rotationYaw;
		entityToUpdate.prevRotationPitch = this.rotationPitch;
		entityToUpdate.rotationPitch = this.rotationPitch;
		if (entityToUpdate instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) entityToUpdate;
			entity.prevRotationYawHead = this.rotationYaw;
			entity.rotationYawHead = this.rotationYaw;
			entity.prevRenderYawOffset = this.rotationYaw;
			entity.renderYawOffset = this.rotationYaw;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void updateRotation() {
		Minecraft mc = Minecraft.getInstance();
		if (this.getControllingPassenger() == mc.player) {
			float f = mc.getRenderPartialTicks() - this.partialTicks;
			if (f < 0.0F) {
				f++;
			}
			this.partialTicks = mc.getRenderPartialTicks();

			if (this.insideWater && this.hasEnergy()) {
				double d = mc.gameSettings.mouseSensitivity * 0.6D + 0.2D;
				double d1 = d * d * d * 8.0D;
				double deltaX = BetterDivingMouseHelper.deltaX;
				double deltaY = BetterDivingMouseHelper.deltaY;
				double d2 = MathHelper.clamp(deltaX * d1 * 0.05D, -40.0D * f, 40.0D * f);
				double d3 = MathHelper.clamp(deltaY * d1 * 0.05D, -40.0D * f, 40.0D * f);
				if (mc.gameSettings.invertMouse) {
					d3 *= -1.0D;
				}

				this.rotationYaw += (float) d2 * 0.5F;
				this.rotationPitch += (float) d3 * 0.5F;
				this.yaw += (float) d2 * 0.5F;
				this.pitch += (float) d3 * 0.5F;
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
			this.rotationPitch = MathHelper.clamp(this.rotationPitch, -90.0F, 90.0F);
		}
	}

	public void updateMotion() {
		if (this.insideWater) {
			this.setMotion(this.getMotion().scale(0.95D));
		} else {
			this.setMotion(this.getMotion().scale(0.99D));
		}

		if (Math.abs(this.getMotion().x) < 0.001D) {
			this.setMotion(new Vector3d(0.0D, this.getMotion().y, this.getMotion().z));
		}
		if (Math.abs(this.getMotion().y) < 0.001D) {
			this.setMotion(new Vector3d(this.getMotion().x, 0.0D, this.getMotion().z));
		}
		if (Math.abs(this.getMotion().z) < 0.001D) {
			this.setMotion(new Vector3d(this.getMotion().x, this.getMotion().y, 0.0D));
		}

		if (!this.insideWater) {
			this.setMotion(this.getMotion().subtract(0.0D, 0.015D, 0.0D));
		}

		if (this.getControllingPassenger() instanceof PlayerEntity && this.isPlayerSteering() && this.hasEnergy()) {
			if (!this.world.isRemote) {
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

				Vector3d vec = BetterDivingHelper.getSeamothMoveVec(forward, up, strafe, speed, this.rotationYaw, this.rotationPitch);
				this.setMotion(this.getMotion().add(vec));
			}
		}
	}

	public boolean isPlayerSteering() {
		return this.inputForward != this.inputBack || this.inputLeft != this.inputRight || this.inputUp != this.inputDown;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return true;
	}

	@Override
	public void onEnterBubbleColumn(boolean downwards) {
		// do nothing
	}

	@Override
	public void onEnterBubbleColumnWithAirAbove(boolean downwards) {
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
		if (!this.world.isRemote && this.getControllingPassenger() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) this.getControllingPassenger();
			BetterDiving.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SPacketSyncSeamothPowerCell(this));
		}
	}

	public void syncEnergy() {
		if (!this.world.isRemote && this.getControllingPassenger() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) this.getControllingPassenger();
			BetterDiving.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SPacketSyncSeamothEnergy(this));
		}
	}

}
