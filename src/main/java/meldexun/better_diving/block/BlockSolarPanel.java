package meldexun.better_diving.block;

import meldexun.better_diving.tileentity.TileEntitySolarPanel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSolarPanel extends Block {

	public BlockSolarPanel() {
		super(Material.IRON);
		this.blockResistance = Float.MAX_VALUE;
		this.blockHardness = -1.0F;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntitySolarPanel();
	}

	public int getEnergyStored(World world, BlockPos pos) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof TileEntitySolarPanel) {
			return ((TileEntitySolarPanel) tileEntity).getEnergyStored();
		}
		return 0;
	}

	public int getMaxEnergyStored(World world, BlockPos pos) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof TileEntitySolarPanel) {
			return ((TileEntitySolarPanel) tileEntity).getMaxEnergyStored();
		}
		return 0;
	}

	public int receiveEnergy(World world, BlockPos pos, int energy) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof TileEntitySolarPanel) {
			return ((TileEntitySolarPanel) tileEntity).receiveEnergy(energy);
		}
		return 0;
	}

	public int extractEnergy(World world, BlockPos pos, int energy) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof TileEntitySolarPanel) {
			return ((TileEntitySolarPanel) tileEntity).extractEnergy(energy);
		}
		return 0;
	}

}
