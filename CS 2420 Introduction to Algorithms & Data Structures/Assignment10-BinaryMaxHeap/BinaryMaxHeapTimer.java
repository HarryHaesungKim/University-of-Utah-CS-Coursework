package assign10;

import java.util.Random;

/**
 * This class collects running times for methods of BinaryMaxHeap.
 * 
 * @author Harry Kim & Braden Morfin
 * @version November 18, 2020
 */
public class BinaryMaxHeapTimer {

	public static void main(String[] args) {
		Random rng = new Random();

		int timesToLoop = 10000;

		int incr = 100000;
				
		for (int probSize = 100000; probSize <= 2000000; probSize += incr) {

			BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
			BinaryMaxHeap<Integer> heap2 = new BinaryMaxHeap<Integer>();

			for (int i = 0; i < probSize; i++) {
				heap.add(rng.nextInt(probSize)); // best case of insert: logN + 1 -> O(logN)
				heap2.add(rng.nextInt(probSize)); // best case of insert: logN + 1 -> O(logN)
			}
			
			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.

			long stopTime, midpointTime, startTime = System.nanoTime();

			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Collect running times for add.
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {
				heap.add(i);
				heap.balancer(1); // remove element from priority queue to keep size at N
			}

			midpointTime = System.nanoTime();

			// Capture the cost of running the loop and any other operations done
			// above that are not the essential method call being timed.
			for (int i = 0; i < timesToLoop; i++) {
				heap.balancer(0);
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and searching.
			// Average it over the number of runs.
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / (double) timesToLoop;
			
			
			// Collect running times for peek.
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {
				heap2.peek();
			}

			midpointTime = System.nanoTime();

			// Capture the cost of running the loop and any other operations done
			// above that are not the essential method call being timed.
			for (int i = 0; i < timesToLoop; i++) {
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and searching.
			// Average it over the number of runs.
			double averageTime2 = ((midpointTime - startTime) - (stopTime - midpointTime)) / (double) timesToLoop;
			
			
			// Collect running times for extractMax.
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {
				heap2.extractMax();
				heap2.add(rng.nextInt(probSize)); // adds back element from heap to keep size at N
			}

			midpointTime = System.nanoTime();

			// Capture the cost of running the loop and any other operations done
			// above that are not the essential method call being timed.
			for (int i = 0; i < timesToLoop; i++) {
				rng.nextInt(probSize);
				heap2.add(0);
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and searching.
			// Average it over the number of runs.
			double averageTime3 = ((midpointTime - startTime) - (stopTime - midpointTime)) / (double) timesToLoop;
			
			
			System.out.println(probSize + "  " + averageTime + "  " + averageTime2 + "  " + averageTime3 + "  " + averageTime3/Math.log(probSize));

		}
	}
}