package code;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.OutputStreamBitSink;

public class HuffEncode {

	public static void main(String[] args) throws Exception {
		String input_file_name = "/Users/khalidsaleh/eclipse-workspace/Huffman Code/data/encoderInput.txt";
		String output_file_name = "/Users/khalidsaleh/eclipse-workspace/Huffman Code/data/encoderOutput";

		FileInputStream fis = new FileInputStream(input_file_name);

		int[] symbol_counts = new int[256];
		int num_symbols = 0;
		int [] list = new int[256];
		
		int next8Bits = fis.read();
		while (next8Bits != -1) {
			symbol_counts[next8Bits]++;
			num_symbols++;
			next8Bits = fis.read();
		}

		fis.close();
		
		double entropy = 0;
		double myEntropy = 0;

		
		int[] symbols = new int[256];
		for (int i=0; i<256; i++) {
			symbols[i] = i;
			
			
			Double probability = new Double((double)symbol_counts[i]/(double)num_symbols);
			if(probability>0) entropy+=((double)probability*(-1)*(Math.log((double)probability))/Math.log(2));
		}
		System.out.println("Theoretical entropy is  " + entropy);

		
		HuffmanEncoder encoder = new HuffmanEncoder(symbols, symbol_counts);
		
		FileOutputStream fos = new FileOutputStream(output_file_name);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);
		
		for (int i = 0; i != 256; i++) {
			Double probability = new Double((double)symbol_counts[i]/(double)num_symbols);
			if(probability > 0) {
				myEntropy+=((double)probability*(double)encoder.getCode(i).length());
			}
			bit_sink.write(encoder.getCode(i).length(), 8);
		}
		
		System.out.println("Entropy achieved by my encoder is  " + myEntropy);

		bit_sink.write(num_symbols, 32);
		
		fis = new FileInputStream(input_file_name);
		
		next8Bits = fis.read();
		while (next8Bits != -1) {
			encoder.encode(next8Bits, bit_sink);
			next8Bits = fis.read();
		}
		

		// Pad output to next word.
		bit_sink.padToWord();

		// Close files.
		fis.close();
		fos.close();
	}
}
