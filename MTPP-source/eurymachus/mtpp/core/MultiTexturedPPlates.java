package eurymachus.mtpp.core;

import slimevoid.lib.ICommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import eurymachus.mtpp.network.MTPPConnection;

@Mod(
		modid = "MultiTexturedPPlates",
		name = "Multi-Textured Pressure Plates",
		dependencies = "after:SlimevoidLib",
		version = "2.0.0.0")
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false,
		channels = { "MTPP" },
		packetHandler = MTPPConnection.class,
		connectionHandler = MTPPConnection.class)
public class MultiTexturedPPlates {
	@SidedProxy(
			clientSide = "eurymachus.mtpp.client.proxy.ClientProxy",
			serverSide = "eurymachus.mtpp.proxy.CommonProxy")
	public static ICommonProxy proxy;

	@PreInit
	public void MultiTexturedPPlatePreInit(FMLPreInitializationEvent event) {
	}

	@Init
	public void MultiTexturedPPlateInit(FMLInitializationEvent event) {
	}

	@PostInit
	public void MultiTexturedPPlatePostInit(FMLPostInitializationEvent event) {
		MTPPCore.initialize(proxy);
	}
}
