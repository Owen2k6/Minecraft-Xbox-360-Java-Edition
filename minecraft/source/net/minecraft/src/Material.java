package net.minecraft.src;

public class Material {
	public static final Material air = new MaterialTransparent(MapColor.field_28212_b);
	public static final Material grassMaterial = new Material(MapColor.field_28211_c);
	public static final Material ground = new Material(MapColor.field_28202_l);
	public static final Material wood = (new Material(MapColor.field_28199_o)).setBurning();
	public static final Material rock = new Material(MapColor.field_28201_m);
	public static final Material iron = new Material(MapColor.field_28206_h);
	public static final Material water = new MaterialLiquid(MapColor.field_28200_n);
	public static final Material lava = new MaterialLiquid(MapColor.field_28208_f);
	public static final Material leaves = (new Material(MapColor.field_28205_i)).setBurning().func_28127_i();
	public static final Material plants = new MaterialLogic(MapColor.field_28205_i);
	public static final Material sponge = new Material(MapColor.field_28209_e);
	public static final Material cloth = (new Material(MapColor.field_28209_e)).setBurning();
	public static final Material fire = new MaterialTransparent(MapColor.field_28212_b);
	public static final Material sand = new Material(MapColor.field_28210_d);
	public static final Material circuits = new MaterialLogic(MapColor.field_28212_b);
	public static final Material glass = (new Material(MapColor.field_28212_b)).func_28127_i();
	public static final Material tnt = (new Material(MapColor.field_28208_f)).setBurning().func_28127_i();
	public static final Material field_4262_q = new Material(MapColor.field_28205_i);
	public static final Material ice = (new Material(MapColor.field_28207_g)).func_28127_i();
	public static final Material snow = (new MaterialLogic(MapColor.field_28204_j)).func_27284_f().func_28127_i();
	public static final Material builtSnow = new Material(MapColor.field_28204_j);
	public static final Material cactus = (new Material(MapColor.field_28205_i)).func_28127_i();
	public static final Material clay = new Material(MapColor.field_28203_k);
	public static final Material pumpkin = new Material(MapColor.field_28205_i);
	public static final Material portal = new MaterialPortal(MapColor.field_28212_b);
	public static final Material cakeMaterial = new Material(MapColor.field_28212_b);
	private boolean canBurn;
	private boolean field_27285_A;
	private boolean field_28128_D;
	public final MapColor materialMapColor;

	public Material(MapColor var1) {
		this.materialMapColor = var1;
	}

	public boolean getIsLiquid() {
		return false;
	}

	public boolean isSolid() {
		return true;
	}

	public boolean getCanBlockGrass() {
		return true;
	}

	public boolean getIsSolid() {
		return true;
	}

	private Material func_28127_i() {
		this.field_28128_D = true;
		return this;
	}

	private Material setBurning() {
		this.canBurn = true;
		return this;
	}

	public boolean getBurning() {
		return this.canBurn;
	}

	public Material func_27284_f() {
		this.field_27285_A = true;
		return this;
	}

	public boolean func_27283_g() {
		return this.field_27285_A;
	}

	public boolean func_28126_h() {
		return this.field_28128_D ? false : this.getIsSolid();
	}
}
