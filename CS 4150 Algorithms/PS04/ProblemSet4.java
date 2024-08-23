package problemSet4;

import java.util.Scanner;

public class ProblemSet4 {
	public static void main(String[] args) {

//		On the first line of console input, your program will receive an integer n, the number of production
//		lines in the range [2..5], and then an integer s, the number of production steps in the range [1..600].
//		The next n lines will provide the step costs for manufacturing line.  Each line will provide s integers in the range [1..100],
//		The last line will provide the switching costs for switching between lines.  This line will provide s-1 integers
//		(because switching is only possible between steps) in the range [1..30].

		Scanner inputScanner = new Scanner(System.in);

		// Number of number of production lines in the range [2..5].
		int n = inputScanner.nextInt();

		// Number of production steps in the range [1..600].
		int s = inputScanner.nextInt();
		
		// No lines or no steps means no time.
		if (n == 0 || s==0) {
			System.out.println(0);
			System.out.println(0);
			return;
		}

		// All the lines with each of their steps
		int[][] lines = new int[n + 1][s];

		// The cost table
		int[][] cost = new int[n][s];

		// The step track table
		int[][] stepTrack = new int[n][s];

		// Listing the step cost for manufacturing lines.
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < s; j++) {
				lines[i][j] = inputScanner.nextInt();
			}
			// First cost.
			cost[i][0] = lines[i][0];
		}

		// Costs for switching between lines.
		for (int k = 0; k < s - 1; k++) {
			lines[n][k] = inputScanner.nextInt();
		}

		// System.out.println(lines.length);

		// We will always start at the assembly line with the lowest first number.
		// Build a table of all subproblems then after the table is filled out, find the
		// optimum path.
		// which position will take the least amount of time to get their and have their
		// time added to that time?

		// Two tables: one to keep track of the time cost and one to keep track of the
		// line you are coming from
		// Build from the back
		// Need a find min function.

		// Listing the step cost for manufacturing lines.

		int minTime = -1;
		int minIndex = -1;

		if (s == 1) {
			for (int i = 0; i < n; i++) {
				if (minTime == -1 || cost[i][0] < minTime) {
					minTime = cost[i][0];
					minIndex = i + 1;
				}
			}
			System.out.println(minTime);
			System.out.println(minIndex);
			return;
		}
		
		else {
			for (int j = 1; j < s; j++) {
				for (int i = 0; i < n; i++) {
					cost[i][j] = shortestCost(i, j, lines[n][j - 1], lines, cost, stepTrack);
					if ((j == s - 1) && (minTime == -1 || cost[i][j] < minTime)) {
						minTime = cost[i][j];
						minIndex = i;
						stepTrack[i][j] = i + 1;
					}
				}
			}
		}

		System.out.println(minTime);

		String path = "";

		for (int k = s - 1; k >= 0; k--) {
			if (k == 0) {
				path += stepTrack[minIndex][k];
				break;
			}
			path += stepTrack[minIndex][k] + " ";
			minIndex = stepTrack[minIndex][k] - 1;
		}

		System.out.println(new StringBuilder(path).reverse().toString());
	}

	public static int shortestCost(int currentLine, int currentStep, int currentSwitchCost, int[][] lines, int[][] cost,
			int[][] stepTrack) {
		int currentMin = -1;
		for (int i = 0; i < lines.length - 1; i++) {
			if (i == currentLine) {
				if (currentMin == -1 || (cost[i][currentStep - 1] + lines[currentLine][currentStep]) < currentMin) {
					currentMin = cost[i][currentStep - 1] + lines[currentLine][currentStep];
					// Keeping track of the step it came from;
					stepTrack[currentLine][currentStep - 1] = i + 1;
				}
			} else {
				if (currentMin == -1 || (cost[i][currentStep - 1] + lines[currentLine][currentStep]
						+ currentSwitchCost) < currentMin) {
					currentMin = cost[i][currentStep - 1] + lines[currentLine][currentStep] + currentSwitchCost;
					// Keeping track of the step it came from;
					stepTrack[currentLine][currentStep - 1] = i + 1;
				}
			}
		}
		return currentMin;
	}
}