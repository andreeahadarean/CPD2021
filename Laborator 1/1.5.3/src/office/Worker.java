package office;

import java.util.Random;

public class Worker extends Thread {

    private long delay;
    private DocumentManager documentManager;

    public Worker(long delay, DocumentManager documentManager) {
        super();
        this.delay = delay;
        this.documentManager = documentManager;
    }

    @Override
    public void run() {
        try {
            for(int i = 0; i <= 2; i++) {
                sleep(delay);
                Random random = new Random();
                Document document = new Document(random.nextInt(1000));
                documentManager.elaborateDocument(document);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
