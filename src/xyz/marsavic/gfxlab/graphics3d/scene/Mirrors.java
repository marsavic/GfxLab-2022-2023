package xyz.marsavic.gfxlab.graphics3d.scene;

import xyz.marsavic.geometry.Vector;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class Mirrors extends Scene.Base {
	
	public Mirrors(int nBalls) {
		var materialUVWalls  = Grid.standard(Color.WHITE);
		var materialUVWallsL = Grid.standard(Color.hsb(0.00, 0.5, 1.0));
		var materialUVWallsR = Grid.standard(Color.hsb(0.33, 0.5, 1.0));
		
		Collection<Solid> solids = new ArrayList<>();
		Collections.addAll(solids,
				HalfSpace.pn(Vec3.xyz(-1,  0,  0), Vec3.xyz( 1,  0,  0), materialUVWallsL),
				HalfSpace.pn(Vec3.xyz( 1,  0,  0), Vec3.xyz(-1,  0,  0), materialUVWallsR),
				HalfSpace.pn(Vec3.xyz( 0, -1,  0), Vec3.xyz( 0,  1,  0), materialUVWalls),
				HalfSpace.pn(Vec3.xyz( 0,  1,  0), Vec3.xyz( 0, -1,  0), materialUVWalls),
				HalfSpace.pn(Vec3.xyz( 0,  0,  1), Vec3.xyz( 0,  0, -1), materialUVWalls)
		);
		
		Collections.addAll(lights,
				Light.pc(Vec3.xyz(-0.8, 0.8, -0.8), Color.WHITE),
				Light.pc(Vec3.xyz(-0.8, 0.8,  0.8), Color.WHITE),
				Light.pc(Vec3.xyz( 0.8, 0.8, -0.8), Color.WHITE),
				Light.pc(Vec3.xyz( 0.8, 0.8,  0.8), Color.WHITE)
		);
		
		for (int i = 0; i < nBalls; i++) {
			Vector c = Vector.polar(0.5, 1.0 * i / nBalls);
			Ball ball = Ball.cr(Vec3.zp(0, c), 0.4, uv -> Material.MIRROR);
			solids.add(ball);
		}
		
		solid = Group.of(solids);
	}
	
}
