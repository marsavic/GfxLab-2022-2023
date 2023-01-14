package xyz.marsavic.gfxlab.tonemapping.colortransforms;

import xyz.marsavic.elements.Immutable;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.tonemapping.ColorTransform;


@Immutable
public record Multiply(
		double factor
) implements ColorTransform {
	
	@Override
	public Color at(Color color) {
		return color.mul(factor);
	}
	
}
