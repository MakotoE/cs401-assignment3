import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
	public static void main(String[] args) throws IOException {
		var file = Files.newBufferedReader(Path.of("movies.csv"));
		var movies = Parser.parse(file);

	}
}
