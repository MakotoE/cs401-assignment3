import java.util.Optional;

/**
 * Red-black tree implementation.
 * @param <K> key type
 * @param <V> value type
 */
public class Tree<K extends Comparable<K>, V> {
	private enum Color {
		RED,
		BLACK;

		Color negate() {
			if (this == RED) {
				return BLACK;
			}

			return RED;
		}
	}

	private class Node {
		final K key;
		V value;
		Node left;
		Node right;
		Color color;

		Node(K key, V value, Color color) {
			this.key = key;
			this.value = value;
			this.left = null;
			this.right = null;
			this.color = color;
		}
	}

	private Node root;

	/**
	 * @return Value associated with key if it exists
	 */
	public Optional<V> get(K key) {
		Node curr = root;
		while (curr != null) {
			int cmp = key.compareTo(curr.key);
			if (cmp < 0) {
				curr = curr.left;
			} else if (cmp > 0) {
				curr = curr.right;
			} else {
				return Optional.of(curr.value);
			}
		}
		return Optional.empty();
	}

	/**
	 * Inserts key and value to this tree.
	 */
	public void insert(K key, V value) {
		root = insert(root, key, value);
		root.color = Color.BLACK;
	}

	private Node insert(Node root, K key, V value) {
		if (root == null) {
			return new Node(key, value, Color.RED);
		}

		int cmp = key.compareTo(root.key);
		if (cmp < 0) {
			root.left = insert(root.left, key, value);
		} else if (cmp > 0) {
			root.right = insert(root.right, key, value);
		} else {
			root.value = value;
		}

		if (isRed(root.right) && !isRed(root.left)) {
			root = rotateLeft(root);
		}
		if (isRed(root.left) && isRed(root.left.left)) {
			root = rotateRight(root);
		}
		if (isRed(root.left) && isRed(root.right)) {
			flipColors(root);
		}

		return root;
	}

	private boolean isRed(Node node) {
		return node != null && node.color == Color.RED;
	}

	private Node rotateRight(Node node) {
		Node newRoot = node.left;
		node.left = newRoot.right;
		newRoot.right = node;
		newRoot.color = newRoot.right.color;
		newRoot.right.color = Color.RED;
		return newRoot;
	}

	private Node rotateLeft(Node node) {
		Node newRoot = node.right;
		node.right = newRoot.left;
		newRoot.left = node;
		newRoot.color = newRoot.left.color;
		newRoot.left.color = Color.RED;
		return newRoot;
	}

	private void flipColors(Node node) {
		node.color = node.color.negate();
		node.left.color = node.left.color.negate();
		node.right.color = node.right.color.negate();
	}
}
