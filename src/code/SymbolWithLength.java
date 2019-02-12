package code;

/* SymbolWithCodeLength
 * 
 * Class that encapsulates a symbol value along with the length of the code
 * associated with that symbol. Used to build the canonical huffman tree.
 * Implements Comparable in order to sort first by code length and then by symbol value.
 */

public class SymbolWithCodeLength implements Comparable<SymbolWithCodeLength> {
	
	// Instance fields should be declared here.
	private int value;
	private int codeLength;
	
	// Constructor
	public SymbolWithCodeLength(int _value, int code_length) {
		value = _value;
		codeLength = code_length;
	}

	// codeLength() should return the code length associated with this symbol
	public int codeLength() {
		return this.codeLength;
		// Needs implementation
	}

	// value() returns the symbol value of the symbol
	public int value() {
		return this.value;
		// Needs implementation
	}

	// compareTo implements the Comparable interface
	// First compare by code length and then by symbol value.
	/*public int compareTo(SymbolWithCodeLength other) {
		// Needs implementation
		
	}*/
	public int compareTo(SymbolWithCodeLength other) {
		if(this.codeLength < other.codeLength()) {
			return -1;
		} else if (this.codeLength > other.codeLength()) {
			return 1;
		}
		if (this.codeLength == other.codeLength()) {
			if (this.value < other.value()) {
				return -1;
			} else if (this.value > other.value()) {
				return 1;
			} else {
				return 0;
			}
		}
		return 1;
	}
}
