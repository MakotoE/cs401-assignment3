import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class HashMapTest {
	@Test
	public void test() {
		{
			var map = new HashMap<Integer, String>(0);
			assertTrue(map.get(0).isEmpty());
			map.insert(0, "a");
			map.insert(0, "b");
			map.insert(1, "c");
			assertThrows(RuntimeException.class, () -> map.insert(2, "d"));
		}
		{
			var map = new HashMap<Integer, String>(1);
			map.insert(0, "a");
			assertEquals(Optional.of("a"), map.get(0));
		}
		{
			var map = new HashMap<Integer, String>(4);
			map.insert(0, "a");
			map.insert(1, "b");
			map.insert(2, "c");
			map.insert(1, "d");
			map.insert(10, "e");
			assertEquals(Optional.of("a"), map.get(0));
			assertEquals(Optional.of("d"), map.get(1));
			assertEquals(Optional.of("c"), map.get(2));
			assertEquals(Optional.of("e"), map.get(10));
		}
	}
}
