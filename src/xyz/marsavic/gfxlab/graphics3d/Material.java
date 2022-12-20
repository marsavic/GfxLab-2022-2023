package xyz.marsavic.gfxlab.graphics3d;

import xyz.marsavic.gfxlab.Color;

public record Material(
		Color diffuse,
		Color specular,
		double shininess,
		Color reflective,
		Color refractive,
		double refractiveIndex

) {
	public Material diffuse        (Color  diffuse        ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex); }
	public Material specular       (Color  specular       ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex); }
	public Material shininess      (double shininess      ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex); }
	public Material reflective     (Color  reflective     ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex); }
	public Material refractive     (Color  refractive     ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex); }
	public Material refractiveIndex(double refractiveIndex) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex); }
	
	public static final Material BLACK   = new Material(Color.BLACK, Color.BLACK, 32, Color.BLACK, Color.BLACK, 1.5);
	
	public static Material matte (Color  c) { return BLACK.diffuse(c); }
	public static Material matte (double k) { return matte(Color.gray(k)); }
	public static Material matte (        ) { return matte(Color.WHITE); }
	public static final Material MATTE = matte();
	
	public static final Material MIRROR = BLACK.reflective(Color.WHITE);
	public static final Material GLASS = BLACK.refractive(Color.WHITE).refractiveIndex(1.5);
}
