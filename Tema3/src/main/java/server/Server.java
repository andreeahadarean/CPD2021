package server;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import server.model.Location;
import server.model.LocationManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;

    private LocationManager locationManager;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            this.locationManager = new LocationManager();
            while (true)
                new ClientHandler(serverSocket.accept(), locationManager).start();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }

    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private LocationManager locationManager;

        public ClientHandler(Socket socket, LocationManager locationManager) {
            this.clientSocket = socket;
            this.locationManager = locationManager;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String[] messages = inputLine.split(", ");
                    if("post".equals(messages[0])) {
                        locationManager.addLocation(messages[1]);
                        locationManager.printLocations();
                        out.println("We added your offer!");
                        System.out.println("We added your offer!");
                    } else if ("rent".equals(inputLine)) {
                        out.println("You can rent a location!");
                        System.out.println("You can rent a location!");
                    }
                    else if ("thank you".equals(inputLine)) {
                        out.println("Thank you!");
                        System.out.println("Thank you!");
                        break;
                    }
                }

                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                LOG.debug(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(5555);
    }
}
