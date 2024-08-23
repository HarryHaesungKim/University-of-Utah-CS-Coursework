package assign02;

import java.util.GregorianCalendar;

/**
 * This class represents a library book but a generic type.
 * 
 * @author Harry Kim and Braden Morfin
 *
 */

public class LibraryBookGeneric<Type> extends Book {
	
	private Type holder = null;
	
	private GregorianCalendar dueDate = null;

	public LibraryBookGeneric(long isbn, String author, String title) {
		super(isbn, author, title);
		// TODO Auto-generated constructor stub
	}
	
	public Type getHolder() {
		return holder;
	}
	
	public GregorianCalendar getDueDate() {
		return dueDate;
	}
	
	public void checkIn() {
		holder = null;
		dueDate = null;
	}
	
	public void checkOut(Type holder, GregorianCalendar dueDate) {
		this.holder = holder;
		this.dueDate = dueDate;
	}
	
}
