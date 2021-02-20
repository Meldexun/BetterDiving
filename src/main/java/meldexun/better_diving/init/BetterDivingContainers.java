package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.inventory.container.ContainerSeamothEntity;
import meldexun.better_diving.inventory.container.ContainerSeamothItem;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BetterDivingContainers {

	private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BetterDiving.MOD_ID);

	public static final RegistryObject<ContainerType<ContainerSeamothEntity>> SEAMOTH_ENTITY = CONTAINERS.register("seamoth_entity", () -> new ContainerType<>(ContainerSeamothEntity::new));
	public static final RegistryObject<ContainerType<ContainerSeamothItem>> SEAMOTH_ITEM = CONTAINERS.register("seamoth_item", () -> new ContainerType<>(ContainerSeamothItem::new));

	public static void registerContainers() {
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

}
