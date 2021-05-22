import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TreeTest {
	@Test
	public void test() {
		var tree = new Tree<String>();
		assertEquals(Optional.empty(), tree.get("a"));
		tree.insert("a", 0);
		assertEquals(Optional.of(0), tree.get("a"));
		tree.insert("b", 1);
		assertEquals(Optional.of(1), tree.get("b"));
		tree.insert("c", 2);
		assertEquals(Optional.of(2), tree.get("c"));
		tree.insert("a", 3);
		assertEquals(Optional.of(3), tree.get("a"));
	}
}
