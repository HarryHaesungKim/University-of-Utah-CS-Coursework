package a8;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class StringSetTest {

	@Test(timeout = 1000)
	public void saStringSet_toStringEmptyArray() {
		StringSet ss = new StringSet();
		assertEquals("{}", ss.toString());
	}

	@Test(timeout = 1000)
	public void sbStringSet_toStringCommonCase() {
		StringSet ss = new StringSet();
		ss.insert("hello");
		ss.insert("goodbye");
		ss.insert("why");
		ss.insert("TA");
		String[] elems = { "hello", "goodbye", "why", "TA" };
		for (int i = 0; i < elems.length; i++)
			assertTrue(ss.contains(elems[i]));
	}

	@Test(timeout = 1000)
	public void scStringSet_SizeEmptySet() {
		StringSet ss = new StringSet();
		assertEquals(0, ss.size());
	}

	@Test(timeout = 1000)
	public void sdStringSet_SizeCommonCase() {
		StringSet ss = new StringSet();
		ss.insert("hello");
		ss.insert("goodbye");
		ss.insert("why");
		ss.insert("TA");
		assertEquals(4, ss.size());
	}

	@Test(timeout = 1000)
	public void seStringSet_ContainsFalse() {
		StringSet ss = new StringSet();
		ss.insert("hello");
		ss.insert("goodbye");
		ss.insert("why");
		ss.insert("TA");
		assertFalse(ss.contains("ashlee"));
	}

	@Test(timeout = 1000)
	public void sfStringSet_ContainsTrue() {
		StringSet ss = new StringSet();
		ss.insert("hello");
		ss.insert("goodbye");
		ss.insert("why");
		ss.insert("TA");
		assertTrue("Contains failed, check for == usage", ss.contains("TA"));
	}

	@Test(timeout = 1000)
	public void skStringSet_RemoveAValidValue() {
		StringSet ss = new StringSet();
		for (int i = 0; i < 5; i++) {
			ss.insert("A" + 2);
			assertEquals(1, ss.size());
		}
		ss.remove("A2");
		assertEquals(0, ss.size());
		assertEquals("{}", ss.toString());
	}

	@Test(timeout = 1000)
	public void skStringSet_RemoveNonexistentValue() {
		StringSet ss = new StringSet();
		ss.insert("A" + 2);
		ss.remove("Penguin");
		assertEquals(1, ss.size());
		assertEquals("{A2}", ss.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void skStringSet_RemoveAnInvalidNullValue() {
		StringSet ss = new StringSet();

		for (int i = 0; i < 5; i++) {
			ss.insert("A" + 2);
			assertEquals(1, ss.size());
		}
		ss.remove(null);
	}
	
	//union
	@Test(timeout = 1000)
	public void smStringSet_UnionWithAllDifferentValues() {
		StringSet aValues = new StringSet();

		for (int i = 0; i < 3; i++) {
			aValues.insert("A" + i);
		}

		StringSet bValues = new StringSet();

		for (int i = 0; i < 3; i++) {
			bValues.insert("B" + i);
		}

		StringSet _union = aValues.union(bValues);

		assertEquals(6, _union.size());
		String[] elems = { "A0", "A1", "A2", "B0", "B1", "B2" };
		for (int i = 0; i < elems.length; i++)
			assertTrue(_union.contains(elems[i]));

		// The ordering is not guaranteed, so this might not work.
		assertEquals("{A0, A1, A2, B0, B1, B2}", _union.toString());
	}
	
	@Test(timeout = 1000)
	public void smStringSet_UnionWithSomeSimilarValues() {
		StringSet aValues = new StringSet();

		for (int i = 0; i < 3; i++) {
			aValues.insert("A" + i);
		}

		StringSet a2Values = new StringSet();

		for (int i = 1; i < 6; i++) {
			a2Values.insert("A" + i);
		}

		StringSet _union = aValues.union(a2Values);

		assertEquals(6, _union.size());
		String[] elems = { "A0", "A1", "A2", "A3", "A4", "A5" };
		for (int i = 0; i < elems.length; i++)
			assertTrue(_union.contains(elems[i]));

		// The ordering is not guaranteed, so this might not work.
		assertEquals("{A0, A1, A2, A3, A4, A5}", _union.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void sqStringSet_UnionWithNullInput() {
		StringSet first = new StringSet();

		StringSet _union = first.union(null);
	}
	
	
	//intersection
	@Test(timeout = 1000)
	public void smStringSet_IntersectionWithAllDifferentValues() {
		StringSet aValues = new StringSet();

		for (int i = 0; i < 3; i++) {
			aValues.insert("A" + i);
		}

		StringSet bValues = new StringSet();

		for (int i = 0; i < 3; i++) {
			bValues.insert("B" + i);
		}

		StringSet _intersection = aValues.intersection(bValues);

		assertEquals(0, _intersection.size());
		String[] elems = {};
		for (int i = 0; i < elems.length; i++)
			assertTrue(_intersection.contains(elems[i]));

		// The ordering is not guaranteed, so this might not work.
		assertEquals("{}", _intersection.toString());
	}
	
	@Test(timeout = 1000)
	public void smStringSet_IntersectionWithSomeSimilarValues() {
		StringSet aValues = new StringSet();

		for (int i = 0; i < 3; i++) {
			aValues.insert("A" + i);
		}

		StringSet a2Values = new StringSet();

		for (int i = 1; i < 6; i++) {
			a2Values.insert("A" + i);
		}

		StringSet _intersection = aValues.intersection(a2Values);

		assertEquals(2, _intersection.size());
		String[] elems = {"A1", "A2" };
		for (int i = 0; i < elems.length; i++)
			assertTrue(_intersection.contains(elems[i]));

		// The ordering is not guaranteed, so this might not work.
		assertEquals("{A1, A2}", _intersection.toString());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void sqStringSet_IntersectionWithNullInput() {
		StringSet first = new StringSet();

		StringSet _intersection = first.intersection(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void suStringSet_InsertWithNullInput() {
		StringSet ss = new StringSet();

		for (int i = 0; i < 15; i++) {
			ss.insert("A" + 2);
			assertEquals(1, ss.size());
		}

		ss.insert(null);
	}

}
