package meldexun.better_diving.registry;

import java.lang.reflect.Method;
import java.util.List;

import meldexun.better_diving.util.BiomeHelper;
import meldexun.better_diving.util.config.EntityConfig;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class EntitySpawnEntry {

	public final Class<? extends EntityLiving> entityClass;
	public boolean enabled;
	public int weight;
	public List<Biome> biomes;
	public List<Integer> dimensions;

	public EntitySpawnEntry(Class<? extends EntityLiving> entityClass, EntityConfig config) {
		this(entityClass, config.enabled, config.weight, BiomeHelper.getBiomes(config.biomes), BiomeHelper.getDimensions(config.dimensions));
	}

	public EntitySpawnEntry(Class<? extends EntityLiving> entityClass, boolean enabled, int weight, List<Biome> biomes, List<Integer> dimensions) {
		this.entityClass = entityClass;
		this.enabled = enabled;
		this.weight = weight;
		this.biomes = biomes;
		this.dimensions = dimensions;
		if (this.biomes.isEmpty() || this.dimensions.isEmpty()) {
			this.enabled = false;
		}
	}

	public boolean canSpawnAt(World world, BlockPos pos) {
		try {
			Method canEntitySpawnAt = this.entityClass.getMethod("canSpawnAt", World.class, BlockPos.class);
			if (!(boolean) canEntitySpawnAt.invoke(null, world, pos)) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.dimensions.contains(world.provider.getDimension()) && this.biomes.contains(world.getBiome(pos));
	}

}
