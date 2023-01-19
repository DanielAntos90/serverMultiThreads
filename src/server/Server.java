package server;

import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
    private ServerSocket serverSocket;
    private static Logger logger = Logger.getLogger(Server.class.getName());

    public Server(int port) throws UnknownHostException, IOException {
        try {
            serverSocket = new ServerSocket(port);
            logger.log(Level.INFO, "Server is up");
        } catch (IOException e) {
            e.printStackTrace();
            serverSocket.close();
        }
    }
    public void start() throws IOException, InterruptedException {
        try {
            ExecutorService exec = Executors.newCachedThreadPool();  // 1024
            var watchdog = new Watchdog();
            watchdog.start();

            while (true) {
                Socket socket = serverSocket.accept();
                SocketHandler handler = new SocketHandler(socket);
                watchdog.register(handler);
                exec.execute(handler);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logger.log(Level.INFO, "Server ended.");
        }
    }
}
