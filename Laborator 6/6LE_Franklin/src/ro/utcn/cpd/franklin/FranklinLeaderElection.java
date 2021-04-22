package ro.utcn.cpd.franklin;

import ro.utcn.cpd.franklin.enums.NodeState;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a simulation class.
 * User: Tamas
 * Date: 05/01/14
 * Time: 14:19
 */
public class FranklinLeaderElection {
    /**
     * Holds the number of nodes/processes
     */
    private final int n;

    /**
     * Holds the maximum identity number.
     */
    private final int k;

    public FranklinLeaderElection(int n, int k) {
        this.n = n;
        this.k = k;
    }

    /**
     * Simulates the leader election algorithm for the given nodes with maximum identity.
     */
    public void simulate() {
        // create the nodes queue
        List<Node> nodes = initNodes();

        // set the neighbor of the nodes
        setNeighbors(nodes);


        System.out.println("Node neighbors: ");
        for (Node node : nodes) {
            System.out.println(node.getNodeIndex() + " : " + node.getNeighborsString());
        }

        boolean leaderFound = false;
        while (!leaderFound) {
            System.out.println("New iteration");

            System.out.println("Nodes: ");
            for (Node node : nodes) {
                System.out.println(node);
            }

            // start message sending between neighbours
            for (Node node : nodes) {
                node.sendMessages();
            }

            List<Node> activeNodes = new ArrayList<Node>();
            for (Node node : nodes) {
                // add active nodes in a separate list
                if (node.isActive()) {
                    activeNodes.add(node);
                }

                // change the iteration of the node
                node.changeBit();
                if (node.isLeader()) {
                    System.out.println("Node " + node.getNodeIndex() + " is leader.");
                    leaderFound = true;
                }

                node.generateNewIdentity();
                node.removeMessages();
            }

            if (activeNodes.size() == 0 && !leaderFound) {
                System.out.println("Invalid solution!");
                break;
            }
        }

    }

    /**
     * Initializes the node queue.
     *
     * @return A collection of nodes
     */
    private List<Node> initNodes() {
        List<Node> nodes = new ArrayList<Node>();

        for (int i = 0; i < n; i++) {
            nodes.add(new Node(i, n, k));
        }

        return nodes;
    }

    /**
     * Sets the neighbor nodes of every node.
     *
     * @param nodes A collection of nodes
     */
    private void setNeighbors(List<Node> nodes) {
        for (int i = 0; i < n; i++) {
            Node node = nodes.get(i);

            // left neighbor index
            int leftNodeIndex = (i - 1) % n;
            // right neighbor index
            int rightNodeIndex = (i + 1) % n;

            // correct the left neighbor index
            if (leftNodeIndex < 0) {
                leftNodeIndex = n + leftNodeIndex;
            }

            // correct the right neighbor index
            if (rightNodeIndex > n) {
                rightNodeIndex = rightNodeIndex - n;
            }

            // set neighbors
            node.setNeighbors(nodes.get(leftNodeIndex), nodes.get(rightNodeIndex));
        }
    }
}
