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

public class ScreenSeamoth<C extends ContainerSeamoth> extends ContainerScreen<C> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/seamoth_container.png");

	private final EntitySeamoth seamoth = new EntitySeamoth(BetterDivingEntities.SEAMOTH.get(), Minecraft.getInstance().world);

	public ScreenSeamoth(C screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);
		ItemStack powerCell = this.container.getSlot(36).getStack();
		if (powerCell.getItem() instanceof ItemPowerCell) {
			IEnergyStorage ienergy = powerCell.getCapability(CapabilityEnergy.ENERGY).orElseThrow(NullPointerException::new);
			int energy = MathHelper.ceil((double) ienergy.getEnergyStored() / (double) ienergy.getMaxEnergyStored() * 100.0D);
			this.font.drawString(matrixStack, "Energy: " + energy + "%", 8, 16, 4210752);
		} else {
			this.font.drawString(matrixStack, "No power cell", 8, 16, 4210752);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		this.blit(matrixStack, (this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
		this.drawEntity(this.seamoth, (this.width - this.xSize) / 2 + 118, (this.height - this.ySize) / 2 + 60, 16, x, y);
	}

	private void drawEntity(Entity entity, int x, int y, int scale, float mouseX, float mouseY) {
		drawEntityOnScreen(x, y, scale, x - mouseX, y - entity.getEyeHeight() * scale - mouseY, entity);
	}

	/**
	 * Copied from {@link InventoryScreen#drawEntityOnScreen(int, int, int, float, float, LivingEntity)}
	 */
	@SuppressWarnings("deprecation")
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
		quaternion.multiply(quaternion1);
		matrixstack.rotate(quaternion);
		float f3 = entity.rotationYaw;
		float f4 = entity.rotationPitch;
		entity.rotationYaw = 180.0F + f * 40.0F;
		entity.rotationPitch = -f1 * 20.0F;
		EntityRendererManager entityrenderermanager = Minecraft.getInstance().getRenderManager();
		quaternion1.conjugate();
		entityrenderermanager.setCameraOrientation(quaternion1);
		entityrenderermanager.setRenderShadow(false);
		IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
		RenderSystem.runAsFancy(() -> {
			entityrenderermanager.renderEntityStatic(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);
		});
		irendertypebuffer$impl.finish();
		entityrenderermanager.setRenderShadow(true);
		entity.rotationYaw = f3;
		entity.rotationPitch = f4;
		RenderSystem.popMatrix();
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

}
