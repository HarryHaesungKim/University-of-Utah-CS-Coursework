package a3;

import java.util.Scanner;
import java.awt.Color;

/**
 * A collection of methods exercising loops and image processing.
 * 
 * @author harrykim
 *
 */
public class LoopsAndImages {
	/**
	 * The main method is uesd to make calls to the other methods and print the
	 * testing results. The other methods do not depend on the main to execute their
	 * given tasks.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String hideLetterATest = hideLetterA("A rabbit has a carrot");
		System.out.println(hideLetterATest);

		String testString = "1 3 4 6 -8";
		boolean hasMoreEvenThanOddTest = hasMoreEvenThanOdd(testString);
		System.out.println("Of these tokens: (" + testString + "), There are more even numbers than odd ones: "
				+ hasMoreEvenThanOddTest);
		
		String testString2 = "";
		boolean hasMoreEvenThanOddTest2 = hasMoreEvenThanOdd(testString2);
		System.out.println("Of these tokens: (" + testString2 + "), There are more even numbers than odd ones: "
				+ hasMoreEvenThanOddTest2);

		String makeTextTriangleTest = makeTextTriangle(4);
		System.out.println(makeTextTriangleTest);

		// For Reference
		System.out.println("Printing original picture...");
		Picture original = new Picture("Arches.jpg");
		original.show();

		System.out.println("Printing greyscale picture...");
		Picture makeGreyTest = new Picture("Arches.jpg");
		makeGreyTest = makeGrey(makeGreyTest);
		makeGreyTest.show();

		System.out.println("Printing inverted picture...");
		Picture makeNegativeTest = new Picture("Arches.jpg");
		makeNegativeTest = makeNegative(makeNegativeTest);
		makeNegativeTest.show();

		int testValue = 300;
		int safeColorTest = safeColor(testValue);
		System.out.println("From the previous value " + testValue + ", The new safe color value is: " + safeColorTest);

		int testValue2 = 100;
		int safeColorTest2 = safeColor(testValue2);
		System.out
				.println("From the previous value " + testValue2 + ", The new safe color value is: " + safeColorTest2);

		int testValue3 = -100;
		int safeColorTest3 = safeColor(testValue3);
		System.out
				.println("From the previous value " + testValue3 + ", The new safe color value is: " + safeColorTest3);

		System.out.println("Printing brighter picture...");
		Picture makeBrighterTest = new Picture("Arches.jpg");
		makeBrighterTest = makeBrighter(makeBrighterTest);
		makeBrighterTest.show();
	}

	/**
	 * The hideLetterA() method takes every letter 'a' from the given string and
	 * replaces it with '*'.
	 * 
	 * @param sentence The string input to rewrite with every 'a' replaced with '*'.
	 * @return The final sentence made of '*' for every letter 'a' from the original
	 *         sentence.
	 */
	public static String hideLetterA(String sentence) {
		int counter = 0;
		char letter;
		String finalSentence = "";
		while (counter < sentence.length()) {
			letter = sentence.charAt(counter);
			if (letter == 'a') {
				finalSentence = finalSentence + "*";
				counter++;
			} else {
				finalSentence = finalSentence + letter;
				counter++;
			}
		}
		return finalSentence;
	}

