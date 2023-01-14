package xyz.marsavic.gfxlab;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.gui.UtilsGL;

import java.util.Arrays;


public class MatrixObject<E> implements Matrix<E> {
	
	private final Vector size;
	private final E[][] data;
	
	
	public MatrixObject(Vector size, E initialValue) {
		this.size = size.floor();
		//noinspection unchecked
		data = (E[][]) new Object[this.size.yInt()][this.size.xInt()];
		
		if (initialValue != null) {
			fill(initialValue);
		}
	}
	
	
	public MatrixObject(Vector size) {
		this(size, null);
	}
	
	
	@Override
	public Vector size() {
		return size;
	}
	
	
	@Override
	public E get(int x, int y) {
		return data[y][x];
	}
	
	@Override
	public void set(int x, int y, E value) {
		data[y][x] = value;
	}
	
	
	@Override
	public void fill(E value) {
		UtilsGL.parallel(data.length, y -> Arrays.fill(data[y], value));
	}
	
}
