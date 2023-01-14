package xyz.marsavic.gfxlab;

import xyz.marsavic.functions.F1;


public class TransformationsFromSize {
	
	public static final F1<Transformation, Vec3> toIdentity  = TransformationsFromSize::toIdentity;
	public static final F1<Transformation, Vec3> toUnitBox   = TransformationsFromSize::toUnitBox;
	public static final F1<Transformation, Vec3> toGeometric = TransformationsFromSize::toGeometric;

	
	
	public static Transformation toIdentity(Vec3 s) {
		return Transformation.identity;
	}
	
	
	public static Transformation toUnitBox(Vec3 s) {
		return p -> p.div(s);
	}
	
	
	private static final Vec3 t = Vec3.xyz(-1, -1,  1);
	
	public static Transformation toGeometric(Vec3 s) {
		Vec3 c = Vec3.xyz( 2,  2, -2).div(s);
		return p -> p.mul(c).add(t);
	}
	
}
