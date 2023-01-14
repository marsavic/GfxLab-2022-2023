package xyz.marsavic.gfxlab;

import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.gui.UtilsGL;


public class MatrixColor implements Matrix<Color> {
	
	private final Vector size;
	private final double [][][] data;
	
	
	public MatrixColor(Vector size, Color initialValue) {
		this.size = size.floor();
		data = new double[this.size.yInt()][this.size.xInt()][3];
		
		if (initialValue != null) {
			fill(initialValue);
		}
	}
	
	
	public MatrixColor(Vector size) {
		this(size, null);
	}
	
	
	@Override
	public Vector size() {
		return size;
	}
	
	
	@Override
	public Color get(int x, int y) {
		return Color.rgb(data[y][x][0], data[y][x][1], data[y][x][2]);
	}
	
	
	@Override
	public void set(int x, int y, Color value) {
		data[y][x][0] = value.r;
		data[y][x][1] = value.g;
		data[y][x][2] = value.b;
	}
	
	
	public void fillBlack() {
		UtilsGL.parallelY(size, y -> {
			int w = data[y].length;
			for (int x = 0; x < w; x++) {
				data[y][x][0] = 0;
				data[y][x][1] = 0;
				data[y][x][2] = 0;
			}
		});
	}
	
}
