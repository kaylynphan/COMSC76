/*
 * Write a program that prompts the user to enter a String and, using recursion, returns the String printed in reverse order.
 * 
 * Kaylyn Phan
 * 18 Feb 2020
 * 
 */

package recursion;

import java.util.Scanner;

public class TestReverseDisplay {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a string: ");
		String value = input.nextLine();
		reverseDisplay(value);
		input.close();
	}
	
	public static void reverseDisplay(String value) {
		reverseDisplay(value, value.length() - 1);
	}
	// Helper method
	public static void reverseDisplay(String value, int high) {
		// Base case: when high = -1, the recursion stops
		if (high >= 0) {
			System.out.print(value.charAt(high));
			high--;
			reverseDisplay(value, high);
		}
	}
}
