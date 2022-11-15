package xyz.marsavic.gfxlab.elements;

import xyz.marsavic.functions.interfaces.F1;


public abstract class ElementF1<R, P0> extends ElementF<R> {
	
	protected final F1<R, P0> f;
	
	protected final Input<? extends P0> in0;
	
	
	
	public ElementF1(F1<R, P0> f, HasOutput<? extends P0> p0) {
		this.f = f;
		in0 = new Input<>(p0.out());
	}
	
	
	
	public static class Lazy<R, P0> extends ElementF1<R, P0> {
		
		public Lazy(F1<R, P0> f, HasOutput<? extends P0> p0) {
			super(f, p0);
		}
		
		private R object;
		
		@Override
		protected void buildItUp() {
			object = null;
		}
		
		public R object() {
			if (object == null) {
				object = f.at(in0.get());
			}
			
			return object;
		}
	}
	
}
