package readwrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Data {

    File file;
    boolean writing = false;

    public Data(File file) {
        this.file = file;
    }

    public synchronized void write(String sentence) {
        while (!writing) {
            try {
                System.out.println("Data writer is waiting");
                wait();
                System.out.println("Data writer was notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file.getName());
            fileWriter.write(sentence);
            fileWriter.close();
            System.out.println("Writing " + sentence);
        } catch (IOException e) {
            e.printStackTrace();
        }
        writing = false;
        notifyAll();
    }

    public synchronized String read() throws FileNotFoundException {
        while (writing) {
            try {
                System.out.println("Data reader is waiting");
                wait();
                System.out.println("Data reader was notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Scanner reader = new Scanner(file);
        StringBuilder stringBuilder = new StringBuilder();
        while (reader.hasNextLine()) {
            String sentence = reader.nextLine();
            stringBuilder.append(sentence);
            System.out.println("Reading " + sentence);
        }
        notifyAll();
        writing = true;
        return stringBuilder.toString();
    }
}
