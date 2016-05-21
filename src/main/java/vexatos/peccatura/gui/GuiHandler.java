package vexatos.peccatura.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import fox.spiteful.avaritia.tile.TileEntityDireCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author Vexatos
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 0) {
			return new GUIAutomaTable(player.inventory, world, x, y, z, (TileEntityDireCrafting) world.getTileEntity(x, y, z));
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 0) {
			return new ContainerAutomaTable(player.inventory, world, x, y, z, (TileEntityDireCrafting) world.getTileEntity(x, y, z));
		}
		return null;
	}
}
