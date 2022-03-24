package meldexun.better_diving.plugin;

import java.util.UUID;

import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.integration.BeyondEarth;
import meldexun.better_diving.util.BetterDivingHelper;
import meldexun.better_diving.util.DivingGearHelper;
import meldexun.reflectionutil.ReflectionMethod;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.ModList;

public class LivingEntityHook {

	private static final ReflectionMethod<Float> LIVING_ENTITY_GET_WATER_SLOWDOWN = new ReflectionMethod<>(LivingEntity.class, "func_189749_co", "getWaterSlowDown");

	private static final UUID DIVING_GEAR_MODIFIER = UUID.fromString("3f391130-0c29-4347-8ac9-72085af35236");
	private static final UUID DEPTH_STRIDER_MODIFIER = UUID.fromString("e8b4c5fd-34e6-4b70-bf3e-70718c7fe670");
	private static final UUID DIVING_MODIFIER = UUID.fromString("c47024c2-c4b9-4b57-8d31-17e51f317301");
	private static final UUID DOLPHINS_GRACE_MODIFIER = UUID.fromString("987712a1-5b42-4676-a7f1-be3f8ee42bde");
	private static final UUID HUNGER_MODIFIER = UUID.fromString("8a59a8ba-17fb-4ab7-94ec-ad24d992e543");
	private static final UUID MAINHAND_MODIFIER = UUID.fromString("8dd6d56b-3bba-4019-bd0a-1fdaf45f6fdb");
	private static final UUID OFFHAND_MODIFIER = UUID.fromString("f4d92ce8-ef22-4bad-bdc2-9beec0a79b85");
	private static final UUID OVERWATER_MODIFIER = UUID.fromString("002df552-8d60-42b0-9dce-1bdaaefddec0");

	public static boolean handlePlayerTravelInWater(LivingEntity entity) {
		if (!(entity instanceof PlayerEntity)) {
			return false;
		}
		PlayerEntity player = (PlayerEntity) entity;

		// update swim speed modifiers
		updateSwimSpeed(player);

		if (!BetterDivingConfig.SERVER_CONFIG.movementChanges.get()) {
			return false;
		}

		// subtract jump factor applied in PlayerEntity#travel(Vector3d)
		if (player.isSwimming() && !player.isPassenger()) {
			double d3 = player.getLookAngle().y;
			double d4 = d3 < -0.2D ? 0.085D : 0.06D;
			if (d3 <= 0.0D || player.jumping
					|| !player.level.getBlockState(new BlockPos(player.getX(), player.getY() + 1.0D - 0.1D, player.getZ())).getFluidState().isEmpty()) {
				Vector3d vector3d1 = player.getDeltaMovement();
				player.setDeltaMovement(new Vector3d(vector3d1.x, (vector3d1.y - d4 * d3) / (1 - d4), vector3d1.z));
			}
		}

		// subtract jump factor applied in LivingEntity#handleFluidJump(ITag)
		if (player.jumping) {
			player.setDeltaMovement(player.getDeltaMovement().subtract(0.0D, (double) 0.04F * player.getAttribute(ForgeMod.SWIM_SPEED.get()).getValue(), 0.0D));
		}

		// subtract sneak factor applied in LivingEntity#handleFluidSneak()
		if (player.level.isClientSide()) {
			handleFluidSneak(player);
		}

		// handle movement in water
		double oldY = player.getY();
		double slowdown = (double) LIVING_ENTITY_GET_WATER_SLOWDOWN.invoke(player);
		double swimSpeed = BetterDivingHelper.getSwimSpeedRespectEquipment(player);

		Vector3d vec = BetterDivingHelper.getMoveVec(player.zza, player.yya, player.xxa, swimSpeed, player.yRot, player.xRot);
		player.setDeltaMovement(player.getDeltaMovement().add(vec));
		player.move(MoverType.SELF, player.getDeltaMovement());
		Vector3d vec1 = player.getDeltaMovement();
		if (player.verticalCollision && player.onClimbable()) {
			vec1 = new Vector3d(vec1.x, 0.2D, vec1.z);
		}

		Vector3d vec2 = vec1.multiply(slowdown, slowdown, slowdown);
		player.setDeltaMovement(vec2);
		if (player.horizontalCollision && player.isFree(vec2.x, vec2.y + (double) 0.6F - player.getY() + oldY, vec2.z)) {
			player.setDeltaMovement(vec2.x, (double) 0.3F, vec2.z);
		}

		return true;
	}

