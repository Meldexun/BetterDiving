package meldexun.better_diving.client.model.entity;

import meldexun.better_diving.client.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBladderfish extends ModelBase {

	private final ModelRenderer body;
	private final ModelRenderer eye;
	private final ModelRenderer bottom_membrane;
	private final ModelRenderer bottom_membrane_left;
	private final ModelRenderer bottom_membrane_right;
	private final ModelRenderer top_membrane;
	private final ModelRenderer top_membrane_left;
	private final ModelRenderer top_membrane_right;
	private final ModelRenderer top_tentacle1;
	private final ModelRenderer top_tentacle2;
	private final ModelRenderer top_tentacle3;
	private final ModelRenderer top_tentakel_end;
	private final ModelRenderer bottom_tentacle1;
	private final ModelRenderer bottom_tentacle2;
	private final ModelRenderer bottom_tentacle3;
	private final ModelRenderer bottom_tentacle_end;

	public ModelBladderfish() {
		this.textureWidth = 128;
		this.textureHeight = 128;

		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -1.0F, -1.0F, -10.0F, 2, 2, 26, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 28, -2.0F, -2.0F, -14.0F, 4, 4, 4, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 40, -1.0F, -1.0F, -16.0F, 2, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 36, -1.5F, -1.5F, -15.0F, 3, 3, 1, 0.0F, false));

		this.eye = new ModelRenderer(this);
		this.eye.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.eye.cubeList.add(new ModelBox(this.eye, 32, 28, -2.25F, -1.0F, -13.0F, 1, 2, 2, 0.0F, false));
		this.eye.cubeList.add(new ModelBox(this.eye, 38, 28, 1.25F, -1.0F, -13.0F, 1, 2, 2, 0.0F, false));

		this.bottom_membrane = new ModelRenderer(this);
		this.bottom_membrane.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bottom_membrane.cubeList.add(new ModelBox(this.bottom_membrane, 52, 43, -1.0F, 2.0F, -11.0F, 2, 1, 23, 0.0F, false));
		this.bottom_membrane.cubeList.add(new ModelBox(this.bottom_membrane, 52, 43, -1.0F, 3.0F, -13.0F, 2, 1, 24, 0.0F, false));
		this.bottom_membrane.cubeList.add(new ModelBox(this.bottom_membrane, 52, 43, -1.0F, 4.0F, -14.0F, 2, 1, 24, 0.0F, false));
		this.bottom_membrane.cubeList.add(new ModelBox(this.bottom_membrane, 52, 43, -1.0F, 5.0F, -14.0F, 2, 1, 23, 0.0F, false));
		this.bottom_membrane.cubeList.add(new ModelBox(this.bottom_membrane, 52, 43, -1.0F, 6.0F, -14.0F, 2, 1, 22, 0.0F, false));
		this.bottom_membrane.cubeList.add(new ModelBox(this.bottom_membrane, 52, 43, -1.0F, 8.0F, -14.0F, 2, 1, 20, 0.0F, false));
		this.bottom_membrane.cubeList.add(new ModelBox(this.bottom_membrane, 52, 43, -1.0F, 7.0F, -14.0F, 2, 1, 21, 0.0F, false));
		this.bottom_membrane.cubeList.add(new ModelBox(this.bottom_membrane, 52, 43, -1.0F, 9.0F, -14.0F, 2, 1, 19, 0.0F, false));
		this.bottom_membrane.cubeList.add(new ModelBox(this.bottom_membrane, 52, 43, -1.0F, 10.0F, -13.0F, 2, 1, 16, 0.0F, false));
		this.bottom_membrane.cubeList.add(new ModelBox(this.bottom_membrane, 52, 43, -1.0F, 11.0F, -11.0F, 2, 1, 11, 0.0F, false));
		this.bottom_membrane.cubeList.add(new ModelBox(this.bottom_membrane, 52, 43, -1.0F, 1.0F, -10.0F, 2, 1, 24, 0.0F, false));

		this.bottom_membrane_left = new ModelRenderer(this);
		this.bottom_membrane_left.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bottom_membrane.addChild(this.bottom_membrane_left);
		this.bottom_membrane_left.cubeList.add(new ModelBox(this.bottom_membrane_left, 0, 43, 0.75F, 10.0F, -10.0F, 1, 1, 9, 0.0F, false));
		this.bottom_membrane_left.cubeList.add(new ModelBox(this.bottom_membrane_left, 0, 43, 0.75F, 9.0F, -12.0F, 1, 1, 14, 0.0F, false));
		this.bottom_membrane_left.cubeList.add(new ModelBox(this.bottom_membrane_left, 0, 43, 0.75F, 8.0F, -13.0F, 1, 1, 17, 0.0F, false));
		this.bottom_membrane_left.cubeList.add(new ModelBox(this.bottom_membrane_left, 0, 43, 0.75F, 7.0F, -13.0F, 1, 1, 18, 0.0F, false));
		this.bottom_membrane_left.cubeList.add(new ModelBox(this.bottom_membrane_left, 0, 43, 0.75F, 6.0F, -13.0F, 1, 1, 19, 0.0F, false));
		this.bottom_membrane_left.cubeList.add(new ModelBox(this.bottom_membrane_left, 0, 43, 0.75F, 5.0F, -13.0F, 1, 1, 20, 0.0F, false));
		this.bottom_membrane_left.cubeList.add(new ModelBox(this.bottom_membrane_left, 0, 43, 0.75F, 4.0F, -12.0F, 1, 1, 20, 0.0F, false));
		this.bottom_membrane_left.cubeList.add(new ModelBox(this.bottom_membrane_left, 0, 43, 0.75F, 3.0F, -10.0F, 1, 1, 18, 0.0F, false));
		this.bottom_membrane_left.cubeList.add(new ModelBox(this.bottom_membrane_left, 0, 43, 0.75F, 2.0F, -9.0F, 1, 1, 16, 0.0F, false));

		this.bottom_membrane_right = new ModelRenderer(this);
		this.bottom_membrane_right.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bottom_membrane.addChild(this.bottom_membrane_right);
		this.bottom_membrane_right.cubeList.add(new ModelBox(this.bottom_membrane_right, 0, 43, -1.75F, 10.0F, -10.0F, 1, 1, 9, 0.0F, false));
		this.bottom_membrane_right.cubeList.add(new ModelBox(this.bottom_membrane_right, 0, 43, -1.75F, 9.0F, -12.0F, 1, 1, 14, 0.0F, false));
		this.bottom_membrane_right.cubeList.add(new ModelBox(this.bottom_membrane_right, 0, 43, -1.75F, 8.0F, -13.0F, 1, 1, 17, 0.0F, false));
		this.bottom_membrane_right.cubeList.add(new ModelBox(this.bottom_membrane_right, 0, 43, -1.75F, 7.0F, -13.0F, 1, 1, 18, 0.0F, false));
		this.bottom_membrane_right.cubeList.add(new ModelBox(this.bottom_membrane_right, 0, 43, -1.75F, 6.0F, -13.0F, 1, 1, 19, 0.0F, false));
		this.bottom_membrane_right.cubeList.add(new ModelBox(this.bottom_membrane_right, 0, 43, -1.75F, 5.0F, -13.0F, 1, 1, 20, 0.0F, false));
		this.bottom_membrane_right.cubeList.add(new ModelBox(this.bottom_membrane_right, 0, 43, -1.75F, 4.0F, -12.0F, 1, 1, 20, 0.0F, false));
		this.bottom_membrane_right.cubeList.add(new ModelBox(this.bottom_membrane_right, 0, 43, -1.75F, 3.0F, -10.0F, 1, 1, 18, 0.0F, false));
		this.bottom_membrane_right.cubeList.add(new ModelBox(this.bottom_membrane_right, 0, 43, -1.75F, 2.0F, -9.0F, 1, 1, 16, 0.0F, false));

		this.top_membrane = new ModelRenderer(this);
		this.top_membrane.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.top_membrane.cubeList.add(new ModelBox(this.top_membrane, 0, 43, -1.0F, -2.0F, -10.0F, 2, 1, 24, 0.0F, false));
		this.top_membrane.cubeList.add(new ModelBox(this.top_membrane, 0, 43, -1.0F, -3.0F, -11.0F, 2, 1, 23, 0.0F, false));
		this.top_membrane.cubeList.add(new ModelBox(this.top_membrane, 0, 43, -1.0F, -5.0F, -14.0F, 2, 1, 24, 0.0F, false));
		this.top_membrane.cubeList.add(new ModelBox(this.top_membrane, 0, 43, -1.0F, -4.0F, -13.0F, 2, 1, 24, 0.0F, false));
		this.top_membrane.cubeList.add(new ModelBox(this.top_membrane, 0, 43, -1.0F, -6.0F, -14.0F, 2, 1, 23, 0.0F, false));
		this.top_membrane.cubeList.add(new ModelBox(this.top_membrane, 0, 43, -1.0F, -7.0F, -14.0F, 2, 1, 22, 0.0F, false));
		this.top_membrane.cubeList.add(new ModelBox(this.top_membrane, 0, 43, -1.0F, -8.0F, -14.0F, 2, 1, 21, 0.0F, false));
		this.top_membrane.cubeList.add(new ModelBox(this.top_membrane, 0, 43, -1.0F, -9.0F, -14.0F, 2, 1, 20, 0.0F, false));
		this.top_membrane.cubeList.add(new ModelBox(this.top_membrane, 0, 43, -1.0F, -10.0F, -14.0F, 2, 1, 19, 0.0F, false));
		this.top_membrane.cubeList.add(new ModelBox(this.top_membrane, 0, 43, -1.0F, -11.0F, -13.0F, 2, 1, 16, 0.0F, false));
		this.top_membrane.cubeList.add(new ModelBox(this.top_membrane, 0, 43, -1.0F, -12.0F, -11.0F, 2, 1, 11, 0.0F, false));

		this.top_membrane_left = new ModelRenderer(this);
		this.top_membrane_left.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.top_membrane.addChild(this.top_membrane_left);
		this.top_membrane_left.cubeList.add(new ModelBox(this.top_membrane_left, 0, 43, 0.75F, -11.0F, -10.0F, 1, 1, 9, 0.0F, false));
		this.top_membrane_left.cubeList.add(new ModelBox(this.top_membrane_left, 0, 43, 0.75F, -10.0F, -12.0F, 1, 1, 14, 0.0F, false));
		this.top_membrane_left.cubeList.add(new ModelBox(this.top_membrane_left, 0, 43, 0.75F, -9.0F, -13.0F, 1, 1, 17, 0.0F, false));
		this.top_membrane_left.cubeList.add(new ModelBox(this.top_membrane_left, 0, 43, 0.75F, -8.0F, -13.0F, 1, 1, 18, 0.0F, false));
		this.top_membrane_left.cubeList.add(new ModelBox(this.top_membrane_left, 0, 43, 0.75F, -7.0F, -13.0F, 1, 1, 19, 0.0F, false));
		this.top_membrane_left.cubeList.add(new ModelBox(this.top_membrane_left, 0, 43, 0.75F, -6.0F, -13.0F, 1, 1, 20, 0.0F, false));
		this.top_membrane_left.cubeList.add(new ModelBox(this.top_membrane_left, 0, 43, 0.75F, -5.0F, -12.0F, 1, 1, 20, 0.0F, false));
		this.top_membrane_left.cubeList.add(new ModelBox(this.top_membrane_left, 0, 43, 0.75F, -4.0F, -10.0F, 1, 1, 18, 0.0F, false));
		this.top_membrane_left.cubeList.add(new ModelBox(this.top_membrane_left, 0, 43, 0.75F, -3.0F, -9.0F, 1, 1, 16, 0.0F, false));

		this.top_membrane_right = new ModelRenderer(this);
		this.top_membrane_right.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.top_membrane.addChild(this.top_membrane_right);
		this.top_membrane_right.cubeList.add(new ModelBox(this.top_membrane_right, 0, 43, -1.75F, -11.0F, -10.0F, 1, 1, 9, 0.0F, false));
		this.top_membrane_right.cubeList.add(new ModelBox(this.top_membrane_right, 0, 43, -1.75F, -10.0F, -12.0F, 1, 1, 14, 0.0F, false));
		this.top_membrane_right.cubeList.add(new ModelBox(this.top_membrane_right, 0, 43, -1.75F, -9.0F, -13.0F, 1, 1, 17, 0.0F, false));
		this.top_membrane_right.cubeList.add(new ModelBox(this.top_membrane_right, 0, 43, -1.75F, -8.0F, -13.0F, 1, 1, 18, 0.0F, false));
		this.top_membrane_right.cubeList.add(new ModelBox(this.top_membrane_right, 0, 43, -1.75F, -7.0F, -13.0F, 1, 1, 19, 0.0F, false));
		this.top_membrane_right.cubeList.add(new ModelBox(this.top_membrane_right, 0, 43, -1.75F, -6.0F, -13.0F, 1, 1, 20, 0.0F, false));
		this.top_membrane_right.cubeList.add(new ModelBox(this.top_membrane_right, 0, 43, -1.75F, -5.0F, -12.0F, 1, 1, 20, 0.0F, false));
		this.top_membrane_right.cubeList.add(new ModelBox(this.top_membrane_right, 0, 43, -1.75F, -4.0F, -10.0F, 1, 1, 18, 0.0F, false));
		this.top_membrane_right.cubeList.add(new ModelBox(this.top_membrane_right, 0, 43, -1.75F, -3.0F, -9.0F, 1, 1, 16, 0.0F, false));

		this.top_tentacle1 = new ModelRenderer(this);
		this.top_tentacle1.setRotationPoint(0.0F, -12.0F, -10.5F);
		this.top_tentacle1.cubeList.add(new ModelBox(this.top_tentacle1, 0, 68, -0.5F, -4.0F, 0.0F, 1, 4, 1, 0.0F, false));

		this.top_tentacle2 = new ModelRenderer(this);
		this.top_tentacle2.setRotationPoint(0.0F, -12.0F, -1.5F);
		this.top_tentacle2.cubeList.add(new ModelBox(this.top_tentacle2, 4, 68, -0.5F, -4.0F, 0.0F, 1, 4, 1, 0.0F, false));

		this.top_tentacle3 = new ModelRenderer(this);
		this.top_tentacle3.setRotationPoint(0.0F, -8.0F, 5.5F);
		this.top_tentacle3.cubeList.add(new ModelBox(this.top_tentacle3, 8, 68, -0.5F, -4.0F, 0.0F, 1, 4, 1, 0.0F, false));

		this.top_tentakel_end = new ModelRenderer(this);
		this.top_tentakel_end.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.top_tentakel_end.cubeList.add(new ModelBox(this.top_tentakel_end, 0, 78, -1.0F, -14.8284F, -7.6716F, 2, 1, 2, 0.0F, false));
		this.top_tentakel_end.cubeList.add(new ModelBox(this.top_tentakel_end, 8, 78, -1.0F, -14.8284F, 1.3284F, 2, 1, 2, 0.0F, false));
		this.top_tentakel_end.cubeList.add(new ModelBox(this.top_tentakel_end, 16, 78, -1.0F, -10.8284F, 8.3284F, 2, 1, 2, 0.0F, false));

		this.bottom_tentacle1 = new ModelRenderer(this);
		this.bottom_tentacle1.setRotationPoint(0.0F, 12.0F, -10.5F);
		this.bottom_tentacle1.cubeList.add(new ModelBox(this.bottom_tentacle1, 0, 73, -0.5F, 0.0F, 0.0F, 1, 4, 1, 0.0F, false));

		this.bottom_tentacle2 = new ModelRenderer(this);
		this.bottom_tentacle2.setRotationPoint(0.0F, 12.0F, -1.5F);
		this.bottom_tentacle2.cubeList.add(new ModelBox(this.bottom_tentacle2, 8, 73, -0.5F, 0.0F, 0.0F, 1, 4, 1, 0.0F, false));

		this.bottom_tentacle3 = new ModelRenderer(this);
		this.bottom_tentacle3.setRotationPoint(0.0F, 8.0F, 5.5F);
		this.bottom_tentacle3.cubeList.add(new ModelBox(this.bottom_tentacle3, 4, 73, -0.5F, 0.0F, 0.0F, 1, 4, 1, 0.0F, false));

		this.bottom_tentacle_end = new ModelRenderer(this);
		this.bottom_tentacle_end.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bottom_tentacle_end.cubeList.add(new ModelBox(this.bottom_tentacle_end, 0, 81, -1.0F, 13.8284F, -7.6716F, 2, 1, 2, 0.0F, false));
		this.bottom_tentacle_end.cubeList.add(new ModelBox(this.bottom_tentacle_end, 8, 81, -1.0F, 13.8284F, 1.3284F, 2, 1, 2, 0.0F, false));
		this.bottom_tentacle_end.cubeList.add(new ModelBox(this.bottom_tentacle_end, 16, 81, -1.0F, 9.8284F, 8.3284F, 2, 1, 2, 0.0F, false));
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 30.0F, 30.0F);
		this.bottom_membrane.render(scaleFactor);
		this.top_membrane.render(scaleFactor);
		this.top_tentacle1.render(scaleFactor);
		this.top_tentacle2.render(scaleFactor);
		this.top_tentacle3.render(scaleFactor);
		this.top_tentakel_end.render(scaleFactor);
		this.bottom_tentacle1.render(scaleFactor);
		this.bottom_tentacle2.render(scaleFactor);
		this.bottom_tentacle3.render(scaleFactor);
		this.bottom_tentacle_end.render(scaleFactor);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 120.0F, 120.0F);
		this.body.render(scaleFactor);
		this.eye.render(scaleFactor);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		double tick = ageInTicks + Minecraft.getMinecraft().getRenderPartialTicks();

		this.setRotationAngle(this.body, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.eye, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.bottom_membrane, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.top_membrane, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.top_tentacle1, (float) Math.toRadians(-45.0D), 0.0F, 0.0F);
		this.setRotationAngle(this.top_tentacle2, (float) Math.toRadians(-45.0D), 0.0F, 0.0F);
		this.setRotationAngle(this.top_tentacle3, (float) Math.toRadians(-45.0D), 0.0F, 0.0F);
		this.setRotationAngle(this.top_tentakel_end, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.bottom_tentacle1, (float) Math.toRadians(45.0D), 0.0F, 0.0F);
		this.setRotationAngle(this.bottom_tentacle2, (float) Math.toRadians(45.0D), 0.0F, 0.0F);
		this.setRotationAngle(this.bottom_tentacle3, (float) Math.toRadians(45.0D), 0.0F, 0.0F);
		this.setRotationAngle(this.bottom_tentacle_end, 0.0F, 0.0F, 0.0F);

		double d = Math.sin(tick * GuiHelper.TWO_PI / 40.0D);
		this.top_membrane_left.setRotationPoint((float) (0.25D * d), 0.0F, 0.0F);
		this.top_membrane_right.setRotationPoint((float) (-0.25D * d), 0.0F, 0.0F);
		this.bottom_membrane_left.setRotationPoint((float) (0.25D * d), 0.0F, 0.0F);
		this.bottom_membrane_right.setRotationPoint((float) (-0.25D * d), 0.0F, 0.0F);
	}

}
