package assign06;

import java.util.NoSuchElementException;
/**
 * This class implements a LIFO (last in first out) stack using 
 * a linked list as the backing data structure.
 * 
 * @author Harry Kim && Braden Morfin
 * @version 10/15/2020
 *
 * @param <E> - the type of elements contained in the stack
 */
public class LinkedListStack<E> implements Stack<E>
{
	// singly linked list used to back the stack
	private SinglyLinkedList<E> stack;
	
	
	/**
	 * Constructor used to creak a LinkedListStack object
	 */
	public LinkedListStack(){
		stack = new SinglyLinkedList<E>();
	}

	@Override
	/**
	 * Removes all of the elements from the stack.
	 */
	public void clear() {
		stack.clear();
	}

	@Override
	/**
	 * @return true if the stack contains no elements; false, otherwise.
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	/**
	 * Returns, but does not remove, the element at the top of the stack.
	 * 
	 * @return the element at the top of the stack
	 * @throws NoSuchElementException if the stack is empty
	 */
	public E peek() throws NoSuchElementException {
	
		return stack.getFirst();
	}

	@Override
	/**
	 * Returns and removes the item at the top of the stack.
	 * 
	 * @return the element at the top of the stack
	 * @throws NoSuchElementException if the stack is empty
	 */
	public E pop() throws NoSuchElementException {
		
		return stack.removeFirst();
	}

	@Override
	/**
	 * Adds a given element to the stack, putting it at the top of the stack.
	 * 
	 * @param element - the element to be added
	 */
	public void push(E element) {
		stack.addFirst(element);
	}

	@Override
	/**
	 * @return the number of elements in the stack
	 */
	public int size() {
		return stack.size();
	}
	
	

}