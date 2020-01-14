
package cs228hw2;

import java.util.*;

/**
 * implimintation of deque using ALL
 * 
 * @author nicholaskrabbenhoft
 *
 * @param <E> the tyoe to hold
 */
public class Deque228<E> implements Deque<E> {

	AmusingLinkedList<E> myQue;

	public Deque228() {
		myQue = new AmusingLinkedList<E>();
	}

	private Deque228(AmusingLinkedList<E> input) {
		myQue = input;

	}

	@Override
	public Deque228<E> clone() {

		return new Deque228<E>(myQue.clone());

	}

	public String toString() {

		return "The Deque228 Contains:\n" + myQue.toString();

	}

	@Override
	public boolean isEmpty() {
		return myQue.isEmpty();
	}

	@Override
	public Object[] toArray() {
		return myQue.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return myQue.toArray(a);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return myQue.containsAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return myQue.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return myQue.retainAll(c);
	}

	@Override
	public void clear() {
		myQue.clear();
	}

	@Override
	public void addFirst(E e) {
		myQue.add(0, e);
	}

	@Override
	public void addLast(E e) {
		myQue.add(e);
	}

	@Override
	public boolean offerFirst(E e) {

		try {
			myQue.add(0, e);
			return true;
		} catch (Exception x) {
		}

		return false;
	}

	@Override
	public boolean offerLast(E e) {

		try {
			System.out.println(e);
			myQue.add(e);
			System.out.println(e);
			return true;
		} catch (Exception x) {
		}

		return false;
	}

	@Override
	public E removeFirst() {
		if (myQue.isEmpty()) {
			throw new NoSuchElementException();
		}

		E temp = myQue.get(0);
		myQue.remove(0);

		return temp;
	}

	@Override
	public E removeLast() {
		E temp = myQue.get(myQue.size - 1);
		myQue.remove(myQue.size - 1);

		return temp;
	}

	@Override
	public E pollFirst() {

		try {
			return this.removeFirst();
		} catch (Exception x) {
		}

		return null;
	}

	@Override
	public E pollLast() {

		try {
			return this.removeLast();
		} catch (Exception x) {
		}

		return null;
	}

	@Override
	public E getFirst() {
		return myQue.get(0);
	}

	@Override
	public E getLast() {
		return myQue.get(myQue.size - 1);
	}

	@Override
	public E peekFirst() {
		try {
			return this.getFirst();
		} catch (Exception x) {
		}

		return null;
	}

	@Override
	public E peekLast() {
		try {
			return this.getLast();
		} catch (NullPointerException x) {
		}

		return null;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		return myQue.remove(o);
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		try {
			// This gets the last index of an object then removes it
			myQue.remove(myQue.lastIndexOf(o));
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean add(E e) {

		try {
			this.addLast(e);
			return true;
		} catch (Exception x) {
		}

		return false;

	}

	@Override
	public boolean offer(E e) {

		return myQue.add(e);

	}

	@Override
	public E remove() {
		return this.removeFirst();
	}

	@Override
	public E poll() {
		return this.pollFirst();
	}

	@Override
	public E element() {
		return this.getFirst();
	}

	@Override
	public E peek() {
		return this.peekFirst();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return myQue.addAll(c);
	}

	@Override
	public void push(E e) {
		this.addFirst(e);
	}

	@Override
	public E pop() {
		return this.removeFirst();
	}

	@Override
	public boolean remove(Object o) {
		return this.removeFirstOccurrence(o);
	}

	@Override
	public boolean contains(Object o) {
		return myQue.contains(o);
	}

	@Override
	public int size() {
		return myQue.size();
	}

	@Override
	public Iterator<E> iterator() {
		return myQue.iterator();
	}

	@Override
	public ListIterator<E> descendingIterator() {

		return myQue.descendingIterator();
	}

	public ListIterator<E> listIterator() {
		return myQue.listIterator();
	}

}
