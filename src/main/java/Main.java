import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	@SuppressWarnings({"unchecked", "OptionalGetWithoutIsPresent"})
	public static void main(String[] args) throws IOException {
		var file = Files.newBufferedReader(Path.of("movies.csv"));
		var movies = Parser.parse(file);

		var yearTree = new Tree<Integer>();
		var scoreTree = new Tree<Movie.Score>();
		var languageTree = new Tree<String>();
		var contentRatingTree = new Tree<String>();
		for (int i = 0; i < movies.length; ++i) {
			yearTree.insert(movies[i].year, i);
			scoreTree.insert(movies[i].score, i);
			languageTree.insert(movies[i].language, i);
			contentRatingTree.insert(movies[i].contentRating, i);
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
					var yearResult = ((Tree<Integer>)map.get("year").get())
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
					var scoreResult = ((Tree<Movie.Score>)map.get("score").get())
						.get(score.get());
				}

				System.out.print("Language: ");
				var languageInput = scanner.next();
				if (!languageInput.equals("-")) {
					var languageResult = ((Tree<String>)map.get("language").get())
						.get(languageInput);
				}

				System.out.print("Rating: ");
				var ratingInput = scanner.next();
				if (!ratingInput.equals("-")) {
					var languageResult = ((Tree<String>)map.get("rating").get())
						.get(ratingInput);
				}
			} catch (InputMismatchException ignored) {
				System.out.println("Invalid input");
				continue;
			}

		}
	}
}
