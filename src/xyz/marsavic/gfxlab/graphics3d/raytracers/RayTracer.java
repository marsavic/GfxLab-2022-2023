package xyz.marsavic.gfxlab.graphics3d.raytracers;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.ColorFunctionT;
import xyz.marsavic.gfxlab.graphics3d.Camera;
import xyz.marsavic.gfxlab.graphics3d.Ray;
import xyz.marsavic.gfxlab.graphics3d.Scene;


public abstract class RayTracer implements ColorFunctionT {
	
	protected final Scene scene;
	protected final Camera camera;

	
	public RayTracer(Scene scene, Camera camera) {
		this.scene = scene;
		this.camera = camera;
	}
	
	
	protected abstract Color sample(Ray r);
	
	@Override
	public Color at(double t, Vector p) {
		Ray ray = camera.exitingRay(p);
		return sample(ray);
	}
	
}
