package utdallas.edu;

//Ver 1.0:  Starter code for Indexed heaps

import java.util.Comparator;

public class IndexedHeap<T extends Index> extends BinaryHeap<T> {
	/** Build a priority queue with a given array q */
	IndexedHeap(T[] q, Comparator<T> comp) {
		super(q, comp);
		setPriorityQueueIndex();

	}

	/** Create an empty priority queue of given maximum size */
	IndexedHeap(int n, Comparator<T> comp) {
		super(n, comp);
		setPriorityQueueIndex();
	}

	/** restore heap order property after the priority of x has decreased */
	void decreaseKey(T x) {
		percolateUp(x.getIndex());
	}

	@Override
	public void addElement(int index, T data) {
		super.addElement(index, data);
		data.putIndex(index);
	}

	public void setPriorityQueueIndex() {
		for (int i = 1; i <= sizeofqueue; i++)
			((Index) pq[i]).putIndex(i);
	}
}
