package xyz.marsavic.gfxlab;

import xyz.marsavic.functions.F1;
import xyz.marsavic.functions.F2;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.gui.UtilsGL;


public interface Matrix<E> {
	
	Vector size();
	
	
	E get(int x, int y);
	
	
	default E get(Vector p) {
		return get(p.xInt(), p.yInt());
	}

	
	void set(int x, int y, E value);
	
	
	default void set(Vector p, E value) {
		set(p.xInt(), p.yInt(), value);
	}
	
	
	default void fill(E value) {
		int sizeX = size().xInt();
		UtilsGL.parallelY(size(), y -> {
			for (int x = 0; x < sizeX; x++) {
				set(x, y, value);
			}
		});
	}
	
	
	default void fill(F1<E, Vector> f) {
		int sizeX = size().xInt();
		UtilsGL.parallelY(size(), y -> {
			for (int x = 0; x < sizeX; x++) {
				set(x, y, f.at(Vector.xy(x, y)));
			}
		});

//		UtilsGL.parallel(size(), p -> set(p, f.at(p))); // prettier but slower
	}
	

	default void fill(F2<E, Integer, Integer> f) {
		int sizeX = size().xInt();
		UtilsGL.parallelY(size(), y -> {
			for (int x = 0; x < sizeX; x++) {
				set(x, y, f.at(x, y));
			}
		});

//		UtilsGL.parallel(size(), p -> set(p, f.at(p))); // prettier but slower
	}
	
	
	default void copyFrom(Matrix<E> o) {
		Vector size = Matrix.assertEqualSizes(this, o);
		
		int sizeX = size.xInt();
		UtilsGL.parallelY(size, y -> {
			for (int x = 0; x < sizeX; x++) {
				set(x, y, o.get(x, y));
			}
		});
		
		// A pretty equivalent: UtilsGL.parallelYVec(size, p -> set(p, o.get(p)));
	}
	
	// ...........................
	
	
	static void assertSize(Matrix<?> a, Vector size) {
		if (!a.size().equals(size)) {
			throw new IllegalArgumentException("Matrix is not of the designated size.");
		}
	}
	
	static Vector assertEqualSizes(Matrix<?> a, Matrix<?> b) {
		if (!b.size().equals(a.size())) {
			throw new IllegalArgumentException("Matrix sizes are not equal.");
		}
		return a.size();
	}
	
	
	// TODO delete
	static Matrix<Color> createBlack(Vector size) {
		return new MatrixObject<>(size, Color.BLACK);
	}
	
	
	static void add(Matrix<Color> a, Matrix<Color> b, Matrix<Color> result) {
		Vector size = Matrix.assertEqualSizes(a, result);
		
		int sizeX = size.xInt();
		UtilsGL.parallelY(size, y -> {
			for (int x = 0; x < sizeX; x++) {
				result.set(x, y, a.get(x, y).add(b.get(x, y)));
			}
		});
	}
	
	
	static Matrix<Color> add(Matrix<Color> a, Matrix<Color> b) {
		Matrix<Color> result = new MatrixColor(a.size());
		add(a, b, result);
		return result;
	}
	
	
	
	static void addInPlace(Matrix<Color> toChange, Matrix<Color> byHowMuch) {
		Vector size = assertEqualSizes(toChange, byHowMuch);

		int sizeX = size.xInt();
		UtilsGL.parallelY(size, y -> {
			for (int x = 0; x < sizeX; x++) {
				toChange.set(x, y, toChange.get(x, y).add(byHowMuch.get(x, y)));
			}
		});
	}
	
	
	static void mul(Matrix<Color> a, double k, Matrix<Color> result) {
		Vector size = Matrix.assertEqualSizes(a, result);
		
		int sizeX = size.xInt();
		UtilsGL.parallelY(size, y -> {
			for (int x = 0; x < sizeX; x++) {
				result.set(x, y, a.get(x, y).mul(k));
			}
		});
	}
	
	
	static Matrix<Color> mul(Matrix<Color> a, double k) {
		Matrix<Color> result = new MatrixColor(a.size());
		mul(a, k, result);
		return result;
	}
	
	
	static void mulInPlace(Matrix<Color> toChange, double factor) {
		Vector size = toChange.size();
		
		int sizeX = size.xInt();
		UtilsGL.parallelY(size, y -> {
			for (int x = 0; x < sizeX; x++) {
				toChange.set(x, y, toChange.get(x, y).mul(factor));
			}
		});
	}
	
}
