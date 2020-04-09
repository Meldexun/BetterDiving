package meldexun.better_diving.capability.diving;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.item.oxygen.CapabilityOxygenProvider;
import meldexun.better_diving.capability.item.oxygen.ICapabilityOxygen;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.integration.MatterOverdrive;
import meldexun.better_diving.integration.Metamorph;
import meldexun.better_diving.integration.Vampirism;
import meldexun.better_diving.item.AbstractItemDivingGear;
import meldexun.better_diving.item.ItemSeaglide;
import meldexun.better_diving.network.packet.CPacketSyncPlayerInput;
import meldexun.better_diving.network.packet.SPacketSyncOxygen;
import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.EntityHelper;
import meldexun.better_diving.util.MovementHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CapabilityDivingAttributes implements ICapabilityDivingAttributes {

	private final EntityPlayer player;

	private int oxygen = 0;

	private boolean isDiving = false;
	private boolean prevIsDiving = false;
	private float divingTick;
	private float prevDivingTick;
	private float divingTickHorizontal;
	private float prevDivingTickHorizontal;
	private float divingTickVertical;
	private float prevDivingTickVertical;

	public CapabilityDivingAttributes() {
		this(null);
	}

	public CapabilityDivingAttributes(EntityPlayer player) {
		this.player = player;
	}

	@SideOnly(Side.CLIENT)
	public void handleDiving() {
		GameSettings settings = Minecraft.getMinecraft().gameSettings;

		this.prevIsDiving = this.isDiving;
		if (this.player.capabilities.isFlying || this.player.isElytraFlying()) {
			this.isDiving = false;
		} else if (this.prevIsDiving) {
			if (this.player.isRiding()) {
				this.isDiving = false;
			} else if (this.player.isInsideOfMaterial(Material.WATER)) {
				this.isDiving = settings.keyBindForward.isKeyDown() && !settings.keyBindBack.isKeyDown();
			} else {
				Vec3d vec = this.player.getPositionVector();
				AxisAlignedBB aabb = new AxisAlignedBB(vec.x, vec.y, vec.z, vec.x + 0.6D, vec.y + 1.8D, vec.z + 0.6D);
				this.isDiving = this.player.world.collidesWithAnyBlock(aabb);
			}
		} else {
			this.isDiving = !this.player.isRiding() && this.player.isInsideOfMaterial(Material.WATER) && settings.keyBindForward.isKeyDown() && !settings.keyBindBack.isKeyDown() && settings.keyBindSprint.isKeyDown();
		}

		this.prevDivingTick = this.divingTick;
		this.prevDivingTickHorizontal = this.divingTickHorizontal;
		this.prevDivingTickVertical = this.divingTickVertical;
		if (this.isDiving) {
			this.divingTick = Math.min(this.divingTick + 0.1F, 1.0F);

			if (settings.keyBindLeft.isKeyDown() && !settings.keyBindRight.isKeyDown()) {
				this.divingTickHorizontal = Math.min(this.divingTickHorizontal + 0.1F, 1.0F);
			} else if (!settings.keyBindLeft.isKeyDown() && settings.keyBindRight.isKeyDown()) {
				this.divingTickHorizontal = Math.max(this.divingTickHorizontal - 0.1F, -1.0F);
			} else {
				if (this.divingTickHorizontal < 0.0F) {
					this.divingTickHorizontal = Math.min(this.divingTickHorizontal + 0.1F, 0.0F);
				} else if (this.divingTickHorizontal > 0.0F) {
					this.divingTickHorizontal = Math.max(this.divingTickHorizontal - 0.1F, 0.0F);
				} else {
					this.divingTickHorizontal = 0.0F;
				}
			}

			if (settings.keyBindJump.isKeyDown() && !settings.keyBindSneak.isKeyDown()) {
				this.divingTickVertical = Math.min(this.divingTickVertical + 0.1F, 1.0F);
			} else if (!settings.keyBindJump.isKeyDown() && settings.keyBindSneak.isKeyDown()) {
				this.divingTickVertical = Math.max(this.divingTickVertical - 0.1F, -1.0F);
			} else {
				if (this.divingTickVertical < 0.0F) {
					this.divingTickVertical = Math.min(this.divingTickVertical + 0.1F, 0.0F);
				} else if (this.divingTickVertical > 0.0F) {
					this.divingTickVertical = Math.max(this.divingTickVertical - 0.1F, 0.0F);
				} else {
					this.divingTickVertical = 0.0F;
				}
			}
		} else {
			this.divingTick = Math.max(this.divingTick - 0.1F, 0.0F);
			if (this.divingTickHorizontal < 0.0F) {
				this.divingTickHorizontal = Math.min(this.divingTickHorizontal + 0.1F, 0.0F);
			} else if (this.divingTickHorizontal > 0.0F) {
				this.divingTickHorizontal = Math.max(this.divingTickHorizontal - 0.1F, 0.0F);
			} else {
				this.divingTickHorizontal = 0.0F;
			}
			if (this.divingTickVertical < 0.0F) {
				this.divingTickVertical = Math.min(this.divingTickVertical + 0.1F, 0.0F);
			} else if (this.divingTickVertical > 0.0F) {
				this.divingTickVertical = Math.max(this.divingTickVertical - 0.1F, 0.0F);
			} else {
				this.divingTickVertical = 0.0F;
			}
		}

		BetterDiving.network.sendToServer(new CPacketSyncPlayerInput(this.isDiving, this.divingTick, this.divingTickHorizontal, this.divingTickVertical));
	}

	@Override
	public void tick() {
		if (this.player.world.isRemote) {
			this.handleDiving();
			this.handleMovement();
			if (!BetterDivingConfig.getInstance().general.oxygenSyncPackets) {
				this.handleOxygen();
			}
		} else {
			this.handleOxygen();
			if (BetterDivingConfig.getInstance().general.oxygenSyncPackets) {
				BetterDiving.network.sendTo(new SPacketSyncOxygen(this.player), (EntityPlayerMP) this.player);
			}
		}
	}

	protected void handleMovement() {
		if (BetterDivingConfig.getInstance().modules.divingMovement && this.player.isInWater() && !this.player.capabilities.isFlying && !this.player.isRiding()) {
			GameSettings settings = Minecraft.getMinecraft().gameSettings;

			boolean inputForward = settings.keyBindForward.isKeyDown();
			boolean inputBack = settings.keyBindBack.isKeyDown();
			boolean inputRight = settings.keyBindRight.isKeyDown();
			boolean inputLeft = settings.keyBindLeft.isKeyDown();
			boolean inputUp = settings.keyBindJump.isKeyDown();
			boolean inputDown = settings.keyBindSneak.isKeyDown();

			float rotationPitch = this.player.rotationPitch;
			float rotationYaw = this.player.rotationYaw;

			double slow = this.calculateSlow();
			double speed = this.getSwimSpeedFromPlayer();

			double strafe = 0.0D;
			double forward = 0.0D;
			double up = 0.0D;
			if (inputForward) {
				forward++;
			}
			if (inputBack) {
				forward--;
			}
			if (inputRight) {
				strafe--;
			}
			if (inputLeft) {
				strafe++;
			}
			if (inputUp) {
				up++;
			}
			if (inputDown) {
				up--;
			}

			if (!BetterDivingConfig.getInstance().general.vanillaDivingMovement) {
				this.player.motionY += 0.02D;

				if (inputDown) {
					slow *= 0.3D;
				}

				if (inputUp) {
					this.player.motionY -= 0.03999999910593033D;
				}

				if (inputForward != inputBack || inputRight != inputLeft) {
					MovementHelper.move2D(this.player, strafe, forward, -slow, rotationYaw);
				}

				if (inputForward != inputBack || inputRight != inputLeft || inputUp != inputDown) {
					if (inputForward && !inputBack) {
						if (inputUp && !inputDown) {
							rotationPitch = (rotationPitch - 90.0F) / 2.0F;
							up = 0;
						} else if (inputDown && !inputUp) {
							rotationPitch = (rotationPitch + 90.0F) / 2.0F;
							up = 0;
						}
					} else if (inputBack && !inputForward) {
						if (inputUp && !inputDown) {
							rotationPitch = (rotationPitch + 90.0F) / 2.0F;
							up = 0;
						} else if (inputDown && !inputUp) {
							rotationPitch = (rotationPitch - 90.0F) / 2.0F;
							up = 0;
						}
					}

					if (ItemSeaglide.canUseSeaglide(this.player) && !inputForward) {
						speed *= 0.4D;
					}

					this.move3D(this.player, strafe, up, forward, speed, rotationYaw, rotationPitch);
				}
			} else {
				this.player.motionY += 0.015D;

				if ((settings.keyBindSprint.isKeyDown() || this.player.isSprinting()) && inputForward && !inputBack) {
					this.player.motionY += 0.005D;

					if (inputDown) {
						slow *= 0.3D;
					}

					if (inputUp) {
						this.player.motionY -= 0.03999999910593033D;
					}

					MovementHelper.move2D(this.player, strafe, forward, -slow, rotationYaw);

					if (inputUp && !inputDown) {
						rotationPitch = (rotationPitch - 90.0F) / 2.0F;
						up = 0;
					} else if (inputDown && !inputUp) {
						rotationPitch = (rotationPitch + 90.0F) / 2.0F;
						up = 0;
					}

					this.move3D(this.player, strafe, up, forward, speed, rotationYaw, rotationPitch);
				} else if (inputDown) {
					this.player.motionY -= 0.03999999910593033D;
					slow *= 0.7D;

					MovementHelper.move2D(this.player, strafe, forward, slow, rotationYaw);
				}
			}
		}
	}

	protected double calculateSlow() {
		double slow = 0.02D;
		ItemStack feet = this.player.inventory.armorInventory.get(0);
		double depthStrider = (double) EnchantmentHelper.getEnchantmentLevel(Enchantments.DEPTH_STRIDER, feet);

		if (depthStrider > 0.0D) {
			if (depthStrider > 3.0D) {
				depthStrider = 3.0D;
			}
			if (!this.player.onGround) {
				depthStrider *= 0.5D;
			}
			slow += (this.player.getAIMoveSpeed() - slow) * depthStrider / 3.0D;
		}

		return slow * 0.98D;
	}

	protected void move3D(EntityPlayer player, double strafe, double up, double forward, double speed, double yaw, double pitch) {
		double d = strafe * strafe + up * up + forward * forward;
		if (d >= 1.0E-4D) {
			d = Math.sqrt(d);
			if (d < 1.0D) {
				d = 1.0D;
			}
			d = speed / d;

			strafe *= d;
			up *= d;
			forward *= d;

			double d1 = Math.sin(yaw * 0.017453292D);
			double d2 = Math.cos(yaw * 0.017453292D);
			double d3 = Math.sin(pitch * 0.017453292D);
			double d4 = Math.cos(pitch * 0.017453292D);

			double depthStriderFactor = 1.0D;
			ItemStack feet = player.inventory.armorInventory.get(0);
			double depthStrider = EnchantmentHelper.getEnchantmentLevel(Enchantments.DEPTH_STRIDER, feet);
			if (depthStrider > 0.0D) {
				if (depthStrider > 3.0D) {
					depthStrider = 3.0D;
				}
				if (!player.onGround) {
					depthStrider *= 0.5D;
				}
				depthStriderFactor += 1.2699997 / 3.0D * depthStrider;
			}

			player.motionX += (strafe * d2 - forward * d1 * d4) * depthStriderFactor;
			player.motionY += up - forward * d3;
			player.motionZ += (forward * d2 * d4 + strafe * d1) * depthStriderFactor;
		}
	}

	protected void handleOxygen() {
		if (BetterDivingConfig.getInstance().modules.oxygenHandling) {
			int airUsage = 0;
			if (this.player.isInsideOfMaterial(Material.WATER) && !this.player.canBreatheUnderwater() && !this.player.isPotionActive(MobEffects.WATER_BREATHING) && !this.player.capabilities.disableDamage
					&& !(this.player.getRidingEntity() instanceof EntitySeamoth) && !Metamorph.hasWaterBreathing(this.player) && !Vampirism.hasWaterBreathing(this.player) && !MatterOverdrive.hasWaterBreathing(this.player)) {
				airUsage -= 1;
				if (BetterDivingConfig.getInstance().divingValues.airEfficiency) {
					ItemStack helm = this.player.inventory.armorInventory.get(3);
					if (!(helm.getItem() instanceof AbstractItemDivingGear) || !((AbstractItemDivingGear) helm.getItem()).isImproved()) {
						airUsage -= EntityHelper.blocksUnderWater(this.player) / BetterDivingConfig.getInstance().divingValues.airEfficiencyLimit;
					}
				}
			} else {
				airUsage += 25;
			}

			if (airUsage < 0) {
				this.extractOxygenFromPlayer(-airUsage);
			} else if (airUsage > 0) {
				this.receiveOxygenFromPlayer(airUsage);
			}

			this.player.setAir(this.getOxygenFromPlayer() > 0 ? (int) (this.getOxygenFromPlayerInPercent() * 300.0D) : 0);

			if (!this.player.world.isRemote && this.oxygen <= -20) {
				this.oxygen = 0;

				((WorldServer) this.player.world).spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.player.posX, this.player.posY + this.player.height * 0.5D, this.player.posZ, 8, 0.25D, 0.25D, 0.25D, 0.0D);

				this.player.attackEntityFrom(DamageSource.DROWN, 2.0F);
			}
		}
	}

	@Override
	public void setOxygen(int oxygen) {
		this.oxygen = oxygen;
	}

	@Override
	public int getOxygen() {
		return this.oxygen;
	}

	@Override
	public int getOxygenFromPlayer() {
		int oxygenFromPlayer = this.getOxygen();

		ItemStack helm = this.player.inventory.armorInventory.get(3);
		ItemStack chest = this.player.inventory.armorInventory.get(2);

		if (helm.getItem() instanceof AbstractItemDivingGear && chest.getItem() instanceof AbstractItemDivingGear) {
			ICapabilityOxygen ioxygen = chest.getCapability(CapabilityOxygenProvider.OXYGEN, null);
			oxygenFromPlayer += ioxygen.getOxygen();
		}

		return oxygenFromPlayer;
	}

	@Override
	public double getOxygenFromPlayerInPercent() {
		return (double) this.getOxygenFromPlayer() / (double) this.getOxygenCapacityFromPlayer();
	}

	@Override
	public int receiveOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.getOxygenCapacity() - this.oxygen);
		this.oxygen += amount;
		return amount;
	}

	@Override
	public int receiveOxygenFromPlayer(int amount) {
		if (amount > 0) {
			int amountReceived = this.receiveOxygen(amount);
			int amountToReceive = amount - amountReceived;

			if (amountToReceive > 0) {
				ItemStack helm = this.player.inventory.armorInventory.get(3);
				ItemStack chest = this.player.inventory.armorInventory.get(2);

				if (helm.getItem() instanceof AbstractItemDivingGear && chest.getItem() instanceof AbstractItemDivingGear) {
					ICapabilityOxygen ioxygen = chest.getCapability(CapabilityOxygenProvider.OXYGEN, null);
					amountReceived += ioxygen.receiveOxygen(amountToReceive);
				}
			}

			return amountReceived;
		}
		return 0;
	}

	@Override
	public int extractOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.oxygen + 20);
		this.oxygen -= amount;
		return amount;
	}

	@Override
	public int extractOxygenFromPlayer(int amount) {
		if (amount > 0) {
			int amountExtracted = 0;

			ItemStack helm = this.player.inventory.armorInventory.get(3);
			ItemStack chest = this.player.inventory.armorInventory.get(2);

			if (helm.getItem() instanceof AbstractItemDivingGear && chest.getItem() instanceof AbstractItemDivingGear) {
				ICapabilityOxygen ioxygen = chest.getCapability(CapabilityOxygenProvider.OXYGEN, null);
				amountExtracted += ioxygen.extractOxygen(amount);
			}

			int amountToExtract = amount - amountExtracted;

			if (amountToExtract > 0) {
				amountExtracted += this.extractOxygen(amountToExtract);
			}

			return amountExtracted;
		}
		return 0;
	}

	@Override
	public int getOxygenCapacity() {
		return BetterDivingConfig.getInstance().divingValues.airBase;
	}

	@Override
	public int getOxygenCapacityFromPlayer() {
		int oxygenCapacity = this.getOxygenCapacity();

		ItemStack helm = this.player.inventory.armorInventory.get(3);
		ItemStack chest = this.player.inventory.armorInventory.get(2);

		if (helm.getItem() instanceof AbstractItemDivingGear && chest.getItem() instanceof AbstractItemDivingGear) {
			ICapabilityOxygen ioxygen = chest.getCapability(CapabilityOxygenProvider.OXYGEN, null);
			oxygenCapacity += ioxygen.getOxygenCapacity();
		}

		int respirationLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.RESPIRATION, helm);
		if (respirationLevel > 0) {
			oxygenCapacity += BetterDivingConfig.getInstance().divingValues.airPerRespirationLevel * respirationLevel;
		}

		return oxygenCapacity;
	}

	@Override
	public double getSwimSpeed() {
		return BetterDivingConfig.getInstance().divingValues.swimSpeed;
	}

	@Override
	public double getSwimSpeedFromPlayer() {
		if (ItemSeaglide.canUseSeaglide(this.player)) {
			return BetterDivingConfig.getInstance().divingValues.seaglideSpeed;
		}

		double swimSpeedBase = this.getSwimSpeed();
		double swimSpeedBonus = 0.0D;

		ItemStack chest = this.player.inventory.armorInventory.get(2);
		ItemStack feet = this.player.inventory.armorInventory.get(0);

		if (chest.getItem() instanceof AbstractItemDivingGear) {
			swimSpeedBonus += ((AbstractItemDivingGear) chest.getItem()).getSwimSpeed();
		}
		if (feet.getItem() instanceof AbstractItemDivingGear) {
			swimSpeedBonus += ((AbstractItemDivingGear) feet.getItem()).getSwimSpeed();
		}
		if (!this.player.getHeldItemMainhand().isEmpty()) {
			swimSpeedBonus -= 0.08D;
		}
		if (!this.player.getHeldItemOffhand().isEmpty()) {
			swimSpeedBonus -= 0.08D;
		}
		if (!this.player.isCreative()) {
			double hunger = (double) this.player.getFoodStats().getFoodLevel() / 20.0D;
			if (hunger < 0.2D) {
				swimSpeedBonus += 2.5D * hunger - 0.5D;
			}
		}
		if (!this.player.isInsideOfMaterial(Material.WATER)) {
			swimSpeedBase *= 1.3D;
		}

		double min = swimSpeedBase * BetterDivingConfig.getInstance().divingValues.swimSpeedLimitLower;
		double max = swimSpeedBase * BetterDivingConfig.getInstance().divingValues.swimSpeedLimitUpper;
		double speed = MathHelper.clamp(swimSpeedBase * (1.0D + swimSpeedBonus), min, max);

		int depthStriderLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.DEPTH_STRIDER, feet);
		if (depthStriderLevel > 0) {
			speed += swimSpeedBase * BetterDivingConfig.getInstance().divingValues.swimSpeedDepthStrider * depthStriderLevel;
		}

		return speed;
	}

	@Override
	public float getBreakSpeed() {
		return (float) BetterDivingConfig.getInstance().divingValues.breakSpeed;
	}

	@Override
	public float getBreakSpeedFromPlayer() {
		float breakSpeed = this.getBreakSpeed();

		ItemStack helm = this.player.inventory.armorInventory.get(3);
		ItemStack legs = this.player.inventory.armorInventory.get(1);

		if (legs.getItem() instanceof AbstractItemDivingGear) {
			breakSpeed *= 1.0F + ((AbstractItemDivingGear) legs.getItem()).getBreakSpeed();
		}

		if (this.player.isInsideOfMaterial(Material.WATER)) {
			int aquaAffinityLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.AQUA_AFFINITY, helm);
			if (aquaAffinityLevel > 0) {
				breakSpeed *= 1.0F + (float) BetterDivingConfig.getInstance().divingValues.breakSpeedAquaAffinity * (float) aquaAffinityLevel;
			} else {
				breakSpeed *= 5.0F;
			}
		}

		if (!this.player.onGround) {
			breakSpeed *= 5.0F;
		}

		return breakSpeed;
	}

	@Override
	public void setIsDiving(boolean isDiving) {
		this.isDiving = isDiving;
	}

	@Override
	public boolean isDiving() {
		return this.isDiving;
	}

	@Override
	public void setPrevIsDiving(boolean prevIsDiving) {
		this.prevIsDiving = prevIsDiving;
	}

	@Override
	public boolean prevIsDiving() {
		return this.prevIsDiving;
	}

	@Override
	public void setDivingTick(float divingTick) {
		this.divingTick = divingTick;
	}

	@Override
	public float getDivingTick() {
		return this.divingTick;
	}

	@Override
	public void setPrevDivingTick(float prevDivingTick) {
		this.prevDivingTick = prevDivingTick;
	}

	@Override
	public float getPrevDivingTick() {
		return this.prevDivingTick;
	}

	@Override
	public void setDivingTickHorizontal(float divingTickHorizontal) {
		this.divingTickHorizontal = divingTickHorizontal;
	}

	@Override
	public float getDivingTickHorizontal() {
		return this.divingTickHorizontal;
	}

	@Override
	public void setPrevDivingTickHorizontal(float prevDivingTickHorizontal) {
		this.prevDivingTickHorizontal = prevDivingTickHorizontal;
	}

	@Override
	public float getPrevDivingTickHorizontal() {
		return this.prevDivingTickHorizontal;
	}

	@Override
	public void setDivingTickVertical(float divingTickVertical) {
		this.divingTickVertical = divingTickVertical;
	}

	@Override
	public float getDivingTickVertical() {
		return this.divingTickVertical;
	}

	@Override
	public void setPrevDivingTickVertical(float prevDivingTickVertical) {
		this.prevDivingTickVertical = prevDivingTickVertical;
	}

	@Override
	public float getPrevDivingTickVertical() {
		return this.prevDivingTickVertical;
	}

}
