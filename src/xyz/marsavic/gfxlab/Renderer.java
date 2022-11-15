package xyz.marsavic.gfxlab;

import xyz.marsavic.functions.interfaces.A1;
import xyz.marsavic.functions.interfaces.A2;
import xyz.marsavic.gfxlab.gui.UtilsGL;
import xyz.marsavic.utils.Numeric;

public record Renderer(Vec3 size, A2<Matrix<Integer>, Double> aFillFrameInt) {
	
	public void process(double t, A1<Matrix<Integer>> aProcess) {
		UtilsGL.matricesInt.borrow(size.p12(), m -> {
			aFillFrameInt.execute(m, Numeric.mod(t, size.x()));
			aProcess.execute(m);
		});
	}
	
}
