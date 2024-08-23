package assign10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This class contains generic static methods for finding the k largest items in
 * a list.
 * 
 * @author Erin Parker & Harry Kim & Braden Morfin
 * @version 11/17/20
 */
public class FindKLargest {

	/**
	 * Determines the k largest items in the given list, using a binary max heap and
	 * the natural ordering of the items.
	 * 
	 * @param items - the given list
	 * @param k     - the number of largest items
	 * @return a list of the k largest items, in descending order
	 * @throws IllegalArgumentException if k is negative or larger than the size of
	 *                                  the given list
	 */
	public static <E extends Comparable<? super E>> List<E> findKLargestHeap(List<E> items, int k)
			throws IllegalArgumentException {
		// Checks that k is not negative or larger than the length of the given list
		if (k < 0 || k > items.size())
			throw new IllegalArgumentException();

		// creates the list to store the k largest items and the binary max heap to
		// "sort" the given list.
		ArrayList<E> kLargestItems = new ArrayList<E>();
		BinaryMaxHeap<E> heap = new BinaryMaxHeap<E>(items);

		// adds the k largest items to the return list
		for (int i = 0; i < k; i++) {
			kLargestItems.add(heap.extractMax());
		}
		return kLargestItems;
	}

	/**
	 * Determines the k largest items in the given list, using a binary max heap.
	 * 
	 * @param items - the given list
	 * @param k     - the number of largest items
	 * @param cmp   - the comparator defining how to compare items
	 * @return a list of the k largest items, in descending order
	 * @throws IllegalArgumentException if k is negative or larger than the size of
	 *                                  the given list
	 */
	public static <E> List<E> findKLargestHeap(List<E> items, int k, Comparator<? super E> cmp)
			throws IllegalArgumentException {
		// Checks that k is not negative or larger than the length of the given list
		if (k < 0 || k > items.size())
			throw new IllegalArgumentException();

		// creates the list to store the k largest items and the binary max heap to
		// "sort" the given list.
		ArrayList<E> kLargestItems = new ArrayList<E>();
		BinaryMaxHeap<E> heap = new BinaryMaxHeap<E>(items, cmp);

		// adds the k largest items to the return list
		for (int i = 0; i < k; i++) {
			kLargestItems.add(heap.extractMax());
		}
		return kLargestItems;
	}

	/**
	 * Determines the k largest items in the given list, using Java's sort routine
	 * and the natural ordering of the items.
	 * 
	 * @param items - the given list
	 * @param k     - the number of largest items
	 * @return a list of the k largest items, in descending order
	 * @throws IllegalArgumentException if k is negative or larger than the size of
	 *                                  the given list
	 */
	public static <E extends Comparable<? super E>> List<E> findKLargestSort(List<E> items, int k)
			throws IllegalArgumentException {

		// Checks that k is not negative or larger than the length of the given list
		if (k < 0 || k > items.size())
			throw new IllegalArgumentException();

		// creates an array from the given list items and sorts the new array
//		@SuppressWarnings("unchecked")
//		E[] arrayOfItems = (E[]) new Object[items.size()];
//		int g = 0;
//		for(E item : items) {
//			arrayOfItems[g] = item;
//			g++;
//		}
//		
//		Arrays.sort(arrayOfItems);

		// creates the list of k largest items
		ArrayList<E> kLargestItems = new ArrayList<E>();
		
		items.sort(null);
		
		// adds the k largest items to the return list
		for (int i = 0; i < k; i++)
			kLargestItems.add(items.get(items.size() - 1 - i));

		return kLargestItems;
	}

	/**
	 * Determines the k largest items in the given list, using Java's sort routine.
	 * 
	 * @param items - the given list
	 * @param k     - the number of largest items
	 * @param cmp   - the comparator defining how to compare items
	 * @return a list of the k largest items, in descending order
	 * @throws IllegalArgumentException if k is negative or larger than the size of
	 *                                  the given list
	 */
	public static <E> List<E> findKLargestSort(List<E> items, int k, Comparator<? super E> cmp)
			throws IllegalArgumentException {

		// Checks that k is not negative or larger than the length of the given list
		if (k < 0 || k > items.size())
			throw new IllegalArgumentException();

		// creates an array from the given list items and sorts the new array
//		@SuppressWarnings("unchecked")
//		E[] arrayOfItems = (E[]) new Object[items.size()];
//		int g = 0;
//		for(E item : items) {
//			arrayOfItems[g] = item;
//			g++;
//		}
//		
//		Arrays.sort(arrayOfItems);

		// creates the list of k largest items
		ArrayList<E> kLargestItems = new ArrayList<E>();
		
		items.sort(cmp);
		
		// adds the k largest items to the return list
		for (int i = 0; i < k; i++)
			kLargestItems.add(items.get(items.size() - 1 - i));

		return kLargestItems;
	}
}