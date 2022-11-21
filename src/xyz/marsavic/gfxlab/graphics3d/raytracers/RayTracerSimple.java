package xyz.marsavic.gfxlab.graphics3d.raytracers;

import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.*;


public class RayTracerSimple extends RayTracer {
	
	public RayTracerSimple(Scene scene) {
		super(scene);
	}
	
	@Override
	protected Color sample(Ray r) {
		Hit hit = scene.solid().firstHit(r);
		if (hit == null) {
			return scene.colorBackground();
		}
		
		Vec3 p  = r.at(hit.t());               // The hit point
		Vec3 n_ = hit.n_();                    // Normalized normal to the body surface at the hit point
		
		Color lightDiffuse = Color.BLACK;      // The sum of diffuse contributions from all the lights
		
		for (Light light : scene.lights()) {
			Vec3 l = light.p().sub(p);         // Vector from p to the light;
			double lLSqr = l.lengthSquared();  // Distance from p to the light squared
			double lL = Math.sqrt(lLSqr);      // Distance from p to the light
			double cosLN = n_.dot(l) / lL;     // Cosine of the angle between l and n_
			
			if (cosLN > 0) {                   // If the light is above the surface
				Color irradiance = light.c().mul(cosLN / lLSqr);
				// The irradiance represents how much light is received by a unit area of the surface. It is
				// proportional to the cosine of the incoming angle and inversely proportional to the distance squared
				// (inverse-square law).
				lightDiffuse = lightDiffuse.add(irradiance);
			}
		}
		
		return hit.material().diffuse().mul(lightDiffuse);
	}
	
}
