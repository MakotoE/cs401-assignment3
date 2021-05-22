import java.util.ArrayList;
import java.util.Optional;

public class HashMap {
	private static class Entry {
		final Integer key;
		final Integer value;

		private Entry(Integer key, Integer value) {
			this.key = key;
			this.value = value;
		}
	}

	private final ArrayList<Optional<Entry>> array;
	private int size;

	public HashMap(int capacity) {
		this.array = new ArrayList<>(capacity);
		this.size = 0;
	}

	public void insert(Integer key, Integer value) {
		if (this.size >= this.array.size() / 0.5) {
			throw new RuntimeException("size exceeds array size * load factor");
		}

		int hash = key.hashCode();
		int i = 0;
		while (true) {
			int index = (hash + i * i) % array.size();
			if (array.get(index).isEmpty() || array.get(index).get().key.equals(key)) {
				array.add(index, Optional.of(new Entry(key, value)));
				++size;
				return;
			}
			++i;
		}
	}

	public Optional<Integer> get(Integer key) {
		if (array.size() == 0) {
			return Optional.empty();
		}

		int hash = key.hashCode();
		int i = 0;
		while (true) {
			int index = (hash + i * i) % array.size();
			if (array.get(index).isEmpty() || array.get(index).get().key.equals(key)) {
				return Optional.of(array.get(index).get().value);
			}

			if (i == array.size()) {
				return Optional.empty();
			}

			++i;
		}
	}
}
