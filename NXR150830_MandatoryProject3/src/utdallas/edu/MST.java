package utdallas.edu;

/* Ver 1.0: Starter code for Prim's MST algorithm */

import utdallas.edu.BinaryHeap.VertexComparator;
import utdallas.edu.BinaryHeap.EdgeComparator;

import java.util.Comparator;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class MST {
	static final int Infinity = Integer.MAX_VALUE;

	/*
	 * priority queue of vertices, using indexed heaps When E is large, it's
	 * better to use a heap for the priority queue as we will have many nodes in
	 * the queue. It will take time to find min/remove min from an array
	 * O(n)/O(n), while a heap only takes O(1)/log(n).
	 */
	static int PrimMST(Graph g, Vertex s) {
		int wmst = 0;

		// Code for Prim's algorithm needs to be written
		Vertex[] arrayOfVertices = new Vertex[g.size + 1];
		Comparator<Vertex> comparator = new VertexComparator();
		int i = 1;
		for (Vertex resVertex : g.v) {
			if (resVertex != null) {
				resVertex.seen = false;
				resVertex.p = null;
				resVertex.d = Infinity;
				resVertex.index = i;
				arrayOfVertices[i++] = resVertex;
			}
		}
		s.d = 0;

		IndexedHeap<Vertex> indexedHeaps = new IndexedHeap<Vertex>(
				arrayOfVertices, comparator);

		/*
		 * 
		 * */
		while (!indexedHeaps.isEmpty()) {
			Vertex otherVertex = indexedHeaps.remove();
			otherVertex.seen = true;
			wmst += otherVertex.d;
			for (Edge edgeInfo : otherVertex.adj) {
				Vertex v = edgeInfo.otherEnd(otherVertex);
				if (!v.seen && edgeInfo.weight < v.d) {
					v.d = edgeInfo.weight;
					v.p = otherVertex;
					indexedHeaps.decreaseKey(v);
				}
			}
		}

		return wmst;
	}

	/*
	 * Prim1 (priority queue of edges; using Java's priority queues), If E is
	 * small, we will have few nodes in the queue and thus, to find min and
	 * remove it from the array will not need a lot of operations in this case.
	 * In this using a heap will not be necessary and it might even be slower
	 * than an array due to operations needed to build the heap.
	 */
	static int PrimMST1(Graph g, Vertex s) {
		for (Vertex vertexInfo : g.v) {
			if (vertexInfo != null) {
				vertexInfo.seen = false;
				vertexInfo.p = null;
			}
		}
		int wmst = 0;
		s.seen = true;
		Comparator<Edge> comparator = new EdgeComparator();
		BinaryHeap<Edge> javaPriorityQueue = new BinaryHeap<Edge>(g.size + 1,
				comparator);

		for (Edge edgeInfo : s.adj)
			javaPriorityQueue.add(edgeInfo);

		/*
		 * Here we will add all the nodes to the queue so that is the result it
		 * will take long time to process large dense graphs
		 */
		while (!javaPriorityQueue.isEmpty()) {
			Edge getEdgeInfo = javaPriorityQueue.remove();
			if (getEdgeInfo.from.seen && getEdgeInfo.to.seen)
				continue;
			Vertex oneVertex = null;
			Vertex otherVertex = null;
			if (getEdgeInfo.from.seen) {
				otherVertex = getEdgeInfo.from;
				oneVertex = getEdgeInfo.to;
			} else {
				otherVertex = getEdgeInfo.to;
				oneVertex = getEdgeInfo.from;
			}
			oneVertex.p = otherVertex;
			wmst = wmst + getEdgeInfo.weight;
			oneVertex.seen = true;
			for (Edge f : oneVertex.adj) {
				if (!(f.from.seen && f.to.seen))
					javaPriorityQueue.add(f);
			}
		}

		return wmst;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

		Graph g = Graph.readGraph(in);

		Vertex s = g.getVertex(1);
		Timer timer = new Timer();
		System.out.println(PrimMST1(g, s));
		timer.end();
		System.out.println(timer);

	}
}
