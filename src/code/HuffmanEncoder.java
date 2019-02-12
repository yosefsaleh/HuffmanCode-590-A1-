package code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.OutputStreamBitSink;

public class HuffmanEncoder {
	private Map<Integer, String> codeMap;
	private Map<Integer, String> cmap;
	
	public HuffmanEncoder(int[] symbols, int[] symbolsCount) throws Exception {
		assert symbols.length == symbolsCount.length;
		
		
		// Given symbols and their associated counts, create initial
		// Huffman tree. Use that tree to get code lengths associated
		// with each symbol. Create canonical tree using code lengths.
		// Use canonical tree to form codes as strings of 0 and 1
		// characters that are inserted into _code_map.

		// Start with an empty list of nodes
		
		List<HuffmanNode> nodeList = new ArrayList<HuffmanNode>();
		
		// Create a leaf node for each symbol, encapsulating the
		// frequency count information into each leaf.
		for (int i = 0; i != 256; i++) {
			nodeList.add(new LeafHuffmanNode(symbols[i], symbolsCount[i]));
		}

		// Sort the leaf nodes
		//nodeList.sort(null);
		Collections.sort(nodeList);


		// While you still have more than one node in your list...
		while(nodeList.size() > 1) {
			// Remove the two nodes associated with the smallest counts
			
			// Create a new internal node with those two nodes as children.
			
			// Add the new internal node back into the list
			
			// Resort
			HuffmanNode smallest = nodeList.remove(0);
			HuffmanNode smallest2 = nodeList.remove(0);
			
			InternalHuffmanNode node = new InternalHuffmanNode(smallest, smallest2);
			nodeList.add(node);
			
			Collections.sort(nodeList);
		}
		assert nodeList.size()==1;
		
		cmap = new HashMap<Integer, String>();


		// Create a temporary empty mapping between symbol values and their code strings
		//Map<Integer, String> cmap = new HashMap<Integer, String>();
		HuffmanNode root = nodeList.get(0);
		stringOfLeaf("", true, root);

		// Start at root and walk down to each leaf, forming code string along the
		// way (0 means left, 1 means right). Insert mapping between symbol value and
		// code string into cmap when each leaf is reached.
		
		// Create empty list of SymbolWithCodeLength objects
		List<SymbolWithCodeLength> sym_with_length = new ArrayList<SymbolWithCodeLength>();

		// For each symbol value, find code string in cmap and create new SymbolWithCodeLength
		// object as appropriate (i.e., using the length of the code string you found in cmap).
		for (int i = 0; i != 256; i++) {
			sym_with_length.add(new SymbolWithCodeLength(i, cmap.get(i).length()));
			
		}
		// Sort sym_with_lenght
		//sym_with_length.sort(null);
		Collections.sort(sym_with_length);

		// Now construct the canonical tree as you did in HuffmanDecodeTree constructor
		
		InternalHuffmanNode canonical_root = new InternalHuffmanNode();
		for (int i = 0; i != 256; i++) {
			canonical_root.insertSymbol(sym_with_length.get(i).codeLength(), sym_with_length.get(i).value());
		}
		

		// If all went well, tree should be full.
		assert canonical_root.isFull();
		
		// Create code map that encoder will use for encoding
		
		codeMap = new HashMap<Integer, String>();
		
		// Walk down canonical tree forming code strings as you did before and
		// insert into map.		
		stringOfLeaf("", false, canonical_root);
	}
	
	public String getCode(int symbol) {
		return codeMap.get(symbol);
	}

	public void encode(int symbol, OutputStreamBitSink bit_sink) throws IOException {
		bit_sink.write(codeMap.get(symbol));
	}
	public void stringOfLeaf(String s, boolean leaf, HuffmanNode root) throws Exception {
		if (!root.isLeaf()) {
			if (root.right() != null) {
				stringOfLeaf(s + "1", leaf, root.right());
			}
			if (root.left() != null) {
				stringOfLeaf(s + "0", leaf, root.left());
			}
		} else {
			if (leaf) {
				cmap.put(root.symbol(), s);
			} else {
				codeMap.put(root.symbol(), s);
			}
			}
			
		}
	}

