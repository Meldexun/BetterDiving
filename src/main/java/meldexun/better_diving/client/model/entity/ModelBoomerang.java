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
public class ModelBoomerang extends ModelBase {

	private final ModelRenderer body;
	private final ModelRenderer top_fin;
	private final ModelRenderer top_fin_end;
	private final ModelRenderer bottom_fin;
	private final ModelRenderer bottom_fin_end;

	public ModelBoomerang() {
		this.textureWidth = 128;
		this.textureHeight = 128;

		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.cubeList.add(new ModelBox(this.body, 0, 0, -0.5F, -1.0F, -8.0F, 1, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 3, -1.0F, -2.0F, -7.0F, 2, 4, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 10, 0, -1.0F, -3.0F, -6.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 16, 0, -1.0F, 2.0F, -6.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 10, 2, -1.0F, -4.0F, -5.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 10, 4, -1.0F, -4.0F, -4.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 16, 2, -1.0F, 3.0F, -5.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 16, 4, -1.0F, 3.0F, -4.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 10, 10, -1.0F, -6.0F, -1.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 10, 12, -1.0F, -6.0F, 0.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 16, 10, -1.0F, 5.0F, -1.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 16, 12, -1.0F, 5.0F, 0.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 10, 6, -1.0F, -5.0F, -3.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 10, 8, -1.0F, -5.0F, -2.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 16, 6, -1.0F, 4.0F, -3.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 16, 8, -1.0F, 4.0F, -2.0F, 2, 1, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 8, -2.0F, -2.0F, -6.0F, 4, 4, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 13, -2.0F, -3.0F, -5.0F, 4, 6, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 20, -2.0F, -3.0F, -4.0F, 4, 6, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 27, -2.0F, -4.0F, -3.0F, 4, 8, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 36, -2.0F, -4.0F, -2.0F, 4, 8, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 45, -2.0F, -5.0F, -1.0F, 4, 10, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 56, -2.0F, -5.0F, 0.0F, 4, 10, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 67, -2.0F, -4.0F, 1.0F, 4, 8, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 76, -2.0F, -1.0F, 2.0F, 4, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 94, -1.0F, -2.0F, 3.0F, 2, 4, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 99, -1.0F, -1.0F, 4.0F, 2, 2, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 16, 14, -1.0F, 4.0F, 1.0F, 2, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 10, 14, -1.0F, -7.0F, 1.0F, 2, 3, 1, 0.0F, false));
		this.body.cubeList.add(new ModelBox(this.body, 0, 79, -1.0F, -7.0F, 2.0F, 2, 14, 1, 0.0F, false));

		this.top_fin = new ModelRenderer(this);
		this.top_fin.setRotationPoint(0.0F, -6.0F, 3.0F);
		this.top_fin.cubeList.add(new ModelBox(this.top_fin, 10, 23, -1.0F, -2.0F, 1.0F, 2, 3, 1, 0.0F, false));
		this.top_fin.cubeList.add(new ModelBox(this.top_fin, 10, 18, -1.0F, -2.0F, 0.0F, 2, 4, 1, 0.0F, false));
		this.top_fin.cubeList.add(new ModelBox(this.top_fin, 10, 27, -1.0F, -3.0F, 2.0F, 2, 3, 1, 0.0F, false));

		this.top_fin_end = new ModelRenderer(this);
		this.top_fin_end.setRotationPoint(0.0F, -2.0F, 3.0F);
		this.top_fin.addChild(this.top_fin_end);
		this.top_fin_end.cubeList.add(new ModelBox(this.top_fin_end, 10, 31, -1.0F, -1.0F, 0.0F, 2, 2, 1, 0.0F, false));
		this.top_fin_end.cubeList.add(new ModelBox(this.top_fin_end, 10, 34, -1.0F, -1.0F, 1.0F, 2, 2, 1, 0.0F, false));
		this.top_fin_end.cubeList.add(new ModelBox(this.top_fin_end, 10, 37, -1.0F, -1.0F, 2.0F, 2, 1, 1, 0.0F, false));

		this.bottom_fin = new ModelRenderer(this);
		this.bottom_fin.setRotationPoint(0.0F, 6.0F, 3.0F);
		this.bottom_fin.cubeList.add(new ModelBox(this.bottom_fin, 16, 18, -1.0F, -2.0F, 0.0F, 2, 4, 1, 0.0F, false));
		this.bottom_fin.cubeList.add(new ModelBox(this.bottom_fin, 16, 23, -1.0F, -1.0F, 1.0F, 2, 3, 1, 0.0F, false));
		this.bottom_fin.cubeList.add(new ModelBox(this.bottom_fin, 16, 27, -1.0F, 0.0F, 2.0F, 2, 3, 1, 0.0F, false));

		this.bottom_fin_end = new ModelRenderer(this);
		this.bottom_fin_end.setRotationPoint(0.0F, 2.0F, 3.0F);
		this.bottom_fin.addChild(this.bottom_fin_end);
		this.bottom_fin_end.cubeList.add(new ModelBox(this.bottom_fin_end, 16, 31, -1.0F, -1.0F, 0.0F, 2, 2, 1, 0.0F, false));
		this.bottom_fin_end.cubeList.add(new ModelBox(this.bottom_fin_end, 16, 34, -1.0F, -1.0F, 1.0F, 2, 2, 1, 0.0F, false));
		this.bottom_fin_end.cubeList.add(new ModelBox(this.bottom_fin_end, 16, 37, -1.0F, 0.0F, 2.0F, 2, 1, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

		this.body.render(scaleFactor);
		this.top_fin.render(scaleFactor);
		this.bottom_fin.render(scaleFactor);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		double tick = (double) (ageInTicks + Minecraft.getMinecraft().getRenderPartialTicks());

		GlStateManager.rotate((float) (2.0D * Math.sin(tick * GuiHelper.TWO_PI / 20.0D)), 0.0F, 1.0F, 0.0F);

		this.setRotationAngle(this.body, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.top_fin, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.top_fin_end, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.bottom_fin, 0.0F, 0.0F, 0.0F);
		this.setRotationAngle(this.bottom_fin_end, 0.0F, 0.0F, 0.0F);

		float f = (float) (Math.toRadians(5.0D) * Math.sin(tick * GuiHelper.TWO_PI / 20.0D));
		this.top_fin.rotateAngleY += f;
		this.top_fin_end.rotateAngleY += f;
		this.bottom_fin.rotateAngleY += f;
		this.bottom_fin_end.rotateAngleY += f;
	}

}
