package meldexun.better_diving.client.renderer.entity;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.model.entity.ModelSeamoth;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSeamoth extends Render<EntitySeamoth> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterDiving.MOD_ID, "textures/entity/seamoth.png");

	protected ModelBase model = new ModelSeamoth(0.0F);
	protected ModelBase modelFirstPerson = new ModelSeamoth(7.32F);

	public RenderSeamoth(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
	}

	@Override
	public void doRender(EntitySeamoth entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		if (entity.getControllingPassenger() == Minecraft.getMinecraft().player && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
			this.setupTranslation(x, y + 0.8125F + 0.4575F, z);
		} else {
			this.setupTranslation(x, y + 0.8125F, z);
		}
		this.setupRotation(entity, entityYaw, partialTicks);
		this.bindEntityTexture(entity);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		if (entity.getControllingPassenger() == Minecraft.getMinecraft().player && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
			this.modelFirstPerson.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		} else {
			this.model.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	public void setupTranslation(double x, double y, double z) {
		GlStateManager.translate((float) x, (float) y, (float) z);
	}

	public void setupRotation(EntitySeamoth entity, float entityYaw, float partialTicks) {
		if (entity.getControllingPassenger() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) entity.getControllingPassenger();
			GlStateManager.rotate(p.rotationPitch, MathHelper.cos(p.rotationYaw * 0.017453292F), 0.0F, MathHelper.sin(p.rotationYaw * 0.017453292F));
			GlStateManager.rotate(180.0F - p.rotationYaw, 0.0F, 1.0F, 0.0F);
		} else {
			GlStateManager.rotate(entity.rotationPitch, MathHelper.cos(entity.rotationYaw * 0.017453292F), 0.0F, MathHelper.sin(entity.rotationYaw * 0.017453292F));
			GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
		}

		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySeamoth entity) {
		return TEXTURE;
	}

	protected float interpolateRotation(float prevRotation, float rotation, float partialTicks) {
		return prevRotation + MathHelper.wrapDegrees(rotation - prevRotation) * partialTicks;
	}

}