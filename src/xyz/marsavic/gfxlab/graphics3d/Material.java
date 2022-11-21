package xyz.marsavic.gfxlab.graphics3d;

import xyz.marsavic.gfxlab.Color;

public record Material(
		Color diffuse
) {
	public Material diffuse(Color diffuse) { return new Material(diffuse); }
	
	public static final Material BLACK   = new Material(Color.BLACK);
	
	public static Material matte (Color  c) { return BLACK.diffuse(c); }
	public static Material matte (double k) { return matte(Color.gray(k)); }
	public static Material matte (        ) { return matte(Color.WHITE); }
	public static final Material MATTE = matte();
}
