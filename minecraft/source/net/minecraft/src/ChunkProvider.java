package net.minecraft.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChunkProvider implements IChunkProvider {
	private Set field_28065_a = new HashSet();
	private Chunk field_28064_b;
	private IChunkProvider field_28070_c;
	private IChunkLoader field_28069_d;
	private Map field_28068_e = new HashMap();
	private List field_28067_f = new ArrayList();
	private World field_28066_g;

	public ChunkProvider(World var1, IChunkLoader var2, IChunkProvider var3) {
		this.field_28064_b = new EmptyChunk(var1, new byte[-Short.MIN_VALUE], 0, 0);
		this.field_28066_g = var1;
		this.field_28069_d = var2;
		this.field_28070_c = var3;
	}

	public boolean chunkExists(int var1, int var2) {
		return this.field_28068_e.containsKey(Integer.valueOf(ChunkCoordIntPair.chunkXZ2Int(var1, var2)));
	}

	public Chunk func_538_d(int var1, int var2) {
		int var3 = ChunkCoordIntPair.chunkXZ2Int(var1, var2);
		this.field_28065_a.remove(Integer.valueOf(var3));
		Chunk var4 = (Chunk)this.field_28068_e.get(Integer.valueOf(var3));
		if(var4 == null) {
			var4 = this.func_28061_d(var1, var2);
			if(var4 == null) {
				if(this.field_28070_c == null) {
					var4 = this.field_28064_b;
				} else {
					var4 = this.field_28070_c.provideChunk(var1, var2);
				}
			}

			this.field_28068_e.put(Integer.valueOf(var3), var4);
			this.field_28067_f.add(var4);
			if(var4 != null) {
				var4.func_4143_d();
				var4.onChunkLoad();
			}

			if(!var4.isTerrainPopulated && this.chunkExists(var1 + 1, var2 + 1) && this.chunkExists(var1, var2 + 1) && this.chunkExists(var1 + 1, var2)) {
				this.populate(this, var1, var2);
			}

			if(this.chunkExists(var1 - 1, var2) && !this.provideChunk(var1 - 1, var2).isTerrainPopulated && this.chunkExists(var1 - 1, var2 + 1) && this.chunkExists(var1, var2 + 1) && this.chunkExists(var1 - 1, var2)) {
				this.populate(this, var1 - 1, var2);
			}

			if(this.chunkExists(var1, var2 - 1) && !this.provideChunk(var1, var2 - 1).isTerrainPopulated && this.chunkExists(var1 + 1, var2 - 1) && this.chunkExists(var1, var2 - 1) && this.chunkExists(var1 + 1, var2)) {
				this.populate(this, var1, var2 - 1);
			}

			if(this.chunkExists(var1 - 1, var2 - 1) && !this.provideChunk(var1 - 1, var2 - 1).isTerrainPopulated && this.chunkExists(var1 - 1, var2 - 1) && this.chunkExists(var1, var2 - 1) && this.chunkExists(var1 - 1, var2)) {
				this.populate(this, var1 - 1, var2 - 1);
			}
		}

		return var4;
	}

	public Chunk provideChunk(int var1, int var2) {
		Chunk var3 = (Chunk)this.field_28068_e.get(Integer.valueOf(ChunkCoordIntPair.chunkXZ2Int(var1, var2)));
		return var3 == null ? this.func_538_d(var1, var2) : var3;
	}

	private Chunk func_28061_d(int var1, int var2) {
		if(this.field_28069_d == null) {
			return null;
		} else {
			try {
				Chunk var3 = this.field_28069_d.loadChunk(this.field_28066_g, var1, var2);
				if(var3 != null) {
					var3.lastSaveTime = this.field_28066_g.getWorldTime();
				}

				return var3;
			} catch (Exception var4) {
				var4.printStackTrace();
				return null;
			}
		}
	}

	private void func_28063_a(Chunk var1) {
		if(this.field_28069_d != null) {
			try {
				this.field_28069_d.saveExtraChunkData(this.field_28066_g, var1);
			} catch (Exception var3) {
				var3.printStackTrace();
			}

		}
	}

	private void func_28062_b(Chunk var1) {
		if(this.field_28069_d != null) {
			try {
				var1.lastSaveTime = this.field_28066_g.getWorldTime();
				this.field_28069_d.saveChunk(this.field_28066_g, var1);
			} catch (IOException var3) {
				var3.printStackTrace();
			}

		}
	}

	public void populate(IChunkProvider var1, int var2, int var3) {
		Chunk var4 = this.provideChunk(var2, var3);
		if(!var4.isTerrainPopulated) {
			var4.isTerrainPopulated = true;
			if(this.field_28070_c != null) {
				this.field_28070_c.populate(var1, var2, var3);
				var4.setChunkModified();
			}
		}

	}

	public boolean saveChunks(boolean var1, IProgressUpdate var2) {
		int var3 = 0;

		for(int var4 = 0; var4 < this.field_28067_f.size(); ++var4) {
			Chunk var5 = (Chunk)this.field_28067_f.get(var4);
			if(var1 && !var5.neverSave) {
				this.func_28063_a(var5);
			}

			if(var5.needsSaving(var1)) {
				this.func_28062_b(var5);
				var5.isModified = false;
				++var3;
				if(var3 == 24 && !var1) {
					return false;
				}
			}
		}

		if(var1) {
			if(this.field_28069_d == null) {
				return true;
			}

			this.field_28069_d.saveExtraData();
		}

		return true;
	}

	public boolean func_532_a() {
		for(int var1 = 0; var1 < 100; ++var1) {
			if(!this.field_28065_a.isEmpty()) {
				Integer var2 = (Integer)this.field_28065_a.iterator().next();
				Chunk var3 = (Chunk)this.field_28068_e.get(var2);
				var3.onChunkUnload();
				this.func_28062_b(var3);
				this.func_28063_a(var3);
				this.field_28065_a.remove(var2);
				this.field_28068_e.remove(var2);
				this.field_28067_f.remove(var3);
			}
		}

		if(this.field_28069_d != null) {
			this.field_28069_d.func_814_a();
		}

		return this.field_28070_c.func_532_a();
	}

	public boolean func_536_b() {
		return true;
	}

	public String makeString() {
		return "ServerChunkCache: " + this.field_28068_e.size() + " Drop: " + this.field_28065_a.size();
	}
}
