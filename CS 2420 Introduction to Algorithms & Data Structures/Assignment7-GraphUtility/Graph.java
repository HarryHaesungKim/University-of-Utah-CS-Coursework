package assign07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class represents a generic, directed, unweighted, sparse graph.
 * 
 * @author Erin Parker && Harry Kim && Braden Morfin
 * @version October 19, 2020
 */
public class Graph<E> {

	// the graph -- a set of vertices (String name mapped to Vertex instance)
	private HashMap<E, Vertex<E>> vertices;

	private static boolean areConnected;

	/**
	 * Constructs an empty graph.
	 */
	public Graph() {
		vertices = new HashMap<E, Vertex<E>>();
		areConnected = false;
	}

	/**
	 * Adds to the graph a directed edge from the vertex with name "name1" to the
	 * vertex with name "name2". (If either vertex does not already exist in the
	 * graph, it is added.)
	 * 
	 * @param data1 - string name for source vertex
	 * @param data2 - string name for destination vertex
	 */
	public void addEdge(E data1, E data2) {
		Vertex<E> vertex1;
		// if vertex already exists in graph, get its object
		if (vertices.containsKey(data1))
			vertex1 = vertices.get(data1);
		// else, create a new object and add to graph
		else {
			vertex1 = new Vertex<E>(data1);
			vertices.put(data1, vertex1);
		}

		Vertex<E> vertex2;
		if (vertices.containsKey(data2))
			vertex2 = vertices.get(data2);
		else {
			vertex2 = new Vertex<E>(data2);
			vertices.put(data2, vertex2);
		}

		// add new directed edge from vertex1 to vertex2
		vertex1.addEdge(vertex2);
	}

	/**
	 * Generates the DOT encoding of this graph as string, which can be pasted into
	 * http://www.webgraphviz.com to produce a visualization.
	 */
	public String generateDot() {
		StringBuilder dot = new StringBuilder("digraph d {\n");

		// for every vertex
		for (Vertex<E> v : vertices.values()) {
			// for every edge
			Iterator<Edge<E>> edges = v.iteratorEdges();
			while (edges.hasNext())
				dot.append("\t" + v.getData() + " -> " + edges.next() + "\n");

		}

		return dot.toString() + "}";
	}

	/**
	 * Generates a simple textual representation of this graph.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();

		for (Vertex<E> v : vertices.values())
			result.append(v + "\n");

		return result.toString();
	}

	/**
	 * Recursive method that performs depth first search to see if there exists a
	 * path from one vertex to the other.
	 * 
	 * @param <Type>            - Type of elements in the graph
	 * @param startingVertex    - The starting vertex
	 * @param destinationVertex - The vertex we want to find the path of
	 * @return True if if there exists a path from the startingVertex to the
	 *         destinationVertex.
	 */
	private static <Type> boolean depthFirstSearchHelper(Vertex<Type> startingVertex, Vertex<Type> destinationVertex) {
		// checks to see if srcData is equal to dstData,
		// meaning they are the same vertex
		if (startingVertex.equals(destinationVertex)) {
			return true;
		}

		// Depth-First Search algorithm from lecture
		for (Edge<Type> e : startingVertex.edges()) {
			Vertex<Type> w = e.getOtherVertex();

			// destination vertex is found, break
			if (w.equals(destinationVertex)) {
				areConnected = true;
				break;
			}

			if (w.getDistanceFromStart() == Double.POSITIVE_INFINITY) {
				w.setDistanceFromStart(startingVertex.getDistanceFromStart() + 1);
				w.setPrevVertex(startingVertex);
				depthFirstSearchHelper(w, destinationVertex);
			}
		}
		return areConnected;

	}

	/**
	 * Driver method for the recursive method that performs depth first search to
	 * see if there exists a path from one vertex to the other.
	 * 
	 * @param <Type>  - Type of elements in the graph
	 * @param g       - The graph to be searched
	 * @param srcData - The data of the vertex we want to find the path of
	 * @param dstData - The data of the vertex we want to find the path to
	 * @return True if if there exists a path from the srcData to the dstData.
	 */
	public static <Type> boolean depthFirstSearch(Graph<Type> g, Type srcData, Type dstData) {
		for (Vertex<Type> v : g.vertices.values()) {
			v.setDistanceFromStart(Double.POSITIVE_INFINITY);
		}

		// Starting vertex
		Vertex<Type> s = g.vertices.get(srcData);

		// Destination vertex we want to see if there exists a path to
		Vertex<Type> d = g.vertices.get(dstData);

		s.setDistanceFromStart(0);
		return depthFirstSearchHelper(s, d);
	}

