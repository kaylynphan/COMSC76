/*
 * Decompress a file previously compressed so that it replicates the orginal source code from
 * Compress_a_File.java
 * Pass the files from the command line using java Decompress_a_File compressedFile.txt decompressedFile.txt
 * 
 * Kaylyn Phan
 * 20 May 2020
 */

package programmingProject;

import java.io.*;

public class Decompress_a_File {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//create an ObjectInputStream to read Huffman codes
		FileInputStream input = new FileInputStream(args[0]);
		ObjectInputStream inputCodes = new ObjectInputStream(input);
		String[] codes = (String[])(inputCodes.readObject()); 
		int size = inputCodes.readInt();
		
		//use FileInputStream to read bits
		int current = 0;
		StringBuilder text = new StringBuilder("");
		while (current != -1) {
		  current = input.read();
		  text.append(getBits(current));
		}
		input.close();
		text.delete(size, text.length());
		
		//decode the bits
		String decodedText = decodeText(text, codes); 
		
		//output the decoded text with FileOutputStream
		FileOutputStream output = new FileOutputStream(args[1]);
		output.write(decodedText.getBytes());
		output.close();
	}
	
	public static String decodeText(StringBuilder bitString, String[] codes) {
		String decodedText = "";
		while (bitString.length() != 0) {
			for (int i = 0; i < 256; i++) {
				if ((codes[i] != null) && (bitString.indexOf(codes[i]) == 0)) {
					decodedText+=(char)i;
					bitString.delete(0, codes[i].length());
				}
			}
		}
		return decodedText;
	}
	
	public static String getBits(int value) {	
		value = value % 256;
		String bit = "";
		int i = 0;
		int temp = value >> i;
		for (int j = 0; j < 8; j++) {
			bit = (temp & 1) + bit;
			i++;
			temp = value >> i;
		} 
		return bit;
	}	
	
}


