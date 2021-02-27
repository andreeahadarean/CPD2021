package readwrite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Data {

    FileWriter fileWriter;
    PrintWriter printWriter;
    Scanner reader;
    boolean writing = false;

    public Data(File file) throws IOException {
        this.fileWriter = new FileWriter(file.getName());
        printWriter = new PrintWriter(fileWriter);
        this.reader = new Scanner(file);
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
        printWriter.println(sentence);
        System.out.println("Writing " + sentence);
        writing = false;
        notifyAll();
    }

    public synchronized String read() {
        while (writing) {
            try {
                System.out.println("Data reader is waiting");
                wait();
                System.out.println("Data reader was notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (reader.hasNextLine()) {
            String sentence = reader.nextLine();
            stringBuilder.append(reader.nextLine());
            System.out.println("Reading " + sentence);
        }
        notifyAll();
        writing = true;
        return stringBuilder.toString();
    }
}
