package meldexun.better_diving.client.renderer.entity;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.AbstractEntityFish;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFish<T extends AbstractEntityFish> extends Render<T> {

	protected final ResourceLocation texture;
	protected final ModelBase model;

	public RenderFish(RenderManager renderManager, ModelBase model, float shadowSize, String entityName) {
		super(renderManager);
		this.model = model;
		this.shadowSize = shadowSize;
		this.texture = new ResourceLocation(BetterDiving.MOD_ID, "textures/entity/" + entityName + ".png");
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		float f1 = this.interpolateRotation(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
		float f2 = this.interpolateRotation(entity.prevRotationPitch, entity.rotationPitch, partialTicks);

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y + (0.5D * entity.height), z);
		GlStateManager.rotate(180.0F - f1, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(f2, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(-0.3D, -0.3D, 0.3D);
		this.bindEntityTexture(entity);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		this.model.render(entity, 0.0F, 0.0F, entity.ticksExisted, f1, f2, 0.0625F);

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return this.texture;
	}

	protected float interpolateRotation(float prevRotation, float rotation, float partialTicks) {
		return prevRotation + MathHelper.wrapDegrees(rotation - prevRotation) * partialTicks;
	}

}