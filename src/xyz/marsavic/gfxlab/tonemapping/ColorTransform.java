package xyz.marsavic.gfxlab.tonemapping;

import xyz.marsavic.functions.F1;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Matrix;


public interface ColorTransform extends F1<Color, Color> {
	
	default F1<ColorTransform, Matrix<Color>> asColorTransformFromMatrixColor() {
		return colorMatrix -> ColorTransform.this;
	}
	
}

