package code;

public class InternalHuffmanNode implements HuffmanNode {
	private HuffmanNode right;
	private HuffmanNode left;
	
	public InternalHuffmanNode(HuffmanNode l, HuffmanNode r) {
		left = l;
		right = r;
	}
	public InternalHuffmanNode() {
		left = null;
		right = null;
	}

	public int count() {
		return left.count() + right.count();
	}

	public boolean isLeaf() {
		return false;
	}

	public int symbol() {
		throw new RuntimeException("no symbol for internal nodes");
	}

	public int height() {
		return Math.max(right.height() + 1, left.height() + 1);
	}

	public boolean isFull() {
		if (left == null || right == null) {
			return false;
		} else {
			return right.isFull() && left.isFull();
		}
	}
	
	public boolean insertSymbol(int length, int symbol) throws Exception {
		if(left!=null) {
			if(!left.isFull()) {
				return left.insertSymbol(length-1,symbol);
			}else if(right!=null) {
				if(!right.isFull()) {
					return right.insertSymbol(length-1, symbol);
				}else {
					return false; 
				}
			}else {
				if(length==1) {
					right = new LeafHuffmanNode(symbol, 0);
					return true;
				}else {
					right = new InternalHuffmanNode();
					return right.insertSymbol(length-1, symbol);
				}
			}
		}else {
			if(length==1) {
				left = new LeafHuffmanNode(symbol, 0);
				return true; 
			}else {
				left = new InternalHuffmanNode();
				return left.insertSymbol(length-1, symbol);
			}
		}
	}

	/*public boolean insertSymbol(int length, int symbol) throws Exception {
		if (right != null) {
			if (!right.isFull()) {
				return right.insertSymbol(length - 1, symbol);
			} else if (left != null) {
				if (!left.isFull()) {
					return left.insertSymbol(length - 1, symbol);
				} else {
					return false;
				}
			} else {
				if (length == 1) {
					right = new LeafHuffmanNode(symbol, 0);
					return true;
				} else {
					right = new InternalHuffmanNode(null, null);
					return right.insertSymbol(length - 1, symbol);
				}
		} /*else {
			if (length == 1) {
				right = new LeafHuffmanNode(symbol, 0);
				return true;
			} else {
				right = new InternalHuffmanNode(null, null);
				return right.insertSymbol(length - 1, symbol);
			}*/
		/*} else {
			if (length == 1) {
				left = new LeafHuffmanNode(symbol, 0);
				return true;
			} else {
				left = new InternalHuffmanNode(null, null);
				return left.insertSymbol(length - 1, symbol);
			}
		}
	}*/

	public HuffmanNode left() {
		return left;
	}

	public HuffmanNode right() {
		return right;
	}

}
