package xyz.marsavic.gfxlab.gui.instruments;

import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Renderer;
import xyz.marsavic.gfxlab.elements.Output;
import xyz.marsavic.gfxlab.gui.UtilsGL;
import xyz.marsavic.objectinstruments.instruments.ObjectInstrument;
import xyz.marsavic.time.Profiler;


public class InstrumentRenderer extends ObjectInstrument<Output<Renderer>> {

	private final Pane pane;
	private final ImageView imageView;
	
	private WritableImage image;
	
	private int iFrameNext = 0;
	
	private final Profiler profilerUpdate = UtilsGL.profiler(this, "update");
	
	
	
	public InstrumentRenderer(Output<Renderer> outRenderer) {
		imageView = new ImageView();
		pane = new VBox(imageView);
		
		setObject(outRenderer);
	}
	
	
	
	@Override
	public void update() {
		profilerUpdate.enter();
		object().get().process(iFrameNext++, m -> {
			Vector size = m.size();
			if ((image == null) || !UtilsGL.imageSize(image).equals(size)) {
				image = UtilsGL.createWritableImage(size);
				imageView.setImage(image);
			}
			UtilsGL.writeRawImageToImage(image, m);
		});
		profilerUpdate.exit();
	}
	
	
	@Override
	public Region node() {
		return pane;
	}
	
}
