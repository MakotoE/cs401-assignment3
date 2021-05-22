import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
	@SuppressWarnings({"unchecked", "OptionalGetWithoutIsPresent"})
	public static void main(String[] args) throws IOException {
		var file = Files.newBufferedReader(Path.of("movies.csv"));
		var movies = Parser.parse(file);

		var yearTree = new Tree<Integer, ArrayList<Integer>>();
		var scoreTree = new Tree<Movie.Score, ArrayList<Integer>>();
		var languageTree = new Tree<String, ArrayList<Integer>>();
		var contentRatingTree = new Tree<String, ArrayList<Integer>>();
		for (int i = 0; i < movies.length; ++i) {
			getOrDefault(yearTree, movies[i].year).add(i);
			getOrDefault(scoreTree, movies[i].score).add(i);
			getOrDefault(languageTree, movies[i].language).add(i);
			getOrDefault(contentRatingTree, movies[i].contentRating).add(i);
		}

		var map = new HashMap<String, Object>(4);
		map.insert("year", yearTree);
		map.insert("score", scoreTree);
		map.insert("language", languageTree);
		map.insert("contentRating", contentRatingTree);

		var scanner = new Scanner(System.in);
		while (true) {
			try {
				System.out.print("Year: ");
				var yearInput = scanner.next();
				if (!yearInput.equals("-")) {
					var yearResult =
						((Tree<Integer, ArrayList<Integer>>) map.get("year").get())
							.get(Integer.parseInt(yearInput));
				}

				System.out.print("Score: ");
				var scoreInput = scanner.next();
				if (!scoreInput.equals("-")) {
					var score = Movie.Score.parse(scoreInput);
					if (score.isEmpty()) {
						System.out.println("Invalid input");
						continue;
					}
					var scoreResult =
						((Tree<Movie.Score, ArrayList<Integer>>) map.get("score").get())
							.get(score.get());
				}

				System.out.print("Language: ");
				var languageInput = scanner.next();
				if (!languageInput.equals("-")) {
					var languageResult =
						((Tree<String, ArrayList<Integer>>) map.get("language").get())
							.get(languageInput);
				}

				System.out.print("Rating: ");
				var ratingInput = scanner.next();
				if (!ratingInput.equals("-")) {
					var languageResult =
						((Tree<String, ArrayList<Integer>>) map.get("rating").get())
							.get(ratingInput);
				}
			} catch (InputMismatchException ignored) {
				System.out.println("Invalid input");
				continue;
			}

		}
	}

	static <K extends Comparable<K>> ArrayList<Integer>
	getOrDefault(Tree<K, ArrayList<Integer>> tree, K key) {
		var result = tree.get(key);
		if (result.isEmpty()) {
			var array = new ArrayList<Integer>();
			tree.insert(key, array);
			return array;
		}
		return result.get();
	}
}
