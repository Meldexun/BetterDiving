package meldexun.better_diving.structure.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import meldexun.better_diving.block.BlockStructure;
import meldexun.better_diving.structure.manager.ModuleManager;
import meldexun.better_diving.tileentity.TileEntityStructure;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Door extends Wall {

	public SeabaseModule otherModule;
	private UUID otherModuleUUID;

	public Door(World world, SeabaseModule module) {
		super(world, module);
	}

	public Door(World world, SeabaseModule module, BlockStructure block, BlockPos startPos, BlockPos endPos, EnumFacing facing) {
		super(world, module, block, startPos, endPos, facing);
	}

	@Override
	public String toString() {
		return "Door(open=" + (this.otherModuleUUID != null) + ", x=" + this.startPos.getX() + ", y=" + this.startPos.getY() + ", z=" + this.startPos.getZ() + ")";
	}

	@Override
	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = super.writeToNBT();
		if (this.otherModule != null) {
			compound.setTag("otherModule", NBTUtil.createUUIDTag(this.otherModule.getUUID()));
		}
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("otherModule")) {
			this.otherModuleUUID = NBTUtil.getUUIDFromTag(compound.getCompoundTag("otherModule"));
		}
	}

	public void updateOtherModule() {
		if (!this.world.isRemote && this.otherModuleUUID != null) {
			ModuleManager moduleManager = ModuleManager.getInstanceForWorld(this.world);
			this.otherModule = moduleManager.getModule(this.otherModuleUUID);
		}
	}

	@Override
	public void generate() {
		if (!this.world.isRemote) {
			if (this.check()) {
				BlockPos position = this.module.getBlockPos().add(this.startPos).offset(this.facing);
				TileEntity tileEntity = this.world.getTileEntity(position);
				this.otherModule = ((TileEntityStructure) tileEntity).getModule();
				this.clear();
				Door otherDoor = this.otherModule.getDoorFromPos(position);
				if (otherDoor != null) {
					otherDoor.clear();
					otherDoor.otherModule = this.module;
				}
			} else {
				this.place();
			}
		}
	}

	public boolean check() {
		if (!this.world.isRemote) {
			List<SeabaseModule> list = new ArrayList<>();
			BlockPos pos = this.module.getBlockPos().add(this.startPos).offset(this.facing);
			for (int x = 0; x <= this.endPos.getX(); x++) {
				for (int y = 0; y <= this.endPos.getY(); y++) {
					for (int z = 0; z <= this.endPos.getZ(); z++) {
						TileEntity tileEntity = this.world.getTileEntity(pos.add(x, y, z));
						if (!(tileEntity instanceof TileEntityStructure) || !((TileEntityStructure) tileEntity).isDoor()) {
							return false;
						}
						list.add(((TileEntityStructure) tileEntity).getModule());
					}
				}
			}
			if (!list.isEmpty()) {
				SeabaseModule module1 = list.get(0);
				for (SeabaseModule module2 : list) {
					if (module1 != module2) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void delete() {
		if (!this.world.isRemote) {
			if (this.otherModule == null) {
				this.clear();
			} else {
				BlockPos position = this.module.getBlockPos().add(this.startPos).offset(this.facing);
				Door otherDoor = this.otherModule.getDoorFromPos(position);
				if (otherDoor != null) {
					otherDoor.place();
					otherDoor.otherModule = null;
				}
			}
		}
	}

	@Override
	protected void place() {
		if (!this.world.isRemote) {
			BlockPos pos = this.module.getBlockPos().add(this.startPos);
			for (int x = 0; x <= this.endPos.getX(); x++) {
				for (int y = 0; y <= this.endPos.getY(); y++) {
					for (int z = 0; z <= this.endPos.getZ(); z++) {
						this.block.placeBlock(this.world, pos.add(x, y, z), 3, this.module, true);
					}
				}
			}
		}
	}

}
