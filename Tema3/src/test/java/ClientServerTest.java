import client.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientServerTest {

    private static int port = 5555;
    private Client client = new Client();

    @Before
    public void init() {
        client.startConnection("127.0.0.1", port);
    }

    @After
    public void tearDown() {
        client.stopConnection();
    }

    @Test
    public void postALocation() {
        Client client = new Client();
        client.startConnection("127.0.0.1", port);
        String msg1 = client.sendMessage("post, Budapest");
        String terminate = client.sendMessage("thank you");

        assertEquals("We added your offer!", msg1);
        assertEquals("Thank you!", terminate);
        client.stopConnection();
    }

    @Test
    public void rentAFreeLocation() {
        Client client = new Client();
        client.startConnection("127.0.0.1", port);
        String msg1 = client.sendMessage("rent");
        String terminate = client.sendMessage(".");

        assertEquals("You can rent a location!", msg1);
        assertEquals("Thank you!", terminate);
        client.stopConnection();
    }
}
