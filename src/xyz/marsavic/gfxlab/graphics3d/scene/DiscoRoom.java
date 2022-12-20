package xyz.marsavic.gfxlab.graphics3d.scene;

import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Light;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Scene;
import xyz.marsavic.gfxlab.graphics3d.Solid;
import xyz.marsavic.gfxlab.graphics3d.solids.Ball;
import xyz.marsavic.gfxlab.graphics3d.solids.Group;
import xyz.marsavic.gfxlab.graphics3d.solids.HalfSpace;
import xyz.marsavic.gfxlab.graphics3d.textures.Grid;
import xyz.marsavic.random.RNG;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class DiscoRoom extends Scene.Base {
	
	public DiscoRoom(int nBalls, int nLights, long seed) {
		RNG rngBalls  = new RNG(2*seed);
		
		var materialUVWalls = Grid.standard(Color.WHITE);
		
		Collection<Solid> solids = new ArrayList<>();
		Collections.addAll(solids,
				HalfSpace.pn(Vec3.xyz(-1,  0,  0), Vec3.xyz( 1,  0,  0), materialUVWalls),
				HalfSpace.pn(Vec3.xyz( 1,  0,  0), Vec3.xyz(-1,  0,  0), materialUVWalls),
				HalfSpace.pn(Vec3.xyz( 0, -1,  0), Vec3.xyz( 0,  1,  0), materialUVWalls),
				HalfSpace.pn(Vec3.xyz( 0,  1,  0), Vec3.xyz( 0, -1,  0), materialUVWalls),
				HalfSpace.pn(Vec3.xyz( 0,  0,  1), Vec3.xyz( 0,  0, -1), materialUVWalls)
		);
		
		for (int i = 0; i < nBalls; i++) {
			double hue = rngBalls.nextDouble();
			Material material = rngBalls.nextDouble() < 0.8 ?
					Material.matte(Color.hsb(hue, 0.9, 0.9)).specular(Color.WHITE).shininess(16) :
					Material.MIRROR;
			
			solids.add(Ball.cr(Vec3.random(rngBalls).ZOtoMP(), 0.2, uv -> material));
		}
		
		solid = Group.of(solids);
		
		
		RNG rngLights = new RNG(2*seed + 1);
		
		for (int i = 0; i < nLights; i++) {
			lights.add(Light.pc(
					Vec3.random(rngLights).ZOtoMP(),
					Color.hsb(rngLights.nextDouble(), 0.75, 1))
			);
		}
	}
	
}
