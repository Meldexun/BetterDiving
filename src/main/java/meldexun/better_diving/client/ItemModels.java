package meldexun.better_diving.client;

import java.util.ArrayList;
import java.util.List;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.block.BlockCreepvine;
import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Side.CLIENT)
public class ItemModels {

	private static StateMap.Builder builder = new StateMap.Builder();
	private static final List<Item> REGISTERED_ITEM_MODELS = new ArrayList<Item>();

	@SubscribeEvent
	public static void registerItemModels(ModelRegistryEvent event) {
		for (Item item : ModItems.ItemRegistrationHandler.ITEMS) {
			if (!REGISTERED_ITEM_MODELS.contains(item)) {
				registerItemModel(item);
			}
		}

		for (ItemBlock itemBlock : ModBlocks.BlockRegistrationHandler.ITEM_BLOCKS) {
			if (!REGISTERED_ITEM_MODELS.contains(itemBlock)) {
				registerItemModel(itemBlock);
			}
		}

		for (Block block : ModBlocks.BlockRegistrationHandler.BLOCKS) {
			if (block.getDefaultState().getMaterial() == Material.WATER) {
				ignoreProperty(block, BlockLiquid.LEVEL);
			}
		}
		ignoreProperty(ModBlocks.CREEPVINE, BlockCreepvine.SEED);
	}

	private static void registerItemModel(Item item) {
		registerCustomItemModel(item, 0, item.getRegistryName(), "inventory");
	}

	private static void registerCustomItemModel(Item item, int meta, ResourceLocation modelLocation, String variant) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(modelLocation, variant));
		REGISTERED_ITEM_MODELS.add(item);
	}

	private static void ignoreProperty(Block block, IProperty property) {
		ModelLoader.setCustomStateMapper(block, builder.ignore(property).build());
	}

}
