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

        ServerWebSocket admin , console;

        server.websocketHandler(new Handler<ServerWebSocket>() {
            public void handle(final ServerWebSocket ws) {
                if (ws.path().equals("/console")) {

                    ws.dataHandler(new Handler<Buffer>() {
                        public void handle(Buffer data) {
                            ws.writeTextFrame(data.toString()); // Echo it back
                        }
                    });

                } else if (ws.path().equals("/admin-console")) {

                    ws.dataHandler(new Handler<Buffer>() {
                        public void handle(Buffer data) {
                            ws.writeTextFrame(data.toString()); // Echo it back
                        }
                    });
                    
                } else {
                    ws.reject();
                }
            }
        });

        RouteMatcher routeMatcher = new RouteMatcher();

        routeMatcher.get("/", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().sendFile("index.html");
            }
        });
        routeMatcher.get("/main-console.js", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().sendFile("js/main-console.js");
            }
        });
        routeMatcher.get("/admin", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().sendFile("admin.html");
            }
        });
        routeMatcher.get("/client-console.js", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().sendFile("js/client-console.js");
            }
        });

        routeMatcher.get("/jquery-2.0.2.min.js", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().sendFile("js/lib/jquery-2.0.2.min.js");
            }
        });
        routeMatcher.get("/bootstrap.min.js", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().sendFile("js/lib/bootstrap.min.js");
            }
        });

        routeMatcher.get("/jquery.json-2.4.min.js", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().sendFile("js/lib/jquery.json-2.4.min.js");
            }
        });
        routeMatcher.get("/underscore.js", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().sendFile("js/lib/underscore.js");
            }
        });

        routeMatcher.get("/bootstrap.min.css", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().sendFile("css/bootstrap.min.css");
            }
        });
        routeMatcher.get("/bootstrap-responsive.min.css", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().sendFile("css/bootstrap-responsive.min.css");
            }
        });

        server.requestHandler(routeMatcher);
        server.listen(8080,"localhost");
    }
}
