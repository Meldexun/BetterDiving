package meldexun.better_diving.registry;

import java.util.Set;

import javax.annotation.Nullable;

import meldexun.better_diving.util.config.EntityConfig;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class EntitySpawnEntry extends IForgeRegistryEntry.Impl<EntitySpawnEntry> {

	public static final IForgeRegistry<EntitySpawnEntry> REGISTRY = GameRegistry.findRegistry(EntitySpawnEntry.class);

	private final Class<? extends EntityLiving> entityClass;
	private final EntityConfig config;
	private final IEntityProvider entityProvider;

	public EntitySpawnEntry(Class<? extends EntityLiving> entityClass, EntityConfig config, IEntityProvider entityProvider) {
		this.entityClass = entityClass;
		this.config = config;
		this.entityProvider = entityProvider;
	}

	public boolean canSpawnAt(World world, BlockPos pos) {
		return this.canSpawnInDimension(world.provider.getDimension()) && this.canSpawnInBiome(world.getBiome(pos));
	}

	public boolean canSpawnInDimension(int dimension) {
		for (int dim : this.config.dimensions) {
			if (dim == dimension) {
				return true;
			}
		}
		return false;
	}

	public boolean canSpawnInBiome(Biome biome) {
		if (this.config.biomeOverride) {
			return true;
		}
		Set<BiomeDictionary.Type> biomeTypeSet = BiomeDictionary.getTypes(biome);
		for (String biomeTypeName : this.config.biomeTypes) {
			BiomeDictionary.Type biomeType = this.getBiomeTypeByName(biomeTypeName);
			if (biomeType != null && biomeTypeSet.contains(biomeType)) {
				return true;
			}
		}
		ResourceLocation biomeRegistryName = biome.getRegistryName();
		for (ResourceLocation biomeName : this.config.getBiomes()) {
			if (biomeName.equals(biomeRegistryName)) {
				return true;
			}
		}
		return false;
	}

	@Nullable
	private BiomeDictionary.Type getBiomeTypeByName(String biomeTypeName) {
		for (BiomeDictionary.Type biomeType : BiomeDictionary.Type.getAll()) {
			if (biomeType.getName().equals(biomeTypeName)) {
				return biomeType;
			}
		}
		return null;
	}

	public Class<? extends EntityLiving> getEntityClass() {
		return this.entityClass;
	}

	public boolean isEnabled() {
		return this.config.isEnabled();
	}

	public int getWeight() {
		return this.config.weight;
	}

	public EntityLiving createEntity(World world) {
		return this.entityProvider.createEntity(world);
	}

	public interface IEntityProvider {
		EntityLiving createEntity(World world);
	}

}
