package xyz.marsavic.gfxlab.elements;

import xyz.marsavic.functions.interfaces.F0;


public abstract class ElementF0<R> extends ElementF<R> {
	
	protected final F0<R> f;
	
	
	
	public ElementF0(F0<R> f) {
		this.f = f;
	}
	
	
	
	public static class Lazy<R> extends ElementF0<R> {
		
		public Lazy(F0<R> f) {
			super(f);
		}
		
		private R object;
		
		@Override
		protected void buildItUp() {
			object = null;
		}
		
		public R object() {
			if (object == null) {
				object = f.at();
			}
			
			return object;
		}
	}
	
}
