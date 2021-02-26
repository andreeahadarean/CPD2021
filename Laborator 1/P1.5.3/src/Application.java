import office.DocumentManager;
import office.Printer;
import office.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Application {

    public static void main(String[] args) {
        DocumentManager documentManager = new DocumentManager();
        List<Worker> workerList = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 8; i++) {
            int delay = random.ints(1000, 3000).findFirst().getAsInt();
            workerList.add(new Worker(delay, documentManager));
        }
        int delay = random.ints(1000, 3000).findFirst().getAsInt();
        Printer printer = new Printer(delay, documentManager);

        for(Worker worker : workerList) {
            System.out.println("Star worker thread");
            worker.start();
        }
        System.out.println("Star printer thread");
        printer.start();
    }
}
