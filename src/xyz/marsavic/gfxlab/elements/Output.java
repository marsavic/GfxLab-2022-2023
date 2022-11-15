package xyz.marsavic.gfxlab.elements;

import xyz.marsavic.functions.interfaces.F0;


public class Output<T> extends Invalidatable.Base implements HasOutput<T> {
	
	private final F0<T> resultProvider;

	
	
	public Output(F0<T> resultProvider) {
		this.resultProvider = resultProvider;
	}
	
	
	public T get() {
		return resultProvider.at();
	}
	
	
	@Override
	public Output<T> out() {
		return this;
	}
	

	public static <T> Output<T> val(T object) {
		return new Output<>(() -> object);
	}
	
}
