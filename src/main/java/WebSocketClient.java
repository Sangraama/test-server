/**
 * Class Description.
 *
 * @author: Gihan Karunarathne
 * Date: 12/10/13
 * Time: 3:00 AM
 * @email: gckarunarathne@gmail.com
 */

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.http.WebSocket;
import org.vertx.java.platform.Verticle;

public class WebSocketClient extends Verticle {

    public void start() {

        // Setting host as localhost is not strictly necessary as it's the default
        vertx.createHttpClient().setHost("localhost").setPort(8080).connectWebsocket("/myapp", new Handler<WebSocket>() {
            @Override
            public void handle(WebSocket websocket) {
                websocket.dataHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer data) {
                        System.out.println("Received " + data);
                    }
                });
                //Send some data
                websocket.writeTextFrame("hello world");
            }
        });

    }
}
