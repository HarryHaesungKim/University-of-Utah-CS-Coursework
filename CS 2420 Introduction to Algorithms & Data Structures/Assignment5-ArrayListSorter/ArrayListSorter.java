package assign05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * A class that uses merge sort and quick sort to sort ArrayLists.
 * 
 * @author Harry Kim & Braden Morfin
 *
 * @param <T>
 */
public class ArrayListSorter<T> {

	// The size threshold that determines whether or not to use insertion sort.
	private static int threshold;

	// The index of the pivot point to be used in quick sort.
	private static int pivot;
	
	// Used only for timing 
	private static int pivstrat;

	/**
	 * The driver method that evokes merge sort on the given generic ArrayList.
	 * Evokes insertion sort when the threshold surpasses the size.
	 * 
	 * @param <T>  The type of the ArrayList.
	 * @param list to be sorted
	 */
	public static <T extends Comparable<? super T>> void mergesort(ArrayList<T> list) {

		// Determine the threshold.
		threshold = list.size() / 16;

		// Populates the tempList with spaces.
		ArrayList<T> tempList = new ArrayList<T>();
		for (int i = 0; i < list.size(); i++) {
			tempList.add(null);
		}
		mergesortDivide(list, tempList, 0, list.size() - 1);
	}
	
	/**
	 *  Private helper method used only for testing. Allows the change of the threshold that determines 
	 *  whether or not to use insertion sort.
	 * @param size - size threshold
	 */
	private static void threshold(int size){
		threshold = size;
	}
	
	/**
	 *  Private helper method used inly for testing. Allows the change of how quick sort chooses
	 *  a pivot.
	 * @param piv - number that determines what pivot strategy to use.
	 */
	private static void piv(int piv){
		pivstrat = piv;
	}

	/**
	 * The private helper method that performs the recursion of merge sort.
	 * 
	 * @param <T>   The type of the ArrayList.
	 * @param list  The list to be sorted.
	 * @param temp  The temporary ArrayList required for merge sort.
	 * @param left  The left index of the subarray to be sorted.
	 * @param right The right index of the subarray to be sorted.
	 */
	private static <T extends Comparable<? super T>> void mergesortDivide(ArrayList<T> list, ArrayList<T> temp,
			int left, int right) {

		// Checks that there's still subarrays to be sorted and if the size of the
		// threshold surpasses the size of the subarray.
		if (left < right && right - left + 1 >= threshold) {
			int mid = (left + right) / 2;
			mergesortDivide(list, temp, left, mid);
			mergesortDivide(list, temp, mid + 1, right);
			mergesortConquer(list, temp, left, mid, right);
		}

		// Performs insertion sort on subarray if threshold is met.
		else {
			insertionSort(list, left, right);
		}
	}

	/**
	 * The private helper method that merges the subarrays.
	 * 
	 * @param <T>   The type of the ArrayList.
	 * @param list  The list to be sorted.
	 * @param temp  The temporary ArrayList required for merge sort.
	 * @param left  The starting index of the left subarray.
	 * @param mid   The ending index of the left subarray.
	 * @param right The ending index of the right subarray.
	 */
	private static <T extends Comparable<? super T>> void mergesortConquer(ArrayList<T> list, ArrayList<T> temp,
			int left, int mid, int right) {
		int cursorL = left; // The starting position in left subarray.
		int endL = mid; // The index of the last item in the left subarray.
		int cursorR = mid + 1; // The starting position of the right subarray.
		int endR = right; // The index of the last item in the right subarray.
		int index = left; // The starting position in temp array.

		// Primary interleaving loop which increments cursorL and cursorR until endL or
		// endR is reached.
		while (cursorL <= endL && cursorR <= endR) {
			if (list.get(cursorL).compareTo(list.get(cursorR)) < 0) {
				temp.set(index, list.get(cursorL));
				cursorL++;
				index++;
			} else {
				temp.set(index, list.get(cursorR));
				cursorR++;
				index++;
			}
		}

		// If the loop is terminated because end of the left subarray was reached, add
		// the remaining items in the right subarray to the temp array.
		if (cursorL > endL) {
			while (cursorR <= endR) {
				temp.set(index, list.get(cursorR));
				index++;
				cursorR++;
			}
		}
		// Otherwise, add the remaining items in the left subarray to the temp array.
		if (cursorR > endR) {
			while (cursorL <= endL) {
				temp.set(index, list.get(cursorL));
				index++;
				cursorL++;
			}
		}

		// Copy the items into temp array from left to right back into the original
		// array.
		for (int i = left; i <= right; i++) {
			list.set(i, temp.get(i));
		}
	}

