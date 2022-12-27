package xyz.marsavic.gfxlab.graphics3d.scene;

import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.*;
import xyz.marsavic.gfxlab.graphics3d.solids.Ball;
import xyz.marsavic.gfxlab.graphics3d.solids.Group;
import xyz.marsavic.gfxlab.graphics3d.solids.HalfSpace;
import xyz.marsavic.gfxlab.graphics3d.textures.Grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class TransformTest extends Scene.Base {
	
	public TransformTest() {
		var materialUVWalls  = Grid.standard(Color.WHITE);
		var materialUVWallsL = Grid.standard(Color.hsb(0.00, 0.5, 0.8));
		var materialUVWallsR = Grid.standard(Color.hsb(0.33, 0.5, 0.8));
		
		Collection<Solid> solids = new ArrayList<>();
		Collections.addAll(solids,
				HalfSpace.pn(Vec3.xyz(-1,  0,  0), Vec3.xyz( 1,  0,  0), materialUVWallsL),
				HalfSpace.pn(Vec3.xyz( 1,  0,  0), Vec3.xyz(-1,  0,  0), materialUVWallsR),
				HalfSpace.pn(Vec3.xyz( 0, -1,  0), Vec3.xyz( 0,  1,  0), materialUVWalls),
				HalfSpace.pn(Vec3.xyz( 0,  1,  0), Vec3.xyz( 0, -1,  0), materialUVWalls),
				HalfSpace.pn(Vec3.xyz( 0,  0,  1), Vec3.xyz( 0,  0, -1), materialUVWalls),
				
				Ball.cr(Vec3.xyz(0,  0,  0.0), 0.6, Material.GLASS)
						.transformed(Affine.IDENTITY
								.then(Affine.scaling(Vec3.xyz(0.3, 1, 1)))
								.then(Affine.rotationAboutZ(0.2))
								.then(Affine.rotationAboutX(-0.1))
						)
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
