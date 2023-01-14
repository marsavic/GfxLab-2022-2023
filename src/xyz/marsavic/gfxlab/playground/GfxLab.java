package xyz.marsavic.gfxlab.playground;

import xyz.marsavic.elements.HasOutput;
import xyz.marsavic.functions.A2;
import xyz.marsavic.functions.A3;
import xyz.marsavic.functions.F1;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.*;
import xyz.marsavic.gfxlab.elements.EAggregator;
import xyz.marsavic.gfxlab.graphics3d.Affine;
import xyz.marsavic.gfxlab.graphics3d.cameras.Perspective;
import xyz.marsavic.gfxlab.graphics3d.cameras.ThinLensFOV;
import xyz.marsavic.gfxlab.graphics3d.cameras.TransformedCamera;
import xyz.marsavic.gfxlab.graphics3d.raytracers.PathTracer;
import xyz.marsavic.gfxlab.graphics3d.raytracers.RayTracerSimple;
import xyz.marsavic.gfxlab.graphics3d.scene.CityOfNight;
import xyz.marsavic.gfxlab.graphics3d.scene.GITest;
import xyz.marsavic.gfxlab.graphics3d.scene.GoldTrinket;
import xyz.marsavic.gfxlab.graphics3d.scene.Oranges;
import xyz.marsavic.gfxlab.gui.UtilsGL;
import xyz.marsavic.gfxlab.tonemapping.ColorTransform;
import xyz.marsavic.gfxlab.tonemapping.ToneMapping;
import xyz.marsavic.gfxlab.tonemapping.colortransforms.Multiply;
import xyz.marsavic.gfxlab.tonemapping.matrixcolor_to_colortransforms.AutoSoft;
import xyz.marsavic.random.RNG;
import xyz.marsavic.random.fixed.noise.MapToRndNumber;
import xyz.marsavic.resources.Resource;

import static xyz.marsavic.elements.ElementF.e;


public class GfxLab {
	
	public GfxLab() {
		pathTracing();
	}
	
	public HasOutput<F1<Resource<Matrix<Integer>>, Double>> sink;


	private void pathTracing() {
		var eSize = e(Vec3.xyz(1200, 640, 640));
		
		sink =
				e(Fs::rToneMapped,
						new EAggregator(
								e(Fs::aFillFrameColorRandomized,
										e(Fs::transformedColorFunction,
/*
												e(RayTracerSimple::new,
														e(GoldTrinket::new),
														e(TransformedCamera::new,
																e(Perspective::new, e(1.0/3)),
																e(Affine.IDENTITY
																			.then(Affine.translation(Vec3.xyz(0, 0, -4)))
																)
														)
												),
*/
/*
												e(PathTracer::new,
														e(GITest::new),
														e(TransformedCamera::new,
																e(Perspective::new, e(1.0/3)),
																e(Affine.IDENTITY
																		.then(Affine.translation(Vec3.xyz(0, 0, -4)))
																)
														),
														e(16)
												),
*/
/*
												e(PathTracer::new,
														e(Oranges::new),
														e(TransformedCamera::new,
																e(Perspective::new, e(1.0/3)),
																e(Affine.IDENTITY
																		.then(Affine.rotationAboutX(0.025))
																		.then(Affine.translation(Vec3.xyz(0, 0, -3)))
																		.then(Affine.rotationAboutX(0.04))
																		.then(Affine.rotationAboutY(0.11))
																)
														),
														e(16)
												),
*/
												e(PathTracer::new,
														e(CityOfNight::new, e(50), e(0x3B660712F3CFA050L)),
														e(TransformedCamera::new,
																e(ThinLensFOV::new, e(1.0/3), e(7.0), e(0.1)),
																e(Affine.IDENTITY
																		.then(Affine.translation(Vec3.xyz(0, 0, -7)))
																		.then(Affine.rotationAboutX(0.12))
																		.then(Affine.rotationAboutY(-0.1))
																)
														),
														e(16)
												),
										
												e(TransformationsFromSize.toGeometric, eSize)
										)
								),
								eSize,
								e(0xA6A08E5C173D29FL)
						),
						e(Fs::toneMapping,
								e(AutoSoft::new, e(0x1p-4), e(1.0))
						)
				);
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
			output.fill((x, y) -> f.at(input.get(x, y)).codeClamp());
		};
	}

	public static A3<Matrix<Color>, Integer, Long> aFillFrameColorRandomized(ColorFunction colorFunction) {
		return (result, t, seed) -> {
			MapToRndVec3 rnd = new MapToRndVec3(seed);
			result.fill(p -> {
				Vec3 v = Vec3.xp(t, p);
				return colorFunction.at(v.add(rnd.get(v)));
			});
		};
	}
	
	public static F1<Resource<Matrix<Integer>>, Double> rToneMapped(F1<Resource<Matrix<Color>>, Double> frFrame, ToneMapping toneMapping) {
		return t -> {
			return frFrame.at(t).f(mC -> {
				Resource<Matrix<Integer>> rMatI = UtilsGL.matricesInt.borrow(mC.size(), true);
				rMatI.a(mI -> toneMapping.apply(mC, mI));
				return rMatI;
			});
		};
	}
	
}
