package net.minecraft.src;

import java.util.Comparator;

class SorterStatsBlock implements Comparator {
	final GuiStats field_27299_a;
	final GuiSlotStatsBlock field_27298_b;

	SorterStatsBlock(GuiSlotStatsBlock var1, GuiStats var2) {
		this.field_27298_b = var1;
		this.field_27299_a = var2;
	}

	public int func_27297_a(StatCrafting var1, StatCrafting var2) {
		int var3 = var1.func_25072_b();
		int var4 = var2.func_25072_b();
		StatBase var5 = null;
		StatBase var6 = null;
		if(this.field_27298_b.field_27271_e == 2) {
			var5 = StatList.mineBlockStatArray[var3];
			var6 = StatList.mineBlockStatArray[var4];
		} else if(this.field_27298_b.field_27271_e == 0) {
			var5 = StatList.field_25158_z[var3];
			var6 = StatList.field_25158_z[var4];
		} else if(this.field_27298_b.field_27271_e == 1) {
			var5 = StatList.field_25172_A[var3];
			var6 = StatList.field_25172_A[var4];
		}

		if(var5 != null || var6 != null) {
			if(var5 == null) {
				return 1;
			}

			if(var6 == null) {
				return -1;
			}

			int var7 = GuiStats.func_27142_c(this.field_27298_b.field_27274_a).func_27184_a(var5);
			int var8 = GuiStats.func_27142_c(this.field_27298_b.field_27274_a).func_27184_a(var6);
			if(var7 != var8) {
				return (var7 - var8) * this.field_27298_b.field_27270_f;
			}
		}

		return var3 - var4;
	}

	public int compare(Object var1, Object var2) {
		return this.func_27297_a((StatCrafting)var1, (StatCrafting)var2);
	}
}
