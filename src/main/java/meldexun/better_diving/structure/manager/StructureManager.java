package meldexun.better_diving.structure.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.commons.io.FileUtils;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.structure.SeabaseStructure;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class StructureManager {

	private static final Map<World, StructureManager> instances = new HashMap<>();
	private final Map<UUID, SeabaseStructure> structures = new HashMap<>();
	private final World world;
	private final File folder;

	public StructureManager(World world) {
		this.world = world;
		int dim = world.provider.getDimension();
		if (dim == 0) {
			this.folder = new File(world.getSaveHandler().getWorldDirectory(), "data/better_diving/structures");
		} else {
			this.folder = new File(world.getSaveHandler().getWorldDirectory(), "DIM" + dim + "/data/better_diving/structures");
		}
	}

	public static void onWorldSave(World world) {
		if (!world.isRemote) {
			StructureManager.getInstanceForWorld(world).saveData();
		}
	}

	public static void onWorldLoad(World world) {
		if (!world.isRemote) {
			StructureManager.createInstanceForWorld(world);
			StructureManager.getInstanceForWorld(world).loadData();
		}
	}

	public static void onWorldUnload(World world) {
		if (!world.isRemote) {
			StructureManager.deleteInstanceForWorld(world);
		}
	}

	@Nullable
	public static StructureManager getInstanceForWorld(World world) {
		if (world != null && !world.isRemote) {
			return StructureManager.instances.get(world);
		}
		return null;
	}

	private static void createInstanceForWorld(World world) {
		if (world != null && !world.isRemote && !StructureManager.instances.containsKey(world)) {
			StructureManager.instances.put(world, new StructureManager(world));
		}
	}

	private static void deleteInstanceForWorld(World world) {
		if (world != null && !world.isRemote && StructureManager.instances.containsKey(world)) {
			StructureManager.instances.remove(world);
		}
	}

	public boolean addStructure(SeabaseStructure structure) {
		if (structure != null && !this.structures.containsKey(structure.getUUID())) {
			this.structures.put(structure.getUUID(), structure);
			return true;
		}
		return false;
	}

	public SeabaseStructure getStructure(UUID uuid) {
		if (uuid != null) {
			return this.structures.get(uuid);
		}
		return null;
	}

	public boolean hasStructure(UUID uuid) {
		if (uuid != null) {
			return this.structures.containsKey(uuid);
		}
		return false;
	}

	public boolean removeStructure(UUID uuid) {
		if (uuid != null && this.structures.containsKey(uuid)) {
			this.structures.remove(uuid);
			return true;
		}
		return false;
	}

	public void deleteInvalidModules() {
		List<SeabaseStructure> list = new LinkedList<>();
		for (SeabaseStructure structure : this.structures.values()) {
			if (!structure.exists()) {
				list.add(structure);
			}
		}
		for (SeabaseStructure structure : list) {
			structure.delete();
		}
	}

	public void saveData() {
		if (!this.world.isRemote) {
			if (!this.folder.exists()) {
				this.folder.mkdirs();
			}
			for (File file : FileUtils.listFiles(this.folder, new String[] { "nbt" }, false)) {
				file.delete();
			}
			this.deleteInvalidModules();
			for (SeabaseStructure structure : this.structures.values()) {
				this.createFileFromStructure(this.folder, structure);
			}
		}
	}

	public void createFileFromStructure(File folder, SeabaseStructure structure) {
		File file = new File(folder, structure.getUUID().toString() + ".nbt");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			try (OutputStream outputStream = new FileOutputStream(file)) {
				CompressedStreamTools.writeCompressed(structure.writeToNBT(), outputStream);
			}
		} catch (IOException e) {
			BetterDiving.logger.error("Failed to save seabase structure to file: " + file.getName(), e);
		}
	}

	public void loadData() {
		if (!this.world.isRemote) {
			if (!this.folder.exists()) {
				this.folder.mkdirs();
			}
			for (File file : FileUtils.listFiles(this.folder, new String[] { "nbt" }, false)) {
				this.createStructureFromFile(file);
			}
		}
	}

	public SeabaseStructure createStructureFromFile(File file) {
		try (InputStream inputStream = new FileInputStream(file)) {
			NBTTagCompound compound = CompressedStreamTools.readCompressed(inputStream);
			SeabaseStructure structure = new SeabaseStructure(this.world);
			structure.readFromNBT(compound);
			this.addStructure(structure);
			return structure;
		} catch (IOException e) {
			BetterDiving.logger.error("Failed to load seabase structure from file: " + file.getName(), e);
		}
		return null;
	}

}
