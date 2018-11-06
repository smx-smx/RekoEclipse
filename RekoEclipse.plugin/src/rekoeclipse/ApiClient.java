package rekoeclipse;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Builder;
import java.net.http.WebSocket.Listener;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletionStage;

import com.google.gson.Gson;

public class ApiClient {
	private final class ListenerImplementation implements Listener {
		@Override
		public void onOpen(WebSocket webSocket) {
			Listener.super.onOpen(webSocket);
		}

		@Override
		public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
			return Listener.super.onBinary(webSocket, data, last);
		}
	}
	
	private byte[] createRestRequest(ApiRequestPayload restReq) {
		String reqJson = new Gson().toJson(restReq);
		
		ApiMessageHeader header = ApiMessageHeader.newRestRequest(reqJson.length(), lastSeqNo);
		lastSeqNo += 2;
		
		ApiRequestMessage message = new ApiRequestMessage(header, reqJson.getBytes());
		return message.getBytes();
	}
	
	public int sendRestRequest(HttpMethod method, String url, Map<String, Object> data) {
		ApiRequestPayload payload = new ApiRequestPayload(method.name(), url, data);
		byte[] buf = createRestRequest(payload);
		
		webSocket.sendBinary(ByteBuffer.wrap(buf), true).join();
		return 0;
	}
	
	private final WebSocket webSocket;
	
	private int lastSeqNo = 0; 

	public ApiClient(String wsPath) throws URISyntaxException {
		HttpClient client = HttpClient.newBuilder()
				.version(Version.HTTP_1_1)
				.connectTimeout(Duration.ofSeconds(5))
				.build();
		
		webSocket = client.newWebSocketBuilder()
				.subprotocols("reko")
				.connectTimeout(Duration.ofSeconds(5))
				.buildAsync(new URI(wsPath), new ListenerImplementation()).join();
	}

}
