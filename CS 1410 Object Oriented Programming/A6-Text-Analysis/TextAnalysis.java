package a6;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.Scanner;

public class TextAnalysis {

	public static void main(String args[]) {
		String negative = "src/a6/negative.txt";
		String positive = "src/a6/positive.txt";
		String stopWords = "src/a6/stop_words.txt";
		String TaleOfTwoCities = "src/a6/TaleOfTwoCities.txt";
		String TheScarletLetter = "src/a6/TheScarletLetter.txt";
		String trumpSpeech = "src/a6/trumpSpeech.txt";

		// Number of words tests
		System.out.println("Number of words in this text: " + numberOfWordsInTextFile(trumpSpeech));

		// File to string array tests
		System.out.println(Arrays.toString(convertFileToStringArray(trumpSpeech)));

		// String to lower case tests
		System.out.println(Arrays.toString(stringtoLowerCase(trumpSpeech)));

		// Remove punctuation tests
		System.out.println(Arrays.toString(removePunctuationCharacters(trumpSpeech)));
		System.out.println();

		// Remove boring words tests
		System.out.println(Arrays.toString(removeBoringWords(trumpSpeech)));

		// Positive and Negative words count tests
		System.out.println("The number of positive words is: " + numOfPositiveWords(trumpSpeech));
		System.out.println("The number of negative words is: " + numOfNegativeWords(trumpSpeech));

		// Positive and Negative words percentage tests
		System.out.println("The percent of positive words is: " + percentageOfPositive(trumpSpeech) + "%");
		System.out.println("The percent of negative words is: " + percentageOfNegative(trumpSpeech) + "%");

		// Sentiment tests
		System.out.println("The overall sentiment is: " + sentiment(trumpSpeech));
		System.out.println();

		// Chunks tests
		System.out.println(reportingInChunks(TheScarletLetter));
		System.out.println();
		System.out.println(reportingInChunks(TaleOfTwoCities));
		System.out.println();
		System.out.println(reportingInChunks(trumpSpeech));
		System.out.println();	

		// Other tests
		String my = "src/a6/my.txt";

		System.out.println(reportingInChunks(my));
		System.out.println("The number of positive words is: " + numOfPositiveWords(my));
		System.out.println("The number of negative words is: " + numOfNegativeWords(my));

		System.out.println("The percent of positive words is: " + percentageOfPositive(my) + "%");
		System.out.println("The percent of negative words is: " + percentageOfNegative(my) + "%");
	}

	/**
	 * The textFileToString() takes a text and converts the whole thing into a
	 * string.
	 * 
	 * @param textFile The provided text.
	 * @return The huge string.
	 */
	public static String textFileToString(String textFile) {
		String entireText = "";
		File file = new File(textFile);
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(file);
			// Loops and adds to the entireText string.
			while (fileScanner.hasNext()) {
				String token = fileScanner.next();
				entireText = entireText + token + " ";
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		}
		return entireText;
	}

	/**
	 * The numberOfWordsInText() method counts the number of words in the given
	 * string.
	 * 
	 * @param text The provided string.
	 * @return The number of words.
	 */
	public static int numberOfWordsInText(String text) {
		int total = 0;
		Scanner stringScanner = new Scanner(text);
		// Loops and counts tokens in string.
		while (stringScanner.hasNext()) {
			String token = stringScanner.next();
			total++;
		}
		return total;
	}

	/**
	 * numberOfWordsInTextFile() Method just feeds the textFileToString() method
	 * through the numberOfWordsInText() method to go directly from text file to
	 * number of words in said file.
	 * 
	 * @param textFile The provided text
	 * @return The number of words.
	 */
	public static int numberOfWordsInTextFile(String textFile) {
		return numberOfWordsInText(textFileToString(textFile));

	}

	/**
	 * The convertFileToStringArray() method converts a text file into a string
	 * array of its given tokens.
	 * 
	 * @param textFile The provided text.
	 * @return A string array.
	 */
	public static String[] convertFileToStringArray(String textFile) {
		String[] stringArray = new String[numberOfWordsInTextFile(textFile)];
		File file = new File(textFile);
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(file);
			int index = 0;
			while (fileScanner.hasNext()) {
				String token = fileScanner.next();
				stringArray[index] = token;
				index++;
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		}
		return stringArray;
	}

