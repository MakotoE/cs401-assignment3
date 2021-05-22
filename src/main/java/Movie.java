import java.util.Optional;

public class Movie {
	public int id;
	public String color;
	public String movieTitle;
	public int duration;
	public String directorName;
	public String actor1Name;
	public String actor2Name;
	public String actor3Name;
	public String movieIMDBLink;
	public String language;
	public String country;
	public String contentRating;
	public int year;
	public Score score;

	public Movie(
		int id,
		String color,
		String movieTitle,
		int duration,
		String directorName,
		String actor1Name,
		String actor2Name,
		String actor3Name,
		String movieIMDBLink,
		String language,
		String country,
		String contentRating,
		int year,
		Score score
	) {
		this.id = id;
		this.color = color;
		this.movieTitle = movieTitle;
		this.duration = duration;
		this.directorName = directorName;
		this.actor1Name = actor1Name;
		this.actor2Name = actor2Name;
		this.actor3Name = actor3Name;
		this.movieIMDBLink = movieIMDBLink;
		this.language = language;
		this.country = country;
		this.contentRating = contentRating;
		this.year = year;
		this.score = score;
	}

	@Override
	public String toString() {
		return
			"id:" + id +
			"\ncolor:" + color +
			"\nmovieTitle:" + movieTitle +
			"\nduration:" + duration +
			"\ndirector_name:" + directorName +
			"\nact1:" + actor1Name +
			"\nact2:" + actor2Name +
			"\nact3:" + actor3Name +
			"\nmovie_imdb_link:" + movieIMDBLink +
			"\nlanguage:" + language +
			"\ncountry:" + country +
			"\ncontent_rating:" + contentRating +
			"\ntitle_year:" + year +
			"\nimdb_score:" + score;
	}

	public static class Score implements Comparable<Score> {
		private final int n;

		public Score(int n) {
			this.n = n;
		}

		public static Optional<Score> parse(String s) {
			try {
				return Optional.of(
					new Score((int)(Float.parseFloat(s) * 10))
				);
			} catch (NumberFormatException ignored) {
				return Optional.empty();
			}
		}

		@Override
		public String toString() {
			return Float.toString((float)n / 10);
		}

		@Override
		public int compareTo(Score o) {
			return Integer.compare(n, o.n);
		}
	}
}
