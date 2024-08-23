package assign09;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class collects running times for methods of SimplePriorityQueue.
 * 
 * @author Erin Parker & Harry Kim & Braden Morfin
 * @version September 16, 2020
 */
public class StudentHashFunctionTimer {

	public static void main(String[] args) {

		int timesToLoop = 300;

		int incr = 100;

		Random rng = new Random();

		for (int probSize = 100; probSize <= 2_000; probSize += incr) {

			HashTable<StudentBadHash, Integer> studentBad = new HashTable<StudentBadHash, Integer>();
			HashTable<StudentMediumHash, Integer> studentMedium = new HashTable<StudentMediumHash, Integer>();
			HashTable<StudentGoodHash, Integer> studentGood = new HashTable<StudentGoodHash, Integer>();

			for (int i = 0; i < probSize; i++) {

				// Creates random strings to put into the student hashes.
				String firstName = "";
				for (int j = 0; j < 5; j++) {
					firstName = firstName + new Character((char) ((char) rng.nextInt(25) + 97)).toString();
				}
				String lastName = "";
				for (int j = 0; j < 5; j++) {
					lastName = lastName + new Character((char) ((char) rng.nextInt(25) + 97)).toString();
				}

				studentGood.put(new StudentGoodHash(i, firstName, lastName), i);
				studentBad.put(new StudentBadHash(i, firstName, lastName), i);
				studentMedium.put(new StudentMediumHash(i, firstName, lastName), i);
			}

			String firstName = "";
			String lastName = "";
			for (int i = 0; i < (probSize - 8) / 2; i++) {
				firstName = firstName + new Character((char) ((char) rng.nextInt(25) + 97)).toString();
				lastName = lastName + new Character((char) ((char) rng.nextInt(25) + 97)).toString();

			}
			StudentBadHash bad = new StudentBadHash(23456789, firstName, lastName);
			StudentMediumHash medium = new StudentMediumHash(23456789, firstName, lastName);
			StudentGoodHash good = new StudentGoodHash(23456789, firstName, lastName);

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.

			long stopTime, midpointTime, startTime = System.nanoTime();

			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Collect running times for StudentBadHash.
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {
				int hash = bad.hashCode();
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
			double averageBadTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / (double) timesToLoop;

			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Collect running times for StudentMediumHash.
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {
				int hash = medium.hashCode();
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
			double averageMediumTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / (double) timesToLoop;

			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Collect running times for StudentGoodHash.
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {
				int hash = good.hashCode();
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
			double averageGoodTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / (double) timesToLoop;

			System.out.println(probSize + "  " + averageBadTime + "  " + averageMediumTime + "  " + averageGoodTime
					+ "  " + studentBad.collisions() + "  " + studentMedium.collisions() + "  "
					+ studentGood.collisions() + "  ");

		}
	}
}