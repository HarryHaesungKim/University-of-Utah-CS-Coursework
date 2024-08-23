package assign10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This class collects running times for methods of FindKLargest.
 * 
 * @author Harry Kim & Braden Morfin
 * @version November 18, 2020
 */
public class FindKLargestTimer {

	public static void main(String[] args) {

		int timesToLoop = 500;

		int incr = 1000;

		Random rng = new Random();

		for (int probSize = 1000; probSize <= 20_000; probSize += incr) {
			
			List<Integer> list = new ArrayList<Integer>(probSize);
			
			int k = probSize;
			
			for (int i = 0; i < probSize; i++) {
				list.add(i);
			}

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.

			long stopTime, midpointTime, startTime = System.nanoTime();

			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Collect running times.
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {
				FindKLargest.findKLargestHeap(list, k);
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
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / (double) timesToLoop;

			// Collect running times.
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {
				FindKLargest.findKLargestSort(list, k);
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

			System.out.println(probSize + "  " + averageTime + "  " + averageTime2 + "  " + averageTime/(probSize + k * Math.log(probSize)) + "  " + averageTime2/(probSize * Math.log(probSize)));

		}
	}
}