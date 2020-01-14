
/*
 * THis is mostly done I just need to 
 * 			1. Comb through to make sure all of the methiods are exactly what they are supposed to be
 * 
 * 			2. thouroughly comment everything
 * 
 * 
 * 			3. The iterator could use more work particularly if I'm doing the reverse iterator
 * 
 * 
 * 			4. The odd functions that take in arrays or collections all need to be checked
 * 
 * 
 * 			5. I need to make sure all of the runtime is good. In particular when the repairList is called
 * 
 * 
 * 			6. remove all sysout's that shouldn't be there which is probably all of them
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

package cs228hw2;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * @author nicholaskrabbenhoft
 *
 */
public class AmusingLinkedList<E> implements List<E> {

	Node<E> head;
	int size;

	/*
	 * No method in your implementation of the List interface may have greater than
	 * O(n) run time, and the methods “boolean add( E o)”, “int size()”, “boolean
	 * isEmpty()”, and “clear()” must run in O(1) (constant) time. In addition to
	 * the methods required by the List Interface, you must also override the
	 * toString method. The definition for this method is given below.
	 */

	/**
	 * default constructor that creates an empty list
	 */
	public AmusingLinkedList() {
		head = null;
		size = 0;
	}

	/**
	 * Private constructor that creates an new list with all of the same values as
	 * the given list
	 * 
	 * @param toCopy the list you want to copy over into this list
	 */
	private AmusingLinkedList(AmusingLinkedList<E> toCopy) {
		ListIterator<E> iter = toCopy.listIterator();
		this.head = null;
		size = 0;

		while (iter.hasNext()) {

			this.add(iter.next());

		}

	}

	// original java docs
	/**
	 * Appends the specified element to the end of this list (optional operation).
	 *
	 *
	 * @param e element to be appended to this list
	 * @return boolean value true if list was effected
	 */
	@Override
	public boolean add(E e) {

		Node<E> toAdd = new Node<E>(e);

		// This is sets the object to be a node at the head if the list is empty
		if (head == null) {
			head = toAdd;
			head.prev = head;
			head.next = head;

			// This is to detirmn if the
			// if list is even then it is equel to head
			// if last iteam in list has odd index then this is not the case
			// This is when we are adding an odd index
		} else if (head.prev.next.equals(head)) {

			toAdd.next = head;
			// head.prev stays as null
			head.prev.next = toAdd;

			// This is when I'm adding the next even one
		} else {

			// sets it so to add point back to the head
			toAdd.next = head;
			// makes the prev = to the secound to last even node
			toAdd.prev = head.prev;
			// inserts toAdd to the end of the list
			// note head.prev points to the secound to last node
			head.prev.next.next = toAdd;
			// sets head.prev equal to toAdd which is now the last even node in the list
			head.prev = toAdd;

		}

		size++;

		return true;

	}

	// the standard list java docs
	/**
	 * Inserts the specified element at the specified position in this list
	 * (optional operation). Shifts the element currently at that position (if any)
	 * and any subsequent elements to the right (adds one to their indices).
	 *
	 * @param index   index at which the specified element is to be inserted
	 * @param element element to be inserted
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *                                   ({@code index < 0 || index > size()})
	 */
	@Override
	public void add(int index, E element) {

		// add input checks less then size greater then 0 and possible need speacial
		// case for 0

		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}

		Node<E> position = head;
		Node<E> toAdd = new Node<E>(element);

		if (head == null) {
			head = toAdd;

			repairList();
			return;
		}
		if (index == 0) {
			toAdd.next = head;
			breakCirclar();
			head = toAdd;
			repairList();
			return;
		}

		int pos = 0;

		//
		// This gets to the index-1 and then we in insert the new node in and repair the
		// list
		while (pos < index - 1) {

			pos++;
			position = position.next;
		}

		toAdd.next = position.next;

