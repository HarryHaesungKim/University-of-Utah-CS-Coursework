package assign03;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * This class contains tests for SimplePriorityQueue.
 * 
 * @author Harry Kim & Braden Morfin
 * @version September 16, 2020
 */
class SimplePriorityQueueTest {

	@Test
	void insertInteger() {
		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
		testQueue.insert(1);
		assertEquals(testQueue.findMin(), 1);
	}
	
	@Test
	void insertString() {
		SimplePriorityQueue<String> testQueue = new SimplePriorityQueue<String>();
		testQueue.insert("a");
		assertEquals(testQueue.findMin(), "a");
	}
	
	@Test
	void insertLongerString() {
		SimplePriorityQueue<String> testQueue = new SimplePriorityQueue<String>();
		testQueue.insert("apple");
		testQueue.insert("bear");
		testQueue.insert("citation");
		assertEquals(testQueue.findMin(), "apple");
	}

	@Test
	void insertIntegerRandomOrder() {
		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
		testQueue.insert(3);
		testQueue.insert(4);
		testQueue.insert(1);
		testQueue.insert(5);
		testQueue.insert(2);
		assertEquals(testQueue.findMin(), 1);
	}
	
	@Test
	void insertStringRandomOrder() {
		SimplePriorityQueue<String> testQueue = new SimplePriorityQueue<String>();
		testQueue.insert("w");
		testQueue.insert("b");
		testQueue.insert("x");
		testQueue.insert("c");
		testQueue.insert("g");
		assertEquals(testQueue.findMin(), "b");
	}

	@Test
	void expandQueInteger() {
		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
		testQueue.insert(3);
		testQueue.insert(4);
		testQueue.insert(1);
		testQueue.insert(5);
		testQueue.insert(2);
		testQueue.insert(3);
		testQueue.insert(4);
		testQueue.insert(1);
		testQueue.insert(5);
		testQueue.insert(2);
		testQueue.insert(3);
		testQueue.insert(4);
		testQueue.insert(1);
		testQueue.insert(5);
		testQueue.insert(2);
		testQueue.insert(3);
		testQueue.insert(4);
		testQueue.insert(1);
		testQueue.insert(5);
		testQueue.insert(2);
		assertEquals(testQueue.size(), 20);
	}

	@Test
	void expandQueString() {
		SimplePriorityQueue<String> testQueue = new SimplePriorityQueue<String>();
		testQueue.insert("w");
		testQueue.insert("b");
		testQueue.insert("x");
		testQueue.insert("c");
		testQueue.insert("g");
		testQueue.insert("w");
		testQueue.insert("b");
		testQueue.insert("x");
		testQueue.insert("c");
		testQueue.insert("g");
		testQueue.insert("w");
		testQueue.insert("b");
		testQueue.insert("x");
		testQueue.insert("c");
		testQueue.insert("g");
		testQueue.insert("w");
		testQueue.insert("b");
		testQueue.insert("x");
		testQueue.insert("c");
		testQueue.insert("g");
		assertEquals(testQueue.size(), 20);
	}
	
	@Test
	void insertHighNumberLastRandomOrder() {
		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
		testQueue.insert(3);
		testQueue.insert(4);
		testQueue.insert(1);
		testQueue.insert(5);
		testQueue.insert(876);
		assertEquals(testQueue.findMin(), 1);
	}
	
