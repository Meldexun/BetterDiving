package meldexun.better_diving.client.model.entity;

import meldexun.better_diving.client.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHolefish extends ModelBase {

	private final ModelRenderer body;
	private final ModelRenderer fin;
	private final ModelRenderer fin_middle;
	private final ModelRenderer fin_end;

	public ModelHolefish() {
		this.textureWidth = 128;
		this.textureHeight = 128;

		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.cubeList.add(new ModelBox(this.body, 8, 20, -1.0F, -3.0F, -2.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 14, 17, -1.0F, 1.0F, -2.0F, 2, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 8, 17, -1.0F, -4.0F, -3.0F, 2, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 14, 14, -1.0F, 2.0F, -3.0F, 2, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 8, 14, -1.0F, -5.0F, -4.0F, 2, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 14, 11, -1.0F, 3.0F, -4.0F, 2, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 8, 10, -1.0F, -7.0F, -5.0F, 2, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 14, 8, -1.0F, 4.0F, -5.0F, 2, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 8, 6, -1.0F, -6.0F, -6.0F, 2, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 14, 5, -1.0F, 3.0F, -6.0F, 2, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 8, 2, -1.0F, -4.0F, -7.0F, 2, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 14, 2, -1.0F, 2.0F, -7.0F, 2, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 8, 0, -1.0F, -2.0F, -8.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 14, 0, -1.0F, 2.0F, -8.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 20, 7, -1.0F, -1.0F, -9.0F, 2, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 20, 35, -1.5F, -3.0F, -4.0F, 3, 6, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 20, 26, -1.5F, -4.0F, -5.0F, 3, 8, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 20, 19, -1.5F, -3.0F, -6.0F, 3, 6, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 20, 11, -1.5F, -1.0F, -8.0F, 3, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 20, 15, -1.5F, -1.0F, -7.0F, 3, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 20, 47, -1.5F, -2.0F, -2.0F, 3, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 20, 42, -1.5F, -2.0F, -3.0F, 3, 4, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 21, -0.5F, -8.0F, -4.0F, 1, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 16, -0.5F, -11.0F, -5.0F, 1, 4, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 4, 12, -0.5F, 6.0F, -5.0F, 1, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 12, -0.5F, -9.0F, -6.0F, 1, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 4, 9, -0.5F, 5.0F, -6.0F, 1, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 8, -0.5F, -7.0F, -7.0F, 1, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 4, 6, -0.5F, 4.0F, -7.0F, 1, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 4, 15, -0.5F, 5.0F, -4.0F, 1, 4, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 4, -0.5F, -5.0F, -8.0F, 1, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 4, 3, -0.5F, 3.0F, -8.0F, 1, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -0.5F, -3.0F, -9.0F, 1, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 4, 0, -0.5F, 2.0F, -9.0F, 1, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 20, 2, -0.5F, -1.0F, -10.0F, 1, 4, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 20, 0, -0.5F, 1.0F, -11.0F, 1, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 28, -0.5F, -5.0F, -2.0F, 1, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 25, -0.5F, -6.0F, -3.0F, 1, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 4, 20, -0.5F, 4.0F, -3.0F, 1, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 4, 24, -0.5F, 3.0F, -2.0F, 1, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 4, 27, -0.5F, 2.0F, -1.0F, 1, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 14, 20, -1.0F, 1.0F, -1.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 20, 51, -1.5F, -1.0F, -1.0F, 3, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 8, 22, -1.0F, -3.0F, -1.0F, 2, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 31, -0.5F, -4.0F, -1.0F, 1, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 4, 30, -0.5F, 2.0F, 0.0F, 1, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 14, 22, -1.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 20, 54, -1.5F, -1.0F, 0.0F, 3, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 8, 25, -1.0F, -3.0F, 0.0F, 2, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 33, -0.5F, -4.0F, 0.0F, 1, 1, 1, 0.0F, false));

		this.fin = new ModelRenderer(this);
		this.fin.setRotationPoint(0.0F, -0.5F, 1.0F);
		this.fin.cubeList.add(new ModelBox(this.fin, 0, 57, -0.5F, -3.5F, 0.0F, 1, 1, 1, 0.0F, false));
		this.fin.cubeList.add(new ModelBox(this.fin, 0, 59, -1.0F, -2.5F, 0.0F, 2, 2, 1, 0.0F, false));
		this.fin.cubeList.add(new ModelBox(this.fin, 0, 62, -1.5F, -0.5F, 0.0F, 3, 1, 1, 0.0F, false));
		this.fin.cubeList.add(new ModelBox(this.fin, 0, 64, -1.0F, 0.5F, 0.0F, 2, 2, 1, 0.0F, false));
		this.fin.cubeList.add(new ModelBox(this.fin, 0, 67, -0.5F, 2.5F, 0.0F, 1, 1, 1, 0.0F, false));
		this.fin.cubeList.add(new ModelBox(this.fin, 0, 35, -0.5F, -3.5F, 1.0F, 1, 1, 1, 0.0F, false));
		this.fin.cubeList.add(new ModelBox(this.fin, 8, 28, -1.0F, -2.5F, 1.0F, 2, 2, 1, 0.0F, false));
		this.fin.cubeList.add(new ModelBox(this.fin, 20, 56, -1.5F, -0.5F, 1.0F, 3, 1, 1, 0.0F, false));
		this.fin.cubeList.add(new ModelBox(this.fin, 14, 25, -1.0F, 0.5F, 1.0F, 2, 2, 1, 0.0F, false));
		this.fin.cubeList.add(new ModelBox(this.fin, 4, 32, -0.5F, 2.5F, 1.0F, 1, 1, 1, 0.0F, false));
		this.fin.cubeList.add(new ModelBox(this.fin, 4, 34, -0.5F, 3.5F, 2.0F, 1, 1, 1, 0.0F, false));
		this.fin.cubeList.add(new ModelBox(this.fin, 20, 58, -1.0F, -3.5F, 2.0F, 2, 7, 1, 0.0F, false));
		this.fin.cubeList.add(new ModelBox(this.fin, 0, 37, -0.5F, -4.5F, 2.0F, 1, 1, 1, 0.0F, false));

		this.fin_middle = new ModelRenderer(this);
		this.fin_middle.setRotationPoint(0.0F, 0.0F, 3.0F);
		this.fin.addChild(this.fin_middle);
		this.fin_middle.cubeList.add(new ModelBox(this.fin_middle, 0, 39, -0.5F, -4.5F, 0.0F, 1, 1, 1, 0.0F, false));
		this.fin_middle.cubeList.add(new ModelBox(this.fin_middle, 0, 41, -0.5F, -4.5F, 1.0F, 1, 1, 1, 0.0F, false));
		this.fin_middle.cubeList.add(new ModelBox(this.fin_middle, 0, 43, -0.5F, -4.5F, 2.0F, 1, 1, 1, 0.0F, false));
		this.fin_middle.cubeList.add(new ModelBox(this.fin_middle, 8, 37, -1.0F, -3.5F, 2.0F, 2, 2, 1, 0.0F, false));
		this.fin_middle.cubeList.add(new ModelBox(this.fin_middle, 8, 34, -1.0F, -3.5F, 1.0F, 2, 2, 1, 0.0F, false));
		this.fin_middle.cubeList.add(new ModelBox(this.fin_middle, 8, 31, -1.0F, -3.5F, 0.0F, 2, 2, 1, 0.0F, false));
		this.fin_middle.cubeList.add(new ModelBox(this.fin_middle, 14, 31, -1.0F, 1.5F, 1.0F, 2, 2, 1, 0.0F, false));
		this.fin_middle.cubeList.add(new ModelBox(this.fin_middle, 14, 34, -1.0F, 1.5F, 2.0F, 2, 2, 1, 0.0F, false));
		this.fin_middle.cubeList.add(new ModelBox(this.fin_middle, 14, 28, -1.0F, 1.5F, 0.0F, 2, 2, 1, 0.0F, false));
		this.fin_middle.cubeList.add(new ModelBox(this.fin_middle, 4, 36, -0.5F, 3.5F, 0.0F, 1, 1, 1, 0.0F, false));
		this.fin_middle.cubeList.add(new ModelBox(this.fin_middle, 4, 38, -0.5F, 3.5F, 1.0F, 1, 1, 1, 0.0F, false));
		this.fin_middle.cubeList.add(new ModelBox(this.fin_middle, 4, 40, -0.5F, 3.5F, 2.0F, 1, 1, 1, 0.0F, false));

		this.fin_end = new ModelRenderer(this);
		this.fin_end.setRotationPoint(0.0F, 0.0F, 3.0F);
		this.fin_middle.addChild(this.fin_end);
		this.fin_end.cubeList.add(new ModelBox(this.fin_end, 0, 45, -0.5F, -4.5F, 0.0F, 1, 1, 1, 0.0F, false));
		this.fin_end.cubeList.add(new ModelBox(this.fin_end, 20, 66, -1.0F, -3.5F, 0.0F, 2, 7, 1, 0.0F, false));
		this.fin_end.cubeList.add(new ModelBox(this.fin_end, 0, 47, -0.5F, -3.5F, 1.0F, 1, 1, 1, 0.0F, false));
		this.fin_end.cubeList.add(new ModelBox(this.fin_end, 20, 80, -0.5F, -2.5F, 2.0F, 1, 5, 1, 0.0F, false));
		this.fin_end.cubeList.add(new ModelBox(this.fin_end, 20, 74, -1.0F, -2.5F, 1.0F, 2, 5, 1, 0.0F, false));
		this.fin_end.cubeList.add(new ModelBox(this.fin_end, 4, 44, -0.5F, 2.5F, 1.0F, 1, 1, 1, 0.0F, false));
		this.fin_end.cubeList.add(new ModelBox(this.fin_end, 4, 42, -0.5F, 3.5F, 0.0F, 1, 1, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

		this.body.render(scaleFactor);
		this.fin.render(scaleFactor);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		double tick = ageInTicks + Minecraft.getMinecraft().getRenderPartialTicks();

		GlStateManager.rotate((float) (2.0D * Math.sin(tick * GuiHelper.TWO_PI / 10.0D)), 0.0F, 1.0F, 0.0F);

		this.setRotationAngle(this.body, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.fin, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.fin_middle, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.fin_end, 0.0F, 0.0F, 0.0F);

		float f = (float) (Math.toRadians(5.0D) * Math.sin(tick * GuiHelper.TWO_PI / 10.0D));
		this.fin.rotateAngleY += f;
		this.fin_middle.rotateAngleY += f;
		this.fin_end.rotateAngleY += f;
	}

}
