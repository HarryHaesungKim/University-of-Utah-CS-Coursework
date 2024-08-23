package assign10;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents a binary max heap, implicitly, using an array.
 * 
 * Assumes the root of the tree is at index 1.
 * 
 * @author Harry Kim & Braden Morfin
 *
 * @param <E> - type of binary max heap
 */
public class BinaryMaxHeap<E> implements PriorityQueue<E> {

	private E[] queue;

	private Comparator<? super E> ordering;

	private int size;

	/**
	 * Creates a new, empty binary max heap while assuming natural ordering.
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap() {
		size = 0;
		queue = (E[]) new Object[16];
		ordering = null;
	}

	/**
	 * Creates a new, empty binary max heap while taking a comparator.
	 * 
	 * @param C - the comparator
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap(Comparator<? super E> C) {
		size = 0;
		queue = (E[]) new Object[16];
		ordering = C;
	}

	/**
	 * Creates a binary max heap, filled with the items in the given list using
	 * build heap operation. Assumes natural ordering.
	 * 
	 * @param items - list of items to be added
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap(List<? extends E> items) {
		size = items.size();
		queue = (E[]) new Object[items.size() * 2];
		ordering = null;
		buildHeap(items);
	}

	/**
	 * Creates a binary max heap, filled with the items in the given list using
	 * build heap operation. Takes a comparator.
	 * 
	 * @param items - list of items to be added
	 * @param C     - the comparator
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap(List<? extends E> items, Comparator<? super E> C) {
		size = items.size();
		queue = (E[]) new Object[items.size() * 2];
		ordering = C;
		buildHeap(items);

	}

	/**
	 * Adds the given item to this priority queue. O(1) in the average case, O(log
	 * N) in the worst case
	 * 
	 * @param item
	 */
	@Override
	public void add(E item) {

		// If the queue is empty, place item at first address because there is nothing
		// there.
		if (this.isEmpty()) {
			queue[1] = item;
			size++;
			return;
		}

		// Creates larger backing array if limit of the array is reached.
		else if (size >= queue.length - 1) {
			@SuppressWarnings("unchecked")
			E[] newQueue = (E[]) new Object[queue.length * 2];
			for (int i = 0; i < size; i++) {
				newQueue[i] = queue[i];
			}
			queue = newQueue;
		}

		// Insert item at the next available spot.
		queue[size + 1] = item;

		int i = size + 1;

		percolateUp(i);

		size++;
	}

	/**
	 * Returns, but does not remove, the maximum item this priority queue. O(1)
	 * 
	 * @return the maximum item
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	@Override
	public E peek() throws NoSuchElementException {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		}
		return queue[1];
	}

	/**
	 * Returns and removes the maximum item this priority queue. O(log N)
	 * 
	 * @return the maximum item
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	@Override
	public E extractMax() throws NoSuchElementException {
		if (this.isEmpty())
			throw new NoSuchElementException();

		// Swapping root item with last item the array
		E temp = queue[1];
		queue[1] = queue[size];
		queue[size] = temp;
		size--;

		// Percolating down.
		percolateDown(1);

		return temp;
	}

	/**
	 * Returns the number of items in this priority queue. O(1)
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns true if this priority queue is empty, false otherwise. O(1)
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Empties this priority queue of items. O(1)
	 */
	@Override
	public void clear() {
		size = 0;
	}

