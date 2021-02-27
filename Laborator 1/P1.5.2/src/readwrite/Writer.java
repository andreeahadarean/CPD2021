package readwrite;

public class Writer extends Thread {

    private String[] sentences = {"Sentence 1", "Sentence 2", "Sentence 3", "Sentence 4", "Sentence 5", "End"};
    private long delay;
    private Data data;

    public Writer(long delay, Data data) {
        this.delay = delay;
        this.data = data;
    }

    @Override
    public void run() {
        try {
            for (String sentence : sentences) {
                sleep(delay);
                data.write(sentence);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