		position.next = toAdd;
		repairList();

	}

	// original from java doc
	/**
	 * Appends all of the elements in the specified collection to the end of this
	 * list, in the order that they are returned by the specified collection's
	 * iterator (optional operation). The behavior of this operation is undefined if
	 * the specified collection is modified while the operation is in progress.
	 * (Note that this will occur if the specified collection is this list, and it's
	 * nonempty.)
	 *
	 * @param c collection containing elements to be added to this list
	 * @return boolean value if the list was effected
	 * @throws ClassCastException       if the class of an element of the specified
	 *                                  collection prevents it from being added to
	 *                                  this list
	 * @throws IllegalArgumentException if some property of an element of the
	 *                                  specified collection prevents it from being
	 *                                  added to this list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(Collection<? extends E> c) {

		if (c == null) {
			throw new NullPointerException();
		}

		if (c.size() == 0) {
			return false;
		}

		Iterator<?> cIterator = c.iterator();
		try {
			while (cIterator.hasNext()) {
				this.add((E) cIterator.next());

			}
			return true;

			// this is to deal with possible exceptions
		} catch (ClassCastException e) {
			throw e;

		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * methiod to add a collection of objects the list starting at an index. This
	 * shits all elements at the index and after to the right
	 * 
	 * @param index      index to start adding the objects
	 * @param Collection c collection to add to the list
	 * @throws ClasscastException        if the collection contains on=bjects that
	 *                                   can't be cast to this lists type
	 * @throws NullPointerException      if the given collection is null
	 * @throws IndexOutOfBoundsException if the given index is greater then the size
	 *                                   or less then 0
	 * @return boolean true if the list was affected
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {

		// input validation
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (c == null) {
			throw new NullPointerException();
		}
		if (c.size() == 0) {
			return false;
		}

		// There is a +1 so that in the while loop the last one doesn't throw a index
		// out of bounds exception

		Node<E>[] tempNodes = new Node[c.size() + 1];

		for (int i = 0; i < c.size() + 1; i++) {
			tempNodes[i] = new Node<E>();
		}

		Iterator<?> addIter = c.iterator();

		int i = 0;
		while (addIter.hasNext()) {
			tempNodes[i].data = (E) addIter.next();
			tempNodes[i].next = tempNodes[i + 1];
			i++;
		}

		// Special case if we wanted to add all of them at the head
		int j = 0;
		if (head != null && index == 0) {
			// the minus 2 is because there is one extra padding on the end of the array
			Node<E> tail = this.getNodeAtIndex(size - 1);
			tempNodes[tempNodes.length - 2].next = tail.next;
			this.breakCirclar();
			head = tempNodes[0];
			repairList();
			return true;
		} else if (head == null && index == 0) {
			tempNodes[tempNodes.length - 2].next = null;
			head = tempNodes[0];
			repairList();
			return true;
		}

		// This moves pos to the point that we want to add all of the iteams
		Node<E> pos = head;
		while (j++ < index) {
			pos = pos.next;
		}

		// This is minus 2 because there should be 1 extra node in the array with
		// nothing in it
		tempNodes[tempNodes.length - 2].next = pos.next;
		pos.next = tempNodes[0];

		size += c.size();

		// This will make sure all of the node.prevs work out right
		repairList();

		return true;

	}

	/**
	 * methiod to remove everything from the list
	 */
	@Override
	public void clear() {
		head = null;
		size = 0;

	}

	/**
	 * methiod that returns true if the input object is within the list
	 * 
	 * @param object o is the object to check against
	 * @return true if object is within the list
	 */
	@Override
	public boolean contains(Object o) {

		AmusingListIterator iterator = new AmusingListIterator();
		E compareTo;
		while (iterator.hasNext()) {
			compareTo = iterator.next();

			// The or statment deals with the chance that the compareValue is null
			if (compareTo == o || (compareTo != null && compareTo.equals(o))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns true if this list contains all of the elements of the specified
	 * collection.
	 * 
	 * @param c collection to be checked
	 * @return true if all values in c are in the list and false if any values
	 *         aren't in the list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean containsAll(Collection<?> c) {

		Iterator<?> cIterator = c.iterator();

		try {
			while (cIterator.hasNext()) {
				// if the next objcect isn't in it returns false

				E toCheck = (E) cIterator.next();
				if (!this.contains(toCheck)) {
					return false;
				}

			}
			return true;
		} catch (ClassCastException e) {
			// This may not be an error because the collection may contain the wrong type
			// which could fail to cast and throw an error getting here
			return false;
		}

	}

	/**
	 * Methiod to return the data at an index
	 * 
	 * @param the index to look for the inforamtion
	 * @throws IndexOutOfBoundsException if the index if larger then the size or
	 *                                   less then 0
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}

		Node<E> position = head;

		// posible need a special case for 0

		int pos = 0;
		E toReturn;

		// this gets pos to the one right before the get
		while (pos < index) {

			pos++;
			position = position.next;
		}

		// increment to the one that I want

		toReturn = position.data;

		// No need to repair since I'm just getting the data

		return toReturn;
	}

	/**
	 * This methiod takes an object and returns the index of that object if it isin
	 * the list or -1 if it is not in the list
	 * 
	 * @param Object o to look for
	 * @return the first index of the object or -1 if the object isn't in the list
	 */
	@Override
	public int indexOf(Object o) {

		Node<E> position = head;
		int index = 0;
		// return false if there is nothing
		if (head == null) {
			return -1;
		}

		// checks if the data exists at that node and returns if it does
		// if it goes all the way through return 0
		do {
			if (position.data == o || (position.data != null && position.data.equals(o))) {
				return index;
			}

			position = position.next;
			index++;
		} while (index < size);

		return -1;
	}

	/**
	 * A methiod to return true if the list is empty
	 * 
	 * @return true if the list contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return head == null;
	}

	/**
	 * A methiod that returns an iterator for the list
	 * 
	 * @return an AmusingListIterator of type E for the list
	 */
	@Override
	public Iterator<E> iterator() {

		return new AmusingListIterator();
	}

	/**
	 * This returns the last index of the object in the list or -1 if it isn't in it
	 * 
	 * @param object o to search for
	 * @return int of the index or -1 if not in it
	 */
	@Override
	public int lastIndexOf(Object o) {

		int returnInt = -1;

		// return false if there is nothing
		if (head == null) {
			return -1;
		}
		AmusingListIterator iter = new AmusingListIterator();
		E toCompare;

		// This iterates through the list and checks if it is equal and if it is it
		// stores the value and keeps going because there could be a larger index where
		// they are equal
		while (iter.hasNext()) {
			toCompare = iter.next();
			if (toCompare == o || (toCompare != null && toCompare.equals(o))) {
				returnInt = iter.previousIndex();
			}
		}

		return returnInt;
	}

	/**
	 * A methiod to give return a iterator for the list
	 * 
	 * @return Iterator iterator for the list
	 */
	@Override
	public ListIterator<E> listIterator() {
		return new AmusingListIterator();
	}

	/**
	 * A methiod to give return a iterator for the list at a given index
	 * 
	 * @param int index to move the iterator to
	 * @return Iterator iterator for the list at a given index
	 * @throws IndexOutOfBoundsException if index<0 or index>= size
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}

		ListIterator<E> temp = new AmusingListIterator();

		while (temp.nextIndex() != index) {
			temp.next();

		}

		return temp;
	}

	/**
	 * Methiod to remove the object at a given index and return it's value
	 * 
	 * @throws IndexOutOfBoundsException if index < 0 or Index>= Size
	 * @return E object that was at the index
	 * @param index you want to work on
	 */
	@Override
	public E remove(int index) {

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		// add input checks less then size greater then 0 and possible need speacial
		// case for 0
		E returnE;

		// Here is the point to deal with if the head is null or we want to remove the
		// head
		if (head == null) {
			return null;
		} else if (index == 0) {
			this.breakCirclar();
			returnE = head.getData();
			head = head.next;

			repairList();
			return returnE;
		}

		Node<E> position = head;

		int pos = 0;

		// this gets pos to the one right before the removal
		while (pos < index - 1) {

			pos++;
			position = position.next;
		}
		// This gets the value of the next data object and stores it
		returnE = position.next.data;

		// this sets the next pointer on the node before the one we want to remove equl
		// to the node after the one we want to remove removing it from the list
		position.next = position.next.next;

		// function that goes through and makes sure everything points to where it's
		// supposed to
		repairList();

		return returnE;
	}

	/**
	 * methiod to remove the first instance of an object
	 * 
	 * @param o the object to be removed
	 * @return true if the object was removed sucseffuly
	 */
	@Override
	public boolean remove(Object o) {
		try {
			// Note remove throws error for -1 which index of returns if the object doesn't
			// exist which is the reason for the try catch
			this.remove(this.indexOf(o));
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

	}

	/**
	 * a function to remove all of a collection from the list. Note: this will
	 * remove all instances of an object
	 * 
	 * @param c collection of objects to remove
	 * @return boolean true if sucesful
	 * @throws NullPointerException if c is null
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		Iterator<?> cIterator = c.iterator();

		while (cIterator.hasNext()) {
			Object toRemove = cIterator.next();

			while (this.contains(toRemove)) {
				this.remove(toRemove);
			}
		}
		return true;

	}

	/**
	 * This methiod will remove all elements except the ones that are in the given
	 * collection
	 * 
	 * @throws NullPointerException if given collection is null
	 * @return true if the list was affected
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}

		Iterator<E> iter = this.iterator();

		E valueToCheck = null;

		// This goes through the list and if the value isn't in the collection it
		// removes all repitions of it from the list

		while (iter.hasNext()) {
			valueToCheck = (E) iter.next();
			// This checks if this list cointains the element and c doesn't and if it does
			// it removes it
			while (!c.contains(valueToCheck) && this.contains(valueToCheck)) {

				this.remove(valueToCheck);

			}

		}

		repairList();

		return true;
	}

	/**
	 * This methiod takes a UnaryOperator and applies it to every object in this
	 * list
	 * 
	 * @param op is the operation that will be preformed on every entry in the list
	 * @throws NullPointerException if op is null
	 * 
	 */
	public void replaceAll(UnaryOperator<E> op) {
		if (op == null) {
			throw new NullPointerException();
		}

		ListIterator<E> iter = this.listIterator();
		AmusingLinkedList<E> set = new AmusingLinkedList<>();

		while (iter.hasNext()) {
			set.add(op.apply(iter.next()));

		}

	}

	/**
	 * Methiod that takes an object and checks if it is an AmusingLinkedList with
	 * all of the same values as this list and if it does it returns true
	 * 
	 * @param object o to compare to
	 * @return true if both this and o are AmusingLinkedLists and have the same
	 *         values in the same order
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!this.getClass().equals(o)) {
			return false;
		}

		AmusingLinkedList<?> comparor = (AmusingLinkedList<?>) o;

		Iterator<E> thisIter = this.iterator();
		Iterator<?> otherIter = comparor.iterator();

		while (thisIter.hasNext() && otherIter.hasNext()) {
			if (!thisIter.next().equals(otherIter.hasNext())) {
				return false;
			}
		}

		if (thisIter.hasNext() || otherIter.hasNext()) {
			return false;
		}

		return true;
	}

	/**
	 * Methiod that sets the element stored at the given index to the given one and
	 * returns the element previously at that index
	 * 
	 * @param index the index to change
	 * @param E     element the object to put in the given index
	 * @throws IndexOutOfBoundsException if the given index is less then zero or
	 *                                   greater then size-1
	 */
	@Override
	public E set(int index, E element) {

		if (index >= this.size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		// add input checks less then size greater then 0 and possible need speacial
		// case for 0

		Node<E> position = head;
		E toReturn;

		int pos = 0;

		// this gets pos to the right node
		while (pos < index) {

			pos++;
			position = position.next;
		}

		toReturn = position.data;

		position.data = element;

		return toReturn;

	}

	/**
	 * Returns the number of pieces of data in the list. If this contains more
	 * values then Integer.Max it's behavor is unknown
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * A god awful methiod that creates a sublist that references the main list
	 * between 2 indexes but can't be edited
	 * 
	 * @param fromIndex the index to start including data (inclusive)
	 * @param toINdex   the index to stop including data (exclusive)
	 * 
	 */
	@Override
	public List<E> subList(int fromIndex, int toIndex) {

		return new SubList<E>(fromIndex, toIndex, this);

	}

	/**
	 * A methiod that will return an array containing a deep copy of all of the
	 * objects in the List in the correct order
	 * 
	 * @return an Object[] containing all of the objects in order
	 */
	@Override
	public Object[] toArray() {

		Object[] returnArray = new Object[this.size];

		Iterator<E> iter = this.iterator();
		int i = 0;
		while (iter.hasNext()) {
			returnArray[i++] = iter.next();
		}
		return returnArray.clone();
	}

	/**
	 * This is a methiod to put the linked list into an array which is given. If the
	 * array is too small a new array is created of type T but otherwise the array
	 * given is filled in with a deep copy of all of the objects in order. All
	 * unused space is then set to null
	 * 
	 * @param An array to be filled
	 * @return Array of same type as the input containing all the values in correct
	 *         order
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		T[] returnArray;

		// This if statment block makes sure the given array is valid and if not just
		// returns a new array
		if (a != null) {
			for (int i = 0; i < a.length; i++) {
				a[i] = null;
			}

			if (this.head == null) {
				return a;
			}

			if (a.length >= size) {
				returnArray = a;

			} else {
				T[] toReturn;

				try {
					toReturn = (T[]) this.toArray();
				} catch (ClassCastException e) {
					throw new ArrayStoreException();
				}

				return toReturn;
			}
		} else {
			throw new NullPointerException();
		}

		AmusingListIterator iter = new AmusingListIterator();

		int i = 0;
		while (iter.hasNext()) {
			try {
				returnArray[i++] = (T) iter.next();
			} catch (ClassCastException e) {
				throw new ArrayStoreException();
			}

		}

		return returnArray;
	}

	/**
	 * Returns a string representation of the class. The toString method for this
	 * class must return the following data, starting with the head and traversing
	 * forward through the list. List index of the current node in the iteration
	 * 
	 * List index of the node that the previous link references. (Print -1 if the
	 * previous reference is null)
	 * 
	 * List index of the node referenced by the next link. (Print -1 if the next
	 * reference is null).
	 * 
	 * the String returned when toString is called on the data object for the node,
	 * or “null” if the data item is null.
	 * 
	 * @return string of th list
	 */
	public String toString() {

		String returnString = "";
		int index = 0;

		Node<E> pos = head;

		// special case to see if this is what they want
		if (pos == null) {
			return "null";
		}

		// returnString += "Head: " + head.toString().substring(27) + "\n";
		// returnString += "Size: " + this.size + "\n";

		// This is set up so it can end when the position is equal to head but we can
		// start with the position equalling head

		// special for the head
		returnString += "Index: " + index + "  previous ";

		// this part is just in case but shouln't happen
		if (pos.prev == null) {
			returnString += "-1             ";

			// else should always happen
		} else {

			// using int divison this should always give the index of last even node
			returnString += ((size - 1) / 2) * 2 + "  ";
		}

		if (pos.next != null) {
			returnString += "Data: " + pos.getData() + "		Next: " + index + "\n";
			// Just in case but should never happen

		} else {
			returnString += "Data: " + pos.getData() + "		Next: -1" // +
																			// pos.next.toString().substring(27)
					+ "\n";
		}
		pos = pos.next;
		index++;

		while (pos.next != null && pos.next != head) {

			returnString += "Index: " + index + "  previous ";
			if (pos.prev == null) {
				returnString += "-1             ";

			} else {

				returnString += index - 2 + "  ";
			}

			if (pos.next != null) {
				returnString += "Data: " + pos.getData() + "		Next: " + (index + 1) + "\n";
				// Just in case but should never happen
			} else {
				returnString += "Data: " + pos.getData() + "		Next: -1" // +
																				// pos.next.toString().substring(27)
						+ "\n";
			}
			pos = pos.next;
			index++;

		}

		// spicial for the tail
		returnString += "Index: " + index + "  previous ";
		if (pos.prev == null) {
			returnString += "-1             ";

		} else {

			returnString += (index - 2) + "  ";
		}
		if (pos.next != null) {
			returnString += "Data: " + pos.getData() + "		Next: " + 0 + "\n";

			// Just in case but should never happen
		} else {
			returnString += "Data: " + pos.getData() + "		Next: -1" // +
																			// pos.next.toString().substring(27)
					+ "\n";
		}

		return returnString;
	}

	/**
	 * This is a class that simply strips all of the methiods that change the
	 * structure of a list and then fills itsel with the values of the parents
	 * between two indexes
	 * 
	 * @author nicholaskrabbenhoft
	 *
	 * @param <E>
	 */
	@SuppressWarnings("hiding")
	public class SubList<E> extends AmusingLinkedList<E> implements List<E> {

		/**
		 * Constructor for the sublist that takes in the parent list and then creates a
		 * list between the fromIndex inclusive to the toIndex exclusive
		 * 
		 * @param fromIndex
		 * @param toIndex
		 * @param parent
		 */
		public SubList(int fromIndex, int toIndex, AmusingLinkedList<E> parent) {

			if (fromIndex > toIndex) {
				throw new IndexOutOfBoundsException();
			}

			if (parent == null) {
				head = null;
				size = 0;
				return;
			}

			head = new Node<E>();
			head.data = parent.getNodeAtIndex(fromIndex).data;
			Node<E> prev = head;

			for (int i = fromIndex + 1; i < toIndex; i++) {
				Node<E> next = new Node<E>();

				next.data = parent.getNodeAtIndex(i).data;
				prev.next = next;
				prev = next;
			}

			this.repairList();

		}

		@Override
		public boolean add(E e) {

			throw new UnsupportedOperationException();

		}

		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(@SuppressWarnings("rawtypes") Collection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int index, @SuppressWarnings("rawtypes") Collection c) {
			throw new UnsupportedOperationException();

		}

		@Override
		public boolean removeAll(@SuppressWarnings("rawtypes") Collection c) {
			throw new UnsupportedOperationException();

		}

		@Override
		public boolean retainAll(@SuppressWarnings("rawtypes") Collection c) {
			throw new UnsupportedOperationException();

		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();

		}

		@Override
		public void add(int index, Object element) {
			throw new UnsupportedOperationException();

		}

		@Override
		public E remove(int index) {
			throw new UnsupportedOperationException();

		}

	}

	/**
	 * This is a methiod to return the node in the list at a given index
	 * 
	 * @param index the index in the list you want to get a node from
	 * @return Node<E> which is a internal class of this object conting data and
	 *         pointers to a next and previous node
	 */
	public Node<E> getNodeAtIndex(int index) {

		// input validation
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		Node<E> pos = head;
		int point = 0;

		// moves the pos varible to the node we want
		while (point < index) {

			pos = pos.next;
			point++;
		}

		return pos;

	}

	/**
	 * Private methiod that breaks the cirularity of the list
	 */
	private void breakCirclar() {
		// This sets the tail nodes next value to null so it is no longer a circular
		// list
		this.getNodeAtIndex(size - 1).next = null;

	}

	/**
	 * Internal methiod that repairs the list to correct specs
	 */
	protected void repairList() {

		// This makes the list correctly formated

		if (head == null) {
			size = 0;
			return;
		}

		int position = 0;
		Node<E> pos = head;
		Node<E> previus = head;

		do {

			if (position % 2 == 0 && position != 0) {

				pos.prev = previus;

				previus = pos;
				pos = pos.next;

			} else {
				pos.prev = null;
				pos = pos.next;
			}

			if (pos == null || pos.equals(this.head)) {
				head.prev = previus;
				size = position + 1;
				if (position % 2 == 0) {
					head.prev.next = head;
				} else {
					head.prev.next.next = head;
				}

				return;

			}
			position++;

		} while (pos != null);

		System.out.println("error lskjfbjlkbasjf");

	}

	/**
	 * Class that contains the data and refernces of the linked list
	 * 
	 * @author nicholaskrabbenhoft
	 *
	 * @param <E>
	 */
	@SuppressWarnings("hiding")
	public class Node<E> {
		public Node<E> prev;
		public Node<E> next;
		public E data;

		/**
		 * default constructor
		 */
		public Node() {
			prev = null;
			next = null;
			data = null;
		}

		/**
		 * Constructor that takes data
		 * 
		 * @param data to for the node to hold
		 */
		public Node(E data) {
			this.prev = null;
			this.next = null;
			this.data = data;
		}

		public E getData() {
			return data;

		}

		public Node<E> getNext() {
			return next;

		}

		public Node<E> getPrev() {
			return prev;

		}

	}

	/**
	 * 
	 * @author nicholaskrabbenhoft A List iterator class that can not effect the
	 *         list and must be recreated after each change to the list
	 *
	 */
	public class AmusingListIterator implements ListIterator<E> {

		int thisSize;

		Node<E> pos;

		int index;

		public AmusingListIterator() {
			pos = head;
			index = 0;
			thisSize = size;

		}

		/**
		 * methiod to chack if there is a next iteam
		 * 
		 * @return boolean true if there is a next
		 */
		@Override
		public boolean hasNext() {

			return index < thisSize;
		}

		/**
		 * methiod to return the next iteam in the list
		 * 
		 * @return E object which is next in the list
		 */
		@Override
		public E next() {
			/// *
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			// */
			E temp = pos.getData();

			pos = pos.next;
			index++;
			return temp;
		}

		/**
		 * methiod to check if there is a previous object in the list
		 * 
		 * @return boolean is true if there are previous objects
		 */
		@Override
		public boolean hasPrevious() {

			return index > 0;

		}

		/**
		 * a method to return the previous object in the list
		 * 
		 * @return E object of the previous index
		 */
		@Override
		public E previous() {

			if (!hasPrevious()) {
				throw new NoSuchElementException();

			}

			index--;

			// This deals with the head spcial case
			if (pos.equals(head)) {
				if (pos.prev.next.equals(pos)) {
					pos = pos.prev;
					return pos.getData();
				} else {
					pos = pos.prev.next;
					return pos.getData();
				}

			}

			// if it did have a previos then go to the previous and then move
			// to the next and returns that data
			if (pos.getPrev() != null) {
				pos = pos.prev.next;
				return pos.getData();
			} else {

				pos = pos.next.prev;
				return pos.getData();
			}

		}

		@Override
		public int nextIndex() {

			return index;
		}

		@Override
		public int previousIndex() {
			return index - 1;
		}

		@Override
		public void remove() {

			throw new UnsupportedOperationException();

		}

		@Override
		public void set(E e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException();

		}

	}

	/**
	 * A desending List iterator that just translates all commands into a regular
	 * list iterator
	 * 
	 * @author nicholaskrabbenhoft
	 *
	 */
	public class AmusingListDecendingIterator implements ListIterator<E> {
		// The plan is to just make a regular iterator at the end and then revers
		// everything
		AmusingListIterator cheat;

		public AmusingListDecendingIterator() {
			cheat = new AmusingListIterator();
			while (cheat.hasNext()) {
				cheat.next();
			}

		}

		@Override
		public boolean hasNext() {
			return cheat.hasPrevious();
		}

		@Override
		public E next() {
			return cheat.previous();
		}

		@Override
		public boolean hasPrevious() {
			return cheat.hasNext();
		}

		@Override
		public E previous() {
			return cheat.next();
		}

		@Override
		public int nextIndex() {
			return cheat.previousIndex();
		}

		@Override
		public int previousIndex() {
			return cheat.nextIndex();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}

		@Override
		public void set(E e) {
			throw new UnsupportedOperationException();

		}

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException();

		}

	}

	/**
	 * methiod to clone the list. returns a new list with no references to this list
	 */
	@Override
	public AmusingLinkedList<E> clone() {

		return new AmusingLinkedList<E>(this);
	}

	/**
	 * Methiod to create a desending iterator for this list
	 * 
	 * @return A listIterator that goes through all of the values in decending order
	 */
	public ListIterator<E> descendingIterator() {

		return new AmusingListDecendingIterator();
	}

}
