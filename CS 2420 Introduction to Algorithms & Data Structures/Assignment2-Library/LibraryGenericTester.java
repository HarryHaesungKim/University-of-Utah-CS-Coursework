package assign02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * This class contains tests for LibraryGeneric.
 * 
 * @author Erin Parker, Harry Kim, and Braden Morfin
 * @version September 2, 2020
 */
public class LibraryGenericTester {

	private LibraryGeneric<String> nameLib; // library that uses names to identify patrons (holders)
	private LibraryGeneric<PhoneNumber> phoneLib; // library that uses phone numbers to identify patrons

	@BeforeEach
	void setUp() throws Exception {
		nameLib = new LibraryGeneric<String>();
		nameLib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		nameLib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		nameLib.add(9780446580342L, "David Baldacci", "Simple Genius");

		phoneLib = new LibraryGeneric<PhoneNumber>();
		phoneLib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		phoneLib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		phoneLib.add(9780446580342L, "David Baldacci", "Simple Genius");
	}

	@Test
	public void testNameLibCheckout() {
		String patron = "Jane Doe";
		assertTrue(nameLib.checkout(9780330351690L, patron, 1, 1, 2008));
		assertTrue(nameLib.checkout(9780374292799L, patron, 1, 1, 2008));

		String patron2 = "Sally Doe";
		assertFalse(nameLib.checkout(9780330351690L, patron2, 1, 1, 2008));
		assertFalse(nameLib.checkout(9780374292799L, patron2, 1, 1, 2008));
	}

	@Test
	public void testNoIsbnLibCheckout() {
		String patron2 = "Sally Doe";
		assertFalse(nameLib.checkout(0000000000000L, patron2, 1, 1, 2008));
	}

	@Test
	public void testNameLibLookup() {
		String patron = "Jane Doe";
		nameLib.checkout(9780330351690L, patron, 1, 1, 2008);
		nameLib.checkout(9780374292799L, patron, 1, 1, 2008);
		ArrayList<LibraryBookGeneric<String>> booksCheckedOut = nameLib.lookup(patron);

		assertNotNull(booksCheckedOut);
		assertEquals(2, booksCheckedOut.size());
		assertTrue(booksCheckedOut.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
		assertTrue(booksCheckedOut.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
		assertEquals(patron, booksCheckedOut.get(0).getHolder());
		assertEquals(patron, booksCheckedOut.get(1).getHolder());
	}

	@Test
	public void testIsbnLibLookup() {
		String patron = "Jane Doe";
		long book = 9780330351690L;
		nameLib.checkout(9780330351690L, patron, 1, 1, 2008);

		assertEquals(patron, nameLib.lookup(book));
	}

	@Test
	public void testNameLibLookupNoBooksCheckedOut() {
		String patron = "Sally Doe";
		ArrayList<LibraryBookGeneric<String>> booksCheckedOut = nameLib.lookup(patron);

		assertEquals(0, booksCheckedOut.size());
	}

	@Test
	public void testNameLibCheckin() {
		String patron = "Jane Doe";
		nameLib.checkout(9780330351690L, patron, 1, 1, 2008);
		nameLib.checkout(9780374292799L, patron, 1, 1, 2008);
		assertTrue(nameLib.checkin(patron));
	}

	@Test
	public void testIsbnLibCheckin() {
		String patron = "Jane Doe";
		long book = 9780330351690L;
		nameLib.checkout(9780330351690L, patron, 1, 1, 2008);
		assertTrue(nameLib.checkin(book));
	}

	@Test
	public void testNameLibCheckinFalse() {
		String patron = "Sally Doe";
		assertFalse(nameLib.checkin(patron));
	}

	@Test
	public void testPhoneLibCheckout() {
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		assertTrue(phoneLib.checkout(9780330351690L, patron, 1, 1, 2008));
		assertTrue(phoneLib.checkout(9780374292799L, patron, 1, 1, 2008));
	}

	@Test
	public void testPhoneLibLookup() {
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		phoneLib.checkout(9780330351690L, patron, 1, 1, 2008);
		phoneLib.checkout(9780374292799L, patron, 1, 1, 2008);
		ArrayList<LibraryBookGeneric<PhoneNumber>> booksCheckedOut = phoneLib.lookup(patron);

		assertNotNull(booksCheckedOut);
		assertEquals(2, booksCheckedOut.size());
		assertTrue(booksCheckedOut.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
		assertTrue(booksCheckedOut.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
		assertEquals(patron, booksCheckedOut.get(0).getHolder());
		assertEquals(patron, booksCheckedOut.get(1).getHolder());
	}

	@Test
	public void testPhoneLibCheckin() {
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		phoneLib.checkout(9780330351690L, patron, 1, 1, 2008);
		phoneLib.checkout(9780374292799L, patron, 1, 1, 2008);
		assertTrue(phoneLib.checkin(patron));
	}

	@Test
	public void testOrderByDueDate() {
		ArrayList<LibraryBookGeneric<String>> overDueList = new ArrayList<LibraryBookGeneric<String>>();
		overDueList.add(new LibraryBookGeneric<String>(9780374292799L, "Thomas L. Friedman", "The World is Flat"));

		nameLib.checkout(9780374292799L, "Sally", 2000, 9, 9);
		nameLib.checkout(9780330351690L, "Joe", 2000, 10, 9);
		nameLib.checkout(9780446580342L, "Steve", 2000, 11, 9);

		assertEquals(overDueList, nameLib.getOverdueList(2000, 10, 9));

	}
	
	@Test
	public void testOrderByDueDateMultiple() {
		ArrayList<LibraryBookGeneric<String>> overDueList = new ArrayList<LibraryBookGeneric<String>>();
		overDueList.add(new LibraryBookGeneric<String>(9780330351690L, "Jon Krakauer", "Into the Wild"));
		overDueList.add(new LibraryBookGeneric<String>(9780446580342L, "David Baldacci", "Simple Genius"));
		overDueList.add(new LibraryBookGeneric<String>(9780374292799L, "Thomas L. Friedman", "The World is Flat"));


		nameLib.checkout(9780374292799L, "Sally", 9, 9, 2000);
		nameLib.checkout(9780330351690L, "Joe", 7, 9, 2000);
		nameLib.checkout(9780446580342L, "Steve", 8, 9, 2000);

		assertEquals(overDueList, nameLib.getOverdueList(10, 9, 2000));

	}
	
	@Test
	public void testOrderByTitle() {
		ArrayList<LibraryBookGeneric<String>> orderedList = new ArrayList<LibraryBookGeneric<String>>();
		orderedList.add(new LibraryBookGeneric<String>(9780330351690L, "Jon Krakauer", "Into the Wild"));
		orderedList.add(new LibraryBookGeneric<String>(9780446580342L, "David Baldacci", "Simple Genius"));
		orderedList.add(new LibraryBookGeneric<String>(9780374292799L, "Thomas L. Friedman", "The World is Flat"));

		assertEquals(orderedList, nameLib.getOrderedByTitle());

	}

}
