package a5;

public class ArraysAndSound {
	public static void main(String args[]) {
		int[] test = new int[] { 1, 3, 2, 1 };
		// Miscellaneous tests for array stuff.
		System.out.println(test.length);
		System.out.println(test[0]);
		System.out.println(arrayToString(test));
		clearArray(test);
		System.out.println(arrayToString(test));

		System.out.println();

		// array to string testsl
		int[] test1 = new int[] { 1, 3, 2, 6 };
		System.out.println("The array is: " + arrayToString(test1));
		int[] emptyTest = new int[] {};
		System.out.println("The array is: " + arrayToString(emptyTest));

		System.out.println();

		// contains duplicate tests.
		String[] stringTestTrue = new String[] { "c", "b", "a", "y", "z", "r", "a" };
		String[] stringTestFalse = new String[] { "c", "b", "a", "y", "z", "r", "o" };
		String[] stringTestEmpty = new String[] {};

		System.out.println("Contains duplicate? " + containsDuplicate(stringTestTrue));
		System.out.println("Contains duplicate? " + containsDuplicate(stringTestFalse));
		System.out.println("Contains duplicate? " + containsDuplicate(stringTestEmpty));

		System.out.println();

		// average of values tests.
		int[] averageTest = new int[] { 1, 3, 2, 6 };
		int[] averageTestEasy = new int[] { 2, 2 };
		int[] averageTestOneNum = new int[] { 453 };

		System.out.println("The average is " + averageArrayValues(averageTest));
		System.out.println("The average is " + averageArrayValues(averageTestEasy));
		System.out.println("The average is " + averageArrayValues(averageTestOneNum));

		System.out.println();

		// frequency tests.
		int[] frequencyTest = new int[] { 0, 0, 1, 1, 1, 7 };
		int[] frequencyTestEmpty = new int[] {};
		int[] frequencyTestError = new int[] { -4, -6, 13, 151324, -13949 };

		System.out.println("The frequency of each number is: " + arrayToString(frequencyCount(frequencyTest)));
		System.out.println("The frequency of each number is: " + arrayToString(frequencyCount(frequencyTestEmpty)));
		System.out.println("The frequency of each number is: " + arrayToString(frequencyCount(frequencyTestError)));

		System.out.println();

		// reverse tests.
		double[] reverseTest = new double[] { 3.0, 5.0, 1.4, 7.1, 1.2, 7.0 };
		double[] reversedArray = reverseSound(reverseTest);
		System.out.println("Not reversed array: " + arrayToStringDouble(reverseTest));
		System.out.println("Reversed array: " + arrayToStringDouble(reversedArray));

		System.out.println();

		// scale tests.
		double[] ScaleTest = new double[] { 0.0, -0.1, 0.3 };
		double[] ScaledArray = scaleSound(ScaleTest, 2);
		System.out.println("Not scaled array: " + arrayToStringDouble(ScaleTest));
		System.out.println("Scaled array: " + arrayToStringDouble(ScaledArray));

		double[] ScaledArrayZero = scaleSound(ScaleTest, 0);
		System.out.println("Not scaled array: " + arrayToStringDouble(ScaleTest));
		System.out.println("Scaled array: " + arrayToStringDouble(ScaledArrayZero));
		System.out.println();

		// echo tests
		double[] echoTest = new double[] { 0.1, 0.2, 0.3, 0.4 };
		System.out.println("Echo array: " + arrayToStringDouble(echoSound(echoTest, 1, 0.5)));
		System.out.println("Echo array: " + arrayToStringDouble(echoSound(echoTest, 2, 0.5)));
		System.out.println("Echo array: " + arrayToStringDouble(echoSound(echoTest, 3, 0.5)));
		System.out.println();

		// smooth tests
		double[] smoothTest = new double[] { 0.0, 0.2, 0.7, 0.2 };
		System.out.println("Origial array: " + arrayToStringDouble((smoothTest)));
		System.out.println("smooth sound array: " + arrayToStringDouble(smoothSound(smoothTest)));

		// appreciation
		double[] samples = StdAudio.read("asyouwish2.wav");
		StdAudio.play(samples);
		double[] reversed = reverseSound(samples);
		StdAudio.play(reversed);
		double[] echoed = echoSound(samples, 5, 0.75);
		StdAudio.play(echoed);
		double[] smooth = smoothSound(samples);
		StdAudio.play(smooth);
	}

