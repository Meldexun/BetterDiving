package meldexun.better_diving.capability.diving;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.oxygen.CapabilityOxygenProvider;
import meldexun.better_diving.capability.oxygen.ICapabilityOxygen;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.integration.Vampirism;
import meldexun.better_diving.item.AbstractItemDivingGear;
import meldexun.better_diving.network.packet.SPacketSyncDivingCapability;
import meldexun.better_diving.network.packet.SPacketSyncOxygen;
import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.BetterDivingConfigClient;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;

public class CapabilityDivingAttributes implements ICapabilityDivingAttributes {

	private int oxygen = 0;
	private int prevOxygen = 0;
	private int oxygenCapacity = 0;

	private double swimSpeedBase = 0.0D;
	private double swimSpeedBonus = 0.0D;

	private float breakSpeed = 0.0F;

	@Override
	public void changeEquip(EntityPlayer player) {
		if (!player.world.isRemote) {
			ItemStack helm = player.inventory.armorInventory.get(3);
			ItemStack chest = player.inventory.armorInventory.get(2);
			ItemStack legs = player.inventory.armorInventory.get(1);
			ItemStack feet = player.inventory.armorInventory.get(0);
			int respirationLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.RESPIRATION, helm);
			int depthStriderLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.DEPTH_STRIDER, feet);
			int aquaAffinityLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.AQUA_AFFINITY, helm);

			int oxygenCapacity = BetterDivingConfig.DIVING_VALUES.airBase;
			double swimBaseSpeed = BetterDivingConfig.DIVING_VALUES.swimSpeed;
			double swimBonusSpeed = 0.0D;
			float breakSpeed = (float) BetterDivingConfig.DIVING_VALUES.breakSpeed;

			if (chest.getItem() instanceof AbstractItemDivingGear) {
				swimBonusSpeed += ((AbstractItemDivingGear) chest.getItem()).swimSpeed;
			}
			if (legs.getItem() instanceof AbstractItemDivingGear) {
				breakSpeed *= 1.0F + ((AbstractItemDivingGear) legs.getItem()).breakSpeed;
			}
			if (feet.getItem() instanceof AbstractItemDivingGear) {
				swimBonusSpeed += ((AbstractItemDivingGear) feet.getItem()).swimSpeed;
			}

			if (respirationLevel > 0) {
				oxygenCapacity += BetterDivingConfig.DIVING_VALUES.airPerRespirationLevel * respirationLevel;
			}
			if (depthStriderLevel > 0) {
				swimBaseSpeed *= 1.0D + BetterDivingConfig.DIVING_VALUES.swimSpeedDepthStrider * depthStriderLevel;
			}
			if (aquaAffinityLevel == 0) {
				breakSpeed *= 5.0F;
			} else if (aquaAffinityLevel > 0) {
				breakSpeed *= 1.0F + (float) BetterDivingConfig.DIVING_VALUES.breakSpeedAquaAffinity * aquaAffinityLevel;
			}

			this.oxygenCapacity = oxygenCapacity;
			this.swimSpeedBase = swimBaseSpeed;
			this.swimSpeedBonus = swimBonusSpeed;
			this.breakSpeed = breakSpeed;

			BetterDiving.CONNECTION.sendTo(new SPacketSyncDivingCapability(player), (EntityPlayerMP) player);
		}
	}

	@Override
	public void tick(EntityPlayer player) {
		if (player.world.isRemote) {
			this.handleMovement(player);
			if (!BetterDivingConfigClient.oxygenSyncPackets) {
				this.handleOxygen(player);
			}
		} else {
			this.handleOxygen(player);
			if (BetterDivingConfigClient.oxygenSyncPackets) {
				BetterDiving.CONNECTION.sendTo(new SPacketSyncOxygen(player), (EntityPlayerMP) player);
			}
		}
	}

	protected void handleMovement(EntityPlayer player) {
		if (BetterDivingConfigClient.divingMovement && player.isInWater() && !player.capabilities.isFlying) {
			GameSettings settings = Minecraft.getMinecraft().gameSettings;

			boolean inputForward = settings.keyBindForward.isKeyDown();
			boolean inputBack = settings.keyBindBack.isKeyDown();
			boolean inputRight = settings.keyBindRight.isKeyDown();
			boolean inputLeft = settings.keyBindLeft.isKeyDown();
			boolean inputUp = settings.keyBindJump.isKeyDown();
			boolean inputDown = settings.keyBindSneak.isKeyDown();

			float rotationPitch = player.rotationPitch;
			float rotationYaw = player.rotationYaw;

			double slow = this.calculateSlow(player);
			double speed = this.calculateSpeed(player);

			int strafe = 0;
			int forward = 0;
			int up = 0;
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

			if (!BetterDivingConfigClient.vanillaDivingMovement) {
				player.motionY += 0.02D;

				if (inputDown) {
					slow *= 0.3D;
				}

				if (inputUp) {
					player.motionY -= 0.03999999910593033D;
				}

				if (inputForward != inputBack || inputRight != inputLeft) {
					MovementHelper.move2D(player, strafe, forward, -slow, rotationYaw);
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

					this.move3D(player, strafe, up, forward, speed, rotationYaw, rotationPitch);
				}
			} else {
				player.motionY += 0.015D;

				if ((settings.keyBindSprint.isKeyDown() || player.isSprinting()) && inputForward && !inputBack) {
					player.motionY += 0.005D;

					if (inputDown) {
						slow *= 0.3D;
					}

					if (inputUp) {
						player.motionY -= 0.03999999910593033D;
					}

					MovementHelper.move2D(player, strafe, forward, -slow, rotationYaw);

					if (inputUp && !inputDown) {
						rotationPitch = (rotationPitch - 90.0F) / 2.0F;
						up = 0;
					} else if (inputDown && !inputUp) {
						rotationPitch = (rotationPitch + 90.0F) / 2.0F;
						up = 0;
					}

					this.move3D(player, strafe, up, forward, speed, rotationYaw, rotationPitch);
				} else if (inputDown) {
					player.motionY -= 0.03999999910593033D;
					slow *= 0.7D;

					MovementHelper.move2D(player, strafe, forward, slow, rotationYaw);
				}
			}
		}
	}

	protected double calculateSlow(EntityPlayer player) {
		double slow = 0.02D;
		ItemStack feet = player.inventory.armorInventory.get(0);
		double depthStrider = (double) EnchantmentHelper.getEnchantmentLevel(Enchantments.DEPTH_STRIDER, feet);

		if (depthStrider > 0.0D) {
			if (depthStrider > 3.0D) {
				depthStrider = 3.0D;
			}
			if (!player.onGround) {
				depthStrider *= 0.5D;
			}
			slow += (player.getAIMoveSpeed() - slow) * depthStrider / 3.0D;
		}

		return slow * 0.98D;
	}

	protected double calculateSpeed(EntityPlayer player) {
		double baseSpeed = this.swimSpeedBase;
		double bonusSpeed = this.swimSpeedBonus;

		if (player.getHeldItemMainhand().isEmpty()) {
			bonusSpeed += 0.05D;
		}
		if (player.getHeldItemOffhand().isEmpty()) {
			bonusSpeed += 0.05D;
		}
		if (!player.isCreative()) {
			double hunger = (double) player.getFoodStats().getFoodLevel() / 20.0D;
			if (hunger < 0.2D) {
				bonusSpeed -= 0.2D - hunger;
			}
		}
		if (player.isInWater() && !player.isInsideOfMaterial(Material.WATER)) {
			bonusSpeed *= 2.0D;
		}

		double min = baseSpeed * BetterDivingConfigClient.swimSpeedLimitLower;
		double max = baseSpeed * BetterDivingConfigClient.swimSpeedLimitUpper;
		return MathHelper.clamp(baseSpeed * (1.0D + bonusSpeed), min, max);
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

	protected void handleOxygen(EntityPlayer player) {
		if (BetterDivingConfigClient.oxygenHandling) {
			this.prevOxygen = this.getOxygenFromPlayer(player);

			int airUsage = 0;
			if (player.isInsideOfMaterial(Material.WATER) && !(player.getRidingEntity() instanceof EntitySeamoth) && !player.canBreatheUnderwater() && !player.isPotionActive(MobEffects.WATER_BREATHING) && !player.capabilities.disableDamage
					&& (!Vampirism.loaded || !Vampirism.isVampire(player))) {
				airUsage -= 1;
				if (BetterDivingConfigClient.airEfficiency) {
					ItemStack helm = player.inventory.armorInventory.get(3);
					if (!(helm.getItem() instanceof AbstractItemDivingGear) || !((AbstractItemDivingGear) helm.getItem()).isImprovedGear) {
						airUsage -= EntityHelper.blocksUnderWater(player) / BetterDivingConfigClient.airEfficiencyLimit;
					}
				}
			} else {
				airUsage += 25;
			}

			if (airUsage < 0) {
				this.extractOxygenFromPlayer(player, -airUsage);
			} else if (airUsage > 0) {
				this.receiveOxygenFromPlayer(player, airUsage);
			}

			player.setAir(this.getOxygenFromPlayer(player) > 0 ? (int) (this.getOxygenPercent(player) * 300.0D) : 0);

			if (!player.world.isRemote) {
				if (this.oxygen <= -20) {
					this.oxygen = 0;

					((WorldServer) player.world).spawnParticle(EnumParticleTypes.WATER_BUBBLE, player.posX, player.posY + player.height * 0.5D, player.posZ, 8, 0.25D, 0.25D, 0.25D, 0.0D);

					player.attackEntityFrom(DamageSource.DROWN, 2.0F);
				}
			}
		}
	}

	@Override
	public void setOxygen(int currentAir) {
		this.oxygen = currentAir;
	}

	@Override
	public int getOxygen() {
		return this.oxygen;
	}

	@Override
	public int receiveOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.oxygenCapacity - this.oxygen);
		this.oxygen += amount;
		return amount;
	}

	@Override
	public int extractOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.oxygen + 20);
		this.oxygen -= amount;
		return amount;
	}

	@Override
	public void setPrevOxygen(int previousAir) {
		this.prevOxygen = previousAir;
	}

	@Override
	public int getPrevOxygen() {
		return this.prevOxygen;
	}

	@Override
	public void setOxygenCapacity(int maxAir) {
		this.oxygenCapacity = maxAir;
	}

	@Override
	public int getOxygenCapacity() {
		return this.oxygenCapacity;
	}

	@Override
	public void setSwimSpeedBase(double siwmBaseSpeed) {
		this.swimSpeedBase = siwmBaseSpeed;
	}

	@Override
	public double getSwimSpeedBase() {
		return this.swimSpeedBase;
	}

	@Override
	public void setSwimSpeedBonus(double swimBonusSpeed) {
		this.swimSpeedBonus = swimBonusSpeed;
	}

	@Override
	public double getSwimSpeedBonus() {
		return this.swimSpeedBonus;
	}

	@Override
	public void setBreakSpeed(float breakSpeed) {
		this.breakSpeed = breakSpeed;
	}

	@Override
	public float getBreakSpeed() {
		return this.breakSpeed;
	}

	@Override
	public int getOxygenFromPlayer(EntityPlayer player) {
		ItemStack helm = player.inventory.armorInventory.get(3);
		if (helm.getItem() instanceof AbstractItemDivingGear) {
			ItemStack chest = player.inventory.armorInventory.get(2);
			if (chest.getItem() instanceof AbstractItemDivingGear) {
				ICapabilityOxygen ioxygen = chest.getCapability(CapabilityOxygenProvider.OXYGEN, null);
				return this.oxygen + ioxygen.getOxygen();
			}
		}
		return this.oxygen;
	}

	@Override
	public int getOxygenCapacityFromPlayer(EntityPlayer player) {
		ItemStack helm = player.inventory.armorInventory.get(3);
		if (helm.getItem() instanceof AbstractItemDivingGear) {
			ItemStack chest = player.inventory.armorInventory.get(2);
			if (chest.getItem() instanceof AbstractItemDivingGear) {
				ICapabilityOxygen ioxygen = chest.getCapability(CapabilityOxygenProvider.OXYGEN, null);
				return this.oxygenCapacity + ioxygen.getOxygenCapacity();
			}
		}
		return this.oxygenCapacity;
	}

	@Override
	public int receiveOxygenFromPlayer(EntityPlayer player, int amount) {
		if (amount > 0) {
			amount -= this.receiveOxygen(amount);
			ItemStack helm = player.inventory.armorInventory.get(3);
			if (helm.getItem() instanceof AbstractItemDivingGear) {
				ItemStack chest = player.inventory.armorInventory.get(2);
				if (chest.getItem() instanceof AbstractItemDivingGear) {
					ICapabilityOxygen ioxygen = chest.getCapability(CapabilityOxygenProvider.OXYGEN, null);
					ioxygen.receiveOxygen(amount);
				}
			}
		}
		return 0;
	}

	@Override
	public int extractOxygenFromPlayer(EntityPlayer player, int amount) {
		if (amount > 0) {
			ItemStack helm = player.inventory.armorInventory.get(3);
			if (helm.getItem() instanceof AbstractItemDivingGear) {
				ItemStack chest = player.inventory.armorInventory.get(2);
				if (chest.getItem() instanceof AbstractItemDivingGear) {
					ICapabilityOxygen ioxygen = chest.getCapability(CapabilityOxygenProvider.OXYGEN, null);
					amount -= ioxygen.extractOxygen(amount);
				}
			}
			this.extractOxygen(amount);
		}
		return 0;
	}

	@Override
	public double getOxygenPercent(EntityPlayer player) {
		return (double) this.getOxygenFromPlayer(player) / (double) this.getOxygenCapacityFromPlayer(player);
	}

}
