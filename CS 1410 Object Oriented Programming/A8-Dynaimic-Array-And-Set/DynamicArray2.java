package a8;

/**
 * A more efficient version of the DynamicArray class. In other words, the first
 * virtualArrayLength elements of data are elements of the dynamic array. The
 * remainder of data is room to grow. When data fills up, we allocate a new
 * array that is twice as long.
 * 
 * @author harrykim
 *
 */

public class DynamicArray2 {

	private String[] data; // the backing array
	private int virtualArrayLength; // the number of elements in the dynamic array

	/**
	 * Creates an empty dynamic array with room to grow. (DO NOT modify this
	 * method.)
	 */
	public DynamicArray2() {
		// Start with a few available spaces. Do not change this.
		data = new String[8];
		// But the virtual array is empty.
		virtualArrayLength = 0;
	}

	/**
	 * Returns the number of elements in the dynamic array.
	 * 
	 * @return the number of elements
	 */
	public int size() {
		return virtualArrayLength;
	}

	/**
	 * Appends s to the end of the dynamic array at index this.size().
	 * 
	 * @param s String input
	 */
	public void add(String s) {
		add(virtualArrayLength, s);
	}

	/**
	 * Inserts String s at index i and moves other objects over to make room for the
	 * inputed String. Also doubles array size if the array length is exceeded from
	 * the new input. Elements can be added from index 0 to this.size().
	 * 
	 * @param i index number input
	 * @param s String input
	 */
	public void add(int i, String s) {
		// Throws an IndexOutOfBoundsException if i is not a valid index (below 0 and
		// above virtualArrayLength).
		if (i < 0 || i > virtualArrayLength)
			throw new IndexOutOfBoundsException();
		// Makes a new array double the size if there isn't enough room in the current
		// array for an input.
		if (virtualArrayLength == data.length) {
			String[] newData = new String[data.length * 2];
			for (int j = 0; j < data.length; j++)
				newData[j] = data[j];
			data = newData;
		}
		// Inserts s at index i while moving each element up to make room for s.
		String holder1 = s;
		for (int j = i; j < virtualArrayLength; j++) {
			String holder2 = data[j];
			data[j] = holder1;
			holder1 = holder2;
		}
		data[virtualArrayLength] = holder1;
		virtualArrayLength++;
	}

	/**
	 * Removes object at index i and moves other objects over to fill the empty
	 * spot. Also doubles array size if the array length is exceeded from the new
	 * input. Elements can be removed from index 0 to this.size().
	 * 
	 * @param i index number input
	 */
	public void remove(int i) {
		// Throws an IndexOutOfBoundsException if i is not a valid index
		if (i < 0 || i > virtualArrayLength)
			throw new IndexOutOfBoundsException();
		// Removes object at index i while moving each element down to fill in gap.
		String holder1 = null;
		for (int j = virtualArrayLength - 1; j >= i; j--) {
			String holder2 = data[j];
			data[j] = holder1;
			holder1 = holder2;
		}
		virtualArrayLength--;
	}

	/**
	 * Throws an IndexOutOfBoundsException if i is not a valid index or null of the
	 * dynamic array, otherwise returns the element at index i.
	 * 
	 * @param i index number input
	 * @return object at data[i]
	 */
	public String get(int i) {
		// i > virtualArrayLength to avoid returning null.
		if (i < 0 || i > virtualArrayLength)
			throw new IndexOutOfBoundsException();
		return data[i];
	}

	/**
	 * Replaces the element at index i with s. Throws an IndexOutOfBoundsException
	 * if i is not a valid index of the dynamic array.
	 * 
	 * @param i index number input
	 * @param s String input
	 */
	public void set(int i, String s) {
		// i > virtualArrayLength to avoid setting objects past virtualArrayLength.
		if (i < 0 || i > virtualArrayLength)
			throw new IndexOutOfBoundsException();
		data[i] = s;
	}

	/**
	 * Returns a formatted string version of this dynamic array.
	 * 
	 * @return the formatted string
	 */
	public String toString() {
		String result = "[";
		if (size() > 0)
			result += get(0);

		for (int i = 1; i < size(); i++)
			result += ", " + get(i);

		return result + "] backing size: " + data.length;
	}

	/**
	 * Just some tests to ensure that DynamicArray2 is working properly.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DynamicArray2 d = new DynamicArray2();
		System.out.println(d.toString());

		// Add tests
		d.add("a");
		d.add("b");
		d.add("c");
		d.add("d");
		d.add(4, "e");
		d.add(5, "f");
		d.add(6, "g");
		d.add("h");
		System.out.println(d.toString());
		d.add("i");
		System.out.println(d.toString());
		d.add(4, "m");
		System.out.println(d.toString());

		// Remove tests
		d.remove(0);
		System.out.println(d.toString());
		d.remove(3);
		System.out.println(d.toString());
		d.remove(7);
		System.out.println(d.toString());

		// Get tests
		System.out.println(d.get(0));
		System.out.println(d.get(1));

		// Set tests
		d.set(4, "w");
		System.out.println(d.toString());
		// System.out.println(d.get(13));
		// d.set(13, "w");
		System.out.println(d.toString());

		// Size tests
		System.out.println(d.size());
	}

}
