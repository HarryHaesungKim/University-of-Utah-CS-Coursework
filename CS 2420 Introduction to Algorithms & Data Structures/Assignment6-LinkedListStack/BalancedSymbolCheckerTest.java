package assign06;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import assign04.AnagramChecker;
/**
 * Class used to test BalanceSymbolChecker
 * 
 * @author Harry Kim && Braden Morfin
 * @Version 10/15/2020
 */

class BalancedSymbolCheckerTest {

	@Test
	void testClass1() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Class1.java");
		
		assertEquals("ERROR: Unmatched symbol at line 6 and column 1. Expected ), but read } instead.", classTest);
	}
	
	@Test
	void testClass2() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Class2.java");
		
		assertEquals("ERROR: Unmatched symbol at line 7 and column 1. Expected  , but read } instead.", classTest);
	}
	
	@Test
	void testClass3() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Class3.java");
		
		assertEquals("No errors found. All symbols match.", classTest);
	}
	
	@Test
	void testClass4() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Class4.java");
		
		assertEquals("ERROR: File ended before closing comment.", classTest);
	}
	
	@Test
	void testClass5() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Class5.java");
		
		assertEquals("ERROR: Unmatched symbol at line 3 and column 18. Expected ], but read } instead.", classTest);
	}
	
	@Test
	void testClass6() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Classbc.java");
		
		File fileName = new File("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Classbc.java");
		
		Scanner sc = new Scanner(fileName);
		
		while(sc.hasNextLine()) {
			String currentLine = sc.nextLine();
			System.out.println(currentLine);
		}

		
		assertEquals("No errors found. All symbols match.", classTest);
	}
	
	@Test
	void testClass7() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Class7.java");
		
		assertEquals("ERROR: Unmatched symbol at line 3 and column 33. Expected ], but read ) instead.", classTest);
	}
	
	@Test
	void testClass8() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Class8.java");
		
		assertEquals("ERROR: Unmatched symbol at line 5 and column 30. Expected }, but read ) instead.", classTest);
	}
	
	@Test
	void testClass9() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Class9.java");
		
		assertEquals("ERROR: Unmatched symbol at line 3 and column 33. Expected ), but read ] instead.", classTest);
	}
	
	@Test
	void testClass10() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Class10.java");
		
		assertEquals("ERROR: Unmatched symbol at line 5 and column 10. Expected }, but read ] instead.", classTest);
	}
	
	@Test
	void testClass11() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Class11.java");
		
		assertEquals("ERROR: Unmatched symbol at the end of file. Expected }.", classTest);
	}
	
	@Test
	void testClass12() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Class12.java");
		
		assertEquals("No errors found. All symbols match.", classTest);
	}
	
	@Test
	void testClass13() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Class13.java");
		
		assertEquals("No errors found. All symbols match.", classTest);
	}
	
	@Test
	void testClass14() throws FileNotFoundException {
		
		String classTest = BalancedSymbolChecker.checkFile("/Users/harry/eclipse-workspace/CS2420-Assignments/examples/Class14.java");
		
		assertEquals("No errors found. All symbols match.", classTest);
	}
	
}