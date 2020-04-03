package meldexun.better_diving.tileentity;

import java.util.Random;

import meldexun.better_diving.block.AbstractBlockCreepvine;
import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityCreepvine extends TileEntity implements ITickable {

	public int maxHeight;
	public boolean generateSeeds;
	public Random rand = new Random();

	public TileEntityCreepvine() {
		this(AbstractBlockCreepvine.MAX_HEIGHT, false);
	}

	public TileEntityCreepvine(int maxHeight, boolean generateSeeds) {
		this.maxHeight = maxHeight;
		this.generateSeeds = generateSeeds;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("maxHeight", this.maxHeight);
		compound.setBoolean("canGenerateSeeds", this.generateSeeds);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.maxHeight = compound.getInteger("maxHeight");
		this.generateSeeds = compound.getBoolean("canGenerateSeeds");
	}

	@Override
	public void update() {
		if (!this.world.isRemote) {
			if (this.world.getBlockState(this.pos).getBlock() == ModBlocks.CREEPVINE_TOP) {
				if (this.rand.nextInt(1800) == 0) {
					int height = this.getCreepvineHeight();
					if (height < this.maxHeight) {
						if (ModBlocks.CREEPVINE_TOP.canPlaceBlockAt(this.world, this.pos.up())) {
							ModBlocks.CREEPVINE.setCreepvine(this.world, this.pos, 3, this.maxHeight, this.generateSeeds);
							ModBlocks.CREEPVINE_TOP.setCreepvine(this.world, this.pos.up(), 3, this.maxHeight, this.generateSeeds);
						}
					} else if (BetterDivingConfig.PLANTS.shouldGenerateCreepvineSeedCluster && this.generateSeeds && this.rand.nextBoolean()) {
						ModBlocks.CREEPVINE_SEED.setCreepvine(this.world, this.pos.down(height / 2), 3, this.maxHeight, this.generateSeeds);
					}
				}
			}
		}
	}

	public int getCreepvineHeight() {
		int i = 1;
		while (this.world.getBlockState(this.pos.down(i)).getBlock() instanceof AbstractBlockCreepvine) {
			i++;
		}
		return i;
	}

}
