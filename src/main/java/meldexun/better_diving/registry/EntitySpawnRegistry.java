package meldexun.better_diving.registry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.AbstractEntityFish;
import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.EntityHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
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
	public void onWorldTickEvent(WorldTickEvent event) {
		if (event.world.isRemote || !BetterDivingConfig.getInstance().modules.entitySpawning || EntitySpawnEntry.REGISTRY.getValuesCollection().isEmpty() || !event.world.getGameRules().getBoolean("doMobSpawning")) {
			return;
		}
		WorldServer world = (WorldServer) event.world;
		Iterator<Chunk> iterator = world.getPersistentChunkIterable(world.getPlayerChunkMap().getChunkIterator());

		while (iterator.hasNext()) {
			Chunk chunk = iterator.next();

			if (!chunk.isLoaded()) {
				continue;
			}

			if (EntitySpawnRegistry.RAND.nextInt(BetterDivingConfig.getInstance().entities.chance) == 0) {
				int x = chunk.x * 16 + EntitySpawnRegistry.RAND.nextInt(16);
				int y = Math.max(world.getSeaLevel() - 1, 1);
				int z = chunk.z * 16 + EntitySpawnRegistry.RAND.nextInt(16);
				BlockPos pos = new BlockPos(x, y, z);

				if (world.getBlockState(pos).getMaterial() == Material.WATER) {
					int blocksToSeafloor = EntityHelper.blocksToSeafloor(world, pos);
					if (blocksToSeafloor > 10) {
						double modalValue = 0.75D;
						double standardDeviation = 0.15D;
						double min = 0.0D;
						double max = 1.0D;
						double d = MathHelper.clamp(modalValue + EntitySpawnRegistry.RAND.nextGaussian() * standardDeviation, min, max);
						pos = pos.down((int) Math.round((double) blocksToSeafloor * d));
					}

					if (world.isAnyPlayerWithinRangeAt(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 128.0D)) {
						int range = BetterDivingConfig.getInstance().entities.range;
						BlockPos pos1 = pos.add(-range, -range, -range);
						BlockPos pos2 = pos.add(range, range, range);

						if (world.getEntitiesWithinAABB(AbstractEntityFish.class, new AxisAlignedBB(pos1, pos2)).size() < BetterDivingConfig.getInstance().entities.limit) {
							List<EntitySpawnEntry> possibleEntityEntryList = this.getPossibleEntitySpawnEntryList(world, pos);

							if (!possibleEntityEntryList.isEmpty()) {
								EntitySpawnEntry entitySpawnEntry = this.getRandomEntitySpawnEntryFromList(possibleEntityEntryList);

								try {
									EntityLiving entity = entitySpawnEntry.getEntityClass().getConstructor(World.class).newInstance(world);
									entity.setPosition(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
									world.spawnEntity(entity);
								} catch (Exception e) {
									BetterDiving.logger.error("Failed to spawn fish at " + pos, e);
								}
							}
						}
					}
				}
			}
		}
	}

	protected List<EntitySpawnEntry> getPossibleEntitySpawnEntryList(World world, BlockPos pos) {
		List<EntitySpawnEntry> possibleEntityEntryList = new ArrayList<>();

		for (EntitySpawnEntry entitySpawnEntry : EntitySpawnEntry.REGISTRY.getValuesCollection()) {
			if (entitySpawnEntry.isEnabled() && entitySpawnEntry.canSpawnAt(world, pos)) {
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
				i += entry.getWeight();
			}

			int j = EntitySpawnRegistry.RAND.nextInt(i);
			for (EntitySpawnEntry entry : entitySpawnEntryList) {
				j -= entry.getWeight();
				if (j <= 0) {
					return entry;
				}
			}
		}

		return null;
	}

}
