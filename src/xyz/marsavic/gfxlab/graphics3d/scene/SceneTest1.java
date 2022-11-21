package xyz.marsavic.gfxlab.graphics3d.scene;

import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.Light;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Scene;
import xyz.marsavic.gfxlab.graphics3d.solids.Ball;
import xyz.marsavic.gfxlab.graphics3d.solids.Group;
import xyz.marsavic.gfxlab.graphics3d.solids.HalfSpace;

import java.util.Collections;


public class SceneTest1 extends Scene.Base{
	
	public SceneTest1() {
		solid = Group.of(
				Ball.cr(Vec3.xyz(0, 0, 0), 1,
						uv -> new Material(Color.hsb(uv.x() * 6, 0.8, uv.y()))
				),
				HalfSpace.pn(Vec3.xyz(0, -1, 0), Vec3.xyz(0, 1, 0),
						uv -> new Material(Color.hsb(uv.x(), 0.8, 0.8))
				)
		);
		
		Collections.addAll(lights,
				Light.pc(Vec3.xyz(-1, 1, -1), Color.hsb(0.0, 1.0, 0.6)),
				Light.pc(Vec3.xyz( 2, 0,  0), Color.gray(0.6)),
				Light.pc(Vec3.xyz( 0, 0,  -2), Color.gray(0.1))
		);
	}
	
}
