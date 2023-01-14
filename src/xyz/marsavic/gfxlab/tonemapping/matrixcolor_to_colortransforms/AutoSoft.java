package xyz.marsavic.gfxlab.tonemapping.matrixcolor_to_colortransforms;

import xyz.marsavic.functions.F1;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Matrix;
import xyz.marsavic.gfxlab.gui.UtilsGL;
import xyz.marsavic.gfxlab.tonemapping.ColorTransform;


// TODO
public class AutoSoft implements F1<ColorTransform, Matrix<Color>> {
	
	private final double preFactor;
	private final double power;
	private final double postFactor = 1.0;
	private final boolean autoPostFactor = true;
	
	
	public AutoSoft(double preFactor, double power) {
		this.preFactor = preFactor;
		this.power = power;
	}
	
	
	private double lFactor(double lSrc) {
		double lPre = lSrc * preFactor;
		double lDst = 1 - 1 / (1 + Math.pow(lPre, power));
		
		double f = lDst / lSrc;
		if (Double.isNaN(f)) {
			f = 0;
		}
		return f;
	}
	
	
	@Override
	public ColorTransform at(Matrix<Color> colorMatrix) {
		Vector size = colorMatrix.size();
		
		double postFactor_;
		
		if (autoPostFactor) {
			double[] maxY = new double[size.xInt()];
			
			UtilsGL.parallelY(size, y -> {
				maxY[y] = Double.NEGATIVE_INFINITY;
				for (int x = 0; x < size.xInt(); x++) {
					Color c = colorMatrix.get(x, y);
					Color result = c.mul(lFactor(c.luminance()));
					maxY[y] = Math.max(maxY[y], result.max());
				}
			});
			
			// TODO Replace with fork-join task.
			
			double max = Double.NEGATIVE_INFINITY;
			for (int y = 0; y < size.yInt(); y++) {
				max = Math.max(max, maxY[y]);
			}
			
			postFactor_ = 1 / max;
		} else {
			postFactor_ = postFactor;
		}
		
		return color -> color.mul(lFactor(color.luminance()) * postFactor_);
	}
	
}
