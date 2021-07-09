/*
 * Compress a source file into a target file using the Huffman Coding method
 * Pass the files from the command line using java Compress_a_File sourceFile.txt compressedFile.txt
 * 
 * Kaylyn Phan
 * 20 May 2020
 */

package programmingProject;

import java.io.*;

import java.util.ArrayList;

public class Compress_a_File {
	public static void main(String[] args) throws IOException {
		//read bytes from a source file into an array of bytes
		FileInputStream inputStream = new FileInputStream(new File(args[0]));
		byte[] byteArray = new byte[inputStream.available()];
		inputStream.read(byteArray);
		inputStream.close();
		
		//convert from byte array to char array
		char[] charArray = (new String(byteArray)).toCharArray();
		
		//create an array of character frequencies
		int[] freq = frequencies(charArray);
		//create a Huffman Tree
		Tree huffmanTree = getHuffmanTree(freq);
		//create a table of codes
		String[] codes = getCode(huffmanTree.root);
		
		//encode characters into a string of bits
		String bitString = "";
		for (int i = 0; i < charArray.length; i++) {
		bitString += (codes[charArray[i]]);
		}
		 
		//Output Huffman Codes using ObjectOutputStream
		ObjectOutputStream codesOutput = new ObjectOutputStream(new FileOutputStream(args[1]));
		codesOutput.writeObject(codes);
		codesOutput.writeInt(bitString.length());
		codesOutput.close();

		//Output encoded bits using BitOutputStream
		BitOutputStream output = new BitOutputStream(new File(args[1]), true);
		output.writeBit(bitString);
		output.close();
	}
	
	public static int[] frequencies(char[] input) {
		int[] freq = new int[256];
		for (int i = 0; i < input.length; i++) {
			freq[input[i]]++;
		}
		return freq;
	}
	
	public static Tree getHuffmanTree(int[] freq) {
		Heap<Tree> heap = new Heap<>();
		for (int i = 0; i < freq.length; i++) {
			if (freq[i] > 0) {
				//add a leaf node tree to the heap
				heap.add(new Tree(freq[i], (char)i));
			}
		}
		//combine the smallest weight trees together until the heap of trees becomes a single tree
		while (heap.getSize() > 1) {
			Tree t1 = heap.remove();
			Tree t2 = heap.remove();
			heap.add(new Tree(t1, t2));
		}
		return heap.remove();
	}
	
	public static String[] getCode(Tree.Node root) {
		if (root == null) {
			return null;
		}
		String[] codes = new String[256];
		assignCode(root, codes);
		return codes;
	}
	
	private static void assignCode(Tree.Node root, String[] codes) {
		if (root.left != null) {
			root.left.code = root.code + "0";
			assignCode(root.left, codes);
			
			root.right.code = root.code + "1";
			assignCode(root.right, codes);
		} else {
			codes[(int)root.element] = root.code; 
		}
	}
}

//Huffman Coding tree
class Tree implements Comparable<Tree> {
	Node root;
	
	public Tree(Tree t1, Tree t2) {
		root = new Node();
		root.left = t1.root;
		root.right = t2.root;
		root.weight = t1.root.weight + t2.root.weight;
	}
	
	public Tree(int weight, char element) {
		root = new Node(weight, element) ;
	}
	
	@Override
	public int compareTo(Tree t) {
		if (root.weight < t.root.weight) {
			return 1;
		} else if (root.weight == t.root.weight) {
			return 0;
		} else {
			return -1;
		}
	}
	
	public class Node {
		char element ;
		int weight;
		Node left;
		Node right;
		String code = "";
		
		public Node() {
		}
		
		public Node(int weight, char element) {
			this.weight = weight;
			this.element = element;
		}
	}
}

//Heap class
class Heap<E extends Comparable<E>> {
	private ArrayList<E> list = new ArrayList<E>();
	
	public Heap() {
	}
	
	public Heap(E[] objects) {
		for (int i = 0; i < objects.length; i++) {
			add(objects[i]);
		}
	}
	
	public void add(E newObject) {
		list.add(newObject);
		int currentIndex = list.size() - 1;
		
		while (currentIndex > 0) {
			int parentIndex = (currentIndex - 1) / 2;
			if (list.get(currentIndex).compareTo(list.get(parentIndex)) > 0) {
				E temp = list.get(currentIndex);
				list.set(currentIndex, list.get(parentIndex));
				list.set(parentIndex,  temp);
			} else {
				break;
			}
			currentIndex = parentIndex;
		}
	}
	
	public E remove() {
		if (list.size() == 0) {
			return null;
		}
		E removedObject = list.get(0);
		list.set(0,  list.get(list.size() - 1));
		list.remove(list.size() - 1);
		
		int currentIndex = 0;
		while (currentIndex < list.size()) {
			int leftChildIndex = 2 * currentIndex + 1;
			int rightChildIndex = 2 * currentIndex + 2;
			
			if (leftChildIndex >= list.size()) {
				break;
			}
			int maxIndex = leftChildIndex;
			if (rightChildIndex < list.size()) {
				if (list.get(maxIndex).compareTo(list.get(rightChildIndex)) < 0) {
					maxIndex = rightChildIndex;
				}
			}
			if (list.get(currentIndex).compareTo(list.get(maxIndex)) < 0) {
				E temp = list.get(maxIndex);
				list.set(maxIndex,  list.get(currentIndex));
				list.set(currentIndex, temp);
				currentIndex = maxIndex;
			} else {
				break;
			}
		}
		return removedObject;
	}
	
	public int getSize() {
		return list.size();
	}
}

//BitOutputStream object has a FileOutputStream, value (byte that is filled and reset), count (counts up to when byte is full)
class BitOutputStream {	
	FileOutputStream output;
	int value = 0;
	int count = 0;
	
	// construct a FileOutputStream given a file name
	public BitOutputStream(File file) throws IOException {
		output = new FileOutputStream(file);	
	}

	public BitOutputStream(File file, boolean append) throws IOException {
		output = new FileOutputStream(file, append);	
	}
	
	// write a single bit into value, check if byte is full
	public void writeBit(char bitChar) throws IOException {
		count++;
		value <<= 1;
		if (bitChar == '1') {
			value = value | 1;
		}
		if (count == 8) {
			output.write(value);
			value = 0;
			count = 0;
		}
	}
	
	// write a string of 8 bits (byte)
	public void writeBit(String bitString) throws IOException {
		for (int i = 0; i < bitString.length(); i++) {
			writeBit(bitString.charAt(i));
		}
	}
	
	// method to close the output stream
	public void close() throws IOException {
		if (count > 0 && count < 8) {
			//push forward bits by amount needed to make 8
			value <<= (8 - count);
			output.write(value);
		}
	}
}