	/**
	 * Creates and returns an array of the items in this priority queue, in the same
	 * order they appear in the backing array. O(N)
	 * 
	 * (NOTE: This method is needed for grading purposes. The root item must be
	 * stored at index 0 in the returned array, regardless of whether it is in
	 * stored there in the backing array.)
	 */
	@Override
	public Object[] toArray() {
		@SuppressWarnings("unchecked")
		E[] array = (E[]) new Object[size];
		for (int i = 0; i < size; i++) {
			array[i] = queue[i + 1];
		}
		return array;
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
	 * Private helper method used to percolate items up the heap at the given index.
	 * 
	 * @param index - the given index to percolate up from
	 */
	private void percolateUp(int index) {
		while (index > 0) {
			if (this.getParent(index) == null) {
				return;
			}
			if (this.compare(queue[index], this.getParent(index)) > 0) {
				E temp = queue[index];
				queue[index] = queue[index / 2];
				queue[index / 2] = temp;
				index = index / 2;
			} else
				return;
		}
	}

	/**
	 * Private helper method used to percolate items down the heap at the given
	 * index.
	 * 
	 * @param index - the given index to percolate down from
	 */
	private void percolateDown(int index) {
		while (index <= size) {

			// Reaches a leaf.
			if (this.getLeftChild(index) == null && this.getRightChild(index) == null) {
				return;
			}

			else if (this.getLeftChild(index) == null) {
				if (this.compare(queue[index], this.getRightChild(index)) < 0) {
					E temp = queue[index];
					queue[index] = queue[2 * index + 1];
					queue[2 * index + 1] = temp;
					index = 2 * index + 1;
				} else {
					return;
				}
			}

			else if (this.getRightChild(index) == null) {
				if (this.compare(queue[index], this.getLeftChild(index)) < 0) {
					E temp = queue[index];
					queue[index] = queue[2 * index];
					queue[2 * index] = temp;
					index = 2 * index;
				} else {
					return;
				}
			}

			// If both children are bigger.
			else if (this.compare(queue[index], this.getLeftChild(index)) < 0
					&& this.compare(queue[index], this.getRightChild(index)) < 0) {

				// Percolates down left child.
				if (this.compare(this.getLeftChild(index), this.getRightChild(index)) > 0) {
					E temp = queue[index];
					queue[index] = queue[2 * index];
					queue[2 * index] = temp;
					index = 2 * index;
				}

				// Percolates down right child.
				else {
					E temp = queue[index];
					queue[index] = queue[2 * index + 1];
					queue[2 * index + 1] = temp;
					index = 2 * index + 1;
				}
			}

			// Percolates down left child.
			else if (this.compare(queue[index], this.getLeftChild(index)) < 0) {
				E temp = queue[index];
				queue[index] = queue[2 * index];
				queue[2 * index] = temp;
				index = 2 * index;
			}

			// Percolates down right child.
			else if (this.compare(queue[index], this.getRightChild(index)) < 0) {
				E temp = queue[index];
				queue[index] = queue[2 * index + 1];
				queue[2 * index + 1] = temp;
				index = 2 * index + 1;
			}

			// In correct position.
			else
				return;
		}
	}

	/**
	 * Builds a binary max heap.
	 * 
	 * @param items items to be added to the heap.
	 * @return the sorted heap.
	 */
	private E[] buildHeap(List<? extends E> items) {
		int i = 1;

		// Structure property satisfied.
		for (E item : items) {
			queue[i] = item;
			i++;
		}

		// Order property satisfied.
		for (int j = size; j > 0; j--) {
			percolateDown(j);
		}

		return queue;
	}

	/**
	 * Retrieves the data from the node that is the parent of the node at the given
	 * index. If the node at the given index has no parent, returns null.
	 * 
	 * @param i
	 */
	private E getParent(int i) {
		if (i == 1)
			return null;
		return queue[i / 2];
	}

	/**
	 * Retrieves the data from the node that is the left child of the node at the
	 * given index. If the node at the given index has no left child, returns null.
	 * 
	 * @param i
	 */
	private E getLeftChild(int i) {
		int index = 2 * i;
		if (index > size)
			return null;
		return queue[index];
	}

	/**
	 * Retrieves the data from the node that is the right child of the node at the
	 * given index. If the node at the given index has no right child, returns null.
	 * 
	 * @param i
	 */
	private E getRightChild(int i) {
		int index = 2 * i + 1;
		if (index > size)
			return null;
		return queue[index];
	}
	
	/**
	 * Used for timing code. Otherwise, private.
	 */
	public void balancer(int i) {
		size = size - i;
	}
}