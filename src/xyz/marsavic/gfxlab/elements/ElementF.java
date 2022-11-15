package xyz.marsavic.gfxlab.elements;

import xyz.marsavic.functions.interfaces.F0;
import xyz.marsavic.functions.interfaces.F1;
import xyz.marsavic.functions.interfaces.F2;
import xyz.marsavic.functions.interfaces.F3;

public abstract class ElementF<R> extends Element implements HasOutput<R> {
	
	public abstract R object();
	
	
	public final Output<R> out = new Output<>(this::object);
	
	@Override
	public Output<R> out() {
		return out;
	}
	
	
	public static <R> ElementF0<R> e(R result) {
		return e(() -> result);
	}
	
	public static <R            > ElementF0<R            > e(F0<R> f                                                                                                ) { return new ElementF0.Lazy<>(f            ); }
	public static <R, P0        > ElementF1<R, P0        > e(F1<R, P0        > f, HasOutput<? extends P0> p0                                                        ) { return new ElementF1.Lazy<>(f, p0        ); }
	public static <R, P0, P1    > ElementF2<R, P0, P1    > e(F2<R, P0, P1    > f, HasOutput<? extends P0> p0, HasOutput<? extends P1> p1                            ) { return new ElementF2.Lazy<>(f, p0, p1    ); }
	public static <R, P0, P1, P2> ElementF3<R, P0, P1, P2> e(F3<R, P0, P1, P2> f, HasOutput<? extends P0> p0, HasOutput<? extends P1> p1, HasOutput<? extends P2> p2) { return new ElementF3.Lazy<>(f, p0, p1, p2); }
	
}
