package mtpp.network.packets;

import mtpp.core.MTPPInit;
import mtpp.tileentities.TileEntityMTPPlate;
import eurysmods.network.packets.core.PacketPayload;
import eurysmods.network.packets.core.PacketTileEntityMT;

public class PacketUpdateMTPPlate extends PacketTileEntityMT {
	public PacketUpdateMTPPlate() {
		super(MTPPInit.MTPP.getModChannel());
	}

	public PacketUpdateMTPPlate(TileEntityMTPPlate tileentitymtpplate) {
		super(MTPPInit.MTPP.getModChannel(), tileentitymtpplate);
		this.payload = new PacketPayload(1, 0, 0, 0);
		this.setTriggerType(tileentitymtpplate.getTriggerType());
	}

	public void setTriggerType(int triggerType) {
		this.payload.setIntPayload(0, triggerType);
	}

	public int getTriggerType() {
		return this.payload.getIntPayload(0);
	}
}
