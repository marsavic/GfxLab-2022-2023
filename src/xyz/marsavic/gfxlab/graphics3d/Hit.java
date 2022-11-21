package xyz.marsavic.gfxlab.graphics3d;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Vec3;


/** Interaction of a ray with a solid.*/
public interface Hit {
	
	/** The time of the hit. */
	double t();
	
	/** The normal at the hit point. */
	Vec3 n();
	
	/** Surface material at the hit point. */
	Material material();
	
	/** 2D coordinates in the internal coordinate system of the surface. */
	Vector uv();
	
	/** The normalized normal at the point of the hit */
	default Vec3 n_() {
		return n().normalized_();
	}
	

	// =====================================================================================================

	
	static double t(Hit hit) {
		return hit == null ? Double.POSITIVE_INFINITY : hit.t();
	}
	
	
	abstract class RayT implements Hit {
		private final Ray ray;
		private final double t;
		
		protected RayT(Ray ray, double t) {
			this.ray = ray;
			this.t = t;
		}
		
		public Ray ray() {
			return ray;
		}
		
		@Override
		public double t() {
			return t;
		}
	}
	
}
