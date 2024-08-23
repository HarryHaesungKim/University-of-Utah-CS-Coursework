package assign07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Contains several methods for solving problems on generic, directed,
 * unweighted, sparse graphs.
 * 
 * @author Erin Parker && Harry Kim && Braden Morfin
 * @version October 14, 2020
 */
public class GraphUtility {

	/**
	 * This driver method uses the depth-first search algorithm to determine whether
	 * there is a path from the vertex with srcData to the vertex with dstData in
	 * the graph. Throws an IllegalArgumentException if there does not exist a
	 * vertex in the graph with srcData, and likewise for dstData.
	 * 
	 * @param <Type>       - type of elements in the graph
	 * @param sources      - list of source vertices
	 * @param destinations - list of destinations vertices
	 * @param srcData      - source vertex
	 * @param dstData      - destination vertex
	 * @return true if there exists a path from srcData to dstData. Otherwise,
	 *         returns false.
	 * @throws IllegalArgumentException if srcData or dstData is not contained in
	 *                                  sources or destinations.
	 */
	public static <Type> boolean areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData)
			throws IllegalArgumentException {
		Graph<Type> g = buildGraph(sources, destinations);

		// Checks to see if srcData is within either sources or destinations
		if (!(sources.contains(srcData) || destinations.contains(srcData))) {
			throw new IllegalArgumentException();
		}

		// Checks to see if dstData is within either sources or destinations
		if (!(sources.contains(dstData) || destinations.contains(dstData))) {
			throw new IllegalArgumentException();
		}

		return Graph.depthFirstSearch(g, srcData, dstData);
	}

	/**
	 * This method returns the shortest path from srcData to dstData, should a path
	 * between the two exist uses a Breadth-First Search to do so.
	 * 
	 * @param <Type>       - type of elements in the graph
	 * @param sources      - list of source vertices
	 * @param destinations - list of destinations vertices
	 * @param srcData      - source vertex
	 * @param dstData      - destination vertex
	 * @return List - List representation of the shortest path from src to dst
	 * @throws IllegalArgumentException if srcData or dstData is not contained in
	 *                                  sources or destinations or if there does not
	 *                                  exist a path between src and dst.
	 */
	public static <Type> List<Type> shortestPath(List<Type> sources, List<Type> destinations, Type srcData,
			Type dstData) throws IllegalArgumentException {
		Graph<Type> g = buildGraph(sources, destinations);

		// Checks to see if srcData is within either sources or destinations
		if (!(sources.contains(srcData) || destinations.contains(srcData))) {
			throw new IllegalArgumentException();
		}

		// Checks to see if dstData is within either sources or destinations
		if (!(sources.contains(dstData) || destinations.contains(dstData))) {
			throw new IllegalArgumentException();
		}

		if (!(areConnected(sources, destinations, srcData, dstData))) {
			throw new IllegalArgumentException();
		}

		return Graph.breadthFirstSearch(g, srcData, dstData);
	}

	/**
	 * This method performs a topological sort, and returns one possible topological
	 * sorting of the given sources and destinations. Throws
	 * IllegalArgumentException if the graph is cyclic.
	 * 
	 * @param <Type>       - The type of elements in the graph
	 * @param sources      - list of source vertices
	 * @param destinations - list of destinations vertices
	 * @return - list of the one possible topological sorting of the graph
	 * @throws IllegalArgumentException if the graph is cyclic
	 */
	public static <Type> List<Type> sort(List<Type> sources, List<Type> destinations) throws IllegalArgumentException {
		Graph<Type> g = buildGraph(sources, destinations);
		return Graph.topologialSort(g);
	}

	/**
	 * Builds a graph for the vertices.
	 * 
	 * @param <Type>       - type of list in the graph
	 * @param sources      - list of sources
	 * @param destinations - list of sources
	 * @return a new graph
	 * @throws IllegalArgumentException if sources and destinations are not the same
	 *                                  size.
	 */
	private static <Type> Graph<Type> buildGraph(List<Type> sources, List<Type> destinations)
			throws IllegalArgumentException {
		// if the size of the two lists is not equal throw exception
		if (sources.size() != destinations.size()) {
			throw new IllegalArgumentException();
		}
		// creates graph object to be returned
		Graph<Type> g = new Graph<Type>();

		for (int i = 0; i < sources.size(); i++) {
			g.addEdge(sources.get(i), destinations.get(i));
		}

		return g;
	}

	/**
	 * Builds "sources" and "destinations" lists according to the edges specified in
	 * the given DOT file (e.g., "a -> b"). Assumes that the vertex data type is
	 * String.
	 * 
	 * Accepts many valid "digraph" DOT files (see examples posted on Canvas).
	 * --accepts \\-style comments --accepts one edge per line or edges terminated
	 * with ; --does not accept attributes in [] (e.g., [label = "a label"])
	 * 
	 * @param filename     - name of the DOT file
	 * @param sources      - empty ArrayList, when method returns it is a valid
	 *                     "sources" list that can be passed to the public methods
	 *                     in this class
	 * @param destinations - empty ArrayList, when method returns it is a valid
	 *                     "destinations" list that can be passed to the public
	 *                     methods in this class
	 */
	public static void buildListsFromDot(String filename, ArrayList<String> sources, ArrayList<String> destinations) {

		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		scan.useDelimiter(";|\n");

		// Determine if graph is directed (i.e., look for "digraph id {").
		String line = "", edgeOp = "";
		while (scan.hasNext()) {
			line = scan.next();

			// Skip //-style comments.
			line = line.replaceFirst("//.*", "");

			if (line.indexOf("digraph") >= 0) {
				edgeOp = "->";
				line = line.replaceFirst(".*\\{", "");
				break;
			}
		}
		if (edgeOp.equals("")) {
			System.out.println("DOT graph must be directed (i.e., digraph).");
			scan.close();
			System.exit(0);

		}

		// Look for edge operator -> and determine the source and destination
		// vertices for each edge.
		while (scan.hasNext()) {
			String[] substring = line.split(edgeOp);

			for (int i = 0; i < substring.length - 1; i += 2) {
				// remove " and trim whitespace from node string on the left
				String vertex1 = substring[0].replace("\"", "").trim();
				// if string is empty, try again
				if (vertex1.equals(""))
					continue;

				// do the same for the node string on the right
				String vertex2 = substring[1].replace("\"", "").trim();
				if (vertex2.equals(""))
					continue;

				// indicate edge between vertex1 and vertex2
				sources.add(vertex1);
				destinations.add(vertex2);
			}

			// do until the "}" has been read
			if (substring[substring.length - 1].indexOf("}") >= 0)
				break;

			line = scan.next();

			// Skip //-style comments.
			line = line.replaceFirst("//.*", "");
		}

		scan.close();
	}
}