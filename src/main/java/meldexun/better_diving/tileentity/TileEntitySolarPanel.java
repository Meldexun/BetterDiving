package meldexun.better_diving.tileentity;

import meldexun.better_diving.capability.energy.CapabilityEnergyStorage;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntitySolarPanel extends TileEntity implements ITickable {

	private final CapabilityEnergyStorage energy = new CapabilityEnergyStorage(7500, 75, 75, 0);

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY ? true : super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY ? (T) this.energy : super.getCapability(capability, facing);
	}

	@Override
	public void update() {
		int water = 0;
		boolean canSeeSky = true;
		for (int i = 1; i <= 256; i++) {
			IBlockState state = this.world.getBlockState(this.pos.up(i));
			Block block = state.getBlock();
			if (block != Blocks.AIR) {
				if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
					water++;
				} else {
					canSeeSky = false;
					break;
				}
			}
		}
		if (canSeeSky) {
			double energy = 0.0004D * ((double) water - 250) * ((double) water - 250) / 20.0D;
			this.receiveEnergy((int) energy);
		}
	}

	public int getEnergyStored() {
		return this.energy.getEnergyStored();
	}

	public int getMaxEnergyStored() {
		return this.energy.getMaxEnergyStored();
	}

	public int receiveEnergy(int energy) {
		return this.energy.receiveEnergy(energy, false);
	}

	public int extractEnergy(int energy) {
		return this.energy.extractEnergy(energy, false);
	}

}
