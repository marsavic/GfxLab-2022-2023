package xyz.marsavic.gfxlab.tonemapping;

import xyz.marsavic.functions.interfaces.F1;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Matrix;
import xyz.marsavic.gfxlab.MatrixInts;


public interface ToneMapping extends F1<Matrix<Integer>, Matrix<Color>> {
	void apply(Matrix<Color> input, Matrix<Integer> output);
	
	
	/** A better option is to use ResourceManager to borrow an output Matrix and call apply on it. */
	@Override
	default Matrix<Integer> at(Matrix<Color> input) {
		Matrix<Integer> output = new MatrixInts(input.size());
		apply(input, output);
		return output;
	}
	
}
