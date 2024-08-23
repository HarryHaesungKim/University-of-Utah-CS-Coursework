package lab8;

import java.util.Scanner;

//Harry Kim U1226472 Lab6
public class PT1 {
	public static void main(String args[]) {
		System.out.println(subSix(7));
		System.out.println(subSix(3));

		System.out.println("The health of this person is: " + determineHealth(91));
		System.out.println("The health of this person is: " + determineHealth(72));
		System.out.println("The health of this person is: " + determineHealth(63.4));
		System.out.println("The health of this person is: " + determineHealth(2));

		System.out.println(makeExpression(2, 8));
		
		System.out.println(first("2 Hi 5 asdfa 3 coolboi 200 100 David 1 Joe"));
		
		int[] test = new int[] {1,2,4,12,4,-3};
		System.out.println(count2and4(test));
	}

	public static int subSix(int number) {
		return number - 6;
	}

	public static String determineHealth(double health) {
		String status = "";
		if (health >= 90) {
			status = "Super";
		}
		if (health >= 70 && health < 90) {
			status = "Healthy";
		}
		if (health >= 20 && health < 70) {
			status = "Struggling";
		}
		if (health < 20) {
			status = "Alive";
		}
		return status;
	}

	public static int makeExpression(int num1, int num2) {
		num1 = num1 + 3;
		num2 = subSix(num2);
		return num1 * num2;
	}

	public static int first(String string) {
		Scanner stringScanner = new Scanner(string);
		int GreaterThan50 = 0;
		//String placeholder = "";
		
		while (stringScanner.hasNextInt()) {
			int number = stringScanner.nextInt();
			if (number > 50) {
				GreaterThan50 = number;
			}
		}
		stringScanner.close();
		
		return GreaterThan50;
	}
	
	public static int count2and4(int[] in) {
		int twos = 0;
		int fours = 0;
		for(int count = 0; count < in.length; count++) {
			if (in[count] == 2) {
				twos++;
			}
			if (in[count] == 4) {
				fours++;
			}
		}
		return twos - fours;
	}
}
