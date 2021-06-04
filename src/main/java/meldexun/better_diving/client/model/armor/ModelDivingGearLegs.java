package meldexun.better_diving.client.model.armor;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelDivingGearLegs extends ModelCustomArmor {

	public ModelDivingGearLegs() {
		super(64, 32);

		this.body = new ModelRenderer(this, 16, 16);
		this.body.setPos(0.0F, 0.0F, 0.0F);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.252F, false);

		this.rightLeg = new ModelRenderer(this, 40, 16);
		this.rightLeg.setPos(-1.9F, 12.0F, 0.0F);
		this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.251F, false);

		this.leftLeg = new ModelRenderer(this, 0, 16);
		this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
		this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.251F, false);

		this.head = new ModelRenderer(this);
		this.head.visible = false;
		this.hat = new ModelRenderer(this);
		this.hat.visible = false;
		this.rightArm = new ModelRenderer(this);
		this.rightArm.visible = false;
		this.leftArm = new ModelRenderer(this);
		this.leftArm.visible = false;
	}

}
