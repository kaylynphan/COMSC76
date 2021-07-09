/*
 * Edit Listing 21.9 to read from a text file, passed as a command-line argument. Count words in a case sensitive fashion, 
 * and delimited by whitespace, punctuation, quotation parks, parentheses.
 * Display all words in alphabetical order along with the number of times it occurs.
 * 
 * Kaylyn Phan
 * 17 March 2020
 * 
 */

package setsAndMaps;

import java.util.*;
import java.io.*;
import java.util.Scanner;

public class CountOccurrenceOfWords {
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1) {
			System.out.println("Please type: java CountOccurenceOfWords fileName");
			System.exit(1);
		}
		File file = new File(args[0]);
		Scanner input = new Scanner(file);
		String text = "";
		while (input.hasNextLine()) {
			text = text + input.nextLine();
		}
		input.close();
		
		// create a tree map that holds words as key and occurrence count as value
		TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
		
		// split text by anything that isn't a letter a-z
		String[] words = text.split("[^a-zA-Z]");
		for (int i = 0; i < words.length; i++) {
			String key = words[i].toLowerCase();
			if(key.length() > 0) {
				if (!treeMap.containsKey(key)) {
					treeMap.put(key, 1);
				}
				else {
					int value = treeMap.get(key);
					value++;
					treeMap.put(key, value);
				}
			}
		}
		// print each key and value
		treeMap.forEach((k, v) -> {
			System.out.println(k + "\t" + v);	
		});
	}
}

