package utdallas.edu;

/* Ver 1.0: Starter code for Dijkstra's Shortest path algorithm */

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class ShortestPath {
	static final int Infinity = Integer.MAX_VALUE;

	static void DijkstraShortestPaths(Graph g, Vertex s) {
		// Code for Dijkstra's algorithm needs to be written
		// Shortest paths will be stored in fields d,p in the Vertex class
		graphInitialization(g, s);
		IndexedHeap<Vertex> indexedHeap = new IndexedHeap<>(
				g.v.toArray(new Vertex[g.v.size()]), new Vertex());
		Vertex oneVertex, otherVertex;

		while (!indexedHeap.isEmpty()) {
			oneVertex = indexedHeap.remove();
			oneVertex.seen = true;
			for (Edge edge : oneVertex.adj) {
				otherVertex = edge.otherEnd(oneVertex);
				// Here the distance as soon as it changes update the vertex
				// also
				if (!otherVertex.seen)
					if (updateGraphWithNewChanges(oneVertex, otherVertex, edge))
						indexedHeap.decreaseKey(otherVertex);
			}
		}

	}

	/*
	 * 
	 * Here this function will update basically the distance-d and the parent-p
	 * of the vertex with the shortest distance seen.Basically it will return
	 * true if new changes are updated else it returns false.
	 */
	public static boolean updateGraphWithNewChanges(Vertex oneVertex,
			Vertex otherVertex, Edge edge) {
		if (otherVertex.d > oneVertex.d + edge.weight) {
			otherVertex.d = oneVertex.d + edge.weight;
			otherVertex.p = oneVertex;
			return true;
		}
		return false;
	}

	/*
	 * This function initializes the graph with the distance value, whether its
	 * seen already?,who is the parent and the total count.
	 */
	public static void graphInitialization(Graph graph, Vertex vertex) {
		for (Vertex getVertexInfo : graph) {
			getVertexInfo.d = Integer.MAX_VALUE;
			getVertexInfo.p = null;
			getVertexInfo.seen = false;
			getVertexInfo.count = 0;
		}
		vertex.d = 0;
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
		int src = in.nextInt();
		int target = in.nextInt();
		Vertex s = g.getVertex(src);
		Vertex t = g.getVertex(target);
		Timer timer = new Timer();
		DijkstraShortestPaths(g, s);

		System.out.println(t.d);
		timer.end();
		System.out.println(timer);

	}
}
