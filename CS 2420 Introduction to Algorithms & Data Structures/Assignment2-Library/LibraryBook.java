package assign02;

import java.util.GregorianCalendar;

/**
 * This class represents a library book.
 * 
 * @author Harry Kim and Braden Morfin
 *
 */

public class LibraryBook extends Book {
	
	private String holder = null;
	
	private GregorianCalendar dueDate = null;

	public LibraryBook(long isbn, String author, String title) {
		super(isbn, author, title);
		// TODO Auto-generated constructor stub
	}
	
	public String getHolder() {
		return holder;
	}
	
	public GregorianCalendar getDueDate() {
		return dueDate;
	}
	
	public void checkIn() {
		holder = null;
		dueDate = null;
	}
	
	public void checkOut(String holder, GregorianCalendar dueDate) {
		this.holder = holder;
		this.dueDate = dueDate;
	}
	
}
