package jet.demo.faas.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SentimentResponse {
	private String response;
	private int responseCode;
	private String text;
	private String sentiment;
	double positiveScore, neutralScore, negativeScore;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getPositiveScore() {
		return positiveScore;
	}

	public void setPositiveScore(double positiveScore) {
		this.positiveScore = positiveScore;
	}

	public double getNeutralScore() {
		return neutralScore;
	}

	public void setNeutralScore(double neutralScore) {
		this.neutralScore = neutralScore;
	}

	public double getNegativeScore() {
		return negativeScore;
	}

	public void setNegativeScore(double negativeScore) {
		this.negativeScore = negativeScore;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

}
