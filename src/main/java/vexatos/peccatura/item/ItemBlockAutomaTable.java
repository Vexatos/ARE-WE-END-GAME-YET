package vexatos.peccatura.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import vexatos.peccatura.util.TooltipUtil;

import java.util.List;

/**
 * @author Vexatos
 */
public class ItemBlockAutomaTable extends ItemBlock {

	public ItemBlockAutomaTable(Block block) {
		super(block);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean wat) {
		TooltipUtil.addShiftTooltip(stack, list);
	}
}
