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
            Random random = new Random();
            int nrOfDocuments = random.nextInt(10);
            for(int i = 0; i <= nrOfDocuments; i++) {
                sleep(delay);
                Document document = new Document(random.nextInt(1000));
                documentManager.elaborateDocument(document);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
