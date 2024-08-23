package problemSet7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class ProblemSet7 {
	public static void main(String[] args) {
		Scanner inputScanner = new Scanner(System.in);

		// Gathering inputs
		int seed = inputScanner.nextInt();
		int vertexCount = inputScanner.nextInt();
		int minWeight = inputScanner.nextInt();
		int maxWeight = inputScanner.nextInt();
		int connectivity = inputScanner.nextInt();
		String algorithm = inputScanner.next();
		int startVertex = 0;

		// Extra input if Jarnik
		if (algorithm.equals("Jarnik")) {
			startVertex = inputScanner.nextInt();
		}

		inputScanner.close();

		GenerateWeights gw = null;

		// Generating the graph of weights.
		int[][] graph = gw.generateWeights(seed, vertexCount, minWeight, maxWeight, connectivity);

		List<Edge> edges = new ArrayList<Edge>();

		// Getting all the edges of the graph.
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < i; j++) {
				if (graph[i][j] != 0) {
					Edge edge = new Edge(i, j, graph[i][j]);
					edges.add(edge);
				}
			}
		}
		
		Collections.sort(edges);
		
		List<Edge> solution = null;

		// Jarnik
		if (algorithm.equals("Jarnik")) {

			solution = jarnik(graph, startVertex, new ArrayList<Integer>(), new ArrayList<Edge>(),
					new PriorityQueue<Edge>());
		}

		// Kruskal
		else if (algorithm.equals("Kruskal")) {
			solution = kruskal(graph, edges);
		}

		// Boruvka
		else {
			solution = boruvka(graph, edges);
		}

		// Printing out the solution.
		System.out.println();
		int totalWeight = 0;
		for (Edge e : solution) {
			totalWeight += e.getWeight();
		}
		System.out.println(totalWeight);
		System.out.println(solution.size());
		for (Edge e : solution) {
			if (e.getVertex1() < e.getVertex2()) {
				System.out.println(e.getVertex1() + " " + e.getVertex2());
			} else {
				System.out.println(e.getVertex2() + " " + e.getVertex1());
			}
		}
	}

	// Edge weights are NOT unique.

	/**
	 * Jarnik's MST algorithm.
	 * 
	 * @param graph
	 * @param startVertex
	 * @param visited
	 * @param solution
	 * @param pq
	 * @return
	 */

	public static List<Edge> jarnik(int[][] graph, int startVertex, List<Integer> visited, List<Edge> solution,
			PriorityQueue<Edge> pq) {

		// Adding the starting vertex
		visited.add(startVertex);

		// Base case: Once the size is equal to the number of vertices, return.
		if (visited.size() == graph.length) {
			return solution;
		}

		// Adding every edge of the vertex to a priority queue.
		for (int i = 0; i < graph.length; i++) {
			if (graph[startVertex][i] != 0) {
				pq.add(new Edge(startVertex, i, graph[startVertex][i]));
			}
		}

		// Extracting the smallest edge.
		Edge minEdge = pq.poll();

		// If visited already has this edge, get the next smallest edge.
		while (visited.contains(minEdge.getVertex2()) == true) {
			minEdge = pq.poll();
		}

		// Add it to the solution list.
		solution.add(minEdge);

		// Recursion.
		return jarnik(graph, minEdge.getVertex2(), visited, solution, pq);
	}

	/**
	 * Kruskal's MTS Algorithm.
	 * 
	 * @param graph
	 * @return
	 */
	public static List<Edge> kruskal(int[][] graph, List<Edge> edges) {
		Set<Integer> edgesSet = new HashSet<Integer>();

		// List<Integer> listOfWeights = new ArrayList<Integer>();
		List<Edge> solution = new ArrayList<Edge>();
		// Set<Integer> visited = new HashSet<Integer>();

		// Getting all the edges of the graph.
		// Already done.

		// Sort edges by increasing weight.
		// Collections.sort(edges);
		// Also already done.

		// long[] subsets = new long[graph.length];
		// ArrayList<Boolean> subsets = new ArrayList<Boolean>();
		// boolean[][] subsets = new boolean[graph.length][graph.length];

		//ArrayList<HashSet<Integer>> subsets = new ArrayList<HashSet<Integer>>(graph.length);
		
		int[][] subsets = new int[graph.length][graph.length];

		// Create a set containing only the vertex v for each vertex.
		for (int i = 0; i < graph.length; i++) {
			// subsets[i] = 0 | (1L << i);
			subsets[i][i] = 1;
			
//			subsets.add(i, new HashSet<Integer>());
//			subsets.get(i).add(i);
		}

		// Making sure edge is safe and adding it to the solution.
		for (Edge e : edges) {

			// If u is in a different set than v
//			if (!subsets.get(e.getVertex1()).equals(subsets.get(e.getVertex2()))) {
			if (!subsets[e.getVertex1()].equals(subsets[e.getVertex2()])) {

				// subsets.get(e.getVertex1()).addAll(subsets.get(e.getVertex2()));
				
				int[] union = new int[graph.length];
				
				for(int i = 0; i < graph.length; i++) {
					if(subsets[e.getVertex1()][i] == 1 || subsets[e.getVertex2()][i] == 1) {
						union[i] = 1;
					}
				}
				
				for(int i = 0; i < graph.length; i++) {
					if(union[i] == 1) {
						subsets[i] = union;
					}
				}

				// When you perform a union between 2 sets to get a new component make sure you
				// update the component for every vertex in it and not just the two vertices in
				// the edge that you're adding
//				for (Integer i : subsets.get(e.getVertex1())) {
//					if (i != e.getVertex1()) {
//						subsets.get(i).clear();
//						subsets.get(i).addAll(subsets.get(e.getVertex1()));
//					}
//				}

				solution.add(e);
			}
		}
		return solution;
	}

	/**
	 * Boruvka's MST algorithm.
	 * 
	 * @param graph
	 * @param edges
	 * @return
	 */
	public static List<Edge> boruvka(int[][] graph, List<Edge> edges) {
		// List<Edge> solution = new ArrayList<Edge>();

		ArrayList<Edge> solution = new ArrayList<Edge>();

		String[] F = new String[graph.length + 1];

		int[] component = new int[graph.length];

		int count = countAndLabel(graph, solution, component);
		
		while (count > 1) {
			addAllSafeEdges(edges, F, solution, count, component);
			count = countAndLabel(graph, solution, component);
		}

		return solution;
	}

	public static void addAllSafeEdges(List<Edge> E, String[] F, ArrayList<Edge> solution, int count, int[] component) {
		Edge[] safe = new Edge[count + 1];
		
		for (Edge uv : E) {
			if (component[uv.getVertex1()] != component[uv.getVertex2()]) {

				if (safe[component[uv.getVertex1()]] == null
						|| (uv.getWeight() < safe[component[uv.getVertex1()]].getWeight())) {
					safe[component[uv.getVertex1()]] = uv;
				}

				if (safe[component[uv.getVertex2()]] == null
						|| (uv.getWeight() < safe[component[uv.getVertex2()]].getWeight())) {
					safe[component[uv.getVertex2()]] = uv;
				} else if (safe[component[uv.getVertex2()]] == null
						|| (uv.getWeight() == safe[component[uv.getVertex2()]].getWeight())) {
					// shorterEdge
				}
			}
		}
		for (int i = 1; i < count + 1; i++) {
			if (safe[i] != null) {

				// Problem area: Adding to a list instead of its correct positions
//				F.remove(i);
//				F.add(i, safe[i]);
				
				if(!solution.contains(safe[i])) {
					solution.add(safe[i]);
				}
				

				if (safe[i].getVertex1() < safe[i].getVertex2()) {
					F[i] = safe[i].getVertex1() + " " + safe[i].getVertex2();
				} else {
					F[i] = safe[i].getVertex2() + " " + safe[i].getVertex1();
				}
			}
		}
	}

	public static int countAndLabel(int[][] graph, List<Edge> edges, int[] component) {

		int count = 0;
		String[] vertexMark = new String[graph.length];

		for (int i = 0; i < graph.length; i++) {
			if (vertexMark[i] == null) {
				count++;
				labelOne(graph, edges, i, count, vertexMark, component);
			}
		}

		return count;
	}

	public static void labelOne(int[][] graph, List<Edge> edges, int vprime, int count, String[] vertexMark, int[] component) {
		Stack<Integer> bag = new Stack<Integer>();
		bag.add(vprime);

		while (!bag.isEmpty()) {
			int v = bag.pop();
			if (vertexMark[v] == null) {
				vertexMark[v] = "marked";
				component[v] = count;

				for (Edge e : edges) {
					if (e.getVertex1() == v && !bag.contains(e.getVertex2())) {
						bag.add(e.getVertex2());
					}

					if (e.getVertex2() == v && !bag.contains(e.getVertex1())) {
						bag.add(e.getVertex1());
					}
				}
			}
		}
	}
}

