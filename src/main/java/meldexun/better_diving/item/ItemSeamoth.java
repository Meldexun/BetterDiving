package meldexun.better_diving.item;

import java.util.List;

import meldexun.better_diving.capability.inventory.CapabilityItemHandlerProvider;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.init.BetterDivingEntities;
import meldexun.better_diving.init.BetterDivingItemGroups;
import meldexun.better_diving.init.BetterDivingItems;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemSeamoth extends Item {

	public ItemSeamoth() {
		super(new Item.Properties().maxStackSize(1).group(BetterDivingItemGroups.BETTER_DIVING));
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		return new CapabilityItemHandlerProvider(() -> new ItemStackHandler(1));
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			items.add(new ItemStack(this));
			ItemStack stack = new ItemStack(this);
			stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(c -> {
				((ItemStackHandler) c).setStackInSlot(0, new ItemStack(BetterDivingItems.POWER_CELL.get()));
			});
			items.add(stack);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!worldIn.isRemote) {
			if (playerIn.isSneaking()) {
				// TODO open gui
			} else {
				EntitySeamoth seamoth = new EntitySeamoth(BetterDivingEntities.SEAMOTH.get(), worldIn);

				playerIn.getHeldItem(handIn).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(c -> {
					seamoth.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(c1 -> {
						c1.insertItem(0, c.getStackInSlot(0).copy(), false);
					});
				});

				Vector3d start = playerIn.getEyePosition(1.0F);
				Vector3d look = playerIn.getLookVec();
				Vector3d end = start.add(look.scale(5.0D));
				RayTraceResult result = worldIn.rayTraceBlocks(new RayTraceContext(start, end, BlockMode.COLLIDER, FluidMode.NONE, null));
				Vector3d vec = (result != null ? result.getHitVec() : end).subtract(look);

				seamoth.setPosition(vec.x, vec.y, vec.z);
				worldIn.addEntity(seamoth);

				if (worldIn.getBlockState(seamoth.getPosition()).getMaterial() == Material.WATER) {
					seamoth.playSound(SoundEvents.AMBIENT_UNDERWATER_ENTER, 1.0F, 0.9F + worldIn.rand.nextFloat() * 0.2F);
				}

				if (!playerIn.isCreative()) {
					playerIn.getHeldItem(handIn).shrink(1);
				}
			}
		}
		return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		ItemStack powerCell = ItemStack.EMPTY;
		LazyOptional<IItemHandler> cap = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if (cap.isPresent()) {
			powerCell = cap.orElseThrow(NullPointerException::new).getStackInSlot(0);
		}
		if (!powerCell.isEmpty() && powerCell.getItem() instanceof ItemPowerCell) {
			int energy = MathHelper.ceil(ItemEnergyStorage.getEnergyPercent(powerCell) * 100.0D);
			if (flagIn.isAdvanced()) {
				tooltip.add(new StringTextComponent(TextFormatting.GRAY + String.format("Energy %d%% (%d/%d)", energy, ItemEnergyStorage.getEnergy(powerCell), ItemEnergyStorage.getEnergyCapacity(powerCell))));
			} else {
				tooltip.add(new StringTextComponent(TextFormatting.GRAY + String.format("Energy %d%%", energy)));
			}
		} else {
			tooltip.add(new StringTextComponent(TextFormatting.GRAY + "No power cell"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		super.onCreated(stack, worldIn, playerIn);
		stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(c -> {
			((ItemStackHandler) c).setStackInSlot(0, new ItemStack(BetterDivingItems.POWER_CELL.get()));
		});
	}

}
