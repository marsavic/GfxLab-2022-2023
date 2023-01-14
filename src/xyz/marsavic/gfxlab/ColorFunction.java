package xyz.marsavic.gfxlab;

import xyz.marsavic.functions.F1;
import xyz.marsavic.geometry.Vector;


public interface ColorFunction extends F1<Color, Vec3> {
	
	default Color at(double t, Vector p) {
		return at(Vec3.xp(t, p));
	}
	
}
