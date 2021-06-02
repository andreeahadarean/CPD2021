import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Client {

    private static boolean hasToken = true;
    private static LocalDateTime receivedTime = LocalDateTime.now();
    private static LocalDateTime sendTime = receivedTime.plusSeconds(9);

    public static void main(String[] args){
        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc = new Scanner(System.in);
        try {
            clientSocket = new Socket("127.0.0.1",5000);
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            TimerTask sendTask = new TimerTask() {
                @Override
                public void run() {
                    //System.out.println(hasToken);
                    //System.out.println("***" + LocalDateTime.now().withNano(0) + " " + sendTime.withNano(0));
                    if(!hasToken && LocalDateTime.now().withNano(0).equals(sendTime.withNano(0).plusSeconds(1))) {
                        System.out.println("Sending token to server at: " + sendTime.withNano(0));
                        String msg = "token";
                        out.println(msg);
                        out.flush();
                    }
                }};
            new Timer().scheduleAtFixedRate(sendTask, 0, 1000);

            TimerTask checkTask = new TimerTask() {
                @Override
                public void run() {
                    if(hasToken && LocalDateTime.now().withNano(0).equals(sendTime.withNano(0))) {
                        hasToken = false;
                    }
                }};
            new Timer().scheduleAtFixedRate(checkTask, 0, 1000);

            Thread receiver = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        while(msg != null){
                            if(!hasToken) {
                                if (msg.equals("token")) {
                                    hasToken = true;
                                    receivedTime = LocalDateTime.now();
                                    sendTime = receivedTime.plusSeconds(10);
                                    System.out.println("Client has the token at: " + receivedTime + " and will send it at " + sendTime);
                                }
                            }
                            msg = in.readLine();
                        }
                        System.out.println("Server out of service");
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiver .start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
