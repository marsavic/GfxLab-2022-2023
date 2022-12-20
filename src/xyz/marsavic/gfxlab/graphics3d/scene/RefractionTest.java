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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class RefractionTest extends Scene.Base {
	
	public RefractionTest() {
		var materialUVWalls  = Grid.standard(Color.WHITE);
		var materialUVWallsL = Grid.standard(Color.hsb(0.00, 0.5, 1.0));
		var materialUVWallsR = Grid.standard(Color.hsb(0.33, 0.5, 1.0));
		
		Collection<Solid> solids = new ArrayList<>();
		Collections.addAll(solids,
				HalfSpace.pn(Vec3.xyz(-1,  0,  0), Vec3.xyz( 1,  0,  0), materialUVWallsL),
				HalfSpace.pn(Vec3.xyz( 1,  0,  0), Vec3.xyz(-1,  0,  0), materialUVWallsR),
				HalfSpace.pn(Vec3.xyz( 0, -1,  0), Vec3.xyz( 0,  1,  0), materialUVWalls),
				HalfSpace.pn(Vec3.xyz( 0,  1,  0), Vec3.xyz( 0, -1,  0), materialUVWalls),
				HalfSpace.pn(Vec3.xyz( 0,  0,  1), Vec3.xyz( 0,  0, -1), materialUVWalls),
				
				Ball.cr(Vec3.xyz(-0.3,  0.3,  0.0), 0.4, uv -> Material.GLASS.refractive(Color.hsb(0.7, 0.2, 1.0))),
				Ball.cr(Vec3.xyz( 0.4, -0.4,  0.0), 0.4, uv -> Material.GLASS),
				Ball.cr(Vec3.xyz(-0.3, -0.4, -0.6), 0.4, uv -> Material.GLASS.refractiveIndex(2.5)),
				Ball.cr(Vec3.xyz( 0.4,  0.3,  0.6), 0.4, uv -> Material.GLASS.refractiveIndex(0.6))
		);
		
		Collections.addAll(lights,
				Light.pc(Vec3.xyz(-0.7, 0.7, -0.7), Color.WHITE),
				Light.pc(Vec3.xyz(-0.7, 0.7,  0.7), Color.WHITE),
				Light.pc(Vec3.xyz( 0.7, 0.7, -0.7), Color.WHITE),
				Light.pc(Vec3.xyz( 0.7, 0.7,  0.7), Color.WHITE)
		);
		
		solid = Group.of(solids);
	}
	
}
