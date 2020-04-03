package meldexun.better_diving.client.model.armor;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDivingGear extends ModelCustomArmor {

	private final ModelRenderer diveHeadFront;
	private final ModelRenderer tank;
	private final ModelRenderer tankTop;
	private final ModelRenderer tankTopLong;
	private final ModelRenderer tankTopRight;
	private final ModelRenderer tankTopLeft;
	private final ModelRenderer rightFin;
	private final ModelRenderer leftFin;

	public ModelDivingGear(float scale, boolean smallArmsIn) {
		super(scale, 64, 64);

		this.bipedHead = new ModelRenderer(this);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.cubeList.add(new ModelBox(this.bipedHead, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.51F, false));

		this.bipedBody = new ModelRenderer(this);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedBody.cubeList.add(new ModelBox(this.bipedBody, 16, 16, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.253F, false));

		if (smallArmsIn) {
			this.bipedRightArm = new ModelRenderer(this);
			this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
			this.bipedRightArm.cubeList.add(new ModelBox(this.bipedRightArm, 41, 16, -2.0F, -2.0F, -2.0F, 3, 12, 4, 0.251F, false));

			this.bipedLeftArm = new ModelRenderer(this);
			this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
			this.bipedLeftArm.cubeList.add(new ModelBox(this.bipedLeftArm, 1, 16, -1.0F, -2.0F, -2.0F, 3, 12, 4, 0.251F, false));
		} else {
			this.bipedRightArm = new ModelRenderer(this);
			this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
			this.bipedRightArm.cubeList.add(new ModelBox(this.bipedRightArm, 40, 16, -3.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false));

			this.bipedLeftArm = new ModelRenderer(this);
			this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
			this.bipedLeftArm.cubeList.add(new ModelBox(this.bipedLeftArm, 0, 16, -1.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false));
		}

		this.bipedRightLeg = new ModelRenderer(this);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.bipedRightLeg.cubeList.add(new ModelBox(this.bipedRightLeg, 40, 32, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false));

		this.bipedLeftLeg = new ModelRenderer(this);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.bipedLeftLeg.cubeList.add(new ModelBox(this.bipedLeftLeg, 0, 32, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false));

		this.bipedHeadwear = new ModelRenderer(this);
		this.bipedHeadwear.isHidden = true;

		this.diveHeadFront = new ModelRenderer(this);
		this.diveHeadFront.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.addChild(this.diveHeadFront);
		this.diveHeadFront.cubeList.add(new ModelBox(this.diveHeadFront, 32, 0, -1.0F, -3.0F, -6.51F, 2, 2, 2, 0.0F, false));

		this.tank = new ModelRenderer(this);
		this.tank.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedBody.addChild(this.tank);
		this.tank.cubeList.add(new ModelBox(this.tank, 16, 32, -2.0F, 1.0F, 2.253F, 4, 10, 4, 0.0F, false));

		this.tankTop = new ModelRenderer(this);
		this.tankTop.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.tank.addChild(this.tankTop);
		this.tankTop.cubeList.add(new ModelBox(this.tankTop, 32, 32, -1.0F, 0.0F, 3.253F, 2, 1, 2, 0.0F, false));

		this.tankTopLong = new ModelRenderer(this);
		this.tankTopLong.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.tankTop.addChild(this.tankTopLong);
		this.tankTopLong.cubeList.add(new ModelBox(this.tankTopLong, 16, 46, -3.5F, -1.0F, 3.753F, 7, 1, 1, 0.0F, false));

		this.tankTopRight = new ModelRenderer(this);
		this.tankTopRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.tankTopLong.addChild(this.tankTopRight);
		this.tankTopRight.cubeList.add(new ModelBox(this.tankTopRight, 32, 35, -3.5F, 0.0F, 1.753F, 1, 1, 3, 0.0F, false));

		this.tankTopLeft = new ModelRenderer(this);
		this.tankTopLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.tankTopLong.addChild(this.tankTopLeft);
		this.tankTopLeft.cubeList.add(new ModelBox(this.tankTopLeft, 32, 39, 2.5F, 0.0F, 1.753F, 1, 1, 3, 0.0F, false));

		this.rightFin = new ModelRenderer(this);
		this.rightFin.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedRightLeg.addChild(this.rightFin);
		this.rightFin.cubeList.add(new ModelBox(this.rightFin, 40, 48, -2.0F, 11.0F, -9.502F, 4, 1, 7, 0.251F, false));

		this.leftFin = new ModelRenderer(this);
		this.leftFin.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedLeftLeg.addChild(this.leftFin);
		this.leftFin.cubeList.add(new ModelBox(this.leftFin, 0, 48, -2.0F, 11.0F, -9.502F, 4, 1, 7, 0.251F, false));
	}

}
