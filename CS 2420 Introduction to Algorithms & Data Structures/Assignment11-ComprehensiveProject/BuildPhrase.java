package comprehensive;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * This class builds the random phrase from the given map of nonTerminals and
 * their production rules.
 * 
 * @author Harry Kim && Braden Morfin
 * @version 11/24/2020
 */
public class BuildPhrase {

	// map of nonTerminals and their production rules
	private static HashMap<String, ArrayList<String>> nonTerminals;

	// random phrase to be returned
	private static StringBuilder returnPhrase;

	// random number generator used to make a randomPhrase
	private static Random rng = new Random();

	/**
	 * This method builds a random phrase using the given grammar file
	 * 
	 * @param fileName - the given valid grammar file
	 * @return - a random phrase using the given grammar rules
	 * @throws IOException
	 */
	public static String buildPhrase(String fileName, int numOfPhrases) throws IOException {

		returnPhrase = new StringBuilder("");
		nonTerminals = ReadFile.readFile(fileName);
		StringBuilder allPhrases = new StringBuilder();

		for (int i = 0; i < numOfPhrases; i++) {
			allPhrases.append(buildPhraseHelper("<start>") + "\n");
			returnPhrase = new StringBuilder("");
		}
		return allPhrases.toString();
	}

	/**
	 * This method recursively builds a random phrase from the map of nonTerminals
	 * and their production rules
	 * 
	 * @param nonTerminal - nonTerminal key of the map
	 * @return - random phrase
	 */
	private static String buildPhraseHelper(String nonTerminal) {
		// creates an empty ArrayList to store the production rules of the input
		// nonTerminal
		ArrayList<String> nonTerminalValues = nonTerminals.get(nonTerminal);

		// if the nonTerminal is <start> we must print all of it's production rules
		if (nonTerminal.equals("<start>")) {
			String productionRule = nonTerminalValues.get((rng.nextInt(nonTerminalValues.size())));
				buildTerminal(productionRule);
		}

		// the nonTerminal is not <start>, this means we must chose a random value from
		// the given nonTerminal
		else {
			String value = nonTerminalValues.get(rng.nextInt(nonTerminalValues.size()));
			buildTerminal(value);
		}

		return returnPhrase.toString();
	}

	/**
	 * This method takes in a value of a nonTerminal and adds it to the
	 * returnPhrase, if the production rule itself is or contains another
	 * nonTerminal, it will Recursively choose a random value for the nonTerminal
	 * that was a value of the given nonTerminal
	 * 
	 * @param valueOfNonTerminal - a value of a nonTerminal
	 */
	public static void buildTerminal(String valueOfNonTerminal) {
		// creates StringBuilder that builds any nonTerminal in the definition of the
		// given nonTerminal and sets boolean inNonTerminal to false

		StringBuilder nonTerminalInValue = new StringBuilder("");

		boolean inNonTerminal = false;

		// loops through a single production rule for a non-terminal character by
		// character
		for (int i = 0; i < valueOfNonTerminal.length(); i++) {
			switch (valueOfNonTerminal.charAt(i)) {

			// this case checks for the beginning of a non-terminal
			case '<':
				nonTerminalInValue.append(valueOfNonTerminal.charAt(i));
				inNonTerminal = true;
				break;

			// this case checks for the end of a non-terminal
			case '>':
				inNonTerminal = false;
				nonTerminalInValue.append(valueOfNonTerminal.charAt(i));
				buildPhraseHelper(nonTerminalInValue.toString());
				nonTerminalInValue = new StringBuilder("");
				break;

			// default cases (not the start or end of non-terminal)
			default:

				// checks if we are within a non-terminal
				if (inNonTerminal && valueOfNonTerminal.charAt(i) != '>')
					nonTerminalInValue.append(valueOfNonTerminal.charAt(i));

				// else we are looking a terminal and add it to the return phrase
				else
					returnPhrase.append(valueOfNonTerminal.charAt(i));
			}
		}
	}
}