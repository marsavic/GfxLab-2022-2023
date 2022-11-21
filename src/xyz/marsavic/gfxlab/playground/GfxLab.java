package xyz.marsavic.gfxlab.playground;

import xyz.marsavic.functions.interfaces.A2;
import xyz.marsavic.functions.interfaces.F1;
import xyz.marsavic.gfxlab.*;
import xyz.marsavic.gfxlab.elements.Output;
import xyz.marsavic.gfxlab.graphics3d.raytracers.RayTracerSimple;
import xyz.marsavic.gfxlab.graphics3d.scene.SceneTest1;
import xyz.marsavic.gfxlab.gui.UtilsGL;
import xyz.marsavic.gfxlab.tonemapping.ColorTransform;
import xyz.marsavic.gfxlab.tonemapping.ToneMapping;
import xyz.marsavic.gfxlab.tonemapping.colortransforms.Multiply;

import static xyz.marsavic.gfxlab.elements.ElementF.e;
import static xyz.marsavic.gfxlab.elements.Output.val;


public class GfxLab {
	
	public Output<Renderer> outRenderer;
	
	public GfxLab() {
		var eSize = e(Vec3::new, val(1200.0), val(640.0), val(640.0));
		
		var eRenderer =
				e(Renderer::new,
						eSize,
						e(Fs::aFillFrameInt,
								e(Fs::aFillFrameColor,
										e(Fs::transformedColorFunction,
//												e(Blobs::new, val(5), val(0.1), val(0.2)),
												e(RayTracerSimple::new,
														e(SceneTest1::new)
												),
												e(TransformationsFromSize.toGeometric, eSize)
										)
								),
								e(Fs::toneMapping,
										e(ColorTransform::asColorTransformFromMatrixColor,
												e(Multiply::new, val(1.0))
										)
								)
						)
				);
		
		outRenderer = eRenderer.out();
	}
	
}


class Fs {
	
	public static ColorFunction transformedColorFunction(ColorFunction colorFunction, Transformation transformation) {
		return p -> colorFunction.at(transformation.at(p));
	}
	
	public static A2<Matrix<Color>, Double> aFillFrameColor(ColorFunction colorFunction) {
		return (Matrix<Color> result, Double t) -> {
			result.fill(p -> colorFunction.at(t, p));
		};
	}
	
	public static A2<Matrix<Integer>, Double> aFillFrameInt(A2<Matrix<Color>, Double> aFillFrameColor, ToneMapping toneMapping) {
		return (Matrix<Integer> result, Double t) -> {
			UtilsGL.matricesColor.borrow(result.size(), true, mC -> {
				aFillFrameColor.execute(mC, t);
				toneMapping.apply(mC, result);
			});
		};
	}
	
	public static ToneMapping toneMapping(F1<ColorTransform, Matrix<Color>> f_ColorTransform_MatrixColor) {
		return (input, output) -> {
			ColorTransform f = f_ColorTransform_MatrixColor.at(input);
			output.fill(p -> f.at(input.get(p)).codeClamp());
		};
	}
	
}
