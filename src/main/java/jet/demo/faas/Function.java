package jet.demo.faas;

import java.util.Optional;
import java.util.logging.Level;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import jet.demo.faas.bo.SentimentRequest;
import jet.demo.faas.bo.SentimentResponse;
import jet.demo.faas.nlp.SentimentAnalyzer;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {

	@FunctionName("DetectSentiment")
	public HttpResponseMessage detectSentiment(@HttpTrigger(name = "req", methods = { HttpMethod.GET,
			HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
			final ExecutionContext context) {
		context.getLogger().info("Java HTTP trigger processed a request.");

		// Parse query parameter
		final String qryText = request.getQueryParameters().get("text");
		SentimentRequest sentReq = null;
		SentimentResponse res = new SentimentResponse();
		res.setResponseCode(-1);
		res.setResponse("Error:: No text available for processing.");
		try {
			Optional<String> content = request.getBody();
			if (!content.isEmpty()) {
				sentReq = new ObjectMapper().readValue(content.get(), SentimentRequest.class);
			}
		} catch (Exception e) {
			context.getLogger().log(Level.SEVERE, e.getLocalizedMessage());
			res.setResponseCode(-1);
			res.setResponse("Error::" + "Unable to load SentimentRequest");
			sentReq = null;
		}

		if (sentReq == null && qryText == null) {
			try {
				return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
						.body(new ObjectMapper().writeValueAsString(res)).build();
			} catch (Exception e1) {
				context.getLogger().log(Level.SEVERE, e1.getLocalizedMessage());
			}
		} else {
			try {
				String text = sentReq != null ? sentReq.getText() : qryText;
				TextAnalyticsClient client = SentimentAnalyzer.authenticateClient(System.getenv("TEXT_ANALYTICS_KEY"),
						System.getenv("TEXT_ANALYTICS_ENDPOINT"));
				res = SentimentAnalyzer.analyzeText(client, text, context);
				return request.createResponseBuilder(HttpStatus.OK).body(new ObjectMapper().writeValueAsString(res))
						.build();
			} catch (Exception e1) {
				context.getLogger().log(Level.SEVERE, e1.getLocalizedMessage());
			}
		}
		return null;
	}
}
