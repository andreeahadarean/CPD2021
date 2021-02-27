package readwrite;

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
                System.out.println("Reading " + sentence);
                sleep(delay);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
