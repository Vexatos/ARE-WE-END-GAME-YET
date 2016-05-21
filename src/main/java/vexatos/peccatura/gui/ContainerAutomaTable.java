package vexatos.peccatura.gui;

import fox.spiteful.avaritia.tile.TileEntityDireCrafting;
import fox.spiteful.avaritia.tile.inventory.InventoryDireCraftResult;
import fox.spiteful.avaritia.tile.inventory.InventoryDireCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vexatos.peccatura.Peccatura;

/**
 * @author SpitefulFox, Vexatos
 */
public class ContainerAutomaTable extends Container {

	/** The crafting matrix inventory (9x9). */
	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;

	public ContainerAutomaTable(InventoryPlayer player, World world, int x, int y, int z, TileEntityDireCrafting table) {
		this.worldObj = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		craftMatrix = new InventoryDireCrafting(this, table);
		craftResult = new InventoryDireCraftResult(table);
		this.addSlotToContainer(new SlotOutput(this.craftResult, 0, 210, 80));
		int wy;
		int ex;

		for(wy = 0; wy < 9; ++wy) {
			for(ex = 0; ex < 9; ++ex) {
				this.addSlotToContainer(new Slot(this.craftMatrix, ex + wy * 9, 12 + ex * 18, 8 + wy * 18));
			}
		}

		for(wy = 0; wy < 3; ++wy) {
			for(ex = 0; ex < 9; ++ex) {
				this.addSlotToContainer(new Slot(player, ex + wy * 9 + 9, 39 + ex * 18, 174 + wy * 18));
			}
		}

		for(ex = 0; ex < 9; ++ex) {
			this.addSlotToContainer(new Slot(player, ex, 39 + ex * 18, 232));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(IInventory matrix) {
		super.onCraftMatrixChanged(matrix);
	}

	/**
	 * Called when the container is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.worldObj.getBlock(this.posX, this.posY, this.posZ) == Peccatura.automaTable && player.getDistanceSq((double) this.posX + 0.5D, (double) this.posY + 0.5D, (double) this.posZ + 0.5D) <= 64.0D;
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotNumber);

		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(slotNumber == 0) {
				if(!this.mergeItemStack(itemstack1, 82, 118, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if(slotNumber >= 82 && slotNumber < 109) {
				if(!this.mergeItemStack(itemstack1, 109, 118, false)) {
					return null;
				}
			} else if(slotNumber >= 109 && slotNumber < 118) {
				if(!this.mergeItemStack(itemstack1, 82, 109, false)) {
					return null;
				}
			} else if(!this.mergeItemStack(itemstack1, 82, 118, false)) {
				return null;
			}

			if(itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			if(itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}
}
