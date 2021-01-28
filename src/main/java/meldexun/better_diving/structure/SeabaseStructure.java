package meldexun.better_diving.structure;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import meldexun.better_diving.structure.manager.ModuleManager;
import meldexun.better_diving.structure.manager.StructureManager;
import meldexun.better_diving.structure.modules.SeabaseModule;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class SeabaseStructure {

	public final World world;
	private UUID uuid = MathHelper.getRandomUUID();
	private Set<SeabaseModule> moduleList = new HashSet<>();

	public SeabaseStructure(World world) {
		this.world = world;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("Seabase Structure(uuid=" + this.uuid);
		for (SeabaseModule module : this.moduleList) {
			s.append(", " + module);
		}
		s.append(")");
		return s.toString();
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public boolean addSeabaseModule(SeabaseModule module) {
		if (!this.world.isRemote) {
			return this.moduleList.add(module);
		}
		return false;
	}

	public boolean removeSeabaseModule(SeabaseModule module) {
		if (!this.world.isRemote && this.moduleList.contains(module)) {
			this.moduleList.remove(module);
			this.split();
			return true;
		}
		return false;
	}

	public boolean exists() {
		return !this.moduleList.isEmpty();
	}

	public void delete() {
		this.moduleList.clear();
		StructureManager.getInstanceForWorld(this.world).removeStructure(this.uuid);
	}

	public static void merge(SeabaseStructure structure1, SeabaseStructure structure2) {
		if (structure1.moduleList.size() >= structure2.moduleList.size()) {
			structure1.merge(structure2);
		} else {
			structure2.merge(structure1);
		}
	}

	public void merge(SeabaseStructure structure) {
		if (structure != this && this.world == structure.world) {
			for (SeabaseModule module : structure.moduleList) {
				this.addSeabaseModule(module);
				module.setStructure(this);
			}
			structure.delete();
		}
	}

	public void split() {
		if (this.moduleList.isEmpty()) {
			this.delete();
		} else if (this.moduleList.size() > 1) {
			Set<SeabaseModule> modules = this.moduleList.iterator().next().getConnectedModules(new HashSet<>());

			if (modules.size() < this.moduleList.size()) {
				SeabaseStructure structure = new SeabaseStructure(this.world);
				for (SeabaseModule module : modules) {
					this.moduleList.remove(module);
					structure.addSeabaseModule(module);
					module.setStructure(structure);
				}
				StructureManager.getInstanceForWorld(this.world).addStructure(structure);

				this.split();
			}
		}
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();

		compound.setTag("uuid", NBTUtil.createUUIDTag(this.uuid));

		NBTTagList nbtTagList = new NBTTagList();
		for (SeabaseModule module : this.moduleList) {
			nbtTagList.appendTag(NBTUtil.createUUIDTag(module.getUUID()));
		}
		compound.setTag("moduleList", nbtTagList);

		return compound;
	}

	public void readFromNBT(NBTTagCompound compound) {
		this.moduleList.clear();

		this.uuid = NBTUtil.getUUIDFromTag(compound.getCompoundTag("uuid"));

		NBTTagList nbtTagList = compound.getTagList("moduleList", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < nbtTagList.tagCount(); i++) {
			NBTTagCompound tag = nbtTagList.getCompoundTagAt(i);
			SeabaseModule module = ModuleManager.getInstanceForWorld(this.world).getModule(NBTUtil.getUUIDFromTag(tag));
			if (module != null) {
				module.setStructure(this);
				this.moduleList.add(module);
			}
		}
	}

}
