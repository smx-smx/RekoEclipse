package rekoeclipse.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Map;

import com.google.gson.Gson;
import java.util.concurrent.CompletableFuture;
import org.osgi.service.component.ComponentContext;


import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import rekoeclipse.api.ApiMessageHeader;
import rekoeclipse.api.ApiMessageListener;
import rekoeclipse.api.ApiReplyMessage;
import rekoeclipse.api.ApiRequestMessage;
import rekoeclipse.api.ApiRequestPayload;
import rekoeclipse.api.HttpMethod;

@Component(service = ApiClient.class)
public class ApiClient implements AutoCloseable {
	private final String API_PREFIX = "/reko/v1/";

	private WebSocket webSocket;

	private int lastSeqNo = 0;
	private final ApiMessageListener listener = new ApiMessageListener();

	@Reference
	private RekoHostService hostSvc;
	
	public ApiClient() {
	}
	
	@Activate
	protected void activate(ComponentContext ctx) {
		String wsPath = String.format("ws://127.0.0.1:%d%s", hostSvc.getSocketPort(), API_PREFIX);

		HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(5))
				.build();

		try {
			webSocket = client.newWebSocketBuilder().subprotocols("reko").connectTimeout(Duration.ofSeconds(5))
					.buildAsync(new URI(wsPath), listener).join();
		} catch (URISyntaxException ex) {
			throw new Error(ex);
		}
	}

	public boolean isConnected() {
		return webSocket.isInputClosed() || webSocket.isOutputClosed();
	}

	@Override
	public void close() {
		this.webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Terminating");
	}

	private ApiRequestMessage createRestRequest(ApiRequestPayload restReq) {
		String reqJson = new Gson().toJson(restReq);

		ApiMessageHeader header = ApiMessageHeader.newRestRequest(reqJson.length(), lastSeqNo);
		lastSeqNo += 2;

		ApiRequestMessage message = new ApiRequestMessage(header, reqJson.getBytes());
		return message;
	}

	private CompletableFuture<byte[]> sendRequest(ApiRequestMessage msg) {
		byte[] msgData = msg.serialize();
		ByteBuffer buf = ByteBuffer.wrap(msgData);

		ApiMessageHeader header = msg.getHeader();
		listener.registerRequest(header);

		// send message asynchronously
		webSocket.sendBinary(buf, true);

		// wait for its reply
		return listener.waitForMessage(header.getSequenceNumber());
	}

	public CompletableFuture<ApiReplyMessage> sendRestRequest(HttpMethod method, String url, Map<String, Object> data) {
		ApiRequestPayload body = new ApiRequestPayload(method.name(), url, data);
		ApiRequestMessage message = createRestRequest(body);

		return sendRequest(message).thenApply((replyData) -> {
			String json = new String(replyData);
			return new Gson().fromJson(json, ApiReplyMessage.class);
		});
	}

}
