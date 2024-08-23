
package problemSet9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

public class ProblemSet9 {

	public static List<Integer> bestFlowPath;
	
	public static int bestMinWeight = 0;
	
	public static List<Integer> currentFlowPath;
	
	public static int currentMinWeight = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner inputScanner = new Scanner(System.in);

		// n, s, and t (in that order, space separated). The integer n is the number of
		// vertices in [3..100], the integers s and t

		int n = inputScanner.nextInt();
		int s = inputScanner.nextInt();
		int t = inputScanner.nextInt();

		// int[][] graph = new int[n][n];

		Vertex[] vertices = new Vertex[n];
		int[][] capacity = new int[n][n];
		int[][] residual = new int[n][n];
		
		List<Edge> edges = new ArrayList<Edge>();

		for (int k = 0; k < n; k++) {
			Vertex v = new Vertex(k);
			vertices[k] = v;
		}

		int totalCapacity = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int input = inputScanner.nextInt();

				residual[i][j] = input;

				if (input != 0) {
					Edge e = new Edge(i, j);
					edges.add(e);
					totalCapacity += input;
					capacity[i][j] = input;
					vertices[i].getChildren().add(j);
				}

			}
		}

		inputScanner.close();
		
		// Line 1: A sequence of integers indicating how much flow was contributed by
		// each augmenting path. (The first path you calculate will contribute some
		// flow. Print that number first. The second path you calculate will contribute
		// some flow. Print that number next. ...etc...).

		buildResidualGraph(residual, vertices, s, t, n);

		// Okay we have the residual graph. Now find the min cut and max flow.

		// System.out.println(listOfFlows.toString());

		// System.out.println(listOfFlows.stream().collect(Collectors.summingInt(Integer::intValue)));
		
		int maxFlow = 0;
		int satEdges = 0;
		
		int totalFlowST = 0;
		
		List<Integer> inS = new ArrayList<Integer>();
		
		boolean[] visited = new boolean[n];

        Stack<Integer> stack = new Stack<>();
        
        stack.push(s);
        
        while(!stack.isEmpty()) {
        	
        	int current = stack.pop();
        	
        	visited[current] = true;
        	inS.add(current);
        	
        	for(Integer i : vertices[current].getChildren()) {
        		if(!visited[i]) {
        			visited[i] = true;
        			stack.add(i);
        		}
        	}
        }
        
		int totalCapTS = 0;
		
//		for(int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++) {
//				if(capacity[i][j] == residual[j][i] && capacity[i][j] != 0) {
//					satEdges++;
//					if(inS.contains(i) && !inS.contains(j)) {
//						totalFlowST += capacity[i][j];
//						maxFlow += capacity[i][j];
//					}
//				}
//				if(capacity[i][j] == residual[i][j] && capacity[i][j] != 0 && !inS.contains(i) && inS.contains(j)) {
//					totalCapTS += capacity[i][j];
//				}
//			}
//		}
		
		for(Edge e : edges) {
			
			if(capacity[e.getu()][e.getv()] == residual[e.getv()][e.getu()] && capacity[e.getu()][e.getv()] != 0) {
				satEdges++;
				if(inS.contains(e.getu()) && !inS.contains(e.getv())) {
					totalFlowST += capacity[e.getu()][e.getv()];
					maxFlow += capacity[e.getu()][e.getv()];
				}
			}
			if(capacity[e.getu()][e.getv()] == residual[e.getu()][e.getv()] && capacity[e.getu()][e.getv()] != 0 && !inS.contains(e.getu()) && inS.contains(e.getv())) {
				totalCapTS += capacity[e.getu()][e.getv()];
			}
		}
		
		// Line 2:  A single integer indicating s-t maximum flow.
        System.out.println(maxFlow);
        
		// Line 3:  A single integer indicating the number of saturated edges in the s-t maximum flow.
        System.out.println(satEdges);
        
		// Line 4:  A sorted (increasing) sequence of integers indicating the indices of the vertices in S in the s-t cut.		
		Collections.sort(inS);
		for(Integer i : inS) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		// Line 5:  A single integer indicating the total flow across the edges from S to T (Note that S and T are sets of vertices in the min-cut).
		System.out.println(totalFlowST);
		
		// NEED TO FIX THIS!!!!!!!!!! It is done.
		// Line 6:  A single integer indicating the total capacity across the edges from T to S (Note that S and T are sets of vertices in the min-cut).
		System.out.println(totalCapTS);
		
		// Clean up code? Ehh...
	}
	
