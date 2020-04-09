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
import meldexun.better_diving.registry.ModuleRegistry;
import meldexun.better_diving.structure.modules.SeabaseModule;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ModuleManager {

	private static final Map<World, ModuleManager> instances = new HashMap<>();
	private final Map<UUID, SeabaseModule> modules = new HashMap<>();
	private final World world;
	private final File folder;

	public ModuleManager(World world) {
		this.world = world;
		int dim = world.provider.getDimension();
		if (dim == 0) {
			this.folder = new File(world.getSaveHandler().getWorldDirectory(), "data/better_diving/modules");
		} else {
			this.folder = new File(world.getSaveHandler().getWorldDirectory(), "DIM" + dim + "/data/better_diving/modules");
		}
	}

	public static void onWorldSave(World world) {
		if (!world.isRemote) {
			ModuleManager.getInstanceForWorld(world).saveData();
		}
	}

	public static void onWorldLoad(World world) {
		if (!world.isRemote) {
			ModuleManager.createInstanceForWorld(world);
			ModuleManager.getInstanceForWorld(world).loadData();
		}
	}

	public static void onWorldUnload(World world) {
		if (!world.isRemote) {
			ModuleManager.deleteInstanceForWorld(world);
		}
	}

	@Nullable
	public static ModuleManager getInstanceForWorld(World world) {
		if (world != null && !world.isRemote) {
			return ModuleManager.instances.get(world);
		}
		return null;
	}

	private static boolean createInstanceForWorld(World world) {
		if (world != null && !world.isRemote && !ModuleManager.instances.containsKey(world)) {
			ModuleManager.instances.put(world, new ModuleManager(world));
			return true;
		}
		return false;
	}

	private static boolean deleteInstanceForWorld(World world) {
		if (world != null && !world.isRemote && ModuleManager.instances.containsKey(world)) {
			ModuleManager.instances.remove(world);
			return true;
		}
		return false;
	}

	public boolean addModule(SeabaseModule module) {
		if (module != null && !this.modules.containsKey(module.getUUID())) {
			this.modules.put(module.getUUID(), module);
			return true;
		}
		return false;
	}

	@Nullable
	public SeabaseModule getModule(UUID uuid) {
		if (uuid != null) {
			return this.modules.get(uuid);
		}
		return null;
	}

	public boolean hasModule(UUID uuid) {
		if (uuid != null) {
			return this.modules.containsKey(uuid);
		}
		return false;
	}

	public boolean removeModule(UUID uuid) {
		if (uuid != null && this.modules.containsKey(uuid)) {
			this.modules.remove(uuid);
			return true;
		}
		return false;
	}

	public void deleteInvalidModules() {
		List<SeabaseModule> list = new LinkedList<>();
		for (SeabaseModule module : this.modules.values()) {
			if (!module.exists()) {
				list.add(module);
			}
		}
		for (SeabaseModule module : list) {
			module.delete();
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
			for (SeabaseModule module : this.modules.values()) {
				this.createFileFromModule(this.folder, module);
			}
		}
	}

	public void createFileFromModule(File folder, SeabaseModule module) {
		File file = new File(folder, module.getUUID().toString() + ".nbt");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			try (OutputStream outputStream = new FileOutputStream(file)) {
				CompressedStreamTools.writeCompressed(module.writeToNBT(), outputStream);
			}
		} catch (IOException e) {
			BetterDiving.logger.error("Failed to save seabase module to file: " + file.getName(), e);
		}
	}

	public void loadData() {
		if (!this.world.isRemote) {
			if (!this.folder.exists()) {
				this.folder.mkdirs();
			}
			for (File file : FileUtils.listFiles(this.folder, new String[] { "nbt" }, false)) {
				this.createModuleFromFile(file);
			}
			for (SeabaseModule module : this.modules.values()) {
				module.updateDoors();
			}
		}
	}

	public SeabaseModule createModuleFromFile(File file) {
		try (InputStream inputStream = new FileInputStream(file)) {
			NBTTagCompound compound = CompressedStreamTools.readCompressed(inputStream);
			ResourceLocation registryName = new ResourceLocation(compound.getString("id"));
			SeabaseModule module = ModuleRegistry.getInstance().createModuleFromResourceLocation(this.world, registryName);
			if (module != null) {
				module.readFromNBT(compound);
				this.addModule(module);
				return module;
			}
		} catch (IOException e) {
			BetterDiving.logger.error("Failed to load seabase module from file: " + file.getName(), e);
		}
		return null;
	}

}
