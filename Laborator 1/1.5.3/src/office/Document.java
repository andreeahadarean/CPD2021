package office;

public class Document {
    int docNumber;

    public Document(int docNumber) {
        this.docNumber = docNumber;
    }

    @Override
    public String toString() {
        return "document " + docNumber;
    }
}
