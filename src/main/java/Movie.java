public class Movie {
	public int id;
	public String color;
	public String movieTitle;
	public int duration;
	public String directoryName;
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
		String directoryName,
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
		this.directoryName = directoryName;
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

	public static class Score {
		private final int n;

		private Score(int n) {
			this.n = n;
		}

		public static Score parse(String s) {
			return new Score(Integer.parseInt(s.replaceAll("\\.", "")));
		}

		@Override
		public String toString() {
			return Float.toString((float)n / 10);
		}
	}
}
