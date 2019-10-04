package cs228hw1.stats;

import java.util.*;

/**
 * an abstract class theat implements statobject and holds the methods form statobject interface
 *  all but the get results which must be implemented in the individual classes
 * 
 * @author nicholaskrabbenhoft
 *
 * @param <T> a object that extends number that the stat object will do calculations on
 */
public abstract class AbStat<T extends Number> implements StatObject<T> {

	/**
	 * The arraylist of type T containg the data held in the stat object
	 * (probably not the best name)
	 */
	ArrayList<T> myList;

	/**
	 * A string that contains a discription for the object
	 */
	private String description;

	ArrayList<T> results;

	/**
	 * 
	 * Dealt constructor sets elements to new arraylists
	 */
	protected AbStat() {
		results = new ArrayList<>();
		myList = new ArrayList<>();
	}

	/**
	 * Constructor takes data
	 * 
	 * @param data The data set to do the calculations on
	 */
	protected AbStat(ArrayList<T> data) {

		results = new ArrayList<>();
		myList = new ArrayList<>();

		SetData(data);

	}

	/**
	 * sets the description of the object
	 * 
	 * @param String d to set to the dissipation
	 */
	public void SetDescription(String d) {

		description = d;

	}

	/**
	 * Method to access the description
	 * 
	 * @return String of the description
	 */
	public String GetDescription() {
		return description;
	}

	/**
	 * Clears all of the data currently in the object and sets it to given data
	 * 
	 * @param ArrayList of T data to set the data to
	 */
	public void SetData(ArrayList<T> data) {

		myList.clear();
		if (data == null || data.isEmpty()) {
			return;
		}
		myList.addAll(data);

	}

	/**
	 * Takes Data and throws RuntimeException if null or empty
	 * 
	 * @param data arraylist that is tested to see if empty or null
	 * @throws RuntimeException
	 */
	protected void ifValid(ArrayList<T> data) throws RuntimeException {
		if (data == null || data.size() == 0) {
			throw new RuntimeException();
		}
	}

	/**
	 * accesor for the data in the object
	 * 
	 * @return ArrayList<T> myList
	 */
	public ArrayList<T> GetData() {

		return myList;

	}

	/**
	 * Takes a list of type T and sorts it
	 * 
	 * @param list ArrayList to be sorted
	 * @return ArrayList with same elements reordered to be sorted
	 */
	protected ArrayList<T> sort(ArrayList<T> list) {

		ArrayList<T> tempList = new ArrayList<>();
		tempList.addAll(list);

		// here we loop through the array and find the min value and set it for the
		// first value
		// the program then does that for the second value excluding the first ect
		for (int i = 0; i < tempList.size(); i++) {
			T min = (T) tempList.get(i);
			int index = i;

			for (int j = i; j < tempList.size(); j++) {

				if (min.doubleValue() > (tempList.get(j)).doubleValue()) {
					index = j;
					min = tempList.get(j);

				}

			}
			T temp = tempList.get(i);
			tempList.set(i, min);
			tempList.set(index, temp);

		}

		return tempList;

	}

}
