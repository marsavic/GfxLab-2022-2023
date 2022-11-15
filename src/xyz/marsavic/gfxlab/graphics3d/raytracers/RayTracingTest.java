package xyz.marsavic.gfxlab.graphics3d.raytracers;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.ColorFunctionT;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Hit;
import xyz.marsavic.gfxlab.graphics3d.Ray;
import xyz.marsavic.gfxlab.graphics3d.solids.Ball;
import xyz.marsavic.gfxlab.graphics3d.solids.HalfSpace;


public class RayTracingTest implements ColorFunctionT {

	Ball ball = Ball.cr(Vec3.xyz(0, 0, 2), 1);
	HalfSpace halfSpace = HalfSpace.pn(Vec3.xyz(0, -1, 0), Vec3.xyz(0, 1, 0));
	
	@Override
	public Color at(double t, Vector p) {
		Ray ray = Ray.pq(Vec3.ZERO, Vec3.zp(1, p));
		
		Hit hit1 = ball.firstHit(ray);
		Hit hit2 = halfSpace.firstHit(ray);
		
		double tMin = Math.min(Hit.t(hit1), Hit.t(hit2));
		
		return Color.gray(1.0 / (1.0 + tMin));
	}
	
}
