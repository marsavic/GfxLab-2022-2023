package xyz.marsavic.gfxlab.playground.colorfunctions;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.ColorFunctionT;
import xyz.marsavic.gfxlab.elements.Element;


public class OkLab extends Element implements ColorFunctionT {
	
	@Override
	public Color at(double t, Vector p) {
		return Color.oklabPolar(t, p.x(), p.y());
	}
	
}
