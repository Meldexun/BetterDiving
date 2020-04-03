package meldexun.better_diving.client.model.entity;

import meldexun.better_diving.client.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGarryfish extends ModelBase {

	private final ModelRenderer body;
	private final ModelRenderer eye;
	private final ModelRenderer tail;
	private final ModelRenderer tail_middle;
	private final ModelRenderer tail_end;
	private final ModelRenderer left_fin_end;
	private final ModelRenderer right_fin_end;
	private final ModelRenderer left_fin;
	private final ModelRenderer right_fin;
	private final ModelRenderer left_fin_bottom;
	private final ModelRenderer right_fin_bottom;

	public ModelGarryfish() {
		this.textureWidth = 128;
		this.textureHeight = 128;

		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 5.0F, -14.0F, 4, 4, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 10.0F, -12.0F, 4, 1, 6, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -1.0F, 10.0F, -6.0F, 2, 1, 4, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 9.0F, -5.0F, 4, 1, 4, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -3.0F, 8.0F, -4.0F, 6, 1, 5, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -1.0F, 8.0F, 2.0F, 2, 1, 4, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -1.0F, 9.0F, -1.0F, 2, 1, 2, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 7.0F, 2.0F, 4, 1, 8, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 6.0F, 4.0F, 4, 1, 6, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 5.0F, 1.0F, 4, 1, 7, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 8.0F, 1.0F, 4, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -1.0F, 5.0F, 7.0F, 2, 1, 3, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -1.0F, 4.0F, 6.0F, 2, 1, 2, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -1.0F, 3.0F, 5.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -3.0F, 7.0F, -2.0F, 6, 1, 4, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -3.0F, 6.0F, -1.0F, 6, 1, 5, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -3.0F, 5.0F, -3.0F, 6, 1, 4, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 4.0F, -5.0F, 4, 1, 11, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 3.0F, -6.0F, 4, 1, 11, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 9.0F, -13.0F, 4, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -3.0F, 8.0F, -13.0F, 6, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -3.0F, 7.0F, -13.0F, 6, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -3.0F, 6.0F, -13.0F, 6, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -3.0F, 5.0F, -13.0F, 6, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 4.0F, -13.0F, 4, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -3.0F, 9.0F, -12.0F, 6, 1, 7, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -4.0F, 8.0F, -12.0F, 8, 1, 8, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -4.0F, 7.0F, -12.0F, 8, 1, 10, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -4.0F, 6.0F, -12.0F, 8, 1, 11, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -4.0F, 5.0F, -12.0F, 8, 1, 9, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -3.0F, 4.0F, -12.0F, 6, 1, 7, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -3.0F, 3.0F, -12.0F, 6, 1, 6, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, -5.0F, -10.0F, 4, 1, 8, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, -4.0F, -11.0F, 4, 1, 11, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, -3.0F, -11.0F, 4, 1, 12, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, -2.0F, -12.0F, 4, 1, 14, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, -1.0F, -12.0F, 4, 1, 14, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 0.0F, -12.0F, 4, 1, 15, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 1.0F, -12.0F, 4, 1, 16, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, 2.0F, -12.0F, 4, 1, 17, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -1.0F, 6.0F, 10.0F, 2, 2, 1, 0.0F, false));

		this.eye = new ModelRenderer(this);
		this.eye.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.eye.cubeList.add(new ModelBox(this.eye, 0, 18, -1.75F, -7.0F, -9.0F, 1, 2, 1, 0.0F, false));
		this.eye.cubeList.add(new ModelBox(this.eye, 4, 18, -2.5F, -11.0F, -10.0F, 2, 4, 3, 0.0F, false));
		this.eye.cubeList.add(new ModelBox(this.eye, 18, 18, 0.5F, -11.0F, -10.0F, 2, 4, 3, 0.0F, false));
		this.eye.cubeList.add(new ModelBox(this.eye, 14, 18, 0.75F, -7.0F, -9.0F, 1, 2, 1, 0.0F, false));

		this.tail = new ModelRenderer(this);
		this.tail.setRotationPoint(0.0F, 7.0F, 11.0F);
		this.tail.cubeList.add(new ModelBox(this.tail, 0, 0, -1.0F, -1.0F, 0.0F, 2, 2, 5, 0.0F, false));

		this.tail_middle = new ModelRenderer(this);
		this.tail_middle.setRotationPoint(0.0F, 0.0F, 5.0F);
		this.tail.addChild(this.tail_middle);
		this.tail_middle.cubeList.add(new ModelBox(this.tail_middle, 0, 0, -1.0F, -1.0F, 0.0F, 2, 2, 5, 0.0F, false));

		this.tail_end = new ModelRenderer(this);
		this.tail_end.setRotationPoint(0.0F, 0.0F, 5.0F);
		this.tail_middle.addChild(this.tail_end);
		this.tail_end.cubeList.add(new ModelBox(this.tail_end, 0, 0, -1.0F, -1.0F, 0.0F, 2, 2, 5, 0.0F, false));

		this.left_fin_end = new ModelRenderer(this);
		this.left_fin_end.setRotationPoint(1.0F, -1.0F, 3.0F);
		this.tail_end.addChild(this.left_fin_end);
		this.left_fin_end.cubeList.add(new ModelBox(this.left_fin_end, 12, 50, -0.5F, -3.0F, 1.0F, 1, 4, 1, 0.0F, false));
		this.left_fin_end.cubeList.add(new ModelBox(this.left_fin_end, 8, 50, -0.5F, -2.0F, 0.0F, 1, 3, 1, 0.0F, false));
		this.left_fin_end.cubeList.add(new ModelBox(this.left_fin_end, 4, 50, -0.5F, -1.0F, -1.0F, 1, 3, 1, 0.0F, false));
		this.left_fin_end.cubeList.add(new ModelBox(this.left_fin_end, 0, 50, -0.5F, 0.0F, -2.0F, 1, 2, 1, 0.0F, false));

		this.right_fin_end = new ModelRenderer(this);
		this.right_fin_end.setRotationPoint(-1.0F, -1.0F, 3.0F);
		this.tail_end.addChild(this.right_fin_end);
		this.right_fin_end.cubeList.add(new ModelBox(this.right_fin_end, 28, 50, -0.5F, -3.0F, 1.0F, 1, 4, 1, 0.0F, false));
		this.right_fin_end.cubeList.add(new ModelBox(this.right_fin_end, 24, 50, -0.5F, -2.0F, 0.0F, 1, 3, 1, 0.0F, false));
		this.right_fin_end.cubeList.add(new ModelBox(this.right_fin_end, 20, 50, -0.5F, -1.0F, -1.0F, 1, 3, 1, 0.0F, false));
		this.right_fin_end.cubeList.add(new ModelBox(this.right_fin_end, 16, 50, -0.5F, 0.0F, -2.0F, 1, 2, 1, 0.0F, false));

		this.left_fin = new ModelRenderer(this);
		this.left_fin.setRotationPoint(4.0F, 5.0F, -4.5F);
		this.left_fin.cubeList.add(new ModelBox(this.left_fin, 0, 25, -0.5F, -7.5F, -2.0F, 1, 7, 1, 0.0F, false));
		this.left_fin.cubeList.add(new ModelBox(this.left_fin, 4, 25, -0.5F, -9.5F, -1.0F, 1, 11, 1, 0.0F, false));
		this.left_fin.cubeList.add(new ModelBox(this.left_fin, 8, 25, -0.5F, -11.5F, 0.0F, 1, 13, 1, 0.0F, false));
		this.left_fin.cubeList.add(new ModelBox(this.left_fin, 12, 25, -0.5F, -8.5F, 1.0F, 1, 8, 1, 0.0F, false));

		this.right_fin = new ModelRenderer(this);
		this.right_fin.setRotationPoint(-4.0F, 5.0F, -4.5F);
		this.right_fin.cubeList.add(new ModelBox(this.right_fin, 16, 25, -0.5F, -7.5F, -2.0F, 1, 7, 1, 0.0F, false));
		this.right_fin.cubeList.add(new ModelBox(this.right_fin, 20, 25, -0.5F, -9.5F, -1.0F, 1, 11, 1, 0.0F, false));
		this.right_fin.cubeList.add(new ModelBox(this.right_fin, 24, 25, -0.5F, -11.5F, 0.0F, 1, 13, 1, 0.0F, false));
		this.right_fin.cubeList.add(new ModelBox(this.right_fin, 28, 25, -0.5F, -8.5F, 1.0F, 1, 8, 1, 0.0F, false));

		this.left_fin_bottom = new ModelRenderer(this);
		this.left_fin_bottom.setRotationPoint(3.0F, 10.0F, -7.0F);
		this.left_fin_bottom.cubeList.add(new ModelBox(this.left_fin_bottom, 0, 39, -0.5F, -1.5F, -0.5F, 1, 10, 1, 0.0F, false));

		this.right_fin_bottom = new ModelRenderer(this);
		this.right_fin_bottom.setRotationPoint(-3.0F, 10.0F, -7.0F);
		this.right_fin_bottom.cubeList.add(new ModelBox(this.right_fin_bottom, 4, 39, -0.5F, -1.5F, -0.5F, 1, 10, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

		this.body.render(scaleFactor);
		this.eye.render(scaleFactor);
		this.tail.render(scaleFactor);
		this.left_fin.render(scaleFactor);
		this.right_fin.render(scaleFactor);
		this.left_fin_bottom.render(scaleFactor);
		this.right_fin_bottom.render(scaleFactor);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		double tick = (double) (ageInTicks + Minecraft.getMinecraft().getRenderPartialTicks());

		this.setRotationAngle(this.body, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.eye, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.tail, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.tail_middle, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.tail_end, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.left_fin_end, (float) Math.toRadians(-20.0D), 0.0F, (float) Math.toRadians(40.0D));
		this.setRotationAngle(this.right_fin_end, (float) Math.toRadians(-20.0D), 0.0F, (float) Math.toRadians(-40.0D));
		this.setRotationAngle(this.left_fin, 0.0F, 0.0F, (float) Math.toRadians(75.0D));
		this.setRotationAngle(this.right_fin, 0.0F, 0.0F, (float) Math.toRadians(-75.0D));
		this.setRotationAngle(this.left_fin_bottom, (float) Math.toRadians(60.0D), 0.0F, (float) Math.toRadians(-30.0D));
		this.setRotationAngle(this.right_fin_bottom, (float) Math.toRadians(60.0D), 0.0F, (float) Math.toRadians(30.0D));

		float f = (float) (Math.toRadians(30.0D) * Math.sin(tick * GuiHelper.TWO_PI / 20.0D));
		this.left_fin.rotateAngleZ += f;
		this.right_fin.rotateAngleZ -= f;

		float f2 = (float) (Math.toRadians(15.0D) * Math.cos(tick * GuiHelper.TWO_PI / 20.0D) - Math.toRadians(15.0D));
		this.left_fin.rotateAngleX += f2;
		this.right_fin.rotateAngleX += f2;

		float f1 = (float) (Math.toRadians(10.0D) * Math.sin(tick * GuiHelper.TWO_PI / 15.0D));
		this.tail.rotateAngleX += f1;
		this.tail_middle.rotateAngleX += f1;
		this.tail_end.rotateAngleX += f1;
	}

}
