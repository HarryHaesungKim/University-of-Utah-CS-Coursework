package assign05;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class ArrayListSorterTester {
	@Test
	public void mergeSort() {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(5);
		arr.add(46);
		arr.add(16);
		arr.add(19);
		arr.add(15);
		arr.add(-4);
		arr.add(100);
		ArrayList<Integer> sortedArr = new ArrayList<Integer>();
		sortedArr.add(-4);
		sortedArr.add(5);
		sortedArr.add(15);
		sortedArr.add(16);
		sortedArr.add(19);
		sortedArr.add(46);
		sortedArr.add(100);
		ArrayListSorter.mergesort(arr);
		assertEquals(sortedArr, arr);
	}

	@Test
	public void mergSortUsingInsertionSort() {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(5);
		arr.add(46);
		arr.add(16);
		arr.add(19);
		arr.add(15);
		arr.add(-4);
		arr.add(100);
		ArrayList<Integer> sortedArr = new ArrayList<Integer>();
		sortedArr.add(-4);
		sortedArr.add(5);
		sortedArr.add(15);
		sortedArr.add(16);
		sortedArr.add(19);
		sortedArr.add(46);
		sortedArr.add(100);
		ArrayListSorter.mergesort(arr);
		assertEquals(sortedArr, arr);
	}

	@Test
	public void mergeSortDuplicates() {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(7);
		arr.add(10);
		arr.add(7);
		arr.add(3);
		arr.add(20);
		arr.add(21);
		ArrayListSorter.mergesort(arr);
		ArrayList<Integer> sortedArr = new ArrayList<Integer>();
		sortedArr.add(3);
		sortedArr.add(7);
		sortedArr.add(7);
		sortedArr.add(10);
		sortedArr.add(20);
		sortedArr.add(21);
		assertEquals(sortedArr, arr);
	}
	
	@Test
	public void mergeSortPermuted() {
		ArrayList<Integer> arr = ArrayListSorter.generatePermuted(10);
		ArrayListSorter.mergesort(arr);
		ArrayList<Integer> sortedArr = ArrayListSorter.generateAscending(10);
		assertEquals(sortedArr, arr);
	}

	@Test
	public void quickSortDuplicates() {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(7);
		arr.add(10);
		arr.add(7);
		arr.add(3);
		arr.add(20);
		arr.add(21);
		ArrayListSorter.quicksort(arr);
		ArrayList<Integer> sortedArr = new ArrayList<Integer>();
		sortedArr.add(3);
		sortedArr.add(7);
		sortedArr.add(7);
		sortedArr.add(10);
		sortedArr.add(20);
		sortedArr.add(21);
		assertEquals(sortedArr, arr);
	}

	@Test
	public void quickSortDescendedList() {
		ArrayList<Integer> arr = ArrayListSorter.generateDescending(5);
		ArrayList<Integer> sortedArr = ArrayListSorter.generateAscending(5);
		ArrayListSorter.quicksort(arr);
		assertEquals(sortedArr, arr);
	}

	@Test
	public void quickSortAscendedList() {
		ArrayList<Integer> arr = ArrayListSorter.generateAscending(5);
		ArrayList<Integer> sortedArr = ArrayListSorter.generateAscending(5);
		ArrayListSorter.quicksort(arr);
		assertEquals(sortedArr, arr);
	}

	@Test
	public void quickSortPermutedList() {
		ArrayList<Integer> arr = ArrayListSorter.generatePermuted(5);
		ArrayList<Integer> sortedArr = ArrayListSorter.generateAscending(5);
		ArrayListSorter.quicksort(arr);
		assertEquals(sortedArr, arr);
	}
	
	@Test
	public void mergeDescendedList()
	{
		ArrayList<Integer> arr = ArrayListSorter.generateDescending(5);
		ArrayList<Integer> sortedArr = ArrayListSorter.generateAscending(5);
		ArrayListSorter.quicksort(arr);
		assertEquals(sortedArr, arr);
	}
	
	@Test
	public void charArrayTest()
	{
		ArrayList<Character> arr1 = new ArrayList<Character>();
		arr1.add('w');
		arr1.add('b');
		arr1.add('t');
		arr1.add('P');
		
		ArrayList<Character> arr2 = new ArrayList<Character>(arr1);
		
		ArrayListSorter.quicksort(arr1);
		
		ArrayListSorter.mergesort(arr2);
		
		ArrayList<Character> sortedArr = new ArrayList<Character>();
		
		sortedArr.add('P');
		sortedArr.add('b');
		sortedArr.add('t');
		sortedArr.add('w');
		
		assertEquals(sortedArr, arr1);
		assertEquals(sortedArr, arr2);
	}
	
	@Test
	public void mergeEmptyArray()
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		ArrayList<Integer> sortedArr = new ArrayList<Integer>();
		
		ArrayListSorter.mergesort(arr);
		
		assertEquals(sortedArr, arr);
	}
	
	@Test
	public void quickEmptyArray()
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		ArrayList<Integer> sortedArr = new ArrayList<Integer>();
		
		ArrayListSorter.quicksort(arr);
		
		assertEquals(sortedArr, arr);
	}
	

}