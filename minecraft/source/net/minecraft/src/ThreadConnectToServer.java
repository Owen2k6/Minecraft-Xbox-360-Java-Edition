package net.minecraft.src;

import java.net.ConnectException;
import java.net.UnknownHostException;
import net.minecraft.client.Minecraft;

class ThreadConnectToServer extends Thread {
	final Minecraft mc;
	final String hostName;
	final int port;
	final GuiConnecting connectingGui;

	ThreadConnectToServer(GuiConnecting var1, Minecraft var2, String var3, int var4) {
		this.connectingGui = var1;
		this.mc = var2;
		this.hostName = var3;
		this.port = var4;
	}

	public void run() {
		try {
			GuiConnecting.setNetClientHandler(this.connectingGui, new NetClientHandler(this.mc, this.hostName, this.port));
			if(GuiConnecting.isCancelled(this.connectingGui)) {
				return;
			}

			GuiConnecting.getNetClientHandler(this.connectingGui).addToSendQueue(new Packet2Handshake(this.mc.session.username));
		} catch (UnknownHostException var2) {
			if(GuiConnecting.isCancelled(this.connectingGui)) {
				return;
			}

			this.mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.genericReason", new Object[]{"Unknown host \'" + this.hostName + "\'"}));
		} catch (ConnectException var3) {
			if(GuiConnecting.isCancelled(this.connectingGui)) {
				return;
			}

			this.mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.genericReason", new Object[]{var3.getMessage()}));
		} catch (Exception var4) {
			if(GuiConnecting.isCancelled(this.connectingGui)) {
				return;
			}

			var4.printStackTrace();
			this.mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.genericReason", new Object[]{var4.toString()}));
		}

	}
}
