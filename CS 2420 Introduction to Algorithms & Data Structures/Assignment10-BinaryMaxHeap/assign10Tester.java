package assign10;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class assign10Tester {

	@Test
	public void testAddOneThing()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
		assertEquals(0, heap.size());
		assertThrows(NoSuchElementException.class, () -> {heap.peek();});
		
		heap.add(50);
		assertEquals(1, heap.size());
		assertEquals(50, heap.peek());
	}
	
	@Test
	public void testAddPercolateUp()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
		assertEquals(0, heap.size());
		assertThrows(NoSuchElementException.class, () -> {heap.peek();});
		
		heap.add(50);
		assertEquals(1, heap.size());
		assertEquals(50, heap.peek());
		
		heap.add(60);
		assertEquals(2, heap.size());
		assertEquals(60, heap.peek());
	}
	
	@Test
	public void testAddPercolateUpManyItems()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
		assertEquals(0, heap.size());
		assertThrows(NoSuchElementException.class, () -> {heap.peek();});
		
		heap.add(50);
		assertEquals(1, heap.size());
		assertEquals(50, heap.peek());
		
		heap.add(60);
		assertEquals(2, heap.size());
		assertEquals(60, heap.peek());
		
		heap.add(70);
		
		heap.add(80);
		assertEquals(80, heap.peek());
	}
	
	@Test
	public void testAddManyItems()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
		assertEquals(0, heap.size());
		assertThrows(NoSuchElementException.class, () -> {heap.peek();});
		
		heap.add(50);
		heap.add(60);
		heap.add(5);
		heap.add(35);
		heap.add(3);
		heap.add(45);
		
		int[] expected = new int[6];
		expected[0] = 60;
		expected[1] = 50;
		expected[2] = 45;
		expected[3] = 35;
		expected[4] = 3;
		expected[5] = 5;
		
		assertEquals(expected[0], heap.toArray()[0]);
		assertEquals(expected[1], heap.toArray()[1]);
		assertEquals(expected[2], heap.toArray()[2]);
		assertEquals(expected[3], heap.toArray()[3]);
		assertEquals(expected[4], heap.toArray()[4]);
		assertEquals(expected[5], heap.toArray()[5]);
		assertEquals(expected.length, heap.toArray().length);
		
	}
	
	@Test
	public void testPeekEmptyHeap()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
		assertEquals(0, heap.size());
		assertThrows(NoSuchElementException.class, () -> {heap.peek();});
	}
	
	@Test
	public void testPeekSmallHeap()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
		heap.add(45);
		heap.add(35);
		heap.add(65);
		assertEquals(65, heap.peek());
	}
	
	@Test
	public void testExtractMax()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
		heap.add(50);
		heap.add(60);
		heap.add(5);
		heap.add(35);
		heap.add(3);
		heap.add(45);
		
		assertEquals(60, heap.extractMax());
		assertEquals(5, heap.size());
		
		int[] expected = new int[5];
		expected[0] = 50;
		expected[1] = 35;
		expected[2] = 45;
		expected[3] = 5;
		expected[4] = 3;

		assertEquals(expected[0], heap.toArray()[0]);
		assertEquals(expected[1], heap.toArray()[1]);
		assertEquals(expected[2], heap.toArray()[2]);
		assertEquals(expected[3], heap.toArray()[3]);
		assertEquals(expected[4], heap.toArray()[4]);
		assertEquals(expected.length, heap.toArray().length);
	}
	
	@Test
	public void testExtractMaxEmptyHeap()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
		assertThrows(NoSuchElementException.class, () -> {heap.extractMax();});
	}
	
	@Test
	public void testIsEmpty()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
		assertTrue(heap.isEmpty());
	}
	
	@Test
	public void testIsNotEmpty()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
		heap.add(2);
		assertFalse(heap.isEmpty());
	}
	
	@Test
	public void testIsEmptyAfterClear()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
		assertTrue(heap.isEmpty());
		
		heap.add(3);
		assertFalse(heap.isEmpty());
		
		heap.clear();
		assertTrue(heap.isEmpty());
	}
	
	@Test
	public void testClear()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
		heap.add(2);
		heap.add(50);
		assertEquals(50, heap.peek());
		
		heap.clear();
		assertEquals(0, heap.size());
		assertThrows(NoSuchElementException.class, () -> {heap.extractMax();});
		assertThrows(NoSuchElementException.class, () -> {heap.peek();});
		
	}
	
	@Test
	public void testClearToArray()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<Integer>();
		heap.add(2);
		heap.add(50);
		assertEquals(50, heap.peek());
		
		heap.clear();
		assertEquals(0, heap.size());
		assertThrows(NoSuchElementException.class, () -> {heap.extractMax();});
		assertThrows(NoSuchElementException.class, () -> {heap.peek();});
		
		heap.add(3);
		heap.add(76);
		
		int[] arr = new int[2];
		arr[0] = 76;
		arr[1] = 3;
		
		assertEquals(arr[0], heap.toArray()[0]);
		assertEquals(arr[1], heap.toArray()[1]);
		
	}
	
	
	
	
}
