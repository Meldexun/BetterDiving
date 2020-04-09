package meldexun.better_diving.tileentity;

import meldexun.better_diving.block.AbstractBlockCreepvine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCreepvine extends TileEntity {

	private int maxHeight;
	private boolean canGenerateSeeds;

	public TileEntityCreepvine() {
		this(AbstractBlockCreepvine.MAX_HEIGHT, false);
	}

	public TileEntityCreepvine(int maxHeight, boolean generateSeeds) {
		this.maxHeight = maxHeight;
		this.canGenerateSeeds = generateSeeds;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		compound.setInteger("maxHeight", this.maxHeight);
		compound.setBoolean("canGenerateSeeds", this.canGenerateSeeds);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.maxHeight = compound.getInteger("maxHeight");
		this.canGenerateSeeds = compound.getBoolean("canGenerateSeeds");
	}

	public int getCreepvineHeight() {
		int i = 1;
		while (this.world.getBlockState(this.pos.down(i)).getBlock() instanceof AbstractBlockCreepvine) {
			i++;
		}
		return i;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getMaxHeight() {
		return this.maxHeight;
	}

	public void setCanGenerateSeeds(boolean canGenerateSeeds) {
		this.canGenerateSeeds = canGenerateSeeds;
	}

	public boolean canGenerateSeeds() {
		return this.canGenerateSeeds;
	}

}
