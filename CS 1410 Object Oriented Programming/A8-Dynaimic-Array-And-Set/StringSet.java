package a8;

//   
/**
 * A StringSet class with implementation from DynamicArray 2. A StringSet is a
 * collection of non-null strings, with no duplicates (i.e., no two elements may
 * be equal).
 * 
 * @author harrykim
 *
 */
public class StringSet extends DynamicArray2 {

	// To hold the set data, use a DynamicArray.

	private DynamicArray2 set;

	/**
	 * Creates an empty StringSet object.
	 */
	public StringSet() {
		set = new DynamicArray2();
	}

	/**
	 * Adds e to the set if there isn't already an element in the set that is equal
	 * to e.
	 * 
	 * @param e String input
	 */
	public void insert(String e) {
		// Throws an IllegalArgumentException if e is null.
		if (e == null)
			throw new IllegalArgumentException();
		for (int i = 0; i < set.size(); i++) {
			// Ends method if there is an element in the set that is equal to e.
			if (e.contentEquals(set.get(i))) {
				return;
			}
		}
		set.add(e);
	}

	/**
	 * Indicates whether the set contains e.
	 * 
	 * @param e String input
	 * @return boolean statement
	 */
	public boolean contains(String e) {
		// Throws an IllegalArgumentException if e is null.
		if (e == null)
			throw new IllegalArgumentException();
		for (int i = 0; i < set.size(); i++) {
			if (e.contentEquals(set.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes e from the set.
	 * 
	 * @param e String input.
	 */
	public void remove(String e) {
		int indexNum = 0;
		// Throws an IllegalArgumentException if e is null, otherwise
		if (e == null)
			throw new IllegalArgumentException();
		for (int i = 0; i < set.size(); i++) {
			if (e.contentEquals(set.get(i))) {
				break;
			} else
				indexNum += 1;
		}
		if(indexNum == set.size())
			return;
		set.remove(indexNum);
	}

	/**
	 * The number of strings in the set.
	 */
	public int size() {
		return set.size();
	}

	/**
	 * Computes and returns the union of the StringSet that calls this method and
	 * the StringSet argument to the method.
	 * 
	 * @param other
	 * @return
	 */
	public StringSet union(StringSet other) {
		// Throws an IllegalArgumentException if other is null.
		if (other == null)
			throw new IllegalArgumentException();
		// Inserts objects of set to unionStringSet.
		StringSet unionStringSet = new StringSet();
		for (int i = 0; i < set.size(); i++) {
			unionStringSet.insert(set.get(i));
		}
		// Inserts objects of set to unionStringSet. Union possible because of insert()
		// method.
		for (int j = 0; j < other.set.size(); j++) {
			unionStringSet.insert(other.set.get(j));
		}
		return unionStringSet;
	}

	/**
	 * Computes and returns the intersection of the StringSet that calls this method
	 * and the StringSet argument to the method.
	 * 
	 * @param other The StringSet argument
	 * @return intersecting elements
	 */
	public StringSet intersection(StringSet other) {
		if (other == null)
			throw new IllegalArgumentException();
		// Inserts objects of set to unionStringSet.
		StringSet intersectionStringSet = new StringSet();
		// Nested for loop to find common elements in each array.
		for (int i = 0; i < set.size(); i++) {
			for (int j = 0; j < other.set.size(); j++) {
				if (this.contains(other.set.get(j))) {
					intersectionStringSet.insert(other.set.get(j));
				}
			}
		}
		return intersectionStringSet;
	}

	/**
	 * Returns a formatted string version of the set with curly braces.
	 * 
	 */
	public String toString() {
		String result = "{";
		if (set.size() > 0)
			result += set.get(0);
		for (int i = 1; i < set.size(); i++) {
			result += ", " + set.get(i);
		}
		return result + "}";
	}

	public static void main(String Args[]) {
		StringSet s = new StringSet();

		System.out.println();
		// Empty set test
		System.out.println(s.toString());

		// Puting objects into the new StringSet s
		s.insert("a");
		s.insert("b");
		s.insert("c");
		s.insert("d");
		System.out.println(s.toString());

		// Already exists test
		s.insert("d");

		// Contains tests
		System.out.println(s.contains("a"));
		System.out.println(s.contains("c"));
		System.out.println(s.contains("d"));

		// Size Test
		System.out.println(s.size());

		// Removing tests
		System.out.println("Removing 'a':");
		s.remove("a");
		System.out.println(s.toString());
		System.out.println("Removing 'c':");
		s.remove("c");
		System.out.println(s.toString());
		System.out.println("Removing 'd':");
		s.remove("d");
		System.out.println(s.toString());
		System.out.println("Removing 'penguin':");
		s.remove("penguin");
		System.out.println(s.toString());
		System.out.println();

		// Union tests
		System.out.println("Union Tests:");
		StringSet testSet1 = new StringSet();
		testSet1.insert("a");
		testSet1.insert("b");
		testSet1.insert("c");
		testSet1.insert("d");
		testSet1.insert("e");
		System.out.println(testSet1.toString());
		StringSet testSet2 = new StringSet();
		testSet2.insert("f");
		testSet2.insert("g");
		testSet2.insert("h");
		testSet2.insert("i");
		System.out.println(testSet2.toString());
		StringSet uniontest1 = testSet1.union(testSet2);
		System.out.println(uniontest1);
		System.out.println();

		// Union null test
		// StringSet unionNulltest = union1.union(null);

		// intersection tests
		System.out.println("Intersection Tests:");
		StringSet intersectiontest1 = testSet1.intersection(testSet2);
		System.out.println(intersectiontest1);
		System.out.println(intersectiontest1.size());
		
		
	}

}