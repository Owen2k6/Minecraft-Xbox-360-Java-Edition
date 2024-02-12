package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderSquid extends RenderLiving {
	public RenderSquid(ModelBase var1, float var2) {
		super(var1, var2);
	}

	public void func_21008_a(EntitySquid var1, double var2, double var4, double var6, float var8, float var9) {
		super.doRenderLiving(var1, var2, var4, var6, var8, var9);
	}

	protected void func_21007_a(EntitySquid var1, float var2, float var3, float var4) {
		float var5 = var1.field_21088_b + (var1.field_21089_a - var1.field_21088_b) * var4;
		float var6 = var1.field_21086_f + (var1.field_21087_c - var1.field_21086_f) * var4;
		GL11.glTranslatef(0.0F, 0.5F, 0.0F);
		GL11.glRotatef(180.0F - var3, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(var5, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(var6, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, -1.2F, 0.0F);
	}

	protected void func_21005_a(EntitySquid var1, float var2) {
	}

	protected float func_21006_b(EntitySquid var1, float var2) {
		float var3 = var1.field_21082_j + (var1.field_21083_i - var1.field_21082_j) * var2;
		return var3;
	}

	protected void preRenderCallback(EntityLiving var1, float var2) {
		this.func_21005_a((EntitySquid)var1, var2);
	}

	protected float func_170_d(EntityLiving var1, float var2) {
		return this.func_21006_b((EntitySquid)var1, var2);
	}

	protected void rotateCorpse(EntityLiving var1, float var2, float var3, float var4) {
		this.func_21007_a((EntitySquid)var1, var2, var3, var4);
	}

	public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9) {
		this.func_21008_a((EntitySquid)var1, var2, var4, var6, var8, var9);
	}

	public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
		this.func_21008_a((EntitySquid)var1, var2, var4, var6, var8, var9);
	}
}
