package sorting;
/* Write a program that shows the execution time of selection sort, merge sort, quick sort, heap sort, and radix sort for
 * input sizes of 50,000, 100,000, 150,000, 200,000, 250,000, and 300,000.
 * 
 * Kaylyn Phan
 * 2 April 2020
 */

import java.util.Random;

public class ExecutionTimeForSorting {
	public static void main(String[] args) {
		
		// print table
		System.out.println("Array size  |  Selection\tMerge\t\tQuick\tHeap\tRadix");
		System.out.println("-----------------------------------------------------------------------------");	
				
		Random random = new Random();
		// create 6 arrays of random integers w/ increasing size
		for (int i = 1; i < 7; i++) {
			int size = 50000 * i;
			int[] testArray = new int[size];
			for (int j = 0; j < size; j++) {
				testArray[j] = (int) Math.abs(random.nextInt());
			}
			System.out.printf("%,d\t|\t", size);
		
			
			//Selection Sort
			long startTime = System.nanoTime();
			selectionSort(testArray);
			long endTime = System.nanoTime();
			System.out.print(endTime - startTime + "\t");
			
			//Merge Sort
			startTime = System.nanoTime();
			mergeSort(testArray);
			endTime = System.nanoTime();
			System.out.print(endTime - startTime + "\t");
			
			//Quick Sort
			startTime = System.nanoTime();
			quickSort(testArray);
			endTime = System.nanoTime();
			System.out.print(endTime - startTime + "\t");
			
			// Heap Sort
			startTime = System.nanoTime();
			quickSort(testArray);
			endTime = System.nanoTime();
			System.out.print(endTime - startTime + "\t");
			
			//Radix Sort
			startTime = System.nanoTime();
			radixSort(testArray);
			endTime = System.nanoTime();
			System.out.println(endTime - startTime);
		}
		
		// for debugging
		/*int[] testArray = {3, 14, 12, 8, 6, 10, 1, 15, 32, 31, 3};
		heapSort(testArray);
		printArray(testArray);
		*/
	}
	
	public static void selectionSort(int[] list) {
		for (int i = 0; i < list.length; i++) {
			int currentMin = list[i];
			int minIndex = i;
			
			for (int j = i + 1; j < list.length; j++) {
				if (list[j] < currentMin) {
					minIndex = j;
					currentMin = list[j];
				}
			}
			
			//swap
			if (minIndex != i) {
				list[minIndex] = list[i];
				list[i] = currentMin;
			}
		}
	}
	
	public static void mergeSort(int[] list) {
		if (list.length < 2) {
			return;
		} else {
			int midIndex = list.length / 2;
			int[] list1 = new int[midIndex];
			int[] list2 = new int[list.length - midIndex];
			
			//fill list1 and list2
			for (int i = 0; i < midIndex; i++) {
				list1[i] = list[i];
			}
			for (int i = midIndex; i < list.length; i++) {
				list2[i - midIndex] = list[i];
			}
			mergeSort(list1);
			mergeSort(list2);
			
			//merge
			int[] sortedArray = new int[list.length];
			int i = 0; //index of sortedArray
			int j = 0; //index of list1
			int k = 0; //index of list2
			//copy competing elements
			while (j < list1.length && k < list2.length) {
				if (list1[j] < list2[k]) {
					sortedArray[i] = list1[j];
					j++;
				} else {
					sortedArray[i] = list2[k];
					k++;
				}
				i++;
			}
			//copy remaining elements
			while (j < list1.length) {
				sortedArray[i] = list1[j];
				j++;
				i++;
			}
			while (k < list2.length) {
				sortedArray[i] = list2[k];
				i++;
				k++;
			}
			return;
		}	
	}
	
	public static void quickSort(int[] list) {
		quickSort(list, 0, list.length - 1);
	}
	
	public static void quickSort(int[] list, int left, int right) {
		if (left >= right) {
			return;
		} 	
		int pivot = list[left];
		while (left <= right) {
			while (list[left] < pivot) {
				left++;
			}
			while (list[right] > pivot) {
				right--;
			}
			if (left <= right) {
				int temp = list[left];
				list[left] = list[right];
				list[right] = temp;
				left++;
				right--;
			}
		}
		int partition = left;
		
		quickSort(list, left, partition - 1);
		quickSort(list, partition, right);
	}
	
	public static <E> void heapSort(int[] list) {
		int n = list.length;
		for (int i = n / 2 - 1; i >= 0; i--) {
			heapify(list, n, i);
		}
		for(int i = n - 1; i > 0; i--) {
			int temp = list[i];
			list[i] = list[0];
			list[0] = temp;
			heapify(list, i, 0);
		}
	}
	
	public static void heapify(int[] list, int n, int i) {
		int biggest = i;
		int left = 2 * i + 1;
		int right = left + 1;
		
		if (left < n && list[left] > list[biggest]) {
			biggest = left;
		}
		if (right < n && list[right] > list[biggest]) {
			biggest = right;
		}
		if (biggest != i) {
			int temp = list[i];
			list[i] = list[biggest];
			list[biggest] = temp;
			heapify(list, n, biggest);
		}
	}
	
	public static void radixSort(int[] list) {
		//find maximum element in the list
		int max = list[0];
		for (int i = 1; i < list.length; i++) {
			if (list[i] > max) {
				max = list[i]; 
			}
		}
		//do a counting sort for every digit
		for (int digit = 1; max / digit > 0; digit *= 10) {
			countingSort(list, list.length, digit);
		}
	}
	
	public static void countingSort(int[] list, int n, int digit) {
		int[] result = new int[n];
		int i;
		int[] counter = new int[10];
		for (i = 0; i < n; i++) {
			counter[(list[i]/digit) % 10]++;
		}
		for (i = 1; i < 10; i++) {
			counter[i] += counter[i-1];
		}
		for (i = n-1; i >= 0; i--) {
			result[counter[(list[i] / digit) % 10] - 1] = list[i];
			counter[(list[i] / digit) % 10]--;
		}
		//copy the result into the initial array
		for (i = 0; i < n; i++) {
			list[i] = result[i];
		}
	}
	
	// just for debugging
	public static void printArray(int[] list) {
		System.out.print("[ ");
		for(int i: list) {
			System.out.print(i + " ");
		}
		System.out.println("]");
	}
	

}
