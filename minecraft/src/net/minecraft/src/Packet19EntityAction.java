package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet19EntityAction extends Packet {
	public int entityId;
	public int state;

	public Packet19EntityAction() {
	}

	public Packet19EntityAction(Entity var1, int var2) {
		this.entityId = var1.entityId;
		this.state = var2;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.entityId = var1.readInt();
		this.state = var1.readByte();
	}

	public void writePacketData(DataOutputStream var1) throws IOException {
		var1.writeInt(this.entityId);
		var1.writeByte(this.state);
	}

	public void processPacket(NetHandler var1) {
		var1.func_21147_a(this);
	}

	public int getPacketSize() {
		return 5;
	}
}
