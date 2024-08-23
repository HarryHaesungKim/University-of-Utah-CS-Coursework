package problemSet8;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class ProblemSet8 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner inputScanner = new Scanner(System.in);

		// Number of road segments.
		int n = inputScanner.nextInt();

		// Number of intersections.
		int i = inputScanner.nextInt();

		// Starting road segment.
		int s = inputScanner.nextInt();

		// Road segment where the driver will end up.
		int e = inputScanner.nextInt();

		// adjacency list is the way to go.

		// Data structures we will need:
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] graph = new LinkedList[n];
		
		// Did not need visited data.
		// boolean[] visited = new boolean[n];
		
		// Weights data representing left, right, or straight.
		// double[][] weights = new double[n][n];
		
		// Holds directions to one "node" to another. From [] to [].
		String[][] directions = new String[n][n];
		
		// Holds vertices information.
		Vertex[] vertices = new Vertex[n];
		
		// Holds unique vertex IDs to make sure a vertex does not try to get added twice.
		Set<Integer> pqIDs = new HashSet<Integer>();
		
		// Did not end up needing edge data.
		// Edge[] graph = new Edge[n];
		
		// Gathering all the graph information.
		for (int j = 0; j < i; j++) {

			// initialize vertices.
			int sv = inputScanner.nextInt();
			String a = inputScanner.next();
			int ev = inputScanner.nextInt();
			
			Vertex vs;
			
			Vertex ve;
			
			// If the first vertex is the starting vertex.
			if (sv == s) {
				vs = new Vertex(sv, true);
				if (!pqIDs.contains(sv)) {
					pqIDs.add(sv);
					vertices[sv] = vs;
				}
				ve = new Vertex(ev, false);
				if (!pqIDs.contains(ev)) {
					pqIDs.add(ev);
					vertices[ev] = ve;
				}
			}
			
			// If the second vertex is the starting vertex.
			else if(ev == s) {
				ve = new Vertex(ev, true);
				if (!pqIDs.contains(ev)) {
					pqIDs.add(ev);
					vertices[ev] = ve;
				}
				vs = new Vertex(sv, false);
				if (!pqIDs.contains(sv)) {
					pqIDs.add(sv);
					vertices[sv] = vs;
				}
			}
			
			// If neither are the start.
			else {
				vs = new Vertex(sv, false);
				if (!pqIDs.contains(sv)) {
					pqIDs.add(sv);
					vertices[sv] = vs;
				}
				ve = new Vertex(ev, false);
				if (!pqIDs.contains(ev)) {
					pqIDs.add(ev);
					vertices[ev] = ve;
				}
			}
			
			// Building the graph with an adjacency list.
			if (graph[sv] == null) {
				graph[sv] = new LinkedList<Integer>();
			}
			if (graph[ev] == null) {
				graph[ev] = new LinkedList<Integer>();
			}
			graph[sv].add(ev);
						
//			// Updating weights data. 50000 of each data can fit in a double.
//			if (a.equals("right")) {
//				weights[sv][ev] = 0.0000000001;
//			} else if (a.equals("straight")) {
//				weights[sv][ev] = 0.00001;
//			} else if (a.equals("left")) {
//				weights[sv][ev] = 1.0;
//			}
			
			// Saving the directions of each edge.
			directions[sv][ev] = a;
		}
		
		inputScanner.close();

		// finish dijkstra's and then do cool stuff.
		dijkstra(vertices[s], vertices, graph, directions);
		
		// For testing (delete later).
		// System.out.println("Test");

		// Now we can print following the predecessors back to the start.
		int currentV = e;
		int predV = vertices[currentV].getPred();
		int solRoadSegs = 0;
		StringBuilder sb = new StringBuilder();
		
		// Building the solution.
		while(predV > -1) {
			// build the string and print.
			
			solRoadSegs++;
			
			// For testing (delete later).
			//String test2 = directions[predV][currentV];
			
		    sb.insert(0, " " + directions[predV][currentV]);
		    
		    currentV = predV;
		    predV = vertices[currentV].getPred();
		    //predV = graph[currentV].get
		}
		
		// Printing the solution.
		System.out.print(solRoadSegs);
		System.out.print(sb.toString());
	}
	
	/**
	 * Checks to see if an edge between two vertices is tense.
	 * 
	 * @param u
	 * @param v
	 * @param w
	 * @return
	 */
	public static boolean isTense(Vertex u, Vertex v, double w) {
		return (u.getDist() + w < v.getDist());
	}
	
	/**
	 * Relaxes the edge between two vertices by updating it to a smaller distance.
	 * 
	 * @param u
	 * @param v
	 * @param w
	 */
	public static void relax(Vertex u, Vertex v, double w, Vertex[] vertices) {
		v.setDist(u.getDist() + w);
		v.setPred(u.getID());
		//vertices[v.getID()].setPred(u.getID());
	}
	
	/**
	 * Performs Dijkstra's Shortest Path algorithm.
	 * 
	 * @param s
	 * @param graph
	 * @param directions
	 */
	public static void dijkstra(Vertex s, Vertex[] vertices, LinkedList<Integer>[] graph, String[][] directions) {
		
		// Initialization is already done.

		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();
		pq.add(s);

		while (!pq.isEmpty()) {
			Vertex u = pq.poll();
			// System.out.println(u.getID());
			for (int v : graph[u.getID()]) {
				
				double weight;
				if(directions[u.getID()][v].equals("right")) {
					weight = 0.0000000001;
				}
				else if(directions[u.getID()][v].equals("straight")) {
					weight = 0.00001;
				}
				else {
					weight = 1.0;
				}
				
				if (isTense(vertices[u.getID()], vertices[v], weight)) {
					relax(vertices[u.getID()], vertices[v], weight, vertices);
					
					// Code smell
//					if (pq.contains(v)) {
//						// decrease key is already done by nature of the priority queue. So do nothing.
//					} else {
//						pq.add(v);
//					}
					
					if(!pq.contains(vertices[v])) {
						pq.add(vertices[v]);
					}
				}
			}
		}
	}

}

