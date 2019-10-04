package cs228hw1;

import java.util.*;
import cs228hw1.stats.*;
import cs228hw1.stats.Statistics.DATA;

/**
 * Client to take a txt document from NOAA and print out histogram of monthly highs
 * monthly highs and lows and daily average and medians of temprature
 * @author nicholaskrabbenhoft
 *
 */
public class Weather {

	/**
	 * The main function
	 * @param args
	 */
	public static void main(String args[]) {

		String fileName;

		System.out.println("Welcome to the weather analyzer \n" + "Please input the file you want to analyze");
		Scanner scan = new Scanner(System.in);

		fileName = scan.next();

		// for debugging purposes
		 //fileName = "/inputData.txt";

		monthlyHigaLowHisto(fileName);

		dailyAverageMedian(fileName);

		scan.close();

	}

	/**
	 * Function to group all of the printing out of highs lows and the histogram
	 * @param fileName the path to the file
	 */
	private static void monthlyHigaLowHisto(String fileName) {

		LongParser longParser = new LongParser();
		IntParser intParser = new IntParser();

		StatisticsShell<Long> dateShell = new StatisticsShell<Long>(longParser);

		dateShell.ReadFile(fileName, DATA.YR_MO_DA_HR_MN);

		ArrayList<Long> dateList = dateShell.GetDataSet();

		StatisticsShell<Integer> tempShell = new StatisticsShell<Integer>(intParser);
		tempShell.ReadFile(fileName, DATA.TEMP);

		int i = 0;

		// Simply here to reduce number of times needed to calculate the length
		int numOfDates = dateList.size();

		// This loop will go through all lines of data
		// it will find the month that it starts at and record the index
		// it will then loop through the dates till it finds the index of the
		// last day of the month
		// it will then add a min and max stat object and assign them sections
		while (i < numOfDates) {
			int month = Date(dateList.get(i), 3);

			Maximum<Integer> max = new Maximum<Integer>();
			Minimum<Integer> min = new Minimum<Integer>();
			int start = i;

			while (i < numOfDates && month == Date(dateList.get(i), 3)) {

				i++;

			}

			int stop = i;

			min.SetDescription(
					" The min from " + dateList.get(start) + " to " + dateList.get(stop - 1) + " inclusove is ");
			max.SetDescription(
					" The max from " + dateList.get(start) + " to " + dateList.get(stop - 1) + " inclusove is ");

			tempShell.AddStatObject(max, start, stop);
			tempShell.AddStatObject(min, start, stop);

			i++;

		}

		int numOfObjects = tempShell.Count();
		i = 0;
		ArrayList<Integer> maxTemps = new ArrayList<>();

		while (i < numOfObjects) {
			// prints out the description and results of all objects in tempShell
			System.out.println(tempShell.GetStatObject(i).GetDescription() + tempShell.GetStatObject(i).GetResult());
			// if the object is max its value is added to maxTemps to calculate later
			if (tempShell.GetStatObject(i).GetDescription().contains("max")) {
				maxTemps.add((Integer) tempShell.GetStatObject(i).GetResult().get(0));
			}
			i++;
		}

		Histogram<Integer> histo = new Histogram<Integer>();

		histo.SetData(maxTemps);

		histo.SetMaxRange(110);
		histo.SetMinRange(-40);
		histo.SetNumberBins(15);

		System.out.println("A histogram of the high temps in 15 bins from -40 to 110" + histo.GetResult().toString());

	}

	/**
	 * methiod to group the printing out of everything for the daily stuff ie averages and medians
	 * @param fileName path to the name of the file that it gets the data from
	 */
	private static void dailyAverageMedian(String fileName) {

		
		
		/*
		 * I create a parser for long because the dates are too big to be an int and then
		 * pass it into a statistics shell  that then returns the arrayList of dates
		 */
		LongParser longParser = new LongParser();

		StatisticsShell<Long> dateShell = new StatisticsShell<Long>(longParser);

		dateShell.ReadFile(fileName, DATA.YR_MO_DA_HR_MN);

		ArrayList<Long> dateList = dateShell.GetDataSet();

		IntParser intParser = new IntParser();

		StatisticsShell<Integer> tempShell = new StatisticsShell<Integer>(intParser);
		tempShell.ReadFile(fileName, DATA.TEMP);

		int i = 0;

		// Simply here to reduce number of times needed to calculate the length
		int numOfDates = dateList.size();

		// This loop will go through all lines of data
		// it will find the month that it starts at and record the index
		// it will then loop through the dates till it finds the index of the
		// last day of the month
		// it will then add a ave and median stat object and assign them sections
		while (i < numOfDates) {
			int day = Date(dateList.get(i).intValue(), 2);

			Average<Integer> ave = new Average<Integer>();
			Median<Integer> median = new Median<Integer>();
			int start = i;

			//iterates to the begining of the next month
			while (i < numOfDates && day == Date(dateList.get(i).intValue(), 2)) {

				i++;
			}

			int stop = i;
			
			//sets discription to be printed out later
			ave.SetDescription(
					" The average from " + dateList.get(start) + " to " + dateList.get(stop - 1) + " inclusove is ");
			median.SetDescription(
					" The median from " + dateList.get(start) + " to " + dateList.get(stop - 1) + " inclusove is ");

			//add the objects to the stat shell with the indexes for the data
			tempShell.AddStatObject(ave, start, stop);
			tempShell.AddStatObject(median, start, stop);

			i++;

		}

		
		int numOfObjects = tempShell.Count();
		i = 0;

		//print out all objects
		while (i < numOfObjects) {
			// prints out the description and results of all objects in tempShell
			System.out.println(tempShell.GetStatObject(i).GetDescription() + tempShell.GetStatObject(i).GetResult());
			i++;
		}

	}

	/**
	 * program to return the year, month day, hour or minite from the integer value
	 * given by the data file give it a 0 for min 1 for hour 2 for day 3 for month 4
	 * for year
	 */
	private static int Date(long temp, int type) {

		int min = (int) (temp % 100);
		temp /= 100;

		int hour = (int) (temp % 100);

		temp /= 100;

		int day = (int) (temp % 100);

		temp /= 100;

		int month = (int) (temp % 100);

		temp /= 100;

		int year = (int) temp;

		// switch to return the type of value wanted
		switch (type) {
		case 0:

			return min;
		case 1:

			return hour;

		case 2:

			return day;
		case 3:

			return month;
		case 4:

			return year;

		}

		return year;

	}

}
