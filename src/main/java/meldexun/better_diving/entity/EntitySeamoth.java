package meldexun.better_diving.entity;

import java.util.List;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.audio.SeamothEngineLoopSound;
import meldexun.better_diving.init.ModItems;
import meldexun.better_diving.init.ModSounds;
import meldexun.better_diving.item.ItemPowerCell;
import meldexun.better_diving.network.GuiHandler;
import meldexun.better_diving.network.packet.CPacketSyncSeamoth;
import meldexun.better_diving.network.packet.SPacketSyncSeamothBattery;
import meldexun.better_diving.network.packet.SPacketSyncSeamothEnergy;
import meldexun.better_diving.proxy.ClientProxy;
import meldexun.better_diving.util.BetterDivingConfigClient;
import meldexun.better_diving.util.MovementHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class EntitySeamoth extends Entity implements IEntityAdditionalSpawnData {

	private int damage = 0;

	private boolean prevControlled = false;

	public boolean inputForward = false;
	public boolean inputRight = false;
	public boolean inputBack = false;
	public boolean inputLeft = false;
	public boolean inputUp = false;
	public boolean inputDown = false;

	private int motionTick = 0;
	private int energyCounter = 0;

	public float yaw = 0;
	public float pitch = 0;
	public float partialTicks = 0;

	@SideOnly(Side.CLIENT)
	public SeamothEngineLoopSound engineLoopSound;

	public EntitySeamoth(World worldIn) {
		super(worldIn);
		this.setSize(1.8F, 1.8F);
		this.isImmuneToFire = true;
	}

	@SideOnly(Side.CLIENT)
	public void updateEngineSound() {
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.gameSettings.getSoundLevel(SoundCategory.MASTER) > 0.0F) {
			SoundHandler soundHandler = mc.getSoundHandler();

			if (!soundHandler.isSoundPlaying(this.engineLoopSound)) {
				this.engineLoopSound = new SeamothEngineLoopSound(this);
				soundHandler.playSound(this.engineLoopSound);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void updateControls() {
		Minecraft mc = Minecraft.getMinecraft();

		if (this.getControllingPassenger() == mc.player) {
			GameSettings settings = mc.gameSettings;

			this.inputForward = settings.keyBindForward.isKeyDown();
			this.inputRight = settings.keyBindRight.isKeyDown();
			this.inputBack = settings.keyBindBack.isKeyDown();
			this.inputLeft = settings.keyBindLeft.isKeyDown();
			this.inputUp = settings.keyBindJump.isKeyDown();
			this.inputDown = ClientProxy.keyBindSeamothDescend.isKeyDown();

			BetterDiving.CONNECTION.sendToServer(new CPacketSyncSeamoth(this));

			this.prevControlled = true;
		} else if (this.prevControlled) {
			this.inputForward = false;
			this.inputRight = false;
			this.inputBack = false;
			this.inputLeft = false;
			this.inputUp = false;
			this.inputDown = false;

			BetterDiving.CONNECTION.sendToServer(new CPacketSyncSeamoth(this));

			this.prevControlled = false;
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		this.onGround = false;

		if (this.damage > 0) {
			this.damage--;
		}

		if (this.world.isRemote) {
			this.updateEngineSound();
			this.updateControls();
		}

		this.updateMotion();

		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox() {
		return this.getEntityBoundingBox();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!this.world.isRemote && source.getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) source.getTrueSource();

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
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if (player.isSneaking()) {
			if (!this.world.isRemote) {
				player.openGui(BetterDiving.MOD_ID, GuiHandler.GUI_SEAMOTH_ENTITY, this.world, this.getEntityId(), 0, 0);
			}
			return true;
		} else if (!this.isBeingRidden()) {
			if (!this.world.isRemote) {
				player.rotationYaw = this.rotationYaw;
				player.rotationPitch = this.rotationPitch;
				player.startRiding(this);
				this.syncBattery();
				this.world.playSound(null, this.posX, this.posY, this.posZ, ModSounds.SEAMOTH_ENTER, SoundCategory.NEUTRAL, 0.6F, 1.0F);
			}
			return true;
		}
		return false;
	}

	@Override
	public double getMountedYOffset() {
		return 0.0D;
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}

	@Override
	public boolean shouldDismountInWater(Entity rider) {
		return false;
	}

	@Override
	@Nullable
	public Entity getControllingPassenger() {
		List<Entity> list = this.getPassengers();
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void applyOrientationToEntity(Entity entityToUpdate) {
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;

		this.updateRotation();

		entityToUpdate.prevRotationYaw = this.rotationYaw;
		entityToUpdate.rotationYaw = this.rotationYaw;
		entityToUpdate.prevRotationPitch = this.rotationPitch;
		entityToUpdate.rotationPitch = this.rotationPitch;
		if (entityToUpdate instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) entityToUpdate;
			entity.prevRotationYawHead = this.rotationYaw;
			entity.rotationYawHead = this.rotationYaw;
			entity.prevRenderYawOffset = this.rotationYaw;
			entity.renderYawOffset = this.rotationYaw;
		}
	}

	@Override
	protected void entityInit() {

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {

	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		ByteBufUtils.writeItemStack(buffer, this.getBattery());
		buffer.writeInt(this.getEnergy());
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.setBattery(ByteBufUtils.readItemStack(additionalData));
		this.setEnergy(additionalData.readInt());
	}

	@SideOnly(Side.CLIENT)
	public void updateRotation() {
		Minecraft mc = Minecraft.getMinecraft();
		float f = mc.getRenderPartialTicks() - this.partialTicks;
		if (f < 0.0F) {
			f++;
		}
		this.partialTicks = mc.getRenderPartialTicks();

		if (this.inWater && this.getEnergy() > 0) {
			double d = (double) mc.gameSettings.mouseSensitivity * 0.6D + 0.2D;
			double d1 = d * d * d * 8.0D;
			double d2 = MathHelper.clamp((double) (mc.mouseHelper.deltaX) * d1 * 0.035D, -8.0D * (double) f, 8.0D * (double) f);
			double d3 = MathHelper.clamp((double) (-mc.mouseHelper.deltaY) * d1 * 0.035D, -8.0D * (double) f, 8.0D * (double) f);
			if (mc.gameSettings.invertMouse) {
				d3 *= -1.0D;
			}

			this.rotationYaw += (float) d2;
			this.rotationPitch += (float) d3;
			this.yaw += (float) d2;
			this.pitch += (float) d3;
		}

		if (Math.abs(this.yaw) > 0.01F) {
			this.rotationYaw += this.yaw * 0.5F * f;
			this.yaw *= 1.0F - 0.25F * f;
		} else {
			this.yaw = 0.0F;
		}
		if (Math.abs(this.pitch) > 0.01F) {
			this.rotationPitch += this.pitch * 0.5F * f;
			this.pitch *= 1.0F - 0.25F * f;
		} else {
			this.pitch = 0.0F;
		}

		// this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
		this.rotationPitch = MathHelper.clamp(this.rotationPitch, -90.0F, 90.0F);
	}

	public void updateMotion() {
		if (this.inWater) {
			this.motionX *= 0.925D;
			this.motionY *= 0.925D;
			this.motionZ *= 0.925D;
		} else {
			this.motionX *= 0.9625D;
			this.motionY *= 0.9625D;
			this.motionZ *= 0.9625D;
		}

		if (Math.abs(this.motionX) < 0.000001D) {
			this.motionX = 0.0D;
		}
		if (Math.abs(this.motionY) < 0.000001D) {
			this.motionY = 0.0D;
		}
		if (Math.abs(this.motionZ) < 0.000001D) {
			this.motionZ = 0.0D;
		}

		if (!this.inWater) {
			this.motionY -= 0.016D;
		}

		if (this.getControllingPassenger() instanceof EntityPlayer && this.isPlayerSteering() && this.getEnergy() > 0) {
			if (this.world.isRemote) {
				if (!BetterDivingConfigClient.seamothEnergySyncPackets) {
					this.energyCounter++;
					if (this.energyCounter >= 20) {
						this.energyCounter = 0;
						this.extractEnergy(BetterDivingConfigClient.seamothEnergyUsage);
					}
				}
			} else {
				this.energyCounter++;
				if (this.energyCounter >= 20) {
					this.energyCounter = 0;
					this.extractEnergy(BetterDivingConfigClient.seamothEnergyUsage);
				}
				if (BetterDivingConfigClient.seamothEnergySyncPackets) {
					this.syncEnergy();
				}
			}

			if (this.motionTick < 40) {
				this.motionTick++;
			}

			if (this.inWater) {
				double d = BetterDivingConfigClient.seamothSpeed / 60.0D * (20.0D + this.motionTick);

				float rotationYaw = this.rotationYaw;
				float rotationPitch = this.rotationPitch;

				float strafe = 0.0F;
				float forward = 0.0F;
				float up = 0.0F;
				if (this.inputForward) {
					forward += 1.0F;
				}
				if (this.inputBack) {
					forward -= 1.0F;
				}
				if (this.inputRight) {
					strafe -= 1.0F;
				}
				if (this.inputLeft) {
					strafe += 1.0F;
				}
				if (this.inputUp) {
					up += 1.0F;
				}
				if (this.inputDown) {
					up -= 1.0F;
				}

				if (this.inputForward && !this.inputBack) {
					if (this.inputUp && !this.inputDown) {
						rotationPitch = (rotationPitch - 90.0F) / 2.0F;
						up = 0.0F;
					} else if (this.inputDown && !this.inputUp) {
						rotationPitch = (rotationPitch + 90.0F) / 2.0F;
						up = 0.0F;
					}
				} else if (this.inputBack && !this.inputForward) {
					if (this.inputUp && !this.inputDown) {
						rotationPitch = (rotationPitch + 90.0F) / 2.0F;
						up = 0.0F;
					} else if (this.inputDown && !this.inputUp) {
						rotationPitch = (rotationPitch - 90.0F) / 2.0F;
						up = 0.0F;
					}
				}

				MovementHelper.move3D(this, strafe, up, forward, d, rotationYaw, rotationPitch);
			}
		} else {
			if (this.motionTick > 0) {
				this.motionTick--;
			}
		}
	}

	public boolean isPlayerSteering() {
		return this.inputForward != this.inputBack || this.inputLeft != this.inputRight || this.inputUp != this.inputDown;
	}

	public ItemStack toItemStack() {
		ItemStack stack = new ItemStack(ModItems.SEAMOTH);
		ItemStackHandler itemStackHandler = (ItemStackHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		itemStackHandler.setStackInSlot(0, this.getBattery().copy());
		return stack;
	}

	public ItemStack getBattery() {
		return this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
	}

	public void setBattery(ItemStack stack) {
		if (stack.getItem() instanceof ItemPowerCell) {
			ItemStackHandler itemStackHandler = (ItemStackHandler) this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			itemStackHandler.setStackInSlot(0, stack);
		}
	}

	public boolean hasEnergy() {
		ItemStack powerCell = this.getBattery();
		if (powerCell.getItem() instanceof ItemPowerCell) {
			return ItemPowerCell.hasEnergy(powerCell);
		}
		return false;
	}

	public int getEnergy() {
		ItemStack powerCell = this.getBattery();
		if (powerCell.getItem() instanceof ItemPowerCell) {
			return ItemPowerCell.getEnergy(powerCell);
		}
		return 0;
	}

	public boolean setEnergy(int energy) {
		ItemStack powerCell = this.getBattery();
		if (powerCell.getItem() instanceof ItemPowerCell) {
			return ItemPowerCell.setEnergy(powerCell, energy);
		}
		return false;
	}

	public int getEnergyCapacity() {
		ItemStack powerCell = this.getBattery();
		if (powerCell.getItem() instanceof ItemPowerCell) {
			return ItemPowerCell.getEnergyCapacity(powerCell);
		}
		return 0;
	}

	public int receiveEnergy(int amount) {
		if (amount > 0) {
			ItemStack powerCell = this.getBattery();
			if (powerCell.getItem() instanceof ItemPowerCell) {
				return ItemPowerCell.receiveEnergy(powerCell, amount);
			}
		}
		return 0;
	}

	public int extractEnergy(int amount) {
		if (amount > 0) {
			ItemStack powerCell = this.getBattery();
			if (powerCell.getItem() instanceof ItemPowerCell) {
				return ItemPowerCell.extractEnergy(powerCell, amount);
			}
		}
		return 0;
	}

	public void syncBattery() {
		if (!this.world.isRemote && this.getControllingPassenger() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) this.getControllingPassenger();
			BetterDiving.CONNECTION.sendTo(new SPacketSyncSeamothBattery(this), (EntityPlayerMP) player);
		}
	}

	public void syncEnergy() {
		if (!this.world.isRemote && this.getControllingPassenger() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) this.getControllingPassenger();
			BetterDiving.CONNECTION.sendTo(new SPacketSyncSeamothEnergy(this), (EntityPlayerMP) player);
		}
	}

}
