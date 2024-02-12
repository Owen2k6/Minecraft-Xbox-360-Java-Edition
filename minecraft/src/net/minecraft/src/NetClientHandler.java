package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;

public class NetClientHandler extends NetHandler {
	private boolean disconnected = false;
	private NetworkManager netManager;
	public String field_1209_a;
	private Minecraft mc;
	private WorldClient worldClient;
	private boolean field_1210_g = false;
	public MapStorage field_28118_b = new MapStorage((ISaveHandler)null);
	Random rand = new Random();

	public NetClientHandler(Minecraft var1, String var2, int var3) throws IOException, UnknownHostException {
		this.mc = var1;
		Socket var4 = new Socket(InetAddress.getByName(var2), var3);
		this.netManager = new NetworkManager(var4, "Client", this);
	}

	public void processReadPackets() {
		if(!this.disconnected) {
			this.netManager.processReadPackets();
		}

		this.netManager.wakeThreads();
	}

	public void handleLogin(Packet1Login var1) {
		this.mc.playerController = new PlayerControllerMP(this.mc, this);
		this.mc.statFileWriter.func_25100_a(StatList.joinMultiplayerStat, 1);
		this.worldClient = new WorldClient(this, var1.mapSeed, var1.dimension);
		this.worldClient.multiplayerWorld = true;
		this.mc.changeWorld1(this.worldClient);
		this.mc.thePlayer.dimension = var1.dimension;
		this.mc.displayGuiScreen(new GuiDownloadTerrain(this));
		this.mc.thePlayer.entityId = var1.protocolVersion;
	}

	public void handlePickupSpawn(Packet21PickupSpawn var1) {
		double var2 = (double)var1.xPosition / 32.0D;
		double var4 = (double)var1.yPosition / 32.0D;
		double var6 = (double)var1.zPosition / 32.0D;
		EntityItem var8 = new EntityItem(this.worldClient, var2, var4, var6, new ItemStack(var1.itemID, var1.count, var1.itemDamage));
		var8.motionX = (double)var1.rotation / 128.0D;
		var8.motionY = (double)var1.pitch / 128.0D;
		var8.motionZ = (double)var1.roll / 128.0D;
		var8.serverPosX = var1.xPosition;
		var8.serverPosY = var1.yPosition;
		var8.serverPosZ = var1.zPosition;
		this.worldClient.func_712_a(var1.entityId, var8);
	}

	public void handleVehicleSpawn(Packet23VehicleSpawn var1) {
		double var2 = (double)var1.xPosition / 32.0D;
		double var4 = (double)var1.yPosition / 32.0D;
		double var6 = (double)var1.zPosition / 32.0D;
		Object var8 = null;
		if(var1.type == 10) {
			var8 = new EntityMinecart(this.worldClient, var2, var4, var6, 0);
		}

		if(var1.type == 11) {
			var8 = new EntityMinecart(this.worldClient, var2, var4, var6, 1);
		}

		if(var1.type == 12) {
			var8 = new EntityMinecart(this.worldClient, var2, var4, var6, 2);
		}

		if(var1.type == 90) {
			var8 = new EntityFish(this.worldClient, var2, var4, var6);
		}

		if(var1.type == 60) {
			var8 = new EntityArrow(this.worldClient, var2, var4, var6);
		}

		if(var1.type == 61) {
			var8 = new EntitySnowball(this.worldClient, var2, var4, var6);
		}

		if(var1.type == 63) {
			var8 = new EntityFireball(this.worldClient, var2, var4, var6, (double)var1.field_28047_e / 8000.0D, (double)var1.field_28046_f / 8000.0D, (double)var1.field_28045_g / 8000.0D);
			var1.field_28044_i = 0;
		}

		if(var1.type == 62) {
			var8 = new EntityEgg(this.worldClient, var2, var4, var6);
		}

		if(var1.type == 1) {
			var8 = new EntityBoat(this.worldClient, var2, var4, var6);
		}

		if(var1.type == 50) {
			var8 = new EntityTNTPrimed(this.worldClient, var2, var4, var6);
		}

		if(var1.type == 70) {
			var8 = new EntityFallingSand(this.worldClient, var2, var4, var6, Block.sand.blockID);
		}

		if(var1.type == 71) {
			var8 = new EntityFallingSand(this.worldClient, var2, var4, var6, Block.gravel.blockID);
		}

		if(var8 != null) {
			((Entity)var8).serverPosX = var1.xPosition;
			((Entity)var8).serverPosY = var1.yPosition;
			((Entity)var8).serverPosZ = var1.zPosition;
			((Entity)var8).rotationYaw = 0.0F;
			((Entity)var8).rotationPitch = 0.0F;
			((Entity)var8).entityId = var1.entityId;
			this.worldClient.func_712_a(var1.entityId, (Entity)var8);
			if(var1.field_28044_i > 0) {
				if(var1.type == 60) {
					Entity var9 = this.getEntityByID(var1.field_28044_i);
					if(var9 instanceof EntityLiving) {
						((EntityArrow)var8).owner = (EntityLiving)var9;
					}
				}

				((Entity)var8).setVelocity((double)var1.field_28047_e / 8000.0D, (double)var1.field_28046_f / 8000.0D, (double)var1.field_28045_g / 8000.0D);
			}
		}

	}

