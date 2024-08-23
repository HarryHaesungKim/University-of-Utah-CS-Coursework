package assign07;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class represents a vertex (AKA node) in a directed graph. The vertex is
 * generic.
 * 
 * @author Harry Kim && Braden Morfin
 * @version October 16, 2020
 */
public class Vertex<E> {

	private LinkedList<Edge<E>> adj;

	private E data;

	private double distFromStart;

	private boolean visited;
	
	private int indegree;

	private Vertex<E> prevVertex;

	/**
	 * Creates a new Vertex object, using the given data.
	 * 
	 * @param data - Data used to identify this Vertex
	 */
	public Vertex(E data) {
		this.data = data;
		this.adj = new LinkedList<Edge<E>>();
		indegree = 0;
	}

	/**
	 * @return the data used to identify this Vertex
	 */
	public E getData() {
		return data;
	}

	/**
	 * Adds a directed edge from this Vertex to another.
	 * 
	 * @param otherVertex - the Vertex object that is the destination of the edge
	 */
	public void addEdge(Vertex<E> otherVertex) {
		adj.add(new Edge<E>(otherVertex));
	}

	/**
	 * @return a iterator for accessing the edges for which this Vertex is the
	 *         source
	 */
	public LinkedList<Edge<E>> edges() {
		return adj;
	}

	/**
	 * Sets a distance from the starting vertex to this Vertex.
	 * 
	 * @param distance - the determined distance
	 */
	public void setDistanceFromStart(double distance) {
		this.distFromStart = distance;
	}

	/**
	 * Gets a distance from the starting vertex to this Vertex.
	 * 
	 * @return the determined distance
	 */
	public double getDistanceFromStart() {
		return distFromStart;
	}

	/**
	 * Sets whether or not we have visited this vertex.
	 * 
	 * @param visited the boolean to determine if visited
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * Gets whether or not we have visited this vertex.
	 * 
	 * @return boolean of whether or not we visited the vertex
	 */
	public boolean getVisited() {
		return visited;
	}

	/**
	 * Sets the previous vertex.
	 * 
	 * @param vertex the vertex to be set
	 */
	public void setPrevVertex(Vertex<E> vertex) {
		this.prevVertex = vertex;
	}

	/**
	 * Gets the previous vertex.
	 * 
	 * @return vertex the previous vertex
	 */
	public Vertex<E> getPrevVertex() {
		return prevVertex;
	}

	/**
	 * @return a iterator for accessing the edges for which this Vertex is the
	 *         source
	 */
	public Iterator<Edge<E>> iteratorEdges() {
		return adj.iterator();
	}
	
	/**
	 * Sets the indegree vertex.
	 * 
	 * @param indegree The indegree of the vertex
	 */
	public void setIndegree(int indegree) {
		this.indegree = indegree;
	}
	
	/**
	 * Gets the indegree vertex.
	 * 
	 * @return The indegree of the vertex
	 */
	public int getIndegree() {
		return indegree;
	}

	/**
	 * Generates and returns a textual representation of this Vertex.
	 */
	public String toString() {
		String s = "Vertex " + data + " adjacent to vertices ";
		Iterator<Edge<E>> itr = adj.iterator();
		while (itr.hasNext())
			s += itr.next() + "  ";
		return s;
	}
}