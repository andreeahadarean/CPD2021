import readwrite.Data;
import readwrite.Reader;
import readwrite.Writer;

import java.io.File;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        File file = new File("test.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Data data = new Data(file);
        Writer w = new Writer(1000, data);
        Reader r = new Reader(3000, data);
        w.start();
        r.start();
    }
}
