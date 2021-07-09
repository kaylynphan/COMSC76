/*
 * Write a method that uses recursion that counts how many times a specific character appears in an array of characters.
 * 
 * Kaylyn Phan
 * 27 Feb 2020
 */
package test1;

import java.util.Scanner;

public class CharacterCounter {

	public static void main(String[] args) {
		char[] test = {'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 't', 'h', 'e', ' ', 's', 't', 'r', 'i', 'n', 'g'};
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a character: ");
		char ch = input.next().charAt(0);
		System.out.println("The character " + ch + " occurs " + charCount(test, 0, ch) + " times.");
		input.close();
	}
	
	public static int charCount(char[] array, int start, char ch) {
		int count = 0;
		if (start < array.length) {
			if (array[start] == ch) {
				count++;
			}
			count += charCount(array, (start + 1), ch);
		} 
		return count;
	}
}
