import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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
		//noinspection InfiniteLoopStatement
		while (true) {
			var matchingIndices = new ArrayList<Integer>();
			try {
				System.out.print("Year: ");
				var yearInput = scanner.next();
				if (!yearInput.equals("-")) {
					var yearResult =
						((Tree<Integer, ArrayList<Integer>>) map.get("year").get())
							.get(Integer.parseInt(yearInput));
					if (yearResult.isPresent()) {
						matchingIndices = yearResult.get();
					}
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
					if (scoreResult.isPresent()) {
						if (matchingIndices.isEmpty()) {
							matchingIndices = scoreResult.get();
						} else {
							matchingIndices.retainAll(scoreResult.get());
						}
					}
				}

				System.out.print("Language: ");
				var languageInput = scanner.next();
				if (!languageInput.equals("-")) {
					var languageResult =
						((Tree<String, ArrayList<Integer>>) map.get("language").get())
							.get(languageInput);
					if (languageResult.isPresent()) {
						if (matchingIndices.isEmpty()) {
							matchingIndices = languageResult.get();
						} else {
							matchingIndices.retainAll(languageResult.get());
						}
					}
				}

				System.out.print("Rating: ");
				var ratingInput = scanner.next();
				if (!ratingInput.equals("-")) {
					var ratingResult =
						((Tree<String, ArrayList<Integer>>) map.get("contentRating").get())
							.get(ratingInput);
					if (ratingResult.isPresent()) {
						if (matchingIndices.isEmpty()) {
							matchingIndices = ratingResult.get();
						} else {
							matchingIndices.retainAll(ratingResult.get());
						}
					}
				}

				System.out.println();
				System.out.print("Results (Movies -> ");
				if (!yearInput.equals("-")) {
					System.out.print("year:" + yearInput);
				}
				if (!scoreInput.equals("-")) {
					System.out.print("score:" + scoreInput);
				}
				if (!languageInput.equals("-")) {
					System.out.print("language:" + languageInput);
				}
				if (!ratingInput.equals("-")) {
					System.out.print("rating:" + ratingInput);
				}
				System.out.println(")");
			} catch (InputMismatchException ignored) {
				System.out.println("Invalid input");
				continue;
			}

			System.out.println(matchingIndices);
			for (var index : matchingIndices) {
				System.out.println("-----------------------------");
				System.out.println(movies[index]);
				System.out.println("-----------------------------");
				System.out.println();
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
