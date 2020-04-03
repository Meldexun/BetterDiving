package meldexun.better_diving.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSeamoth extends ModelBase {

	private final ModelRenderer bone;
	private final ModelRenderer left_wing;
	private final ModelRenderer right_wing;
	private final ModelRenderer antena;
	private final ModelRenderer window;
	private final ModelRenderer propellar;
	private final ModelRenderer back;
	private final ModelRenderer light;

	public ModelSeamoth(float y) {
		this.textureWidth = 512;
		this.textureHeight = 512;

		this.bone = new ModelRenderer(this);
		this.bone.setRotationPoint(0.0F, 0.0F + y, 0.0F);
		this.bone.cubeList.add(new ModelBox(this.bone, 0, 0, -12.0F, 12.0F, -12.0F, 24, 1, 24, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 0, 25, 12.0F, 2.0F, -13.0F, 1, 10, 26, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 0, 61, -13.0F, 2.0F, -13.0F, 1, 10, 26, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 96, 0, -12.0F, -13.0F, 12.0F, 24, 25, 1, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 54, 25, -13.0F, -13.0F, 8.0F, 1, 15, 5, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 54, 45, 12.0F, -13.0F, 8.0F, 1, 15, 5, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 66, 26, -8.0F, 11.0F, -13.0F, 16, 1, 1, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 66, 28, -12.0F, 2.0F, -13.0F, 4, 10, 1, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 66, 39, 8.0F, 2.0F, -13.0F, 4, 10, 1, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 146, 0, -4.0F, -13.0F, -7.0F, 8, 1, 1, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 146, 2, -5.0F, -13.0F, -6.0F, 10, 1, 2, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 146, 5, -6.0F, -13.0F, -4.0F, 12, 1, 3, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 146, 9, -7.0F, -13.0F, -1.0F, 14, 1, 3, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 146, 13, -8.0F, -13.0F, 2.0F, 16, 1, 3, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 146, 17, -9.0F, -13.0F, 5.0F, 18, 1, 2, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 146, 20, -10.0F, -13.0F, 7.0F, 20, 1, 1, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(this.bone, 146, 22, -12.0F, -13.0F, 8.0F, 24, 1, 4, 0.0F, false));

		this.left_wing = new ModelRenderer(this);
		this.left_wing.setRotationPoint(0.0F, 0.0F + y, 0.0F);
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 416, 128, 9.0F, 10.0F, -29.0F, 4, 1, 16, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 152, 9.0F, 2.0F, -30.0F, 4, 8, 1, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 416, 162, 13.0F, 10.0F, -28.0F, 3, 1, 44, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 161, 13.0F, 2.0F, -29.0F, 3, 8, 1, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 170, 16.0F, 2.0F, -28.0F, 2, 3, 1, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 416, 207, 16.0F, 10.0F, -27.0F, 2, 1, 42, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 416, 400, 22.0F, 10.0F, -19.0F, 1, 1, 26, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 416, 291, 19.0F, 10.0F, -25.0F, 1, 1, 38, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 416, 250, 18.0F, 10.0F, -26.0F, 1, 1, 40, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 416, 330, 20.0F, 10.0F, -24.0F, 1, 1, 36, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 416, 367, 21.0F, 10.0F, -22.0F, 1, 1, 32, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 216, 22.0F, 2.0F, -22.0F, 1, 8, 3, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 206, 21.0F, 2.0F, -24.0F, 1, 3, 2, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 197, 20.0F, 2.0F, -25.0F, 1, 8, 1, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 188, 19.0F, 2.0F, -26.0F, 1, 8, 1, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 179, 18.0F, 2.0F, -27.0F, 1, 8, 1, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 227, 23.0F, 2.0F, -19.0F, 1, 8, 26, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 327, 12.0F, 2.0F, 17.0F, 1, 8, 1, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 309, 16.0F, 2.0F, 15.0F, 2, 3, 1, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 318, 13.0F, 2.0F, 16.0F, 3, 8, 1, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 300, 18.0F, 2.0F, 14.0F, 1, 8, 1, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 282, 20.0F, 2.0F, 12.0F, 1, 8, 1, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 291, 19.0F, 2.0F, 13.0F, 1, 8, 1, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 272, 21.0F, 2.0F, 10.0F, 1, 8, 2, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 261, 22.0F, 2.0F, 7.0F, 1, 8, 3, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 416, 427, 12.0F, 10.0F, 13.0F, 1, 1, 4, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 256, 128, 9.0F, 1.0F, -29.0F, 4, 1, 16, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 256, 400, 22.0F, 1.0F, -19.0F, 1, 1, 26, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 256, 367, 21.0F, 1.0F, -22.0F, 1, 1, 32, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 256, 330, 20.0F, 1.0F, -24.0F, 1, 1, 36, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 256, 291, 19.0F, 1.0F, -25.0F, 1, 1, 38, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 256, 250, 18.0F, 1.0F, -26.0F, 1, 1, 40, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 256, 207, 16.0F, 1.0F, -27.0F, 2, 1, 42, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 256, 162, 13.0F, 1.0F, -28.0F, 3, 1, 44, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 256, 427, 12.0F, 1.0F, 13.0F, 1, 1, 4, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 128, 8.0F, 2.0F, -29.0F, 1, 8, 16, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 175, 16.0F, 7.0F, -28.0F, 2, 3, 1, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 211, 21.0F, 7.0F, -24.0F, 1, 3, 2, 0.0F, false));
		this.left_wing.cubeList.add(new ModelBox(this.left_wing, 352, 314, 16.0F, 7.0F, 15.0F, 2, 3, 1, 0.0F, false));

		this.right_wing = new ModelRenderer(this);
		this.right_wing.setRotationPoint(0.0F, 0.0F + y, 0.0F);
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 291, -20.0F, 2.0F, 13.0F, 1, 8, 1, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 160, 128, -13.0F, 10.0F, -29.0F, 4, 1, 16, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 152, -13.0F, 2.0F, -30.0F, 4, 8, 1, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 160, 162, -16.0F, 10.0F, -28.0F, 3, 1, 44, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 161, -16.0F, 2.0F, -29.0F, 3, 8, 1, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 170, -18.0F, 2.0F, -28.0F, 2, 3, 1, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 160, 207, -18.0F, 10.0F, -27.0F, 2, 1, 42, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 160, 400, -23.0F, 10.0F, -19.0F, 1, 1, 26, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 160, 291, -20.0F, 10.0F, -25.0F, 1, 1, 38, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 160, 250, -19.0F, 10.0F, -26.0F, 1, 1, 40, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 160, 330, -21.0F, 10.0F, -24.0F, 1, 1, 36, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 160, 367, -22.0F, 10.0F, -22.0F, 1, 1, 32, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 216, -23.0F, 2.0F, -22.0F, 1, 8, 3, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 206, -22.0F, 2.0F, -24.0F, 1, 3, 2, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 197, -21.0F, 2.0F, -25.0F, 1, 8, 1, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 188, -20.0F, 2.0F, -26.0F, 1, 8, 1, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 179, -19.0F, 2.0F, -27.0F, 1, 8, 1, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 227, -24.0F, 2.0F, -19.0F, 1, 8, 26, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 327, -13.0F, 2.0F, 17.0F, 1, 8, 1, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 309, -18.0F, 2.0F, 15.0F, 2, 3, 1, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 318, -16.0F, 2.0F, 16.0F, 3, 8, 1, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 300, -19.0F, 2.0F, 14.0F, 1, 8, 1, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 282, -21.0F, 2.0F, 12.0F, 1, 8, 1, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 272, -22.0F, 2.0F, 10.0F, 1, 8, 2, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 261, -23.0F, 2.0F, 7.0F, 1, 8, 3, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 160, 427, -13.0F, 10.0F, 13.0F, 1, 1, 4, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 0, 128, -13.0F, 1.0F, -29.0F, 4, 1, 16, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 0, 400, -23.0F, 1.0F, -19.0F, 1, 1, 26, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 0, 291, -20.0F, 1.0F, -25.0F, 1, 1, 38, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 0, 367, -22.0F, 1.0F, -22.0F, 1, 1, 32, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 0, 330, -21.0F, 1.0F, -24.0F, 1, 1, 36, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 0, 250, -19.0F, 1.0F, -26.0F, 1, 1, 40, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 0, 207, -18.0F, 1.0F, -27.0F, 2, 1, 42, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 0, 162, -16.0F, 1.0F, -28.0F, 3, 1, 44, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 0, 427, -13.0F, 1.0F, 13.0F, 1, 1, 4, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 128, -9.0F, 2.0F, -29.0F, 1, 8, 16, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 314, -18.0F, 7.0F, 15.0F, 2, 3, 1, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 211, -22.0F, 7.0F, -24.0F, 1, 3, 2, 0.0F, false));
		this.right_wing.cubeList.add(new ModelBox(this.right_wing, 96, 175, -18.0F, 7.0F, -28.0F, 2, 3, 1, 0.0F, false));

		this.antena = new ModelRenderer(this);
		this.antena.setRotationPoint(0.0F, -13.0F + y, 10.0F);
		this.setRotationAngle(this.antena, -0.7854F, 0.0F, 0.0F);
		this.antena.cubeList.add(new ModelBox(this.antena, 480, 0, -1.0F, -8.0F, 0.0F, 2, 8, 2, 0.0F, false));

		this.window = new ModelRenderer(this);
		this.window.setRotationPoint(0.0F, 0.0F + y, 0.0F);
		this.window.cubeList.add(new ModelBox(this.window, 0, 448, -13.0F, -12.9F, -13.0F, 26, 0, 21, 0.0F, false));
		this.window.cubeList.add(new ModelBox(this.window, 94, 448, -13.0F, -13.0F, -12.9F, 26, 24, 0, 0.0F, false));
		this.window.cubeList.add(new ModelBox(this.window, 146, 448, -12.9F, -13.0F, -13.0F, 0, 15, 21, 0.0F, false));
		this.window.cubeList.add(new ModelBox(this.window, 188, 448, 12.9F, -13.0F, -13.0F, 0, 15, 21, 0.0F, false));

		this.propellar = new ModelRenderer(this);
		this.propellar.setRotationPoint(0.0F, 0.0F + y, 28.5F);
		this.propellar.cubeList.add(new ModelBox(this.propellar, 448, 0, -0.5F, -3.5F, -0.5F, 1, 7, 1, 0.0F, false));
		this.propellar.cubeList.add(new ModelBox(this.propellar, 448, 8, -3.5F, -0.5F, -0.5F, 7, 1, 1, 0.0F, false));

		this.back = new ModelRenderer(this);
		this.back.setRotationPoint(0.0F, 0.0F + y, 0.0F);
		this.back.cubeList.add(new ModelBox(this.back, 256, 0, -12.0F, -12.0F, 13.0F, 24, 24, 6, 0.0F, false));
		this.back.cubeList.add(new ModelBox(this.back, 256, 30, -11.0F, -11.0F, 19.0F, 22, 22, 3, 0.0F, false));
		this.back.cubeList.add(new ModelBox(this.back, 256, 55, -10.0F, -10.0F, 22.0F, 20, 20, 2, 0.0F, false));
		this.back.cubeList.add(new ModelBox(this.back, 256, 77, -9.0F, -9.0F, 24.0F, 18, 18, 2, 0.0F, false));
		this.back.cubeList.add(new ModelBox(this.back, 316, 0, -8.0F, -8.0F, 26.0F, 16, 4, 3, 0.0F, false));
		this.back.cubeList.add(new ModelBox(this.back, 316, 7, -8.0F, 4.0F, 26.0F, 16, 4, 3, 0.0F, false));
		this.back.cubeList.add(new ModelBox(this.back, 316, 14, 4.0F, -4.0F, 26.0F, 4, 8, 3, 0.0F, false));
		this.back.cubeList.add(new ModelBox(this.back, 316, 25, -8.0F, -4.0F, 26.0F, 4, 8, 3, 0.0F, false));
		this.back.cubeList.add(new ModelBox(this.back, 316, 36, -4.0F, -4.0F, 26.0F, 8, 8, 1, 0.0F, false));
		this.back.cubeList.add(new ModelBox(this.back, 316, 45, -0.5F, -0.5F, 27.0F, 1, 1, 3, 0.0F, false));

		this.light = new ModelRenderer(this);
		this.light.setRotationPoint(0.0F, 0.0F + y, 0.0F);
		this.light.cubeList.add(new ModelBox(this.light, 0, 480, 16.0F, 5.0F, -28.0F, 2, 2, 1, 0.0F, false));
		this.light.cubeList.add(new ModelBox(this.light, 0, 480, 21.0F, 5.0F, -24.0F, 1, 2, 2, 0.0F, false));
		this.light.cubeList.add(new ModelBox(this.light, 0, 480, -18.0F, 5.0F, -28.0F, 2, 2, 1, 0.0F, false));
		this.light.cubeList.add(new ModelBox(this.light, 0, 480, -22.0F, 5.0F, -24.0F, 1, 2, 2, 0.0F, false));
		this.light.cubeList.add(new ModelBox(this.light, 0, 480, 16.0F, 5.0F, 15.0F, 2, 2, 1, 0.0F, false));
		this.light.cubeList.add(new ModelBox(this.light, 0, 480, -18.0F, 5.0F, 15.0F, 2, 2, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.bone.render(scale);
		this.left_wing.render(scale);
		this.right_wing.render(scale);
		this.antena.render(scale);
		this.window.render(scale);
		this.propellar.render(scale);
		this.back.render(scale);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
		this.light.render(scale);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

}
