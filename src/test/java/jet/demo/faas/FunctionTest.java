package jet.demo.faas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;

import jet.demo.faas.bo.SentimentRequest;

/**
 * Unit test for Function class.
 */
public class FunctionTest {
	/**
	 * Unit test for HttpTriggerJava method.
	 */
	@Test
	public void testUrlQuery() throws Exception {
		// Setup
		@SuppressWarnings("unchecked")
		final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

		final Map<String, String> queryParams = new HashMap<>();
		queryParams.put("text", "Azure is awesome! But confusing sometimes. I like it though.");
		doReturn(queryParams).when(req).getQueryParameters();

		final Optional<String> queryBody = Optional.empty();
		doReturn(queryBody).when(req).getBody();

		doAnswer(new Answer<HttpResponseMessage.Builder>() {
			@Override
			public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
				HttpStatus status = (HttpStatus) invocation.getArguments()[0];
				return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
			}
		}).when(req).createResponseBuilder(any(HttpStatus.class));

		final ExecutionContext context = mock(ExecutionContext.class);
		doReturn(Logger.getGlobal()).when(context).getLogger();

		// Invoke
		final HttpResponseMessage ret = new Function().detectSentiment(req, context);

		// Verify
		assertEquals(ret.getStatus(), HttpStatus.OK);
	}

	@Test
	public void testRequestBody() throws Exception {
		// Setup
		@SuppressWarnings("unchecked")
		final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

		SentimentRequest senReq = new SentimentRequest();
		senReq.setAuthor("anonymous");
		senReq.setText("Azure is awesome!");
		final Optional<String> queryBody = Optional.of(new ObjectMapper().writeValueAsString(senReq));
		doReturn(queryBody).when(req).getBody();

		doAnswer(new Answer<HttpResponseMessage.Builder>() {
			@Override
			public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
				HttpStatus status = (HttpStatus) invocation.getArguments()[0];
				return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
			}
		}).when(req).createResponseBuilder(any(HttpStatus.class));

		final ExecutionContext context = mock(ExecutionContext.class);
		doReturn(Logger.getGlobal()).when(context).getLogger();

		// Invoke
		final HttpResponseMessage ret = new Function().detectSentiment(req, context);

		// Verify
		assertEquals(ret.getStatus(), HttpStatus.OK);
	}

}