	/**
	 * The driver method that evokes quick sort on the given generic ArrayList.
	 * 
	 * @param <T>  The type of the ArrayList.
	 * @param list The list to be sorted.
	 */
	public static <T extends Comparable<? super T>> void quicksort(ArrayList<T> list) {
		quicksorter(list, 0, list.size() - 1);
	}

	/**
	 * The private helper method that performs the recursion of quick sort.
	 * 
	 * @param <T>  The type of the ArrayList.
	 * @param list The list to be sorted.
	 * @param low  The starting index of the subarray to be sorted.
	 * @param high The ending index of the subarray to be sorted.
	 */
	private static <T extends Comparable<? super T>> void quicksorter(ArrayList<T> list, int low, int high) {
		// Checks that there's still subarrays to be sorted.
		if (low < high) {
			int partition = quicksortPartition(list, low, high);
			quicksorter(list, low, partition - 1);
			quicksorter(list, partition + 1, high);
		}

	}

	/**
	 * The private helper method that partitions the subarray into two.
	 * 
	 * @param <T>   The type of the ArrayList.
	 * @param list  The list to be sorted.
	 * @param start The start index of the subarray to be partitioned.
	 * @param end   The end index of the subarray to be partitioned.
	 * @return Returns the index of the pivot.
	 */
	private static <T extends Comparable<? super T>> int quicksortPartition(ArrayList<T> list, int start, int end) {

		// Chooses the median of 3 points in the subarray to be the pivot.
		//pivot = pivotMedian(list, start, end);

		if(pivstrat == 1)
		{
			pivot = pivotMedian(list, start, end);	
		}
		else if (pivstrat == 2)
		{
			pivot = pivotRandom(list, start, end);
		}
		else
		{
			pivot = pivotMiddle(list, start, end);
		}
		// used for testing different pivot methods
//		pivot = pivotRandom(list, start, end);

		// used for testing different pivot methods
//		pivot = pivotMiddle(list, start, end);
		

		T temp = list.get(end);
		list.set(end, list.get(pivot));
		list.set(pivot, temp);

		int rightCursor = end - 1; // The end idex of the subarray.
		int leftCursor = start; // The end idex of the subarray.

		while (leftCursor < rightCursor) {

			// If the item at the left cursor is smaller than the pivot, increment the left
			// cursor.
			while (list.get(leftCursor).compareTo(list.get(end)) <= 0 && leftCursor < rightCursor) {
				leftCursor++;
			}

			// If the item at the right cursor is bigger than the pivot, decrement the right
			// cursor.
			while (list.get(rightCursor).compareTo(list.get(end)) > 0 && leftCursor < rightCursor) {
				rightCursor--;
			}

			// Swaps the items smaller than the pivot to the left of the subarray and visa
			// versa.
			temp = list.get(leftCursor);
			list.set(leftCursor, list.get(rightCursor));
			list.set(rightCursor, temp);

		}

		// Swaps the pivot with the leftmost element that is bigger than it and returns
		// that index.
		for (int i = start; i < end; i++) {
			if (list.get(i).compareTo(list.get(end)) > 0) {
				temp = list.get(i);
				list.set(i, list.get(end));
				list.set(end, temp);
				return i;
			}
		}
		return end;
	}

