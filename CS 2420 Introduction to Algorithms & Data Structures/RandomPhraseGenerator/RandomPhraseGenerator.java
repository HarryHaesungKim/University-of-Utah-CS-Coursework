package comprehensive;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class generates the specified number of random phrases from the given
 * grammar file
 * 
 * @author Harry Kim && Braden Morfin
 * @version 11/24/2020
 */
public class RandomPhraseGenerator {

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.println(BuildPhrase.buildPhrase(args[0], Integer.parseInt(args[1])));
	}
}