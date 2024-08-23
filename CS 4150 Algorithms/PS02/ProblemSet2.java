package problemSet2;

import java.util.Scanner;

public class ProblemSet2 {

	public static void main(String[] args) {

		Scanner inputScanner = new Scanner(System.in);

		// Gathering input for number of records.
		int records = inputScanner.nextInt();

		// Listing the variables.
		int rightIndex = records - 1;
		int leftIndex = 0;
		int centerIndex = (rightIndex - leftIndex) / 2 + leftIndex;

		int center;
		int right;
		int left;
		
		int centerRightOne = -1;
		int centerLeftOne = -1;

		// Gathering inputs.
		System.out.println("query " + 0);
		left = inputScanner.nextInt();

		System.out.println("query " + (records - 1));
		right = inputScanner.nextInt();

		center = -1;

		boolean leftIndexChanged = false;
		boolean rightIndexChanged = false;

		boolean leftFalseOrRightTrue; // false for left true for right

		if (left < right) {
			// curve starts at the left
			leftFalseOrRightTrue = false;
			
		} else {
			// curve starts at right
			leftFalseOrRightTrue = true;
			
			// adjusting centerIndex for right side.
			if(records % 2 == 0) {
				centerIndex = (rightIndex - leftIndex) / 2 + leftIndex + 1;
			}
		}

		while (true) {
			// If downward curve starts from left
			// I'm pretty sure left is working...
			if (!leftFalseOrRightTrue) {

				if (leftIndexChanged) {
					System.out.println("query " + leftIndex);
					left = inputScanner.nextInt();
					leftIndexChanged = false;
				}

				System.out.println("query " + centerIndex);
				center = inputScanner.nextInt();
				
				System.out.println("query " + (centerIndex + 1));
				centerRightOne = inputScanner.nextInt();
				
				if (centerIndex - leftIndex <= 1 && center <= left && center < centerRightOne) {
					System.out.println("minimum " + centerIndex);
					break;
				}

				if (left > center && center > centerRightOne) {
					int prevCenterIndex = centerIndex;
					leftIndex = centerIndex;
					leftIndexChanged = true;
					centerIndex = (rightIndex - leftIndex) / 2 + leftIndex;
					if(prevCenterIndex == centerIndex) {
						centerIndex++;
					}
				} else {
					rightIndex = centerIndex;
					centerIndex = (rightIndex - leftIndex) / 2 + leftIndex;
				}
			}

			// If downward curve starts from the right
			// Need to update. Make like left above.
			else {

				if (rightIndexChanged) {
					System.out.println("query " + rightIndex);
					right = inputScanner.nextInt();
					rightIndexChanged = false;
				}

				System.out.println("query " + centerIndex);
				center = inputScanner.nextInt();
				
				System.out.println("query " + (centerIndex - 1));
				centerLeftOne = inputScanner.nextInt();
				
				if (rightIndex - centerIndex <= 1 && center <= right && center < centerLeftOne) {
					System.out.println("minimum " + centerIndex);
					break;
				}

				if (right > center && center > centerLeftOne) {
					int prevCenterIndex = centerIndex;
					rightIndex = centerIndex;
					rightIndexChanged = true;
					centerIndex = rightIndex - (rightIndex - leftIndex) / 2;
					if(prevCenterIndex == centerIndex) {
						centerIndex--;
					}
				} else {
					leftIndex = centerIndex;
					centerIndex = rightIndex - (rightIndex - leftIndex) / 2;
				}
			}
		}

		inputScanner.close();
	}
}