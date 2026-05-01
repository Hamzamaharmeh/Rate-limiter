package hamza.maharmeh.fixedwindow;

import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
public class WindowApp {
    public static void main( String[] args )throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);
        WindowCounter window =  new WindowCounter();

        server.createContext("/limited", handler -> {
            String response = "Limited service\n";
            var address = handler.getRemoteAddress().getAddress().getHostAddress();
            OutputStream os = handler.getResponseBody();

            if(window.tryToRequest()){
                handler.sendResponseHeaders(200, response.getBytes().length);
                os.write(response.getBytes());
            }else {
                handler.sendResponseHeaders(429, 0);
            }
            os.close();
        });

        server.createContext("/unlimited", handler -> {
            String response =  "Unlimited service\n";
            handler.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = handler.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });
        server.setExecutor(null);
        server.start();
        System.out.println("Server starting");
    }
}
