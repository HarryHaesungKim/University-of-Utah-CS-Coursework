package assign06;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
/**
 * Class used to test LinkedListStack
 * @author Harry Kim && Braden Morfin
 * @version 10/15/2020
 *
 */
class LinkedListStackTest {

	@Test
	void testPush() {
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		
		assertEquals(0, stack.size());
		stack.push(3);
		assertEquals(1, stack.size());
	}
	
	@Test
	void testPushMultiple() {
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		
		assertEquals(0, stack.size());
		stack.push(3);
		assertEquals(1, stack.size());
		stack.push(2);
		assertEquals(2, stack.peek());
	} 
	
	@Test
	void testPeek() {
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		
		assertEquals(0, stack.size());
		stack.push(3);
		assertEquals(3, stack.peek());
	}
	
	@Test
	void testPeekNothing() {
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		
		assertThrows(NoSuchElementException.class, () -> {
			stack.peek();
		});
		
	}
	
	@Test
	void testPop() {
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		
		assertEquals(0, stack.size());
		stack.push(3);
		assertEquals(3, stack.pop());
		assertEquals(0, stack.size());
	}
	
	@Test
	void testPopNothing() {
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		
		assertThrows(NoSuchElementException.class, () -> {
			stack.pop();
		});
		
	}
	
	@Test
	void testClear() {
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		stack.push(3);
		stack.push(1);
		stack.push(2);
		assertEquals(3, stack.size());
		stack.clear();
		assertEquals(0, stack.size());
	}
	
	@Test
	void testIsEmptyTrue() {
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		assertTrue(stack.isEmpty());
	}
	
	@Test
	void testIsEmptyFalse() {
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		stack.push(1);
		assertFalse(stack.isEmpty());
	}
	
	@Test
	void testtSize() {
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		assertEquals(0, stack.size());
		stack.push(3);
		stack.push(1);
		stack.push(2);
		assertEquals(3, stack.size());
		stack.clear();
		assertEquals(0, stack.size());
	}
}
