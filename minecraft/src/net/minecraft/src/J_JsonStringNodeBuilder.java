package net.minecraft.src;

public final class J_JsonStringNodeBuilder implements J_JsonNodeBuilder {
	private final String field_27244_a;

	J_JsonStringNodeBuilder(String var1) {
		this.field_27244_a = var1;
	}

	public J_JsonStringNode func_27243_a() {
		return J_JsonNodeFactories.func_27316_a(this.field_27244_a);
	}

	public J_JsonNode func_27234_b() {
		return this.func_27243_a();
	}
}
