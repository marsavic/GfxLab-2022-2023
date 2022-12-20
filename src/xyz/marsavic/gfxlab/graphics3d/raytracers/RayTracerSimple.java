package xyz.marsavic.gfxlab.graphics3d.raytracers;

import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.*;


public class RayTracerSimple extends RayTracer {
	
	private static final double EPSILON = 1e-9;
	
	public RayTracerSimple(Scene scene, Camera camera) {
		super(scene, camera);
	}
	
	@Override
	protected Color sample(Ray ray) {
		return sample(ray, 64);
	}
	
	protected Color sample(Ray ray, int depthRemaining) {
		if (depthRemaining == 0) {
			return Color.BLACK;
		}
		
		Hit hit = scene.solid().firstHit(ray, EPSILON);
		if (hit == null) {
			return scene.colorBackground();
		}
		
		Vec3 p = ray.at(hit.t());                 // The hit point
		Vec3 n_ = hit.n_();                       // Normalized normal to the body surface at the hit point
		Vec3 i = ray.d().inverse();               // Incoming direction
		double lI = i.length();
		Vec3 r = GeometryUtils.reflectedN(n_, i); // Reflected ray (i reflected over n)
		Vec3 r_ = r.div(lI);                      // Reflected ray (i reflected over n)
		
		Color lightDiffuse  = Color.BLACK;        // The sum of diffuse contributions from all the lights
		Color lightSpecular = Color.BLACK;        // The sum of specular contributions from all the lights
		
		Material material = hit.material();
		
		for (Light light : scene.lights()) {
			Vec3 l = light.p().sub(p);            // Vector from p to the light;
			
			Ray rayToLight = Ray.pd(p, l);
			if (scene.solid().hitBetween(rayToLight, EPSILON, 1)) continue;
			
			double lLSqr = l.lengthSquared();     // Distance from p to the light squared
			double lL = Math.sqrt(lLSqr);         // Distance from p to the light
			double cosLN = n_.dot(l) / lL;        // Cosine of the angle between l and n_
			
			if (cosLN > 0) {                      // If the light is above the surface
				Color irradiance = light.c().mul(cosLN / lLSqr);
				// The irradiance represents how much light is received by a unit area of the surface. It is
				// proportional to the cosine of the incoming angle and inversely proportional to the distance squared
				// (inverse-square law).
				lightDiffuse = lightDiffuse.add(irradiance);
				
				double cosLR = l.dot(r_);
				if (cosLR > 0) {                  // If the angle between l and r is acute
					cosLR /= lL;
					lightSpecular = lightSpecular.add(irradiance.mul(Math.pow(cosLR, material.shininess())));
				}
			}
		}
		
		Color result = Color.BLACK;
		result = result.add(material.diffuse ().mul(lightDiffuse ));
		result = result.add(material.specular().mul(lightSpecular));
		
		if (material.reflective().notZero()) {
			// When material has reflective properties we recursively find the color visible along the ray (p, r).
			Color lightReflected = sample(Ray.pd(p, r), depthRemaining - 1);
			result = result.add(material.reflective().mul(lightReflected));
		}
		
		return result;
	}
	
}
