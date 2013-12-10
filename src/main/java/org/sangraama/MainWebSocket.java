package org.sangraama;

/**
 * Class Description.
 *
 * @author: Gihan Karunarathne
 * Date: 12/10/13
 * Time: 2:53 AM
 * @email: gckarunarathne@gmail.com
 */

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.platform.Verticle;

public class MainWebSocket extends Verticle {

    public void start() {

        vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
            public void handle(final ServerWebSocket ws) {
                if (ws.path().equals("/myapp")) {
                    ws.dataHandler(new Handler<Buffer>() {
                        public void handle(Buffer data) {
                            ws.writeTextFrame(data.toString()); // Echo it back
                        }
                    });
                } else {
                    ws.reject();
                }
            }
        }).requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                if (req.path().equals("/")) req.response().sendFile("index.html"); // Serve the html
                /*String file = req.path().equals("/") ? "index.html" : req.path();
                req.response().sendFile("webroot/" + file);*/
            }
        }).listen(8080,"localhost");

    }
}