	private static void updateSwimSpeed(PlayerEntity player) {
		ModifiableAttributeInstance attribute = player.getAttribute(ForgeMod.SWIM_SPEED.get());

		applyModifier(attribute, DIVING_GEAR_MODIFIER, "Diving Gear Modifier", DivingGearHelper.getSwimspeedBonus(player), 1);

		if (BetterDivingConfig.SERVER_CONFIG.movementChanges.get()) {
			BetterDivingConfig.ServerConfig.Movement config = BetterDivingConfig.SERVER_CONFIG.movement;

			ItemStack feet = player.getItemBySlot(EquipmentSlotType.FEET);
			int depthStriderLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.DEPTH_STRIDER, feet);
			if (depthStriderLevel > 0) {
				double amount = config.depthStriderAmount.get() * depthStriderLevel;
				applyModifier(attribute, DEPTH_STRIDER_MODIFIER, "Depth Strider Modifier", amount, config.depthStriderOperation.get());
			} else {
				attribute.removeModifier(DEPTH_STRIDER_MODIFIER);
			}

			if (player.isSwimming()) {
				applyModifier(attribute, DIVING_MODIFIER, "Diving Modifier", config.divingAmount.get(), config.divingOperation.get());
			} else {
				attribute.removeModifier(DIVING_MODIFIER);
			}

			if (player.hasEffect(Effects.DOLPHINS_GRACE)) {
				applyModifier(attribute, DOLPHINS_GRACE_MODIFIER, "Dolphins Grace Modifier", config.dolphinsGraceAmount.get(),
						config.dolphinsGraceOperation.get());
			} else {
				attribute.removeModifier(DOLPHINS_GRACE_MODIFIER);
			}

			if (config.hungerModifier.get()) {
				double hunger = player.getFoodData().getFoodLevel() / 20.0D;
				if (!player.isCreative() && hunger < config.hungerThreshold.get()) {
					double amount = (1.0D - hunger / config.hungerThreshold.get()) * config.hungerAmount.get();
					applyModifier(attribute, HUNGER_MODIFIER, "Hunger Modifier", amount, config.hungerOperation.get());
				} else {
					attribute.removeModifier(HUNGER_MODIFIER);
				}
			}

			if (config.mainhandModifier.get()) {
				if (!player.getMainHandItem().isEmpty()) {
					applyModifier(attribute, MAINHAND_MODIFIER, "Mainhand Modifier", config.mainhandAmount.get(), config.mainhandOperation.get());
				} else {
					attribute.removeModifier(MAINHAND_MODIFIER);
				}
			}

			if (config.offhandModifier.get()) {
				if (!player.getOffhandItem().isEmpty()) {
					applyModifier(attribute, OFFHAND_MODIFIER, "Offhand Modifier", config.offhandAmount.get(), config.offhandOperation.get());
				} else {
					attribute.removeModifier(OFFHAND_MODIFIER);
				}
			}

			if (config.overwaterModifier.get()) {
				if (!player.isEyeInFluid(FluidTags.WATER)) {
					applyModifier(attribute, OVERWATER_MODIFIER, "Overwater Modifier", config.overwaterAmount.get(), config.overwaterOperation.get());
				} else {
					attribute.removeModifier(OVERWATER_MODIFIER);
				}
			}
		} else {
			attribute.removeModifier(DEPTH_STRIDER_MODIFIER);
			attribute.removeModifier(DIVING_MODIFIER);
			attribute.removeModifier(DOLPHINS_GRACE_MODIFIER);
			attribute.removeModifier(HUNGER_MODIFIER);
			attribute.removeModifier(MAINHAND_MODIFIER);
			attribute.removeModifier(OFFHAND_MODIFIER);
			attribute.removeModifier(OVERWATER_MODIFIER);
		}
	}

	private static void applyModifier(ModifiableAttributeInstance attribute, UUID modifierId, String modifierName, double modifierAmount,
			int modifierOperation) {
		AttributeModifier.Operation operation = AttributeModifier.Operation.fromValue(modifierOperation);
		AttributeModifier oldModifier = attribute.getModifier(modifierId);
		if (oldModifier == null) {
			attribute.addTransientModifier(new AttributeModifier(modifierId, modifierName, modifierAmount, operation));
		} else if (oldModifier.getAmount() != modifierAmount || oldModifier.getOperation() != operation) {
			attribute.removeModifier(oldModifier);
			attribute.addTransientModifier(new AttributeModifier(modifierId, modifierName, modifierAmount, operation));
		}
	}

	@OnlyIn(Dist.CLIENT)
	private static void handleFluidSneak(PlayerEntity player) {
		if (!(player instanceof ClientPlayerEntity)) {
			return;
		}
		if (!((ClientPlayerEntity) player).input.shiftKeyDown) {
			return;
		}
		player.setDeltaMovement(player.getDeltaMovement().subtract(0.0D, (double) -0.04F * player.getAttribute(ForgeMod.SWIM_SPEED.get()).getValue(), 0.0D));
	}

	public static boolean canBreatheUnderwater(LivingEntity entity) {
		if (ModList.get().isLoaded("boss_tools")
				&& entity instanceof PlayerEntity
				&& BeyondEarth.isSpace(entity.level)) {
			return true;
		}
		return entity.getVehicle() instanceof EntitySeamoth && ((EntitySeamoth) entity.getVehicle()).hasEnergy();
	}

	public static int decreaseAirSupply(PlayerEntity player, int air) {
		int oxygenUsage = 1;

		if (BetterDivingConfig.SERVER_CONFIG.oxygen.oxygenEfficiency.get()) {
			int blocksUnderWater = BetterDivingHelper.blocksUnderWater(player);
			int maxDivingDepth = DivingGearHelper.getMaxDivingDepth(player);
			if (blocksUnderWater > maxDivingDepth) {
				oxygenUsage += 1 + (blocksUnderWater - maxDivingDepth) / BetterDivingConfig.SERVER_CONFIG.oxygen.oxygenEfficiencyRate.get();
			}
		}

		return MathHelper.clamp(air - oxygenUsage, -20, player.getMaxAirSupply());
	}

	public static int increaseAirSupply(PlayerEntity player, int air) {
		return MathHelper.clamp(air + BetterDivingConfig.SERVER_CONFIG.oxygen.oxygenRefillRate.get(), -20, player.getMaxAirSupply());
	}

}
