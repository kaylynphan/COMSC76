/* 
 * Create a class named BitOutputStream that writes a stream of bits into a file and a test class 
 * that implements BitOutputStream.
 * 
 * Kaylyn Phan
 * 10 Feb 2020
 * 
 */

package fileIO;

import java.io.*;

// Test program that writes String of bits into document testOutput.dat
public class TestBitOutputStream {
	public static void main(String[] args) throws IOException {
		BitOutputStream outputStream = new BitOutputStream(new File("testOutput.dat"));
		outputStream.writeBit("010000100100001001101");
		outputStream.close();
	}
}

// BitOutputStream object has a FileOutputStream, value (byte that is filled and reset), count (counts up to when byte is full)
class BitOutputStream {	
	FileOutputStream output;
	int value = 0;
	int count = 0;
	
	// construct a FileOutputStream given a file name
	public BitOutputStream(File file) throws IOException {
		output = new FileOutputStream(file);	
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