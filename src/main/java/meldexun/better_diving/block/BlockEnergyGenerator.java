package meldexun.better_diving.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class BlockEnergyGenerator extends BlockStructurePart {

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public abstract TileEntity createTileEntity(World world, IBlockState state);

	public int getEnergyStored(World world, BlockPos pos) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity != null) {
			IEnergyStorage ienergy = tileEntity.getCapability(CapabilityEnergy.ENERGY, null);
			if (ienergy != null) {
				return ienergy.getEnergyStored();
			}
		}
		return 0;
	}

	public int getMaxEnergyStored(World world, BlockPos pos) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity != null) {
			IEnergyStorage ienergy = tileEntity.getCapability(CapabilityEnergy.ENERGY, null);
			if (ienergy != null) {
				return ienergy.getMaxEnergyStored();
			}
		}
		return 0;
	}

	public int receiveEnergy(World world, BlockPos pos, int energy) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity != null) {
			IEnergyStorage ienergy = tileEntity.getCapability(CapabilityEnergy.ENERGY, null);
			if (ienergy != null) {
				return ienergy.receiveEnergy(energy, false);
			}
		}
		return 0;
	}

	public int extractEnergy(World world, BlockPos pos, int energy) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity != null) {
			IEnergyStorage ienergy = tileEntity.getCapability(CapabilityEnergy.ENERGY, null);
			if (ienergy != null) {
				return ienergy.extractEnergy(energy, false);
			}
		}
		return 0;
	}

}
