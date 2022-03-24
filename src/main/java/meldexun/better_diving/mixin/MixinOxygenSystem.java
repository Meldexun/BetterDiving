package meldexun.better_diving.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.events.OxygenSystem;

@Pseudo
@Mixin(targets = "net.mrscauthd.boss_tools.events.OxygenSystem")
public class MixinOxygenSystem {

	@Redirect(method = "Lnet/mrscauthd/boss_tools/events/OxygenSystem;OxygenSystem(Lnet/minecraft/entity/player/PlayerEntity;)V", remap = false, at = @At(value = "INVOKE", target = "Lnet/mrscauthd/boss_tools/events/Methodes;OxygenDamage(Lnet/minecraft/entity/LivingEntity;)V"))
	private static void OxygenDamage(LivingEntity entity) {
		// do nothing
	}

	@Redirect(method = "Lnet/mrscauthd/boss_tools/events/OxygenSystem;OxygenSystem(Lnet/minecraft/entity/player/PlayerEntity;)V", remap = false, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;func_70050_g(I)V", ordinal = 0))
	private static void setAir0(PlayerEntity entity, int air) {
		Methodes.OxygenDamage(entity);
	}

	@Redirect(method = "Lnet/mrscauthd/boss_tools/events/OxygenSystem;OxygenSystem(Lnet/minecraft/entity/player/PlayerEntity;)V", remap = false, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;func_70050_g(I)V", ordinal = 1))
	private static void setAir1(PlayerEntity entity, int air) {
		// do nothing
	}

	@Redirect(method = "Lnet/mrscauthd/boss_tools/events/OxygenSystem;OxygenSystem(Lnet/minecraft/entity/player/PlayerEntity;)V", remap = false, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;func_70050_g(I)V", ordinal = 2))
	private static void setAir2(PlayerEntity entity, int air) {
		Methodes.OxygenDamage(entity);
	}

}
