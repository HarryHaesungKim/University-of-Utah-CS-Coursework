package a2;

/**
 * A collection of methods exercising if, while, String, and problem solving.
 * 
 * @author David Johnson
 * @author Harry Kim
 * 
 *         This material is copyright David Johnson January 2020. Derived works
 *         may not be published publicly. This notice must remain. Failure to
 *         follow these copyright restrictions constitutes academic misconduct
 *         for the course and academic sanctions can be applied at any time.
 *
 */
public class ExampleMethods {
	/**
	 * The main method is uesd to make calls to the other methods and print the
	 * testing results. The other methods do not depend on the main to execute their
	 * given tasks.
	 */
	public static void main(String[] args) {
		// Testing describeSignOfNumber() method.
		int testNegativeSign = -7;
		String negativeSignTest = describeSignOfNumber(testNegativeSign);
		System.out.println("The sign of " + testNegativeSign + " is " + negativeSignTest);

		int testNonNegativeSign = 7;
		String nonNegativeSignTest = describeSignOfNumber(testNonNegativeSign);
		System.out.println("The sign of " + testNonNegativeSign + " is " + nonNegativeSignTest);

		System.out.println();

		// Testing classifyNumber() method.
		int testClassifyNegative = -7;
		String classifyNegativeTest = classifyNumber(testClassifyNegative);
		System.out.println("The number " + testClassifyNegative + " is " + classifyNegativeTest);

		int testClassifyZero = 0;
		String classifyZeroTest = classifyNumber(testClassifyZero);
		System.out.println("The number " + testClassifyZero + " is " + classifyZeroTest);

		int testClassifyPositive = 7;
		String classifyPositiveTest = classifyNumber(testClassifyPositive);
		System.out.println("The number " + testClassifyPositive + " is " + classifyPositiveTest);

		System.out.println();

		// Testing isEvenlyDivisibleBySeven() method.
		int testIsEvenlyDivisible = 14;
		boolean isDivisibleBySevenTest = isEvenlyDivisibleBySeven(testIsEvenlyDivisible);
		System.out.println("The number " + testIsEvenlyDivisible + " is divisible by seven: " + isDivisibleBySevenTest);

		int testNotEvenlyDivisible = 16;
		boolean NotDivisibleBySevenTest = isEvenlyDivisibleBySeven(testNotEvenlyDivisible);
		System.out
				.println("The number " + testNotEvenlyDivisible + " is divisible by seven: " + NotDivisibleBySevenTest);

		System.out.println();

		// Testing chooseLargest() method.
		int testLargerNumber = 100;
		int testSmallerNumber = 10;
		double largerNumTest = chooseLargest(testSmallerNumber, testLargerNumber);
		System.out.println("Of the two numbers " + testLargerNumber + " and " + testSmallerNumber
				+ ", the largest number is: " + largerNumTest);

		System.out.println();

		// Testing firstDoublingPastOneHundred() method.
		int testDoublingPastOneHundredTest = 5;
		System.out.println("Doubling the number " + testDoublingPastOneHundredTest
				+ " until it is greater than 100, the number is: "
				+ firstDoublingPastOneHundred(testDoublingPastOneHundredTest));

		System.out.println();

		// Testing everyOtherLetter() method.
		String testString = "David is cool";
		String everyOtherLetterTest = everyOtherLetter(testString);
		System.out.println("The sentence '" + testString + "' including only every other letter is: '"
				+ everyOtherLetterTest + "'");

		System.out.println();

		// Testing makeSquare() method.
		int testSquareWidth = 4;
		String makingSquareTest = makeSquare(testSquareWidth);
		System.out.println(
				"A square with the width of " + testSquareWidth + " units looks like this:" + "\n" + makingSquareTest);
	}

	/**
	 * This method returns a string literal "negative" if value is less than zero,
	 * and returns the string literal "non-negative" if value is greater than or
	 * equal to zero.
	 * 
	 * @param value The number used in the method to determine if its sign is either
	 *              a negative or a non-negative.
	 * @return sign The string literal being either "negative" or "non-negative"
	 *         depending on the input value.
	 */
	public static String describeSignOfNumber(int value) {
		String sign = null;
		if (value < 0)
			sign = "negative";
		else
			sign = "non-negative";
		return sign;
	}

