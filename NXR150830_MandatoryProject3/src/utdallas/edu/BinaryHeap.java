package utdallas.edu;

//Ver 1.0:  Starter code for Binary Heap implementation

import java.util.Comparator;

public class BinaryHeap<T> implements PQ<T> {
	Object[] pq;
	Comparator<T> c;
	int sizeofqueue;

	/** Build a priority queue with a given array q */
	BinaryHeap(T[] q, Comparator<T> comp) {
		pq = q;
		c = comp;
		sizeofqueue = q.length - 1;
		buildHeap();
	}

	/** Create an empty priority queue of given maximum size */
	@SuppressWarnings("unchecked")
	BinaryHeap(int n, Comparator<T> comp) { /* to be implemented */
		pq = (T[]) new Object[n + 1];
		c = comp;
		sizeofqueue = 0;

	}

	public void insert(T x) {
		add(x);
	}

	public T deleteMin() {
		return remove();
	}

	public boolean isEmpty() {
		return sizeofqueue == 0;
	}

	public T min() {
		return peek();
	}

	/*
	 * Add the element at the bottom leaf of the Heap. Perform the Percolate-Up
	 * operation. All Insert Operations must perform the percolate-up operation
	 */
	public void add(T x) { /* to be implemented */
		if (pq.length - 1 == sizeofqueue)
			/*
			 * resize priority queue array when it is full.
			 */
			resizePriorityQueue();

		sizeofqueue++;
		addElement(sizeofqueue, x);
		percolateUp(sizeofqueue);
	}

	@SuppressWarnings("unchecked")
	/*
	 * Re-size priority queue
	 */
	public void resizePriorityQueue() {
		T[] newPriorityQueue = (T[]) pq;
		/*
		 * This capacity will be incremented by 2 x whenever it is needed
		 */
		pq = (T[]) new Object[2 * sizeofqueue];
		for (int i = 0; i <= sizeofqueue; i++)
			pq[i] = newPriorityQueue[i];
	}

	/*
	 * steps for inserting Add the element at the particular index and then
	 * perform percolate up it it violates the condition.
	 */
	public void addElement(int index, T data) {
		pq[index] = data;
	}

	@SuppressWarnings("unchecked")
	/*
	 * Find the index for the element to be deleted. Take out the last element
	 * from the last level from the heap and replace the index with this element
	 * . Perform Percolate-Down
	 */
	public T remove() { /* to be implemented */
		T minElement;
		minElement = (T) pq[1];
		addElement(1, (T) pq[sizeofqueue--]);
		percolateDown(1);
		return minElement;
	}

	@SuppressWarnings("unchecked")
	public T peek() { /* to be implemented */
		return (T) pq[1];
	}

	/** pq[i] may violate heap order with parent */
	/*
	 * If inserted element is smaller than its parent node in case of Min-Heap
	 * OR greater than itsparent node in case of Max-Heap, swap the element with
	 * its parent.Keep repeating the above step, if node reaches its correct
	 * position, STOP.
	 */
	@SuppressWarnings("unchecked")
	void percolateUp(int i) { /* to be implemented */
		addElement(0, (T) pq[i]);
		while (c.compare((T) pq[i / 2], (T) pq[0]) > 0) {
			addElement(i, (T) pq[i / 2]);
			i = i / 2;
		}
		addElement(i, (T) pq[0]);
	}

	/** pq[i] may violate heap order with children */
	/*
	 * Take out the last ele­ment from the last level from the heap and replace
	 * the root with the element. Perform Percolate-Down If replaced element is
	 * greater than any of its child node in case of Min-Heap OR smaller than
	 * any if its child node in case of Max-Heap, swap the ele­ment with its
	 * smallest child(Min-Heap) or with its great­est child(Max-Heap). Keep
	 * repeating the above step, if node reaches its correct position, STOP.
	 */
	@SuppressWarnings("unchecked")
	void percolateDown(int i) { /* to be implemented */
		int childrenPriorityQueue;
		T temp = (T) pq[i];
		for (; 2 * i <= sizeofqueue; i = childrenPriorityQueue) {
			childrenPriorityQueue = 2 * i;
			if (childrenPriorityQueue != sizeofqueue
					&& c.compare((T) pq[childrenPriorityQueue + 1],
							(T) pq[childrenPriorityQueue]) < 0)
				childrenPriorityQueue++;
			if (c.compare((T) pq[childrenPriorityQueue], temp) < 0)
				addElement(i, (T) pq[childrenPriorityQueue]);
			else
				break;
		}
		addElement(i, temp);
	}

	/** Create a heap. Precondition: none. */
	void buildHeap() {
		for (int i = sizeofqueue / 2; i > 0; i--)
			percolateDown(i);
	}

	/*
	 * sort array A[1..n]. A[0] is not used. Sorted order depends on comparator
	 * used to buid heap. min heap ==> descending order max heap ==> ascending
	 * order
	 */
	public static <T> void heapSort(T[] A, Comparator<T> comp) { /*
																 * to be
																 * implemented
																 */
		BinaryHeap<T> binaryheap = new BinaryHeap<>(A, comp);
		binaryheap.buildHeap();
		T x;

		for (int i = A.length - 1; i > 0; i--) {
			x = A[i];
			A[i] = A[1];
			A[1] = x;

			binaryheap.sizeofqueue--;
			binaryheap.percolateDown(1);
		}
	}

	/*
	 * For comparing the two vertex
	 */
	public static class VertexComparator implements Comparator<Vertex> {
		public int compare(Vertex u, Vertex v) {
			return u.d - v.d;
		}
	}

	/*
	 * Edge comparison comparator
	 */
	public static class EdgeComparator implements Comparator<Edge> {
		public int compare(Edge e1, Edge e2) {
			return e1.weight - e2.weight;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i <= sizeofqueue; i++)
			builder.append(pq[i] + " ");
		return builder.toString();
	}
}