	/**
	 * This method performs a breadth-first Search to determine the shortest path
	 * from srcData to dstData.
	 * 
	 * @param <Type>  - The type of elements in the graph
	 * @param g       - The graph to be searched
	 * @param srcData - The data of the starting vertex
	 * @param dstData - The data of the destination vertex
	 * @return - The list representation of the shortest path from src to dst
	 */
	public static <Type> List<Type> breadthFirstSearch(Graph<Type> g, Type srcData, Type dstData) {
		ArrayList<Type> shortestPath = new ArrayList<Type>();

		// sets each vertices distance from start to infinity
		for (Vertex<Type> v : g.vertices.values()) {
			v.setDistanceFromStart(Double.POSITIVE_INFINITY);
		}

		Queue<Vertex<Type>> q = new LinkedList<Vertex<Type>>();

		Vertex<Type> s = g.vertices.get(srcData);

		Vertex<Type> d = g.vertices.get(dstData);

		Vertex<Type> destinationVertex = d;

		// Breadth-First Search algorithm from lecture
		q.offer(s);
		s.setDistanceFromStart(0);

		while (!(q.isEmpty())) {
			Vertex<Type> x = q.poll();

			for (Edge<Type> e : x.edges()) {
				Vertex<Type> w = e.getOtherVertex();
				if (w.getDistanceFromStart() == Double.POSITIVE_INFINITY) {
					w.setDistanceFromStart(x.getDistanceFromStart() + 1);
					w.setPrevVertex(x);

					// if we find the destination vertex
					// that means we have found the shortest
					// path, so break from the loop
					if (w.getData().equals(d.getData())) {
						destinationVertex = w;
						break;
					}

					q.offer(w);
				}
			}

		}

		// populating the return list with null
		// (the number of vertices in the shortest path)
		for (double i = 0; i <= destinationVertex.getDistanceFromStart(); i++) {
			shortestPath.add((int) i, null);
		}

		// filling the return list of the shortest path
		// with the vertices of the shortest path
		for (double i = destinationVertex.getDistanceFromStart(); i >= 0; i--) {
			shortestPath.set((int) i, destinationVertex.getData());
			destinationVertex = destinationVertex.getPrevVertex();
		}
		return shortestPath;
	}

	/**
	 * Performs topological sort in order to sort the vertices of a graph
	 * 
	 * @param <Type> - The type of elements in the graph
	 * @param g      - the graph to be sorted
	 * @return one possible topological sorting of a graph in a form of a list
	 */
	public static <Type> List<Type> topologialSort(Graph<Type> g) {
		// List that holds the topological sorting of the graph
		ArrayList<Type> topSortedList = new ArrayList<Type>();

		// Sets the indegree of all the vertices
		for (Vertex<Type> v : g.vertices.values()) {
			for (Edge<Type> e : v.edges()) {
				e.getOtherVertex().setIndegree(e.getOtherVertex().getIndegree() + 1);
			}
		}

		// Top sort algorithm
		Queue<Vertex<Type>> q = new LinkedList<Vertex<Type>>();
		for (Vertex<Type> v : g.vertices.values()) {
			if (v.getIndegree() == 0)
				q.offer(v);
		}
		while (!(q.isEmpty())) {
			Vertex<Type> x = q.poll();

			// adds the next vertex in the topological sorting of the graph to the return list
			topSortedList.add(x.getData());

			for (Edge<Type> e : x.edges()) {
				Vertex<Type> w = e.getOtherVertex();
				w.setIndegree(w.getIndegree() - 1);
				if (w.getIndegree() == 0) {
					q.offer(w);
				}
			}
		}

		// checks for a cycle in the graph
		if (topSortedList.size() != g.vertices.size()) {
			throw new IllegalArgumentException();
		}

		return topSortedList;
	}

}