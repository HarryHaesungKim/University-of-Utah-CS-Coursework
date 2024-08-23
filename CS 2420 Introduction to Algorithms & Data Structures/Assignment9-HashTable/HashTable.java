package assign09;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This Hashtable class is a hash function that makes use of separate chaining
 * to resolve collisions.
 * 
 * @author Harry Kim & Braden Morfin
 *
 * @param <K> - placeholder for key type
 * @param <V> - placeholder for values type
 */
public class HashTable<K, V> implements Map<K, V> {

	private ArrayList<LinkedList<MapEntry<K, V>>> table;

	// private double loadFactor;

	private int numOfItems;

	private int numCollisions;

	/**
	 * the HashTable constructor that gives users the option to choose the capacity
	 * size of the Hashtable Arraylist.
	 * 
	 * @param - capacity the capacity of the Hashtable Arraylist.
	 */
	public HashTable(int capacity) {
		numOfItems = 0;
		table = new ArrayList<LinkedList<MapEntry<K, V>>>();
		for (int i = 0; i < capacity; i++)
			table.add(new LinkedList<MapEntry<K, V>>());
		numCollisions = 0;
	}

	/**
	 * the HashTable constructor that defaults the capacity size of the Hashtable
	 * Arraylist to 20.
	 */
	public HashTable() {
		numOfItems = 0;
		table = new ArrayList<LinkedList<MapEntry<K, V>>>();
		for (int i = 0; i < 20; i++)
			table.add(new LinkedList<MapEntry<K, V>>());
		numCollisions = 0;
	}

	/**
	 * Removes all mappings from this map.
	 * 
	 */
	@Override
	public void clear() {
		numOfItems = 0;
		for (int i = 0; i < table.size(); i++)
			table.set(i, new LinkedList<MapEntry<K, V>>());
	}

	/**
	 * Determines whether this map contains the specified key.
	 * 
	 * @param key
	 * @return true if this map contains the key, false otherwise
	 */
	@Override
	public boolean containsKey(K key) {
		int currentPos = Math.abs(key.hashCode()) % table.size();
		for (int i = 0; i < table.get(currentPos).size(); i++) {
			if (table.get(currentPos).get(i).getKey().equals(key))
				return true;
			// used only for collecting stats and timing
			numCollisions++;
		}
		return false;
	}

	/**
	 * Determines whether this map contains the specified value.
	 * 
	 * @param value
	 * @return true if this map contains one or more keys to the specified value,
	 *         false otherwise
	 */
	@Override
	public boolean containsValue(V value) {
		for (int i = 0; i < table.size(); i++) {
			for (int j = 0; j < table.get(i).size(); j++) {
				if (table.get(i).get(j).getValue().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns a List view of the mappings contained in this map, where the ordering
	 * of mapping in the list is insignificant.
	 * 
	 * @return a List object containing all mapping (i.e., entries) in this map
	 */
	@Override
	public List<MapEntry<K, V>> entries() {
		List<MapEntry<K, V>> list = new ArrayList<MapEntry<K, V>>();
		for (int i = 0; i < table.size(); i++) {
			for (int j = 0; j < table.get(i).size(); j++) {
				list.add(table.get(i).get(j));
			}
		}
		return list;
	}

	/**
	 * Gets the value to which the specified key is mapped.
	 * 
	 * @param key
	 * @return the value to which the specified key is mapped, or null if this map
	 *         contains no mapping for the key
	 */
	@Override
	public V get(K key) {
		int currentPos = Math.abs(key.hashCode()) % table.size();
		for (int i = 0; i < table.get(currentPos).size(); i++) {
			if (table.get(currentPos).get(i).getKey().equals(key))
				return table.get(currentPos).get(i).getValue();
			numCollisions++;
		}
		return null;
	}

	/**
	 * Determines whether this map contains any mappings.
	 * 
	 * @return true if this map contains no mappings, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return numOfItems == 0;
	}

	/**
	 * Associates the specified value with the specified key in this map. (I.e., if
	 * the key already exists in this map, resets the value; otherwise adds the
	 * specified key-value pair.)
	 * 
	 * @param key
	 * @param value
	 * @return the previous value associated with key, or null if there was no
	 *         mapping for key
	 */
	@Override
	public V put(K key, V value) {

		// Rehashing
		if (numOfItems / table.size() >= 5) {
			ArrayList<LinkedList<MapEntry<K, V>>> temp = new ArrayList<LinkedList<MapEntry<K, V>>>();
			for (int i = 0; i < table.size() * 2; i++)
				temp.add(new LinkedList<MapEntry<K, V>>());
			for (LinkedList<MapEntry<K, V>> currentList : table) {
				for (MapEntry<K, V> currentEntry : currentList) {

					int currentPos = Math.abs(currentEntry.getKey().hashCode()) % temp.size();

					MapEntry<K, V> newMapEntry = new MapEntry<K, V>(currentEntry.getKey(), currentEntry.getValue());
					temp.get(currentPos).add(newMapEntry);
				}
			}
			table = temp;
		}

		int currentPos = Math.abs(key.hashCode()) % table.size();
		for (int i = 0; i < table.get(currentPos).size(); i++) {
			if (table.get(currentPos).get(i).getKey().equals(key)) {
				V temp = table.get(currentPos).get(i).getValue();
				table.get(currentPos).get(i).setValue(value);
				return temp;
			}
			numCollisions++;
		}
		MapEntry<K, V> newMapEntry = new MapEntry<K, V>(key, value);
		table.get(currentPos).add(newMapEntry);
		numOfItems++;
		return null;
	}

	/**
	 * Removes the mapping for a key from this map if it is present.
	 *
	 * @param key The key to be removed.
	 * @return the previous value associated with key, or null if there was no
	 *         mapping for key
	 */
	@Override
	public V remove(K key) {
		int currentPos = Math.abs(key.hashCode()) % table.size();
		for (int i = 0; i < table.get(currentPos).size(); i++) {
			if (table.get(currentPos).get(i).getKey().equals(key)) {
				V temp = table.get(currentPos).get(i).getValue();
				table.get(currentPos).remove(i);
				numOfItems--;
				return temp;
			}
			numCollisions++;
		}
		return null;
	}

	/**
	 * Determines the number of mappings in this map.
	 * 
	 * @return the number of mappings in this map
	 */
	@Override
	public int size() {
		return numOfItems;
	}

	/**
	 * This method returns the size of the backing array. This method is only used
	 * for testing. When testing the method is public, otherwise private.
	 * 
	 * @return the size of the backing array
	 */
	private int tableSize() {
		return table.size();

	}

	/**
	 * This method is used to collect the number of collisions experienced from
	 * running methods from this HashTable class.
	 * 
	 * @return the number of collisions incurred (so far)
	 */
	public int collisions() {
		return numCollisions;
	}

	/**
	 * Resets the number-of-collisions statistic to 0.
	 */
	public void resetCollisions() {
		numCollisions = 0;
	}
}
