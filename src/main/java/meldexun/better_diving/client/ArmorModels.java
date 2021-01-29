package meldexun.better_diving.client;

import meldexun.better_diving.client.model.armor.ModelDivingGear;
import meldexun.better_diving.client.model.armor.ModelDivingGearLegs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArmorModels {

	public static final ModelDivingGear model = new ModelDivingGear(false);
	public static final ModelDivingGear modelSlim = new ModelDivingGear(true);
	public static final ModelDivingGearLegs modelLegs = new ModelDivingGearLegs();

}