	public void handleWeather(Packet71Weather var1) {
		double var2 = (double)var1.field_27053_b / 32.0D;
		double var4 = (double)var1.field_27057_c / 32.0D;
		double var6 = (double)var1.field_27056_d / 32.0D;
		EntityLightningBolt var8 = null;
		if(var1.field_27055_e == 1) {
			var8 = new EntityLightningBolt(this.worldClient, var2, var4, var6);
		}

		if(var8 != null) {
			var8.serverPosX = var1.field_27053_b;
			var8.serverPosY = var1.field_27057_c;
			var8.serverPosZ = var1.field_27056_d;
			var8.rotationYaw = 0.0F;
			var8.rotationPitch = 0.0F;
			var8.entityId = var1.field_27054_a;
			this.worldClient.addWeatherEffect(var8);
		}

	}

	public void func_21146_a(Packet25EntityPainting var1) {
		EntityPainting var2 = new EntityPainting(this.worldClient, var1.xPosition, var1.yPosition, var1.zPosition, var1.direction, var1.title);
		this.worldClient.func_712_a(var1.entityId, var2);
	}

	public void func_6498_a(Packet28EntityVelocity var1) {
		Entity var2 = this.getEntityByID(var1.entityId);
		if(var2 != null) {
			var2.setVelocity((double)var1.motionX / 8000.0D, (double)var1.motionY / 8000.0D, (double)var1.motionZ / 8000.0D);
		}
	}

	public void func_21148_a(Packet40EntityMetadata var1) {
		Entity var2 = this.getEntityByID(var1.entityId);
		if(var2 != null && var1.func_21047_b() != null) {
			var2.getDataWatcher().updateWatchedObjectsFromList(var1.func_21047_b());
		}

	}

	public void handleNamedEntitySpawn(Packet20NamedEntitySpawn var1) {
		double var2 = (double)var1.xPosition / 32.0D;
		double var4 = (double)var1.yPosition / 32.0D;
		double var6 = (double)var1.zPosition / 32.0D;
		float var8 = (float)(var1.rotation * 360) / 256.0F;
		float var9 = (float)(var1.pitch * 360) / 256.0F;
		EntityOtherPlayerMP var10 = new EntityOtherPlayerMP(this.mc.theWorld, var1.name);
		var10.prevPosX = var10.lastTickPosX = (double)(var10.serverPosX = var1.xPosition);
		var10.prevPosY = var10.lastTickPosY = (double)(var10.serverPosY = var1.yPosition);
		var10.prevPosZ = var10.lastTickPosZ = (double)(var10.serverPosZ = var1.zPosition);
		int var11 = var1.currentItem;
		if(var11 == 0) {
			var10.inventory.mainInventory[var10.inventory.currentItem] = null;
		} else {
			var10.inventory.mainInventory[var10.inventory.currentItem] = new ItemStack(var11, 1, 0);
		}

		var10.setPositionAndRotation(var2, var4, var6, var8, var9);
		this.worldClient.func_712_a(var1.entityId, var10);
	}

	public void handleEntityTeleport(Packet34EntityTeleport var1) {
		Entity var2 = this.getEntityByID(var1.entityId);
		if(var2 != null) {
			var2.serverPosX = var1.xPosition;
			var2.serverPosY = var1.yPosition;
			var2.serverPosZ = var1.zPosition;
			double var3 = (double)var2.serverPosX / 32.0D;
			double var5 = (double)var2.serverPosY / 32.0D + 1.0D / 64.0D;
			double var7 = (double)var2.serverPosZ / 32.0D;
			float var9 = (float)(var1.yaw * 360) / 256.0F;
			float var10 = (float)(var1.pitch * 360) / 256.0F;
			var2.setPositionAndRotation2(var3, var5, var7, var9, var10, 3);
		}
	}

