package assign09;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
/**
 * This class is used to test HashTable
 * 
 * @author Harry Kim && Braden Morfin
 */

class HashTableTest {

	@Test
	void testContainsKeyTrue() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		assertTrue(table.containsKey(801));
	}
	
	@Test
	void testContainsAllKeysTrue() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		assertTrue(table.containsKey(801));
		assertTrue(table.containsKey(802));
		assertTrue(table.containsKey(803));
		assertTrue(table.containsKey(804));
		assertTrue(table.containsKey(805));
	}
	
	@Test
	void testContainsKeyFalse() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		assertFalse(table.containsKey(800));
	}
	
	@Test
	void testContainsValueTrue() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		assertTrue(table.containsValue("abc"));
	}
	
	@Test
	void testContainsAllValuesTrue() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		assertTrue(table.containsValue("abc"));
		assertTrue(table.containsValue("def"));
		assertTrue(table.containsValue("hij"));
		assertTrue(table.containsValue("klm"));
		assertTrue(table.containsValue("nop"));
	}
	
	@Test
	void testContainsValueFalse() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		assertFalse(table.containsValue("a"));
	}
	
	@Test
	void testEntries() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		List<MapEntry<Integer, String>> actual = table.entries();
		List<MapEntry<Integer, String>> expected = new ArrayList<MapEntry<Integer, String>>();
		MapEntry<Integer, String> a1 = new MapEntry<Integer, String>(801, "abc");
		MapEntry<Integer, String> a2 = new MapEntry<Integer, String>(802, "def");
		MapEntry<Integer, String> a3 = new MapEntry<Integer, String>(803, "hij");
		MapEntry<Integer, String> a4 = new MapEntry<Integer, String>(804, "klm");
		MapEntry<Integer, String> a5 = new MapEntry<Integer, String>(805, "nop");
		expected.add(a1);
		expected.add(a2);
		expected.add(a3);
		expected.add(a4);
		expected.add(a5);
		assertEquals(actual.get(0), expected.get(0));
		assertEquals(actual.get(1), expected.get(1));
		assertEquals(actual.get(2), expected.get(2));
		assertEquals(actual.get(3), expected.get(3));
		assertEquals(actual.get(4), expected.get(4));
	}
	
	@Test
	void testEntriesEmpty() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		List<MapEntry<Integer, String>> actual = table.entries();
		List<MapEntry<Integer, String>> expected = new ArrayList<MapEntry<Integer, String>>();
		assertTrue(table.isEmpty());
		assertEquals(actual, expected);
	}
	
	@Test
	void testGetKeyExists() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		assertEquals(table.get(801), "abc");
	}
	
	@Test
	void testGetKeyDoesNotExist() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		assertEquals(table.get(800), null);
	}
	
	@Test
	void testIsEmptyFalse() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		assertFalse(table.isEmpty());
	}
	
	@Test
	void testIsEmptyTrue() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		assertTrue(table.isEmpty());
	}
	
	@Test
	void testSize() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		assertEquals(table.size(), 5);
	}
	
	@Test
	void testSizeZero() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		assertEquals(table.size(), 0);
	}
	
	@Test
	void testRemove() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		assertEquals("abc" ,table.remove(801));
		assertFalse(table.containsKey(801));
		assertFalse(table.containsValue("abc"));
		assertEquals(table.size(), 4);
	}
	
	@Test
	void testRemoveNothing() {
		HashTable<Integer, String> table = new HashTable<Integer, String>();
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		assertEquals(null ,table.remove(800));
	}
	
	@Test
	void testPutRehash() {
		HashTable<Integer, String> table = new HashTable<Integer, String>(2);
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		table.put(901, "a");
		table.put(902, "d");
		table.put(903, "h");
		table.put(904, "k");
		table.put(905, "n");
		assertEquals(table.size(), 10);
		assertTrue(table.containsKey(801));
		assertTrue(table.containsKey(802));
		assertTrue(table.containsKey(803));
		assertTrue(table.containsKey(804));
		assertTrue(table.containsKey(805));
		assertTrue(table.containsKey(901));
		assertTrue(table.containsKey(902));
		assertTrue(table.containsKey(903));
		assertTrue(table.containsKey(904));
		assertTrue(table.containsKey(905));
		
		//uses a private method used only for testing
		//assertEquals(2, table.tableSize());
		
		table.put(101, "t");
		assertEquals(table.size(), 11);
		assertTrue(table.containsKey(801));
		assertTrue(table.containsKey(802));
		assertTrue(table.containsKey(803));
		assertTrue(table.containsKey(804));
		assertTrue(table.containsKey(805));
		assertTrue(table.containsKey(901));
		assertTrue(table.containsKey(902));
		assertTrue(table.containsKey(903));
		assertTrue(table.containsKey(904));
		assertTrue(table.containsKey(905));
		assertTrue(table.containsKey(101));
		
		//uses a private method used only for testing
		//assertEquals(4, table.tableSize());
	}
	
	@Test
	void testPut() {
		HashTable<Integer, String> table = new HashTable<Integer, String>(2);
		assertNull(table.put(801, "abc"));
		assertNull(table.put(802, "def"));
		assertNull(table.put(803, "hij"));
		assertNull(table.put(804, "klm"));
	}
	
	@Test
	void testPutDuplicates() {
		HashTable<Integer, String> table = new HashTable<Integer, String>(2);
		assertNull(table.put(801, "abc"));
		assertNull(table.put(802, "def"));
		assertNull(table.put(803, "hij"));
		assertNull(table.put(804, "klm"));
		assertTrue(table.containsValue("abc"));
		assertEquals("abc", table.put(801, "new"));
		assertFalse(table.containsValue("abc"));
		assertTrue(table.containsValue("new"));
	}
	
	@Test
	void testClear() {
		HashTable<Integer, String> table = new HashTable<Integer, String>(2);
		table.put(801, "abc");
		table.put(802, "def");
		table.put(803, "hij");
		table.put(804, "klm");
		table.put(805, "nop");
		assertTrue(table.containsKey(801));
		assertTrue(table.containsKey(802));
		assertTrue(table.containsKey(803));
		assertTrue(table.containsKey(804));
		assertTrue(table.containsKey(805));
		
		
		assertEquals(5, table.size());
		table.clear();
		assertEquals(0, table.size());
		assertTrue(table.isEmpty());
		
		assertFalse(table.containsKey(801));
		assertFalse(table.containsKey(802));
		assertFalse(table.containsKey(803));
		assertFalse(table.containsKey(804));
		assertFalse(table.containsKey(805));
		
	}
	
	@Test
	void testCollisions() {
		HashTable<StudentBadHash, String> table = new HashTable<StudentBadHash, String>(10);
		StudentBadHash student1 = new StudentBadHash(123, "def", "abc");
		StudentBadHash student2 = new StudentBadHash(123, "drg", "abc");
		StudentBadHash student3 = new StudentBadHash(123, "det", "abc");
		StudentBadHash student4 = new StudentBadHash(123, "defd", "aw");
		assertNull(table.put(student1, "abc"));
		assertNull(table.put(student2, "def"));
		assertNull(table.put(student3, "hij"));
		assertNull(table.put(student4, "klm"));
		assertEquals(6, table.collisions());
		
	}
	
}
