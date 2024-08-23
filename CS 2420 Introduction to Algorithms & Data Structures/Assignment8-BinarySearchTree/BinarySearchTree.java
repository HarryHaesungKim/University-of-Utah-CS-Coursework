package assign08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class BinarySearchTree<Type extends Comparable<? super Type>> implements SortedSet<Type> {

	/**
	 * Represents a (generic) node in a binary tree.
	 */
	private class BinaryNode<T> {

		public T element;

		public BinaryNode<T> left;

		public BinaryNode<T> right;

		public BinaryNode(T element, BinaryNode<T> left, BinaryNode<T> right) {
			this.element = element;
			this.left = left;
			this.right = right;
		}

		/**
		 * The recursive helper method for the add driver method which add and ensures
		 * that this set contains the specified item.
		 * 
		 * @param current - the current node we are at
		 * @param item    - the element we are looking for
		 * @return true if item was added and false if item is already within set
		 */
		public boolean addHelper(BinaryNode<Type> current, Type item) {

			// If item we are observing is less than current, then go left
			if (current.element.compareTo(item) > 0 && current.left != null) {
				return addHelper(current.left, item);
			}

			// If item we are observing is greater than current, then go right
			else if (current.element.compareTo(item) < 0 && current.right != null) {
				return addHelper(current.right, item);
			}

			// If we are at a leaf node and current is greater than item, then current.left
			// (left child) becomes item.
			else if (current.left == null && current.element.compareTo(item) > 0) {
				current.left = new BinaryNode<Type>(item, null, null);
				size++;
				return true;
			}

			// If we are at a leaf node and current is less than item, then current.right
			// (right child) becomes item.
			else if (current.right == null && current.element.compareTo(item) < 0) {
				current.right = new BinaryNode<Type>(item, null, null);
				size++;
				return true;
			}

			else {
				return false;
			}
		}

		/**
		 * The recursive helper method for the contains driver method that determines if
		 * there is the item we are looking for in this set.
		 * 
		 * @param current - the current node we are at
		 * @param item    - the element we are looking for
		 * @return true if the set contains item and false otherwise
		 */
		public boolean containsHelper(BinaryNode<Type> current, Type item) {

			if (current.element.equals(item)) {
				return true;
			}

			// Compares if current is more than item
			if (current.element.compareTo(item) > 0 && current.left != null) {
				return containsHelper(current.left, item);
			}

			// Compares if current is less than item
			if (current.element.compareTo(item) < 0 && current.right != null) {
				return containsHelper(current.right, item);
			} else {
				return false;
			}
		}

		/**
		 * The recursive helper method for the first driver method that returns the
		 * smallest (left most) item within the set.
		 * 
		 * @return smallest item
		 */
		public T firstHelper() {
			if (this.left == null)
				return this.element;
			else {
				return this.left.firstHelper();
			}
		}

		/**
		 * The recursive helper method for the last driver method that returns the
		 * largest (right most) item within the set.
		 * 
		 * @return largest item
		 */
		public T lastHelper() {
			if (this.right == null)
				return this.element;
			else {
				return this.right.lastHelper();
			}
		}

		/**
		 * The recursive helper method for the toArrayList driver method that returns
		 * the set as an ArrayList in sorted, ascending order.
		 * 
		 * @param current - the current node we are at
		 * @param list    - the list to be populated
		 * @return the sorted list
		 */
		public ArrayList<T> toArrayListHelper(BinaryNode<T> current, ArrayList<T> list) {

			// Left side
			if (current.left != null) {
				toArrayListHelper(current.left, list);
			}

			list.add(current.element);

			// Right side
			if (current.right != null) {
				toArrayListHelper(current.right, list);
			}

			return list;
		}

		/**
		 * @return a string containing all of the edges in the tree rooted at "this"
		 *         node, in DOT format
		 */
		public String generateDot() {
			String ret = "\tnode" + element + " [label = \"<f0> |<f1> " + element + "|<f2> \"]\n";
			if (left != null)
				ret += "\tnode" + element + ":f0 -> node" + left.element + ":f1\n" + left.generateDot();
			if (right != null)
				ret += "\tnode" + element + ":f2 -> node" + right.element + ":f1\n" + right.generateDot();

			return ret;
		}
	}

	private BinaryNode<Type> root;

	private int size;

	public BinarySearchTree() {
		root = null;
		size = 0;
	}

	/**
	 * Ensures that this set contains the specified item.
	 * 
	 * @param item - the item whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if
	 *         the input item was actually inserted); otherwise, returns false
	 */
	@Override
	public boolean add(Type item) {
		if (size == 0) {
			root = new BinaryNode<Type>(item, null, null);
			size++;
			return true;
		}

		return root.addHelper(root, item);
	}

	/**
	 * Ensures that this set contains all items in the specified collection.
	 * 
	 * @param items - the collection of items whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if
	 *         any item in the input collection was actually inserted); otherwise,
	 *         returns false
	 */
	@Override
	public boolean addAll(Collection<? extends Type> items) {
		boolean check = false;
		for (Type e : items) {
			if (check == add(e)) {
			} else {
				check = true;
			}
		}
		return check;
	}

	/**
	 * Removes all items from this set. The set will be empty after this method
	 * call.
	 */
	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	/**
	 * Determines if there is an item in this set that is equal to the specified
	 * item.
	 * 
	 * @param item - the item sought in this set
	 * @return true if there is an item in this set that is equal to the input item;
	 *         otherwise, returns false
	 */
	@Override
	public boolean contains(Type item) {
		if (isEmpty()) {
			return false;
		}
		return root.containsHelper(root, item);
	}

	/**
	 * Determines if for each item in the specified collection, there is an item in
	 * this set that is equal to it.
	 * 
	 * @param items - the collection of items sought in this set
	 * @return true if for each item in the specified collection, there is an item
	 *         in this set that is equal to it; otherwise, returns false
	 */
	@Override
	public boolean containsAll(Collection<? extends Type> items) {
		boolean check = true;
		for (Type e : items) {
			if (check == contains(e)) {
			} else {
				check = false;
			}
		}
		return check;
	}

	/**
	 * Returns the first (i.e., smallest) item in this set.
	 * 
	 * @throws NoSuchElementException if the set is empty
	 */
	public Type first() throws NoSuchElementException {
		if (size == 0)
			throw new NoSuchElementException();

		return root.firstHelper();
	}

	/**
	 * Returns true if this set contains no items.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the last (i.e., largest) item in this set.
	 * 
	 * @throws NoSuchElementException if the set is empty
	 */
	public Type last() throws NoSuchElementException {
		if (size == 0)
			throw new NoSuchElementException();

		return root.lastHelper();
	}

	/**
	 * Ensures that this set does not contain the specified item.
	 * 
	 * @param item - the item whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if
	 *         the input item was actually removed); otherwise, returns false
	 */
	public boolean remove(Type item) {
		if (!contains(item)) {
			return false;
		}
		BinaryNode<Type> temp = root;
		BinaryNode<Type> tempPrev = null;

		// If there's only one node
		if (size == 1) {
			this.clear();
			return true;
		}

		// Gets where temp is
		while (temp != null) {
			if (temp.element.equals(item))
				break;
			else if (temp.element.compareTo(item) > 0 && temp.left != null) {
				tempPrev = temp;
				temp = temp.left;
			} else if (temp.element.compareTo(item) < 0 && temp.right != null) {
				tempPrev = temp;
				temp = temp.right;
			}
		}

		// if temp is a leaf
		if (temp.left == null && temp.right == null) {
			if (tempPrev.left == temp) {
				tempPrev.left = null;
			} else {
				tempPrev.right = null;
			}
			size--;
			
			// Used for testing.
//			System.out.println(root.generateDot());
			return true;
		}

		// if temp only has one child to the left
		else if (temp.left != null && temp.right == null) {
			if (tempPrev == null) {
				root = temp.left;
			} else if (tempPrev.left == temp) {
				tempPrev.left = temp.left;
			} else {
				tempPrev.right = temp.left;
			}
			size--;
			
			// Used for testing.
//			System.out.println(root.generateDot());
			return true;
		}

		// if temp only has one child to the right
		else if (temp.left == null && temp.right != null) {
			if (tempPrev == null) {
				root = temp.right;
			} else if (tempPrev.left == temp) {
				tempPrev.left = temp.right;
			} else {
				tempPrev.right = temp.right;
			}
			size--;
			
			// Used for testing.
//			System.out.println(root.generateDot());
			return true;
		}

		// if temp has two children
		else {
			BinaryNode<Type> a = temp.right;
			BinaryNode<Type> aPrev = temp;
			while (a.left != null) {
				aPrev = a;
				a = a.left;
			}
			temp.element = a.element;
			
			// if a temp.right is a leaf
			if (a.left == null && a.right == null) {
				temp.right = null;
			}
			
			// if the temp.right has a left subtree 
			else if (a.left != null && a.right == null) {
				aPrev.left = null;
			}
			
			// if the temp.right has a right subtree 
			else {
				aPrev.right = a.right;
			}
			size--;
			
			// Used for testing.
//			System.out.println(root.generateDot());
			return true;
		}
	}

	/**
	 * Ensures that this set does not contain any of the items in the specified
	 * collection.
	 * 
	 * @param items - the collection of items whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if
	 *         any item in the input collection was actually removed); otherwise,
	 *         returns false
	 */
	public boolean removeAll(Collection<? extends Type> items) {
		boolean check = false;
		for (Type e : items) {
			if (check == remove(e)) {
			} else {
				check = true;
			}
		}
		return check;
	}

	/**
	 * Returns the number of items in this set.
	 */
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	/**
	 * Returns an ArrayList containing all of the items in this set, in sorted
	 * order.
	 */
	public ArrayList<Type> toArrayList() {
		ArrayList<Type> list = new ArrayList<Type>();
		if (root == null) {
			return list;
		}
		return root.toArrayListHelper(root, list);
	}

}