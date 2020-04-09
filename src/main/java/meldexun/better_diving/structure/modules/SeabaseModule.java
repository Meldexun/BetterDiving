package meldexun.better_diving.structure.modules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import meldexun.better_diving.block.BlockStructure;
import meldexun.better_diving.structure.SeabaseStructure;
import meldexun.better_diving.structure.manager.ModuleManager;
import meldexun.better_diving.structure.manager.StructureManager;
import meldexun.better_diving.tileentity.TileEntityStructure;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class SeabaseModule extends IForgeRegistryEntry.Impl<SeabaseModule> {

	public final World world;
	private UUID uuid = MathHelper.getRandomUUID();
	private int x;
	private int y;
	private int z;
	private Rotation rotation;
	private final List<Wall> wallList = new ArrayList<>();
	private final List<Door> doorList = new ArrayList<>();
	private final Set<BlockPos> structurePartList = new HashSet<>();
	private final Set<BlockPos> energyGeneratorList = new HashSet<>();
	private SeabaseStructure structure;

	public SeabaseModule(World world) {
		this.world = world;
	}

	public SeabaseModule(World world, int x, int y, int z, Rotation rot) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.rotation = rot;
	}

	@Override
	public String toString() {
		return "Seabase Module(uuid=" + this.uuid + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + ")";
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public BlockPos getBlockPos() {
		return new BlockPos(this.x, this.y, this.z);
	}

	public void setStructure(SeabaseStructure structure) {
		this.structure = structure;
	}

	public SeabaseStructure getStructure() {
		return this.structure;
	}

	public void addWall(BlockStructure block, BlockPos start, BlockPos end, EnumFacing facing) {
		this.wallList.add(new Wall(this.world, this, block, start.rotate(this.rotation), end.rotate(this.rotation), this.getRotatedFacing(facing)));
	}

	public void addDoor(BlockStructure block, BlockPos start, BlockPos end, EnumFacing facing) {
		this.doorList.add(new Door(this.world, this, block, start.rotate(this.rotation), end.rotate(this.rotation), this.getRotatedFacing(facing)));
	}

	public void addStructurePart(BlockPos pos) {
		if (!this.world.isRemote && !this.structurePartList.contains(pos)) {
			this.structurePartList.add(pos);
			TileEntity tileEntity = this.world.getTileEntity(pos);
			if (tileEntity != null && tileEntity.hasCapability(CapabilityEnergy.ENERGY, null)) {
				this.energyGeneratorList.add(pos);
			}
		}
	}

	public boolean removeStructurePart(BlockPos pos) {
		if (!this.world.isRemote && this.structurePartList.contains(pos)) {
			this.structurePartList.remove(pos);
			this.energyGeneratorList.remove(pos);
			return true;
		}
		return false;
	}

	public int getEnergyStored() {
		if (!this.world.isRemote) {
			int energy = 0;
			for (BlockPos pos : this.energyGeneratorList) {
				TileEntity tileEntity = this.world.getTileEntity(pos);
				if (tileEntity != null && tileEntity.hasCapability(CapabilityEnergy.ENERGY, null)) {
					energy += tileEntity.getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored();
				}
			}
			return energy;
		}
		return 0;
	}

	public int getMaxEnergyStored() {
		if (!this.world.isRemote) {
			int energy = 0;
			for (BlockPos pos : this.energyGeneratorList) {
				TileEntity tileEntity = this.world.getTileEntity(pos);
				if (tileEntity != null && tileEntity.hasCapability(CapabilityEnergy.ENERGY, null)) {
					energy += tileEntity.getCapability(CapabilityEnergy.ENERGY, null).getMaxEnergyStored();
				}
			}
			return energy;
		}
		return 0;
	}

	public int extractEnergy(int amount, boolean simulate) {
		if (!this.world.isRemote) {
			amount = Math.abs(amount);
			int energyExtracted = 0;
			for (BlockPos pos : this.energyGeneratorList) {
				if (energyExtracted >= amount) {
					break;
				}
				TileEntity tileEntity = this.world.getTileEntity(pos);
				if (tileEntity != null && tileEntity.hasCapability(CapabilityEnergy.ENERGY, null)) {
					energyExtracted += tileEntity.getCapability(CapabilityEnergy.ENERGY, null).extractEnergy(amount - energyExtracted, simulate);
				}
			}
			return energyExtracted;
		}
		return 0;
	}

	public int receiveEnergy(int amount, boolean simulate) {
		if (!this.world.isRemote) {
			amount = Math.abs(amount);
			int energyReceived = 0;
			for (BlockPos pos : this.energyGeneratorList) {
				if (energyReceived >= amount) {
					break;
				}
				TileEntity tileEntity = this.world.getTileEntity(pos);
				if (tileEntity != null && tileEntity.hasCapability(CapabilityEnergy.ENERGY, null)) {
					energyReceived += tileEntity.getCapability(CapabilityEnergy.ENERGY, null).receiveEnergy(amount - energyReceived, simulate);
				}
			}
			return energyReceived;
		}
		return 0;
	}

	private EnumFacing getRotatedFacing(EnumFacing facing) {
		if (facing.getAxis() != Axis.Y) {
			if (this.rotation == Rotation.CLOCKWISE_90) {
				return facing.rotateY();
			} else if (this.rotation == Rotation.CLOCKWISE_180) {
				return facing.rotateY().rotateY();
			} else if (this.rotation == Rotation.COUNTERCLOCKWISE_90) {
				return facing.rotateYCCW();
			}
		}
		return facing;
	}

	public Wall getWallFromPos(BlockPos pos) {
		for (Wall wall : this.wallList) {
			BlockPos start = this.getBlockPos().add(wall.startPos);
			BlockPos end = start.add(wall.endPos);
			if (pos.getX() >= start.getX() && pos.getY() >= start.getY() && pos.getZ() >= start.getZ() && pos.getX() <= end.getX() && pos.getY() <= end.getY() && pos.getZ() <= end.getZ()) {
				return wall;
			}
		}
		return null;
	}

	public Door getDoorFromPos(BlockPos pos) {
		for (Door door : this.doorList) {
			BlockPos start = this.getBlockPos().add(door.startPos);
			BlockPos end = start.add(door.endPos);
			if (pos.getX() >= start.getX() && pos.getY() >= start.getY() && pos.getZ() >= start.getZ() && pos.getX() <= end.getX() && pos.getY() <= end.getY() && pos.getZ() <= end.getZ()) {
				return door;
			}
		}
		return null;
	}

	/**
	 * Checks if at least one tile entity with a reference to this module still exists.
	 */
	public boolean exists() {
		for (Wall wall : this.wallList) {
			for (BlockPos pos : wall.getBlockPosList()) {
				TileEntity tileEntity = this.world.getTileEntity(pos);
				if (tileEntity instanceof TileEntityStructure && ((TileEntityStructure) tileEntity).getModule() == this) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Removes this module from its structure and from the module manager.
	 */
	public void delete() {
		if (this.structure != null) {
			this.structure.removeSeabaseModule(this);
		}
		ModuleManager.getInstanceForWorld(this.world).removeModule(this.uuid);
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();

		compound.setString("id", this.getRegistryName().toString());
		compound.setTag("uuid", NBTUtil.createUUIDTag(this.uuid));
		compound.setInteger("x", this.x);
		compound.setInteger("y", this.y);
		compound.setInteger("z", this.z);
		compound.setInteger("rot", this.rotation.ordinal());

		NBTTagList nbtTagList = new NBTTagList();
		for (Wall wall : this.wallList) {
			nbtTagList.appendTag(wall.writeToNBT());
		}
		compound.setTag("wallList", nbtTagList);

		NBTTagList nbtTagList1 = new NBTTagList();
		for (Door door : this.doorList) {
			nbtTagList1.appendTag(door.writeToNBT());
		}
		compound.setTag("doorList", nbtTagList1);

		NBTTagList nbtTagList2 = new NBTTagList();
		for (BlockPos pos : this.structurePartList) {
			nbtTagList2.appendTag(NBTUtil.createPosTag(pos));
		}
		compound.setTag("structurePartList", nbtTagList2);

		NBTTagList nbtTagList3 = new NBTTagList();
		for (BlockPos pos : this.energyGeneratorList) {
			nbtTagList3.appendTag(NBTUtil.createPosTag(pos));
		}
		compound.setTag("energyGeneratorList", nbtTagList3);

		return compound;
	}

	public void readFromNBT(NBTTagCompound compound) {
		this.wallList.clear();
		this.doorList.clear();
		this.structurePartList.clear();
		this.energyGeneratorList.clear();

		this.x = compound.getInteger("x");
		this.y = compound.getInteger("y");
		this.z = compound.getInteger("z");
		this.rotation = Rotation.values()[compound.getInteger("rot")];

		NBTTagList nbtTagList = compound.getTagList("wallList", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < nbtTagList.tagCount(); i++) {
			NBTTagCompound tag = nbtTagList.getCompoundTagAt(i);
			Wall wall = new Wall(this.world, this);
			wall.readFromNBT(tag);
			this.wallList.add(wall);
		}

		NBTTagList nbtTagList1 = compound.getTagList("doorList", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < nbtTagList1.tagCount(); i++) {
			NBTTagCompound tag = nbtTagList1.getCompoundTagAt(i);
			Door door = new Door(this.world, this);
			door.readFromNBT(tag);
			this.doorList.add(door);
		}

		NBTTagList nbtTagList2 = compound.getTagList("structurePartList", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < nbtTagList2.tagCount(); i++) {
			NBTTagCompound tag = nbtTagList2.getCompoundTagAt(i);
			BlockPos pos = NBTUtil.getPosFromTag(tag);
			this.structurePartList.add(pos);
		}

		NBTTagList nbtTagList3 = compound.getTagList("energyGeneratorList", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < nbtTagList3.tagCount(); i++) {
			NBTTagCompound tag = nbtTagList3.getCompoundTagAt(i);
			BlockPos pos = NBTUtil.getPosFromTag(tag);
			this.energyGeneratorList.add(pos);
		}
	}

	public void updateDoors() {
		if (!this.world.isRemote) {
			for (Door door : this.doorList) {
				door.updateOtherModule();
			}
		}
	}

	/**
	 * Checks if this module can be generated and if no module with this UUID is already registered.
	 * If both conditions are met the module and a structure is generated.
	 */
	public boolean tryGenerate() {
		if (!this.world.isRemote) {
			ModuleManager moduleManager = ModuleManager.getInstanceForWorld(this.world);
			StructureManager structureManager = StructureManager.getInstanceForWorld(this.world);
			if (!moduleManager.hasModule(this.uuid) && this.canGenerate()) {
				SeabaseStructure seabaseStructure = new SeabaseStructure(this.world);
				this.structure = seabaseStructure;
				this.generate();
				moduleManager.addModule(this);
				seabaseStructure.addSeabaseModule(this);
				structureManager.addStructure(seabaseStructure);
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if this module can be generated in the world.
	 */
	public boolean canGenerate() {
		if (!this.world.isRemote) {
			for (Wall wall : this.wallList) {
				if (!wall.canGenerate()) {
					return false;
				}
			}
			for (Door door : this.doorList) {
				if (!door.canGenerate()) {
					return false;
				}
			}
			return this.canSetAirBlocks();
		}
		return false;
	}

	/**
	 * Places the blocks for this module in the world.<br>
	 * Does not check for existing blocks!
	 */
	public void generate() {
		if (!this.world.isRemote) {
			this.setAirBlocks();
			for (Wall wall : this.wallList) {
				wall.generate();
			}
			for (Door door : this.doorList) {
				door.generate();
			}
		}
	}

	/**
	 * Places the air blocks inside this module.
	 */
	public abstract void setAirBlocks();

	/**
	 * Checks if it can place the air blocks inside this module.
	 */
	public abstract boolean canSetAirBlocks();

	protected void setAirBlocks(BlockPos start, BlockPos end) {
		if (!this.world.isRemote) {
			start = start.rotate(this.rotation);
			end = end.rotate(this.rotation);
			BlockPos pos = this.getBlockPos().add(start);
			for (int posX = 0; posX < end.getX(); posX++) {
				for (int posY = 0; posY < end.getX(); posY++) {
					for (int posZ = 0; posZ < end.getX(); posZ++) {
						this.world.setBlockToAir(pos.add(posX, posY, posZ));
					}
				}
			}
		}
	}

	/**
	 * Calls {@link Wall#delete()} for all doors and walls of this module. Then calls {@link SeabaseModule#delete()}.
	 */
	public void destroy() {
		if (!this.world.isRemote) {
			for (Door door : this.doorList) {
				door.delete();
			}
			for (Wall wall : this.wallList) {
				wall.delete();
			}
			this.delete();
		}
	}

	/**
	 * Fills the passed set with all modules which are connected with this module and returns it.
	 */
	public Set<SeabaseModule> getConnectedModules(Set<SeabaseModule> list) {
		list.add(this);
		if (!this.world.isRemote) {
			for (Wall wall : this.wallList) {
				for (BlockPos pos : wall.getBlockPosList()) {
					this.getConnectedModules(pos, list);
				}
			}
		}
		return list;
	}

	private Set<SeabaseModule> getConnectedModules(BlockPos pos, Set<SeabaseModule> list) {
		if (!this.world.isRemote) {
			for (int i = 0; i < EnumFacing.values().length; i++) {
				TileEntity tileEntity = this.world.getTileEntity(pos.offset(EnumFacing.values()[i]));
				if (tileEntity instanceof TileEntityStructure) {
					TileEntityStructure tileEntityStructure = (TileEntityStructure) tileEntity;
					if (!tileEntityStructure.isDoor()) {
						SeabaseModule otherModule = tileEntityStructure.getModule();
						if (otherModule != this && !list.contains(otherModule)) {
							otherModule.getConnectedModules(list);
						}
					}
				}
			}
		}
		return list;
	}

}