//	public static void verticesInS(int[][] residual, int[][] capacity, Vertex[] vertices, int s, int n){
//		
//		int maxFlow = 0;
//		int satEdges = 0;
//		
//		int totalFlowST = 0;
//		
//		List<Integer> inS = new ArrayList<Integer>();
//		
//		boolean[] visited = new boolean[n];
//
//        Stack<Integer> stack = new Stack<>();
//        
//        stack.push(s);
//        
//        while(!stack.isEmpty()) {
//        	
//        	int current = stack.pop();
//        	
//        	visited[current] = true;
//        	inS.add(current);
//        	
//        	for(Integer i : vertices[current].getChildren()) {
//        		if(!visited[i]) {
//        			visited[i] = true;
//        			stack.add(i);
//        		}
//        	}
//        }
//        
//		int totalCapTS = 0;
//		
//		for(int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++) {
//				if(capacity[i][j] == residual[j][i] && capacity[i][j] != 0) {
//					satEdges++;
//					if(inS.contains(i) && !inS.contains(j)) {
//						totalFlowST += capacity[i][j];
//						maxFlow += capacity[i][j];
//					}
//				}
//				if(capacity[i][j] == residual[i][j] && capacity[i][j] != 0 && !inS.contains(i) && inS.contains(j)) {
//					totalCapTS += capacity[i][j];
//				}
//			}
//		}
//		
//		// Line 2:  A single integer indicating s-t maximum flow.
//        System.out.println(maxFlow);
//        
//		// Line 3:  A single integer indicating the number of saturated edges in the s-t maximum flow.
//        System.out.println(satEdges);
//        
//		// Line 4:  A sorted (increasing) sequence of integers indicating the indices of the vertices in S in the s-t cut.		
//		Collections.sort(inS);
//		for(Integer i : inS) {
//			System.out.print(i + " ");
//		}
//		System.out.println();
//		
//		// Line 5:  A single integer indicating the total flow across the edges from S to T (Note that S and T are sets of vertices in the min-cut).
//		System.out.println(totalFlowST);
//		
//		// NEED TO FIX THIS!!!!!!!!!! It is done.
//		// Line 6:  A single integer indicating the total capacity across the edges from T to S (Note that S and T are sets of vertices in the min-cut).
//		System.out.println(totalCapTS);
//		
//		// Clean up code? Ehh...
//	}
	

	public static void buildResidualGraph(int[][] residual, Vertex[] vertices, int s, int t, int n) {
		
		boolean result = true;
		
		while(result) {
			// keep track of flows and pick the lowest one with a tiebreaker function (?).
			bestFlowPath = new ArrayList<Integer>();
			
			bestMinWeight = 0;
			
			currentFlowPath = new ArrayList<Integer>();
			
			currentMinWeight = 0;
			
			result = false;
			
			result = dfs(new boolean[n], residual, vertices, s, t, result);
			
			// Build residual graph.
			for(int i = 1; i < bestFlowPath.size(); i++) {
            	residual[bestFlowPath.get(i - 1)][bestFlowPath.get(i)] -= bestMinWeight;
            	
            	if(residual[bestFlowPath.get(i - 1)][bestFlowPath.get(i)] == 0) {
            		vertices[bestFlowPath.get(i - 1)].getChildren().remove(bestFlowPath.get(i));
            	}
            	else {
            		if(!vertices[bestFlowPath.get(i - 1)].getChildren().contains(bestFlowPath.get(i))) {
            			vertices[bestFlowPath.get(i - 1)].getChildren().add(bestFlowPath.get(i));
            		}
            	}
            	
            	residual[bestFlowPath.get(i)][bestFlowPath.get(i - 1)] += bestMinWeight;
            	
            	if(residual[bestFlowPath.get(i)][bestFlowPath.get(i - 1)] == 0) {
            		vertices[bestFlowPath.get(i)].getChildren().remove(bestFlowPath.get(i - 1));
            	}
            	else {
            		if(!vertices[bestFlowPath.get(i)].getChildren().contains(bestFlowPath.get(i - 1))) {
            			vertices[bestFlowPath.get(i)].getChildren().add(bestFlowPath.get(i - 1));
            		}
            	}
            	
			}
			
			if(result) {
	    		System.out.print(bestMinWeight + " ");
			}
		}
		System.out.println();
	}
	
	public static boolean dfs(boolean[] visited, int[][] residual, Vertex[] vertices, int s, int t, boolean result) {
		
		// initial minWeight will be 0.
		
		// initial result == false;
		
        // Print the current node
        // System.out.print(s + " ");
		
		currentFlowPath.add(s);
		
		if(currentFlowPath.size() > bestFlowPath.size() && bestFlowPath.size() != 0) {
			return result;
		}
        
        if(s == t) {
        	
//        	System.out.println("Data: ");
//    		System.out.println(currentFlowPath.toString());
//    		System.out.println(currentMinWeight);
//    		System.out.println(bestFlowPath.toString());
//    		System.out.println(bestMinWeight);
        	
        	// Tie-breaker.
        	if(bestFlowPath.isEmpty() ||  (currentFlowPath.size() < bestFlowPath.size() && currentFlowPath.size() > 0) ) {
        		bestFlowPath = new ArrayList<Integer>(currentFlowPath);
        		bestMinWeight = currentMinWeight;
        		
//            	System.out.println("Data: ");
//        		System.out.println(currentFlowPath.toString());
//        		System.out.println(currentMinWeight);
//        		System.out.println(bestFlowPath.toString());
//        		System.out.println(bestMinWeight);
        		
//        		currentFlowPath.remove(currentFlowPath.size() - 1);
        		// currentMinWeight = 0;
        	}
        	else if(currentFlowPath.size() == bestFlowPath.size()) {
        		if(currentMinWeight > bestMinWeight) {
            		bestFlowPath = new ArrayList<Integer>(currentFlowPath);
            		bestMinWeight = currentMinWeight;
            		
//                	System.out.println("Data: ");
//            		System.out.println(currentFlowPath.toString());
//            		System.out.println(currentMinWeight);
//            		System.out.println(bestFlowPath.toString());
//            		System.out.println(bestMinWeight);
            		
            		// currentFlowPath.remove(currentFlowPath.size() - 1);
            		// currentMinWeight = 0;
        		}
        		else if(currentMinWeight == bestMinWeight) {
            		for(int i = currentFlowPath.size() - 1; i >= 0; i--) {
            			if(currentFlowPath.get(i) < bestFlowPath.get(i)) {
                    		bestFlowPath = new ArrayList<Integer>(currentFlowPath);
                    		bestMinWeight = currentMinWeight;
                    		
//                        	System.out.println("Data: ");
//                    		System.out.println(currentFlowPath.toString());
//                    		System.out.println(currentMinWeight);
//                    		System.out.println(bestFlowPath.toString());
//                    		System.out.println(bestMinWeight);
                    		
                    		// currentFlowPath.remove(currentFlowPath.size() - 1);
                    		// currentMinWeight = 0;
                    		break;
            			}
            			// else, continue;
            		}
        		}
        	}
        	
        	// There is an augmenting path within the residual graph.
        	result = true;
        	return result;
        }
         
        // Set current node as visited
        visited[s] = true;
 
        // For every node of the graph
        // for (int i = 0; i < residual[s].length; i++) {
        
        for(Integer i: vertices[s].getChildren()) {
 
            // If some node is adjacent to the current node and it has not already been visited and the weight to that node is not zero.
            if (residual[s][i] != 0 && (!visited[i])) {
            	
            	int oldMin = 0 + currentMinWeight;
            	
            	if(currentMinWeight == 0 || currentMinWeight > residual[s][i]) {
            		currentMinWeight = residual[s][i];
            	}
            	
            	
            	result = dfs(visited, residual, vertices, i, t, result);
            	
        		currentFlowPath.remove(currentFlowPath.size() - 1);
        		currentMinWeight = oldMin;
        		visited[i] = false;
        		
//            	System.out.println("Data: ");
//        		System.out.println(currentFlowPath.toString());
//        		System.out.println(currentMinWeight);
//        		System.out.println(bestFlowPath.toString());
//        		System.out.println(bestMinWeight);
            	
            	// When dfs is returning, we can update the residual graph and the flow!
            	// Maybe flow? Do we even need the flow? No. if capacity[i][j] == residual [j][i], saturated edge. Print or something.
            	
//            	if(result == true) {
////                	residual[s][i] -= currentMinWeight;
////                	residual[i][s] += currentMinWeight;
//            		return result;
//            	}
            }
        }
        
        return result;
	}

//	public static int findSmallestWeightFromPath(LinkedList<Integer> path, int[][] residualGraph) {
//		int minWeight = residualGraph[path.get(0)][path.get(1)];
//		for (int i = 2; i < path.size(); i++) {
//			if (minWeight > residualGraph[path.get(i - 1)][path.get(i)]) {
//				minWeight = residualGraph[path.get(i - 1)][path.get(i)];
//			}
//		}
//
//		return minWeight;
//	}

}

class Vertex {
	private int id;
	private LinkedList<Integer> children = new LinkedList<Integer>();

	Vertex(int id) {
		this.id = id;
	}

	public LinkedList<Integer> getChildren() {
		return children;
	}
}

class Edge {
	private int u;
	private int v;

	Edge(int u, int v) {
		this.u = u;
		this.v = v;
	}
	
	public int getu() {
		return u;
	}
	
	public int getv() {
		return v;
	}
}