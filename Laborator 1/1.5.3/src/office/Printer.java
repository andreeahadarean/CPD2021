package office;

public class Printer extends Thread {

    private long delay;
    private DocumentManager documentManager;

    public Printer(long delay, DocumentManager documentManager) {
        super();
        this.delay = delay;
        this.documentManager = documentManager;
    }

    @Override
    public void run() {
        try {
            while (!documentManager.hasDocuments()) {
                System.out.println("Nothing to print");
                Thread.sleep(500);
            }
            while (documentManager.hasDocuments()) {
                documentManager.printDocument();
                sleep(delay);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
