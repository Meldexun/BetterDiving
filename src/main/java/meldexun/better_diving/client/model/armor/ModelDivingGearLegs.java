package meldexun.better_diving.client.model.armor;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelDivingGearLegs extends ModelCustomArmor {

	public ModelDivingGearLegs() {
		super(64, 32);

		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.252F, false);

		this.bipedRightLeg = new ModelRenderer(this, 40, 16);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.251F, false);

		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.251F, false);

		this.bipedHead = new ModelRenderer(this);
		this.bipedHead.showModel = false;
		this.bipedHeadwear = new ModelRenderer(this);
		this.bipedHeadwear.showModel = false;
		this.bipedRightArm = new ModelRenderer(this);
		this.bipedRightArm.showModel = false;
		this.bipedLeftArm = new ModelRenderer(this);
		this.bipedLeftArm.showModel = false;
	}

}
