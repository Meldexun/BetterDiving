package meldexun.better_diving.oxygenprovider;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class OxygenProviderManager {

	private static final OxygenProviderManager INSTANCE = new OxygenProviderManager();
	private final List<OxygenProviderItem> oxygenProviderItemList = new ArrayList<>();
	private final List<OxygenProviderEntity> oxygenProviderEntityList = new ArrayList<>();

	public static OxygenProviderManager getInstance() {
		return INSTANCE;
	}

	public void loadOxygenProviderItems(List<String> config) {
		this.oxygenProviderItemList.clear();
		for (String s : config) {
			OxygenProviderItem oxygenProviderItem = new OxygenProviderItem(s);
			if (oxygenProviderItem.isValid()) {
				this.oxygenProviderItemList.add(oxygenProviderItem);
			}
		}
	}

	public void loadOxygenProviderEntities(List<String> config) {
		this.oxygenProviderEntityList.clear();
		for (String s : config) {
			OxygenProviderEntity oxygenProviderEntity = new OxygenProviderEntity(s);
			if (oxygenProviderEntity.isValid()) {
				this.oxygenProviderEntityList.add(oxygenProviderEntity);
			}
		}
	}

	@Nullable
	public OxygenProviderItem getOxygenProviderItem(ItemStack stack) {
		if (stack.isEmpty()) {
			return null;
		}
		Item item = stack.getItem();
		for (OxygenProviderItem oxygenProviderItem : this.oxygenProviderItemList) {
			if (oxygenProviderItem.getItem() == item) {
				return oxygenProviderItem;
			}
		}
		return null;
	}

	@Nullable
	public OxygenProviderEntity getOxygenProviderEntity(Entity entity) {
		ResourceLocation registryName = entity.getType().getRegistryName();
		for (OxygenProviderEntity oxygenProviderEntity : this.oxygenProviderEntityList) {
			if (oxygenProviderEntity.getRegistryName().equals(registryName)) {
				return oxygenProviderEntity;
			}
		}
		return null;
	}

}
