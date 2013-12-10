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
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.platform.Verticle;

public class MainWebSocket extends Verticle {

    public void start() {
        HttpServer server = vertx.createHttpServer();

        server.websocketHandler(new Handler<ServerWebSocket>() {
            public void handle(final ServerWebSocket ws) {
                if (ws.path().equals("/chat")) {
                    ws.dataHandler(new Handler<Buffer>() {
                        public void handle(Buffer data) {
                            ws.writeTextFrame(data.toString()); // Echo it back
                        }
                    });
                } else {
                    ws.reject();
                }
            }
        });//listen(8080,"localhost");
        /*requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                if (req.path().equals("/")) req.response().sendFile("index.html"); // Serve the html
                //String file = req.path().equals("/") ? "index.html" : req.path();
                //req.response().sendFile("webroot/" + file);
            }
        });*/

        RouteMatcher routeMatcher = new RouteMatcher();

        routeMatcher.get("/", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().sendFile("index.html");
            }
        });
        routeMatcher.get("/main-console.js", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().sendFile("main-console.js");
            }
        });

        server.requestHandler(routeMatcher);
        server.listen(8080,"localhost");
    }
}
