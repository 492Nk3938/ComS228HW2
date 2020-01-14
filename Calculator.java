
package cs228hw2;

import java.util.*;

/**
 * @author nicholaskrabbenhoft Class that has a main methiod that takes in
 *         instructions from standard in and then apply postfix calculator rules
 *         to them
 */
public class Calculator {

	static Deque<Object> instrutions;

	public static void main(String[] args) throws Exception {

		// instructios
		// System.out.println("please input instructions or zxcvbnm,,./ to end the
		// instructions or close the input");

		Scanner scan = new Scanner(System.in);
		// Scanner scan = new Scanner("2 5 6 - +");

		// scan.useDelimiter("\s");
		// they are being pulled off backward on the to string methiod

		instrutions = new Deque228<>();

		// byte temp1 = Byte.parseByte("1");

		// System.out.println(temp1);

		while (scan.hasNext()) {
			String input = scan.next();
			// AmusingPreciseNumber number = new AmusingPreciseNumber(temp);
			Object toAdd;
			if (isNumber(input)) {
				toAdd = new AmusingPreciseNumber(input);

				instrutions.add(toAdd);

			} else {
				if (input.equals("zxcvbnm,,./")) {
					break;
				}

				toAdd = input;

				instrutions.add(toAdd);
			}

		}

		// System.out.println(instrutions.toString());

		try {
			System.out.println("I'll try and calculate that");
			System.out.println(calculate().toString());

		} catch (Exception e) {
			System.err.println("there was an error in calculating the number");
		}
		scan.close();
		// */
	}

	/**
	 * private methiod to see if a string contains any numbers
	 * 
	 * @param temp
	 * @return
	 */
	private static boolean isNumber(String temp) {

		for (int i = 0; i < temp.length(); i++) {

			if ("0123456789".contains(temp.charAt(i) + "")) {
				return true;

			}

		}

		return false;

	}

	/**
	 * Methiod that calculates all of the instructions held in the instructions
	 * deque acording to postfix calculator rules
	 * 
	 * @return asingle APN that is the results of the calculations
	 * @throws Exception this methiod will through an error if an invalid set of
	 *                   instructions was put in
	 */
	private static AmusingPreciseNumber calculate() throws Exception {

		AmusingPreciseNumber number1 = null;
		AmusingPreciseNumber number2 = null;
		String operation;

		Deque<Object> pastInstructions = new Deque228<>();

		// The remove is wrong
		// number1 = (AmusingPreciseNumber) instrutions.pop();

		while (!instrutions.isEmpty() || pastInstructions.size() > 1) {

			while (instrutions.peek() != null && instrutions.peek().getClass() != "".getClass()) {
				pastInstructions.push(instrutions.pop());
			}

			operation = (String) instrutions.pop();

			// This preforms the operation speciffied on number one
			if (operation.equals("abs")) {

				number1 = (AmusingPreciseNumber) pastInstructions.pop();
				number1.abs();
				pastInstructions.push(number1);

			} else if (operation.equals("neg")) {

				number1 = (AmusingPreciseNumber) pastInstructions.pop();
				number1.negate();
				pastInstructions.push(number1);

			} else if (operation.equals("+")) {
				number1 = (AmusingPreciseNumber) pastInstructions.pop();
				number2 = (AmusingPreciseNumber) pastInstructions.pop();
				number1.add(number2);
				pastInstructions.push(number1);

			} else if (operation.equals("-")) {
				number2 = (AmusingPreciseNumber) pastInstructions.pop();
				number1 = (AmusingPreciseNumber) pastInstructions.pop();

				number1.subtract(number2);
				pastInstructions.push(number1);

			} else {
				throw new Exception();
			}

		}

		return number1;

	}

}