	/**
	 * The StringtoLowerCase() method converts the words in the provided text into
	 * all lower case letter words in an array.
	 * 
	 * @param textFile The provided text.
	 * @return The lowercase words in an array.
	 */
	public static String[] stringtoLowerCase(String textFile) {
		String[] lowerCaseArray = convertFileToStringArray(textFile);
		for (int count = 0; count < lowerCaseArray.length; count++) {
			lowerCaseArray[count] = lowerCaseArray[count].toLowerCase();
		}
		return lowerCaseArray;
	}

	/**
	 * The removePunctuationCharacters() method removes punctuations from the text
	 * file array.
	 * 
	 * @param textFile The provided text.
	 * @return The words without punctuations in an array.
	 */
	public static String[] removePunctuationCharacters(String textFile) {
		String[] noPunctuationArray = stringtoLowerCase(textFile);
		for (int count = 0; count < noPunctuationArray.length; count++) {
			noPunctuationArray[count] = noPunctuationArray[count].replaceAll("[^a-zA-Z ]", "");
		}
		return noPunctuationArray;
	}

	/**
	 * The removeBoringWords() method takes a text file and removes the boring words
	 * in the form of an array.
	 * 
	 * @param textFile    The provided text.
	 * @param boringWords The list of boring words to be taken out.
	 * @return A not boring worded array.
	 */
	public static String[] removeBoringWords(String textFile) {
		String stopWords = "src/a6/stop_words.txt";
		String[] boringWordsArray = convertFileToStringArray(stopWords);
		String[] originalText = removePunctuationCharacters(textFile);
		String[] noBoringWordsArray = new String[numberOfWordsInTextFile(textFile)];
		// Nested for loop looks at each word and compares them to the boring words
		// list. If one is found, the word is removed and in its place is a null.
		for (int i = 0; i < originalText.length; i++) {
			Boolean found = false;
			for (int j = 0; j < boringWordsArray.length; j++) {
				if (originalText[i].contentEquals(boringWordsArray[j])) {
					noBoringWordsArray[i] = null;
					found = true;
				}
			}
			if (found == false) {
				noBoringWordsArray[i] = originalText[i];
			}
		}
		return noBoringWordsArray;
	}

	/**
	 * The numOfNonNullWords() method counts the number of non null values within a
	 * text.
	 * 
	 * @param textFile The provided text.
	 * @return The number of non null values.
	 */
	public static int numOfNonNullWords(String textFile) {
		String[] originalText = removeBoringWords(textFile);
		int numOfNonNull = 0;
		// Nested for loop looks at each word in the text to see if it is null or not.
		// If it is, numOfNonNull is incremented by 1.
		for (int i = 0; i < originalText.length; i++) {
			Boolean found = false;
			// If the value in the original array is a null, just ignore it and move on.
			if (originalText[i] == null) {
				numOfNonNull = numOfNonNull + 0;
			}
			if (originalText[i] != null) {
				numOfNonNull++;

			}
		}
		return numOfNonNull;
	}

	/**
	 * The numOfPositiveWords() method counts the number of positive words within a
	 * text.
	 * 
	 * @param textFile The provided text.
	 * @return The number of positive words.
	 */
	public static int numOfPositiveWords(String textFile) {
		String positive = "src/a6/positive.txt";
		String[] positiveWordsArray = convertFileToStringArray(positive);
		String[] originalText = removeBoringWords(textFile);
		int numOfPositives = 0;
		// Nested for loop looks at each word in the original text and compares them to
		// the positivie words list. If one is found, int numOfPositives is incremented
		// by 1.
		for (int i = 0; i < originalText.length; i++) {
			Boolean found = false;
			// If the value in the original array is a null, just ignore it and move on.
			if (originalText[i] == null) {
				numOfPositives = numOfPositives + 0;
			}
			if (originalText[i] != null) {
				for (int j = 0; j < positiveWordsArray.length; j++) {
					if (originalText[i].contentEquals(positiveWordsArray[j])) {
						numOfPositives++;
						found = true;
					}
				}
				if (found == false) {
					numOfPositives = numOfPositives + 0;
				}
			}
		}
		return numOfPositives;
	}

