package jet.demo.faas.nlp;

import java.util.logging.Level;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.azure.core.credential.AzureKeyCredential;
import com.microsoft.azure.functions.ExecutionContext;

import jet.demo.faas.bo.SentimentResponse;

public class SentimentAnalyzer {
	public static TextAnalyticsClient authenticateClient(String key, String endpoint) {
		return new TextAnalyticsClientBuilder().credential(new AzureKeyCredential(key)).endpoint(endpoint)
				.buildClient();
	}

	public static SentimentResponse analyzeText(TextAnalyticsClient client, String text,
			final ExecutionContext context) {
		SentimentResponse res = new SentimentResponse();
		try {
			DocumentSentiment documentSentiment = client.analyzeSentiment(text);
			res.setResponse("Success");
			res.setResponseCode(1);
			res.setSentiment(documentSentiment.getSentiment().toString());
			res.setPositiveScore(documentSentiment.getConfidenceScores().getPositive());
			res.setNeutralScore(documentSentiment.getConfidenceScores().getNeutral());
			res.setNegativeScore(documentSentiment.getConfidenceScores().getNegative());
		} catch (Exception e) {
			res.setResponse("Failed");
			res.setResponseCode(-1);
			context.getLogger().log(Level.SEVERE, e.getLocalizedMessage());
		}
		return res;

	}
}
