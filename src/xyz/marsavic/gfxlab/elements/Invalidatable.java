package xyz.marsavic.gfxlab.elements;

import xyz.marsavic.reactions.Dispatcher;
import xyz.marsavic.reactions.Reactions;
import xyz.marsavic.reactions.Reactive;
import xyz.marsavic.reactions.values.EventInvalidated;


public interface Invalidatable extends Reactive {
	Reactions<EventInvalidated> onInvalidated();
	
	
	class Base {
		protected final Dispatcher<EventInvalidated> dispatcherInvalidated = new Dispatcher<>();
		
		public Reactions<EventInvalidated> onInvalidated() {
			return dispatcherInvalidated.reactions();
		}
		
		protected void fireInvalidated() {
			dispatcherInvalidated.fire(new EventInvalidated());
		}
	}
	
}
