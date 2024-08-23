package problemSet1;

import java.util.Arrays;
import java.util.Scanner;

public class ProblemSet1 {
	public static void main(String[] args) {

		Scanner userInput = new Scanner(System.in);

		// Gathering inputs for number of words (n) and length of each word (k).
		int n = userInput.nextInt();
		int k = userInput.nextInt();

		// Gathering inputs.
		String[] words = new String[n];
		for (int i = 0; i < n; i++) {
			String s = userInput.next();
			s = reorderCharsOfString(s);
			words[i] = s;
		}

		Arrays.sort(words);

		// For testing.
		// System.out.println(Arrays.toString(words));
		
		// Finding anagram groups.
		int anagramGroups = 0;
		for (int i = 1; i <= words.length; i++) {
			// If an anagram group exists.
			if (i != words.length - 1) {
				if (words[i].equals(words[i - 1]) && !words[i].equals(words[i + 1])) {
					anagramGroups++;
				}
			}
			// If end of array has been reached.
			else if (i == words.length - 1) {
				if (words[i].equals(words[i - 1])) {
					anagramGroups++;
				}
				break;
			}
		}
		
		System.out.print(anagramGroups);
		userInput.close();
	}

	/**
	 * This method takes a string, sorts the characters of that string into
	 * alphabetical order, then returns the ordered string.
	 * 
	 * @param input - The input string.
	 * @return a new string whose characters are in alphabetical order.
	 */
	public static String reorderCharsOfString(String input) {
		char[] arrayOfCharsFromInput = input.toCharArray();
		Arrays.sort(arrayOfCharsFromInput);
		String sortedString = new String(arrayOfCharsFromInput);
		return sortedString;
	}
}