package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BetterDivingEntities {

	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, BetterDiving.MOD_ID);

	public static final RegistryObject<EntityType<EntitySeamoth>> SEAMOTH = ENTITIES.register("seamoth", () -> EntityType.Builder.create(EntitySeamoth::new, EntityClassification.MISC).immuneToFire().setTrackingRange(64).setUpdateInterval(1).size(1.82F, 1.82F).setShouldReceiveVelocityUpdates(true).build("seamoth"));

	public static void registerEntities() {
		ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

}
