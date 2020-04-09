package meldexun.better_diving.client.gui;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.item.crafting.Recipe;
import meldexun.better_diving.network.packet.CPacketCraftRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiRecipeButton extends GuiButton {

	private final Recipe recipe;

	public GuiRecipeButton(int buttonId, int x, int y, Recipe recipe) {
		super(buttonId, x, y, 80, 20, recipe.getOutput().getDisplayName());
		this.recipe = recipe;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.enabled = this.recipe.canCraft(mc.player);
		super.drawButton(mc, mouseX, mouseY, partialTicks);
	}

	public void actionPerformed(GuiFabricator guiFabricator) {
		BetterDiving.network.sendToServer(new CPacketCraftRecipe(this.recipe));
	}

}
