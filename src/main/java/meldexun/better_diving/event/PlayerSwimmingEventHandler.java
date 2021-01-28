package meldexun.better_diving.event;

import java.util.UUID;
import java.util.stream.Stream;

import com.google.common.base.Predicates;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.util.BetterDivingHelper;
import meldexun.better_diving.util.reflection.ReflectionField;
import meldexun.better_diving.util.reflection.ReflectionMethod;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ReuseableStream;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class PlayerSwimmingEventHandler {

	private static final UUID DEPTH_STRIDER_MODIFIER = UUID.fromString("e8b4c5fd-34e6-4b70-bf3e-70718c7fe670");
	private static final UUID DIVING_MODIFIER = UUID.fromString("c47024c2-c4b9-4b57-8d31-17e51f317301");
	private static final UUID DOLPHINS_GRACE_MODIFIER = UUID.fromString("987712a1-5b42-4676-a7f1-be3f8ee42bde");
	private static final UUID HUNGER_MODIFIER = UUID.fromString("8a59a8ba-17fb-4ab7-94ec-ad24d992e543");
	private static final UUID MAINHAND_MODIFIER = UUID.fromString("8dd6d56b-3bba-4019-bd0a-1fdaf45f6fdb");
	private static final UUID OFFHAND_MODIFIER = UUID.fromString("f4d92ce8-ef22-4bad-bdc2-9beec0a79b85");
	private static final UUID OVERWATER_MODIFIER = UUID.fromString("002df552-8d60-42b0-9dce-1bdaaefddec0");

	private static final ReflectionField<Boolean> FIELD_IS_JUMPING = new ReflectionField<>(LivingEntity.class, "field_70703_bu", "isJumping");
	private static final ReflectionMethod<Float> METHOD_GET_WATER_SLOW_DOWN = new ReflectionMethod<>(LivingEntity.class, "func_189749_co", "getWaterSlowDown");

	@SubscribeEvent
	public static void onInputUpdateEvent(InputUpdateEvent event) {
		PlayerEntity player = event.getPlayer();

		if (player.world.isRemote) {
			player.moveVertical = 0.0F;

			if (BetterDivingConfig.SERVER_CONFIG.movementChanges.get()) {
				if (player.isInWater()) {
					MovementInput input = ((ClientPlayerEntity) player).movementInput;
					if (input.jump) {
						player.moveVertical += 1.0F;
					}
					if (input.sneaking) {
						player.moveVertical -= 1.0F;
					}
					player.moveVertical *= 0.98F;
				}

				if (!player.abilities.isFlying && !isOnGround(player)) {
					((ClientPlayerEntity) event.getPlayer()).movementInput.sneaking = false;
				}
			}
		}
	}

	/**
	 * Copied from {@link Entity#move(MoverType, Vector3d)} and
	 * {@link Entity#getAllowedMovement(Vector3d, AxisAlignedBB, net.minecraft.world.IWorldReader, ISelectionContext, ReuseableStream)}
	 */
	private static boolean isOnGround(PlayerEntity player) {
		Vector3d vec = new Vector3d(0.0D, -0.01D, 0.0D);
		AxisAlignedBB axisalignedbb = player.getBoundingBox();
		ISelectionContext iselectioncontext = ISelectionContext.forEntity(player);
		VoxelShape voxelshape = player.world.getWorldBorder().getShape();
		Stream<VoxelShape> stream = VoxelShapes.compare(voxelshape, VoxelShapes.create(axisalignedbb.shrink(1.0E-7D)), IBooleanFunction.AND) ? Stream.empty() : Stream.of(voxelshape);
		Stream<VoxelShape> stream1 = player.world.func_230318_c_(player, axisalignedbb.expand(vec), Predicates.alwaysTrue());
		ReuseableStream<VoxelShape> reuseablestream = new ReuseableStream<>(Stream.concat(stream1, stream));
		Vector3d vector3d = vec.lengthSquared() == 0.0D ? vec : Entity.collideBoundingBoxHeuristically(player, vec, axisalignedbb, player.world, iselectioncontext, reuseablestream);
		return vec.y != vector3d.y;
	}

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
			double d3 = player.getLookVec().y;
			double d4 = d3 < -0.2D ? 0.085D : 0.06D;
			if (d3 <= 0.0D || FIELD_IS_JUMPING.get(player) || !player.world.getBlockState(new BlockPos(player.getPosX(), player.getPosY() + 1.0D - 0.1D, player.getPosZ())).getFluidState().isEmpty()) {
				Vector3d vector3d1 = player.getMotion();
				player.setMotion(vector3d1.subtract(0.0D, (d3 - vector3d1.y) * d4, 0.0D));
			}
		}

		// subtract jump factor applied in LivingEntity#handleFluidJump(ITag)
		if (FIELD_IS_JUMPING.get(player)) {
			player.setMotion(player.getMotion().subtract(0.0D, (double) 0.04F * player.getAttribute(ForgeMod.SWIM_SPEED.get()).getValue(), 0.0D));
		}

		// handle movement in water
		double oldY = player.getPosY();
		double slowdown = (double) METHOD_GET_WATER_SLOW_DOWN.invoke(player);
		double swimSpeed = BetterDivingHelper.getSwimSpeedRespectEquipment(player);

		Vector3d vec = BetterDivingHelper.getMoveVec(player.moveForward, player.moveVertical, player.moveStrafing, swimSpeed, player.rotationYaw, player.rotationPitch);
		player.setMotion(player.getMotion().add(vec));
		player.move(MoverType.SELF, player.getMotion());
		Vector3d vec1 = player.getMotion();
		if (player.collidedHorizontally && player.isOnLadder()) {
			vec1 = new Vector3d(vec1.x, 0.2D, vec1.z);
		}

		Vector3d vec2 = vec1.mul(slowdown, slowdown, slowdown);
		player.setMotion(vec2);
		if (player.collidedHorizontally && player.isOffsetPositionInLiquid(vec2.x, vec2.y + (double) 0.6F - player.getPosY() + oldY, vec2.z)) {
			player.setMotion(vec2.x, (double) 0.3F, vec2.z);
		}

		return true;
	}

	private static void updateSwimSpeed(PlayerEntity player) {
		ModifiableAttributeInstance attribute = player.getAttribute(ForgeMod.SWIM_SPEED.get());

		if (BetterDivingConfig.SERVER_CONFIG.movementChanges.get()) {
			BetterDivingConfig.ServerConfig.Movement config = BetterDivingConfig.SERVER_CONFIG.movement;

			ItemStack feet = player.getItemStackFromSlot(EquipmentSlotType.FEET);
			int depthStriderLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.DEPTH_STRIDER, feet);
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

			if (player.isPotionActive(Effects.DOLPHINS_GRACE)) {
				applyModifier(attribute, DOLPHINS_GRACE_MODIFIER, "Dolphins Grace Modifier", config.dolphinsGraceAmount.get(), config.dolphinsGraceOperation.get());
			} else {
				attribute.removeModifier(DOLPHINS_GRACE_MODIFIER);
			}

			if (config.hungerModifier.get()) {
				double hunger = player.getFoodStats().getFoodLevel() / 20.0D;
				if (!player.isCreative() && hunger < config.hungerThreshold.get()) {
					double amount = (1.0D - hunger / config.hungerThreshold.get()) * config.hungerAmount.get();
					applyModifier(attribute, HUNGER_MODIFIER, "Hunger Modifier", amount, config.hungerOperation.get());
				} else {
					attribute.removeModifier(HUNGER_MODIFIER);
				}
			}

			if (config.mainhandModifier.get()) {
				if (!player.getHeldItemMainhand().isEmpty()) {
					applyModifier(attribute, MAINHAND_MODIFIER, "Mainhand Modifier", config.mainhandAmount.get(), config.mainhandOperation.get());
				} else {
					attribute.removeModifier(MAINHAND_MODIFIER);
				}
			}

			if (config.offhandModifier.get()) {
				if (!player.getHeldItemOffhand().isEmpty()) {
					applyModifier(attribute, OFFHAND_MODIFIER, "Offhand Modifier", config.offhandAmount.get(), config.offhandOperation.get());
				} else {
					attribute.removeModifier(OFFHAND_MODIFIER);
				}
			}

			if (config.overwaterModifier.get()) {
				if (!player.areEyesInFluid(FluidTags.WATER)) {
					applyModifier(attribute, OVERWATER_MODIFIER, "Overwater Modifier", config.overwaterAmount.get(), config.overwaterOperation.get());
				} else {
					attribute.removeModifier(OVERWATER_MODIFIER);
				}
			}
		} else {
			attribute.removeModifier(DEPTH_STRIDER_MODIFIER);
			attribute.removeModifier(DEPTH_STRIDER_MODIFIER);
			attribute.removeModifier(DIVING_MODIFIER);
			attribute.removeModifier(DOLPHINS_GRACE_MODIFIER);
			attribute.removeModifier(HUNGER_MODIFIER);
			attribute.removeModifier(MAINHAND_MODIFIER);
			attribute.removeModifier(OFFHAND_MODIFIER);
			attribute.removeModifier(OVERWATER_MODIFIER);
		}
	}

	private static void applyModifier(ModifiableAttributeInstance attribute, UUID modifierId, String modifierName, double modifierAmount, int modifierOperation) {
		AttributeModifier.Operation operation = AttributeModifier.Operation.byId(modifierOperation);
		AttributeModifier oldModifier = attribute.getModifier(modifierId);
		if (oldModifier == null) {
			attribute.applyNonPersistentModifier(new AttributeModifier(modifierId, modifierName, modifierAmount, operation));
		} else if (oldModifier.getAmount() != modifierAmount || oldModifier.getOperation() != operation) {
			attribute.removeModifier(oldModifier);
			attribute.applyNonPersistentModifier(new AttributeModifier(modifierId, modifierName, modifierAmount, operation));
		}
	}

}
