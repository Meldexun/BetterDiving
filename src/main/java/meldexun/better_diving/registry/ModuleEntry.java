package meldexun.better_diving.registry;

import meldexun.better_diving.structure.modules.SeabaseModule;

public class ModuleEntry {

	public final Class<? extends SeabaseModule> moduleClass;

	public ModuleEntry(Class<? extends SeabaseModule> moduleClass) {
		this.moduleClass = moduleClass;
	}

}
