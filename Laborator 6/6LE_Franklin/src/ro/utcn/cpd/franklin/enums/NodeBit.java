package ro.utcn.cpd.franklin.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Leader election algorithm iteration.
 * User: Tamas
 * Date: 05/01/14
 * Time: 13:54
 */
public enum NodeBit {
    T,
    F;

    /**
     * Holds the opposite bit.
     */
    private static Map<NodeBit, NodeBit> opposite = new HashMap<NodeBit, NodeBit>();

    static {
        opposite.put(F, T);
        opposite.put(T, F);
    }

    public NodeBit getOppositeBit() {
        return opposite.get(this);
    }
}