	/**
	 * This method returns a string literal "negative" if value is less than zero,
	 * "zero" if value is zero, and "positive" if value is greater than zero.
	 * 
	 * @param value The number used in the method to determine if it is either
	 *              negative, zero, or positive.
	 * @return classification The string literal being either "negative" or
	 *         "non-negative" depending on the input value.
	 */
	public static String classifyNumber(int value) {
		String classification = null;
		if (value < 0)
			classification = "negative";
		if (value == 0)
			classification = "zero";
		if (value > 0)
			classification = "positive";
		return classification;
	}

	/**
	 * This method returns a boolean expression. It will return "false" if the value
	 * is not divisible by 7, and "true" if value is divisible by 7.
	 * 
	 * @param value The number used in the method to determine if it is divisible by
	 *              7.
	 * @return The boolean expression being either "true" or "false" depending on
	 *         the divisibility of the input value by 7.
	 * 
	 */
	public static boolean isEvenlyDivisibleBySeven(int value) {
		boolean divisibility;
		int remainder = value % 7;
		if (remainder == 0)
			divisibility = true;
		else
			divisibility = false;
		return divisibility;
	}

	/**
	 * This method returns the larger of the two given values.
	 * 
	 * @param number1 One of the two values which will be compared to see which is
	 *                larger.
	 * @param number2 One of the two values which will be compared to see which is
	 *                larger.
	 * @return The largest of the two given values.
	 */
	public static double chooseLargest(double number1, double number2) {
		double largestNum;
		if (number1 > number2)
			largestNum = number1;
		else
			largestNum = number2;
		return largestNum;
	}

	/**
	 * This method takes the startNumber value and doubles it while the value is
	 * less than or equal to 100. As soon as it is greater than 100, that value is
	 * returned.
	 * 
	 * @param startNumber The given value to be doubled until it is greater 100.
	 * @return The value as soon as it has become greater than 100.
	 * 
	 */
	public static int firstDoublingPastOneHundred(int startNumber) {
		while (startNumber <= 100) {
			startNumber = startNumber * 2;
		}
		return startNumber;
	}

	/**
	 * This method takes every other letter from a given string and forms it into a
	 * new string to be returned.
	 * 
	 * @param sentence The input for the method to break up and peice together into
	 *                 a new sentence.
	 * @return The final sentence made of every other letter from the original
	 *         sentence.
	 */
	public static String everyOtherLetter(String sentence) {
		int counter = 0;
		String letter;
		String finalSentence = "";
		while (counter < sentence.length()) {
			letter = sentence.substring(counter, counter + 1);
			counter = counter + 2;
			finalSentence = finalSentence + letter;
			// You can add strings together in a loop to form a sentence.
		}
		return finalSentence;
	}

	/**
	 * Produces a String starting and ending with the edge character and having the
	 * inner char repeated in-between. The total number of characters in the string
	 * is width. As an example makeLine('+', '-', 8) would return the string
	 * "+------+".
	 * 
	 * NOTE: This method is already completely implemented and must not be modified
	 * for the assignment.
	 * 
	 * @param edge  The character used at the start and end of the returned string.
	 * @param inner The character repeated in-between the edge char.
	 * @param width The total number of characters in the returned string. Width
	 *              must be greater or equal to 2.
	 * @return A string with width characters.
	 */
	public static String makeLine(char edge, char inner, int width) {
		String line = "";
		int currentLocation = 0;
		// Make the middle part of the line first.
		while (currentLocation < width - 2) {
			line = line + inner;
			currentLocation = currentLocation + 1;
		}
		// Add in the start and end character to the line.
		return edge + line + edge;
	}

	/**
	 * This method calls the makeLine() method in order to create a square. Said
	 * square is then returned.
	 * 
	 * @param width The given side length for the construction of the square.
	 * @return The finished square.
	 */
	public static String makeSquare(int width) {
		String square = null;
		int count = 2;
		square = makeLine('+', '-', width) + "\n";
		while (count < width) {
			square = square + makeLine('|', ' ', width) + "\n";
			count++;
		}
		square = square + makeLine('+', '-', width) + "\n";
		return square;
	}
}
