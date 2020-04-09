package meldexun.better_diving.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.util.GuiHelper;
import meldexun.better_diving.item.crafting.Recipe;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFabricator extends GuiContainer {

	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/seamoth_fabricator.png");

	public GuiFabricator(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		this.xSize = 256;
	}

	@Override
	public void initGui() {
		super.initGui();
		ScaledResolution scaled = new ScaledResolution(this.mc);
		int x = (int) ((double) scaled.getScaledWidth() * 0.5D);
		int y = (int) ((double) scaled.getScaledHeight() * 0.5D);

		List<Recipe> recipes = new ArrayList<>(Recipe.REGISTRY.getValuesCollection());
		for (int i = 0; i < recipes.size() && i < 6; i++) {
			this.addButton(new GuiRecipeButton(i, x + 43, y - 54 + i * 22, recipes.get(i)));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiFabricator.TEXTURE);
		this.drawTexturedModalRect((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
		GuiHelper.drawEntity(this.mc.player, (this.width - this.xSize) / 2 + 51, (this.height - this.ySize) / 2 + 75, 30, mouseX, mouseY);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button instanceof GuiRecipeButton) {
			((GuiRecipeButton) button).actionPerformed(this);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
