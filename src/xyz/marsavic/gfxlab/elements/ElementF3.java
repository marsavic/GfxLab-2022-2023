package xyz.marsavic.gfxlab.elements;

import xyz.marsavic.functions.interfaces.F3;


public abstract class ElementF3<R, P0, P1, P2> extends ElementF<R> {
	
	protected final F3<R, P0, P1, P2> f;
	
	protected final Input<? extends P0> in0;
	protected final Input<? extends P1> in1;
	protected final Input<? extends P2> in2;
	
	
	
	public ElementF3(F3<R, P0, P1, P2> f, HasOutput<? extends P0> p0, HasOutput<? extends P1> p1, HasOutput<? extends P2> p2) {
		this.f = f;
		in0 = new Input<>(p0.out());
		in1 = new Input<>(p1.out());
		in2 = new Input<>(p2.out());
	}
	

	
	public static class Lazy<R, P0, P1, P2> extends ElementF3<R, P0, P1, P2> {
		
		public Lazy(F3<R, P0, P1, P2> f, HasOutput<? extends P0> p0, HasOutput<? extends P1> p1, HasOutput<? extends P2> p2) {
			super(f, p0, p1, p2);
		}
		
		private R object;
		
		@Override
		protected void buildItUp() {
			object = null;
		}
		
		public R object() {
			if (object == null) {
				object = f.at(in0.get(), in1.get(), in2.get());
			}
			
			return object;
		}
	}
	
}
