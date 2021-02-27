package readwrite;

import java.io.FileNotFoundException;

public class Reader extends Thread {

    private long delay;
    private Data data;

    public Reader(long delay, Data data) {
        this.delay = delay;
        this.data = data;
    }

    @Override
    public void run() {
        String sentence = null;
        try {
            while (!"End".equals(sentence)) {
                sentence = data.read();
                sleep(delay);
            }
        } catch (InterruptedException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
