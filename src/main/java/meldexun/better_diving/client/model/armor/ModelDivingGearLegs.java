package meldexun.better_diving.client.model.armor;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDivingGearLegs extends ModelCustomArmor {

	public ModelDivingGearLegs(float scale) {
		super(scale, 64, 32);

		this.bipedBody = new ModelRenderer(this);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedBody.cubeList.add(new ModelBox(this.bipedBody, 16, 16, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.252F, false));

		this.bipedRightLeg = new ModelRenderer(this);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.bipedRightLeg.cubeList.add(new ModelBox(this.bipedRightLeg, 40, 16, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false));

		this.bipedLeftLeg = new ModelRenderer(this);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.bipedLeftLeg.cubeList.add(new ModelBox(this.bipedLeftLeg, 0, 16, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false));

		this.bipedHead = new ModelRenderer(this);
		this.bipedHead.isHidden = true;
		this.bipedHeadwear = new ModelRenderer(this);
		this.bipedHeadwear.isHidden = true;
		this.bipedRightArm = new ModelRenderer(this);
		this.bipedRightArm.isHidden = true;
		this.bipedLeftArm = new ModelRenderer(this);
		this.bipedLeftArm.isHidden = true;
	}

}
