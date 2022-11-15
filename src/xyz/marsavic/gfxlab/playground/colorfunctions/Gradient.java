package xyz.marsavic.gfxlab.playground.colorfunctions;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.ColorFunctionT;
import xyz.marsavic.gfxlab.elements.Element;
import xyz.marsavic.utils.Numeric;


public class Gradient extends Element implements ColorFunctionT {
	
	@Override
	public Color at(double t, Vector p) {
		return Color.rgb(p.x(), 1-p.x(), 0.5 + 0.5 * Numeric.sinT(t));
	}
	
}
