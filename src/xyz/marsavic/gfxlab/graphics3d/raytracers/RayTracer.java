package xyz.marsavic.gfxlab.graphics3d.raytracers;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.ColorFunctionT;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Ray;
import xyz.marsavic.gfxlab.graphics3d.Scene;


public abstract class RayTracer implements ColorFunctionT {
	
	protected final Scene scene;

	
	public RayTracer(Scene scene) {
		this.scene = scene;
	}
	
	
	protected abstract Color sample(Ray r);
	
	@Override
	public Color at(double t, Vector p) {
		Ray ray = Ray.pd(Vec3.xyz(0, 0, -2.6), Vec3.zp(1.6, p));
		return sample(ray);
	}
	
}
