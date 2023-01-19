package server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class SocketHandler implements Runnable {
    protected Socket socket;
    private static Logger logger = Logger.getLogger(Watchdog.class.getName());
    protected long lastActive = System.currentTimeMillis();;

    public SocketHandler(Socket socket) {
        this.socket = socket;
        logger.log(Level.INFO, String.format("New client: %s:%s",socket.getInetAddress(),socket.getPort()));
    }

    public void run() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            int letter;
            while ((letter = in.read()) != -1) {
                lastActive = System.currentTimeMillis();

                out.write(letter);
                out.flush();
            }

            logger.log(Level.INFO, String.format("Client disconnect: %s:%s",socket.getInetAddress(),socket.getPort()));
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void terminate() {
        try {
            socket.close();
        } catch (IOException e) {
            logger.log(Level.INFO, "Unable to close socket");
        }
    }

    public boolean isInactive() {
        if((System.currentTimeMillis() - lastActive) > 10000) {
            return true;
        }
        return false;
    }
}