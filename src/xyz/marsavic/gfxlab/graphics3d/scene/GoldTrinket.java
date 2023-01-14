package xyz.marsavic.gfxlab.graphics3d.scene;

import xyz.marsavic.functions.F1;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;
import xyz.marsavic.gfxlab.graphics3d.*;
import xyz.marsavic.gfxlab.graphics3d.solids.Ball;
import xyz.marsavic.gfxlab.graphics3d.solids.Box;
import xyz.marsavic.gfxlab.graphics3d.solids.Group;
import xyz.marsavic.gfxlab.graphics3d.solids.HalfSpace;
import xyz.marsavic.utils.Numeric;

import java.util.Collections;


public class GoldTrinket extends Scene.Base {
	
	public GoldTrinket() {
		Color cl = Color.hsb(0.16, 0.5, 0.9);
		Material m = Material.mirror(cl).diffuse(cl.mul(0.02));
		
		Solid sA = Box.$.r(0.66)
				.material(m)
				.transformed(Affine.IDENTITY
						.then(Affine.rotationAboutX(0.1))
						.then(Affine.rotationAboutY(0.1))
				);
		Solid sB = Ball.cr(Vec3.xyz(0, 0, 0), 0.80, m);
		Solid sC = Ball.cr(Vec3.xyz(0, 0, 0), 0.88, m);
		Solid s = Solid.intersection(Solid.difference(sA, sB), sC);
		
		
		F1<Material, Vector> materialUVWalls = uv -> Material.matte((1 + Numeric.cosT(Numeric.sinT(uv.y()) + uv.x())) * (1 + Numeric.cosT(Numeric.sinT(uv.x()) + uv.y())) / 4);
		
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