	/**
	 * The numOfNegativeWords() method counts the number of negative words within a
	 * text.
	 * 
	 * @param textFile The provided text.
	 * @return The number of negative words.
	 */
	public static int numOfNegativeWords(String textFile) {
		String negative = "src/a6/negative.txt";
		String[] negativeWordsArray = convertFileToStringArray(negative);
		String[] originalText = removeBoringWords(textFile);
		int numOfNegatives = 0;
		// Nested for loop looks at each word in the original text and compares them to
		// the negative words list. If one is found, int numOfNegatives is incremented
		// by 1.
		for (int i = 0; i < originalText.length; i++) {
			Boolean found = false;
			// If the value in the original array is a null, just ignore it and move on.
			if (originalText[i] == null) {
				numOfNegatives = numOfNegatives + 0;
			}
			if (originalText[i] != null) {
				for (int j = 0; j < negativeWordsArray.length; j++) {
					if (originalText[i].contentEquals(negativeWordsArray[j])) {
						numOfNegatives++;
						found = true;
					}
				}
				if (found == false) {
					numOfNegatives = numOfNegatives + 0;
				}
			}
		}
		return numOfNegatives;
	}

	/**
	 * The percentageOfPositive method returns the percentage of positive words to
	 * all the words in the given text.
	 * 
	 * @param textFile The provided text.
	 * @return The percentage.
	 */
	public static double percentageOfPositive(String textFile) {
		int numOfPositives = numOfPositiveWords(textFile);
		int totalWords = numOfNonNullWords(textFile);
		// Careful math to keep boolean.
		return (numOfPositives * 100.0) / totalWords;
	}

	/**
	 * The percentageOfNegative method returns the percentage of negative words to
	 * all the words in the given text.
	 * 
	 * @param textFile The provided text.
	 * @return The percentage.
	 */
	public static double percentageOfNegative(String textFile) {
		int numOfNegatives = numOfNegativeWords(textFile);
		int totalWords = numOfNonNullWords(textFile);
		// Careful math to keep boolean.
		return (numOfNegatives * 100.0) / totalWords;
	}

	/**
	 * The sentiment() method calculates the sentiment by subtracting the percentage
	 * of negative words by the percentage of positive words.
	 * 
	 * @param textFile The provided text.
	 * @return The sentiment value.
	 */
	public static double sentiment(String textFile) {
		double positivePercentage = percentageOfPositive(textFile);
		double negativePercentage = percentageOfNegative(textFile);
		return positivePercentage - negativePercentage;
	}

	/**
	 * The numOfNonNullWords() method counts the number of non null values within an
	 * array.
	 * 
	 * @param textArray The provided text.
	 * @return The number of non null values.
	 */
	public static int numOfNonNullWordsInArray(String[] textArray) {
		String[] originalText = textArray;
		int numOfNonNull = 0;
		// Nested for loop looks at each word in the text to see if it is null or not.
		// If it is, numOfNonNull is incremented by 1.
		for (int i = 0; i < originalText.length; i++) {
			// If the value in the original array is a null, just ignore it and move on.
			if (originalText[i] == null) {
				numOfNonNull = numOfNonNull + 0;
			}
			if (originalText[i] != null) {
				numOfNonNull++;

			}
		}
		return numOfNonNull;
	}

	/**
	 * The numOfPositiveWordsInArray() method counts the number of positive words
	 * within an array.
	 * 
	 * @param textArray The provided array.
	 * @return The number of positive words.
	 */
	public static int numOfPositiveWordsInArray(String[] textArray) {
		String positive = "src/a6/positive.txt";
		String[] positiveWordsArray = convertFileToStringArray(positive);
		String[] originalText = textArray;
		int numOfPositives = 0;
		// Nested for loop looks at each word in the original text and compares them to
		// the positivie words list. If one is found, int numOfPositives is incremented
		// by 1.
		for (int i = 0; i < originalText.length; i++) {
			Boolean found = false;
			// If the value in the original array is a null, just ignore it and move on.
			if (originalText[i] == null) {
				numOfPositives = numOfPositives + 0;
			}
			if (originalText[i] != null) {
				for (int j = 0; j < positiveWordsArray.length; j++) {
					if (originalText[i].contentEquals(positiveWordsArray[j])) {
						numOfPositives++;
						found = true;
					}
				}
				if (found == false) {
					numOfPositives = numOfPositives + 0;
				}
			}
		}
		return numOfPositives;
	}

