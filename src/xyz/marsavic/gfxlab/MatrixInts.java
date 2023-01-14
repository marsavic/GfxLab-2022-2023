package xyz.marsavic.gfxlab;


import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.gui.UtilsGL;

import java.util.Arrays;


public final class MatrixInts implements Matrix<Integer> {
	
	private final int width, height;
	private final int[] data;   // TODO test an implementation with int[][] and compare performances.
	
	
	
	public MatrixInts(Vector size) {
		width = size.xInt();
		height = size.yInt();
		data = new int[width * height];
	}
	
	
	public int height() {
		return height;
	}
	
	
	public int width() {
		return width;
	}
	
	
	@Override
	public Vector size() {
		return Vector.xy(width(), height());
	}
	
	
	@Override
	public Integer get(int x, int y) {
		return data[y * width + x];
	}
	
	
	@Override
	public void set(int x, int y, Integer value) {
		data[y * width + x] = value;
	}
	
	
	public void copyFrom(Matrix<Integer> source) {
		Matrix.assertEqualSizes(this, source);
		
		if (source instanceof MatrixInts m) {
			System.arraycopy(m.array(), 0, data, 0, m.array().length);
/*
			// Optimize: Test if doing it in parallel is faster.
			UtilsGL.parallel(height, y -> {
				int o = y * width;
				System.arraycopy(m.array(), o, data, o, width);
			});
*/
		} else {
			UtilsGL.parallel(height, y -> {
				int o = y * width;
				for (int x = 0; x < width; x++) {
					data[o++] = source.get(x, y);
				}
			});
		}
	}
	
	
	@Override
	public void fill(Integer value) {
		Arrays.fill(data, value);   // Optimize: Parallelism on blocks might be faster?
	}
	
	
	public int[] array() {
		return data;
	}
	
}