	/**
	 * The clearArray() method takes an array parameter and has every element in
	 * said array set to 0.
	 * 
	 * @param array the given array. No return. Only clears the array.
	 */
	public static void clearArray(int[] array) {
		int counter = 0;
		// for loop makes every value into 0 for the length of the array.
		for (int count = 0; count < array.length; count++) {
			array[counter] = 0;
			counter++;
		}
	}

	/**
	 * The arrayToString() method takes an array and prints it out in a string so
	 * that we can see what's inside the array.
	 * 
	 * @param array The given array.
	 * @return The string form of the array.
	 */
	public static String arrayToString(int[] array) {
		String stringArray = "{";
		for (int count = 0; count < array.length; count++) {
			// if the count is at the last value, ", " is not added.
			if (count == array.length - 1) {
				stringArray = stringArray + array[count];
			}
			// otherwise, the value and ", " are added.
			else {
				stringArray = stringArray + array[count] + ", ";
			}
		}
		stringArray = stringArray + "}";
		return stringArray;
	}

	/**
	 * The same as the arrayToString() method, but for double arrays.
	 * 
	 * For testing.
	 * 
	 * @param array The given array.
	 * @return The string form of the array.
	 */
	public static String arrayToStringDouble(double[] array) {
		String stringArray = "{";
		for (int count = 0; count < array.length; count++) {
			// if the count is at the last value, ", " is not added.
			if (count == array.length - 1) {
				stringArray = stringArray + array[count];
			}
			// otherwise, the value and ", " are added.
			else {
				stringArray = stringArray + array[count] + ",";
			}
		}
		stringArray = stringArray + "}";
		return stringArray;
	}