class Edge implements Comparable<Edge> {
	int vertex1;
	int vertex2;
	int weight;
	boolean withinMST = false;

	public Edge(int vertex1, int vertex2, int weight) {
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.weight = weight;
	}

	public int getVertex1() {
		return vertex1;
	}

	public int getVertex2() {
		return vertex2;
	}

	public int getWeight() {
		return weight;
	}

	public boolean getWithinMST() {
		return withinMST;
	}

	public void setWithinMST(boolean withinMST) {
		this.withinMST = withinMST;
	}

	@Override
	public int compareTo(Edge o) {
		// TODO Auto-generated method stub
		return shorterEdge(this, o);
	}

	public static int shorterEdge(Edge ij, Edge kl) {
		if (ij.getWeight() < kl.getWeight()) {
			return -1;
		}
		if (ij.getWeight() > kl.getWeight()) {
			return 1;
		}

		int ijmin = Math.min(ij.getVertex1(), ij.getVertex2());
		int klmin = Math.min(kl.getVertex1(), kl.getVertex2());
		if (ijmin < klmin) {
			return -1;
		}
		if (ijmin > klmin) {
			return 1;
		}

		int ijmax = Math.max(ij.getVertex1(), ij.getVertex2());
		int klmax = Math.max(kl.getVertex1(), kl.getVertex2());
		if (ijmax < klmax) {
			return -1;
		}

		if (ijmax > klmax) {
			return 1;
		}

		return 0;
	}
}

