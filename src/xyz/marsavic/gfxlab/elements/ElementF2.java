package xyz.marsavic.gfxlab.elements;

import xyz.marsavic.functions.interfaces.F2;


public abstract class ElementF2<R, P0, P1> extends ElementF<R> {
	
	protected final F2<R, P0, P1> f;
	
	protected final Input<? extends P0> in0;
	protected final Input<? extends P1> in1;
	
	
	
	public ElementF2(F2<R, P0, P1> f, HasOutput<? extends P0> p0, HasOutput<? extends P1> p1) {
		this.f = f;
		in0 = new Input<>(p0.out());
		in1 = new Input<>(p1.out());
	}
	

	
	public static class Lazy<R, P0, P1> extends ElementF2<R, P0, P1> {
		
		public Lazy(F2<R, P0, P1> f, HasOutput<? extends P0> p0, HasOutput<? extends P1> p1) {
			super(f, p0, p1);
		}
		
		private R object;
		
		@Override
		protected void buildItUp() {
			object = null;
		}
		
		public R object() {
			if (object == null) {
				object = f.at(in0.get(), in1.get());
			}
			
			return object;
		}
	}
	
}
