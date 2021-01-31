package meldexun.better_diving.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.model.entity.ModelSeamoth;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class RenderSeamoth extends EntityRenderer<EntitySeamoth> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterDiving.MOD_ID, "textures/entity/seamoth.png");
	private static final ModelSeamoth MODEL = new ModelSeamoth();

	public RenderSeamoth(EntityRendererManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.0F;
	}

	@Override
	public void render(EntitySeamoth entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.player;

		matrixStackIn.push();

		/*
		if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
			matrixStackIn.translate(0.0D, player.getEyeHeight(), 0.0D);
			this.setupRotation(entityIn, partialTicks, matrixStackIn);
			matrixStackIn.translate(0.0D, 0.8125D - player.getEyeHeight(), -0.32D);
		} else {
			matrixStackIn.translate(0.0D, 0.8125D, 0.0D);
			this.setupRotation(entityIn, partialTicks, matrixStackIn);
		}
		*/

		matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
		MODEL.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(TEXTURE)), packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.pop();

		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	protected void setupRotation(EntitySeamoth entity, float partialTicks, MatrixStack matrixStackIn) {
		float yaw = this.interpolateRotation(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
		float pitch = this.interpolateRotation(entity.prevRotationPitch, entity.rotationPitch, partialTicks);
		yaw = 0;
		pitch = 0;
		float f = (float) Math.toRadians(yaw);
		PlayerEntity player = Minecraft.getInstance().player;
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - yaw));
		matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-pitch));
		//matrixStackIn.rotate(new Quaternion(pitch * MathHelper.cos(f), 0.0F, pitch * MathHelper.sin(f), true));
		//matrixStackIn.rotate(new Quaternion(0.0F, 180.0F - yaw, 0.0F, true));
	}

	@Override
	public ResourceLocation getEntityTexture(EntitySeamoth entity) {
		return TEXTURE;
	}

	protected float interpolateRotation(float prevRotation, float rotation, float partialTicks) {
		return prevRotation + MathHelper.wrapDegrees(rotation - prevRotation) * partialTicks;
	}

}
