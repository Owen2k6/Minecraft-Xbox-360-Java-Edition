package net.minecraft.src;

public interface IChunkProvider {
	boolean chunkExists(int var1, int var2);

	Chunk provideChunk(int var1, int var2);

	Chunk func_538_d(int var1, int var2);

	void populate(IChunkProvider var1, int var2, int var3);

	boolean saveChunks(boolean var1, IProgressUpdate var2);

	boolean func_532_a();

	boolean func_536_b();

	String makeString();
}
