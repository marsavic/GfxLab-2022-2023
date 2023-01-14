package xyz.marsavic.gfxlab.playground.colorfunctions;

import xyz.marsavic.elements.Element;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.ColorFunctionT;

public class GammaTest extends Element implements ColorFunctionT {
	
	@Override
	public Color at(double t, Vector p) {
		return Color.gray(p.x() < 320 ? 0.5 : (p.xInt() ^ p.yInt()) & 1);
	}
	
}
