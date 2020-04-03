package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntityBladderfish;
import meldexun.better_diving.entity.EntityBoomerang;
import meldexun.better_diving.entity.EntityGarryfish;
import meldexun.better_diving.entity.EntityHolefish;
import meldexun.better_diving.entity.EntityPeeper;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.registry.EntitySpawnEntry;
import meldexun.better_diving.registry.EntitySpawnRegistry;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(BetterDiving.MOD_ID)
public class ModEntities {

	public static final EntityEntry SEAMOTH = null;
	public static final EntityEntry PEEPER = null;
	public static final EntityEntry BLADDERFISH = null;
	public static final EntityEntry GARRYFISH = null;
	public static final EntityEntry HOLEFISH = null;
	public static final EntityEntry BOOMERANG = null;

	@EventBusSubscriber(modid = BetterDiving.MOD_ID)
	public static class EntityRegistrationHandler {

		private static int entityID = 0;

		@SubscribeEvent
		public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
			final EntityEntry[] entityEntries = {
					createEntityEntry(EntitySeamoth.class, "seamoth", 64, 1, true).build(),
					createEntityEntry(EntityPeeper.class, "peeper", 64, 1, true).egg(0x1C2833, 0xFFE03C).build(),
					createEntityEntry(EntityBladderfish.class, "bladderfish", 64, 1, true).egg(0x4C2E44, 0x5D5D91).build(),
					createEntityEntry(EntityGarryfish.class, "garryfish", 64, 1, true).egg(0x4C2E44, 0x5D5D91).build(),
					createEntityEntry(EntityHolefish.class, "holefish", 64, 1, true).egg(0x4C2E44, 0x5D5D91).build(),
					createEntityEntry(EntityBoomerang.class, "boomerang", 64, 1, true).egg(0x4C2E44, 0x5D5D91).build() };

			EntitySpawnRegistry.addEntitySpawnEntry(new EntitySpawnEntry(EntityPeeper.class, BetterDivingConfig.ENTITY_SETTINGS.peeper));
			EntitySpawnRegistry.addEntitySpawnEntry(new EntitySpawnEntry(EntityBladderfish.class, BetterDivingConfig.ENTITY_SETTINGS.bladderfish));
			EntitySpawnRegistry.addEntitySpawnEntry(new EntitySpawnEntry(EntityGarryfish.class, BetterDivingConfig.ENTITY_SETTINGS.garryfish));
			EntitySpawnRegistry.addEntitySpawnEntry(new EntitySpawnEntry(EntityHolefish.class, BetterDivingConfig.ENTITY_SETTINGS.holefish));
			EntitySpawnRegistry.addEntitySpawnEntry(new EntitySpawnEntry(EntityBoomerang.class, BetterDivingConfig.ENTITY_SETTINGS.boomerang));

			IForgeRegistry<EntityEntry> registry = event.getRegistry();

			for (EntityEntry entityEntry : entityEntries) {
				registry.register(entityEntry);
			}
		}

		private static EntityEntryBuilder<Entity> createEntityEntry(Class<? extends Entity> entityClass, String name, int trackerRange, int trackerUpdateFrequency, boolean sendVelocityUpdates) {
			return EntityEntryBuilder.create().entity(entityClass).id(new ResourceLocation(BetterDiving.MOD_ID, name), entityID++).name(name).tracker(trackerRange, trackerUpdateFrequency, sendVelocityUpdates);
		}

	}

}
