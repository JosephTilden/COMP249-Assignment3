package Part2;

import java.util.NoSuchElementException;

public class TariffList implements TariffPolicy {
    private TariffNode head = null;
    private int size = 0;


    public TariffList() {}

    // TODO: unclear instructions. Deep copy?

    /**
     * Creates a duplicate TariffList where each node has the same Tariff value, while remaining a deep copy.
     * @param other list to copy
     */
    public TariffList(TariffList other) {
        /*
        Copy head
        Iterate through, copying

        CASES: 0, 1, 2, 3
         */
        if (other.size == 0) {
            head = null;
        } else {
            head = new TariffNode(other.head.value, other.head.link);
            TariffNode currentNode = head;
            while (currentNode != null) {       // Includes final element
            }
        }
    }

    public int getSize() {
        return size;
    }

    // TODO research static warning
    private class TariffNode implements Cloneable {
        private Tariff value = null;
        private TariffNode link = null;

        public TariffNode() {
        }

        public TariffNode(Tariff value, TariffNode link) {
            this.value = value;
            this.link = link;
        }

        public TariffNode(TariffNode other) {
            this.value = new Tariff(other.value);
            this.link = other.link; // Keeps same link I'm assuming. Might be a privacy leak                        !!!
        }

        public Tariff getValue() {
            return value;
        }

        public void setValue(Tariff value) {
            this.value = value;
        }

        public TariffNode getLink() {
            return link;
        }

        public void setLink(TariffNode link) {
            this.link = link;
        }

        /**
         * Implementation for mutable instance variables. Link remains a direct reference.
         * @return a deep copy
         */
        public TariffNode clone() {
            try {
                TariffNode copy = (TariffNode) super.clone();
                copy.value = value.clone();
                copy.link = link;
                return copy;
            } catch (CloneNotSupportedException e){
                return null;
            }
        }

        /**
         * Checks to see if two nodes have the same data. Does not check to see if they have the same tail, since that
         * would require making this recursive, and I think that would make it call .equals on every node that follows
         * the current one.
         * @param other node to compare to
         * @return equality of value
         */
        public boolean equals(TariffNode other) {
            return (value.equals(other.value));
        }
    }

    // PUBLIC METHODS
    /**
     * Creates a new node with the previous head as its link, then makes that the new head of the list
     * @param tariff value for the new node
     */
    public void addToStart(Tariff tariff) {
        head = new TariffNode(tariff, head);
        size++;
    }

    // TODO exposed outside visibility warning?
    /**
     * Finds a TariffNode in the TariffList based on 3 descriptors. Also prints total amount of iterations used to find
     * the target Node.
     * @param origin product country of origin
     * @param destination product destination country
     * @param category type of product
     * @return found tariffNode or null if no nodes match
     */
    public TariffNode find(String origin, String destination, String category) {
        TariffNode currentNode = head;
        int iterations = 0;
        String findMsg = "Find method used: %d iterations\n";
        if (size == 0) {
            System.out.printf(findMsg, iterations);
            return null;
        }
        if (criteriaMatches(head, origin, destination, category)) {
            System.out.printf(findMsg, ++iterations);    // Should be 1 iteration
            return head;
        }
        currentNode = currentNode.link;     // Start at 2nd element
        iterations++;                       // Should be 2
        while (currentNode != null) {      // Exits when all elements have been checked
            if (criteriaMatches(currentNode, origin, destination, category)) {
                System.out.printf(findMsg, iterations);
                return currentNode;
            }
            iterations++;
            currentNode = currentNode.link;
        }
        return null;
    }


    /**
     * Inserts a new node at the given index, with the given tariff data. The new node points
     * to the node previously at that index, and the one preceding the index points to the new node.
     * THIS METHOD CANNOT ADD A NODE TO THE END OF A LIST
     * @param tariff the value of the new node
     * @param index the new node will be inserted right before this. Ex: at 0, new node is inserted before head
     * @throws NoSuchElementException if the index is out of bounds
     */
    public void insertAtIndex(Tariff tariff, int index) throws NoSuchElementException {
        if (index == 0) {
            addToStart(tariff);
            return;
        }
        try {
            TariffNode newNode = new TariffNode(tariff, null);
            TariffNode nodeBefore = findAtIndex(index - 1);          // Get the node positioned before the target index
            newNode.link = nodeBefore.link;                           // Set new node's link
            nodeBefore.link = newNode;                                // Set behind node's link to new node
            size++;
        } catch (IndexOutOfBoundsException e) {
//            throw new NoSuchElementException("Index out of bounds in Linked List.\nIndex: " + index + "\nSize: " + size);
            throw new NoSuchElementException(e.getMessage());         // Gives a message for empty lists too
        }
    }

    /**
     * Removes the node at the given index.
     * @param index position of node to be deleted
     * @throws NoSuchElementException if the index is not in the list's range. Program should terminate
     */
    public void deleteFromIndex(int index) throws NoSuchElementException {
        if (index == 0) {
            deleteFromStart();
            return;
        }
        try {
            TariffNode nodeBefore = findAtIndex(index - 1);          // Get the node positioned before the target index
            nodeBefore.link = nodeBefore.link.link;                  // Just point over the next node. Also works for the tail since the link will be null.
            size--;
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException(e.getMessage());         // Gives a message for empty lists as well
            // TODO CATCH THIS MEANS TERMINATE PROGRAM
        }
    }

    public void deleteFromStart() {
        if (size == 0) {
            return;
        }
        head = head.link;   // For 1 element in the list, the head's link will be null ✅
        size--;
    }

    public void replaceAtIndex(Tariff tariff, int index) {
        TariffNode replacement = new TariffNode(tariff, null);
        if (index == 0) {
            replacement.link = head.link;
            head = replacement;
            return;
        }
        try {
            TariffNode nodeBefore = findAtIndex(index - 1);          // Get the node positioned before the target index
            replacement.link = nodeBefore.link.link;                 // Set new node link to old one's link. Works for end of list
            nodeBefore.link = replacement;                           // Previous points to the new node
        } catch (IndexOutOfBoundsException e) {
            return;                                                  // No action
        }
    }

    public boolean contains(String origin, String destination, String category) {
        return (find(origin, destination, category) != null);
    }

    public String evaluateTrade(double proposedTariff, double minimumTariff) {

    }

    public boolean equals(TariffList other) {}

    // HELPER METHODS
    private TariffNode find(Tariff tariff) {
        return find(tariff.getOriginCountry(), tariff.getDestinationCountry(), tariff.getProductCategory());
    }

    private boolean criteriaMatches(TariffNode node, String origin, String destination, String category) {
        return (node.value.getOriginCountry().equals(origin) &&
                node.value.getDestinationCountry().equals(destination) &&
                node.value.getProductCategory().equals(category));
    }

    /**
     * Finds and returns the node at a given index
     * @param index position of the node
     * @return the node at the given index
     * @throws IndexOutOfBoundsException if the index is no in range of the TariffList, or if the list is empty.
     */
    private TariffNode findAtIndex(int index) throws IndexOutOfBoundsException {
        /*
        Move down list until count = count
        check head
         */
        if (size == 0) {
            throw new IndexOutOfBoundsException("The list contains no elements.");
        }
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index out of bounds in Linked List.\nIndex: " + index + "\nSize: " + size);
        if (index == 0)
            return head;
        else {
            int position = 1;
            TariffNode currentNode = head.link;
            while (position < index) {
                currentNode = currentNode.link;
                position++;
            }
            return currentNode;
        }
    }
}
