/*
 * Write a generic method that sorts an ArrayList of any comparable type. Write a program to test this method.
 * 
 * Kaylyn Phan
 * 25 Feb 2020
 * 
 */

package generics;

import java.util.ArrayList;

public class GenericSort {

	public static void main(String[] args) {
		ArrayList<Integer> intList = new ArrayList<Integer>();
			intList.add(2);
			intList.add(4);
			intList.add(3);
		ArrayList<Double> doubleList = new ArrayList<Double>();
			doubleList.add(3.4);
			doubleList.add(1.2);
			doubleList.add(-12.3);
		ArrayList<String> stringList = new ArrayList<String>();
			stringList.add("Bob");
			stringList.add("Alice");
			stringList.add("Ted");
			stringList.add("Carol");
		sort(intList);
		sort(doubleList);
		sort(stringList);
	}
	
	public static <E extends Comparable<E>> void sort(ArrayList<E> list) {
		E currentMin;
		int currentMinIndex;
		for (int i = 0; i < list.size(); i++) {
			currentMinIndex = i;
			currentMin = list.get(i);
			for (int j = i + 1; j < list.size(); j++) {
				if (currentMin.compareTo(list.get(j)) > 0) {
					currentMinIndex = j;
					currentMin = list.get(j);
				}
			}
			if (currentMinIndex != i) {
				list.set(currentMinIndex, list.get(i));
				list.set(i, currentMin);
			}
		}
		System.out.println(list);
	}
}
