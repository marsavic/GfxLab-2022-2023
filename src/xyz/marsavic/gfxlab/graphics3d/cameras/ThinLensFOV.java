package xyz.marsavic.gfxlab.graphics3d.cameras;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Camera;
import xyz.marsavic.gfxlab.graphics3d.Ray;
import xyz.marsavic.random.sampling.Sampler;
import xyz.marsavic.utils.Numeric;


public class ThinLensFOV implements Camera {
	
	private final double k;
	private final double focalDistance, lensRadius;
	private final Sampler sampler = new Sampler(0xD58B4940DF07A567L);
	
	
	public ThinLensFOV(double k, double focalDistance, double lensRadius) {
		this.k = k;
		this.focalDistance = focalDistance;
		this.lensRadius = lensRadius;
	}
	
	
	public static ThinLensFOV fov(double fovAngle, double focalDistance, double lensRadius) {
		return new ThinLensFOV(Numeric.tanT(fovAngle / 2), focalDistance, lensRadius);
	}
	
	
	@Override
	public Ray exitingRay(Vector pSensor) {
		Vec3 p = Vec3.zp(0, sampler.randomInDisk(lensRadius));
		Vec3 q = Vec3.zp(1, pSensor.mul(k)).mul(focalDistance);
		return Ray.pq(p, q);
	}
	
}
