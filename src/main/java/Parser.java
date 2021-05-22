import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
	public static Movie[] parse(Readable file) {
		var scanner = new Scanner(file);
		scanner.useDelimiter("\\R");
		// Skip header
		if (scanner.hasNext()) {
			scanner.next();
		}

		var movies = new ArrayList<Movie>();
		var line = 2;

		while (scanner.hasNext()) {
			var fields = readLine(scanner.next());
			if (fields.length != 14) {
				throw new RuntimeException(
					"unexpected field count: " + fields.length + " at line: " + line
				);
			}

			var movie = new Movie(
				Integer.parseInt(fields[0]),
				parseColor(fields[1]),
				fields[2],
				parseInt(fields[3]),
				fields[4],
				fields[5],
				fields[6],
				fields[7],
				fields[8],
				fields[9],
				fields[10],
				fields[11],
				parseInt(fields[12]),
				Movie.Score.parse(fields[13])
			);
			movies.add(movie);
			++line;
		}

		return movies.toArray(Movie[]::new);
	}

	private static final String COLOR = "Color";

	private static String parseColor(String s) {
		if (s.equals(COLOR)) {
			return COLOR;
		}
		return s;
	}

	public static String[] readLine(String line) {
		var fields = new ArrayList<String>(14);
		boolean insideQuotes = false;
		var sb = new StringBuilder();
		for (int index = 0; index < line.length(); ++index) {
			switch (line.charAt(index)) {
			case ',' -> {
				if (insideQuotes) {
					sb.append(line.charAt(index));
				} else {
					fields.add(sb.toString());
					sb.setLength(0);
				}
			}
			case '"' -> {
				insideQuotes = !insideQuotes;
			}
			default -> sb.append(line.charAt(index));
			}
		}

		fields.add(sb.toString());
		return fields.toArray(String[]::new);
	}

	private static int parseInt(String s) {
		if (s.length() > 0) {
			return Integer.parseInt(s);
		}

		return 0;
	}
}
