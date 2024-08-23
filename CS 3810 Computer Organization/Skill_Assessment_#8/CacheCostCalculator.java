import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is a cache simulator -- it allows a user to select a cache
 * architecture and input a list of memory addresses. The simulator will
 * simulate accessing the memory addresses twice. The first pass fills the
 * cache. The second pass through the memory addresses will have hits and misses
 * within the cache, and the program will keep track of these hits and misses.
 * Results will be output to the user.
 * 
 * See CS 3810, Assignment #8 for more details.
 * 
 * @author Peter Jensen (starting code)
 * @author <your name here>
 * @version Fall 2021
 */
public class CacheCostCalculator {
	// Students are welcome to add constants, fields, or helper methods to their
	// class. If you want additional
	// classes, feel free to put additional private classes at the end of this file.
	// (One .java file only.)

	/**
	 * This helper method computes the ceiling of log base 2 of some number n. (Any
	 * fractional log is rounded up.) For example: logBase2(8) returns 3,
	 * logBase2(9) returns 4.
	 * 
	 * This method is being provided to help students when they need to solve for x
	 * in 2^x = n.
	 * 
	 * @param n any positive integer
	 * @return the ceiling of log_2(n)
	 */
	public int logBase2(int n) {
		int x = 0;
		long twoToTheXth = 1;
		while (twoToTheXth < n) {
			x++;
			twoToTheXth *= 2;
		}
		return x;
	}

	/**
	 * Application entry point.
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		// Working in main is not great. Instead, let's just create an object
		// and use the run method below to do all the work. This way, we can
		// create fields and helper methods without them having to be static. :)
		// (The work really begins in 'run', below.)

		new CacheCostCalculator().run();
	}

	/**
	 * Empty constructor -- feel free to add code if needed.
	 */
	public CacheCostCalculator() {
		// Constructor does nothing.
	}

