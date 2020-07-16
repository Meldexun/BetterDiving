package meldexun.better_diving.structure.modules;

import java.util.ArrayList;
import java.util.List;

import meldexun.better_diving.block.BlockStructure;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class Wall {

	public final World world;
	protected BlockPos startPos;
	protected BlockPos endPos;
	protected EnumFacing facing;
	protected SeabaseModule module;
	protected BlockStructure block;

	public Wall(World world, SeabaseModule module) {
		this.world = world;
		this.module = module;
	}

	public Wall(World world, SeabaseModule module, BlockStructure block, BlockPos startPos, BlockPos endPos, EnumFacing facing) {
		this.world = world;
		this.module = module;
		this.block = block;
		BlockPos minPos = new BlockPos(Math.min(startPos.getX(), endPos.getX()), Math.min(startPos.getY(), endPos.getY()), Math.min(startPos.getZ(), endPos.getZ()));
		BlockPos maxPos = new BlockPos(Math.max(startPos.getX(), endPos.getX()), Math.max(startPos.getY(), endPos.getY()), Math.max(startPos.getZ(), endPos.getZ()));
		this.startPos = minPos;
		this.endPos = maxPos.subtract(minPos);
		this.facing = facing;
	}

	@Override
	public String toString() {
		return "Wall(x=" + this.startPos.getX() + ", y=" + this.startPos.getY() + ", z=" + this.startPos.getZ() + ")";
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setTag("start", NBTUtil.createPosTag(this.startPos));
		compound.setTag("end", NBTUtil.createPosTag(this.endPos));
		compound.setInteger("face", this.facing.getIndex());
		compound.setString("block", this.block.getRegistryName().toString());
		return compound;
	}

	public void readFromNBT(NBTTagCompound compound) {
		this.startPos = NBTUtil.getPosFromTag(compound.getCompoundTag("start"));
		this.endPos = NBTUtil.getPosFromTag(compound.getCompoundTag("end"));
		this.facing = EnumFacing.byIndex(compound.getInteger("face"));
		this.block = (BlockStructure) ForgeRegistries.BLOCKS.getValue(new ResourceLocation(compound.getString("block")));
	}

	public List<BlockPos> getBlockPosList() {
		if (!this.world.isRemote) {
			List<BlockPos> list = new ArrayList<>();
			BlockPos pos = this.module.getBlockPos().add(this.startPos);
			for (int x = 0; x <= this.endPos.getX(); x++) {
				for (int y = 0; y <= this.endPos.getY(); y++) {
					for (int z = 0; z <= this.endPos.getZ(); z++) {
						list.add(pos.add(x, y, z));
					}
				}
			}
			return list;
		}
		return new ArrayList<>();
	}

	public boolean canGenerate() {
		if (!this.world.isRemote) {
			BlockPos pos = this.module.getBlockPos().add(this.startPos);
			for (int x = 0; x <= this.endPos.getX(); x++) {
				for (int y = 0; y <= this.endPos.getY(); y++) {
					for (int z = 0; z <= this.endPos.getZ(); z++) {
						BlockPos position = pos.add(x, y, z);
						if (!this.world.getBlockState(position).getBlock().isReplaceable(this.world, position)) {
							return false;
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	public void generate() {
		if (!this.world.isRemote) {
			this.place();
		}
	}

	public void delete() {
		if (!this.world.isRemote) {
			this.clear();
		}
	}

	protected void place() {
		if (!this.world.isRemote) {
			BlockPos pos = this.module.getBlockPos().add(this.startPos);
			for (int x = 0; x <= this.endPos.getX(); x++) {
				for (int y = 0; y <= this.endPos.getY(); y++) {
					for (int z = 0; z <= this.endPos.getZ(); z++) {
						this.block.placeBlock(this.world, pos.add(x, y, z), 3, this.module, false);
					}
				}
			}
		}
	}

	protected void clear() {
		if (!this.world.isRemote) {
			BlockPos pos = this.module.getBlockPos().add(this.startPos);
			for (int x = 0; x <= this.endPos.getX(); x++) {
				for (int y = 0; y <= this.endPos.getY(); y++) {
					for (int z = 0; z <= this.endPos.getZ(); z++) {
						this.world.setBlockToAir(pos.add(x, y, z));
					}
				}
			}
		}
	}

}
