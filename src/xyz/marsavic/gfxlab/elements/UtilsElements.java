package xyz.marsavic.gfxlab.elements;

import com.google.common.primitives.Primitives;

// TODO not needed - remove
public class UtilsElements {
	
	@SuppressWarnings("RedundantIfStatement")
	static boolean looksImmutable(Class<?> c) {
		if (c.isRecord()) return true;
		if (c.isAnnotationPresent(Immutable.class)) return true;
		if (Primitives.isWrapperType(c)) return true;
		
		return false;
	}
	
	static boolean looksImmutable(Object o) {
		return looksImmutable(o.getClass());
	}
	
}
