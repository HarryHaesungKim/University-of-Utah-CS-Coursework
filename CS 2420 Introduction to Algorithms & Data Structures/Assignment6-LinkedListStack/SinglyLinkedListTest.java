package assign06;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class SinglyLinkedListTest {

	@Test
	void testAddFirst() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.addFirst(1);
		assertEquals(1, list.getFirst());

		list.addFirst(2);
		assertEquals(2, list.getFirst());

		list.addFirst(3);
		assertEquals(3, list.getFirst());

		list.addFirst(4);
		assertEquals(4, list.getFirst());

		assertEquals(4, list.size());
	}

	@Test
	void testAdd() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(0, 1);
		list.add(1, 2);
		list.add(2, 3);
		list.add(3, 4);

		assertEquals(4, list.get(3));
		assertEquals(3, list.get(2));
		assertEquals(2, list.get(1));
		assertEquals(1, list.get(0));

		assertEquals(4, list.size());
	}

	@Test
	void testAddMiddle() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(0, 1);
		list.add(1, 2);
		list.add(2, 3);
		list.add(3, 4);

		list.add(2, 7);

		assertEquals(7, list.get(2));
		assertEquals(3, list.get(3));
		assertEquals(4, list.get(4));
	}

	@Test
	void testAddExceptionNegative() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();

		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.add(-4, 1);
		});
	}

	@Test
	void testAddExceptionToBig() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(0, 1);

		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.add(2, 4);
		});
	}

	@Test
	void testGetFirstException() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();

		assertThrows(NoSuchElementException.class, () -> {
			list.getFirst();
		});
	}

	@Test
	void testGetExceptionNegative() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(0, 1);

		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.get(-4);
		});
	}

	@Test
	void testGetNothing() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();

		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.get(0);
		});
	}

	@Test
	void testGetToBig() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.addFirst(1);

		// System.out.print(list.get(1));
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.get(1);
		});
	}

	// Remove

	@Test
	void testRemoveFirst() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.addFirst(1);
		assertEquals(1, list.getFirst());

		list.addFirst(2);
		assertEquals(2, list.getFirst());

		list.addFirst(3);
		assertEquals(3, list.getFirst());

		list.removeFirst();
		assertEquals(2, list.getFirst());

		assertEquals(2, list.size());
	}

	@Test
	void testRemoveFirstExceptionNothing() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		assertThrows(NoSuchElementException.class, () -> {
			list.removeFirst();
		});
	}

	@Test
	void testRemove() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(0, 1);
		list.add(1, 2);
		list.add(2, 3);
		list.add(3, 4);

		assertEquals(3, list.remove(2));
		assertEquals(4, list.get(2));
		assertEquals(3, list.size());
	}

	@Test
	void testRemoveExceptionNegative() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();

		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(-4);
		});
	}

	@Test
	void testRemoveExceptionToBig() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(0, 1);

		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(4);
		});
	}

	@Test
	void testRemoveExceptionNothing() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(0);
		});
	}

	@Test
	void testIndexOf() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(0, 1);
		list.add(1, 2);
		list.add(2, 3);
		list.add(3, 4);

		assertEquals(2, list.indexOf(3));
		assertEquals(0, list.indexOf(1));
		assertEquals(3, list.indexOf(4));
	}

	@Test
	void testIndexOfNumberDoesNotExist() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(0, 1);
		list.add(1, 2);
		list.add(2, 3);
		list.add(3, 4);

		assertEquals(-1, list.indexOf(5));
	}

	@Test
	void testIsEmptyTrue() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		assertTrue(list.isEmpty());
	}

	@Test
	void testIsEmptyFalse() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.addFirst(2);
		assertFalse(list.isEmpty());
	}

	@Test
	void testClear() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(0, 1);
		list.add(1, 2);
		list.add(2, 3);
		list.add(3, 4);

		list.clear();

		assertEquals(0, list.size());
		assertThrows(NoSuchElementException.class, () -> {
			list.getFirst();
		});
	}

	@Test
	void testToArray() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(0, 1);
		list.add(1, 2);
		list.add(2, 3);
		list.add(3, 4);

		Object[] listArray = list.toArray();

		assertEquals(listArray[0], list.get(0));
		assertEquals(listArray[1], list.get(1));
		assertEquals(listArray[2], list.get(2));
		assertEquals(listArray[3], list.get(3));
	}

	@Test
	void testToArrayTypeCharacter() {
		SinglyLinkedList<Character> list = new SinglyLinkedList<Character>();
		list.add(0, 'a');
		list.add(1, 'b');
		list.add(2, 'c');
		list.add(3, 'd');

		Object[] listArray = list.toArray();


		assertEquals(listArray[0], list.get(0));
		assertEquals(listArray[1], list.get(1));
		assertEquals(listArray[2], list.get(2));
		assertEquals(listArray[3], list.get(3));
	}
	
	 @Test
	 void testIteratorHasNext()
	 {
		 SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		 list.addFirst(3);
		 Iterator<Integer> iterator = list.iterator();
		 
		 assertTrue(iterator.hasNext());
		 
	 }
	 
	 @Test
	 void testIteratorNext()
	 {
		 SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		 list.addFirst(3);
		 Iterator<Integer> iterator = list.iterator();
		 
		 assertEquals(3 ,iterator.next());
		 
	 }
	 
	 @Test
	 void testIteratorNextMultiple()
	 {
		 SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		 list.addFirst(3);
		 list.addFirst(2);
		 list.addFirst(1);
		 Iterator<Integer> iterator = list.iterator();
		 
		 assertEquals(1 ,iterator.next());
		 assertEquals(2 ,iterator.next());
		 assertEquals(3 ,iterator.next());
		 
	 }
	 
	 @Test
	 void testIteratorRemove()
	 {
		 SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		 list.addFirst(3);
		 Iterator<Integer> iterator = list.iterator();
		 iterator.next();
		 iterator.remove();
		 
		 assertEquals(0, list.size());
		 
	 }
	 
	 @Test
	 void testIteratorRemoveMiddle()
	 {
		 SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		 list.addFirst(5);
		 list.addFirst(4);
		 list.addFirst(3);
		 list.addFirst(2);
		 list.addFirst(1);
		 Iterator<Integer> iterator = list.iterator();
		 iterator.next();
		 iterator.next();
		 iterator.next();
	     iterator.remove();
		 assertEquals(4, list.size());
		 assertEquals(4, list.get(2));
		 
	 }
	 
	 

}