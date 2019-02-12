package code;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffDecode {

	public static void main(String[] args) throws Exception {
		//String input_file_name = "data/recompressed.dat";
		//String output_file_name = "data/reuncompressed.txt";
		
		String input_file_name = "/Users/khalidsaleh/eclipse-workspace/Huffman Code/data/compressed.dat";
		String output_file_name = "/Users/khalidsaleh/eclipse-workspace/Huffman Code/data/decoded.txt";		
		
		FileInputStream fis = new FileInputStream(input_file_name);

		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

		List<SymbolWithCodeLength> symbols_with_length = new ArrayList<SymbolWithCodeLength>();

		// Read in huffman code lengths from input file and associate them with each symbol as a 
		// SymbolWithCodeLength object and add to the list symbols_with_length.
		
		// Then sort they symbols.
		
		for (int i = 0; i != 256; i++) {
			int length = bit_source.next(8);
			symbols_with_length.add(new SymbolWithCodeLength(i, length));
		}
		Collections.sort(symbols_with_length);

		// Now construct the canonical huffman tree

		HuffmanDecodeTree huff_tree = new HuffmanDecodeTree(symbols_with_length);

		//int num_symbols = 0;
		int num_symbols = bit_source.next(32);
		
		int[] symCount = new int[256];

		// Read in the next 32 bits from the input file  the number of symbols

		try {

			// Open up output file.
			
			FileOutputStream fos = new FileOutputStream(output_file_name);

			for (int i=0; i != num_symbols; i++) {
				// Decode next symbol using huff_tree and write out to file.
				int sDecoded = huff_tree.decode(bit_source);
				symCount[sDecoded]++;
				fos.write(sDecoded);
			}
			
			//double q4Entropy = 0;
			for (int i = 0; i != 256; i++) {
				Double probability = new Double(((double)symCount[i]/(double)num_symbols));
				
				if(symCount[i]>=0) System.out.println("probability of symbol at index " + i + " is " + probability.toString());
				
				//if(symCount[i]>=0) System.out.println(i + " has " + symCount[i] + " occurrences, probability " + probability.toString());
				//if(probability >0) q4Entropy+=((double)probability*(double)symbols_with_length.get(i).codeLength());
			}
			//System.out.println("Given entropy is " + q4Entropy);


			// Flush output and close files.
			
			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
