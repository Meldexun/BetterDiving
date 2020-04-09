package meldexun.better_diving.integration;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TerraFirmaCraft {

	private static boolean loaded = false;
	private static Block saltWater = null;

	private TerraFirmaCraft() {

	}

	public static void setLoaded() {
		TerraFirmaCraft.loaded = Loader.isModLoaded("tfc");
		if (TerraFirmaCraft.loaded) {
			TerraFirmaCraft.saltWater = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("tfc", "fluid/salt_water"));
		}
	}

	public static boolean isWater(IBlockState state) {
		if (!TerraFirmaCraft.loaded) {
			return false;
		}
		return state.getBlock() == TerraFirmaCraft.saltWater;
	}

	public static boolean isWater(IBlockState state, int meta) {
		if (!TerraFirmaCraft.loaded) {
			return false;
		}
		Block block = state.getBlock();
		return block == TerraFirmaCraft.saltWater && block.getMetaFromState(state) <= meta;
	}

}
