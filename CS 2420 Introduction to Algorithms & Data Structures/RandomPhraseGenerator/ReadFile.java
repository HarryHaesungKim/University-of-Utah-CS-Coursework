package comprehensive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class takes a valid grammar file and creates a map of the nonTerminals
 * and their values
 * 
 * @author Harry Kim & Braden Morfin
 * @version 11/24/2020
 */
public class ReadFile {

	// boolean that tracks if we are in the definition of a nonterminal
	private static boolean inDefinition = false;

	/**
	 * This method takes a valid grammar file and creates a map of the nonTerminals
	 * and their production rules.
	 * 
	 * @param fileName - the name of the valid grammar file
	 * @return - map of the nonTerminals and their values
	 * @throws IOException
	 */
	public static HashMap<String, ArrayList<String>> readFile(String fileName) throws IOException {

		// creates the given file and passes it into a BufferedReader to be read
		FileReader file = new FileReader(fileName);
		BufferedReader br = new BufferedReader(file);

		// creates a hashMap of NonTerminals and their production rules
		HashMap<String, ArrayList<String>> nonTerminalsList = new HashMap<String, ArrayList<String>>();

		// goes through the file
		String line = br.readLine();
		while (line != null) {

			// creates an empty string to store the nonTerminal
			String nonTerminal = "";

			// creates an empty ArrayList to stores the values of the nonTerminal
			ArrayList<String> nonTerminalValues = new ArrayList<String>();

			// checks that we are at the beginning of a nonTerminal Definition
			if (line.equals("{")) {
				// the next line after the "{" must be the non Terminal whose definition we are
				// looking at
				nonTerminal = br.readLine();
				inDefinition = true;
			}

			// while in the definition of a nonTerminal, add each line to list of production
			// rules of that nonTerminal
			while (inDefinition) {
				line = br.readLine();
				if (line.equals("}"))
					inDefinition = false;
				else
					nonTerminalValues.add(line);
			}

			// checks that line was not a comment, making sure we are not adding a
			// comment to the list of nonTerminals
			if (!nonTerminal.equals(""))
				nonTerminalsList.put(nonTerminal, nonTerminalValues);

			line = br.readLine();
		}

		// close BufferedReaders and return the map of nonTerminals and their production
		// rules
		br.close();
		return nonTerminalsList;
	}
}