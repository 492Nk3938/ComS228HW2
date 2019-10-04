package cs228hw1.stats;

import java.util.*;
import java.io.*;

import cs228hw1.stats.Statistics.DATA;

/**
 * A class to store stat objects and read a file from NOAA containing data into an arraylist
 * @author nicholaskrabbenhoft
 *
 * @param <T> a object that extends number that the stat object will do calculations on
 */
public class StatisticsShell<T extends Number> implements Statistics<T> {

	/**
	 * Arraylist to hold the data read in
	 */
	/**/private/**/ ArrayList<T> dataSet;
	/**
	 * ArrayList to hold the statobjects stored in the object
	 */
	@SuppressWarnings("rawtypes")
	private ArrayList<StatObject> statObjectList;
	/**
	 * The parser used to parse the strings in the file
	 */
	private IParser<?> parse;

	/**
	 * Constructor takes a parser to parse the data it finds in the file you give to
	 * later in read file
	 * 
	 * @param Parser parser to parse data
	 */
	public StatisticsShell(IParser<?> newParser) {
		dataSet = new ArrayList<>();
		statObjectList = new ArrayList<>();
		parse = newParser;
	};

	/**
	 * Reads the weather data (of the specified variety) in the specified file into
	 * the active data set. The data in the file is treated as type T. Missing
	 * values are left in to insure lines match up but will be removed when put into
	 * stat objects.
	 * 
	 * @param d    The set of data to be read from the file.
	 * @param path The path of the file.
	 * @return Returns true if the file is successfully read and false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean ReadFile(String path, DATA d) {

		File input;
		Scanner scan;
		try {
			 input = new File(path);
			 scan = new Scanner(input);
		} catch (Exception e) {
			System.out.println("no file");
			return false;
		}

			 

			scan.nextLine();

				//input everything in the file
				while (scan.hasNextLine()) {

					String line = scan.nextLine();

					dataSet.add((T) parseLine(line, d));
					

				}

			scan.close();


		return true;
	}

	/**
	 * methiod to parse a single line from the data file
	 * 
	 * @param line The line of data that to iderate through and find data to return
	 * @param d    type of data from the file it returns
	 * @return the number from the column indicated by d parsed by parser givin at
	 *         objects construction
	 */
	/**/private/**/ Number parseLine(String line, DATA d) {

		Scanner scan;

		// This cleans the problems possed by ocastionally
		// having a T seperate rather then white space in later
		// colums
		if (line.contains("T")) {
			scan = new Scanner(lineCleaner(line));
		} else {

			scan = new Scanner(line);
		}

		int i;

		// this will get rid off all the unwanted values
		for (i = 0; i < d.ordinal(); i++) {
			scan.next();
		}

		String finalStr = scan.next();

		if (finalStr.contains("*")) {
			scan.close();
			return null;
		}

		scan.close();
		// System.out.println("The data going in is " +
		// parse.parse(finalStr).toString());

		return parse.parse(finalStr);

	}

	// Attempted parser
	/*
	 * private T stringToT(String string, DATA d) {
	 * 
	 * 
	 * if(false) {
	 * 
	 * 
	 * return (T) parse.parse(string);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * //These arrays hold the ordinal values of d of the different //types of data
	 * 
	 * int[] intVal = { 0, 1, 2, 3, 4, 5, 6, 12, 13, 14, 15, 16, 17, 18, 19, 21, 22,
	 * 26, 27};
	 * 
	 * int[] doubleVal = { 11, 23, 24, 25};
	 * 
	 * 
	 * 
	 * T returnT = null;
	 * 
	 * // these ifs check if d's values should be a double or int
	 * if(Arrays.asList(intVal).contains(d.ordinal())) {
	 * 
	 * returnT = (T) (Integer) Integer.parseInt(string);
	 * 
	 * } else if (Arrays.asList(doubleVal).contains(d.ordinal())) {
	 * 
	 * returnT = (T) (Double) Double.parseDouble(string);
	 * 
	 * }
	 * 
	 * 
	 * return returnT;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * //
	 */

	/**
	 * Takes a string with T in it and turns the T into whitespace It ignores T's
	 * preceded by C it ignores the T
	 * 
	 * @param str takes a string with T in it
	 * @return a string with all T not preceded by C removed
	 */
	/**/private/**/ String lineCleaner(String str) {

		char[] arr = str.toCharArray();
		String returnStr = "";

		if (arr[0] == 'T') {
			arr[0] = ' ';
		} else {
			returnStr += arr[0];
		}

		for (int i = 1; i < str.length(); i++) {

			if (i != 0 && arr[i] == 'T' && arr[i - 1] != 'C') {
				arr[i] = ' ';
			}
			returnStr += arr[i];

		}

		return returnStr;
	}

