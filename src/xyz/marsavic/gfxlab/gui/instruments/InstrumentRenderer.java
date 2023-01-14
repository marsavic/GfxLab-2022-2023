package xyz.marsavic.gfxlab.gui.instruments;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import xyz.marsavic.elements.Element;
import xyz.marsavic.functions.F1;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Matrix;
import xyz.marsavic.gfxlab.gui.UtilsGL;
import xyz.marsavic.objectinstruments.instruments.ObjectInstrument;
import xyz.marsavic.resources.Resource;
import xyz.marsavic.resources.ResourceManagerMap;
import xyz.marsavic.time.Profiler;

import java.util.concurrent.Future;


/** Calls the renderer at each pulse if the previous renderer call has returned. The call happens in another thread,
 * not in the JavaFX Application Thread in which the update method is called. */
public class InstrumentRenderer extends ObjectInstrument<Element.Output<F1<Resource<Matrix<Integer>>, Double>>> {
	
	private final Pane pane;
	private final ImageView imageView;
	
	public InstrumentRenderer(Element.Output<F1<Resource<Matrix<Integer>>, Double>> outRenderer) {
		imageView = new ImageView();
		pane = new VBox(imageView);
		setObject(outRenderer);
	}
	
	@Override
	public Region node() {
		return pane;
	}
	
	
	private Future<?> future = null;
	
	@Override
	public synchronized void update() {
		if (future != null && future.isDone()) {
			future = null;
		}
		if (future == null) {
			future = UtilsGL.submitTask(this::fetch);
		}
	}
	
	
	private final Profiler profilerFetch = UtilsGL.profiler(this, "fetch");
	private final ResourceManagerMap<Vector, WritableImage> images = new ResourceManagerMap<>(UtilsGL::createWritableImage, null);
	private int iFrameNext = 0;
	
	
	private void fetch() {
		profilerFetch.enter();
		Resource<Matrix<Integer>> rMI = object().get().at((double) iFrameNext++);
		
		rMI.a(mI -> {
			Resource<WritableImage> rImage = images.borrow(mI.size(), true);
			rImage.a(image -> UtilsGL.writeMatrixToImage(image, mI));
			Platform.runLater(() -> rImage.a(imageView::setImage));
		});
		profilerFetch.exit();
	}
	
}
