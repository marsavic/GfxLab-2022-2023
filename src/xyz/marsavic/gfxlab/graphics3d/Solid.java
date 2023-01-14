package xyz.marsavic.gfxlab.graphics3d;


public interface Solid {
	
	/**
	 * Returns the first hit of the ray into the surface of the solid, occurring strictly after the given time.
	 * The default implementation is based on hits method, but implementations of Solid can choose to override
	 * this method to increase performance when only the first hit is needed.
	 * If the ray misses the Solid, the hit at infinity is returned.
	 * All hits along the same line should alternate between entering and exiting hits, meaning the dot product
	 * between the normal at the hit and line direction should alternate its sign. This should hold also for
	 * the hit at infinity.
	 */
	Hit firstHit(Ray ray, double afterTime);
	
	
	default Hit firstHit(Ray ray) {
		return firstHit(ray, 0);
	}
	
	
	default boolean hitBetween(Ray ray, double afterTime, double beforeTime) {
		double t = firstHit(ray).t();
		return (afterTime < t) && (t < beforeTime);
	}

	
	default Solid transformed(Affine t) {
		return new Solid() {
			private final Affine tInv = t.inverse();
			private final Affine tInvT = tInv.transposeWithoutTranslation();
			
			@Override
			public Hit firstHit(Ray ray, double afterTime) {
				Ray rayO = tInv.at(ray);
				Hit hitO = Solid.this.firstHit(rayO, afterTime);
				return hitO.withN(tInvT.at(hitO.n()));
			}
		};
	}
	
	
	/** The solid made of all the points contained in at least k of the given solids. */
	static Solid atLeast(int k, Solid... solids) {
		return (ray, afterTime) -> {
			int n = solids.length;
			
			Hit[] hits = new Hit[n];
			int[] d = new int[n];
			int inCount = 0;
			
			for (int i = 0; i < n; i++) {
				hits[i] = solids[i].firstHit(ray, afterTime);
				boolean in = ray.d().dot(hits[i].n()) > 0;
				d[i] = in ? -1 : 1;
				if (in) {
					inCount += 1;
				}
			}
			
			boolean inResultingSolid = inCount >= k;
			int target = inResultingSolid ? k - 1 : k;
			
			while (true) {
				int iFirst = 0;
				double tFirst = hits[iFirst].t();
				for (int i = 1; i < n; i++) {
					if (hits[i].t() < tFirst) {
						tFirst = hits[i].t();
						iFirst = i;
					}
				}
				Hit hitFirst = hits[iFirst];
				
				if (tFirst == Double.POSITIVE_INFINITY) {
					return Hit.AtInfinity.axisAligned(ray.d(), inResultingSolid);
				}
				
				inCount += d[iFirst];
				d[iFirst] = -d[iFirst];
				if (inCount == target) {
					return hitFirst;
				}
				
				hits[iFirst] = solids[iFirst].firstHit(ray, tFirst);
			}
		};
	}
	
	
	static Solid union(Solid... solids) {
		return atLeast(1, solids);
	}

	static Solid intersection(Solid... solids) {
		return atLeast(solids.length, solids);
	}
	
	static Solid complement(Solid solid) {
		return (ray, afterTime) -> solid.firstHit(ray, afterTime).inverted();
	}
	
	static Solid difference(Solid solidA, Solid solidB) {
		return intersection(solidA, complement(solidB));
	}
	
}