	@Test
	void insertHighStringLastRandomOrder() {
		SimplePriorityQueue<String> testQueue = new SimplePriorityQueue<String>();
		testQueue.insert("w");
		testQueue.insert("b");
		testQueue.insert("x");
		testQueue.insert("c");
		testQueue.insert("z");
		assertEquals(testQueue.findMin(), "b");
	}

// add method strictly used publicly for testing binary search private method. Otherwise private.

//	@Test
//	void binaryTest() {
//		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
//		testQueue.add(7);
//		testQueue.add(4);
//		testQueue.add(3);
//		testQueue.add(1);
//		int test = testQueue.binarySearch(4);
//		assertEquals(test, 1);
//	}
//
//	@Test
//	void numberInbetweenTwobinaryTest() {
//		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
//		testQueue.add(7);
//		testQueue.add(4);
//		testQueue.add(3);
//		testQueue.add(1);
//		int test = testQueue.binarySearch(2);
//		assertEquals(test, 3);
//	}
//
//	@Test
//	void numberInbetweenTwobinaryTest2() {
//		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
//		testQueue.add(7);
//		testQueue.add(4);
//		testQueue.add(3);
//		testQueue.add(0);
//		int test = testQueue.binarySearch(1);
//		assertEquals(test, 3);
//	}
//
//	@Test
//	void newHighestNumberbinaryTest() {
//		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
//		testQueue.add(7);
//		testQueue.add(4);
//		testQueue.add(3);
//		testQueue.add(1);
//		int test = testQueue.binarySearch(9);
//		assertEquals(test, 0);
//	}
//
//	@Test
//	void newLowestNumberbinaryTest() {
//		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
//		testQueue.add(7);
//		testQueue.add(4);
//		testQueue.add(3);
//		testQueue.add(1);
//		int test = testQueue.binarySearch(-5);
//		assertEquals(test, 4);
//	}
//
//	@Test
//	void zeroInQueuebinaryTest() {
//		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
//		testQueue.add(7);
//		testQueue.add(4);
//		testQueue.add(3);
//		testQueue.add(1);
//		int test = testQueue.binarySearch(0);
//		assertEquals(test, 4);
//	}

	@Test
	void insertAllInteger() {
		ArrayList<Integer> test = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			test.add(i);
		}
		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
		testQueue.insertAll(test);
		testQueue.insert(21);
		assertEquals(testQueue.findMin(), 0);
	}
	
	@Test
	void insertAllString() {
		ArrayList<String> test = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			test.add("pancake");
		}
		SimplePriorityQueue<String> testQueue = new SimplePriorityQueue<String>();
		testQueue.insertAll(test);
		testQueue.insert("apple");
		assertEquals(testQueue.findMin(), "apple");
	}

	@Test
	void deleteMinInteger() {
		ArrayList<Integer> test = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			test.add(i);
		}

		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
		testQueue.insertAll(test);

		assertEquals(testQueue.size(), 20);
		assertEquals(testQueue.findMin(), 0);

		testQueue.deleteMin();

		assertEquals(testQueue.size(), 19);
		assertEquals(testQueue.findMin(), 1);
	}
	
	@Test
	void deleteMinString() {
		ArrayList<String> test = new ArrayList<String>();
		for (int i = 0; i < 19; i++) {
			test.add("pancake");
		}
		test.add("apple");
		SimplePriorityQueue<String> testQueue = new SimplePriorityQueue<String>();
		testQueue.insertAll(test);
		
		assertEquals(testQueue.size(), 20);
		assertEquals(testQueue.findMin(), "apple");
		
		testQueue.deleteMin();
		
		assertEquals(testQueue.size(), 19);
		assertEquals(testQueue.findMin(), "pancake");
	}

	@Test
	void clearInteger() {
		ArrayList<Integer> test = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			test.add(i);
		}

		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
		testQueue.insertAll(test);

		assertEquals(testQueue.size(), 20);
		assertEquals(testQueue.findMin(), 0);

		testQueue.clear();

		assertEquals(testQueue.size(), 0);
		assertThrows(NoSuchElementException.class, () -> {
			testQueue.findMin();
		});
	}
	
	@Test
	void clearString() {
		ArrayList<String> test = new ArrayList<String>();
		for (int i = 0; i < 19; i++) {
			test.add("pancake");
		}
		test.add("apple");
		SimplePriorityQueue<String> testQueue = new SimplePriorityQueue<String>();
		testQueue.insertAll(test);
		
		assertEquals(testQueue.size(), 20);
		assertEquals(testQueue.findMin(), "apple");
		
		testQueue.clear();
		
		assertEquals(testQueue.size(), 0);
		assertThrows(NoSuchElementException.class, () -> {
			testQueue.findMin();
		});	}

	@Test
	void deleteMinException() {
		SimplePriorityQueue<Integer> testQueue = new SimplePriorityQueue<Integer>();
		assertThrows(NoSuchElementException.class, () -> {
			testQueue.deleteMin();
		});
	}
}