package eurymachus.mtpp.network.packets;

import slimevoid.lib.network.PacketPayload;
import slimevoid.lib.network.PacketTileEntityMT;
import eurymachus.mtpp.core.MTPPInit;
import eurymachus.mtpp.tileentities.TileEntityMTPPlate;

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
