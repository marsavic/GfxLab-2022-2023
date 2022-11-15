package xyz.marsavic.gfxlab.graphics3d;


import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.random.sampling.Sampler;
import xyz.marsavic.utils.Numeric;

public class GeometryUtils {
	
	/** An orthogonal vector to v. */
	public static Vec3 normal(Vec3 v) {
		if (v.x() != 0 || v.y() != 0) {
			return Vec3.xyz(-v.y(), v.x(), 0);
		} else {
			return Vec3.EX;
		}
	}
	
/*
	public static Vec3 normal(Vec3 v) {
		Vec3 p = v.cross(Vec3.EX);
		if (p.allZero()) {
			p = v.cross(Vec3.EY);
		}
		return p;
	}
*/

}
