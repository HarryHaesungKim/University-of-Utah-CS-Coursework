package assign03;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * This class represents a simple priority queue that remains sorted from the
 * biggest element to the smallest.
 * 
 * @author Harry Kim & Braden Morfin
 * @version September 16, 2020
 *
 * @param <E>
 */
public class SimplePriorityQueue<E> implements PriorityQueue<E> {

	private E[] queue;

	private Comparator<? super E> ordering;

	private int size;

	@SuppressWarnings("unchecked")
	/**
	 * Creates a new SimplePriorityQueue() object that assumes natural ordering.
	 */
	public SimplePriorityQueue() {
		size = 0;
		queue = (E[]) new Object[16];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Creates a new SimplePriorityQueue() object that uses a comparator to order
	 * instead of natural ordering.
	 */
	public SimplePriorityQueue(Comparator<? super E> C) {
		size = 0;
		queue = (E[]) new Object[16];
		ordering = C;
	}

	@Override
	/**
	 * Retrieves, but does not remove, the minimum element in this priority queue.
	 * 
	 * @return the minimum element
	 * @throws NoSuchElementException if the priority queue is empty
	 */
	public E findMin() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("The priority queue is empty.");
		}
		return queue[size - 1];
	}

	@Override
	/**
	 * Retrieves and removes the minimum element in this priority queue.
	 * 
	 * @return the minimum element
	 * @throws NoSuchElementException if the priority queue is empty
	 */
	public E deleteMin() throws NoSuchElementException {
		E min = this.findMin();
		size--;
		return min;
	}

	@Override
	/**
	 * Inserts the specified element into this priority queue.
	 * 
	 * @param item - the element to insert
	 */
	public void insert(E item) {

		// If the queue is empty, place item at first address because there is nothing
		// there.
		if (this.isEmpty()) {
			queue[0] = item;
			size++;
			return;
		}

		// Creates larger backing array when the limit of elements within the queue is
		// reached.
		else if (size == queue.length) {
			@SuppressWarnings("unchecked")
			E[] newQueue = (E[]) new Object[size * 2];
			for (int i = 0; i < size; i++) {
				newQueue[i] = queue[i];
			}
			queue = newQueue;
		}

		// Finds index via binary search.
		int index = this.binarySearch(item);

		// Shifts elements smaller than item over by one.
		for (int i = index; i < size; i++) {
			E temp = queue[i];
			queue[i] = item;
			item = temp;
		}
		queue[size] = item;
		size++;
		return;

	}

	@Override
	/**
	 * Inserts the specified elements into this priority queue.
	 * 
	 * @param coll - the collection of elements to insert
	 */
	public void insertAll(Collection<? extends E> coll) {

		// Makes use of this.insert to insert a collection of elements into a queue.
		for (E element : coll) {
			this.insert(element);
		}
	}

	@Override
	/**
	 * @return the number of elements in this priority queue
	 */
	public int size() {
		return size;
	}

	@Override
	/**
	 * @return true if this priority queue contains no elements, false otherwise
	 */
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	/**
	 * Removes all of the elements from this priority queue. The queue will be empty
	 * when this call returns.
	 */
	public void clear() {
		size = 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @param elt1
	 * @param elt2
	 * @return difference of elt1 and elt2
	 */
	private int compare(E elt1, E elt2) {
		if (ordering == null) {
			return ((Comparable<? super E>) elt1).compareTo(elt2);
		}
		return ordering.compare(elt1, elt2);
	}

	/**
	 * Returns the index of which item should go within the queue.
	 * 
	 * @param item
	 * @return index in which to insert item
	 */
	private int binarySearch(E item) {
		int low = this.size - 1, high = 0, mid = 0;

		// Binary search
		while (low >= high) {
			mid = (low + high + 1) / 2;
			int a = this.compare(item, queue[mid]);
			if (item.equals(queue[mid]))
				return mid;
			else if (a < 0) {
				high = mid + 1;
			} else {
				low = mid - 1;
			}
		}
		return high;
	}

	// Strictly used for testing.
	private void add(E item) {
		queue[size] = item;
		size++;
	}

}