	/**
	 * Adds a StatObject to this Statistics object to the end of the list of
	 * StatObjects currently in it. The data currently stored in this Statistics
	 * object will be assigned to the new StatObject with all null values removed.
	 */
	public void AddStatObject(StatObject<T> so) {

		ArrayList<T> tempList = new ArrayList<>();

		tempList.addAll(dataSet);

		// this is to remove all null values from the data
		int i = 0;
		while (i < tempList.size()) {

			if (tempList.get(i) == null) {
				tempList.remove(i);
			} else {
				i++;
			}

		}

		so.SetData(tempList);
		statObjectList.add(so);

	}

	/**
	 * Adds a StatObject to this Statistics object to the end of the list of
	 * StatObjects currently in it. A subset of the data currently stored in this
	 * Statistics first is included and last is excluded if first is less then 0 it
	 * is set to 0 and if last is greater then the size of the array it is set to
	 * the size of the array object will be assigned to the new StatObject. if first
	 * is greater then last the object is added and no data is set All null values
	 * are removed from the list before they are added to the statObject
	 * 
	 * @param so    The new StatObject to add.
	 * @param first The index of the first piece of data to add to the provided
	 *              StatObject.
	 * @param last  The index of the last piece of data to add to the provided
	 *              StatObject.
	 */
	public void AddStatObject(StatObject<T> so, int first, int last) {

		ArrayList<T> tempList = new ArrayList<>();

		// IF it trys to add more then is wanted this corrects for it
		// and moves last to a possible last and sets problem to true
		// below the add all it then adds the last one of the list
		if (last > dataSet.size()) {
			last = dataSet.size();

		}
		// if you try to add a index less then 0 it corrects it to zero
		if (first < 0) {
			first = 0;
		}

		// just add the object with no data if first is greater then last
		if (first > last) {
			statObjectList.add(so);
			return;
		}

		for (int i = first; i < last; i++) {

			tempList.add(dataSet.get(i));

		}

		// this is to remove all null values from the data
		int i = 0;
		while (i < tempList.size()) {

			if (tempList.get(i) == null) {
				tempList.remove(i);
			} else {
				i++;
			}

		}

		so.SetData(tempList);
		statObjectList.add(so);

	}

	/**
	 * Obtains the [i]th StatObject in this Statistics object.
	 * 
	 * @param i The index to return.
	 * @return The specified StatObject or null if no such index exists.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public StatObject GetStatObject(int i) {

		// catch if i is a bad value
		if (statObjectList.size() <= i || i < 0) {
			return null;
		}
		// just in case
		try {
			return statObjectList.get(i);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Removes a StatObject from this Statistics object.
	 * 
	 * @param i The index to remove.
	 * @return Returns the StatObject removed or null if no such index exists.
	 */
	public StatObject<T> RemoveStatObject(int i) {

		// catch if i is a bad value
		if (statObjectList.size() <= i || i < 0) {
			return null;
		}

		// Inside try catch just in case previous sanitation didn't work
		try {
			StatObject<T> returnObj = statObjectList.get(i);
			statObjectList.remove(i);
			return returnObj;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}

	}

	/**
	 * Returns the number of StatObjects currently in this Statistics object.
	 */
	public int Count() {
		return statObjectList.size();
	}

	/**
	 * Returns a deep copy of the list containing the current data set used for
	 * calculations. This is when all null values are removed
	 */
	public ArrayList<T> GetDataSet() {
		ArrayList<T> returnArr = new ArrayList<T>();

		returnArr.addAll(dataSet);

		int i = 0;

		// to remove all null values
		while (i < returnArr.size()) {

			if (returnArr.get(i) == null) {
				returnArr.remove(i);
			} else {
				i++;
			}

		}

		return returnArr;
	}

	/**
	 * Performs each calculation in stored in this Statistics object in order.
	 * 
	 * @return an ArrayList of results when GetResult is called in every StatObject
	 *         in order.
	 */
	public ArrayList<ArrayList<T>> MapCar() {

		ArrayList<ArrayList<T>> allAnswers = new ArrayList<>();

		allAnswers.clear();

		for (int i = 0; i < statObjectList.size(); i++) {
			if (statObjectList.get(i).GetResult().size() > 1) {
				System.out.println("in statobjects");
			}
			allAnswers.add(statObjectList.get(i).GetResult());
		}

		return allAnswers;
	}

}
