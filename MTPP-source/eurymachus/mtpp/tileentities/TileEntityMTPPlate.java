package eurymachus.mtpp.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import eurymachus.mtpp.network.packets.PacketUpdateMTPPlate;
import eurysmods.network.packets.core.PacketUpdate;
import eurysmods.tileentities.TileEntityMT;

public class TileEntityMTPPlate extends TileEntityMT {
	public int triggerType;

	public int getTriggerType() {
		return this.triggerType;
	}

	public void setTriggerType(int trigger) {
		this.triggerType = trigger;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("triggerType", this.getTriggerType());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.setTriggerType(nbttagcompound.getInteger("triggerType"));
	}

	@Override
	public Packet getUpdatePacket() {
		return new PacketUpdateMTPPlate(this).getPacket();
	}

	@Override
	public void handleUpdatePacket(World world, PacketUpdate packet) {
		this.setTriggerType(((PacketUpdateMTPPlate) packet).getTriggerType());
		super.handleUpdatePacket(world, packet);
	}
}
