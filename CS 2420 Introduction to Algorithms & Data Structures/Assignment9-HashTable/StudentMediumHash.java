package assign09;

import java.text.DecimalFormat;

public class StudentMediumHash {
	private int uid;
	private String firstName;
	private String lastName;

	/**
	 * Creates a new student with the specified uid, firstName, and lastName.
	 * 
	 * @param uid
	 * @param firstName
	 * @param lastName
	 */
	public StudentMediumHash(int uid, String firstName, String lastName) {
		this.uid = uid;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * @return the UID for this student object
	 */
	public int getUid() {
		return this.uid;
	}

	/**
	 * @return the first name for this student object
	 */

	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * @return the last name for this student object
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * @return true if this student and 'other' have the same UID, first name, and
	 *         last name; false otherwise
	 */
	public boolean equals(Object other) {
		// change to StudentMediumHash and StudentGoodHash for two new classes
		if (!(other instanceof StudentMediumHash))
			return false;

		StudentMediumHash rhs = (StudentMediumHash) other;

		return this.uid == rhs.uid && this.firstName.equals(rhs.firstName) && this.lastName.equals(rhs.lastName);
	}

	/**
	 * @return a textual representation of this student
	 */
	public String toString() {
		DecimalFormat formatter = new DecimalFormat("0000000");
		return firstName + " " + lastName + " (u" + formatter.format(uid) + ")";
	}

	/**
	 * This hashCode method for the StudentGoodHash represents a better distribution
	 * of student objects in a hash table
	 * 
	 */
	public int hashCode() {
		int hashCode = 0;

		String uidString = "" + this.uid;

		hashCode = hashCode + uidString.length();
		
		// Adds the ASCII values of each character of each input.
		for (int i = 0; i < this.firstName.length(); i++) {
			hashCode = hashCode + this.firstName.charAt(i);
		}

		for (int i = 0; i < this.lastName.length(); i++) {
			hashCode = hashCode + this.lastName.charAt(i);
		}
		return hashCode;
	}

}
