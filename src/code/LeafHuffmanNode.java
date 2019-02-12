package code;

public class LeafHuffmanNode implements HuffmanNode {
	
	private int symbol;
	private int count;
	public LeafHuffmanNode(int s, int c) {
		symbol = s;
		count = c;
	}

	public int count() {
		return count;
	}

	public boolean isLeaf() {
		return true;
	}

	public int symbol() {
		return symbol;
	}

	public int height() {
		return 0;
	}

	public boolean isFull() {
		return true;
	}

	public boolean insertSymbol(int length, int symbol) {
		throw new RuntimeException("cannot insert for leaf");
	}

	public HuffmanNode left() {
		throw new RuntimeException("leaf node doesn't have any children");
	}

	public HuffmanNode right() {
		throw new RuntimeException("leaf node doesn't have any children");

	}

}
