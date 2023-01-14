package xyz.marsavic.gfxlab;

import xyz.marsavic.elements.Invalidatable;
import xyz.marsavic.functions.A3;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.gui.UtilsGL;
import xyz.marsavic.resources.Resource;
import xyz.marsavic.time.Profiler;
import xyz.marsavic.utils.Hashing;
import xyz.marsavic.utils.Utils;

import java.util.HashMap;
import java.util.Map;


public class Aggregator extends Invalidatable.Base {
	
	private final A3<Matrix<Color>, Integer, Long> aFillFrameColorRandomized;
	private final long seed;
	private final Vec3 size;
	
	private final int nFrames;
	private final Vector sizeFrame;
	private final Map<Integer, Aggregate> aggregates = new HashMap<>();
	
	
	private Profiler profilerRemoveMePls = UtilsGL.profiler(this, "loop");
	
	private boolean[] shouldRun;
	
	public Aggregator(A3<Matrix<Color>, Integer, Long> aFillFrameColorRandomized, Vec3 size, long seed) {
		this.aFillFrameColorRandomized = aFillFrameColorRandomized;
		this.size = size;
		this.seed = seed;
		
		nFrames = (int) size.x();
		sizeFrame = size.p12();
		int t = 0;
		aggregates.put(t, new Aggregate(aFillFrameColorRandomized, size.p12(), t, Hashing.mix(seed, t)));
		
		shouldRun = Utils.daemonLoop(() -> {
			// TODO FIXME !!!!!!!!!!!! NOOOOOOOOO!!!!!!! This is just a temporary test.
			profilerRemoveMePls.enter();
			addSample(0);
			profilerRemoveMePls.exit();
		});
		
	}
	
	
	private Aggregate getAggregate(int t) {
		return aggregates.get(0); // TODO
	}
	
	
	public Resource<Matrix<Color>> rFrame(double t) {
		return getAggregate((int) t).avg();
	}
	
	
	public void addSample(int t) {
		getAggregate(t).addSample();
		fireInvalidated(); // TODO Fire EventInvalidatedFrame instead
	}
	
	
	public void tearItDown() {
		aggregates.values().forEach(Aggregate::release);
		shouldRun[0] = false;
	}
	
	
	// ------------------------------------------------------------------------------------------------------
	
	
	private static class Aggregate {
		// params
		private final int t;
		private final A3<Matrix<Color>, Integer, Long> aFillFrameColor;
		private final Vector size;
		private final long seed;
		
		
		private record State(
				int count,
				Resource<Matrix<Color>> rSum
		) {
			private void release() {
				rSum.release();         // TODO! Waiting to reach HeapMax before GC actually releases if not releasing here. If releasing here, we release before it is used -> exception.
			}
			
			private Resource<Matrix<Color>> avg() {
				return rSum.f(sum -> {
					Resource<Matrix<Color>> rAvg = UtilsGL.matricesColor.borrow(sum.size(), true);
					rAvg.a(avg -> {
						Matrix.mul(sum, 1.0 / count, avg);
					});
					return rAvg;
				});
			}
		}
		
		private State state = null;
		
		public Aggregate(A3<Matrix<Color>, Integer, Long> aFillFrameColor, Vector size, int t, long seed) {
			this.aFillFrameColor = aFillFrameColor;
			this.size = size;
			this.t = t;
			this.seed = seed;
		}
		
		public synchronized State getState() {
			if (state == null) {
				state = new State(0, UtilsGL.matricesColor.borrow(size));
			}
			return state;
		}
		
		public synchronized void setState(State stateNew) {
			State stateOld = state;
			state = stateNew;
			stateOld.release();
		}
		
		public synchronized void release() {
			if (state != null) {
				state.release();
			}
		}
		
		public void addSample() {
			State stateOld = getState(); // TODO Concurrency problem: stateOld can be released before we use its resource a few lines down.
			State stateNew = new State(stateOld.count + 1, UtilsGL.matricesColor.borrow(size, true));
			
			UtilsGL.matricesColor.borrow(size, mSample -> {
				// Instead of rewriting the same "sum" matrix, we borrow a new one and release the old one. This is
				// done to avoid dealing with concurrency issues. It would be faster and more memory efficient if we
				// added the new sample in-place, but it's more troublesome.
				
				aFillFrameColor.execute(mSample, t, Hashing.mix(seed, state.count));
				
				stateNew.rSum.a(mSumNew -> {
					stateOld.rSum.a(mSumOld -> {
						Matrix.add(mSumOld, mSample, mSumNew);
					});
				});
				
				setState(stateNew);
			});
		}
		
		public synchronized Resource<Matrix<Color>> avg() { // TODO try removing synchronized for this long operation
			return getState().avg();
		}
	}
	
}