package meldexun.better_diving.integration;

// import net.dries007.tfc.objects.blocks.BlockFluidWater;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class TerraFirmaCraft {

	public static boolean loaded = false;

	public static boolean isWater(IBlockState state) {
		Block block = state.getBlock();
		return true;
		// return block instanceof BlockFluidWater && ((BlockFluidWater) block).isSalt;
	}

	public static boolean isWater(IBlockState state, int meta) {
		Block block = state.getBlock();
		return true;
		// return block instanceof BlockFluidWater && ((BlockFluidWater) block).isSalt
		// && block.getMetaFromState(state) <= meta;
	}

}
