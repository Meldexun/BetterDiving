package meldexun.better_diving.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.init.BetterDivingEntities;
import meldexun.better_diving.inventory.container.ContainerSeamoth;
import meldexun.better_diving.item.ItemPowerCell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ScreenSeamoth extends ContainerScreen<ContainerSeamoth> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/seamoth_container.png");

	private final EntitySeamoth seamoth = new EntitySeamoth(BetterDivingEntities.SEAMOTH.get(), Minecraft.getInstance().level);

	public ScreenSeamoth(ContainerSeamoth screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		super.renderLabels(matrixStack, x, y);
		ItemStack powerCell = this.menu.getSlot(36).getItem();
		if (powerCell.getItem() instanceof ItemPowerCell) {
			IEnergyStorage ienergy = powerCell.getCapability(CapabilityEnergy.ENERGY).orElseThrow(NullPointerException::new);
			int energy = MathHelper.ceil((double) ienergy.getEnergyStored() / (double) ienergy.getMaxEnergyStored() * 100.0D);
			this.font.draw(matrixStack, "Energy: " + energy + "%", 8, 16, 4210752);
		} else {
			this.font.draw(matrixStack, "No power cell", 8, 16, 4210752);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
		GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(TEXTURE);
		this.blit(matrixStack, (this.width - this.imageWidth) / 2, (this.height - this.imageHeight) / 2, 0, 0, this.imageWidth, this.imageHeight);
		this.drawEntity(this.seamoth, (this.width - this.imageWidth) / 2 + 118, (this.height - this.imageHeight) / 2 + 60, 16, x, y);
	}

	private void drawEntity(Entity entity, int x, int y, int scale, float mouseX, float mouseY) {
		drawEntityOnScreen(x, y, scale, x - mouseX, y - entity.getEyeHeight() * scale - mouseY, entity);
	}

	/**
	 * Copied from {@link InventoryScreen#drawEntityOnScreen(int, int, int, float, float, LivingEntity)}
	 */
	public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, Entity entity) {
		float f = (float) Math.atan((double) (mouseX / 40.0F));
		float f1 = (float) Math.atan((double) (mouseY / 40.0F));
		RenderSystem.pushMatrix();
		RenderSystem.translatef((float) posX, (float) posY, 1050.0F);
		RenderSystem.scalef(1.0F, 1.0F, -1.0F);
		MatrixStack matrixstack = new MatrixStack();
		matrixstack.translate(0.0D, 0.0D, 1000.0D);
		matrixstack.scale((float) scale, (float) scale, (float) scale);
		Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
		Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
		quaternion.mul(quaternion1);
		matrixstack.mulPose(quaternion);
		float f3 = entity.yRot;
		float f4 = entity.xRot;
		entity.yRot = 180.0F + f * 40.0F;
		entity.xRot = -f1 * 20.0F;
		EntityRendererManager entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
		quaternion1.conj();
		entityrenderermanager.overrideCameraOrientation(quaternion1);
		entityrenderermanager.setRenderShadow(false);
		IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
		RenderSystem.runAsFancy(() -> {
			entityrenderermanager.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);
		});
		irendertypebuffer$impl.endBatch();
		entityrenderermanager.setRenderShadow(true);
		entity.yRot = f3;
		entity.xRot = f4;
		RenderSystem.popMatrix();
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

}
