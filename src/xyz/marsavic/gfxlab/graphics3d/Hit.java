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
	
	default Hit withN(Vec3 n) {
		return new Hit() {
			@Override public double   t       () { return Hit.this.t(); }
			@Override public Vec3     n       () { return n; }
			@Override public Material material() { return Hit.this.material(); }
			@Override public Vector   uv      () { return Hit.this.uv(); }
		};
	}
	
	default Hit inverted() {
		return new Hit() {
			@Override public double   t       () { return Hit.this.t (); }
			@Override public Vec3     n       () { return Hit.this.n ().inverse(); }
			@Override public Vec3     n_      () { return Hit.this.n_().inverse(); }
			@Override public Vector   uv      () { return Hit.this.uv(); }
			@Override public Material material() { return Hit.this.material(); }
		};
	}
	
	
	// =====================================================================================================
	

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
	
	
	record AtInfinity(
			double t,
			Vec3 n
	) implements Hit {
		
		@Override public double   t       () { return t; }
		@Override public Vec3     n       () { return n; }
		@Override public Vector   uv      () { return Vector.ZERO; }
		@Override public Material material() { return Material.BLACK; }
		
		
		public static AtInfinity inLine(Vec3 d, boolean future, boolean in) {
			// We don't like calling this often, because it creates a new object. In frequently executed code, call one of the "axisAligned" methods
			return new AtInfinity(
					future ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY,
					in == future ? d : d.inverse()
			);
		}
		
		
		private static final AtInfinity hitAtInfinityXM = new AtInfinity(Double.POSITIVE_INFINITY, Vec3.xyz(-1, 0, 0));
		private static final AtInfinity hitAtInfinityXP = new AtInfinity(Double.POSITIVE_INFINITY, Vec3.xyz( 1, 0, 0));
		private static final AtInfinity hitAtInfinityYM = new AtInfinity(Double.POSITIVE_INFINITY, Vec3.xyz( 0,-1, 0));
		private static final AtInfinity hitAtInfinityYP = new AtInfinity(Double.POSITIVE_INFINITY, Vec3.xyz( 0, 1, 0));
		private static final AtInfinity hitAtInfinityZM = new AtInfinity(Double.POSITIVE_INFINITY, Vec3.xyz( 0, 0,-1));
		private static final AtInfinity hitAtInfinityZP = new AtInfinity(Double.POSITIVE_INFINITY, Vec3.xyz( 0, 0, 1));
		
		public static AtInfinity axisAligned(Vec3 d, boolean in) {
			return in ? axisAlignedIn(d) : axisAlignedOut(d);
		}

		public static AtInfinity axisAlignedIn(Vec3 d) {
			if (d.x() < 0) return hitAtInfinityXM;
			if (d.x() > 0) return hitAtInfinityXP;
			if (d.y() < 0) return hitAtInfinityYM;
			if (d.y() > 0) return hitAtInfinityYP;
			if (d.z() < 0) return hitAtInfinityZM;
			               return hitAtInfinityZP;
		}

		public static AtInfinity axisAlignedOut(Vec3 d) {
			if (d.x() < 0) return hitAtInfinityXP;
			if (d.x() > 0) return hitAtInfinityXM;
			if (d.y() < 0) return hitAtInfinityYP;
			if (d.y() > 0) return hitAtInfinityYM;
			if (d.z() < 0) return hitAtInfinityZP;
			               return hitAtInfinityZM;
		}
		
	}
	
}
