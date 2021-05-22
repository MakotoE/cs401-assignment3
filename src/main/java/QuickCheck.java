import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(JUnitQuickcheck.class)
public class QuickCheck {
	@Property
	public void hashMap(int[] items) {
		var map = new HashMap<Integer, Integer>(items.length);
		for (var item : items) {
			map.insert(item, item);
		}
		for (var item : items) {
			assertTrue(map.get(item).isPresent());
		}
	}

	@Property
	public void tree(int[] items) {
		var tree = new Tree<Integer>();
		for (var item : items) {
			tree.insert(item, item);
		}
		for (var item : items) {
			assertTrue(tree.get(item).isPresent());
		}
	}
}
