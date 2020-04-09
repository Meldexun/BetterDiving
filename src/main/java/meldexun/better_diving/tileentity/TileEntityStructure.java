package meldexun.better_diving.tileentity;

import java.util.UUID;

import meldexun.better_diving.structure.manager.ModuleManager;
import meldexun.better_diving.structure.modules.SeabaseModule;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;

public class TileEntityStructure extends TileEntity {

	private UUID moduleUUID;
	private SeabaseModule module;
	private boolean isDoor;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (this.moduleUUID != null) {
			compound.setTag("moduleUUID", NBTUtil.createUUIDTag(this.moduleUUID));
		}
		compound.setBoolean("isDoor", this.isDoor);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("moduleUUID")) {
			this.moduleUUID = NBTUtil.getUUIDFromTag(compound.getCompoundTag("moduleUUID"));
		}
		this.isDoor = compound.getBoolean("isDoor");
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		if (this.world != null && !this.world.isRemote) {
			if (this.module != null) {
				this.moduleUUID = this.module.getUUID();
			}
		}
	}

	@Override
	public void onLoad() {
		super.onLoad();
		if (this.world != null && !this.world.isRemote) {
			if (this.moduleUUID != null) {
				ModuleManager moduleManager = ModuleManager.getInstanceForWorld(this.world);
				this.module = moduleManager.getModule(this.moduleUUID);
			}
		}
	}

	public void setModule(SeabaseModule module) {
		this.module = module;
		this.moduleUUID = module.getUUID();
	}

	public SeabaseModule getModule() {
		return this.module;
	}

	public void setDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}

	public boolean isDoor() {
		return this.isDoor;
	}

}
