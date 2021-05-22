import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(JUnitQuickcheck.class)
public class HashMapQuickCheck {
	@Property
	public void test(int[] items) {
		var map = new HashMap<Integer, Integer>(items.length);
		for (var item : items) {
			map.insert(item, item);
		}
		for (var item : items) {
			assertTrue(map.get(item).isPresent());
		}
	}
}
