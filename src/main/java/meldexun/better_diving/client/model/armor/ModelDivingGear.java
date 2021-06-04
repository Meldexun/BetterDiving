package meldexun.better_diving.client.model.armor;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelDivingGear extends ModelCustomArmor {

	private final ModelRenderer diveHeadFront;
	private final ModelRenderer tank;
	private final ModelRenderer tankTop;
	private final ModelRenderer tankTopLong;
	private final ModelRenderer tankTopRight;
	private final ModelRenderer tankTopLeft;
	private final ModelRenderer rightFin;
	private final ModelRenderer leftFin;

	public ModelDivingGear(boolean smallArmsIn) {
		super(64, 64);

		this.head = new ModelRenderer(this, 0, 0);
		this.head.setPos(0.0F, 0.0F, 0.0F);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.51F, false);

		this.body = new ModelRenderer(this, 16, 16);
		this.body.setPos(0.0F, 0.0F, 0.0F);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.253F, false);

		if (smallArmsIn) {
			this.rightArm = new ModelRenderer(this, 41, 16);
			this.rightArm.setPos(-5.0F, 2.5F, 0.0F);
			this.rightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.251F, false);

			this.leftArm = new ModelRenderer(this, 1, 16);
			this.leftArm.setPos(5.0F, 2.5F, 0.0F);
			this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.251F, false);
		} else {
			this.rightArm = new ModelRenderer(this, 40, 16);
			this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
			this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false);

			this.leftArm = new ModelRenderer(this, 0, 16);
			this.leftArm.setPos(5.0F, 2.0F, 0.0F);
			this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false);
		}

		this.rightLeg = new ModelRenderer(this, 40, 32);
		this.rightLeg.setPos(-1.9F, 12.0F, 0.0F);
		this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);

		this.leftLeg = new ModelRenderer(this, 0, 32);
		this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
		this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);

		this.hat = new ModelRenderer(this);
		this.hat.visible = false;

		this.diveHeadFront = new ModelRenderer(this, 32, 0);
		this.diveHeadFront.setPos(0.0F, 0.0F, 0.0F);
		this.diveHeadFront.addBox(-1.0F, -3.0F, -6.51F, 2, 2, 2, 0.0F, false);
		this.head.addChild(this.diveHeadFront);

		this.tank = new ModelRenderer(this, 16, 32);
		this.tank.setPos(0.0F, 0.0F, 0.0F);
		this.tank.addBox(-2.0F, 1.0F, 2.253F, 4, 10, 4, 0.0F, false);
		this.body.addChild(this.tank);

		this.tankTop = new ModelRenderer(this, 32, 32);
		this.tankTop.setPos(0.0F, 0.0F, 0.0F);
		this.tankTop.addBox(-1.0F, 0.0F, 3.253F, 2, 1, 2, 0.0F, false);
		this.tank.addChild(this.tankTop);

		this.tankTopLong = new ModelRenderer(this, 16, 46);
		this.tankTopLong.setPos(0.0F, 0.0F, 0.0F);
		this.tankTopLong.addBox(-3.5F, -1.0F, 3.753F, 7, 1, 1, 0.0F, false);
		this.tankTop.addChild(this.tankTopLong);

		this.tankTopRight = new ModelRenderer(this, 32, 35);
		this.tankTopRight.setPos(0.0F, 0.0F, 0.0F);
		this.tankTopRight.addBox(-3.5F, 0.0F, 1.753F, 1, 1, 3, 0.0F, false);
		this.tankTopLong.addChild(this.tankTopRight);

		this.tankTopLeft = new ModelRenderer(this, 32, 39);
		this.tankTopLeft.setPos(0.0F, 0.0F, 0.0F);
		this.tankTopLeft.addBox(2.5F, 0.0F, 1.753F, 1, 1, 3, 0.0F, false);
		this.tankTopLong.addChild(this.tankTopLeft);

		this.rightFin = new ModelRenderer(this, 40, 48);
		this.rightFin.setPos(0.0F, 0.0F, 0.0F);
		this.rightFin.addBox(-2.0F, 11.0F, -9.502F, 4, 1, 7, 0.251F, false);
		this.rightLeg.addChild(this.rightFin);

		this.leftFin = new ModelRenderer(this, 0, 48);
		this.leftFin.setPos(0.0F, 0.0F, 0.0F);
		this.leftFin.addBox(-2.0F, 11.0F, -9.502F, 4, 1, 7, 0.251F, false);
		this.leftLeg.addChild(this.leftFin);
	}

}
