package xyz.marsavic.gfxlab.elements;

import xyz.marsavic.functions.interfaces.A1;
import xyz.marsavic.functions.interfaces.F0;
import xyz.marsavic.reactions.values.EventInvalidated;
import xyz.marsavic.utils.Caches;
import xyz.marsavic.utils.Reflection;
import xyz.marsavic.utils.Utils;

import java.util.List;


/**
 * If any of element's inputs is changed or invalidated, the element will fire an invalidation event.
 * All elements must be thread safe. All methods must use lockForReading or lockForWriting.
 * lockForReading guarantees that nothing will change while inside the lock's action.
 * lockForWriting guarantees that
 *
 * When other objects need to do a block operation by repeatedly calling this element's functions, they should
 * lock it for reading to ensure the same state throughout the block operation.
 */
public abstract class Element {
	
	public class Input<T> {
		
		private Output<T> output;
		
		
		private final A1<EventInvalidated> onInvalidated = e -> {
			onInputInvalidated(this, e);
			onInputChangedOrInvalidated(this);
		};
		
		
		// TODO Public constructor is a problem. Anyone can "attach" an input to an element and send invalidation events.
		public Input(Output<T> output) {
			connect(output);
		}

		
		public T get() {
			return output.get();
		}
		

		public void connect(Output<T> newOutput) {
			if (newOutput == output) return;

			if (output != null) {
				output.onInvalidated().remove(onInvalidated);
			}
			output = newOutput;
			output.onInvalidated().add(onInvalidated);
			
			onInputChanged(this);
			onInputChangedOrInvalidated(this);
		}
		
	}
	
	
	/** Convenience method that can be called from constructor and when re-initializing data after the inputs change or
	 * get invalidated. This is the default behaviour unless the method onInputChangedOrInvalidated is overridden. */
	protected void buildItUp() {
	}


	/** Convenience method that can be called when disposing the object and when re-initializing data after the inputs
	 * change or get invalidated. This is the default behaviour unless the method onInputChangedOrInvalidated is
	 * overridden. */
	protected void tearItDown() {
	}

	
	/** Override this to react to the internal changes in the input object.
	 * This is called when the input object changed internally, but it is still the same object, i.e. input.get()
	 * will return the same reference as before. */
	protected void onInputInvalidated(Input<?> input, EventInvalidated event) {
	}

	/** Override this to react when input object changes.
	 * This is called when input.get() is not the same reference as before.
	 * Use lock for
	 **/
	protected <T> void onInputChanged(Input<T> input) {
	}

	
	protected <T> void onInputChangedOrInvalidated(Input<T> input) {
		tearItDown();
		buildItUp();
		outputs().forEach(Invalidatable.Base::fireInvalidated);
	}
	
	
	protected final F0<List<Input<?>>> inputsFromFields = Caches.cached(() ->
		Utils.cast(Reflection.fieldsOfType(this, Input.class))
	);
	
	protected final F0<List<Output<?>>> outputsFromFields = Caches.cached(() ->
		Utils.cast(Reflection.fieldsOfType(this, Output.class))
	);
	
	
	
	public List<Input<?>> inputs() {
		return inputsFromFields.at();
	}
	
	public List<Output<?>> outputs() {
		return outputsFromFields.at();
	}

}
