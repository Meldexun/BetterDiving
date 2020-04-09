package meldexun.better_diving.init;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.block.BlockCreepvine;
import meldexun.better_diving.block.BlockCreepvineSeed;
import meldexun.better_diving.block.BlockCreepvineTop;
import meldexun.better_diving.block.BlockFabricator;
import meldexun.better_diving.block.BlockOutcrop;
import meldexun.better_diving.block.BlockSeabaseLadder;
import meldexun.better_diving.block.BlockSeagrassTall;
import meldexun.better_diving.block.BlockSolarPanel;
import meldexun.better_diving.block.BlockStructure;
import meldexun.better_diving.block.BlockUnderwaterBlock;
import meldexun.better_diving.item.ItemBlockCreepvine;
import meldexun.better_diving.item.ItemBlockTooltip;
import meldexun.better_diving.tileentity.TileEntityCreepvine;
import meldexun.better_diving.tileentity.TileEntityCreepvineTop;
import meldexun.better_diving.tileentity.TileEntitySolarPanel;
import meldexun.better_diving.tileentity.TileEntityStructure;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(BetterDiving.MOD_ID)
public class ModBlocks {

	public static final BlockOutcrop LIMESTONE_OUTCROP = null;
	public static final BlockOutcrop SANDSTONE_OUTCROP = null;

	public static final BlockCreepvine CREEPVINE = null;
	public static final BlockCreepvineSeed CREEPVINE_SEED = null;
	public static final BlockCreepvineTop CREEPVINE_TOP = null;
	public static final BlockUnderwaterBlock SEAGRASS = null;
	public static final BlockSeagrassTall SEAGRASS_TALL_BOTTOM = null;
	public static final BlockSeagrassTall SEAGRASS_TALL_TOP = null;
	public static final BlockUnderwaterBlock ACID_MUSHROOM = null;

	public static final BlockStructure STRUCTURE_BLOCK = null;
	public static final BlockSolarPanel SOLAR_PANEL = null;
	public static final BlockFabricator FABRICATOR = null;

	private ModBlocks() {

	}

	@EventBusSubscriber(modid = BetterDiving.MOD_ID)
	public static class BlockRegistrationHandler {

		public static final List<Block> BLOCKS = new ArrayList<>();
		public static final List<Block> REGISTERED_ITEM_BLOCKS = new ArrayList<>();
		public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();

		private BlockRegistrationHandler() {

		}

		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			final Block[] blocks = {
					BlockRegistrationHandler.setBlockName(new BlockOutcrop(), "limestone_outcrop"),
					BlockRegistrationHandler.setBlockName(new BlockOutcrop(), "sandstone_outcrop"),
					BlockRegistrationHandler.setBlockName(new BlockCreepvine(), "creepvine"),
					BlockRegistrationHandler.setBlockNameAndTab(new BlockCreepvineSeed(), "creepvine_seed", null),
					BlockRegistrationHandler.setBlockNameAndTab(new BlockCreepvineTop(), "creepvine_top", null),
					BlockRegistrationHandler.setBlockName(new BlockUnderwaterBlock(), "seagrass"),
					BlockRegistrationHandler.setBlockName(new BlockSeagrassTall(), "seagrass_tall_bottom"),
					BlockRegistrationHandler.setBlockNameAndTab(new BlockSeagrassTall(), "seagrass_tall_top", null),
					BlockRegistrationHandler.setBlockName(new BlockUnderwaterBlock(), "acid_mushroom"),
					BlockRegistrationHandler.setBlockName(new BlockStructure(), "structure_block"),
					BlockRegistrationHandler.setBlockName(new BlockSolarPanel(), "solar_panel"),
					BlockRegistrationHandler.setBlockName(new BlockFabricator(), "fabricator"),
					BlockRegistrationHandler.setBlockName(new BlockSeabaseLadder(), "seabase_ladder") };

			IForgeRegistry<Block> registry = event.getRegistry();

			for (Block block : blocks) {
				registry.register(block);
				BlockRegistrationHandler.BLOCKS.add(block);
			}

			GameRegistry.registerTileEntity(TileEntityStructure.class, new ResourceLocation(BetterDiving.MOD_ID, "tile_entity_building"));
			GameRegistry.registerTileEntity(TileEntityCreepvine.class, new ResourceLocation(BetterDiving.MOD_ID, "tile_entity_creepvine"));
			GameRegistry.registerTileEntity(TileEntityCreepvineTop.class, new ResourceLocation(BetterDiving.MOD_ID, "tile_entity_creepvine_top"));
			GameRegistry.registerTileEntity(TileEntitySolarPanel.class, new ResourceLocation(BetterDiving.MOD_ID, "tile_entity_solar_panel"));
		}

		private static Block setBlockName(Block block, String name) {
			return BlockRegistrationHandler.setBlockNameAndTab(block, name, BetterDiving.TAB_BETTER_DIVING);
		}

		private static Block setBlockNameAndTab(Block block, String name, @Nullable CreativeTabs tab) {
			return block.setUnlocalizedName(name).setRegistryName(BetterDiving.MOD_ID, name).setCreativeTab(tab);
		}

		@SubscribeEvent
		public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
			IForgeRegistry<Item> registry = event.getRegistry();

			ItemBlock creepvine = new ItemBlockCreepvine();
			registry.register(creepvine);
			BlockRegistrationHandler.REGISTERED_ITEM_BLOCKS.add(ModBlocks.CREEPVINE);
			BlockRegistrationHandler.ITEM_BLOCKS.add(creepvine);

			for (Block block : BlockRegistrationHandler.BLOCKS) {
				if (!BlockRegistrationHandler.REGISTERED_ITEM_BLOCKS.contains(block)) {
					ItemBlock itemBlock = BlockRegistrationHandler.createItemBlock(block);
					registry.register(itemBlock);
					BlockRegistrationHandler.REGISTERED_ITEM_BLOCKS.add(block);
					BlockRegistrationHandler.ITEM_BLOCKS.add(itemBlock);
				}
			}
		}

		private static ItemBlock createItemBlock(Block block) {
			return (ItemBlock) new ItemBlockTooltip(block).setUnlocalizedName(block.getLocalizedName()).setRegistryName(block.getRegistryName());
		}
	}

	public static void addBlockDrops() {
		ModBlocks.LIMESTONE_OUTCROP.addDrop(ModItems.TITANIUM, 10);
		ModBlocks.LIMESTONE_OUTCROP.addDrop(ModItems.COPPER_ORE, 10);
		ModBlocks.SANDSTONE_OUTCROP.addDrop(ModItems.LEAD, 10);
		ModBlocks.SANDSTONE_OUTCROP.addDrop(ModItems.SILVER_ORE, 10);
		ModBlocks.SANDSTONE_OUTCROP.addDrop(ModItems.GOLD, 10);
	}

}
