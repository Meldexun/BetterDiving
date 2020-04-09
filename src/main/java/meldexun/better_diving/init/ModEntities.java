package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntityBladderfish;
import meldexun.better_diving.entity.EntityBoomerang;
import meldexun.better_diving.entity.EntityGarryfish;
import meldexun.better_diving.entity.EntityHolefish;
import meldexun.better_diving.entity.EntityPeeper;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.registry.EntitySpawnEntry;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(BetterDiving.MOD_ID)
public class ModEntities {

	private ModEntities() {

	}

	@EventBusSubscriber(modid = BetterDiving.MOD_ID)
	public static class EntityRegistrationHandler {

		private static int entityID = 0;

		private EntityRegistrationHandler() {

		}

		@SubscribeEvent
		public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
			final EntityEntry[] entityEntries = {
					EntityRegistrationHandler.createEntityEntry(EntitySeamoth.class, "seamoth", 64, 1, true).build(),
					EntityRegistrationHandler.createEntityEntry(EntityPeeper.class, "peeper", 64, 1, true).egg(0x1C2833, 0xFFE03C).build(),
					EntityRegistrationHandler.createEntityEntry(EntityBladderfish.class, "bladderfish", 64, 1, true).egg(0x4C2E44, 0x5D5D91).build(),
					EntityRegistrationHandler.createEntityEntry(EntityGarryfish.class, "garryfish", 64, 1, true).egg(0x4C2E44, 0x5D5D91).build(),
					EntityRegistrationHandler.createEntityEntry(EntityHolefish.class, "holefish", 64, 1, true).egg(0x4C2E44, 0x5D5D91).build(),
					EntityRegistrationHandler.createEntityEntry(EntityBoomerang.class, "boomerang", 64, 1, true).egg(0x4C2E44, 0x5D5D91).build() };

			event.getRegistry().registerAll(entityEntries);
		}

		@SubscribeEvent
		public static void registerRecipes(RegistryEvent.Register<EntitySpawnEntry> event) {
			final EntitySpawnEntry[] entitySpawnEntries = {
					new EntitySpawnEntry(EntityPeeper.class, BetterDivingConfig.getInstance().entities.peeper).setRegistryName(BetterDiving.MOD_ID, "peeper"),
					new EntitySpawnEntry(EntityBladderfish.class, BetterDivingConfig.getInstance().entities.bladderfish).setRegistryName(BetterDiving.MOD_ID, "bladderfish"),
					new EntitySpawnEntry(EntityGarryfish.class, BetterDivingConfig.getInstance().entities.garryfish).setRegistryName(BetterDiving.MOD_ID, "garryfish"),
					new EntitySpawnEntry(EntityHolefish.class, BetterDivingConfig.getInstance().entities.holefish).setRegistryName(BetterDiving.MOD_ID, "holefish"),
					new EntitySpawnEntry(EntityBoomerang.class, BetterDivingConfig.getInstance().entities.boomerang).setRegistryName(BetterDiving.MOD_ID, "boomerang") };

			event.getRegistry().registerAll(entitySpawnEntries);
		}

		private static EntityEntryBuilder<Entity> createEntityEntry(Class<? extends Entity> entityClass, String name, int trackerRange, int trackerUpdateFrequency, boolean sendVelocityUpdates) {
			return EntityEntryBuilder.create().entity(entityClass).id(new ResourceLocation(BetterDiving.MOD_ID, name), EntityRegistrationHandler.entityID++).name(name).tracker(trackerRange, trackerUpdateFrequency, sendVelocityUpdates);
		}

	}

}
