package problemSet6;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class ProblemSet6 {

//	static boolean cycleTrigger = false;
// throw random test data
	// edge cases: test if cycle detection is working properly. Try coop quests that are pre reqs to other coop quests.

	public static void main(String[] args) {
		Map<String, Vertex> player1 = new HashMap<>();
		int player1NumQuests = 0;
		Map<String, Vertex> player2 = new HashMap<>();
		int player2NumQuests = 0;
		Queue<String> coop = new LinkedList<>();
		int coopNumQuests = 0;

		Scanner inputScanner = new Scanner(System.in);

		// Filling out player1 quests.
		player1NumQuests = inputScanner.nextInt();
		for (int i = 0; i < player1NumQuests; i++) {
			String nameOfVertex = inputScanner.next();
			String outgoingVertex = inputScanner.next();

			// Adding or updating the vertex to the map.
			if (player1.containsKey(nameOfVertex)) {
				player1.get(nameOfVertex).addOutgoingNeighbor(outgoingVertex);
			} else {
				Vertex thisVertex = new Vertex(nameOfVertex);
				thisVertex.addOutgoingNeighbor(outgoingVertex);
				player1.put(nameOfVertex, thisVertex);
			}

			// Adding or updating the outgoing vertex to the map.
			if (player1.containsKey(outgoingVertex)) {
				player1.get(outgoingVertex).addIncomingNeighbor(nameOfVertex);
			} else {
				Vertex thisVertex = new Vertex(outgoingVertex);
				thisVertex.addIncomingNeighbor(nameOfVertex);
				player1.put(outgoingVertex, thisVertex);
			}
		}

		// Filling out player2 quests.
		player2NumQuests = inputScanner.nextInt();
		for (int i = 0; i < player2NumQuests; i++) {
			String nameOfVertex = inputScanner.next();
			String outgoingVertex = inputScanner.next();

			// Adding or updating the vertex to the map.
			if (player2.containsKey(nameOfVertex)) {
				player2.get(nameOfVertex).addOutgoingNeighbor(outgoingVertex);
			} else {
				Vertex thisVertex = new Vertex(nameOfVertex);
				thisVertex.addOutgoingNeighbor(outgoingVertex);
				player2.put(nameOfVertex, thisVertex);
			}

			// Adding or updating the outgoing vertex to the map.
			if (player2.containsKey(outgoingVertex)) {
				player2.get(outgoingVertex).addIncomingNeighbor(nameOfVertex);
			} else {
				Vertex thisVertex = new Vertex(outgoingVertex);
				thisVertex.addIncomingNeighbor(nameOfVertex);
				player2.put(outgoingVertex, thisVertex);
			}
		}

		// Filling out coop quests.
		coopNumQuests = inputScanner.nextInt();
		for (int i = 0; i < coopNumQuests; i++) {
			String input = inputScanner.next();
			coop.add(input);
		}

		inputScanner.close();

		// Combining the two quests
		Map<String, Vertex> allQuests = new HashMap<>();
		for (Map.Entry<String, Vertex> pair : player1.entrySet()) {
			// If quest is a coop
			if (coop.contains(pair.getKey())) {
				Vertex v = new Vertex(pair.getKey());
				for (String outNeighbor : pair.getValue().getOutgoingNeighbors()) {
					if(coop.contains(outNeighbor)) {
						v.addOutgoingNeighbor(outNeighbor);
						continue;
					}
					v.addOutgoingNeighbor(outNeighbor + "-1");
				}
				for (String inNeighbor : pair.getValue().getIncomingNeighbors()) {
					if(coop.contains(inNeighbor)) {
						v.addIncomingNeighbor(inNeighbor);
						continue;
					}
					v.addIncomingNeighbor(inNeighbor + "-1");
				}
				allQuests.put(pair.getKey(), v);
			}
			// If quest is solo
			else {
				Vertex v = new Vertex(pair.getKey() + "-1");
				for (String outNeighbor : pair.getValue().getOutgoingNeighbors()) {
					if(coop.contains(outNeighbor)) {
						v.addOutgoingNeighbor(outNeighbor);
						continue;
					}
					v.addOutgoingNeighbor(outNeighbor + "-1");
				}
				for (String inNeighbor : pair.getValue().getIncomingNeighbors()) {
					if(coop.contains(inNeighbor)) {
						v.addIncomingNeighbor(inNeighbor);
						continue;
					}
					v.addIncomingNeighbor(inNeighbor + "-1");
				}
				allQuests.put(pair.getKey() + "-1", v);
			}
		}
		
		for (Map.Entry<String, Vertex> pair : player2.entrySet()) {
			if (coop.contains(pair.getKey())) {
				for (String outNeighbor : pair.getValue().getOutgoingNeighbors()) {
					if(coop.contains(outNeighbor)) {
						allQuests.get(pair.getKey()).addOutgoingNeighbor(outNeighbor);
						continue;
					}
					allQuests.get(pair.getKey()).addOutgoingNeighbor(outNeighbor + "-2");
				}
				for (String inNeighbor : pair.getValue().getIncomingNeighbors()) {
					if(coop.contains(inNeighbor)) {
						allQuests.get(pair.getKey()).addIncomingNeighbor(inNeighbor);
						continue;
					}
					allQuests.get(pair.getKey()).addIncomingNeighbor(inNeighbor + "-2");
				}
			}
			
			else {
				
				Vertex v = new Vertex(pair.getKey() + "-2");
				
				for (String outNeighbor : pair.getValue().getOutgoingNeighbors()) {
					if(coop.contains(outNeighbor)) {
						v.addOutgoingNeighbor(outNeighbor);
						continue;
					}
					v.addOutgoingNeighbor(outNeighbor + "-2");
				}
				for (String inNeighbor : pair.getValue().getIncomingNeighbors()) {
					if(coop.contains(inNeighbor)) {
						v.addIncomingNeighbor(inNeighbor);
						continue;
					}
					v.addIncomingNeighbor(inNeighbor + "-2");
				}
				
				
				allQuests.put(pair.getKey() + "-2", v);
			}
		}

		// Tests (will delete later)
//		System.out.println("Test");

		// toposort works. Now need to implelemt print and stuff.

		// New approach: start at the last vertex in either p1TopoSort or p2TopoSort.
		// Backtrack and follow the path of said vertex until it gives us a path of the
		// coop quests.
		// See if you can do the same thing with the other array (same steps of the coop
		// quests). If you cannot, it is unsolvable.
		// If same path is found, print it.
		// In the case that one or both of the graphs are disconnected, same process
		// until you run out of vertices. Then get the next lowest vertex that has not
		// been accounted for and start a new path up.
		// See if you can do the same thing with the other array with the same steps
		// except the disconnected graph can move around the order of its subgraphs
		// (group of strings with contains?).
		String[] sorted = new String[allQuests.size()];
		
		sorted = topologiclaSort(allQuests, sorted);
		
//		if (cycleTrigger) {
//			System.out.print("Unsolvable");
//			return;
//		}
		
		for(int i = 0; i < allQuests.size(); i++) {
			System.out.println(sorted[i]);
		}
	}

	// "clock" references the last position of the "array"

	public static String[] topologiclaSort(Map<String, Vertex> graph, String[] sorted) {
		int clockPos = graph.size() - 1;

		// Iterating HashMap through for loop
		for (Map.Entry<String, Vertex> set : graph.entrySet()) {
			if (set.getValue().getStatus().equals("new")) {
				clockPos = topSortDFS(set.getValue(), clockPos, graph, sorted);
//				if (cycleTrigger) {
//					return sorted;
//				}
			}
		}
		return sorted;
	}

	public static int topSortDFS(Vertex v, int clockPos, Map<String, Vertex> graph, String[] sorted) {
		v.setStatus("active");
		for (String neighbor : v.getOutgoingNeighbors()) {
			if (graph.get(neighbor).getStatus().equals("new")) {
				clockPos = topSortDFS(graph.get(neighbor), clockPos, graph, sorted);
			}
			else if (graph.get(neighbor).getStatus().equals("active")) {
				System.out.println("Unsolvable");
				System.exit(0);
			}
		}
		v.setStatus("finished");
		sorted[clockPos] = v.getName();
		clockPos--;
		return clockPos;
	}

}

class Vertex {
//	String name;
	Set<String> outgoingNeighbors;
	Set<String> incomingNeighbors;
	String status;
	String name;

//	public Vertex(String name, Set<String> neighbors) {
//		this.name = name;
//		this.neighbors = neighbors;
//	}

	public Vertex(String name) {
		outgoingNeighbors = new HashSet<>();
		incomingNeighbors = new HashSet<>();
		status = "new";
		this.name = name;
	}

	public void addOutgoingNeighbor(String neighbor) {
		this.outgoingNeighbors.add(neighbor);
	}

	public void addIncomingNeighbor(String neighbor) {
		this.incomingNeighbors.add(neighbor);
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<String> getOutgoingNeighbors() {
		return outgoingNeighbors;
	}

	public Set<String> getIncomingNeighbors() {
		return incomingNeighbors;
	}

	public String getStatus() {
		return status;
	}

	public String getName() {
		return name;
	}
}