class GenerateWeights {

	/**
	 * For testing only -- application entry point
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		// Generate a small graph with only one spanning tree in it.
		// Print out it's adjacency matrix

		int[][] single = generateWeights(123, 10, 10, 99, 1);
		System.out.println("Connected undirected graph composed of a single spanning tree:\n");
		printWeights(single, 4, false); // Spacing of 4 OK for up to 999
		System.out.println();

		// Generate a small graph with multiple spanning trees in it.
		// Print out it's adjacency matrix

		int[][] multiple = generateWeights(123, 10, 10, 99, 5);
		System.out.println("Connected undirected graph containing multiple spanning trees:\n");
		printWeights(multiple, 4, false); // Spacing of 4 OK for up to 999
		System.out.println();
	}

	/**
	 * Prints out an adjacency matrix. Note that axis are labeled 'from' and 'to',
	 * but for undirected graphs this is irrelevant.
	 * 
	 * @param weights      an int[][] of weights
	 * @param spacing      how many spaces to print for each column
	 * @param showSymmetry graph is undirected -- should the entire matrix be shown?
	 *                     (Go Neo, Go!)
	 */
	public static void printWeights(int[][] weights, int spacing, boolean showSymmetry) {
		for (int to = -3; to < weights.length; to++) {
			if (to >= 0) {
				System.out.printf(to == weights.length / 2 ? "to" : "  ");
				System.out.printf("%" + spacing + "d|", to);
			} else
				System.out.printf("   %" + spacing + "s", "");

			for (int from = 0; from < weights.length; from++)
				if (to >= 0 && (from < to || showSymmetry))
					System.out.printf("%" + spacing + "d", weights[from][to]);
				else if (to == -3)
					System.out.printf(from == weights.length / 2 ? "from %s" : "%" + spacing + "s", "");
				else if (to == -2)
					System.out.printf("%" + spacing + "d", from);
				else if (to == -1)
					System.out.print("-----------".substring(0, spacing));

			System.out.println();
		}
	}

	/**
	 * Generates a connected, undirected, weighted graph. Note that the result is a
	 * symmetric adjacency matrix where each value represents an edge weight. (An
	 * edge weight of 0 represents a non-edge.)
	 * 
	 * Note that the arrays are zero-based, so vertices are numbered
	 * [0..vertexCount).
	 * 
	 * The connectivity parameter specifies how many times a random spanning tree
	 * should be added to the graph. Note that a value greater than 1 will probably
	 * result in a cycle, but it is not guaranteed (especially for tiny graphs)
	 * 
	 * For language independence, the random number generation is done using a
	 * linear feedback shift register with a cycle length of 2^31-1. (Bits 27 and 30
	 * are xor'd and fed back.) (Note: 2^31-1 is prime which is useful when
	 * generating pairs, triples, or other multi-valued sequences. The pattern won't
	 * repeat until after 2^31-1 pairs, triples, etc. are generated.)
	 * 
	 * Finally, the runtime of this generation is O(v) in connectivity, or
	 * k*v*connectivity.
	 * 
	 * @param seed         any positive int
	 * @param vertexCount  any int greater than 1
	 * @param minWeight    any positive int
	 * @param maxWeight    any int greater than minWeight
	 * @param connectivity the overall connectedness of the graph, min 1
	 * @return the weighted adjacency matrix for the graph
	 */
	public static int[][] generateWeights(int seed, int vertexCount, int minWeight, int maxWeight, int connectivity) // Non-zero
																														// seed,
																														// cap
																														// vertices
																														// at
																														// 100,
																														// weights
																														// at
																														// 10000
	{
		int[][] weights = new int[vertexCount][vertexCount];

		for (int pass = 0; pass < connectivity; pass++) {
			List<Integer> connected = new ArrayList<Integer>();
			List<Integer> unused = new ArrayList<Integer>();

			connected.add(0);
			for (int vertex = 1; vertex < vertexCount; vertex++)
				unused.add(vertex);

			while (unused.size() > 0) {
				seed = (((seed ^ (seed >> 3)) >> 12) & 0xffff) | ((seed & 0x7fff) << 16);
				int weight = seed % (maxWeight - minWeight + 1) + minWeight;

				seed = (((seed ^ (seed >> 3)) >> 12) & 0xffff) | ((seed & 0x7fff) << 16);
				Integer fromVertex = connected.get(seed % connected.size());

				seed = (((seed ^ (seed >> 3)) >> 12) & 0xffff) | ((seed & 0x7fff) << 16);
				Integer toVertex = unused.get(seed % unused.size());

				weights[fromVertex][toVertex] = weight;
				weights[toVertex][fromVertex] = weight; // Undirected

				connected.add(toVertex);
				unused.remove(toVertex); // Note -- overloaded, remove element Integer, not position int
			}
		}

		return weights;
	}

}