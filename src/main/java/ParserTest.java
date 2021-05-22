import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ParserTest {
	@Test
	public void parse() throws IOException {
		assertEquals(0, Parser.parse(new StringReader("")).length);
		var file = Files.newBufferedReader(Path.of("movies.csv"));
		assertEquals(5041, Parser.parse(file).length);
	}

	@Test
	public void readField() {
		assertArrayEquals(new String[]{""}, Parser.readLine(""));
		assertArrayEquals(new String[]{"a"}, Parser.readLine("a"));
		assertArrayEquals(new String[]{"", ""}, Parser.readLine(","));
		assertArrayEquals(new String[]{"a", "b"}, Parser.readLine("a,b"));
		assertArrayEquals(new String[]{","}, Parser.readLine("\",\""));
		var s = "1,Color,Avatar ,178,James Cameron,CCH Pounder,Joel David Moore,Wes Studi,http://www.imdb.com/title/tt0499549/?ref_=fn_tt_tt_1,English,USA,PG-13,2009,7.9";
		assertEquals(14, Parser.readLine(s).length);
		s = "63,Color,\"The Chronicles of Narnia: The Lion, the Witch and the Wardrobe \",150,Andrew Adamson,Jim Broadbent,Kiran Shah,Shane Rangi,http://www.imdb.com/title/tt0363771/?ref_=fn_tt_tt_1,English,USA,PG,2005,6.9";
		assertEquals(14, Parser.readLine(s).length);
	}
}
