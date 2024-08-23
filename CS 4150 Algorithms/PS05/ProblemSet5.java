package problemSet5;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class ProblemSet5 {
	public static void main(String[] args) {

//		WhateverFirstSearch Algorithm. 

		Scanner inputScanner = new Scanner(System.in);

		// Number of rows in the maze.
		int rows = inputScanner.nextInt();

		// Number of columns in the maze.
		int columns = inputScanner.nextInt();
		
		// Game data
		char[][] maze = new char[rows][columns];
		char[][] playerVisited = new char[rows][columns];
		char[][] monsterSmell = new char[rows][columns];
		char[][] movingMonsterSmell = new char[rows][columns];
		int numEmptySpaces = 0;
		ArrayList<Integer> emptySpacesCoordsX = new ArrayList<Integer>();
		ArrayList<Integer> emptySpacesCoordsY = new ArrayList<Integer>();
		Stack<Vertex> bag = new Stack<Vertex>();
		
		int playerLocX = 0;
		int playerLocY = 0;

		for (int i = 0; i < rows; i++) {
			String row = inputScanner.next();
			for (int j = 0; j < columns; j++) {
				maze[i][j] = row.charAt(j);
				if(row.charAt(j) == 'm') {
					// f for fear muhaha.
					monsterSmell[i - 1][j] = 'f';
					monsterSmell[i + 1][j] = 'f';
					monsterSmell[i][j - 1] = 'f';
					monsterSmell[i][j + 1] = 'f';
				}
				else if(row.charAt(j) == 'p') {
					bag.push(new Vertex(i,j));
					playerLocX = i;
					playerLocY = j;
				}
			}
		}
		
		inputScanner.close();
		
		for(int i = 1; i < rows - 1; i++) {
			for(int j = 1; j < columns - 1; j++) {
				if(maze[i][j] == '.') {
					if(maze[i + 1][j] != 'p' && maze[i - 1][j] != 'p' && maze[i][j + 1] != 'p' && maze[i][j - 1] != 'p') {
						numEmptySpaces++;
						emptySpacesCoordsX.add(i);
						emptySpacesCoordsY.add(j);
					}
				}
			}
		}
				
		// Possible solution: Depth first search without recursion This is basically
		// WFS. Mark vertices as you go. check every possible position that a monster
		// can go into. Only one will give you a best solution. Keep track of the
		// monster's position and how many "treasures" the player gets in this best
		// solution.

		// Maybe checking every possible position that a monster can go into is too
		// ineffective?
		// Need to confirm. Yes this is exactly what you need to do.
		
		// Keep an independent array to keep track of data.
		
		//vertex myFristBabyVertex = new vertex(0,9);
		
		// Everything should be set up. Now run simulation.
		
		// Need to move monster around to every available position.
		// Monster cannot be right next to player.
		
		// System.out.println(exploreMaze(bag, maze, playerVisited, monsterSmell, movingMonsterSmell));
		
		// Last thing you need to do: restrict monster placement around player.
		
		int currentMin = -1;
		int currentMinCoordX = 0;
		int currentMinCoordY = 0;
		
		for(int i = 0; i < numEmptySpaces; i++) {
			
			movingMonsterSmell[emptySpacesCoordsX.get(i) + 1][emptySpacesCoordsY.get(i)] = 'f';
			movingMonsterSmell[emptySpacesCoordsX.get(i) - 1][emptySpacesCoordsY.get(i)] = 'f';
			movingMonsterSmell[emptySpacesCoordsX.get(i)][emptySpacesCoordsY.get(i) + 1] = 'f';
			movingMonsterSmell[emptySpacesCoordsX.get(i)][emptySpacesCoordsY.get(i) - 1] = 'f';
						
			int potentialMin = exploreMaze(bag, maze, playerVisited, monsterSmell, movingMonsterSmell);
						
			if(currentMin == -1 || potentialMin < currentMin) {
				currentMin = potentialMin;
				currentMinCoordX = emptySpacesCoordsX.get(i);
				currentMinCoordY = emptySpacesCoordsY.get(i);
			}
			
			movingMonsterSmell = new char[rows][columns];
			playerVisited = new char[rows][columns];
			bag.clear();
			bag.push(new Vertex(playerLocX,playerLocY));
			
		}
		
		System.out.println(currentMinCoordX + " " + currentMinCoordY);
		System.out.print(currentMin);
		
		// now loop through each possible position a monster can go into and then output the one that results in the lowest player score.
		// Problem: we need to keep the visited data not on the maze array. We can't afford to reset it every time efficiently. I would recommend another array.
		
		// For testing
//		printMaze(maze);
//		printMaze(playerVisited);
//		printMaze(monsterSmell);
//		printMaze(movingMonsterSmell);
	}
	
	/**
	 * This helper method runs the whatever first search algorithm and moves the player to every position.
	 * 
	 * @param bag
	 * @param maze
	 * @param monsterSmell
	 * @return
	 */
	public static int exploreMaze(Stack<Vertex> bag, char[][]maze, char[][] playerVisited, char[][] monsterSmell, char[][] movingMonsterSmell) {
		int playerScoreCounter = 0;
				
		// WhateverFirstSearch algorithm implementation.
		// 'S' (starting vertex) is already in bag.
		while(!bag.isEmpty()) {
			Vertex v = (Vertex) bag.pop();
			boolean isNumber = (maze[v.getX()][v.getY()] >= '0' && maze[v.getX()][v.getY()] <= '9');
			boolean visited = playerVisited[v.getX()][v.getY()] == 'v';
			
			// If v is a valid vertex, keep exploring. Else, continue to the next vertex on the stack.
			if((maze[v.getX()][v.getY()] == '.' || maze[v.getX()][v.getY()] == 'p' || isNumber) && !visited) {
				if(isNumber) {
					playerScoreCounter += (maze[v.getX()][v.getY()] - '0');
				}
				playerVisited[v.getX()][v.getY()] = 'v';
				
				// If a monster is smelled, don't add any more vertices. Exploration is halted.
				if(monsterSmell[v.getX()][v.getY()] == 'f' || movingMonsterSmell[v.getX()][v.getY()] == 'f') {
					continue;
				}
				
				// Adding all of v's vertices.
				bag.push(new Vertex(v.getX() + 1, v.getY()));
				bag.push(new Vertex(v.getX() - 1, v.getY()));
				bag.push(new Vertex(v.getX(), v.getY() + 1));
				bag.push(new Vertex(v.getX(), v.getY() - 1));
			}
		}		
		return playerScoreCounter;
	}
	
	// for testing. Delete later?
	public static void printMaze(char[][]maze) {
		System.out.println("RESULT:");
		for(int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				System.out.print(maze[i][j]);
			}
			System.out.print('\n');
		}
	}
}

class Vertex {
	int x;
	int y;
	
	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}