package meldexun.better_diving.client.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelSeamoth extends EntityModel<EntitySeamoth> {

	private final ModelRenderer bone;
	private final ModelRenderer left_wing;
	private final ModelRenderer right_wing;
	private final ModelRenderer antena;
	private final ModelRenderer window;
	private final ModelRenderer propellar;
	private final ModelRenderer back;
	private final ModelRenderer light;

	public ModelSeamoth() {
		this.texWidth = 512;
		this.texHeight = 512;

		this.bone = new ModelRenderer(this);
		this.bone.setPos(0.0F, 0.0F, 0.0F);
		this.bone.texOffs(0, 0).addBox(-12.0F, 12.0F, -12.0F, 24.0F, 1.0F, 24.0F, 0.0F, false);
		this.bone.texOffs(0, 25).addBox(12.0F, 2.0F, -13.0F, 1.0F, 10.0F, 26.0F, 0.0F, false);
		this.bone.texOffs(0, 61).addBox(-13.0F, 2.0F, -13.0F, 1.0F, 10.0F, 26.0F, 0.0F, false);
		this.bone.texOffs(96, 0).addBox(-12.0F, -13.0F, 12.0F, 24.0F, 25.0F, 1.0F, 0.0F, false);
		this.bone.texOffs(54, 25).addBox(-13.0F, -13.0F, 8.0F, 1.0F, 15.0F, 5.0F, 0.0F, false);
		this.bone.texOffs(54, 45).addBox(12.0F, -13.0F, 8.0F, 1.0F, 15.0F, 5.0F, 0.0F, false);
		this.bone.texOffs(66, 26).addBox(-8.0F, 11.0F, -13.0F, 16.0F, 1.0F, 1.0F, 0.0F, false);
		this.bone.texOffs(66, 28).addBox(-12.0F, 2.0F, -13.0F, 4.0F, 10.0F, 1.0F, 0.0F, false);
		this.bone.texOffs(66, 39).addBox(8.0F, 2.0F, -13.0F, 4.0F, 10.0F, 1.0F, 0.0F, false);
		this.bone.texOffs(146, 0).addBox(-4.0F, -13.0F, -7.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		this.bone.texOffs(146, 2).addBox(-5.0F, -13.0F, -6.0F, 10.0F, 1.0F, 2.0F, 0.0F, false);
		this.bone.texOffs(146, 5).addBox(-6.0F, -13.0F, -4.0F, 12.0F, 1.0F, 3.0F, 0.0F, false);
		this.bone.texOffs(146, 9).addBox(-7.0F, -13.0F, -1.0F, 14.0F, 1.0F, 3.0F, 0.0F, false);
		this.bone.texOffs(146, 13).addBox(-8.0F, -13.0F, 2.0F, 16.0F, 1.0F, 3.0F, 0.0F, false);
		this.bone.texOffs(146, 17).addBox(-9.0F, -13.0F, 5.0F, 18.0F, 1.0F, 2.0F, 0.0F, false);
		this.bone.texOffs(146, 20).addBox(-10.0F, -13.0F, 7.0F, 20.0F, 1.0F, 1.0F, 0.0F, false);
		this.bone.texOffs(146, 22).addBox(-12.0F, -13.0F, 8.0F, 24.0F, 1.0F, 4.0F, 0.0F, false);

		this.left_wing = new ModelRenderer(this);
		this.left_wing.setPos(0.0F, 0.0F, 0.0F);
		this.left_wing.texOffs(416, 128).addBox(9.0F, 10.0F, -29.0F, 4.0F, 1.0F, 16.0F, 0.0F, false);
		this.left_wing.texOffs(352, 152).addBox(9.0F, 2.0F, -30.0F, 4.0F, 8.0F, 1.0F, 0.0F, false);
		this.left_wing.texOffs(416, 162).addBox(13.0F, 10.0F, -28.0F, 3.0F, 1.0F, 44.0F, 0.0F, false);
		this.left_wing.texOffs(352, 161).addBox(13.0F, 2.0F, -29.0F, 3.0F, 8.0F, 1.0F, 0.0F, false);
		this.left_wing.texOffs(352, 170).addBox(16.0F, 2.0F, -28.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
		this.left_wing.texOffs(416, 207).addBox(16.0F, 10.0F, -27.0F, 2.0F, 1.0F, 42.0F, 0.0F, false);
		this.left_wing.texOffs(416, 400).addBox(22.0F, 10.0F, -19.0F, 1.0F, 1.0F, 26.0F, 0.0F, false);
		this.left_wing.texOffs(416, 291).addBox(19.0F, 10.0F, -25.0F, 1.0F, 1.0F, 38.0F, 0.0F, false);
		this.left_wing.texOffs(416, 250).addBox(18.0F, 10.0F, -26.0F, 1.0F, 1.0F, 40.0F, 0.0F, false);
		this.left_wing.texOffs(416, 330).addBox(20.0F, 10.0F, -24.0F, 1.0F, 1.0F, 36.0F, 0.0F, false);
		this.left_wing.texOffs(416, 367).addBox(21.0F, 10.0F, -22.0F, 1.0F, 1.0F, 32.0F, 0.0F, false);
		this.left_wing.texOffs(352, 216).addBox(22.0F, 2.0F, -22.0F, 1.0F, 8.0F, 3.0F, 0.0F, false);
		this.left_wing.texOffs(352, 206).addBox(21.0F, 2.0F, -24.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);
		this.left_wing.texOffs(352, 197).addBox(20.0F, 2.0F, -25.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.left_wing.texOffs(352, 188).addBox(19.0F, 2.0F, -26.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.left_wing.texOffs(352, 179).addBox(18.0F, 2.0F, -27.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.left_wing.texOffs(352, 227).addBox(23.0F, 2.0F, -19.0F, 1.0F, 8.0F, 26.0F, 0.0F, false);
		this.left_wing.texOffs(352, 327).addBox(12.0F, 2.0F, 17.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.left_wing.texOffs(352, 309).addBox(16.0F, 2.0F, 15.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
		this.left_wing.texOffs(352, 318).addBox(13.0F, 2.0F, 16.0F, 3.0F, 8.0F, 1.0F, 0.0F, false);
		this.left_wing.texOffs(352, 300).addBox(18.0F, 2.0F, 14.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.left_wing.texOffs(352, 282).addBox(20.0F, 2.0F, 12.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.left_wing.texOffs(352, 291).addBox(19.0F, 2.0F, 13.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.left_wing.texOffs(352, 272).addBox(21.0F, 2.0F, 10.0F, 1.0F, 8.0F, 2.0F, 0.0F, false);
		this.left_wing.texOffs(352, 261).addBox(22.0F, 2.0F, 7.0F, 1.0F, 8.0F, 3.0F, 0.0F, false);
		this.left_wing.texOffs(416, 427).addBox(12.0F, 10.0F, 13.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		this.left_wing.texOffs(256, 128).addBox(9.0F, 1.0F, -29.0F, 4.0F, 1.0F, 16.0F, 0.0F, false);
		this.left_wing.texOffs(256, 400).addBox(22.0F, 1.0F, -19.0F, 1.0F, 1.0F, 26.0F, 0.0F, false);
		this.left_wing.texOffs(256, 367).addBox(21.0F, 1.0F, -22.0F, 1.0F, 1.0F, 32.0F, 0.0F, false);
		this.left_wing.texOffs(256, 330).addBox(20.0F, 1.0F, -24.0F, 1.0F, 1.0F, 36.0F, 0.0F, false);
		this.left_wing.texOffs(256, 291).addBox(19.0F, 1.0F, -25.0F, 1.0F, 1.0F, 38.0F, 0.0F, false);
		this.left_wing.texOffs(256, 250).addBox(18.0F, 1.0F, -26.0F, 1.0F, 1.0F, 40.0F, 0.0F, false);
		this.left_wing.texOffs(256, 207).addBox(16.0F, 1.0F, -27.0F, 2.0F, 1.0F, 42.0F, 0.0F, false);
		this.left_wing.texOffs(256, 162).addBox(13.0F, 1.0F, -28.0F, 3.0F, 1.0F, 44.0F, 0.0F, false);
		this.left_wing.texOffs(256, 427).addBox(12.0F, 1.0F, 13.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		this.left_wing.texOffs(352, 128).addBox(8.0F, 2.0F, -29.0F, 1.0F, 8.0F, 16.0F, 0.0F, false);
		this.left_wing.texOffs(352, 175).addBox(16.0F, 7.0F, -28.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
		this.left_wing.texOffs(352, 211).addBox(21.0F, 7.0F, -24.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);
		this.left_wing.texOffs(352, 314).addBox(16.0F, 7.0F, 15.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);

		this.right_wing = new ModelRenderer(this);
		this.right_wing.setPos(0.0F, 0.0F, 0.0F);
		this.right_wing.texOffs(96, 291).addBox(-20.0F, 2.0F, 13.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.right_wing.texOffs(160, 128).addBox(-13.0F, 10.0F, -29.0F, 4.0F, 1.0F, 16.0F, 0.0F, false);
		this.right_wing.texOffs(96, 152).addBox(-13.0F, 2.0F, -30.0F, 4.0F, 8.0F, 1.0F, 0.0F, false);
		this.right_wing.texOffs(160, 162).addBox(-16.0F, 10.0F, -28.0F, 3.0F, 1.0F, 44.0F, 0.0F, false);
		this.right_wing.texOffs(96, 161).addBox(-16.0F, 2.0F, -29.0F, 3.0F, 8.0F, 1.0F, 0.0F, false);
		this.right_wing.texOffs(96, 170).addBox(-18.0F, 2.0F, -28.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
		this.right_wing.texOffs(160, 207).addBox(-18.0F, 10.0F, -27.0F, 2.0F, 1.0F, 42.0F, 0.0F, false);
		this.right_wing.texOffs(160, 400).addBox(-23.0F, 10.0F, -19.0F, 1.0F, 1.0F, 26.0F, 0.0F, false);
		this.right_wing.texOffs(160, 291).addBox(-20.0F, 10.0F, -25.0F, 1.0F, 1.0F, 38.0F, 0.0F, false);
		this.right_wing.texOffs(160, 250).addBox(-19.0F, 10.0F, -26.0F, 1.0F, 1.0F, 40.0F, 0.0F, false);
		this.right_wing.texOffs(160, 330).addBox(-21.0F, 10.0F, -24.0F, 1.0F, 1.0F, 36.0F, 0.0F, false);
		this.right_wing.texOffs(160, 367).addBox(-22.0F, 10.0F, -22.0F, 1.0F, 1.0F, 32.0F, 0.0F, false);
		this.right_wing.texOffs(96, 216).addBox(-23.0F, 2.0F, -22.0F, 1.0F, 8.0F, 3.0F, 0.0F, false);
		this.right_wing.texOffs(96, 206).addBox(-22.0F, 2.0F, -24.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);
		this.right_wing.texOffs(96, 197).addBox(-21.0F, 2.0F, -25.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.right_wing.texOffs(96, 188).addBox(-20.0F, 2.0F, -26.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.right_wing.texOffs(96, 179).addBox(-19.0F, 2.0F, -27.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.right_wing.texOffs(96, 227).addBox(-24.0F, 2.0F, -19.0F, 1.0F, 8.0F, 26.0F, 0.0F, false);
		this.right_wing.texOffs(96, 327).addBox(-13.0F, 2.0F, 17.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.right_wing.texOffs(96, 309).addBox(-18.0F, 2.0F, 15.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
		this.right_wing.texOffs(96, 318).addBox(-16.0F, 2.0F, 16.0F, 3.0F, 8.0F, 1.0F, 0.0F, false);
		this.right_wing.texOffs(96, 300).addBox(-19.0F, 2.0F, 14.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.right_wing.texOffs(96, 282).addBox(-21.0F, 2.0F, 12.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		this.right_wing.texOffs(96, 272).addBox(-22.0F, 2.0F, 10.0F, 1.0F, 8.0F, 2.0F, 0.0F, false);
		this.right_wing.texOffs(96, 261).addBox(-23.0F, 2.0F, 7.0F, 1.0F, 8.0F, 3.0F, 0.0F, false);
		this.right_wing.texOffs(160, 427).addBox(-13.0F, 10.0F, 13.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		this.right_wing.texOffs(0, 128).addBox(-13.0F, 1.0F, -29.0F, 4.0F, 1.0F, 16.0F, 0.0F, false);
		this.right_wing.texOffs(0, 400).addBox(-23.0F, 1.0F, -19.0F, 1.0F, 1.0F, 26.0F, 0.0F, false);
		this.right_wing.texOffs(0, 291).addBox(-20.0F, 1.0F, -25.0F, 1.0F, 1.0F, 38.0F, 0.0F, false);
		this.right_wing.texOffs(0, 367).addBox(-22.0F, 1.0F, -22.0F, 1.0F, 1.0F, 32.0F, 0.0F, false);
		this.right_wing.texOffs(0, 330).addBox(-21.0F, 1.0F, -24.0F, 1.0F, 1.0F, 36.0F, 0.0F, false);
		this.right_wing.texOffs(0, 250).addBox(-19.0F, 1.0F, -26.0F, 1.0F, 1.0F, 40.0F, 0.0F, false);
		this.right_wing.texOffs(0, 207).addBox(-18.0F, 1.0F, -27.0F, 2.0F, 1.0F, 42.0F, 0.0F, false);
		this.right_wing.texOffs(0, 162).addBox(-16.0F, 1.0F, -28.0F, 3.0F, 1.0F, 44.0F, 0.0F, false);
		this.right_wing.texOffs(0, 427).addBox(-13.0F, 1.0F, 13.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		this.right_wing.texOffs(96, 128).addBox(-9.0F, 2.0F, -29.0F, 1.0F, 8.0F, 16.0F, 0.0F, false);
		this.right_wing.texOffs(96, 314).addBox(-18.0F, 7.0F, 15.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
		this.right_wing.texOffs(96, 211).addBox(-22.0F, 7.0F, -24.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);
		this.right_wing.texOffs(96, 175).addBox(-18.0F, 7.0F, -28.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);

		this.antena = new ModelRenderer(this);
		this.antena.setPos(0.0F, -13.0F, 10.0F);
		this.setRotationAngle(this.antena, -0.7854F, 0.0F, 0.0F);
		this.antena.texOffs(480, 0).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

		this.window = new ModelRenderer(this);
		this.window.setPos(0.0F, 0.0F, 0.0F);
		this.window.texOffs(0, 448).addBox(-13.0F, -12.9F, -13.0F, 26.0F, 0.0F, 21.0F, 0.0F, false);
		this.window.texOffs(94, 448).addBox(-13.0F, -13.0F, -12.9F, 26.0F, 24.0F, 0.0F, 0.0F, false);
		this.window.texOffs(146, 448).addBox(-12.9F, -13.0F, -13.0F, 0.0F, 15.0F, 21.0F, 0.0F, false);
		this.window.texOffs(188, 448).addBox(12.9F, -13.0F, -13.0F, 0.0F, 15.0F, 21.0F, 0.0F, false);

		this.propellar = new ModelRenderer(this);
		this.propellar.setPos(0.0F, 0.0F, 28.5F);
		this.propellar.texOffs(448, 0).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, 0.0F, false);
		this.propellar.texOffs(448, 8).addBox(-3.5F, -0.5F, -0.5F, 7.0F, 1.0F, 1.0F, 0.0F, false);

		this.back = new ModelRenderer(this);
		this.back.setPos(0.0F, 0.0F, 0.0F);
		this.back.texOffs(256, 0).addBox(-12.0F, -12.0F, 13.0F, 24.0F, 24.0F, 6.0F, 0.0F, false);
		this.back.texOffs(256, 30).addBox(-11.0F, -11.0F, 19.0F, 22.0F, 22.0F, 3.0F, 0.0F, false);
		this.back.texOffs(256, 55).addBox(-10.0F, -10.0F, 22.0F, 20.0F, 20.0F, 2.0F, 0.0F, false);
		this.back.texOffs(256, 77).addBox(-9.0F, -9.0F, 24.0F, 18.0F, 18.0F, 2.0F, 0.0F, false);
		this.back.texOffs(316, 0).addBox(-8.0F, -8.0F, 26.0F, 16.0F, 4.0F, 3.0F, 0.0F, false);
		this.back.texOffs(316, 7).addBox(-8.0F, 4.0F, 26.0F, 16.0F, 4.0F, 3.0F, 0.0F, false);
		this.back.texOffs(316, 14).addBox(4.0F, -4.0F, 26.0F, 4.0F, 8.0F, 3.0F, 0.0F, false);
		this.back.texOffs(316, 25).addBox(-8.0F, -4.0F, 26.0F, 4.0F, 8.0F, 3.0F, 0.0F, false);
		this.back.texOffs(316, 36).addBox(-4.0F, -4.0F, 26.0F, 8.0F, 8.0F, 1.0F, 0.0F, false);
		this.back.texOffs(316, 45).addBox(-0.5F, -0.5F, 27.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		this.light = new ModelRenderer(this);
		this.light.setPos(0.0F, 0.0F, 0.0F);
		this.light.texOffs(0, 480).addBox(16.0F, 5.0F, -28.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		this.light.texOffs(0, 480).addBox(21.0F, 5.0F, -24.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		this.light.texOffs(0, 480).addBox(-18.0F, 5.0F, -28.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		this.light.texOffs(0, 480).addBox(-22.0F, 5.0F, -24.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		this.light.texOffs(0, 480).addBox(16.0F, 5.0F, 15.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		this.light.texOffs(0, 480).addBox(-18.0F, 5.0F, 15.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(EntitySeamoth entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// previously the render function, render code was moved to a method below
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.bone.render(matrixStack, buffer, packedLight, packedOverlay);
		this.left_wing.render(matrixStack, buffer, packedLight, packedOverlay);
		this.right_wing.render(matrixStack, buffer, packedLight, packedOverlay);
		this.antena.render(matrixStack, buffer, packedLight, packedOverlay);
		this.window.render(matrixStack, buffer, packedLight, packedOverlay);
		this.propellar.render(matrixStack, buffer, packedLight, packedOverlay);
		this.back.render(matrixStack, buffer, packedLight, packedOverlay);
		this.light.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

}
