package meldexun.better_diving.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.Nullable;

import meldexun.better_diving.entity.AbstractEntityFish;
import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.EntityHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

@EventBusSubscriber
public class EntitySpawnRegistry {

	private static final EntitySpawnRegistry INSTANCE = new EntitySpawnRegistry();
	private static final HashMap<Class<? extends EntityLiving>, EntitySpawnEntry> ENTITY_SPAWN_ENTRIES = new HashMap<Class<? extends EntityLiving>, EntitySpawnEntry>();
	private static final Random RAND = new Random();

	public static EntitySpawnRegistry getInstance() {
		return INSTANCE;
	}

	public static void addEntitySpawnEntry(EntitySpawnEntry entitySpawnEntry) {
		if (!ENTITY_SPAWN_ENTRIES.containsKey(entitySpawnEntry.entityClass)) {
			if (entitySpawnEntry.enabled) {
				ENTITY_SPAWN_ENTRIES.put(entitySpawnEntry.entityClass, entitySpawnEntry);
			}
		} else {
			System.err.println("Entity with class " + entitySpawnEntry.entityClass + " already added to entity spawn entries!");
		}
	}

	@SubscribeEvent
	public static void worldTick(WorldTickEvent event) {
		if (event.world != null && !event.world.isRemote) {
			WorldServer world = (WorldServer) event.world;
			if (BetterDivingConfig.MODULES.entitySpawning) {
				EntitySpawnRegistry.getInstance().spawnEntities(world);
			}
		}
	}

	public void spawnEntities(WorldServer world) {
		if (!ENTITY_SPAWN_ENTRIES.isEmpty()) {
			List<Chunk> chunkList = new ArrayList<Chunk>(world.getChunkProvider().getLoadedChunks());
			for (Chunk chunk : chunkList) {
				if (RAND.nextInt(BetterDivingConfig.ENTITY_SETTINGS.chance) == 0) {
					int x = chunk.x * 16 + 1;
					int y = Math.max(world.getSeaLevel() - 1, 1);
					int z = chunk.z * 16 + 1;
					BlockPos pos = new BlockPos(x + RAND.nextInt(16), y, z + RAND.nextInt(16));
					if (world.getBlockState(pos).getMaterial() == Material.WATER) {
						pos = pos.down(RAND.nextInt(EntityHelper.blocksToSeafloor(world, pos)));
						if (world.isAnyPlayerWithinRangeAt(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 128.0D)) {
							int range = BetterDivingConfig.ENTITY_SETTINGS.range;
							BlockPos pos1 = pos.add(-range, -range, -range);
							BlockPos pos2 = pos.add(range, range, range);
							if (world.getEntitiesWithinAABB(AbstractEntityFish.class, new AxisAlignedBB(pos1, pos2)).size() < BetterDivingConfig.ENTITY_SETTINGS.limit) {
								List<EntitySpawnEntry> possibleEntityEntryList = this.getPossibleEntitySpawnEntryList(world, pos);
								if (!possibleEntityEntryList.isEmpty()) {
									EntitySpawnEntry entitySpawnEntry = this.getRandomEntitySpawnEntryFromList(possibleEntityEntryList);
									try {
										EntityLiving entity = entitySpawnEntry.entityClass.getConstructor(World.class).newInstance(world);
										entity.setPosition(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
										world.spawnEntity(entity);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		}
	}

	protected List<EntitySpawnEntry> getPossibleEntitySpawnEntryList(World world, BlockPos pos) {
		List<EntitySpawnEntry> possibleEntityEntryList = new ArrayList<EntitySpawnEntry>();
		for (Entry<Class<? extends EntityLiving>, EntitySpawnEntry> entry : ENTITY_SPAWN_ENTRIES.entrySet()) {
			EntitySpawnEntry entitySpawnEntry = entry.getValue();
			if (entitySpawnEntry.enabled && entitySpawnEntry.canSpawnAt(world, pos)) {
				possibleEntityEntryList.add(entitySpawnEntry);
			}
		}
		return possibleEntityEntryList;
	}

	@Nullable
	protected EntitySpawnEntry getRandomEntitySpawnEntryFromList(List<EntitySpawnEntry> entitySpawnEntryList) {
		if (!entitySpawnEntryList.isEmpty()) {
			int i = 0;
			for (EntitySpawnEntry entry : entitySpawnEntryList) {
				i += entry.weight;
			}
			int j = RAND.nextInt(i);
			for (EntitySpawnEntry entry : entitySpawnEntryList) {
				j -= entry.weight;
				if (j <= 0) {
					return entry;
				}
			}
		}
		return null;
	}

}
