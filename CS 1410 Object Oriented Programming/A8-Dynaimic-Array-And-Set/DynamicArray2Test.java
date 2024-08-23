package a8;

import static org.junit.Assert.*;
import org.junit.Test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class DynamicArray2Test {
	@Test(timeout = 1000)
	public void daDynamicArray2_Constructor() {
		DynamicArray2 da2 = new DynamicArray2();
		String expected = "[] backing size: 8";
		String result = da2.toString();
		assertEquals(expected, result);
		assertEquals(0, da2.size());
	}

	@Test(timeout = 1000)
	public void dbDynamicArray2_toStringCommonCase() {
		DynamicArray2 da2 = new DynamicArray2();
		String expected = "[9, 0, cats, dogs] backing size: 8";
		da2.add("9");
		da2.add("0");
		da2.add("cats");
		da2.add("dogs");
		String result = da2.toString();
		assertEquals(expected, result);
	}

	@Test(timeout = 1000)
	public void dcDynamicArray2_GetValidValue() {
		DynamicArray2 da2 = new DynamicArray2();
		da2.add("9");
		da2.add("0");
		da2.add("cats");
		da2.add("dogs");
		String expected = "dogs";
		assertEquals(expected, da2.get(3));
		assertEquals(4, da2.size());
	}

	@Test(timeout = 1000)
	public void dc1DynamicArray2_GetValidValue() {
		DynamicArray2 da2 = new DynamicArray2();
		da2.add("9");
		da2.add("0");
		da2.add("cats");
		da2.add("dogs");
		String expected = "9";
		assertEquals(expected, da2.get(0));
		assertEquals(4, da2.size());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void dc1DynamicArray2_GetInValidValue() {
		DynamicArray2 da2 = new DynamicArray2();
		da2.add("9");
		da2.add("0");
		da2.add("cats");
		da2.add("dogs");
		da2.get(-43342);
	}

	@Test(timeout = 1000)
	public void ddDynamicArray2_SetValidValue() {
		DynamicArray2 da2 = new DynamicArray2();
		da2.add("9");
		da2.add("0");
		da2.add("cats");
		da2.add("dogs");
		da2.set(2, "apple");
		String expected = "apple";
		assertEquals(expected, da2.get(2));
		assertEquals(4, da2.size());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void dc1DynamicArray2_SetInValidValue() {
		DynamicArray2 da2 = new DynamicArray2();
		da2.add("9");
		da2.add("0");
		da2.add("cats");
		da2.add("dogs");
		da2.set(43342, "sheeps");
	}

	@Test(timeout = 1000)
	public void deDynamicArray2_SimpleRemove() {
		DynamicArray2 da2 = new DynamicArray2();
		for (int i = 0; i < 2; i++) {
			da2.add("A" + i);
		}

		da2.remove(0);
		String expected = "[A1] backing size: 8";
		String result = da2.toString();

		assertEquals("Removed error: was" + result, expected, result);
		assertEquals("Ending size error", 1, da2.size());
	}
	
	@Test(timeout = 1000)
	public void deDynamicArray2_EndRemove() {
		DynamicArray2 da2 = new DynamicArray2();
		for (int i = 0; i < 2; i++) {
			da2.add("A" + i);
		}

		da2.remove(da2.size());
		String expected = "[A0] backing size: 8";
		String result = da2.toString();

		assertEquals("Removed error: was" + result, expected, result);
		assertEquals("Ending size error", 1, da2.size());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void deDynamicArray2_RemoveIndexBelowZero() {
		DynamicArray2 da2 = new DynamicArray2();
		da2.add("ello");
		da2.remove(-1);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void deDynamicArray2_RemoveIndexAboveVirtualArrayLength() {
		DynamicArray2 da2 = new DynamicArray2();
		da2.add("ello");
		da2.remove(4);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void dlDynamicArray2_AddIndexBelowZero() {
		DynamicArray2 da2 = new DynamicArray2();
		da2.add("ello");
		da2.add(-4, "what");
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void dmDynamicArray2_AddIndexAboveVirtualArrayLength() {
		DynamicArray2 da2 = new DynamicArray2();
		da2.add("ello");
		da2.add(3, "what");
	}

	@Test(timeout = 1000)
	public void diDynamicArray2_AddToLastSpotInData() {
		DynamicArray2 da2 = new DynamicArray2();

		for (int i = 0; i < 7; i++) {
			da2.add("A" + i);
		}

		da2.add(0, "C");

		assertEquals(8, da2.size());
		assertEquals("[C, A0, A1, A2, A3, A4, A5, A6] backing size: 8", da2.toString());
	}
	
	@Test(timeout = 1000)
	public void djDynamicArray2_BackingSize() {
		DynamicArray2 da2 = new DynamicArray2();
		for (int i = 0; i < 11; i++) {
			da2.add("A" + i);
		}
		String expected = "[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10] backing size: 16";
		String result = da2.toString();
		assertEquals(expected, result);
		assertEquals(11, da2.size());
	}

}
