package meldexun.better_diving.block;

import meldexun.better_diving.tileentity.TileEntitySolarPanel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSolarPanel extends BlockEnergyGenerator {

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntitySolarPanel();
	}

}
