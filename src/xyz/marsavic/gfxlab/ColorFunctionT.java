package xyz.marsavic.gfxlab;

import xyz.marsavic.geometry.Vector;


public interface ColorFunctionT extends ColorFunction {
	
	/**	p is a point in (x, y) space. */
	Color at(double t, Vector p);
	
	/**	p is a point in (t, x, y) space. */
	@Override
	default Color at(Vec3 p) {
		return at(p.x(), p.p12());
	}
	
}
