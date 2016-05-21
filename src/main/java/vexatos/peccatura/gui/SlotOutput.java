package vexatos.peccatura.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Vexatos
 */
public class SlotOutput extends Slot {

	public SlotOutput(IInventory iinventory, int slotIndex, int posX, int posY) {
		super(iinventory, slotIndex, posX, posY);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return false;
	}
}