	public void handleEntity(Packet30Entity var1) {
		Entity var2 = this.getEntityByID(var1.entityId);
		if(var2 != null) {
			var2.serverPosX += var1.xPosition;
			var2.serverPosY += var1.yPosition;
			var2.serverPosZ += var1.zPosition;
			double var3 = (double)var2.serverPosX / 32.0D;
			double var5 = (double)var2.serverPosY / 32.0D;
			double var7 = (double)var2.serverPosZ / 32.0D;
			float var9 = var1.rotating ? (float)(var1.yaw * 360) / 256.0F : var2.rotationYaw;
			float var10 = var1.rotating ? (float)(var1.pitch * 360) / 256.0F : var2.rotationPitch;
			var2.setPositionAndRotation2(var3, var5, var7, var9, var10, 3);
		}
	}

	public void handleDestroyEntity(Packet29DestroyEntity var1) {
		this.worldClient.removeEntityFromWorld(var1.entityId);
	}

	public void handleFlying(Packet10Flying var1) {
		EntityPlayerSP var2 = this.mc.thePlayer;
		double var3 = var2.posX;
		double var5 = var2.posY;
		double var7 = var2.posZ;
		float var9 = var2.rotationYaw;
		float var10 = var2.rotationPitch;
		if(var1.moving) {
			var3 = var1.xPosition;
			var5 = var1.yPosition;
			var7 = var1.zPosition;
		}

		if(var1.rotating) {
			var9 = var1.yaw;
			var10 = var1.pitch;
		}

		var2.ySize = 0.0F;
		var2.motionX = var2.motionY = var2.motionZ = 0.0D;
		var2.setPositionAndRotation(var3, var5, var7, var9, var10);
		var1.xPosition = var2.posX;
		var1.yPosition = var2.boundingBox.minY;
		var1.zPosition = var2.posZ;
		var1.stance = var2.posY;
		this.netManager.addToSendQueue(var1);
		if(!this.field_1210_g) {
			this.mc.thePlayer.prevPosX = this.mc.thePlayer.posX;
			this.mc.thePlayer.prevPosY = this.mc.thePlayer.posY;
			this.mc.thePlayer.prevPosZ = this.mc.thePlayer.posZ;
			this.field_1210_g = true;
			this.mc.displayGuiScreen((GuiScreen)null);
		}

	}

	public void handlePreChunk(Packet50PreChunk var1) {
		this.worldClient.doPreChunk(var1.xPosition, var1.yPosition, var1.mode);
	}

	public void handleMultiBlockChange(Packet52MultiBlockChange var1) {
		Chunk var2 = this.worldClient.getChunkFromChunkCoords(var1.xPosition, var1.zPosition);
		int var3 = var1.xPosition * 16;
		int var4 = var1.zPosition * 16;

		for(int var5 = 0; var5 < var1.size; ++var5) {
			short var6 = var1.coordinateArray[var5];
			int var7 = var1.typeArray[var5] & 255;
			byte var8 = var1.metadataArray[var5];
			int var9 = var6 >> 12 & 15;
			int var10 = var6 >> 8 & 15;
			int var11 = var6 & 255;
			var2.setBlockIDWithMetadata(var9, var11, var10, var7, var8);
			this.worldClient.func_711_c(var9 + var3, var11, var10 + var4, var9 + var3, var11, var10 + var4);
			this.worldClient.markBlocksDirty(var9 + var3, var11, var10 + var4, var9 + var3, var11, var10 + var4);
		}

	}

	public void handleMapChunk(Packet51MapChunk var1) {
		this.worldClient.func_711_c(var1.xPosition, var1.yPosition, var1.zPosition, var1.xPosition + var1.xSize - 1, var1.yPosition + var1.ySize - 1, var1.zPosition + var1.zSize - 1);
		this.worldClient.setChunkData(var1.xPosition, var1.yPosition, var1.zPosition, var1.xSize, var1.ySize, var1.zSize, var1.chunk);
	}

	public void handleBlockChange(Packet53BlockChange var1) {
		this.worldClient.func_714_c(var1.xPosition, var1.yPosition, var1.zPosition, var1.type, var1.metadata);
	}

	public void handleKickDisconnect(Packet255KickDisconnect var1) {
		this.netManager.networkShutdown("disconnect.kicked", new Object[0]);
		this.disconnected = true;
		this.mc.changeWorld1((World)null);
		this.mc.displayGuiScreen(new GuiConnectFailed("disconnect.disconnected", "disconnect.genericReason", new Object[]{var1.reason}));
	}

