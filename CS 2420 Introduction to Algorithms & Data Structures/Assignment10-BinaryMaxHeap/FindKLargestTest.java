package assign10;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class FindKLargestTest {

	@Test
	void testFindLargestHeapNoComparator() {

		List<Integer> list = new ArrayList<Integer>();

		list.add(3);
		list.add(4);
		list.add(18);
		list.add(66);
		list.add(7);
		list.add(2);

		List<Integer> actual = FindKLargest.findKLargestHeap(list, 3);

		List<Integer> expected = new ArrayList<Integer>();

		expected.add(66);
		expected.add(18);
		expected.add(7);

		assertEquals(expected.toArray()[0], actual.toArray()[0]);
		assertEquals(expected.toArray()[1], actual.toArray()[1]);
		assertEquals(expected.toArray()[2], actual.toArray()[2]);
	}
	
	@Test
	void testFindLargestHeapNoComparatorMoreKValues() {

		List<Integer> list = new ArrayList<Integer>();

		list.add(3);
		list.add(4);
		list.add(18);
		list.add(66);
		list.add(7);
		list.add(2);
		list.add(77);
		list.add(45);
		list.add(183);
		list.add(665);
		list.add(71);
		list.add(20);

		List<Integer> actual = FindKLargest.findKLargestHeap(list, 5);

		List<Integer> expected = new ArrayList<Integer>();

		expected.add(665);
		expected.add(183);
		expected.add(77);
		expected.add(71);
		expected.add(66);

		assertEquals(expected.toArray()[0], actual.toArray()[0]);
		assertEquals(expected.toArray()[1], actual.toArray()[1]);
		assertEquals(expected.toArray()[2], actual.toArray()[2]);
		assertEquals(expected.toArray()[3], actual.toArray()[3]);
		assertEquals(expected.toArray()[4], actual.toArray()[4]);
	}
	
	@Test
	void testFindLargestHeapYesComparator() {

		List<Integer> list = new ArrayList<Integer>();

		list.add(3);
		list.add(4);
		list.add(18);
		list.add(66);
		list.add(7);
		list.add(2);

		List<Integer> actual = FindKLargest.findKLargestHeap(list, 3, (i1,i2)  -> (i2-i1));

		List<Integer> expected = new ArrayList<Integer>();

		expected.add(2);
		expected.add(3);
		expected.add(4);

		assertEquals(expected.toArray()[0], actual.toArray()[0]);
		assertEquals(expected.toArray()[1], actual.toArray()[1]);
		assertEquals(expected.toArray()[2], actual.toArray()[2]);
	}
	
	@Test
	void testFindLargestHeapYesComparatorMoreKValues() {

		List<Integer> list = new ArrayList<Integer>();

		list.add(3);
		list.add(4);
		list.add(18);
		list.add(66);
		list.add(7);
		list.add(2);
		list.add(77);
		list.add(45);
		list.add(183);
		list.add(665);
		list.add(71);
		list.add(20);

		List<Integer> actual = FindKLargest.findKLargestHeap(list, 5, (i1,i2)  -> (i2-i1));

		List<Integer> expected = new ArrayList<Integer>();

		expected.add(2);
		expected.add(3);
		expected.add(4);
		expected.add(7);
		expected.add(18);

		assertEquals(expected.toArray()[0], actual.toArray()[0]);
		assertEquals(expected.toArray()[1], actual.toArray()[1]);
		assertEquals(expected.toArray()[2], actual.toArray()[2]);
		assertEquals(expected.toArray()[3], actual.toArray()[3]);
		assertEquals(expected.toArray()[4], actual.toArray()[4]);
	}
	
// Java's sort
	
	@Test
	void testfindKLargestSortNoComparator() {

		List<Integer> list = new ArrayList<Integer>();

		list.add(3);
		list.add(4);
		list.add(18);
		list.add(66);
		list.add(7);
		list.add(2);

		List<Integer> actual = FindKLargest.findKLargestSort(list, 3);

		List<Integer> expected = new ArrayList<Integer>();

		expected.add(66);
		expected.add(18);
		expected.add(7);

		assertEquals(expected.toArray()[0], actual.toArray()[0]);
		assertEquals(expected.toArray()[1], actual.toArray()[1]);
		assertEquals(expected.toArray()[2], actual.toArray()[2]);
	}
	
	@Test
	void testfindKLargestSortNoComparatorMoreValues() {

		List<Integer> list = new ArrayList<Integer>();

		list.add(3);
		list.add(4);
		list.add(18);
		list.add(66);
		list.add(7);
		list.add(2);
		list.add(77);
		list.add(45);
		list.add(183);
		list.add(665);
		list.add(71);
		list.add(20);

		List<Integer> actual = FindKLargest.findKLargestSort(list, 5);

		List<Integer> expected = new ArrayList<Integer>();

		expected.add(665);
		expected.add(183);
		expected.add(77);
		expected.add(71);
		expected.add(66);

		assertEquals(expected.toArray()[0], actual.toArray()[0]);
		assertEquals(expected.toArray()[1], actual.toArray()[1]);
		assertEquals(expected.toArray()[2], actual.toArray()[2]);
		assertEquals(expected.toArray()[3], actual.toArray()[3]);
		assertEquals(expected.toArray()[4], actual.toArray()[4]);
	}
	
	@Test
	void testfindKLargestSortYesComparator() {

		List<Integer> list = new ArrayList<Integer>();

		list.add(3);
		list.add(4);
		list.add(18);
		list.add(66);
		list.add(7);
		list.add(2);

		List<Integer> actual = FindKLargest.findKLargestSort(list, 3, (i1,i2)  -> (i2-i1));

		List<Integer> expected = new ArrayList<Integer>();

		expected.add(2);
		expected.add(3);
		expected.add(4);

		assertEquals(expected.toArray()[0], actual.toArray()[0]);
		assertEquals(expected.toArray()[1], actual.toArray()[1]);
		assertEquals(expected.toArray()[2], actual.toArray()[2]);
	}
	
	@Test
	void testfindKLargestSortYesComparatorMoreValues() {

		List<Integer> list = new ArrayList<Integer>();

		list.add(3);
		list.add(4);
		list.add(18);
		list.add(66);
		list.add(7);
		list.add(2);
		list.add(77);
		list.add(45);
		list.add(183);
		list.add(665);
		list.add(71);
		list.add(20);

		List<Integer> actual = FindKLargest.findKLargestSort(list, 5, (i1,i2)  -> (i2-i1));

		List<Integer> expected = new ArrayList<Integer>();

		expected.add(2);
		expected.add(3);
		expected.add(4);
		expected.add(7);
		expected.add(18);

		assertEquals(expected.toArray()[0], actual.toArray()[0]);
		assertEquals(expected.toArray()[1], actual.toArray()[1]);
		assertEquals(expected.toArray()[2], actual.toArray()[2]);
		assertEquals(expected.toArray()[3], actual.toArray()[3]);
		assertEquals(expected.toArray()[4], actual.toArray()[4]);
	}

}