class Vertex implements Comparable<Vertex> {
	private int id;
	private boolean isStart;
	private double dist;
	private int pred;

	private boolean visited = false;

	Vertex(int id, boolean isStart) {
		this.id = id;
		if (isStart) {
			this.dist = 0;
		} else {
			this.dist = Double.POSITIVE_INFINITY;
		}
		this.pred = -69;
	}

	public int getID() {
		return id;
	}

	public boolean getIsStart() {
		return isStart;
	}

	public double getDist() {
		return dist;
	}

	public int getPred() {
		return pred;
	}

	public boolean getVisited() {
		return visited;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}

	public void setPred(int pred) {
		this.pred = pred;
	}

	public void setVisitedTrue() {
		this.visited = true;
	}

	@Override
	public int compareTo(Vertex o) {
		
		// For future reference, this configuration will sort it with lowest first in a priority queue:
		
		// If this is larger, return positive.
		if (this.getDist() > o.getDist()) {
			return 1;
		}
		
		// If other is larger, return negative.
		else if (this.getDist() < o.getDist()) {
			return -1;
		}
		
		// If they are equal, return 0.
		else {
			return 0;
		}
	}
}

//class Edge implements Comparable<Edge> {
//	private int startVertex;
//	private String action;
//	private int endVertex;
//
//	Edge(int startVertex, String action, int endVertex) {
//		this.startVertex = startVertex;
//		this.action = action;
//		this.endVertex = endVertex;
//	}
//
//	public int getStartVertex() {
//		return startVertex;
//	}
//
//	public String getAction() {
//		return action;
//	}
//
//	public int getEndVertex() {
//		return endVertex;
//	}
//
//	@Override
//	public int compareTo(Edge o) {
//		// TODO Auto-generated method stub
//		if (!this.getAction().equals(o.getAction())) {
//			if (this.getAction().equals("left")) {
//				return 1;
//			} else if (o.getAction().equals("left")) {
//				return -1;
//			}
//
//			else if (this.getAction().equals("straight")) {
//				return 1;
//			} else if (o.getAction().equals("straight")) {
//				return -1;
//			}
//
//			else if (this.getAction().equals("right")) {
//				return 1;
//			} else if (o.getAction().equals("right")) {
//				return -1;
//			}
//		}
//		return 0;
//	}
//}
//
//class Graph {
//	private int numVertices;
//	private LinkedList<Integer> adjLists[];
//
//	Graph(int numVertices) {
//		this.numVertices = numVertices;
//	}
//
//}