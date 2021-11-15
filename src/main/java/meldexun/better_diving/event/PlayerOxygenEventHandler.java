package meldexun.better_diving.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.util.OxygenPlayerHelper;
import meldexun.reflectionutil.ReflectionMethod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class PlayerOxygenEventHandler {

	private static final ReflectionMethod<Integer> LIVING_ENTITY_INCREASE_AIR_SUPPLY = new ReflectionMethod<>(LivingEntity.class, "func_207300_l", "increaseAirSupply", int.class);

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
		if (event.phase == Phase.START) {
			return;
		}
		PlayerEntity player = event.player;
		if (player.level.isClientSide) {
		   return;
		}
		if (player.getVehicle() instanceof EntitySeamoth && ((EntitySeamoth) player.getVehicle()).hasEnergy()) {
			OxygenPlayerHelper.receiveOxygenRespectEquipment(player, LIVING_ENTITY_INCREASE_AIR_SUPPLY.invoke(player, 0));
		}
		if (player.getAirSupply() < player.getMaxAirSupply()) {
			int i = OxygenPlayerHelper.extractOxygenRespectEquipment(player, player.getMaxAirSupply() - player.getAirSupply());
			player.setAirSupply(player.getAirSupply() + i);
		} else {
		   OxygenPlayerHelper.receiveOxygenRespectEquipment(player, LIVING_ENTITY_INCREASE_AIR_SUPPLY.invoke(player, 0));
		}
		/*
		if (!BetterDivingConfig.SERVER_CONFIG.oxygenChanges.get()) {
			if (!player.level.isClientSide() && player.getVehicle() instanceof EntitySeamoth) {
				player.setAirSupply(Math.min(player.getAirSupply() + 5, player.getMaxAirSupply()));
			}
			return;
		}
		player.getCapability(CapabilityOxygenProvider.OXYGEN).ifPresent(cap -> {
			cap.setOxygen(cap.getOxygen());
			if (!player.level.isClientSide()) {
				if (!BetterDivingHelper.canBreath(player)) {
					int oxygenUsage = 1;

					if (BetterDivingConfig.SERVER_CONFIG.oxygen.oxygenEfficiency.get()) {
						int blocksUnderWater = BetterDivingHelper.blocksUnderWater(player);
						int maxDivingDepth = DivingGearHelper.getMaxDivingDepth(player);
						if (blocksUnderWater > maxDivingDepth) {
							oxygenUsage += 1 + (blocksUnderWater - maxDivingDepth) / BetterDivingConfig.SERVER_CONFIG.oxygen.oxygenEfficiencyRate.get();
						}
					}

					OxygenPlayerHelper.extractOxygenRespectEquipment(player, oxygenUsage);
				} else {
					OxygenPlayerHelper.receiveOxygenRespectEquipment(player, 25);
				}

				player.setAirSupply((int) (OxygenPlayerHelper.getOxygenRespectEquipmentInPercent(player) * player.getMaxAirSupply()));

				if (cap.getOxygen() <= -20) {
					cap.setOxygen(0);

					if (!player.level.isClientSide() && !MinecraftForge.EVENT_BUS.post(new PlayerSuffocateEvent(player))) {
						((ServerWorld) player.level).sendParticles(ParticleTypes.BUBBLE, player.getX(), player.getY() + player.getBbHeight() * 0.5D, player.getZ(), 8, 0.25D, 0.25D, 0.25D, 0.0D);

						player.hurt(DamageSource.DROWN, 2.0F);
					}
				}
			}

			if (!player.level.isClientSide()) {
				BetterDiving.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SPacketSyncOxygen(cap.getOxygen()));
			}
		});
		*/
	}

}
