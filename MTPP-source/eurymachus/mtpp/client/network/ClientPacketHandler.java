package eurymachus.mtpp.client.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import eurymachus.mtpp.tileentities.TileEntityMTPPlate;
import eurysmods.api.IPacketHandling;
import eurysmods.network.packets.core.PacketTileEntity;
import eurysmods.network.packets.core.PacketUpdate;

public class ClientPacketHandler implements IPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketTileEntity packet, EntityPlayer entityplayer, World world) {
		if (packet != null && packet.targetExists(world)) {
			TileEntity tileentity = packet.getTileEntity(world);
			if ((tileentity != null) && (tileentity instanceof TileEntityMTPPlate)) {
				TileEntityMTPPlate tileentitymtpplate = (TileEntityMTPPlate) tileentity;
				tileentitymtpplate.handleUpdatePacket(world, packet);
			}
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer, World world) {
	}

	@Override
	public void handlePacket(PacketUpdate packet, EntityPlayer entityplayer, World world) {
	}
}