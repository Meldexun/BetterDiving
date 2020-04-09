package meldexun.better_diving.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.model.entity.ModelSeamoth;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSeamoth extends Render<EntitySeamoth> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterDiving.MOD_ID, "textures/entity/seamoth.png");

	protected ModelBase model = new ModelSeamoth(0.0F);

	public RenderSeamoth(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
	}

	@Override
	public void doRender(EntitySeamoth entity, double x, double y, double z, float entityYaw, float partialTicks) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		if (player == entity.getControllingPassenger() && mc.gameSettings.thirdPersonView == 0) {
			GL11.glTranslated(0.0D, (double) player.eyeHeight, 0.0D);
			this.setupRotation(entity, partialTicks);
			GL11.glTranslated(0.0D, 0.8125D - (double) player.eyeHeight, -0.32D);
		} else {
			GL11.glTranslated(0.0D, 0.8125D, 0.0D);
			this.setupRotation(entity, partialTicks);
		}

		GL11.glScaled(-1.0D, -1.0D, 1.0D);

		this.bindEntityTexture(entity);
		this.model.render(entity, partialTicks, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	protected void setupRotation(Entity entity, float partialTicks) {
		double yaw = this.interpolateRotation((double) entity.prevRotationYaw, (double) entity.rotationYaw, (double) partialTicks);
		double pitch = this.interpolateRotation((double) entity.prevRotationPitch, (double) entity.rotationPitch, (double) partialTicks);
		// interpolated yaw and pitch does NOT work
		GL11.glRotated(entity.rotationPitch, Math.cos(Math.toRadians(entity.rotationYaw)), 0.0D, Math.sin(Math.toRadians(entity.rotationYaw)));
		GL11.glRotated(180.0D - entity.rotationYaw, 0.0D, 1.0D, 0.0D);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySeamoth entity) {
		return RenderSeamoth.TEXTURE;
	}

	protected double interpolateRotation(double prevRotation, double rotation, double partialTicks) {
		return prevRotation + MathHelper.wrapDegrees(rotation - prevRotation) * partialTicks;
	}

}
