package mtpp.core;

import mtpp.network.MTPPConnection;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import eurysmods.api.ICommonProxy;

@Mod(
		modid = "MultiTexturedPPlates",
		name = "Multi-Textured Pressure Plates",
		dependencies = "after:EurysCore",
		version = "2.0.0.0")
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false,
		channels = { "MTPP" },
		packetHandler = MTPPConnection.class,
		connectionHandler = MTPPConnection.class)
public class MultiTexturedPPlates {
	@SidedProxy(
			clientSide = "mtpp.proxy.ClientProxy",
			serverSide = "mtpp.proxy.CommonProxy")
	public static ICommonProxy proxy;

	@Init
	public void MultiTexturedPPlateInit(FMLInitializationEvent event) {
	}

	@PreInit
	public void MultiTexturedPPlatePreInit(FMLPreInitializationEvent event) {

	}

	@PostInit
	public void MultiTexturedPPlatePostInit(FMLPostInitializationEvent event) {
		MTPPCore.initialize(proxy);
	}
}
