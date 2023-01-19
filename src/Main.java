import server.Server;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {

        try {

            Server s = new Server(7999);
            s.start();

            logger.log(Level.INFO, "Done");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


