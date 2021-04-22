package ro.utcn.cpd.franklin;

import ro.utcn.cpd.franklin.enums.MessageDirection;
import ro.utcn.cpd.franklin.enums.NodeBit;

/**
 * Represents a message exchanged between nodes.
 * User: Tamas
 * Date: 05/01/14
 * Time: 13:57
 */
public class Message {
    /**
     * Holds the id of the transmitter *
     */
    private int id;

    /**
     * Holds the step number *
     */
    private int hop;

    /**
     * Holds the iteration step *
     */
    private NodeBit bit;

    /**
     * Holds the direction of the message *
     */
    private MessageDirection messageDirection;

    public Message(int id, int hop, NodeBit bit, MessageDirection messageDirection) {
        this.id = id;
        this.hop = hop;
        this.bit = bit;
        this.messageDirection = messageDirection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHop() {
        return hop;
    }

    public void setHop(int hop) {
        this.hop = hop;
    }

    public NodeBit getBit() {
        return bit;
    }

    public void setBit(NodeBit bit) {
        this.bit = bit;
    }

    public MessageDirection getMessageDirection() {
        return messageDirection;
    }

    public void setMessageDirection(MessageDirection messageDirection) {
        this.messageDirection = messageDirection;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", hop=" + hop +
                ", bit=" + bit +
                ", messageDirection=" + messageDirection +
                '}';
    }
}
