import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
	/**
	 * Prompts for search strings of various fields and returns movies that match.
	 */
	@SuppressWarnings({"unchecked", "OptionalGetWithoutIsPresent"})
	public static void main(String[] args) throws IOException {
		var file = Files.newBufferedReader(Path.of("movies.csv"));
		var movies = Parser.parse(file);

		// Index movies by field
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

		final String ignoreCommand = "-";

		// Input search strings
		var scanner = new Scanner(System.in);
		var matchingIndices = new ArrayList<Integer>();
		String yearInput;
		while (true) {
			System.out.print("Year: ");
			yearInput = scanner.next();
			if (!yearInput.equals(ignoreCommand)) {
				try {
					var yearResult =
						((Tree<Integer, ArrayList<Integer>>) map.get("year").get())
							.get(Integer.parseInt(yearInput));
					if (yearResult.isPresent()) {
						matchingIndices = yearResult.get();
					}
				} catch (NumberFormatException ignored) {
					System.out.println("Invalid input");
					continue;
				}
			}
			break;
		}

		String scoreInput;
		while (true) {
			System.out.print("Score: ");
			scoreInput = scanner.next();
			if (!scoreInput.equals(ignoreCommand)) {
				var score = Movie.Score.parse(scoreInput);
				if (score.isEmpty()) {
					System.out.println("Invalid input");
					continue;
				}

				try {
					var scoreResult =
						((Tree<Movie.Score, ArrayList<Integer>>) map.get("score").get())
							.get(score.get());
					if (scoreResult.isPresent()) {
						if (matchingIndices.isEmpty()) {
							matchingIndices = scoreResult.get();
						} else {
							matchingIndices.retainAll(scoreResult.get());
						}
					} else {
						matchingIndices.clear();
					}
				} catch (NumberFormatException ignored) {
					System.out.println("Invalid input");
					continue;
				}
			}
			break;
		}

		String languageInput;
		while (true) {
			System.out.print("Language: ");
			languageInput = scanner.next();
			if (!languageInput.equals(ignoreCommand)) {
				try {
					var languageResult =
						((Tree<String, ArrayList<Integer>>) map.get("language").get())
							.get(languageInput);
					if (languageResult.isPresent()) {
						if (matchingIndices.isEmpty()) {
							matchingIndices = languageResult.get();
						} else {
							matchingIndices.retainAll(languageResult.get());
						}
					} else {
						matchingIndices.clear();
					}
				} catch (NumberFormatException ignored) {
					System.out.println("Invalid input");
					continue;
				}
			}
			break;
		}

		String ratingInput;
		while (true) {
			System.out.print("Rating: ");
			ratingInput = scanner.next();
			if (!ratingInput.equals(ignoreCommand)) {
				try {
					var ratingResult =
						((Tree<String, ArrayList<Integer>>) map.get("contentRating").get())
							.get(ratingInput);
					if (ratingResult.isPresent()) {
						if (matchingIndices.isEmpty()) {
							matchingIndices = ratingResult.get();
						} else {
							matchingIndices.retainAll(ratingResult.get());
						}
					} else {
						matchingIndices.clear();
					}
				} catch (NumberFormatException ignored) {
					System.out.println("Invalid input");
					continue;
				}
			}
			break;
		}

		System.out.println();
		System.out.print("Results (Movies ->");
		if (!yearInput.equals(ignoreCommand)) {
			System.out.print(" year:" + yearInput);
		}
		if (!scoreInput.equals(ignoreCommand)) {
			System.out.print(" score:" + scoreInput);
		}
		if (!languageInput.equals(ignoreCommand)) {
			System.out.print(" language:" + languageInput);
		}
		if (!ratingInput.equals(ignoreCommand)) {
			System.out.print(" rating:" + ratingInput);
		}
		System.out.println(")");

		if (
			yearInput.equals(ignoreCommand)
				&& scoreInput.equals(ignoreCommand)
				&& languageInput.equals(ignoreCommand)
				&& ratingInput.equals(ignoreCommand)
		) {
			// All categories were ignored
			matchingIndices = IntStream.range(0, movies.length)
				.boxed()
				.collect(Collectors.toCollection(ArrayList::new));
		}

		// Print IDs
		System.out.println(
			matchingIndices.stream().map(n -> n + 1).collect(Collectors.toList())
		);

		// Print matched movies
		for (var index : matchingIndices) {
			System.out.println("-----------------------------");
			System.out.println(movies[index]);
			System.out.println("-----------------------------");
			System.out.println();
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
