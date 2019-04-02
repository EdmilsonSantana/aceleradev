package challenge;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Quote {

	private String actor;
	private String quote;

	Quote() {

	}

	@JsonCreator
	public Quote(@JsonProperty("actor") String actor, @JsonProperty("quote") String quote) {
		this.actor = actor;
		this.quote = quote;
	}

	public String getActor() {
		return this.actor;
	}

	public String getQuote() {
		return this.quote;
	}

	@Override
	public String toString() {
		return "Quote [actor=" + actor + ", quote=" + quote + "]";
	}

}
