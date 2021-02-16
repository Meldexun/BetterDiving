package meldexun.better_diving.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;

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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderSeamoth extends EntityRenderer<EntitySeamoth> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterDiving.MOD_ID, "textures/entity/seamoth.png");
	private static final ModelSeamoth MODEL = new ModelSeamoth();

	public RenderSeamoth(EntityRendererManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.5F;
	}

	@Override
	public void render(EntitySeamoth entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.player;

		matrixStackIn.push();

		if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
			matrixStackIn.translate(0.0D, player.getEyeHeight(), 0.0D);
			this.setupRotation(entityIn, matrixStackIn);
			matrixStackIn.translate(0.0D, 0.8125D - player.getEyeHeight(), -0.16D);
		} else {
			matrixStackIn.translate(0.0D, 0.8125D, 0.0D);
			this.setupRotation(entityIn, matrixStackIn);
		}

		matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
		MODEL.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntityTranslucentCull(TEXTURE)), packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.pop();

		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

		// otherwise entity will be invisible when player is outside of the water and entity is underwater
		((IRenderTypeBuffer.Impl) bufferIn).finish(RenderType.getEntityTranslucentCull(TEXTURE));
	}

	protected void setupRotation(EntitySeamoth entity, MatrixStack matrixStackIn) {
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - entity.rotationYaw));
		matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-entity.rotationPitch));
	}

	@Override
	public ResourceLocation getEntityTexture(EntitySeamoth entity) {
		return TEXTURE;
	}

	protected float interpolateRotation(float prevRotation, float rotation, float partialTicks) {
		return prevRotation + MathHelper.wrapDegrees(rotation - prevRotation) * partialTicks;
	}

}
