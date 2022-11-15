package xyz.marsavic.gfxlab;


import xyz.marsavic.geometry.Vector;

import java.util.Arrays;


public final class MatrixInts implements Matrix<Integer> {
	
	private final int width;
	private final int[] data;   // TODO test an implementation with int[][] and compare performances.
	
	
	
	public MatrixInts(Vector size) {
		this.width = size.xInt();
		this.data = new int[size.areaInt()];
	}
	
	
	public int height() {
		return array().length / width;
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
	
	
	public void copyFrom(MatrixInts source) {
		Matrix.assertEqualSizes(this, source);
		System.arraycopy(source.array(), 0, array(), 0, width);
	}
	
	
	@Override
	public void fill(Integer value) {
		Arrays.fill(data, value);   // Optimize: Parallelism on blocks might be faster?
	}
	
	
	public int[] array() {
		return data;
	}
	
}
