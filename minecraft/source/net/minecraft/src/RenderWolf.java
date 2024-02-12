package net.minecraft.src;

public class RenderWolf extends RenderLiving {
	public RenderWolf(ModelBase var1, float var2) {
		super(var1, var2);
	}

	public void func_25005_a(EntityWolf var1, double var2, double var4, double var6, float var8, float var9) {
		super.doRenderLiving(var1, var2, var4, var6, var8, var9);
	}

	protected float func_25004_a(EntityWolf var1, float var2) {
		return var1.setTailRotation();
	}

	protected void func_25006_b(EntityWolf var1, float var2) {
	}

	protected void preRenderCallback(EntityLiving var1, float var2) {
		this.func_25006_b((EntityWolf)var1, var2);
	}

	protected float func_170_d(EntityLiving var1, float var2) {
		return this.func_25004_a((EntityWolf)var1, var2);
	}

	public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9) {
		this.func_25005_a((EntityWolf)var1, var2, var4, var6, var8, var9);
	}

	public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
		this.func_25005_a((EntityWolf)var1, var2, var4, var6, var8, var9);
	}
}
