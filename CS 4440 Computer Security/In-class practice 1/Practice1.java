package practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Practice1 {
	public static void main(String[] args) {
		//challenge1();
		// The deciphered text for challenge1 is "helloworld".
		
		challenge2();
		
	}

	public static void challenge1() {
		// First I will set the input as the text to be deciphered.
		String input = "ebiiltloia";

		// Then I will set a new string as the decoded text.
		String newString = "";

		// I will try every shift amount equal to the letters of the alphabet starting
		// by shifting right.
		// If no coherent words or phrases are found, I will shift left.
		for (int j = 1; j < 27; j++) {

			int shiftAmmount = j;

			for (int i = 0; i < input.length(); i++) {

				// input.charAt(i) + shiftAmmount is shifting right. input.charAt(i) -
				// shiftAmmount is shifting left.
				char shiftedChar = (char) (input.charAt(i) + shiftAmmount);

				// char shiftedChar = (char) (input.charAt(i) + shiftAmmount);

				newString = newString + shiftedChar;
			}

			System.out.println(newString);
			System.out.println();

			newString = "";
		}
	}
	
	public static void challenge2() {
		// First I will set the input as the text to be deciphered.
		String input = "4-'3evh?'c)7%t#e-r,g6u#.9uv#%tg2v#7g'w6gA";

		// Then I will set a new string as the decoded text.
		String newString = "";
		
		ArrayList<String> texts = new ArrayList<String>();

		// I will try every shift amount left and right of the number of ASCII values.
		// I will try to find a text that makes the most sense and then try to decipher that one again.
		for (int j = -128; j < 129; j++) {

			int shiftAmmount = j;

			for (int i = 0; i < input.length(); i++) {

				char shiftedChar = (char) (input.charAt(i) + shiftAmmount);

				newString = newString + shiftedChar;
			}
			
			boolean atleastOneAlpha = newString.matches(".*[a-zA-Z]+.*");
			
			if(atleastOneAlpha) {
//				System.out.println(newString);
//				System.out.println();
				texts.add(newString);
			}
			newString = "";
		}
		
		// I will try every shift amount left and right of the number of ASCII values.
		// I will try to find a text that makes the most sense and then try to decipher that one again.
		
		for(int k = 0; k < 10; k++) {
			
			for (int j = -128; j < 129; j++) {

				int shiftAmmount = j;

				for (int i = 0; i < texts.get(k).length(); i++) {
					
					if(input.charAt(i) > 65) {
						char shiftedChar = (char) (texts.get(k).charAt(i) + shiftAmmount);
						newString = newString + shiftedChar;
					}
					else {
						newString = newString + texts.get(k).charAt(i);
					}

				}
				
				boolean atleastOneAlpha = newString.matches(".*[a-zA-Z]+.*");
				
				if(atleastOneAlpha) {
					System.out.println(newString);
					System.out.println();
				}
				newString = "";
			}
		}
	}
}
