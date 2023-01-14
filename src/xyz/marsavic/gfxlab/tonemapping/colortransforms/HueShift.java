package xyz.marsavic.gfxlab.tonemapping.colortransforms;

import xyz.marsavic.elements.Immutable;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.tonemapping.ColorTransform;


@Immutable
public record HueShift(
		double hueShift
) implements ColorTransform {
	
	@Override
	public Color at(Color color) {
		Vec3 hsb = color.hsb();
		return Color.hsb(hsb.add(Vec3.xyz(hueShift, 0, 0)));
	}
	
}
