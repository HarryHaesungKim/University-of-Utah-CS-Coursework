package assign09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * This class collects running times for methods of HashTable and HashMap.
 * 
 * @author Harry Kim & Braden Morfin
 * @version November 4, 2020
 */
public class HashTableTimer {

	public static void main(String[] args) {

		int timesToLoop = 500;

		int incr = 1000;
		
		Random rng = new Random();
		
		for(int probSize = 1000; probSize <= 20_000; probSize += incr) {
			
			HashTable<Integer, String> ourTable = new HashTable<Integer, String>();
			HashMap<Integer, String> javasMap = new HashMap<Integer, String>();
			
			// Putting keys and values into both our HashTable and Java's HashMap
			for(int i = 0; i < probSize; i++)
			{
				String value = "";
				for(int j = 0; j < 10; j++)
				{
					value = value + new Character((char) ((char) rng.nextInt(25) + 97)).toString();
				}
				ourTable.put(i, value);
				javasMap.put(i, value);
			}
			
	  
			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.

			long stopTime, midpointTime, startTime = System.nanoTime();

			while(System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Collect running times.
			startTime = System.nanoTime();
			for(int i = 0; i < timesToLoop; i++) {
				// used for testing put
				//ourTable.put(probSize, "wauifhiaw");
				
				//used for testing remove
				//ourTable.remove(probSize/2);
				
				//used for testing get
				ourTable.get(rng.nextInt(1000));
				
			}

			midpointTime = System.nanoTime();

			// Capture the cost of running the loop and any other operations done
			// above that are not the essential method call being timed.
			for(int i = 0; i < timesToLoop; i++) {
				// used for testing get
				rng.nextInt(1000);
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and searching.
			// Average it over the number of runs.
			double averageTime = ((midpointTime - startTime) - 
						(stopTime - midpointTime)) / (double) timesToLoop;
			
		

			// Collect running times.
			startTime = System.nanoTime();
			for(int i = 0; i < timesToLoop; i++) {
				// used for testing put
				//javasMap.put(probSize, "fjaiwohf");
				
				// used for testing remove
				javasMap.remove(probSize/2);
				
				// used for testing get
				javasMap.get(rng.nextInt(1000));

			}

			midpointTime = System.nanoTime();

			// Capture the cost of running the loop and any other operations done
			// above that are not the essential method call being timed.
			for(int i = 0; i < timesToLoop; i++) {
				// used for testing get
				rng.nextInt(1000);
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and searching.
			// Average it over the number of runs.
			double averageTime2 = ((midpointTime - startTime) - 
						(stopTime - midpointTime)) / (double) timesToLoop;
			
			
			System.out.println(probSize + "  " + averageTime + "  " + averageTime2);
			
		}
	}
}