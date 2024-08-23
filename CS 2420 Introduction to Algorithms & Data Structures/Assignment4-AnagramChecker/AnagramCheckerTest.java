package assign04;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import assign04.AnagramChecker.CompareByAnagram;

/**
 * A testing class that tests the AnagramChecker class.
 * 
 * @author Harry Kim & Braden Morfin
 */
class AnagramCheckerTest {

	@Test
	void sortTest() {
		String testString = "crates";
		assertEquals(AnagramChecker.sort(testString), "acerst");
	}

	@Test
	void sortTestLongerWord() {
		String testString = "zyxwvutsrqponmlkjihgfedcba";
		assertEquals(AnagramChecker.sort(testString), "abcdefghijklmnopqrstuvwxyz");
	}

	@Test
	void sortTestUppercaseLetters() {
		String testString = "aA";
		assertEquals(AnagramChecker.sort(testString), "Aa");
	}

	@Test
	void insertionSortStrings() {
		String[] testStringArrayExptected = new String[] { "cat", "beet", "apple" };
		String[] testStringArray = new String[] { "cat", "apple", "beet" };
		AnagramChecker.insertionSort(testStringArray, (String val1, String val2) -> val1.length() - val2.length());
		assertEquals(testStringArray[0], testStringArrayExptected[0]);
		assertEquals(testStringArray[1], testStringArrayExptected[1]);
		assertEquals(testStringArray[2], testStringArrayExptected[2]);
	}

	@Test
	void insertionSortGroupOfAnagrams() {
		String[] testStringArrayExptected = new String[] { "caller", "two", "Cellar", "sheep", "praised", "money",
				"diapers", "ispreda", "ispread" };
		CompareByAnagram cmp = new CompareByAnagram();
		AnagramChecker.insertionSort(testStringArrayExptected, cmp);
	}

	@Test
	void areAnagramsTrue() {
		String testString = "crates";
		String testString2 = "carets";
		assertEquals(AnagramChecker.areAnagrams(testString, testString2), true);
	}

	@Test
	void areAnagramsUpperCase() {
		String testString = "craTes";
		String testString2 = "carets";
		assertEquals(AnagramChecker.areAnagrams(testString, testString2), true);
	}

	@Test
	void areAnagramsFalse() {
		String testString = "craes";
		String testString2 = "carets";
		assertEquals(AnagramChecker.areAnagrams(testString, testString2), false);
	}

	@Test
	void areAnagramsEmptyStrings() {
		String testString = "";
		String testString2 = "";
		assertEquals(AnagramChecker.areAnagrams(testString, testString2), true);
	}

	@Test
	void getLargestAnagramGroupTest() {
		String[] testStringArray = new String[] { "caller", "two", "Cellar", "sheep", "praised", "money", "diapers",
				"ispreda", "ispread" };
		String[] testStringArrayActual = AnagramChecker.getLargestAnagramGroup(testStringArray);
		String[] testStringArrayExptected = new String[] { "praised", "diapers", "ispreda", "ispread" };
		assertEquals(testStringArrayActual[0], testStringArrayExptected[0]);
		assertEquals(testStringArrayActual[1], testStringArrayExptected[1]);
		assertEquals(testStringArrayActual[2], testStringArrayExptected[2]);
		assertEquals(testStringArrayActual[3], testStringArrayExptected[3]);
	}

	@Test
	void noAnagramGroupTest() {
		String[] testStringArray = new String[] { "caller", "two", "sheep", "praised", "money" };
		String[] testStringArrayActual = AnagramChecker.getLargestAnagramGroup(testStringArray);
		String[] testStringArrayExptected = new String[0];
		assertEquals(testStringArrayActual.length, testStringArrayExptected.length);
	}

	@Test
	void getLargestAnagramGroupFileTest() {
		String[] testStringArrayActual = AnagramChecker.getLargestAnagramGroup("src/assign04/sample_word_list.txt");

		String[] testStringArrayExptected = new String[] { "carets", "Caters", "caster", "crates", "Reacts", "recast",
				"traces" };

		assertEquals(testStringArrayActual[0], testStringArrayExptected[0]);
		assertEquals(testStringArrayActual[1], testStringArrayExptected[1]);
		assertEquals(testStringArrayActual[2], testStringArrayExptected[2]);
		assertEquals(testStringArrayActual[3], testStringArrayExptected[3]);
		assertEquals(testStringArrayActual[4], testStringArrayExptected[4]);
		assertEquals(testStringArrayActual[5], testStringArrayExptected[5]);
		assertEquals(testStringArrayActual[6], testStringArrayExptected[6]);
	}
	
	@Test
	void getLargestAnagramGroupNoFileTest() {
		String[] testStringArrayActual = AnagramChecker.getLargestAnagramGroup("Does not exist");
		assertEquals(testStringArrayActual.length, 0);
	}
}