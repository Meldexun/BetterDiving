package meldexun.better_diving.util;

import java.util.List;

import meldexun.better_diving.integration.TerraFirmaCraft;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockHelper {

	public static BlockPos getSeaBed(World world, BlockPos pos) {
		int i = 0;
		while (BlockHelper.isWaterBlock(world.getBlockState(pos.down(i)))) {
			i++;
		}
		return pos.down(i - 1);
	}

	public static boolean isOreDictionaried(String[] names, ItemStack stack) {
		for (String name : names) {
			List<ItemStack> stacks = OreDictionary.getOres(name);
			for (ItemStack stack1 : stacks) {
				if (stack1.isItemEqual(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isWaterBlock(IBlockState state) {
		Block block = state.getBlock();
		return block == Blocks.WATER || block == Blocks.FLOWING_WATER || TerraFirmaCraft.isWater(state);
	}

	public static boolean isWaterBlock(IBlockState state, int meta) {
		Block block = state.getBlock();
		return block == Blocks.WATER || (block == Blocks.FLOWING_WATER && block.getMetaFromState(state) <= meta) || TerraFirmaCraft.isWater(state, meta);
	}

}
