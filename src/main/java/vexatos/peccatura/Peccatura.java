package vexatos.peccatura;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.avaritia.blocks.LudicrousBlocks;
import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;
import vexatos.peccatura.block.BlockAutomaTable;
import vexatos.peccatura.gui.GuiHandler;
import vexatos.peccatura.item.ItemBlockAutomaTable;
import vexatos.peccatura.reference.Mods;
import vexatos.peccatura.tile.TileAutomaTable;

@Mod(modid = Mods.Peccatura, name = Mods.Peccatura_Name, version = "@VERSION@", dependencies = "required-after:Avaritia")
public class Peccatura {

	@Instance
	public static Peccatura instance;

	public static Logger log;

	public static BlockAutomaTable automaTable;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		log = e.getModLog();
		MinecraftForge.EVENT_BUS.register(this);
		//FMLCommonHandler.instance().bus().register(this);

		automaTable = new BlockAutomaTable();
		GameRegistry.registerBlock(automaTable, ItemBlockAutomaTable.class, "peccatura.AutomaTable");
		GameRegistry.registerTileEntity(TileAutomaTable.class, "peccatura.AutomaTable");
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
	}
}
