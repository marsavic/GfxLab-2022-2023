package xyz.marsavic.gfxlab;

import xyz.marsavic.random.RNG;
import xyz.marsavic.random.fixed.noise.MapToRnd;
import xyz.marsavic.random.fixed.noise.MapToRndNumber;


public class MapToRndVec3 extends MapToRnd<Vec3> {
	private final MapToRndNumber map0, map1, map2;
	
	
	public MapToRndVec3(long seed) {
		RNG rng = new RNG(seed);
		map0 = new MapToRndNumber(rng.nextLong());
		map1 = new MapToRndNumber(rng.nextLong());
		map2 = new MapToRndNumber(rng.nextLong());
	}
	
	
	@Override
	public Vec3 get(long key) {
		return Vec3.xyz(
				map0.getDouble(key),
				map1.getDouble(key),
				map2.getDouble(key)
		);
	}
	
}
