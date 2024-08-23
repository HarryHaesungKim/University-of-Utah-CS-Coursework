package assign04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * A class that provides methods to find anagrams.
 * 
 * @author Harry Kim & Braden Morfin
 *
 */
public class AnagramChecker {

	/**
	 * This method returns the lexicographically sorted version of the input string.
	 * 
	 * @param word to be sorted
	 * @return sorted word
	 */
	public static String sort(String word) {

		char[] wordArray = new char[word.length()];
		String sortedWord = "";

		// Puts letters from word into an wordArray.
		for (int i = 0; i < word.length(); i++) {
			wordArray[i] = word.charAt(i);
		}

		// Insertion Sort for wordArray.
		for (int i = 1; i < word.length(); i++) {
			char letter = wordArray[i];
			int j;
			for (j = i - 1; j >= 0 && wordArray[j] > letter; j--) {
				wordArray[j + 1] = wordArray[j];
			}
			wordArray[j + 1] = letter;
		}

		// Puts letters from the sorted wordArray back into a string.
		for (int k = 0; k < word.length(); k++) {
			sortedWord = sortedWord + wordArray[k];
		}

		return sortedWord;
	}

	/**
	 * This generic method sorts the input array using an insertion sort and the
	 * input Comparator object.
	 * 
	 * @param array<T> Array to be sorted
	 * @param <T>      Comparator object that defines how to sort array
	 */
	public static <T> void insertionSort(T[] array, Comparator<? super T> cmp) {

		// Insertion Sort for array.
		for (int i = 1; i < array.length; i++) {
			T value = array[i];
			int j;
			for (j = i - 1; j >= 0 && cmp.compare(array[j], value) > 0; j--) {
				array[j + 1] = array[j];
			}
			array[j + 1] = value;
		}
	}

	/**
	 * This method returns true if the two input strings are anagrams of each other,
	 * otherwise returns false.
	 * 
	 * @param word1 word to be compared
	 * @param word2 other word to be compared
	 * @return boolean
	 */
	public static boolean areAnagrams(String word1, String word2) {
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		word1 = sort(word1);
		word2 = sort(word2);
		return word1.equals(word2);
	}

	/**
	 * This method returns the largest group of anagrams in the input array of
	 * words, in no particular order. It returns an empty array if there are no
	 * anagrams in the input array.
	 * 
	 * @param String array of words
	 * @return String array of largest anagram group
	 */
	public static String[] getLargestAnagramGroup(String[] array) {
		// creates array of largest anagram group
		CompareByAnagram cmp = new CompareByAnagram();
		// Sorts array into groups of anagrams.
		insertionSort(array, cmp);
		
		// Used only for timing question 6.
		//Arrays.sort(array, cmp);
		

		int biggestGroup = 0;
		int tempCountGroup = 1;
		int biggestGroupIndexStartNum = 0;
		int biggestGroupIndexEndNum = 0;

		int counter = 0;

		for (int i = 0; i < array.length - 1; i++) {

			// If adjacent elements are anagrams, increments number of anagrams in group.
			if (areAnagrams(array[i], array[i + 1])) {
				tempCountGroup++;
			} else {

				// Checks if current temporary group of anagrams is larger than the current
				// biggest, then resets number of anagrams in temp group.
				if (tempCountGroup > biggestGroup) {
					biggestGroupIndexEndNum = i;
					biggestGroup = tempCountGroup;
					biggestGroupIndexStartNum = biggestGroupIndexEndNum - (tempCountGroup - 1);
					tempCountGroup = 1;
				} else {
					tempCountGroup = 1;
				}
			}
		}
		
		// Gets last word within array to be checked for biggest anagram group.
		if (tempCountGroup > biggestGroup) {
			biggestGroupIndexEndNum = array.length - 1;
			biggestGroup = tempCountGroup;
			biggestGroupIndexStartNum = biggestGroupIndexEndNum - (tempCountGroup - 1);
		}

		// Size of largest anagram group for the index size of returning array.
		int indexSize = biggestGroupIndexEndNum - biggestGroupIndexStartNum + 1;

		// If there are no anagrams, returns empty array.
		if (indexSize == 1) {
			String[] noAnagrams = new String[0];
			return noAnagrams;
		}

		// Creates and returns an array of largest anagram group.
		String[] biggestAnagramGroup = new String[indexSize];
		counter = 0;
		for (int j = biggestGroupIndexStartNum; j <= biggestGroupIndexEndNum; j++) {
			biggestAnagramGroup[counter] = array[j];
			counter++;
		}
		return biggestAnagramGroup;
	}

	/**
	 * This method behaves the same as getLargestAnagramGroup method, but reads the
	 * list of words from the input filename. It is assumed that the file contains
	 * one word per line. If the file does not exist or is empty, the method returns
	 * an empty array because there are no anagrams.
	 * 
	 * @param filename that contains words
	 * @return String array of largest anagram group
	 */
	public static String[] getLargestAnagramGroup(String filename) {
		File file = new File(filename);
		String[] arrayOfWordsFromFile = new String[0];
		try {
			int size = 0;
			
			// First scanner gets the size.
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				size++;
				sc.next();
			}
			sc.close();
			arrayOfWordsFromFile = new String[size];
			Scanner sc2 = new Scanner(file);
			int counter = 0;
			
			// Puts tokens into array.
			while (sc2.hasNextLine()) {
				arrayOfWordsFromFile[counter] = sc2.next();
				counter++;
			}
			sc2.close();
		}
		catch (FileNotFoundException e) {
			return arrayOfWordsFromFile;
		}
		String[] largestAnagramGroup = getLargestAnagramGroup(arrayOfWordsFromFile);
		return largestAnagramGroup;
	}
	
	/**
	 * Comparator class that compares two words to see if they are anagrams.
	 * 
	 * @author Harry Kim and Braden Morfin
	 *
	 */
	protected static class CompareByAnagram implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			if (AnagramChecker.areAnagrams(o1, o2)) {
				return 0;
			} else {
				return 1;
			}
		}

	}
}