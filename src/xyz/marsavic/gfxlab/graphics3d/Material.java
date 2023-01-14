package xyz.marsavic.gfxlab.graphics3d;

import xyz.marsavic.functions.F1;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;

public record Material(
		Color diffuse,
		Color specular,
		double shininess,
		Color reflective,
		Color refractive,
		double refractiveIndex,
		
		Color emittance,
		BSDF bsdf
) implements F1<Material, Vector> {
	
	public Material(Color diffuse, Color specular, double shininess, Color reflective, Color refractive, double refractiveIndex, Color emittance) {
		this(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance,
				BSDF.add(
						new BSDF[] {
								BSDF.diffuse   (diffuse),
								BSDF.reflective(reflective),
								BSDF.refractive(refractive, refractiveIndex)
						},
						new double[] {
								diffuse.luminance(),
								reflective.luminance(),
								refractive.luminance()
						}
				)
		);
	}
	
	public Material(BSDF bsdf) {
		this(Color.BLACK, Color.BLACK, 32.0, Color.BLACK, Color.BLACK, 1.5, Color.BLACK, bsdf);
	}
	
	
	@Override
	public Material at(Vector vector) {
		return this;
	}
	
	
	public Material diffuse        (Color  diffuse        ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance); }
	public Material specular       (Color  specular       ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance); }
	public Material shininess      (double shininess      ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance); }
	public Material reflective     (Color  reflective     ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance); }
	public Material refractive     (Color  refractive     ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance); }
	public Material refractiveIndex(double refractiveIndex) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance); }
	public Material emittance      (Color  emittance      ) { return new Material(diffuse, specular, shininess, reflective, refractive, refractiveIndex, emittance); }
	
	public Material specularCopyDiffuse() { return this.specular(diffuse()); }
	
	
	public static final Material BLACK   = new Material(Color.BLACK, Color.BLACK, 32, Color.BLACK, Color.BLACK, 1.5, Color.BLACK);
	
	public static Material matte(Color  c) { return BLACK.diffuse(c); }
	public static Material matte(double k) { return matte(Color.gray(k)); }
	public static Material matte(        ) { return matte(1.0); }
	public static final Material MATTE = matte();
	
	public static Material mirror(Color  c) { return BLACK.reflective(c); }
	public static Material mirror(double k) { return mirror(Color.gray(k)); }
	public static Material mirror(        ) { return mirror(1.0); }
	public static final Material MIRROR = mirror();
	
	public static Material glass(Color  c) { return BLACK.refractive(c).refractiveIndex(1.5); }
	public static Material glass(double k) { return glass(Color.gray(k)); }
	public static Material glass(        ) { return glass(1.0); }
	public static final Material GLASS = glass();
	
	public static Material light(Color  c) { return BLACK.emittance(c); }
	public static Material light(double k) { return light(Color.gray(k)); }
	public static Material light(        ) { return light(1.0); }
	public static final Material LIGHT = light();
	
}
