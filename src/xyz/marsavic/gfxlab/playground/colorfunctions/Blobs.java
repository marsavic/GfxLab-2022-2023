package xyz.marsavic.gfxlab.playground.colorfunctions;

import xyz.marsavic.geometry.Box;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.ColorFunctionT;
import xyz.marsavic.utils.Defaults;
import xyz.marsavic.gfxlab.elements.Immutable;
import xyz.marsavic.random.sampling.Sampler;


@Immutable
public class Blobs implements ColorFunctionT {

	private final int n;
	private final double m;
	private final double threshold;
	
	
	private final Vector[] o, c;

	
	
	public static final Defaults<Blobs> $ = Defaults.args(5, 0.1, 0.2);

	public Blobs(int n, double m, double threshold) {
		this.n = n;
		this.m = m;
		this.threshold = threshold;
		
		o = new Vector[n];
		c = new Vector[n];
		
		Sampler sampler = new Sampler(-923147595275902678L);
		for (int i = 0; i < n; i++) {
			o[i] = sampler.randomInBox(Box.cr(10)).round();
			c[i] = sampler.randomInBox();
		}
	}
	
	
	@Override
	public Color at(double t, Vector p) {
		double s = 0;
		for (int i = 0; i < n; i++) {
			Vector c = Vector.polar(o[i].mul(t).add(this.c[i])).mul(0.5);
			double d = p.sub(c).lengthSquared();
			s += Math.exp(-d / m);
		}
		double k = 0.3 * s - threshold;
		return (k < 0 ? Color.rgb(0.3, 0.0, 0.4) : Color.rgb(0.9, 0.8, 0.1)).mul(Math.abs(k));
	}
	
}
