package meldexun.better_diving.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ITickable;

public class TileEntitySolarPanel extends TileEntityEnergyGenerator implements ITickable {

	public TileEntitySolarPanel() {
		super(7500, 1000, 1000, 0);
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

}
