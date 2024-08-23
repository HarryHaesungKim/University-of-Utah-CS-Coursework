package assign06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class containing the checkFile method for checking if the (, [, and { symbols
 * in an input file are correctly matched.
 * 
 * @author Erin Parker, Harry Kim, && Braden Morfin
 * @version 10/15/2020
 */
public class BalancedSymbolChecker {

	/**
	 * Generates a message indicating whether the input file has unmatched symbols.
	 * (Use the helper methods below for constructing messages.)
	 * 
	 * @param filename - name of the input file to check
	 * @return a message indicating whether the input file has unmatched symbols
	 * @throws FileNotFoundException if the file does not exist
	 */
	public static String checkFile(String filename) throws FileNotFoundException {
		File file = new File(filename);
		ArrayStack<Character> stack = new ArrayStack<Character>();

		boolean withinComment = false;

		// First scanner gets the size.
		Scanner sc = new Scanner(file);
		int line = 0;
		// Looping through each line.
		while (sc.hasNextLine()) {
			// Increments each line and sets the string
			// currentLine to the nextline of the scanner
			line++;
			String currentLine = sc.nextLine();

			// Loops through the string currentLine
			for (int i = 0; i < currentLine.length(); i++) {

				// sets char current to char at index i of the string
				char current = currentLine.charAt(i);

				// If we are within a comment ignore the current, unless the current is 
				// an '*' and the char after current is a '/', indicating
				// the end of a comment
				if (withinComment) {
					// Checks to see if a comment ends.
					if (current == '*' && currentLine.charAt(i + 1) == '/')
						withinComment = false;
				}

				// Else, look at the value of current
				else {

					// Checks for the beginning of a comment (/*)
					// checks that we are not at the end index of currentLine,
					// as to not get an exception
					if (i != currentLine.length() - 1) { 
						// If we are the beginning of a comment set withComment to true
						if (current == '/' && currentLine.charAt(i + 1) == '*') {
							withinComment = true;
							// While the char at i of currentLine and char after are not */,
							// ending of a comment then ignore the current char at i
							// and increment i to look at the next characters
							while (!(currentLine.charAt(i) == '*' && currentLine.charAt(i + 1) == '/')) {
								i++;
								// if we are still within a comment and we reach the end of the string
								// break the while loop
								if (i == currentLine.length())
									break;
							}
						}
					}

					// only occurs when we break the above loop because the rest of the current
					// line is in a comment, so break the loop and look at the next line
					if (i == currentLine.length())
						break;

					// Checks for string literals.
					if (current == '\"') {
						while (currentLine.charAt(i + 1) != '\"' && currentLine.charAt(i) != '\\') {
							i++;
						}
						i = i + 2;
						current = currentLine.charAt(i);
					}

					// Checks for char literals.
					if (current == '\'') {
						while (currentLine.charAt(i + 1) != '\'' && currentLine.charAt(i) != '\\') {
							i++;
						}
						i = i + 2;
						current = currentLine.charAt(i);
					}

					// Checks for comments denoted by //
					if (i != currentLine.length() - 1) {
						if (current == '/' && currentLine.charAt(i + 1) == '/')
							break;
					}

					if (current == '(' || current == '{' || current == '[') {
						stack.push(current);
					} else if (current == ')') {
						if(stack.isEmpty()) {
							return (unmatchedSymbol(line, i + 1, ')', ' '));
						}
						char popCurrent = stack.pop();
						if (popCurrent != '(') {
							if(popCurrent == '[')
								return (unmatchedSymbol(line, i + 1, ')', ']'));
							else
								return (unmatchedSymbol(line, i + 1, ')', '}'));

						}
					} else if (current == '}') {
						if(stack.isEmpty()) {
							return (unmatchedSymbol(line, i + 1, '}', ' '));
						}
						char popCurrent = stack.pop();
						if (popCurrent != '{') {
							if(popCurrent == '[')
								return (unmatchedSymbol(line, i + 1, '}', ']'));
							else
								return (unmatchedSymbol(line, i + 1, '}', ')'));
						}
					} else if (current == ']') {
						if(stack.isEmpty()) {
							return (unmatchedSymbol(line, i + 1, ']', ' '));
						}
						char popCurrent = stack.pop();
						if (popCurrent != '[') {
							if(popCurrent == '(')
								return (unmatchedSymbol(line, i + 1, ']', ')'));
							else
								return (unmatchedSymbol(line, i + 1, ']', '}'));
						}
					}
				}
			}
		}
		sc.close();
		
		if (withinComment)
			return unfinishedComment();

		if (!stack.isEmpty()) {
			if (stack.peek().equals('('))
				return unmatchedSymbolAtEOF(')');
			else if (stack.peek().equals('{'))
				return unmatchedSymbolAtEOF('}');
			else 
				return unmatchedSymbolAtEOF(']');
		}

		return allSymbolsMatch();
	}

	/**
	 * Use this error message in the case of an unmatched symbol.
	 * 
	 * @param lineNumber     - the line number of the input file where the matching
	 *                       symbol was expected
	 * @param colNumber      - the column number of the input file where the
	 *                       matching symbol was expected
	 * @param symbolRead     - the symbol read that did not match
	 * @param symbolExpected - the matching symbol expected
	 * @return the error message
	 */
	private static String unmatchedSymbol(int lineNumber, int colNumber, char symbolRead, char symbolExpected) {
		return "ERROR: Unmatched symbol at line " + lineNumber + " and column " + colNumber + ". Expected "
				+ symbolExpected + ", but read " + symbolRead + " instead.";
	}

	/**
	 * Use this error message in the case of an unmatched symbol at the end of the
	 * file.
	 * 
	 * @param symbolExpected - the matching symbol expected
	 * @return the error message
	 */
	private static String unmatchedSymbolAtEOF(char symbolExpected) {
		return "ERROR: Unmatched symbol at the end of file. Expected " + symbolExpected + ".";
	}

	/**
	 * Use this error message in the case of an unfinished comment (i.e., a file
	 * that ends with an open /* comment).
	 * 
	 * @return the error message
	 */
	private static String unfinishedComment() {
		return "ERROR: File ended before closing comment.";
	}

	/**
	 * Use this message when no unmatched symbol errors are found in the entire
	 * file.
	 * 
	 * @return the success message
	 */
	private static String allSymbolsMatch() {
		return "No errors found. All symbols match.";
	}
}