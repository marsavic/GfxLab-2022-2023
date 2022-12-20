package xyz.marsavic.gfxlab.graphics3d;


public interface Solid {
	
	/**
	 * Returns the first hit of the ray into the surface of the solid, occurring strictly after the given time.
	 * The default implementation is based on hits method, but implementations of Solid can choose to override
	 * this method to increase performance when only the first hit is needed.
	 * If there is no hit, returns null.
	 */
	Hit firstHit(Ray ray, double afterTime);
	
	
	default Hit firstHit(Ray ray) {
		return firstHit(ray, 0);
	}
	
	
	default boolean hitBetween(Ray ray, double afterTime, double beforeTime) {
		Hit hit = firstHit(ray);
		if (hit == null) return false;
		double t = hit.t();
		return (afterTime < t) && (t < beforeTime);
	}
	
}
