package xyz.marsavic.gfxlab.graphics3d.scene;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Scene;
import xyz.marsavic.gfxlab.graphics3d.Solid;
import xyz.marsavic.gfxlab.graphics3d.solids.Box;
import xyz.marsavic.gfxlab.graphics3d.solids.Group;
import xyz.marsavic.gfxlab.graphics3d.solids.HalfSpace;
import xyz.marsavic.random.sampling.Sampler;

import java.util.ArrayList;
import java.util.List;


public class CityOfNight extends Scene.Base {
	
	public CityOfNight(int n, long seed) {
		List<Solid> solids = new ArrayList<>();
		Sampler sampler = new Sampler(seed);
		
		List<xyz.marsavic.geometry.Box> boxes = new ArrayList<>();
		
		int nTrials = 0;
		
		while (boxes.size() < n && nTrials < 10000) {
			nTrials++;
			Vector c = sampler.randomInBox(xyz.marsavic.geometry.Box.cr(3));
			Vector r = Vector.xy(sampler.uniform(0.05, 0.5), sampler.uniform(0.05, 0.5));
			xyz.marsavic.geometry.Box bCandidate = xyz.marsavic.geometry.Box.cr(c, r);
			
			if (boxes.stream().noneMatch(b -> b.intersects(bCandidate))) {
				boxes.add(bCandidate.extend(0.05));
				
				Material material =
						sampler.uniform() < 0.1 ?
								Material.LIGHT :
								sampler.uniform() < 0.1 ?
										Material.mirror(0.75) :
										Material.matte(Color.hsb(sampler.uniform(360), 0.6, 0.8));
				
				double h = sampler.exponential(1);
				solids.add(Box.$.pd(Vec3.yp(0, bCandidate.p()), Vec3.yp(h, bCandidate.d())).material(material));
				
				nTrials = 0;
			}
		}
		
		solids.add(HalfSpace.pn(Vec3.ZERO, Vec3.EY, Material.matte(0.8)));
		
		solid = Group.of(solids);
	}
}
