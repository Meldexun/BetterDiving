package meldexun.better_diving.registry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.AbstractEntityFish;
import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.EntityHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class EntitySpawnRegistry {

	private static final Random RAND = new Random();

	@SubscribeEvent
	public static void createRegistry(RegistryEvent.NewRegistry event) {
		RegistryBuilder<EntitySpawnEntry> builder = new RegistryBuilder<>();
		builder.setType(EntitySpawnEntry.class);
		builder.setName(new ResourceLocation(BetterDiving.MOD_ID, "entity_spawns"));
		builder.setIDRange(0, Short.MAX_VALUE - 1);
		builder.create();
	}

	@SubscribeEvent
	public static void onWorldTickEvent(TickEvent.WorldTickEvent event) {
		if (event.world.isRemote || event.phase == TickEvent.Phase.START || !BetterDivingConfig.getInstance().modules.entitySpawning || EntitySpawnEntry.REGISTRY.getValuesCollection().isEmpty() || !event.world.getGameRules().getBoolean("doMobSpawning")) {
			return;
		}

		WorldServer world = (WorldServer) event.world;
		Iterator<Chunk> iterator = world.getPersistentChunkIterable(world.getPlayerChunkMap().getChunkIterator());

		while (iterator.hasNext()) {
			Chunk chunk = iterator.next();

			if (!chunk.isLoaded()) {
				continue;
			}

			if (EntitySpawnRegistry.RAND.nextInt(BetterDivingConfig.getInstance().entities.chance) != 0) {
				continue;
			}

			int x = chunk.x * 16 + EntitySpawnRegistry.RAND.nextInt(16);
			int y = Math.max(world.getSeaLevel() - 1, 1);
			int z = chunk.z * 16 + EntitySpawnRegistry.RAND.nextInt(16);
			BlockPos pos = new BlockPos(x, y, z);

			if (world.getBlockState(pos).getMaterial() != Material.WATER) {
				continue;
			}

			int blocksToSeafloor = EntityHelper.blocksToSeafloor(world, pos);
			if (blocksToSeafloor > 10) {
				double modalValue = 0.75D;
				double standardDeviation = 0.15D;
				double min = 0.0D;
				double max = 1.0D;
				double d = MathHelper.clamp(modalValue + EntitySpawnRegistry.RAND.nextGaussian() * standardDeviation, min, max);
				pos = pos.down((int) Math.round(blocksToSeafloor * d));
			} else {
				pos = pos.down(EntitySpawnRegistry.RAND.nextInt(blocksToSeafloor));
			}
			Vec3d vec = new Vec3d(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);

			EntitySpawnEntry entitySpawnEntry = getRandomEntitySpawnEntryForPos(world, pos);
			if (entitySpawnEntry == null) {
				continue;
			}

			if (!world.isAnyPlayerWithinRangeAt(vec.x, vec.y, vec.z, 64.0D)) {
				continue;
			}

			int range = BetterDivingConfig.getInstance().entities.range;
			Vec3d vec1 = vec.subtract(range, range, range);
			Vec3d vec2 = vec.add(range, range, range);
			AxisAlignedBB aabb = new AxisAlignedBB(vec1.x, vec1.y, vec1.z, vec2.x, vec2.y, vec2.z);
			if (getEntitiesWithinAABB(world, AbstractEntityFish.class, aabb, null).size() >= BetterDivingConfig.getInstance().entities.limit) {
				continue;
			}

			try {
				Entity entity = entitySpawnEntry.createEntity(world);
				entity.setPosition(vec.x, vec.y, vec.z);
				world.spawnEntity(entity);
			} catch (Exception e) {
				BetterDiving.logger.error("Failed to spawn fish at " + pos, e);
			}
		}
	}

	protected static List<EntitySpawnEntry> getPossibleEntitySpawnEntryList(World world, BlockPos pos) {
		List<EntitySpawnEntry> possibleEntityEntryList = new LinkedList<>();

		for (EntitySpawnEntry entitySpawnEntry : EntitySpawnEntry.REGISTRY.getValuesCollection()) {
			if (entitySpawnEntry.isEnabled() && entitySpawnEntry.canSpawnAt(world, pos)) {
				possibleEntityEntryList.add(entitySpawnEntry);
			}
		}

		return possibleEntityEntryList;
	}

	@Nullable
	protected static EntitySpawnEntry getRandomEntitySpawnEntryFromList(List<EntitySpawnEntry> entitySpawnEntryList) {
		if (entitySpawnEntryList.isEmpty()) {
			return null;
		}

		int i = 0;
		for (EntitySpawnEntry entry : entitySpawnEntryList) {
			i += entry.getWeight();
		}

		int j = EntitySpawnRegistry.RAND.nextInt(i);
		for (EntitySpawnEntry entry : entitySpawnEntryList) {
			j -= entry.getWeight();
			if (j <= 0) {
				return entry;
			}
		}

		return null;
	}

	@Nullable
	protected static EntitySpawnEntry getRandomEntitySpawnEntryForPos(World world, BlockPos pos) {
		return getRandomEntitySpawnEntryFromList(getPossibleEntitySpawnEntryList(world, pos));
	}

	protected static <T extends Entity> List<T> getEntitiesWithinAABB(WorldServer world, Class<? extends T> entityClass, AxisAlignedBB aabb, @Nullable Predicate<? super T> filter) {
		int chunkX1 = (int) aabb.minX >> 4;
		int chunkZ1 = (int) aabb.minZ >> 4;
		int chunkX2 = (int) aabb.maxX >> 4;
		int chunkZ2 = (int) aabb.maxZ >> 4;
		List<T> list = new ArrayList<>();

		for (int x = chunkX1; x <= chunkX2; x++) {
			for (int z = chunkZ1; z <= chunkZ2; z++) {
				if (world.getChunkProvider().chunkExists(x, z)) {
					getEntitiesOfTypeWithinAABB(world.getChunk(x, z), entityClass, aabb, list, filter);
				}
			}
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	protected static <T extends Entity> void getEntitiesOfTypeWithinAABB(Chunk chunk, Class<? extends T> entityClass, AxisAlignedBB aabb, List<T> listToFill, @Nullable Predicate<? super T> filter) {
		ClassInheritanceMultiMap<Entity>[] entityLists = chunk.getEntityLists();
		int chunkY1 = MathHelper.clamp((int) aabb.minY >> 4, 0, entityLists.length - 1);
		int chunkY2 = MathHelper.clamp((int) aabb.maxY >> 4, 0, entityLists.length - 1);

		for (int y = chunkY1; y <= chunkY2; y++) {
			for (Entity entity : entityLists[y]) {
				if (entityClass.isAssignableFrom(entity.getClass()) && entity.getEntityBoundingBox().intersects(aabb) && (filter == null || filter.test((T) entity))) {
					listToFill.add((T) entity);
				}
			}
		}
	}

}