	/**
	 * Gathers input, runs the simulation, and produces output.
	 */
	public void run() {
		// Scan keyboard input from the console.

		Scanner userInput = new Scanner(System.in);

		// Determine which cache architecture is to be used.
		// Caution: Do not change!!! My autograder will expect these
		// exact prompts / responses.

		System.out.println("Cache simulator Fall 2021");
		System.out.println("  (D)irect-mapped");
		System.out.println("  (S)et associative");
		System.out.println("  (F)ully associative");
		System.out.print("Enter a letter to select a cache and press enter: ");

		String choice = userInput.next(); // Get the first 'word' typed by the user.
		choice = choice.toUpperCase(); // Make it uppercase for consistency.

		boolean simulateDirectMapped = choice.startsWith("D");
		boolean simulateSetAssociative = choice.startsWith("S");
		boolean simulateFullyAssociative = choice.startsWith("F");

		// Each cache type has different customizations. Get these inputs from the user.
		// Note: All these variables are not needed. You may rename them, but you
		// MUST NOT CHANGE THE ORDER OF INPUTS. The autograder will give the inputs
		// in the order coded below.

		int entryDataBytes = 0;
		int directRows = 0;
		int setWays = 0;
		int fullEntries = 0;

		// In all caches, we need to know how many data bytes are cached in each entry.
		// Note that we are counting on this being a power of two. (required)

		System.out.println();
		System.out.print("How many data bytes will be in each cache entry? ");
		entryDataBytes = userInput.nextInt(); // Must be a power of two

		// Each cache will require different parameters...

		if (simulateDirectMapped) {
			System.out.print("How many direct-mapped rows will there be? ");
			directRows = userInput.nextInt(); // Must be a power of two
		} else if (simulateSetAssociative) {
			System.out.print("How many direct-mapped rows will there be? ");
			directRows = userInput.nextInt(); // Must be a power of two

			System.out.print("How many 'ways' will there be for each row? ");
			setWays = userInput.nextInt(); // Any positive integer is OK
		} else if (simulateFullyAssociative) {
			System.out.print("How many entries will be in this fully associative cache? ");
			fullEntries = userInput.nextInt(); // Any positive integer is OK
		}

		// The last step is to gather the addresses. We will allow an unlimited number
		// of addresses.
		// Each address represents a memory request (a read from memory).

		List<Integer> addressList = new ArrayList<Integer>(); // Some students may prefer a list.
		int[] addresses; // Some students may prefer an array.

		System.out.println("Enter a whitespace-separated list of addresses, type 'done' followed by enter at the end:");
		while (userInput.hasNextInt())
			addressList.add(userInput.nextInt());

		userInput.close();

		// The input was gathered in a list. Make an array from it. Students may use the
		// array and/or the list
		// for their own purposes.

		addresses = new int[addressList.size()];
		for (int i = 0; i < addressList.size(); i++)
			addresses[i] = addressList.get(i);

		// Done gathering inputs. Simulation code should be added below.

		// Step #1 - students should complete a few computations and update the output
		// statements below. Do not change the text, only add integer answers to the
		// output. (No floating point results.)

		/*
		 * TODO - Compute the total storage size of the cache. Assume 32 bit addresses.
		 */

		/* Your work here. */

		// 32 cached bytes *

		int LRUBitSize = 0;

		int entriesPerCache = 0;

		int totalCacheStorageBits = 0;

		if (simulateDirectMapped) {
			entriesPerCache = directRows;
			totalCacheStorageBits = (entriesPerCache * (1 + (32 - log2(entryDataBytes) - log2(directRows)) + (entryDataBytes * 8) + LRUBitSize));
		}

		else if (simulateSetAssociative) {
			LRUBitSize = Integer.toBinaryString(setWays - 1).length();
			entriesPerCache = directRows * setWays;
			totalCacheStorageBits = (entriesPerCache * (1 + (32 - log2(entryDataBytes) - log2(directRows)) + (entryDataBytes * 8) + LRUBitSize));
		}

		else if (simulateFullyAssociative) {
			LRUBitSize = Integer.toBinaryString(fullEntries - 1).length();
			entriesPerCache = fullEntries;
			directRows = 1;
			totalCacheStorageBits = (entriesPerCache * (1 + (32 - log2(entryDataBytes) - log2(directRows)) + (entryDataBytes * 8) + LRUBitSize));
		}

		// Report the various properties of the cache. Reminder: Do not change the
		// messages
		// or printing order. Replace the "fix_me" with a calculation or variable in
		// each case.

		System.out.println();
		System.out.println("Number of address bits used as offset bits:        " + log2(entryDataBytes));
		System.out.println("Number of address bits used as row index bits:     " + log2(directRows)); // Should be 0 for fully associative cache
		System.out.println("Number of address bits used as tag bits:           " + (32 - log2(entryDataBytes) - log2(directRows)));
		System.out.println();

		System.out.println("Number of valid bits needed in each cache entry:   " + 1);
		System.out.println("Number of tag bits stored in each cache entry:     " + (32 - log2(entryDataBytes) - log2(directRows)));
		System.out.println("Number of data bits stored in each cache entry:    " + (entryDataBytes * 8));
		System.out.println("Number of LRU bits needed in each cache entry:     " + LRUBitSize);
		System.out.println("Total number of storage bits needed in each entry: " + (1 + (32 - log2(entryDataBytes) - log2(directRows)) + (entryDataBytes * 8) + LRUBitSize));
		System.out.println();

		System.out.println("Total number of entries in the cache:              " + entriesPerCache);
		System.out.println("Total number of storage bits needed for the cache: " + totalCacheStorageBits);
		System.out.println();

		// Simulate the cache. This step is entirely up to students. Remember:
		// Simulate memory requests using the addresses in the given order. Do not sort
		// or otherwise alter the order or number of the addresses.

		/*
		 * TODO - Determine the number of hits and misses on the SECOND pass through the
		 * addresses.
		 */

		/* Your work here. */

		// H: calculate tag, row, and offset number. Then determine if it is hit or miss
		// (study on this).

		// For direct map / set associative
		// To calculate tag: address / (block size * row count)
		// block size (bytes per row) is 2 to the power of the offset.
		// row count is the number of rows

		// To calculate row: address / block size % row count

		// To calculate offset: address % block size

		// To calculate number of sets in cache: total storage bits for cache / block
		// size;

		// NEED TO KEEP TRACK OF LRU AND BOOT CACHE ENTRIES.

		int tag;
		int row;
		int offset;
		int numHits = 0;
		int numMisses = 0;
		int LRUCounter = 0;

		if (simulateDirectMapped) {
			int[] validArray = new int[directRows]; // 0 invalid 1 valid
			int[] rowArray = new int[directRows];

			// Initializing arrays.
			for (int i = 0; i < directRows; i++) {
				validArray[i] = 0;
			}

			//System.out.println("Pass #1:");
			for (int address : addressList) {
				tag = (int) (address / (Math.pow(2, log2(entryDataBytes)) * directRows));
				row = (int) (address / Math.pow(2, log2(entryDataBytes)) % directRows);
				offset = (int) (address % Math.pow(2, log2(entryDataBytes)));

				//System.out.print("Accessing " + address + " (tag " + tag + " row " + row + " offset " + offset + ") : ");

				// hit or miss
				if (rowArray[row] == tag && validArray[row] == 1) {
					// print hit
					//System.out.println("hit from row " + row);

					// already valid. No need to change.
				}

				else {
					// Print miss
					//System.out.println("miss - cached to row " + row);

					// Update rowArray
					rowArray[row] = tag;

					// Update validArray
					validArray[row] = 1;
				}
			}

			//System.out.println("Pass #2:");
			for (int address : addressList) {
				tag = (int) (address / (Math.pow(2, log2(entryDataBytes)) * directRows));
				row = (int) (address / Math.pow(2, log2(entryDataBytes)) % directRows);
				offset = (int) (address % Math.pow(2, log2(entryDataBytes)));

				//System.out.print("Accessing " + address + " (tag " + tag + " row " + row + " offset " + offset + ") : ");

				// hit or miss
				if (rowArray[row] == tag && validArray[row] == 1) {
					// print hit
					//System.out.println("hit from row " + row + " way ");

					// already valid. No need to change.

					numHits++;
				}

				else {
					// Print miss
					//System.out.println("miss - cached to row " + row);

					// Update rowArray
					rowArray[row] = tag;

					// Update validArray
					validArray[row] = 1;

					numMisses++;
				}
			}

		}

		else if (simulateSetAssociative) {
			
			// 2 arrays. Or 2d array. One for rows and one for ways.
			// all 2d arrays. All have access to same spots. LRU array, valid array, tag
			// array
			
			int[][] LRUArray = new int[directRows][setWays];
			int[][] validArray = new int[directRows][setWays]; // 0 invalid 1 valid
			ArrayList<Integer>[][] tagArray = new ArrayList[directRows][setWays];

			// Initializing arrays.
			for (int i = 0; i < directRows; i++) {
				for (int j = 0; j < setWays; j++) {
					LRUArray[i][j] = -1;
					validArray[i][j] = 0;
					tagArray[i][j] = new ArrayList<Integer>();
				}
			}

			//System.out.println("Pass #1:");
			for (int address : addressList) {
				tag = (int) (address / (Math.pow(2, log2(entryDataBytes)) * directRows));
				row = (int) (address / Math.pow(2, log2(entryDataBytes)) % directRows);
				offset = (int) (address % Math.pow(2, log2(entryDataBytes)));

				//System.out.print("Accessing " + address + " (tag " + tag + " row " + row + " offset " + offset + ") : ");

				boolean itsAMiss = true;

				for (int way = 0; way < setWays; way++) {
					// hit or miss
					if (tagArray[row][way].contains(tag) && validArray[row][way] == 1) {
						// print hit
						// System.out.println("hit from row " + row + " way " + way);

						// update tagArray (bring to front)
						tagArray[row][way].remove(tagArray[row][way].indexOf(tag));
						tagArray[row][way].add(tag);

						// already valid. No need to change.

						// update LRUArray and increment LRUCounter
						LRUArray[row][way] = LRUCounter;
						LRUCounter++;
						itsAMiss = false;
						break;
					}
				}

				if (itsAMiss) {
					// Getting way.
					int way = 0;
					int sizeOfRowWays = 0;
					for (int i = 0; i < LRUArray[row].length; i++) {
						if (LRUArray[row][i] >= 0) {
							sizeOfRowWays++;
						}
					}
					if (sizeOfRowWays == setWays) {
						way = indexOfSmallest(LRUArray[row]);

						// get rid of smallest tag.
						tagArray[row][way].remove(0);

					} else {
						way = sizeOfRowWays;
					}

					// Print miss
					//System.out.println("miss - cached to row " + row + " way " + way);

					// Update tagArray
					tagArray[row][way].add(tag);

					// Update validArray
					validArray[row][way] = 1;

					// update LRUArray and increment LRUCounter
					LRUArray[row][way] = LRUCounter;
					LRUCounter++;
				}
			}

			//System.out.println("Pass #2:");
			for (int address : addressList) {
				tag = (int) (address / (Math.pow(2, log2(entryDataBytes)) * directRows));
				row = (int) (address / Math.pow(2, log2(entryDataBytes)) % directRows);
				offset = (int) (address % Math.pow(2, log2(entryDataBytes)));

				//System.out.print("Accessing " + address + " (tag " + tag + " row " + row + " offset " + offset + ") : ");

				boolean itsAMiss = true;

				for (int way = 0; way < setWays; way++) {
					// hit or miss
					if (tagArray[row][way].contains(tag) && validArray[row][way] == 1) {
						// print hit
						//System.out.println("hit from row " + row + " way " + way);

						// update tagArray (bring to front)
						tagArray[row][way].remove(tagArray[row][way].indexOf(tag));
						tagArray[row][way].add(tag);

						// already valid. No need to change.

						// update LRUArray and increment LRUCounter
						LRUArray[row][way] = LRUCounter;
						LRUCounter++;
						itsAMiss = false;

						numHits++;

						break;
					}
				}

				if (itsAMiss) {
					// Getting way.
					int way = 0;
					int sizeOfRowWays = 0;
					for (int i = 0; i < LRUArray[row].length; i++) {
						if (LRUArray[row][i] >= 0) {
							sizeOfRowWays++;
						}
					}
					if (sizeOfRowWays == setWays) {
						way = indexOfSmallest(LRUArray[row]);

						// get rid of smallest tag.
						tagArray[row][way].remove(0);

					} else {
						way = sizeOfRowWays;
					}

					// Print miss
					//System.out.println("miss - cached to row " + row + " way " + way);

					// Update tagArray
					tagArray[row][way].add(tag);

					// Update validArray
					validArray[row][way] = 1;

					// update LRUArray and increment LRUCounter
					LRUArray[row][way] = LRUCounter;
					LRUCounter++;

					numMisses++;
				}
			}
		}
		
		else if (simulateFullyAssociative) {
			ArrayList<Integer> LRUArray = new ArrayList<Integer>(fullEntries);
			ArrayList<Integer> validArray = new ArrayList<Integer>(fullEntries); // 0 invalid 1 valid
			ArrayList<Integer> tagArray = new ArrayList<Integer>(fullEntries);

			// Initializing arrays.
			for (int i = 0; i < fullEntries; i++) {
				validArray.add(0);
			}
			
			for (int address : addressList) {
				tag = (int) (address / Math.pow(2, log2(entryDataBytes)));
				offset = (int) (address % Math.pow(2, log2(entryDataBytes)));

				if (tagArray.contains(tag)) {
					// print hit
					
					tagArray.remove(tagArray.indexOf(tag));
					tagArray.add(tag);
					
					LRUArray.remove(tagArray.indexOf(tag));
					LRUArray.add(LRUCounter);

					// update LRUArray and increment LRUCounter
					LRUCounter++;
				}
				
				else {					
					if(tagArray.size() < fullEntries) {
						tagArray.add(tag);
						LRUArray.add(LRUCounter);	
					}
					else {
						tagArray.remove(0);
						LRUArray.remove(0);
						
						tagArray.add(tag);
						LRUArray.add(LRUCounter);
					}
					
					LRUCounter++;
				}
			}
			
			for (int address : addressList) {
				tag = (int) (address / Math.pow(2, log2(entryDataBytes)));
				offset = (int) (address % Math.pow(2, log2(entryDataBytes)));

				if (tagArray.contains(tag)) {
					// print hit
					
					tagArray.remove(tagArray.indexOf(tag));
					tagArray.add(tag);
					
					LRUArray.remove(tagArray.indexOf(tag));
					LRUArray.add(LRUCounter);

					// update LRUArray and increment LRUCounter
					LRUCounter++;
					
					numHits++;
				}
				
				else {
					
					// Print miss
					
					if(tagArray.size() < fullEntries) {
						tagArray.add(tag);
						LRUArray.add(LRUCounter);	
					}
					else {
						tagArray.remove(0);
						LRUArray.remove(0);
						
						tagArray.add(tag);
						LRUArray.add(LRUCounter);
					}
					
					LRUCounter++;
					
					numMisses++;
				}
			}
		}

		// Report the results. Again, Do not change the messages or printing order.
		// Replace the "fix_me" with a calculation or variable in each case.

		System.out.println("Repeatedly accessing the addresses gives the following results (for the common case):");
		System.out.println("Total number of hits:   " + numHits);
		System.out.println("Total number of misses: " + numMisses);

		// Done -- end of run method.
	}

	// This is a great place for additional helper methods. Add any you like.

	/**
	 * Returns log base 2 of a given number.
	 * 
	 * @param input - The given number.
	 * @return the result.
	 */
	public int log2(int input) {
		int result = -1;

		for (int i = input; i > 0; i = i / 2) {
			result++;
		}

		return result;
	}

	public int findMinArray(int[] arr) {
		int min = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < 0 && arr[i] < min) {
				min = arr[i];
			}
		}
		return min;
	}

	public int indexOfSmallest(int[] array) {
		if (array.length == 0)
			return -1;

		int index = 0;
		int min = array[index];

		for (int i = 1; i < array.length; i++) {
			if (array[i] <= min) {
				min = array[i];
				index = i;
			}
		}
		return index;
	}
}

// If you want any additional classes, put them here.  Here is an example:
// Make sure they are non-public.

class Foo {

}