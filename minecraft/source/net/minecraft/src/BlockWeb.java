package net.minecraft.src;

public class BlockWeb extends Block {
	public BlockWeb(int var1, int var2) {
		super(var1, var2, Material.cloth);
	}

	public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5) {
		var5.isInWeb = true;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return null;
	}

	public int getRenderType() {
		return 1;
	}
}
