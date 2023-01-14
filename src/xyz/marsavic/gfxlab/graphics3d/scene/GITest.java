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


public class GITest extends Scene.Base {
	
	public GITest() {
		var mL = Grid.standard(Color.hsb(0.0 / 3, 0.5, 0.7));
		var mR = Grid.standard(Color.hsb(1.0 / 3, 0.5, 0.7));
		var mW = Grid.standard(Color.gray(0.7));
		
		Material glass = new Material(BSDF.mix(BSDF.refractive(1.4), BSDF.REFLECTIVE, 0.05));
		
		Collection<Solid> solids = new ArrayList<>();
		Collections.addAll(solids,
				HalfSpace.pn(Vec3.xyz(-1,  0,  0), Vec3.xyz( 1,  0,  0), mL),
				HalfSpace.pn(Vec3.xyz( 1,  0,  0), Vec3.xyz(-1,  0,  0), mR),
				HalfSpace.pn(Vec3.xyz( 0, -1,  0), Vec3.xyz( 0,  1,  0), mW),
				HalfSpace.pn(Vec3.xyz( 0,  1,  0), Vec3.xyz( 0, -1,  0), Material.LIGHT),
				HalfSpace.pn(Vec3.xyz( 0,  0,  1), Vec3.xyz( 0,  0, -1), mW),
				
				Ball.cr(Vec3.xyz(-0.2, -0.5,  0.0), 0.3, glass),
				Ball.cr(Vec3.xyz( 0.5, -0.5, -0.3), 0.3, Material.MIRROR),
				Ball.cr(Vec3.xyz( 0.0,  0.2,  0.0), 0.2, Material.matte(0.7)),
				Ball.cr(Vec3.xyz(-0.4,  0.5,  0.1), 0.2, Material.mirror(0.9))
		);
		
		solid = Group.of(solids);
	}
	
}
