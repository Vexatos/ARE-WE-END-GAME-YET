package vexatos.peccatura.block;

import fox.spiteful.avaritia.blocks.BlockDireCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vexatos.peccatura.Peccatura;
import vexatos.peccatura.tile.TileAutomaTable;

/**
 * @author Vexatos
 */
public class BlockAutomaTable extends BlockDireCrafting {

	public BlockAutomaTable() {
		super();
		this.setBlockName("peccatura.direst_crafting");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(world.isRemote) {
			return true;
		} else {
			player.openGui(Peccatura.instance, 0, world, x, y, z);
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileAutomaTable();
	}
}
