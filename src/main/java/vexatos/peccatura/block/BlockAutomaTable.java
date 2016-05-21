package vexatos.peccatura.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.blocks.BlockDireCrafting;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import vexatos.peccatura.Peccatura;
import vexatos.peccatura.tile.TileAutomaTable;

/**
 * @author Vexatos
 */
public class BlockAutomaTable extends BlockDireCrafting {

	protected IIcon top;
	protected IIcon sides;
	protected IIcon bottom;

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

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		top = iconRegister.registerIcon("peccatura:direst_crafting_top");
		sides = iconRegister.registerIcon("peccatura:direst_crafting_side");
		bottom = iconRegister.registerIcon("peccatura:direst_crafting_bottom");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		//return super.getIcon(side, metadata);
		return side == 0 ? bottom : side == 1 ? top : sides;
	}

}
