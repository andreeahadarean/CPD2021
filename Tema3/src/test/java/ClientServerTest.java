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
    public void rentAnAvailableLocation() {
        Client client = new Client();
        client.startConnection("127.0.0.1", port);
        String msg1 = client.sendMessage("rent, London, 2");
        String terminate = client.sendMessage("thank you");

        assertEquals("You rented the location!", msg1);
        assertEquals("Thank you!", terminate);
        client.stopConnection();
    }

    @Test
    public void rentARentedLocation() {
        Client client = new Client();
        client.startConnection("127.0.0.1", port);
        String msg1 = client.sendMessage("rent, Berlin, 2");
        String msg2 = client.sendMessage("rent, Paris, 2");
        String terminate = client.sendMessage("thank you");

        assertEquals("Location not available!", msg1);
        assertEquals("You rented the location!", msg2);
        assertEquals("Thank you!", terminate);
        client.stopConnection();
    }
}
