package xyz.marsavic.gfxlab.graphics3d.scene;

import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.*;
import xyz.marsavic.gfxlab.graphics3d.solids.Ball;
import xyz.marsavic.gfxlab.graphics3d.solids.Box;
import xyz.marsavic.gfxlab.graphics3d.solids.Group;
import xyz.marsavic.gfxlab.graphics3d.solids.HalfSpace;
import xyz.marsavic.random.RNG;

import java.util.Collections;


public class Obsidian extends Scene.Base {
	
	public Obsidian() {
		Color cl = Color.hsb(0.75, 0.2, 0.9);
		Material m = Material.glass(cl).refractive(cl).specular(cl).diffuse(cl.mul(0.3));
		
		RNG rng = new RNG(0x66883D9C332766FBL);
		
		int nHoles = 32;
		Solid[] holes = new Solid[nHoles];
		for (int i = 0; i < nHoles; i++) {
			holes[i] = Ball.cr(Vec3.random(rng).ZOtoMP(), rng.nextDouble(0.3, 0.6), m);
		}

		Solid s = Solid.difference(
				Box.$.r(0.6).material(m),
				Solid.union(holes)
		).transformed(Affine.IDENTITY
				.then(Affine.rotationAboutX(-0.1))
				.then(Affine.rotationAboutY(0.1))
		);
		
		
		var materialUVWalls  = Material.matte(Color.gray(0.1));
		
		solid = Group.of(
				HalfSpace.pn(Vec3.xyz( 0,  0,  1.6), Vec3.xyz( 0,  0, -1), materialUVWalls),
				s
		);

		double d = 0.6;
		Collections.addAll(lights,
				Light.p(Vec3.xyz(-d,  d, -d)),
				Light.p(Vec3.xyz(-d,  d,  d)),
				Light.p(Vec3.xyz( d,  d, -d)),
				Light.p(Vec3.xyz( d,  d,  d)),
				Light.p(Vec3.xyz(-d, -d, -d)),
				Light.p(Vec3.xyz(-d, -d,  d)),
				Light.p(Vec3.xyz( d, -d, -d)),
				Light.p(Vec3.xyz( d, -d,  d))
		);
		
	}
	
}
