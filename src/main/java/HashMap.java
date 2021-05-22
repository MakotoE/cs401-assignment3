import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HashMap<K, V> {
	private class Entry {
		final K key;
		final V value;

		private Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private final ArrayList<Optional<Entry>> array;
	private int size;

	public HashMap(int capacity) {
		this.array = Stream.generate(Optional::<Entry>empty)
			.limit((capacity + 1) * 2L)
			.collect(Collectors.toCollection(ArrayList::new));
		this.size = 0;
	}

	public void insert(K key, V value) {
		int hash = key.hashCode();
		int i = 0;
		while (true) {
			int index = (hash + i * i) % array.size();
			var entry = array.get(index);
			if (entry.isEmpty()) {
				if (this.size >= this.array.size()) {
					throw new RuntimeException("size exceeds underlying array size");
				}

				array.set(index, Optional.of(new Entry(key, value)));
				++size;
				return;
			}

			if (entry.get().key.equals(key)) {
				array.set(index, Optional.of(new Entry(key, value)));
				return;
			}

			++i;
		}
	}

	public Optional<V> get(K key) {
		if (array.size() == 0) {
			return Optional.empty();
		}

		int hash = key.hashCode();
		int i = 0;
		while (true) {
			int index = (hash + i * i) % array.size();
			var entry = array.get(index);
			if (entry.isEmpty() || i == array.size() + 1) {
				return Optional.empty();
			}

			if (entry.get().key.equals(key)) {
				return Optional.of(entry.get().value);
			}

			++i;
		}
	}
}
