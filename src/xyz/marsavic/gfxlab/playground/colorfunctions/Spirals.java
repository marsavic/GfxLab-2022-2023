package xyz.marsavic.gfxlab.playground.colorfunctions;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.ColorFunctionT;
import xyz.marsavic.gfxlab.elements.Element;
import xyz.marsavic.utils.Numeric;


public class Spirals extends Element implements ColorFunctionT {
	
	@Override
	public Color at(double t, Vector p) {
		return Color.rgb(
				Math.max(0, Numeric.sinT(-t + 7 * p.angle())),
				Math.max(0, Numeric.sinT(2 * t + 0.25 / p.length() + p.angle())),
				0.4
		);
	}
	
}
