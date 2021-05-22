import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(JUnitQuickcheck.class)
public class TreeQuickCheck {
	@Property
	public void test(int[] items) {
		var tree = new Tree<Integer>();
		for (var item : items) {
			tree.insert(item, item);
		}
		for (var item : items) {
			assertTrue(tree.get(item).isPresent());
		}
	}
}