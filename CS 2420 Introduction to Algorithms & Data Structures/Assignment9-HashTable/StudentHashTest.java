package assign09;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * This class is used to perform simple tests for the StudentBadHash class,
 * StudentBetterHash class, and StudentGoodHash class.
 * 
 * @author Harry Kim && Braden Morfin
 */
class StudentHashTest {

	@Test
	void testBadHash() {
		StudentBadHash student = new StudentBadHash(123, "Braden", "Morfin");
		int hashCode = student.hashCode();
		int expected = 135;
		assertEquals(expected, hashCode);
	}

	@Test
	void testBadHash2() {
		StudentBadHash student = new StudentBadHash(123, "eijser", "Meawnf");
		int hashCode = student.hashCode();
		int expected = 135;
		assertEquals(expected, hashCode);
	}

	@Test
	void testMediumHash() {
		StudentMediumHash student = new StudentMediumHash(123, "abc", "def");
		int hashCode = student.hashCode();
		int expected = 720;
		assertEquals(expected, hashCode);
	}

	@Test
	void testMediumHash2() {
		StudentMediumHash student = new StudentMediumHash(123, "ebd", "acf");
		int hashCode = student.hashCode();
		int expected = 720;
		assertEquals(expected, hashCode);
	}

	@Test
	void testGoodHash() {
		StudentGoodHash student = new StudentGoodHash(123, "ebd", "acf");
		int hashCode = student.hashCode();
		int expected = -1064739862;
		assertEquals(expected, hashCode);
	}

	@Test
	void testGoodHash2() {
		StudentGoodHash student = new StudentGoodHash(123, "abc", "def");
		int hashCode = student.hashCode();
		int expected = -1645645870;
		assertEquals(expected, hashCode);
	}
}
