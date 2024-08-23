package assign06;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements List<E> {

	private class Node {
		public E data;
		public Node next;

		public Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

	private Node head;
	private int elementCount;

	public SinglyLinkedList() {
		head = null;
		elementCount = 0;
	}

	@Override
	public void addFirst(E element) {
		head = new Node(element, head);
		elementCount++;
	}

	/**
	 * Helper method to retrieve the node occurring in the list before the node at
	 * the given position.
	 * 
	 * NOTE: It is a precondition that pos > 0.
	 * 
	 * @param pos 0-indexed position of the node
	 * @return node at position pos-1
	 */
	private Node getPrevNode(int pos) {
		Node temp = head;
		for (int i = 0; i < pos - 1; i++)
			temp = temp.next;
		return temp;
	}

	/**
	 * Helper method to insert a new node containing newData immediately after
	 * prevNode.
	 * 
	 * @param newData
	 * @param prevNode
	 */
	private void insert(E newData, Node prevNode) {
		prevNode.next = new Node(newData, prevNode.next);
		elementCount++;
	}

	@Override
	public void add(int index, E element) throws IndexOutOfBoundsException {
		if (index == 0)
		{
			addFirst(element);
			return;
		}
		if (index < 0 || index > elementCount)
			throw new IndexOutOfBoundsException();
		else
			insert(element, getPrevNode(index));
	}

	@Override
	public E getFirst() throws NoSuchElementException {
		if (elementCount == 0) {
			throw new NoSuchElementException();
		}
		return head.data;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= elementCount) {
			throw new IndexOutOfBoundsException();
		}
		Node temp;
		temp = head;
		int i = 0;
		while (temp != null && i < index) {
			temp = temp.next;
			i++;
		}
		return temp.data;
	}

	@Override
	public E removeFirst() throws NoSuchElementException {
		if (elementCount == 0) {
			throw new NoSuchElementException();
		}
		E temp = head.data;
		head = head.next;
		elementCount--;
		return temp;
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= elementCount)
			throw new IndexOutOfBoundsException();
		if (index == 0) {
			return removeFirst();
		} else {
			E tempReturn = get(index);
			Node tempPrev = getPrevNode(index);
			tempPrev.next = tempPrev.next.next;
			elementCount--;
			return tempReturn;
		}
	}

	@Override
	public int indexOf(E element) {
		Node temp = head;
		int i = 0;
		while (temp != null && i < elementCount) {
			if (temp.data.equals(element)) {
				return i;
			}
			temp = temp.next;
			i++;
		}
		return -1;
	}

	@Override
	public int size() {
		return elementCount;
	}

	@Override
	public boolean isEmpty() {
		return elementCount == 0;
	}

	@Override
	public void clear() {
		elementCount = 0;
		head = null;
	}

	@Override
	public E[] toArray() {
		E[] elementArray = (E[]) new Object[elementCount];
		for (int i = 0; i < elementCount; i++) {
			elementArray[i] = get(i);
		}
		return elementArray;
	}

	@Override
	public Iterator<E> iterator() {

		return new SinglyLinkedListIterator();
	}

	private class SinglyLinkedListIterator implements Iterator<E> {

		private int pos;
		private boolean nextWasCalled;

		
		public SinglyLinkedListIterator(){
			pos = 0;
			nextWasCalled = false;
		}
		@Override
		public boolean hasNext() {
			return pos < elementCount;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			nextWasCalled = true;
			return get(pos++);
		}
		
		public void remove() {
			if (!nextWasCalled) {
				throw new IllegalStateException();
			}
			
		    SinglyLinkedList.this.remove(pos - 1);
			pos--;
		}
	}
}