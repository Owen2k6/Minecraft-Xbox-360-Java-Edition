package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import net.minecraft.server.MinecraftServer;

public class ThreadCommandReader extends Thread {
	final MinecraftServer mcServer;

	public ThreadCommandReader(MinecraftServer var1) {
		this.mcServer = var1;
	}

	public void run() {
		BufferedReader var1 = new BufferedReader(new InputStreamReader(System.in));
		String var2 = null;

		try {
			while(!this.mcServer.serverStopped && MinecraftServer.isServerRunning(this.mcServer)) {
				var2 = var1.readLine();
				if(var2 == null) {
					break;
				}

				this.mcServer.addCommand(var2, this.mcServer);
			}
		} catch (IOException var4) {
			var4.printStackTrace();
		}

	}
}
