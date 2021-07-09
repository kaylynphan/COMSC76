/*
 * Write a program that prompts the user for a String and returns all of the permutations of that string.
 * 
 * Kaylyn Phan
 * 18 Feb 2020
 * 
 */

package recursion;

import java.util.Scanner;

public class TestDisplayPermutation {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a string: ");
		String s = input.nextLine();
		displayPermutation(s);
		input.close();
	}
	
	public static void displayPermutation(String s) {
		displayPermutation("", s);
	}
	// Helper method
	public static void displayPermutation(String s1, String s2) {
		if (s2.length() != 0) {
			for (int index = 0; index < s2.length(); index++) {
				displayPermutation(s1 + s2.charAt(index), s2.substring(0, index) + s2.substring(index + 1));
			}
		// Base case: there are no more characters to permute
		} else { 
			System.out.println(s1);
		}
	}

}
