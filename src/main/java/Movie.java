public class Movie {
	public int id;
	public String color; // This can be a constant
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
