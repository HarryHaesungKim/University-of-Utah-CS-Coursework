package problemSet3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProblemSet3 {

	public static long currentMin;

	public static int currentMinNumOfShops;

	public static long independentIslandIndexes;

	public static long allIslandsUsedLong;

	public static void main(String[] args) {

		Scanner inputScanner = new Scanner(System.in);

		// Number of destinations N.
		int N = inputScanner.nextInt();

		// Number of direct ferry routes R.
		int R = inputScanner.nextInt();

		// Array of longs to hold starting and ending points.
		long[] islands = new long[N + 1];
		for (int i = 0; i < N + 1; i++) {
			islands[i] = 0;
		}

		// Gathering inputs for islands and adding them to a "matrix".
		for (int i = 0; i < R; i++) {
			int pointA = inputScanner.nextInt();
			int pointB = inputScanner.nextInt();
			islands[pointA] = islands[pointA] | (1L << pointB);
			islands[pointB] = islands[pointB] | (1L << pointA);
			islands[pointA] = islands[pointA] | (1L << pointA);
			islands[pointB] = islands[pointB] | (1L << pointB);
		}

		inputScanner.close();

		// Accounting for independent islands (so we don't have to mess with them during
		// recursion and mess everything up.
		long islandsWithShops = 0;
		long islandsUsed = 0;
		int numIslandsWithShops = 0;
		for (int i = 1; i < islands.length; i++) {
			if (islands[i] == 0) {
				islandsWithShops = islandsWithShops | (1L << i);
				islandsUsed = islandsUsed | (1L << i);
				numIslandsWithShops++;
			}
		}
		
		// Calculating all islands used early so that we just have to compare.
		allIslandsUsedLong = (long) (Math.pow(2, islands.length) - 2);

		// All independent islands are now accounted for. If every island is
		// independent, it will just return immediately.
		backTracking(0, islandsUsed, islandsWithShops, numIslandsWithShops, islands);
		
		// Printing the result.
		System.out.println(currentMinNumOfShops);
		for (int i = 0; i < islands.length; i++) {
			if ((currentMin | 1) == currentMin) {
				System.out.print(i + " ");
			}
			currentMin = currentMin >> 1;
		}
	}

	public static void backTracking(int currentIndex, long islandsUsed, long islandsWithShops, int numIslandsWithShops, long[] islands) {

		// Base case;
		if (islandsUsed == allIslandsUsedLong) {
			currentMin = islandsWithShops;
			currentMinNumOfShops = numIslandsWithShops;
			return;
		}

		else {

			// Going through each island
			for (int i = 1; i < islands.length; i++) {

				// If an edge of the same set already exists.
				// If an island doesn't already have a sandwich shop.
				// If there currently is no minimum set or if the number of Islands with shops
				// is still less than the current min. And if the island is not independent.
				if ((i > currentIndex || (islands[i] & islands[currentIndex]) != (islands[currentIndex] & islands[i]))
						&& ((islandsWithShops | (1L << i)) != islandsWithShops)
						&& ((currentMin == 0 || numIslandsWithShops + 1 < currentMinNumOfShops) && islands[i] != 0
								&& (islandsUsed | islands[i]) > islandsUsed)) {

					// Increase the number of sandwich shops.
					numIslandsWithShops++;

					// Recursion with new index, islands used with new shop added, adding islands
					// with shops, new incremented numIslandWithShops, and the islands array.
					backTracking(i, (islandsUsed | islands[i]), (islandsWithShops | (1L << i)), numIslandsWithShops,
							islands);

					// Decrement the number of shops when you come back.
					numIslandsWithShops--;
				}
			}
		}
	}
}