	public void handleErrorMessage(String var1, Object[] var2) {
		if(!this.disconnected) {
			this.disconnected = true;
			this.mc.changeWorld1((World)null);
			this.mc.displayGuiScreen(new GuiConnectFailed("disconnect.lost", var1, var2));
		}
	}

	public void func_28117_a(Packet var1) {
		if(!this.disconnected) {
			this.netManager.addToSendQueue(var1);
			this.netManager.func_28142_c();
		}
	}

	public void addToSendQueue(Packet var1) {
		if(!this.disconnected) {
			this.netManager.addToSendQueue(var1);
		}
	}

	public void handleCollect(Packet22Collect var1) {
		Entity var2 = this.getEntityByID(var1.collectedEntityId);
		Object var3 = (EntityLiving)this.getEntityByID(var1.collectorEntityId);
		if(var3 == null) {
			var3 = this.mc.thePlayer;
		}

		if(var2 != null) {
			this.worldClient.playSoundAtEntity(var2, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			this.mc.effectRenderer.addEffect(new EntityPickupFX(this.mc.theWorld, var2, (Entity)var3, -0.5F));
			this.worldClient.removeEntityFromWorld(var1.collectedEntityId);
		}

	}

	public void handleChat(Packet3Chat var1) {
		this.mc.ingameGUI.addChatMessage(var1.message);
	}

	public void handleArmAnimation(Packet18Animation var1) {
		Entity var2 = this.getEntityByID(var1.entityId);
		if(var2 != null) {
			EntityPlayer var3;
			if(var1.animate == 1) {
				var3 = (EntityPlayer)var2;
				var3.swingItem();
			} else if(var1.animate == 2) {
				var2.performHurtAnimation();
			} else if(var1.animate == 3) {
				var3 = (EntityPlayer)var2;
				var3.wakeUpPlayer(false, false, false);
			} else if(var1.animate == 4) {
				var3 = (EntityPlayer)var2;
				var3.func_6420_o();
			}

		}
	}

	public void func_22186_a(Packet17Sleep var1) {
		Entity var2 = this.getEntityByID(var1.field_22045_a);
		if(var2 != null) {
			if(var1.field_22046_e == 0) {
				EntityPlayer var3 = (EntityPlayer)var2;
				var3.sleepInBedAt(var1.field_22044_b, var1.field_22048_c, var1.field_22047_d);
			}

		}
	}

	public void handleHandshake(Packet2Handshake var1) {
		if(var1.username.equals("-")) {
			this.addToSendQueue(new Packet1Login(this.mc.session.username, 13));
		} else {
			try {
				URL var2 = new URL("http://www.minecraft.net/game/joinserver.jsp?user=" + this.mc.session.username + "&sessionId=" + this.mc.session.sessionId + "&serverId=" + var1.username);
				BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.openStream()));
				String var4 = var3.readLine();
				var3.close();
				if(var4.equalsIgnoreCase("ok")) {
					this.addToSendQueue(new Packet1Login(this.mc.session.username, 13));
				} else {
					this.netManager.networkShutdown("disconnect.loginFailedInfo", new Object[]{var4});
				}
			} catch (Exception var5) {
				var5.printStackTrace();
				this.netManager.networkShutdown("disconnect.genericReason", new Object[]{"Internal client error: " + var5.toString()});
			}
		}

	}

	public void disconnect() {
		this.disconnected = true;
		this.netManager.wakeThreads();
		this.netManager.networkShutdown("disconnect.closed", new Object[0]);
	}

	public void handleMobSpawn(Packet24MobSpawn var1) {
		double var2 = (double)var1.xPosition / 32.0D;
		double var4 = (double)var1.yPosition / 32.0D;
		double var6 = (double)var1.zPosition / 32.0D;
		float var8 = (float)(var1.yaw * 360) / 256.0F;
		float var9 = (float)(var1.pitch * 360) / 256.0F;
		EntityLiving var10 = (EntityLiving)EntityList.createEntity(var1.type, this.mc.theWorld);
		var10.serverPosX = var1.xPosition;
		var10.serverPosY = var1.yPosition;
		var10.serverPosZ = var1.zPosition;
		var10.entityId = var1.entityId;
		var10.setPositionAndRotation(var2, var4, var6, var8, var9);
		var10.isMultiplayerEntity = true;
		this.worldClient.func_712_a(var1.entityId, var10);
		List var11 = var1.getMetadata();
		if(var11 != null) {
			var10.getDataWatcher().updateWatchedObjectsFromList(var11);
		}

	}

	public void handleUpdateTime(Packet4UpdateTime var1) {
		this.mc.theWorld.setWorldTime(var1.time);
	}

	public void handleSpawnPosition(Packet6SpawnPosition var1) {
		this.mc.thePlayer.setPlayerSpawnCoordinate(new ChunkCoordinates(var1.xPosition, var1.yPosition, var1.zPosition));
		this.mc.theWorld.getWorldInfo().setSpawn(var1.xPosition, var1.yPosition, var1.zPosition);
	}

	public void func_6497_a(Packet39AttachEntity var1) {
		Object var2 = this.getEntityByID(var1.entityId);
		Entity var3 = this.getEntityByID(var1.vehicleEntityId);
		if(var1.entityId == this.mc.thePlayer.entityId) {
			var2 = this.mc.thePlayer;
		}

		if(var2 != null) {
			((Entity)var2).mountEntity(var3);
		}
	}

	public void func_9447_a(Packet38EntityStatus var1) {
		Entity var2 = this.getEntityByID(var1.entityId);
		if(var2 != null) {
			var2.handleHealthUpdate(var1.entityStatus);
		}

	}

	private Entity getEntityByID(int var1) {
		return (Entity)(var1 == this.mc.thePlayer.entityId ? this.mc.thePlayer : this.worldClient.func_709_b(var1));
	}

	public void handleHealth(Packet8UpdateHealth var1) {
		this.mc.thePlayer.setHealth(var1.healthMP);
	}

	public void func_9448_a(Packet9Respawn var1) {
		if(var1.field_28048_a != this.mc.thePlayer.dimension) {
			this.field_1210_g = false;
			this.worldClient = new WorldClient(this, this.worldClient.getWorldInfo().getRandomSeed(), var1.field_28048_a);
			this.worldClient.multiplayerWorld = true;
			this.mc.changeWorld1(this.worldClient);
			this.mc.thePlayer.dimension = var1.field_28048_a;
			this.mc.displayGuiScreen(new GuiDownloadTerrain(this));
		}

		this.mc.respawn(true, var1.field_28048_a);
	}

	public void func_12245_a(Packet60Explosion var1) {
		Explosion var2 = new Explosion(this.mc.theWorld, (Entity)null, var1.explosionX, var1.explosionY, var1.explosionZ, var1.explosionSize);
		var2.destroyedBlockPositions = var1.destroyedBlockPositions;
		var2.doExplosionB(true);
	}

	public void func_20087_a(Packet100OpenWindow var1) {
		if(var1.inventoryType == 0) {
			InventoryBasic var2 = new InventoryBasic(var1.windowTitle, var1.slotsCount);
			this.mc.thePlayer.displayGUIChest(var2);
			this.mc.thePlayer.craftingInventory.windowId = var1.windowId;
		} else if(var1.inventoryType == 2) {
			TileEntityFurnace var3 = new TileEntityFurnace();
			this.mc.thePlayer.displayGUIFurnace(var3);
			this.mc.thePlayer.craftingInventory.windowId = var1.windowId;
		} else if(var1.inventoryType == 3) {
			TileEntityDispenser var4 = new TileEntityDispenser();
			this.mc.thePlayer.displayGUIDispenser(var4);
			this.mc.thePlayer.craftingInventory.windowId = var1.windowId;
		} else if(var1.inventoryType == 1) {
			EntityPlayerSP var5 = this.mc.thePlayer;
			this.mc.thePlayer.displayWorkbenchGUI(MathHelper.floor_double(var5.posX), MathHelper.floor_double(var5.posY), MathHelper.floor_double(var5.posZ));
			this.mc.thePlayer.craftingInventory.windowId = var1.windowId;
		}

	}

	public void func_20088_a(Packet103SetSlot var1) {
		if(var1.windowId == -1) {
			this.mc.thePlayer.inventory.setItemStack(var1.myItemStack);
		} else if(var1.windowId == 0 && var1.itemSlot >= 36 && var1.itemSlot < 45) {
			ItemStack var2 = this.mc.thePlayer.inventorySlots.getSlot(var1.itemSlot).getStack();
			if(var1.myItemStack != null && (var2 == null || var2.stackSize < var1.myItemStack.stackSize)) {
				var1.myItemStack.animationsToGo = 5;
			}

			this.mc.thePlayer.inventorySlots.putStackInSlot(var1.itemSlot, var1.myItemStack);
		} else if(var1.windowId == this.mc.thePlayer.craftingInventory.windowId) {
			this.mc.thePlayer.craftingInventory.putStackInSlot(var1.itemSlot, var1.myItemStack);
		}

	}

	public void func_20089_a(Packet106Transaction var1) {
		Container var2 = null;
		if(var1.windowId == 0) {
			var2 = this.mc.thePlayer.inventorySlots;
		} else if(var1.windowId == this.mc.thePlayer.craftingInventory.windowId) {
			var2 = this.mc.thePlayer.craftingInventory;
		}

		if(var2 != null) {
			if(var1.field_20030_c) {
				var2.func_20113_a(var1.field_20028_b);
			} else {
				var2.func_20110_b(var1.field_20028_b);
				this.addToSendQueue(new Packet106Transaction(var1.windowId, var1.field_20028_b, true));
			}
		}

	}

	public void func_20094_a(Packet104WindowItems var1) {
		if(var1.windowId == 0) {
			this.mc.thePlayer.inventorySlots.putStacksInSlots(var1.itemStack);
		} else if(var1.windowId == this.mc.thePlayer.craftingInventory.windowId) {
			this.mc.thePlayer.craftingInventory.putStacksInSlots(var1.itemStack);
		}

	}

	public void func_20093_a(Packet130UpdateSign var1) {
		if(this.mc.theWorld.blockExists(var1.xPosition, var1.yPosition, var1.zPosition)) {
			TileEntity var2 = this.mc.theWorld.getBlockTileEntity(var1.xPosition, var1.yPosition, var1.zPosition);
			if(var2 instanceof TileEntitySign) {
				TileEntitySign var3 = (TileEntitySign)var2;

				for(int var4 = 0; var4 < 4; ++var4) {
					var3.signText[var4] = var1.signLines[var4];
				}

				var3.onInventoryChanged();
			}
		}

	}

	public void func_20090_a(Packet105UpdateProgressbar var1) {
		this.registerPacket(var1);
		if(this.mc.thePlayer.craftingInventory != null && this.mc.thePlayer.craftingInventory.windowId == var1.windowId) {
			this.mc.thePlayer.craftingInventory.func_20112_a(var1.progressBar, var1.progressBarValue);
		}

	}

	public void handlePlayerInventory(Packet5PlayerInventory var1) {
		Entity var2 = this.getEntityByID(var1.entityID);
		if(var2 != null) {
			var2.outfitWithItem(var1.slot, var1.itemID, var1.itemDamage);
		}

	}

	public void func_20092_a(Packet101CloseWindow var1) {
		this.mc.thePlayer.func_20059_m();
	}

	public void func_21145_a(Packet54PlayNoteBlock var1) {
		this.mc.theWorld.playNoteAt(var1.xLocation, var1.yLocation, var1.zLocation, var1.instrumentType, var1.pitch);
	}

	public void func_25118_a(Packet70Bed var1) {
		int var2 = var1.field_25019_b;
		if(var2 >= 0 && var2 < Packet70Bed.field_25020_a.length && Packet70Bed.field_25020_a[var2] != null) {
			this.mc.thePlayer.addChatMessage(Packet70Bed.field_25020_a[var2]);
		}

		if(var2 == 1) {
			this.worldClient.getWorldInfo().setRaining(true);
			this.worldClient.func_27158_h(1.0F);
		} else if(var2 == 2) {
			this.worldClient.getWorldInfo().setRaining(false);
			this.worldClient.func_27158_h(0.0F);
		}

	}

	public void func_28116_a(Packet131MapData var1) {
		if(var1.field_28055_a == Item.mapItem.shiftedIndex) {
			ItemMap.func_28013_a(var1.field_28054_b, this.mc.theWorld).func_28171_a(var1.field_28056_c);
		} else {
			System.out.println("Unknown itemid: " + var1.field_28054_b);
		}

	}

	public void func_28115_a(Packet61DoorChange var1) {
		this.mc.theWorld.func_28106_e(var1.field_28050_a, var1.field_28053_c, var1.field_28052_d, var1.field_28051_e, var1.field_28049_b);
	}

	public void func_27245_a(Packet200Statistic var1) {
		((EntityClientPlayerMP)this.mc.thePlayer).func_27027_b(StatList.func_27361_a(var1.field_27052_a), var1.field_27051_b);
	}

	public boolean isServerHandler() {
		return false;
	}
}
