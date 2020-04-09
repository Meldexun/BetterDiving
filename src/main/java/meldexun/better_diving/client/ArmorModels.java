package meldexun.better_diving.client;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.model.armor.ModelCustomArmor;
import meldexun.better_diving.client.model.armor.ModelDivingGear;
import meldexun.better_diving.client.model.armor.ModelDivingGearLegs;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Side.CLIENT)
public class ArmorModels {

	private ArmorModels() {

	}

	public static ModelCustomArmor divingGearModel;
	public static ModelCustomArmor divingGearModelSlim;
	public static ModelCustomArmor divingGearModelLegs;

	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		ArmorModels.divingGearModel = new ModelDivingGear(0.0F, false);
		ArmorModels.divingGearModelSlim = new ModelDivingGear(0.0F, true);
		ArmorModels.divingGearModelLegs = new ModelDivingGearLegs(0.0F);
	}

}