	/**
	 * The hasMoreEvenThanOdd() method determins whether or not the tokens in the
	 * provided string have more even numbers than odd ones.
	 * 
	 * @param numbers A string of numbers to be determined if it contains more evens
	 *                than odds.
	 * @return A boolean expression 'true' if the string has more even tokens than
	 *         odd, and 'false' if the string has more odd than even tokens.
	 */
	public static boolean hasMoreEvenThanOdd(String numbers) { // "1 3 4 6 -8"
		int evens = 0;
		int odds = 0;
		Scanner strScanner = new Scanner(numbers);
		while (strScanner.hasNext()) {
			int token = strScanner.nextInt();
			// System.out.println(token);
			if (token % 2 == 0)
				evens++;
			else
				odds++;
		}
		strScanner.close();
		if (evens > odds)
			return true;
		else
			return false;
	}
	/**
	 * The makeTextTriangle() method takes a text triangle.
	 * 
	 * @param value The value which will tell the method how many rows the triangle
	 *              will have.
	 * @return The finished text triangle.
	 */
	public static String makeTextTriangle(int value) {
		if (value < 0)
			return "Value must be between 0 and 20.";
		if (value > 20)
			return "Value must be between 0 and 20.";
		else {
			String triangle = "";
			int counter2 = 1;
			for (int counter = 0; counter < value; counter++) {
				int counter1 = 0;
				while (counter1 < counter2) {
					triangle = triangle + "*";
					counter1++;
				}
				counter2++;
				triangle = triangle + "\n";
			}
			return triangle;
		}
	}
	/**
	 * The makeGrey() method takes an image and makes it into a greyscale image.
	 * 
	 * @param image A Picture object, or the source image.
	 * @return A Picture object which is a greyscale image of the original one.
	 */
	public static Picture makeGrey(Picture image) {
		Picture picture = new Picture(image);
		Picture greyPicture = new Picture(picture);
		for (int row = 0; row < greyPicture.height(); row++)
			for (int col = 0; col < greyPicture.width(); col++) {
				Color c = greyPicture.get(col, row);
				int averageColor = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
				Color greyscale = new Color(averageColor, averageColor, averageColor);

				greyPicture.set(col, row, greyscale);
			}
		return greyPicture;
	}
	/**
	 * The makeNegative() takes an image and produces a new image which is the same
	 * as the old one but with inverted colors.
	 * 
	 * @param image A Picture object, or the source image.
	 * @return A Picture object which is a color inverted image of the original one.
	 */
	public static Picture makeNegative(Picture image) {
		Picture picture = new Picture(image);
		Picture negativePicture = new Picture(picture);
		for (int row = 0; row < negativePicture.height(); row++)
			for (int col = 0; col < negativePicture.width(); col++) {
				Color c = negativePicture.get(col, row);
				int inverseRed = (255 - c.getRed());
				int inverseGreen = (255 - c.getGreen());
				int inverseBlue = (255 - c.getBlue());
				Color negativeScale = new Color(inverseRed, inverseGreen, inverseBlue);

				negativePicture.set(col, row, negativeScale);
			}
		return negativePicture;
	}
	/**
	 * The safeColor method takes a value and limits the value between 0 - 255.
	 * 
	 * @param value A single int value representing one of a red, green, or blue
	 *              value.
	 * @return An int that is the same as the parameter, except that it is 0 if the
	 *         original value is less than zero and it is 255 if the original value
	 *         is greater than 255.
	 */
	public static int safeColor(int value) {
		if (value > 255)
			value = 255;
		if (value < 0)
			value = 0;
		return value;
	}
	/**
	 * The makeNegative() takes an image and produces a new image which is brighter
	 * than old one. This method implements the safeColor() method to ensure that
	 * the int variables do not exeed the 0 - 255 limits.
	 * 
	 * @param image A Picture object, or the source image.
	 * @return A Picture object which is brighter than the original one.
	 */
	public static Picture makeBrighter(Picture image) {
		Picture picture = new Picture(image);
		Picture brighterPicture = new Picture(picture);
		for (int row = 0; row < brighterPicture.height(); row++)
			for (int col = 0; col < brighterPicture.width(); col++) {
				Color c = brighterPicture.get(col, row);
				int brighterRed = (c.getRed() * 2);
				brighterRed = safeColor(brighterRed);
				int brighterGreen = (c.getGreen() * 2);
				brighterGreen = safeColor(brighterGreen);
				int brighterBlue = (c.getBlue() * 2);
				brighterBlue = safeColor(brighterBlue);
				Color brightScale = new Color(brighterRed, brighterGreen, brighterBlue);
				brighterPicture.set(col, row, brightScale);
			}
		return brighterPicture;
	}
}
