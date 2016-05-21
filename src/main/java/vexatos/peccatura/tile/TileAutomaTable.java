package vexatos.peccatura.tile;

import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import fox.spiteful.avaritia.tile.TileEntityDireCrafting;
import fox.spiteful.avaritia.tile.inventory.InventoryDireCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author SpitefulFox, Vexatos
 */
public class TileAutomaTable extends TileEntityDireCrafting {

	protected ItemStack result;
	protected ItemStack match;
	protected final ItemStack[] matrix = new ItemStack[81];
	protected InventoryCrafting crafter = makeCraftingGrid();
	private boolean isDirty = true;

	protected InventoryCrafting makeCraftingGrid() {
		return new InventoryDireCrafting(new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer entityplayer) {
				return false;
			}

			@Override
			public void onCraftMatrixChanged(IInventory iinventory) {
			}
		}, this);
	}

	public TileAutomaTable() {
	}

	@Override
	public boolean canUpdate() {
		return true;
	}

	@Override
	public void updateEntity() {
		if(worldObj.isRemote) {
			return;
		}
		if(this.isDirty) {
			this.match = ExtremeCraftingManager.getInstance().findMatchingRecipe(this.crafter, this.worldObj);
			this.isDirty = false;
		}
		if(match != null && couldMerge(match, result)) {
			for(ItemStack stack : matrix) {
				if(stack != null && stack.stackSize < 2) {
					return;
				}
			}
			boolean process = false;
			if(result == null) {
				result = match.copy();
				process = true;
			} else if(result.stackSize + match.stackSize <= result.getMaxStackSize()) {
				result.stackSize += match.stackSize;
				process = true;
			}
			if(process) {
				for(int x = 1; x <= matrix.length; x++) {
					decrStackSize(x, 1);
				}
			}
		}
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		this.result = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Result"));
		for(int x = 0; x < matrix.length; x++) {
			matrix[x] = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Craft" + x));
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		if(result != null) {
			NBTTagCompound produce = new NBTTagCompound();
			result.writeToNBT(produce);
			tag.setTag("Result", produce);
		} else {
			tag.removeTag("Result");
		}

		for(int x = 0; x < matrix.length; x++) {
			if(matrix[x] != null) {
				NBTTagCompound craft = new NBTTagCompound();
				matrix[x].writeToNBT(craft);
				tag.setTag("Craft" + x, craft);
			} else {
				tag.removeTag("Craft" + x);
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return 82;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slot == 0 ? this.result : (slot <= this.matrix.length ? this.matrix[slot - 1] : null);
	}

	@Override
	public ItemStack decrStackSize(int slot, int decrement) {
		if(slot == 0) {
			if(result != null) {
				if(result.stackSize <= decrement) {
					ItemStack craft = result;
					result = null;
					return craft;
				}
				ItemStack split = result.splitStack(decrement);
				if(result.stackSize <= 0) {
					result = null;
				}
				return split;
			} else {
				return null;
			}
		} else if(slot <= matrix.length) {
			if(matrix[slot - 1] != null) {
				if(matrix[slot - 1].stackSize <= decrement) {
					ItemStack ingredient = matrix[slot - 1].copy();
					ingredient.stackSize -= 1;
					matrix[slot - 1].stackSize = 1;
					return ingredient.stackSize > 0 ? ingredient : null;
				}
				ItemStack split = matrix[slot - 1].splitStack(decrement);
				if(matrix[slot - 1].stackSize <= 0) {
					matrix[slot - 1] = null;
				}
				return split;
			}
		}
		return null;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack != null && slot > 0 && slot <= matrix.length && matrix[slot - 1] != null
			&& couldMerge(stack, matrix[slot - 1]);
	}

	// Courtesy of neptunepink
	public static boolean couldMerge(ItemStack a, ItemStack b) {
		return a == null || b == null || a.getItem() == b.getItem() && a.getItemDamage() == b.getItemDamage() && sameItemTags(a, b);
	}

	public static boolean sameItemTags(ItemStack a, ItemStack b) {
		if(a.stackTagCompound == null || b.stackTagCompound == null) {
			return a.stackTagCompound == b.stackTagCompound;
		}
		return a.stackTagCompound.equals(b.stackTagCompound);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if(slot == 0) {
			this.result = stack;
		} else if(slot <= this.matrix.length) {
			if(stack == null || matrix[slot - 1] == null || !couldMerge(matrix[slot - 1], stack)) {
				isDirty = true;
			}
			this.matrix[slot - 1] = stack;
		}
	}

	protected static final int[] INPUT_SLOTS;

	static {
		INPUT_SLOTS = new int[81];
		for(int i = 0; i < INPUT_SLOTS.length; i++) {
			INPUT_SLOTS[i] = i + 1;
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		switch(ForgeDirection.getOrientation(side)) {
			case NORTH:
			case SOUTH:
			case WEST:
			case EAST:
				return INPUT_SLOTS;
			case DOWN:
				return new int[] { 0 };
			default:
				return new int[0];
		}
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		switch(ForgeDirection.getOrientation(side)) {
			case NORTH:
			case SOUTH:
			case WEST:
			case EAST:
				return slot != 0 && isItemValidForSlot(slot, stack);
			default:
				return false;
		}
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		switch(ForgeDirection.getOrientation(side)) {
			case DOWN:
				return slot == 0;
			default:
				return false;
		}
	}
}
