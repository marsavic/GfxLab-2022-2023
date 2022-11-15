package xyz.marsavic.gfxlab.elements;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// Remove this class, it's not needed any more? Or keep it because it allows arbitrarily many inputs.
public class ElementPureLazy<T> extends Element {
	
	private final List<Input<?>> inputs;
	public final Output<T> out = new Output<>(this::object);
	
	private T object;
	private final Constructor<T> constructor;
	private final int nParameters;
	
	
	public ElementPureLazy(Class<T> type, Output<?>... outputs) {
//		if (!UtilsElements.looksImmutable(type)) {
//			throw new IllegalArgumentException("The type does not look immutable. If it is, annotate it with @Immutable.");
//		}
		
		//noinspection unchecked
		Constructor<T>[] constructors = (Constructor<T>[]) type.getConstructors();
		if (constructors.length != 1) {
			throw new IllegalArgumentException("Specified class must have exactly one public constructor.");
		}
		
		constructor = constructors[0];
		nParameters = constructor.getParameterCount();
		if (nParameters != outputs.length) {
			throw new IllegalArgumentException("The number of provided outputs does not match the number of arguments of the constructor.");
		}
		
		inputs = new ArrayList<>(nParameters);
//		Class<?>[] types = constructor.getParameterTypes();
		for (Output<?> output : outputs) {
			// TODO Type check somehow!?
			inputs.add(new Input<>(output));
		}
	}
	
	public static <T> ElementPureLazy<T> of(Class<T> type, Object... args) {
		//noinspection SuspiciousToArrayCall
		return new ElementPureLazy<T>(
				type,
				Arrays.stream(args)
						.map(o -> o.getClass() == Output.class ? o : new Output<>(() -> o))
						.toArray(Output<?>[]::new));
	}
	
	// TODO Constructor with named parameters (like in the class Defaults)
	
	
	@Override
	public List<Input<?>> inputs() {
		return inputs;
	}
	
	
	@Override
	protected void buildItUp() {
		object = null;
	}
	
	
	public T object() {
		if (object == null) {
			Object[] args = new Object[nParameters];
			for (int i = 0; i < nParameters; i++) {
				args[i] = inputs.get(i).get();
			}
			try {
				object = constructor.newInstance(args);
			} catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		
		return object;
	}
	
}
