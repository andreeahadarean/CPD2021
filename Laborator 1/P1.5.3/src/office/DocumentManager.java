package office;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Thread.currentThread;

public class DocumentManager {

    Queue<Document> documents = new LinkedList<>();

    public void elaborateDocument(Document document) {

        System.out.println(currentThread().getId() + " elaborated document: " + document.toString());
        documents.add(document);
    }

    public Document printDocument() {
        Document document = documents.poll();
        assert document != null;
        System.out.println("Printed document " + document.toString());
        return document;
    }

    public boolean hasDocuments() {
        return !documents.isEmpty();
    }
}
