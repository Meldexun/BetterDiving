package meldexun.better_diving.client.model.entity;

import meldexun.better_diving.client.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelPeeper extends ModelBase {

	private final ModelRenderer body;
	private final ModelRenderer eye;
	private final ModelRenderer mouth;
	private final ModelRenderer top_fin;
	private final ModelRenderer bottom_fin;
	private final ModelRenderer back_body;
	private final ModelRenderer back_fin;
	private final ModelRenderer back_fin_end;

	public ModelPeeper() {
		this.textureWidth = 128;
		this.textureHeight = 128;

		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.cubeList.add(new ModelBox(this.body, 72, 0, -1.0F, -7.0F, -15.0F, 2, 14, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 40, 0, -1.0F, -9.0F, -13.0F, 2, 1, 14, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 40, 15, -1.0F, 8.0F, -13.0F, 2, 1, 14, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -2.0F, -8.0F, -14.0F, 4, 16, 16, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 32, 2.0F, -7.0F, -13.0F, 1, 14, 14, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 30, 32, -3.0F, -7.0F, -13.0F, 1, 14, 14, 0.0F, false));

		this.eye = new ModelRenderer(this);
		this.eye.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.eye.cubeList.add(new ModelBox(this.eye, 0, 94, 2.5F, -4.0F, -11.0F, 1, 8, 1, 0.0F, false));
		this.eye.cubeList.add(new ModelBox(this.eye, 0, 103, 2.5F, -5.0F, -10.0F, 1, 10, 8, 0.0F, false));
		this.eye.cubeList.add(new ModelBox(this.eye, 4, 94, 2.5F, -4.0F, -2.0F, 1, 8, 1, 0.0F, false));
		this.eye.cubeList.add(new ModelBox(this.eye, 18, 94, -3.5F, -4.0F, -11.0F, 1, 8, 1, 0.0F, false));
		this.eye.cubeList.add(new ModelBox(this.eye, 18, 103, -3.5F, -5.0F, -10.0F, 1, 10, 8, 0.0F, false));
		this.eye.cubeList.add(new ModelBox(this.eye, 22, 94, -3.5F, -4.0F, -2.0F, 1, 8, 1, 0.0F, false));

		this.mouth = new ModelRenderer(this);
		this.mouth.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.mouth.cubeList.add(new ModelBox(this.mouth, 60, 55, -0.5F, -2.0F, -16.0F, 1, 4, 1, 0.0F, false));
		this.mouth.cubeList.add(new ModelBox(this.mouth, 64, 58, -0.5F, -0.5F, -17.0F, 1, 1, 1, 0.0F, false));

		this.top_fin = new ModelRenderer(this);
		this.top_fin.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.top_fin.cubeList.add(new ModelBox(this.top_fin, 0, 60, -2.0F, -11.0F, -7.0F, 4, 3, 2, 0.0F, false));
		this.top_fin.cubeList.add(new ModelBox(this.top_fin, 0, 65, -2.0F, -13.0F, -6.0F, 4, 2, 1, 0.0F, false));
		this.top_fin.cubeList.add(new ModelBox(this.top_fin, 0, 68, -1.0F, -12.0F, -8.0F, 2, 3, 4, 0.0F, false));
		this.top_fin.cubeList.add(new ModelBox(this.top_fin, 0, 75, -1.0F, -13.0F, -7.0F, 2, 1, 3, 0.0F, false));
		this.top_fin.cubeList.add(new ModelBox(this.top_fin, 0, 79, -1.0F, -14.0F, -7.0F, 2, 1, 4, 0.0F, false));
		this.top_fin.cubeList.add(new ModelBox(this.top_fin, 0, 84, -1.0F, -16.0F, -6.0F, 2, 2, 3, 0.0F, false));
		this.top_fin.cubeList.add(new ModelBox(this.top_fin, 0, 89, -1.0F, -17.0F, -5.0F, 2, 1, 2, 0.0F, false));
		this.top_fin.cubeList.add(new ModelBox(this.top_fin, 0, 92, -1.0F, -18.0F, -4.0F, 2, 1, 1, 0.0F, false));

		this.bottom_fin = new ModelRenderer(this);
		this.bottom_fin.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bottom_fin.cubeList.add(new ModelBox(this.bottom_fin, 12, 60, -2.0F, 8.0F, -7.0F, 4, 3, 2, 0.0F, false));
		this.bottom_fin.cubeList.add(new ModelBox(this.bottom_fin, 12, 65, -2.0F, 11.0F, -6.0F, 4, 2, 1, 0.0F, false));
		this.bottom_fin.cubeList.add(new ModelBox(this.bottom_fin, 12, 68, -1.0F, 9.0F, -8.0F, 2, 3, 4, 0.0F, false));
		this.bottom_fin.cubeList.add(new ModelBox(this.bottom_fin, 12, 75, -1.0F, 12.0F, -7.0F, 2, 1, 3, 0.0F, false));
		this.bottom_fin.cubeList.add(new ModelBox(this.bottom_fin, 12, 79, -1.0F, 13.0F, -7.0F, 2, 1, 4, 0.0F, false));
		this.bottom_fin.cubeList.add(new ModelBox(this.bottom_fin, 12, 84, -1.0F, 14.0F, -6.0F, 2, 2, 3, 0.0F, false));
		this.bottom_fin.cubeList.add(new ModelBox(this.bottom_fin, 12, 89, -1.0F, 16.0F, -5.0F, 2, 1, 2, 0.0F, false));
		this.bottom_fin.cubeList.add(new ModelBox(this.bottom_fin, 12, 92, -1.0F, 17.0F, -4.0F, 2, 1, 1, 0.0F, false));

		this.back_body = new ModelRenderer(this);
		this.back_body.setRotationPoint(0.0F, 0.0F, 2.0F);
		this.back_body.cubeList.add(new ModelBox(this.back_body, 24, 60, -1.0F, -6.0F, 0.0F, 2, 13, 1, 0.0F, false));
		this.back_body.cubeList.add(new ModelBox(this.back_body, 30, 60, -1.0F, -5.0F, 1.0F, 2, 11, 1, 0.0F, false));
		this.back_body.cubeList.add(new ModelBox(this.back_body, 36, 60, -1.0F, -4.0F, 2.0F, 2, 9, 1, 0.0F, false));
		this.back_body.cubeList.add(new ModelBox(this.back_body, 42, 60, -1.0F, -3.0F, 3.0F, 2, 7, 2, 0.0F, false));
		this.back_body.cubeList.add(new ModelBox(this.back_body, 24, 74, -2.0F, -4.0F, 0.0F, 4, 9, 1, 0.0F, false));
		this.back_body.cubeList.add(new ModelBox(this.back_body, 34, 74, -2.0F, -3.0F, 1.0F, 4, 7, 1, 0.0F, false));
		this.back_body.cubeList.add(new ModelBox(this.back_body, 44, 74, -2.0F, -2.0F, 2.0F, 4, 5, 2, 0.0F, false));
		this.back_body.cubeList.add(new ModelBox(this.back_body, 56, 74, -2.0F, -1.0F, 4.0F, 4, 3, 1, 0.0F, false));

		this.back_fin = new ModelRenderer(this);
		this.back_fin.setRotationPoint(0.0F, 0.0F, 5.0F);
		this.back_body.addChild(this.back_fin);
		this.back_fin.cubeList.add(new ModelBox(this.back_fin, 50, 60, -1.0F, -2.0F, 0.0F, 2, 5, 2, 0.0F, false));
		this.back_fin.cubeList.add(new ModelBox(this.back_fin, 58, 60, -1.0F, -1.0F, 2.0F, 2, 3, 2, 0.0F, false));
		this.back_fin.cubeList.add(new ModelBox(this.back_fin, 66, 60, -1.0F, -1.0F, 4.0F, 2, 2, 1, 0.0F, false));
		this.back_fin.cubeList.add(new ModelBox(this.back_fin, 66, 74, -2.0F, -1.0F, 0.0F, 4, 3, 1, 0.0F, false));
		this.back_fin.cubeList.add(new ModelBox(this.back_fin, 76, 74, -2.0F, 0.0F, 1.0F, 4, 1, 2, 0.0F, false));
		this.back_fin.cubeList.add(new ModelBox(this.back_fin, 24, 82, 0.0F, -4.0F, 0.0F, 0, 2, 2, 0.0F, false));
		this.back_fin.cubeList.add(new ModelBox(this.back_fin, 28, 83, 0.0F, -5.0F, 2.0F, 0, 9, 1, 0.0F, false));
		this.back_fin.cubeList.add(new ModelBox(this.back_fin, 30, 82, 0.0F, -6.0F, 3.0F, 0, 11, 2, 0.0F, false));

		this.back_fin_end = new ModelRenderer(this);
		this.back_fin_end.setRotationPoint(0.0F, 0.0F, 5.0F);
		this.back_fin.addChild(this.back_fin_end);
		this.back_fin_end.cubeList.add(new ModelBox(this.back_fin_end, 72, 60, -1.0F, -1.0F, 0.0F, 2, 2, 1, 0.0F, false));
		this.back_fin_end.cubeList.add(new ModelBox(this.back_fin_end, 78, 60, -1.0F, -1.0F, 1.0F, 2, 1, 4, 0.0F, false));
		this.back_fin_end.cubeList.add(new ModelBox(this.back_fin_end, 34, 82, 0.0F, -6.0F, 0.0F, 0, 11, 2, 0.0F, false));
		this.back_fin_end.cubeList.add(new ModelBox(this.back_fin_end, 38, 83, 0.0F, -5.0F, 2.0F, 0, 9, 1, 0.0F, false));
		this.back_fin_end.cubeList.add(new ModelBox(this.back_fin_end, 40, 83, 0.0F, -4.0F, 3.0F, 0, 7, 1, 0.0F, false));
		this.back_fin_end.cubeList.add(new ModelBox(this.back_fin_end, 42, 83, 0.0F, -2.0F, 4.0F, 0, 3, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

		this.body.render(scaleFactor);
		this.mouth.render(scaleFactor);
		this.top_fin.render(scaleFactor);
		this.bottom_fin.render(scaleFactor);
		this.back_body.render(scaleFactor);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
		this.eye.render(scaleFactor);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		double tick = (double) (ageInTicks + Minecraft.getMinecraft().getRenderPartialTicks());

		GlStateManager.rotate((float) (2.0D * Math.sin(tick * GuiHelper.TWO_PI / 10.0D)), 0.0F, 1.0F, 0.0F);

		this.setRotationAngle(this.body, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.eye, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.mouth, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.top_fin, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.bottom_fin, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.back_body, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.back_fin, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.back_fin_end, 0.0F, 0.0F, 0.0F);

		float f = (float) (Math.toRadians(5.0D) * Math.sin(tick * GuiHelper.TWO_PI / 10.0D));
		this.back_body.rotateAngleY += f;
		this.back_fin.rotateAngleY += f;
		this.back_fin_end.rotateAngleY += f;
	}

}