	/**
	 * The containsDuplicate() method returns a boolean expression whether or not
	 * one element of the array is equal() to another element of the array.
	 * 
	 * @param array The given Array.
	 * @return A boolean expression.
	 */
	public static boolean containsDuplicate(String[] array) {
		// this for loop is for each value.
		for (int valueCount = 0; valueCount < array.length; valueCount++) {
			String currentValue = array[valueCount];
			// this for loop compares the current value to the rest of the array
			for (int equalsCount = valueCount; equalsCount < array.length; equalsCount++) {
				int CurrentEqualsCount = equalsCount + 1;
				// this if statement ensures that there is no out of bounds error.
				if (CurrentEqualsCount != array.length) {
					if (currentValue.equals(array[equalsCount + 1])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * The averageArrayValues() method gets the average of the values within the
	 * given array.
	 * 
	 * @param array The given Array.
	 * @return The double average value.
	 */
	public static double averageArrayValues(int[] array) {
		int sum = 0;
		// this for loop adds each value in the given array to sum.
		for (int count = 0; count < array.length; count++) {
			int value = array[count];
			sum = sum + value;
		}
		return sum / array.length;
	}

	/**
	 * The frequencyCounter() method creates new int array of 10 integers. The
	 * values in the new array is the frequency of which a value appears in the
	 * array.
	 * 
	 * @param array The given Array.
	 * @return The new frequency array.
	 */
	public static int[] frequencyCount(int[] array) {
		int zeros = 0;
		int ones = 0;
		int twos = 0;
		int threes = 0;
		int fours = 0;
		int fives = 0;
		int sixes = 0;
		int sevens = 0;
		int eights = 0;
		int nines = 0;

		int[] frequencyArray = new int[10];
		for (int count = 0; count < array.length; count++) {
			// These if statements are for counting when a certain value (between 0 and 9)
			// appear.
			if (array[count] == 0) {
				zeros++;
				frequencyArray[0] = zeros;
			}
			if (array[count] == 1) {
				ones++;
				frequencyArray[1] = ones;
			}
			if (array[count] == 2) {
				twos++;
				frequencyArray[2] = twos;
			}
			if (array[count] == 3) {
				threes++;
				frequencyArray[3] = threes;
			}
			if (array[count] == 4) {
				fours++;
				frequencyArray[4] = fours;
			}
			if (array[count] == 5) {
				fives++;
				frequencyArray[5] = fives;
			}
			if (array[count] == 6) {
				sixes++;
				frequencyArray[6] = sixes;
			}
			if (array[count] == 7) {
				sevens++;
				frequencyArray[7] = sevens;
			}
			if (array[count] == 8) {
				eights++;
				frequencyArray[8] = eights;
			}
			if (array[count] == 9) {
				nines++;
				frequencyArray[9] = nines;
			}
		}
		return frequencyArray;
	}

	/**
	 * The reverseSound() method returns a new double array that has its elements in
	 * reversed order from the given array.
	 * 
	 * @param array The given Array.
	 * @return The reversed Array.
	 */
	public static double[] reverseSound(double[] array) {
		double[] reversedArray = new double[array.length];
		int reversedArrayPosition = array.length - 1;
		// for loop makes the first value in the given array be the last value in
		// reversedArray and works its way from the back.
		for (int count = 0; count < array.length; count++) {
			reversedArray[reversedArrayPosition] = array[count];
			reversedArrayPosition--;
		}
		return reversedArray;
	}

	/**
	 * Returns a new double array that has each of its values in the parameter array
	 * scaled by the second parameter without changing the original array.
	 * 
	 * @param array The given array.
	 * @param value The scale factor.
	 * @return A new scaled array.
	 */
	public static double[] scaleSound(double[] array, double value) {
		double[] scaledArray = new double[array.length];
		// for every value in the new array, it's the value in the given array
		// multiplied by the scale factor value.
		for (int count = 0; count < array.length; count++) {
			scaledArray[count] = array[count] * value;
		}
		return scaledArray;
	}

	/**
	 * The echoSound() method creates an "echo" effect by having the original sound
	 * plus the weighted weaker sound by an offset value.
	 * 
	 * @param array  The given array.
	 * @param offset How many samples offset the echo starts at.
	 * @param weight The weight to the echo.
	 * @return A new echoing array.
	 */
	public static double[] echoSound(double[] array, int offset, double weight) {
		double[] echoArray = new double[array.length + offset];
		// The new echoArray array is the same as the original, but is offset and
		// multiplied by the weight.
		for (int count = 0; count < array.length; count++) {
			echoArray[count + offset] = array[count] * weight;
		}
		// the given array and echoArray array are added together.
		for (int count = 0; count < array.length; count++) {
			echoArray[count] = echoArray[count] + array[count];
		}
		return echoArray;
	}

	/**
	 * The smoothSound() method takes the givin array and creates a new array that
	 * has as its values as a "sliding" average of the parameter array values.
	 * 
	 * @param array The given array.
	 * @return The modified "smooth" array.
	 */
	public static double[] smoothSound(double[] array) {
		double[] smoothArray = new double[array.length];
		for (int count = 0; count < array.length; count++) {
			// if we are at the first array value, then we only average the first two values
			// to avoid error.
			if (count == 0) {
				smoothArray[count] = (array[count] + array[count + 1]) / 2;
			}
			// if we are at the last array value, then we only average the last two values
			// to avoid error.
			else if (count == array.length - 1) {
				smoothArray[count] = (array[count] + array[count - 1]) / 2;
			}
			// The rest, we average the current value, the value before, and the value
			// after.
			else {
				smoothArray[count] = (array[count] + array[count + 1] + array[count - 1]) / 3;
			}
		}
		return smoothArray;
	}
}
