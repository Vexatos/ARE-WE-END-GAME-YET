package vexatos.peccatura;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;
import vexatos.peccatura.reference.Mods;

@Mod(modid = Mods.Peccatura, name = Mods.Peccatura_Name, version = "@VERSION@", dependencies = "required-after:Avaritia")
public class Peccatura {

	@Instance
	public static Peccatura instance;

	public static Logger log;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		log = e.getModLog();
		MinecraftForge.EVENT_BUS.register(this);
		//FMLCommonHandler.instance().bus().register(this);

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {

	}

	@EventHandler
	public void onServerStart(FMLServerAboutToStartEvent e) {

	}

	@EventHandler
	public void onServerStarting(FMLServerStartingEvent e) {

	}

	@EventHandler
	public void onServerStopping(FMLServerStoppingEvent e) {

	}
}