	/**
	 * The numOfNegativeWordsInArray() method counts the number of positive words
	 * within an array.
	 * 
	 * @param textArray The provided array.
	 * @return The number of positive words.
	 */
	public static int numOfNegativeWordsInArray(String[] textArray) {
		String negative = "src/a6/negative.txt";
		String[] negativeWordsArray = convertFileToStringArray(negative);
		String[] originalText = textArray;
		int numOfNegatives = 0;
		// Nested for loop looks at each word in the original text and compares them to
		// the negative words list. If one is found, int numOfNegatives is incremented
		// by 1.
		for (int i = 0; i < originalText.length; i++) {
			Boolean found = false;
			// If the value in the original array is a null, just ignore it and move on.
			if (originalText[i] == null) {
				numOfNegatives = numOfNegatives + 0;
			}
			if (originalText[i] != null) {
				for (int j = 0; j < negativeWordsArray.length; j++) {
					if (originalText[i].contentEquals(negativeWordsArray[j])) {
						numOfNegatives++;
						found = true;
					}
				}
				if (found == false) {
					numOfNegatives = numOfNegatives + 0;
				}
			}
		}
		return numOfNegatives;
	}

	/**
	 * The percentageOfPositiveFromArray() method returns the percentage of positive
	 * words to all the words in the given array.
	 * 
	 * @param textArray The provided array.
	 * @return The percentage.
	 */
	public static double percentageOfPositiveFromArray(String[] textArray) {
		int numOfPositives = numOfPositiveWordsInArray(textArray);
		int totalWords = numOfNonNullWordsInArray(textArray);
		// Careful math to keep boolean.
		return (numOfPositives * 100.0) / totalWords;
	}

	/**
	 * The percentageOfNegativeFromArray() method returns the percentage of negative
	 * words to all the words in the given array.
	 * 
	 * @param textArray The provided array.
	 * @return The percentage.
	 */
	public static double percentageOfNegativeFromArray(String[] textArray) {
		int numOfNegatives = numOfNegativeWordsInArray(textArray);
		int totalWords = numOfNonNullWordsInArray(textArray);
		// Careful math to keep boolean.
		return (numOfNegatives * 100.0) / totalWords;
	}

	/**
	 * The sentimentFromArray() method calculates the sentiment by subtracting the
	 * percentage of negative words by the percentage of positive words.
	 * 
	 * @param textArray The provided array.
	 * @return The sentiment value.
	 */
	public static double sentimentFromArray(String[] textArray) {
		double positivePercentage = percentageOfPositiveFromArray(textArray);
		double negativePercentage = percentageOfNegativeFromArray(textArray);
		return positivePercentage - negativePercentage;
	}

	/**
	 * The reportingInChunks() method and breaks the original text file into 10
	 * roughly equal parts. It then calculates the sentiment for each part and
	 * reports it.
	 * 
	 * @param textFile The provided text
	 * @return The report.
	 */
	public static String reportingInChunks(String textFile) {

		System.out.println("Reporting on " + textFile);

		System.out.println("Overall sentiment is: " + sentiment(textFile));

		String[] words = removeBoringWords(textFile);

		int totalWords = numberOfWordsInTextFile(textFile);
		int chunkSize = totalWords / 10;
		int incrementation = 0;

		for (int count = 0; count < 10; count++) {
			String[] singleChunk = new String[chunkSize];

			for (int count1 = 0; count1 < chunkSize; count1++) {
				singleChunk[count1] = words[incrementation];
				incrementation++;
			}

			System.out.println("chunk" + (count + 1) + ": " + sentimentFromArray(singleChunk));

		}
		return "Finished report on " + textFile;
	}

}
