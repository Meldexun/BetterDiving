package meldexun.better_diving.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.model.entity.ModelSeamoth;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

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

		GL11.glPushMatrix();

		if (player == entityIn.getControllingPassenger() && mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
			GL11.glTranslated(0.0D, player.getEyeHeight(), 0.0D);
			this.setupRotation(entityIn, partialTicks);
			GL11.glTranslated(0.0D, 0.8125D - player.getEyeHeight(), -0.32D);
		} else {
			GL11.glTranslated(0.0D, 0.8125D, 0.0D);
			this.setupRotation(entityIn, partialTicks);
		}

		GL11.glScaled(-1.0D, -1.0D, 1.0D);
		MODEL.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntityTranslucentCull(TEXTURE)), packedLightIn, 0, 1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	protected void setupRotation(Entity entity, float partialTicks) {
		double yaw = this.interpolateRotation(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
		double pitch = this.interpolateRotation(entity.prevRotationPitch, entity.rotationPitch, partialTicks);
		// interpolated yaw and pitch does NOT work
		GL11.glRotated(entity.rotationPitch, Math.cos(Math.toRadians(entity.rotationYaw)), 0.0D, Math.sin(Math.toRadians(entity.rotationYaw)));
		GL11.glRotated(180.0D - entity.rotationYaw, 0.0D, 1.0D, 0.0D);
	}

	@Override
	public ResourceLocation getEntityTexture(EntitySeamoth entity) {
		return TEXTURE;
	}

	protected double interpolateRotation(double prevRotation, double rotation, double partialTicks) {
		return prevRotation + MathHelper.wrapDegrees(rotation - prevRotation) * partialTicks;
	}

}
