package ro.utcn.cpd.franklin;

import ro.utcn.cpd.franklin.enums.MessageDirection;
import ro.utcn.cpd.franklin.enums.NodeBit;
import ro.utcn.cpd.franklin.enums.NodeState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tamas
 * Date: 05/01/14
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
 */
public class Node {
    /**
     * Holds the identity of this node.
     */
    private int id;

    /**
     * Holds the state of this node.
     */
    private NodeState state;

    /**
     * Holds the iteration step.
     */
    private NodeBit bit;

    /**
     * Holds the left neighbor of this node.
     */
    private Node leftNeighbor;

    /**
     * Holds the right neighbor of this node.
     */
    private Node rightNeighbor;

    /**
     * Holds the index of this node.
     */
    private final int nodeIndex;

    /**
     * Holds the nodes count in the queue.
     */
    private final int nodesNumber;

    /**
     * Holds the maximum identity number.
     */
    private final int maxIdentityNumber;

    /**
     * Holds the message queue of this node.
     */
    private List<Message> messages;

    private int bitMessagesCount = 0;

    public Node(int nodeIndex, int nodesNumber, int maxIdentityNumber) {
        this.maxIdentityNumber = maxIdentityNumber;
        this.state = NodeState.ACTIVE;
        this.bit = NodeBit.T;
        this.nodeIndex = nodeIndex;
        this.nodesNumber = nodesNumber;

        generateNewIdentity();

        messages = new ArrayList<Message>();
    }

    /**
     * Send messages to the neighbors.
     */
    public void sendMessages() {
        System.out.println("Node " + nodeIndex + " state: " + state);
        for (Message message : messages) {
            if (NodeState.PASSIVE.equals(state)) {
                message.setHop(message.getHop() + 1);

                forwardMessage(message);
            }
        }

        if (NodeState.ACTIVE.equals(state)) {
            Message message = new Message(id, 1, bit, MessageDirection.LEFT);
            System.out.println("Messages for node " + getNodeIndex() + ":" + messages);
            System.out.println("Node " + nodeIndex + " sends message " + message + " to node " + leftNeighbor.getNodeIndex());
            leftNeighbor.receiveMessage(message);

            message.setMessageDirection(MessageDirection.RIGHT);

            System.out.println("Node " + nodeIndex + " sends message " + message + " to node " + rightNeighbor.getNodeIndex());
            rightNeighbor.receiveMessage(message);
        }
    }

    /**
     * When a message is received process it and decide the state of this node.
     *
     * @param message
     */
    public void receiveMessage(Message message) {
        // if the node is passive forward the message to it's neighbor in the corresponding direction
        if (NodeState.PASSIVE.equals(state)) {
            message.setHop(message.getHop() + 1);
            System.out.println("Forward message from node " + nodeIndex);
            forwardMessage(message);
        } else {
            messages.add(message);
            System.out.println("Node index: " + nodeIndex + ".Bit: " + bit + " Messages size: " + messages.size());
            if (bit.equals(message.getBit())) {
                if (message.getHop() == nodesNumber) {
                    state = NodeState.LEADER;
                }

                bitMessagesCount++;
            }
            System.out.println("bitMessagesCount = " + bitMessagesCount);
            if (bitMessagesCount == 2) {
                decide();
            }
        }
    }

    /**
     * Decide the state of this node.
     */
    private void decide() {
        boolean stateChanged = false;
        Iterator<Message> iterator = messages.iterator();
        while (iterator.hasNext()) {
            Message message = iterator.next();

            if (message.getBit().equals(bit)) {
                if (message.getId() > id) {
                    state = NodeState.PASSIVE;
                    bit = bit.getOppositeBit();
                    stateChanged = true;
                }

                iterator.remove();
                bitMessagesCount--;
            } else if (stateChanged) {
                if (message.getBit().equals(bit.getOppositeBit())) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Forwards the message to the corresponding neighbor.
     *
     * @param message The message to be forwarded
     */
    private void forwardMessage(Message message) {
        if (MessageDirection.LEFT.equals(message.getMessageDirection())) {
            leftNeighbor.receiveMessage(message);
        } else if (MessageDirection.RIGHT.equals(message.getMessageDirection())) {
            rightNeighbor.receiveMessage(message);
        }
    }

    /**
     * Generates a new identity for this node.
     */
    public void generateNewIdentity() {
        id = (int) (Math.random() * maxIdentityNumber);
    }

    /**
     * Returns <code>true</code> if the node is leader, otherwise <code>false</code>
     *
     * @return
     */
    public boolean isLeader() {
        return NodeState.LEADER.equals(state);
    }

    /**
     * Returns <code>true</code> if this node is active, otherwise <code>false</code>.
     *
     * @return
     */
    public boolean isActive() {
        return NodeState.ACTIVE.equals(state);
    }

    /**
     * Sets the state of this node.
     *
     * @param state The new state of this node
     */
    public void setState(NodeState state) {
        this.state = state;
    }

    /**
     * Removes all the messages from this node.
     */
    public void removeMessages() {
        messages.clear();
    }

    /**
     * Returns the node index.
     *
     * @return
     */
    public int getNodeIndex() {
        return nodeIndex;
    }

    /**
     * Set the neighbor of this node.
     *
     * @param leftNeighbor  The left neighbor of this node.
     * @param rightNeighbor The right neighbor of this node.
     */
    public void setNeighbors(Node leftNeighbor, Node rightNeighbor) {
        this.leftNeighbor = leftNeighbor;
        this.rightNeighbor = rightNeighbor;
    }

    public String getNeighborsString() {
        return "[" + leftNeighbor.getNodeIndex() + ", " + rightNeighbor.getNodeIndex() + "]";
    }

    /**
     * Change the iteration of this node.
     */
    public void changeBit() {
        bit = bit.getOppositeBit();
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", state=" + state +
                ", bit=" + bit +
                ", nodeIndex=" + nodeIndex +
                '}';
    }
}
