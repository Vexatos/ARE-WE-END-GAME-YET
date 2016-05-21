package vexatos.peccatura.reference;

import cpw.mods.fml.common.Loader;

/**
 * @author Vexatos
 */
public class Mods {

	//The mod itself
	public static final String
		Peccatura = "estis peccatura",
		Peccatura_Name = "ARE WE END GAME YET?!?";

	public static boolean isLoaded(String name) {
		return Loader.isModLoaded(name);
	}
}
