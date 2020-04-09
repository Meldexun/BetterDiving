package meldexun.better_diving.registry;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.init.ModItems;
import meldexun.better_diving.item.crafting.Recipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class RecipeRegistry {

	@SubscribeEvent
	public static void createRegistry(RegistryEvent.NewRegistry event) {
		RegistryBuilder<Recipe> builder = new RegistryBuilder<>();
		builder.setType(Recipe.class);
		builder.setName(new ResourceLocation(BetterDiving.MOD_ID, "recipes"));
		builder.setIDRange(0, Short.MAX_VALUE - 1);
		builder.create();
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<Recipe> event) {
		IForgeRegistry<Recipe> registry = event.getRegistry();
		registry.register(
				new Recipe(new ItemStack(ModItems.HOLEFISH_CURED), new ItemStack[] { new ItemStack(ModItems.BLADDERFISH, 2), new ItemStack(ModItems.HOLEFISH, 4), new ItemStack(ModItems.BLADDERFISH, 1) }).setRegistryName(BetterDiving.MOD_ID, "test"));
	}

}
