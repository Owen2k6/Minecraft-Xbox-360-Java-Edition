package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneRepeater extends Block {
	public static final double[] field_22014_a = new double[]{-0.0625D, 1.0D / 16.0D, 0.1875D, 0.3125D};
	private static final int[] field_22013_b = new int[]{1, 2, 3, 4};
	private final boolean field_22015_c;

	protected BlockRedstoneRepeater(int var1, boolean var2) {
		super(var1, 6, Material.circuits);
		this.field_22015_c = var2;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F / 16.0F, 1.0F);
	}

	public boolean func_28025_b() {
		return false;
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return !var1.isBlockOpaqueCube(var2, var3 - 1, var4) ? false : super.canPlaceBlockAt(var1, var2, var3, var4);
	}

	public boolean canBlockStay(World var1, int var2, int var3, int var4) {
		return !var1.isBlockOpaqueCube(var2, var3 - 1, var4) ? false : super.canBlockStay(var1, var2, var3, var4);
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		int var6 = var1.getBlockMetadata(var2, var3, var4);
		boolean var7 = this.func_22012_g(var1, var2, var3, var4, var6);
		if(this.field_22015_c && !var7) {
			var1.setBlockAndMetadataWithNotify(var2, var3, var4, Block.redstoneRepeaterIdle.blockID, var6);
		} else if(!this.field_22015_c) {
			var1.setBlockAndMetadataWithNotify(var2, var3, var4, Block.redstoneRepeaterActive.blockID, var6);
			if(!var7) {
				int var8 = (var6 & 12) >> 2;
				var1.scheduleUpdateTick(var2, var3, var4, Block.redstoneRepeaterActive.blockID, field_22013_b[var8] * 2);
			}
		}

	}

	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		return var1 == 0 ? (this.field_22015_c ? 99 : 115) : (var1 == 1 ? (this.field_22015_c ? 147 : 131) : 5);
	}

	public int getBlockTextureFromSide(int var1) {
		return this.getBlockTextureFromSideAndMetadata(var1, 0);
	}

	public boolean isIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5) {
		return this.isPoweringTo(var1, var2, var3, var4, var5);
	}

	public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		if(!this.field_22015_c) {
			return false;
		} else {
			int var6 = var1.getBlockMetadata(var2, var3, var4) & 3;
			return var6 == 0 && var5 == 3 ? true : (var6 == 1 && var5 == 4 ? true : (var6 == 2 && var5 == 2 ? true : var6 == 3 && var5 == 5));
		}
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		if(!this.canBlockStay(var1, var2, var3, var4)) {
			this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
			var1.setBlockWithNotify(var2, var3, var4, 0);
		} else {
			int var6 = var1.getBlockMetadata(var2, var3, var4);
			boolean var7 = this.func_22012_g(var1, var2, var3, var4, var6);
			int var8 = (var6 & 12) >> 2;
			if(this.field_22015_c && !var7) {
				var1.scheduleUpdateTick(var2, var3, var4, this.blockID, field_22013_b[var8] * 2);
			} else if(!this.field_22015_c && var7) {
				var1.scheduleUpdateTick(var2, var3, var4, this.blockID, field_22013_b[var8] * 2);
			}

		}
	}

	private boolean func_22012_g(World var1, int var2, int var3, int var4, int var5) {
		int var6 = var5 & 3;
		switch(var6) {
		case 0:
			return var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4 + 1, 3);
		case 1:
			return var1.isBlockIndirectlyProvidingPowerTo(var2 - 1, var3, var4, 4);
		case 2:
			return var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4 - 1, 2);
		case 3:
			return var1.isBlockIndirectlyProvidingPowerTo(var2 + 1, var3, var4, 5);
		default:
			return false;
		}
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		int var6 = var1.getBlockMetadata(var2, var3, var4);
		int var7 = (var6 & 12) >> 2;
		var7 = var7 + 1 << 2 & 12;
		var1.setBlockMetadataWithNotify(var2, var3, var4, var7 | var6 & 3);
		return true;
	}

	public boolean canProvidePower() {
		return false;
	}

	public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5) {
		int var6 = ((MathHelper.floor_double((double)(var5.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
		var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
		boolean var7 = this.func_22012_g(var1, var2, var3, var4, var6);
		if(var7) {
			var1.scheduleUpdateTick(var2, var3, var4, this.blockID, 1);
		}

	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
		var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
		var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
		var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
		var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
		var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int idDropped(int var1, Random var2) {
		return Item.redstoneRepeater.shiftedIndex;
	}
}
