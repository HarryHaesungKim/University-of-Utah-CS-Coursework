package a4;

import java.util.Scanner;

import java.awt.Color;
/**
 * A collection of methods exercising searching and OptimizingLoops.
 * 
 * @author harrykim
 *
 */

public class SearchAndOptimizingLoops {
	/**
	 * The main method is uesd to make calls to the other methods and print the
	 * testing results. The other methods do not depend on the main to execute their
	 * given tasks.
	 * 
	 * @param args
	 */
	public static void main(String[] arg) {
		String testNumbers = "2 -4 5";
		System.out.println("Of the numbers " + testNumbers + ", the smallest positive number is: "
				+ findSmallestPositiveNumber(testNumbers));
		String testWords = "cat dog apple fish";
		System.out.println("Of the words '" + testWords + "', the lowest alphabetical word is '"
				+ lowestAlphabetically("cat dog apple fish") + "'");
		String TestSetOfNumbers1 = ("12 3 5");
		String TestSetOfNumbers2 = ("2 -1 10");
		System.out.println("Of the two sets of numbers " + TestSetOfNumbers1 + " and " + TestSetOfNumbers2
				+ ", the smallest number is: " + findSmallestNumberInTwoStrings(TestSetOfNumbers1, TestSetOfNumbers2));
		
		String TestScores = "45 85 90";
		System.out.println("Of the scores " + TestScores + ", the new curved scores are: '" + curveScores(TestScores) + "'");

		
		Color colorTest = new Color(87, 162, 243);
		Picture pictureTest = new Picture("Arches.jpg");
		System.out.println(containsThisColor(pictureTest, colorTest));
		
		Color colorTest2 = new Color(1, 4, 7);
		Picture pictureTest2 = new Picture("Arches.jpg");
		System.out.println(containsThisColor(pictureTest2, colorTest2));
		
		//containsThisColor("Arches.jpg",
	}

	/**
	 * This method returns the lowest number that is also greater than zero from a
	 * provided string of numbers separated by spaces.
	 * 
	 * @param numbers A string of numbers.
	 * @return The lowest number greater than 0.
	 */
	public static int findSmallestPositiveNumber(String numbers) { // "2 -4 5"
		Scanner numbersScanner = new Scanner(numbers);
		int smallerNumber = 0;
		int smallestNumber = 0;
		while (numbersScanner.hasNext()) {
			int token = numbersScanner.nextInt();
			if (token > 0) {
				smallerNumber = token;
				if (smallerNumber < smallestNumber || smallestNumber == 0)
					smallestNumber = token;
			} else {
				smallestNumber = smallerNumber;
			}
		}
		numbersScanner.close();
		return smallestNumber;
	}

	/**
	 * This method takes a string of lower-case words separated by spaces and
	 * compares the words to see which one is the lowest alphabetical word using the
	 * compareTo() String method.
	 * 
	 * @param words A string of different words.
	 * @return The lowest alphabetical word.
	 */
	public static String lowestAlphabetically(String words) {
		Scanner stringScanner = new Scanner(words);
		String lowestAlphabeticWord = stringScanner.next();
		while (stringScanner.hasNext()) {
			String token = stringScanner.next();
			int result = token.compareTo(lowestAlphabeticWord);
			if (result < 0)
				lowestAlphabeticWord = token;
		}
		stringScanner.close();
		return lowestAlphabeticWord;
	}

	/**
	 * This method returns the smallest number found in the two strings of numbers.
	 * 
	 * @param numbers1 The first set of numbers.
	 * @param numbers2 The second set of numbers.
	 * @return The smallest number of both sets.
	 */
	public static int findSmallestNumberInTwoStrings(String numbers1, String numbers2) {
		Scanner numbersScanner1 = new Scanner(numbers1);
		int smallestNumber1 = numbersScanner1.nextInt();
		while (numbersScanner1.hasNext()) {
			int token = numbersScanner1.nextInt();
			if (smallestNumber1 > token)
				smallestNumber1 = token;
		}
		numbersScanner1.close();

		Scanner numbersScanner2 = new Scanner(numbers2);
		int smallestNumber2 = numbersScanner2.nextInt();
		while (numbersScanner2.hasNext()) {
			int token = numbersScanner2.nextInt();
			if (smallestNumber2 > token)
				smallestNumber2 = token;
		}
		numbersScanner2.close();

		if (smallestNumber1 < smallestNumber2)
			return smallestNumber1;
		else
			return smallestNumber2;
	}

	/**
	 * This method takes a string of scores and scales them so that the highest
	 * number becomes 100 and all the other numbers are moved up by the same amount.
	 * 
	 * @param scores The string of scores separated by spaces.
	 * @return The new curved scores.
	 */
	public static String curveScores(String scores) {
		Scanner scoresScanner1 = new Scanner(scores);
		int highestScore = scoresScanner1.nextInt();
		while (scoresScanner1.hasNext()) {
			int token = scoresScanner1.nextInt();
			if (highestScore < token)
				highestScore = token;
		}
		scoresScanner1.close();

		Scanner scoresScanner2 = new Scanner(scores);
		String newScores = "";
		int curve = 100 - highestScore;
		while (scoresScanner2.hasNext()) {
			int token = scoresScanner2.nextInt() + curve;
			newScores = newScores + token;
			if (scoresScanner2.hasNext())
				newScores = newScores + " ";
		}
		scoresScanner2.close();
		return newScores;
	}
	/**
	 * 
	 * This method sees if the color parameter matches one of the pixels in the image.
	 * 
	 * @param image A Picture object.
	 * @param match A Color object of your choosing.
	 * @return A boolean value.
	 */
	public static boolean containsThisColor(Picture image, Color match) {
		Picture picture = new Picture(image);
		for (int row = 0; row < picture.height(); row++)
			for (int col = 0; col < picture.width(); col++) {
				Color c = picture.get(col, row);
				Color possibleMatch = new Color(c.getRed(), c.getGreen(), c.getBlue());
				if (possibleMatch.equals(match))
					return true;
			}
		return false;
	}
	

}
