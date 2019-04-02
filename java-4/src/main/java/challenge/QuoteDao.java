package challenge;

import java.util.Random;

public class QuoteDao {

	public Quote getQuote() {
		Integer count = Query.create("select count(*) from scripts", Integer.class).uniqueResult().orElse(0);

		Integer rowNumber = getRandomRowNumber(count);

		String sql = String.format("SELECT actor, detail as quote FROM scripts LIMIT %s,1 ", rowNumber);
		return Query.create(sql, Quote.class).uniqueResult().orElse(null);
	}

	public Quote getQuoteByActor(String actor) {
		Integer count = Query.create("select count(*) from scripts where actor = ?", Integer.class)
				.setParameter(1, actor).uniqueResult().orElse(0);

		Integer rowNumber = getRandomRowNumber(count);

		String sql = String.format("SELECT actor, detail as quote FROM scripts where actor = ? LIMIT %s,1", rowNumber);
		return Query.create(sql, Quote.class).setParameter(1, actor).uniqueResult().orElse(null);

	}

	private Integer getRandomRowNumber(Integer maxValue) {
		return new Random().nextInt(maxValue + 1);
	}

}
