package meldexun.better_diving.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityBuilding extends TileEntity {

	public BlockPos basePos;
	public boolean isBase;
	public boolean isDoor;

	public boolean hasBasePos() {
		return this.basePos != null;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (this.hasBasePos()) {
			compound.setTag("basePos", NBTUtil.createPosTag(this.basePos));
		}
		compound.setBoolean("isBase", this.isBase);
		compound.setBoolean("isDoor", this.isDoor);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("basePos")) {
			this.basePos = NBTUtil.getPosFromTag(compound.getCompoundTag("basePos"));
		}
		this.isBase = compound.getBoolean("isBase");
		this.isDoor = compound.getBoolean("isDoor");
	}

}