	/**
	 * The private method that returns the index of the median of 3 elements
	 * (starting, middle, and end elements).
	 * 
	 * @param <T>   The type of the ArrayList.
	 * @param list  The list of the elements.
	 * @param start The index of first element.
	 * @param end   The index of the last element.
	 * @return Index of the median value.
	 */
	private static <T extends Comparable<? super T>> int pivotMedian(ArrayList<T> list, int start, int end) {
		T value1 = list.get(start);
		T value2 = list.get((start + end) / 2);
		T value3 = list.get(end);
		if (value1.compareTo(value3) > 0) {
			if (value1.compareTo(value2) < 0) {
				return start;
			} else if (value2.compareTo(value3) > 0) {
				return (start + end) / 2;
			} else {
				return end;
			}
		} else if (value1.compareTo(value2) > 0) {
			if (value1.compareTo(value3) < 0) {
				return end;
			} else if (value2.compareTo(value3) > 0) {
				return (start + end) / 2;
			} else {
				return start;
			}
		} else {
			if (value2.compareTo(value3) < 0) {
				return (start + end) / 2;
			} else {
				return end;
			}
		}
	}

	/**
	 * The private helper method that returns the index of a random pivot.
	 * 
	 * @param <T>   The type of the ArrayList.
	 * @param list  The list of the elements.
	 * @param start The index of first element.
	 * @param end   The index of the last element.
	 * @return Index of the random value.
	 */
	private static <T extends Comparable<? super T>> int pivotRandom(ArrayList<T> list, int start, int end) {
		Random rng = new Random();
		int dif = end - start;
		return rng.nextInt(dif) + start;

	}

	/**
	 * The private helper method that returns the index of the middle element to be
	 * used as the pivot.
	 * 
	 * @param <T>   The type of the ArrayList.
	 * @param list  The list of the elements.
	 * @param start The index of first element.
	 * @param end   The index of the last element.
	 * @return Index of the middle value.
	 */
	private static <T extends Comparable<? super T>> int pivotMiddle(ArrayList<T> list, int start, int end) {
		return (end + start) / 2;

	}

	/**
	 * The private helper method that performs insertion sort on the given subarray.
	 * 
	 * @param <T>   The type of the ArrayList.
	 * @param array The array to be sorted.
	 * @param left  The starting index of the subarray.
	 * @param right The ending index of the subarray.
	 */
	private static <T extends Comparable<? super T>> void insertionSort(ArrayList<T> array, int left, int right) {
		for (int i = left + 1; i < right + 1; i++) {
			T value = array.get(i);
			int j;
			for (j = i - 1; j >= left && array.get(j).compareTo(value) > 0; j--) {
				array.set(j + 1, array.get(j));
			}
			array.set(j + 1, value);
		}
	}

	/**
	 * This method generates and returns an ArrayList of integers 1 to size in
	 * ascending order.
	 * 
	 * @param size The size of the generated ArrayList.
	 * @return The Arraylist of integers.
	 */
	public static ArrayList<Integer> generateAscending(int size) {
		ArrayList<Integer> ascendingList = new ArrayList<Integer>();
		for (int i = 1; i <= size; i++) {
			ascendingList.add(i);
		}
		return ascendingList;
	}

	/**
	 * This method generates and returns an ArrayList of integers 1 to size in
	 * permuted order (i,e., randomly ordered).
	 * 
	 * @param size The size of the generated ArrayList.
	 * @return The Arraylist of integers.
	 */
	public static ArrayList<Integer> generatePermuted(int size) {
		ArrayList<Integer> permutedList = generateAscending(size);
		Collections.shuffle(permutedList);
		return permutedList;
	}
	
	/**
	 * This method generates and returns an ArrayList of integers 1 to size in descending order.
	 * 
	 * @param size The size of the generated ArrayList.
	 * @return The Arraylist of integers.
	 */
	public static ArrayList<Integer> generateDescending(int size) {
		ArrayList<Integer> ascendingList = new ArrayList<Integer>();
		for (int i = size; i >= 1; i--) {
			ascendingList.add(i);
		}
		return ascendingList;
	}

}