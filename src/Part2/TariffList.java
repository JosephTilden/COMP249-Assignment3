//-----------------------------------
// Assignment 3
// Question: Part 2: Linked Lists
// Written by: Zayden Kung'u (40311065) & Joseph Tilden (40317545)
//-----------------------------------


package Part2;

import java.util.NoSuchElementException;

/**
 * An array list with TariffNodes as a public static inner class. The reason for public static is
 * explained within. It implements methods for modifying the list and finding nodes, as well as a trade evaluation
 * method.
 */
public class TariffList implements TariffPolicy {
    private TariffNode head = null;
    private int size = 0;

    public TariffList() {}

    /**
     * Creates a duplicate TariffList where each node has the same Tariff value, while remaining a deep copy.
     * @param other list to copy
     */
    public TariffList(TariffList other) {
        if (other.size == 0) {
            head = null;
            size = 0;
        } else {
            size = other.size;
            head = new TariffNode(other.head);
            TariffNode ogCurrentNode = other.head;
            TariffNode copyCurrentNode = head;
            while (ogCurrentNode.link != null) {       // Includes final element
                ogCurrentNode = ogCurrentNode.link;
                copyCurrentNode.link = new TariffNode(ogCurrentNode);
                copyCurrentNode = copyCurrentNode.link;
            }
        }
    }

    public int getSize() {
        return size;
    }

    /**
     * The TariffNode class must be public because the find() method of the linked list returns a TariffNode, not a
     * Tariff object. So, TradeManager driver needs to be able to access TariffNodes as well. Also because the node
     * clone() method needs to be tested in the driver
     */
    public class TariffNode implements Cloneable {
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
            this.link = null;
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
                copy.link = null;
                return copy;
            } catch (CloneNotSupportedException e){
                return null;
            }
        }

        /**
         * Checks to see if two nodes have the same data. Does not check to see if they have the same tail, since that
         * would require making this recursive, and I think that would make it call .equals() on every node that follows
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

    /**
     * Finds a TariffNode in the TariffList based on 3 descriptors. Also prints total amount of iterations used to find
     * the target Node.
     *
     * THIS METHOD ALLOWS A PRIVACY LEAK since it returns a TariffNode, which is an object of the inner class.
     * Because of this method, the inner class must be public so that the driver can use it for its intended function.
     *
     * @param origin product country of origin
     * @param destination product destination country
     * @param category type of product
     * @return found tariffNode or null if no nodes match
     */
    public TariffNode find(String origin, String destination, String category) {
        TariffNode currentNode = head;
        int iterations = 1;
        String findMsg = "(Find method used: %d iterations)\n";
        if (size == 0 || (!criteriaMatches(head, origin, destination, category) && size == 1)) {
            System.out.printf(findMsg, iterations);
            return null;
        }
        if (criteriaMatches(head, origin, destination, category)) {
            System.out.printf(findMsg, iterations);    // Should be 1 iteration
            return head;
        }
        currentNode = currentNode.link;     // Start at 2nd element
        while (currentNode != null) {      // Exits when all elements have been checked
            iterations++;
            if (criteriaMatches(currentNode, origin, destination, category)) {
                System.out.printf(findMsg, iterations);
                return currentNode;
            }
            currentNode = currentNode.link;
        }
        System.out.printf(findMsg, iterations);
        return null;
    }


    /**
     * Inserts a new node at the given index, with the given tariff data. The new node points
     * to the node previously at that index, and the one preceding the index points to the new node.
     * @param tariff the value of the new node
     * @param index the new node will be inserted right before this. Ex: at 0, new node is inserted before head
     * @throws NoSuchElementException if the index is out of bounds
     */
    public void insertAtIndex(Tariff tariff, int index) throws NoSuchElementException {
        if (index == 0) {
            addToStart(tariff);
            return;
        }
        if (index == size) {
            throw new NoSuchElementException("Insertion aborted. Index range is 0-" + (size - 1));
        }
        try {
            TariffNode newNode = new TariffNode(tariff, null);
            TariffNode nodeBefore = findAtIndex(index - 1);          // Get the node positioned before the target index
            newNode.link = nodeBefore.link;                           // Set new node's link
            nodeBefore.link = newNode;                                // Set behind node's link to new node
            size++;
        } catch (IndexOutOfBoundsException e) {
//            throw new NoSuchElementException("Index out of bounds in Linked List.\nIndex: " + index + "\nSize: " + size);
//            throw new NoSuchElementException("Insertion aborted. Issue finding node preceding insertion: " + e.getMessage());         // Gives a message for empty lists too
            throw new NoSuchElementException("Insertion aborted. Index range is 0-" + (size - 1));
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
        if (index == size) {
            throw new NoSuchElementException("Deletion aborted. Index range is 0-" + (size - 1));
        }
        try {
            TariffNode nodeBefore = findAtIndex(index - 1);          // Get the node positioned before the target index
            nodeBefore.link = nodeBefore.link.link;                  // Just point over the next node. Also works for the tail since the link will be null.
            size--;
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException("Deletion aborted. Index range is 0-" + (size - 1));         // Gives a message for empty lists as well
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
        if (index == size) {
            return;                                                  // Out of bounds: no action
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
        System.out.print("Contains method --> ");
        return (find(origin, destination, category) != null);
    }

    /**
     * Compares the proposed tariff from a trade request with the established minimum tariff.
     * Accepted: proposed meets or exceeds minimum
     * Conditionally accepted: The proposed tariff is lower than the minimum but within 20% of it. A surcharge is applied
     * Rejected: The proposed tariff is more than 20% below the required tariff
     * @param proposedTariff from trade request
     * @param minimumTariff from tariff minimums list
     * @return a string indicating if the trade is accepted, conditionally accepted, or rejected
     */
    public String evaluateTrade(double proposedTariff, double minimumTariff) {
        proposedTariff = twoDecimalRound(proposedTariff);
        minimumTariff = twoDecimalRound(minimumTariff);

        if (proposedTariff >= minimumTariff) {
            return "ACCEPTED: Proposed tariff ("+proposedTariff+"%) meets or exceeds the minimum requirement ("+minimumTariff+"%).";
        }

        boolean within20Percent = proposedTariff >= minimumTariff * 0.8;
        if (within20Percent) {
            return String.format("CONDITIONALLY ACCEPTED: Proposed tariff (%.1f%%) is within 20%% of the required minimum tariff (%.1f%%).",
                    proposedTariff, minimumTariff);
        }
        return "REJECTED: Proposed tariff ("+proposedTariff+"%) is below the minimum requirement ("+minimumTariff+"%).";
    }

    /**
     * Compares the Tariffs in each node of both lists.
     * @param other TariffList to compare to
     * @return equality of Tariff information for each node
     */
    public boolean equals(TariffList other) {
        if (size != other.size) {
            return false;
        }
        if (this == other) {
            return true;
        }
        TariffNode ogCurrentNode = head;
        TariffNode otherCurrentNode = other.head;
        while (ogCurrentNode != null) {       // Includes final element
            if (!(ogCurrentNode.equals(otherCurrentNode))) {
                return false;
            }
            ogCurrentNode = ogCurrentNode.link;
            otherCurrentNode = otherCurrentNode.link;
        }
        return true;
    }

    public void displayAll() {
        if (head == null) {
            System.out.println("Empty list");
        }
        TariffNode current = head;
        int index = 0;
        while (current != null) {
            System.out.print("Index: " + index++ + " --> ");
            System.out.println(current.getValue());
            current = current.link;
        }
    }

    // HELPER METHODS
    private TariffNode find(Tariff tariff) {
        return find(tariff.getOriginCountry(), tariff.getDestinationCountry(), tariff.getProductCategory());
    }

    private boolean criteriaMatches(TariffNode node, String origin, String destination, String category) {
        return (node.value.getOriginCountry().equalsIgnoreCase(origin) &&
                node.value.getDestinationCountry().equalsIgnoreCase(destination) &&
                node.value.getProductCategory().equalsIgnoreCase(category));
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

    private static double twoDecimalRound(double number){
        return (double) Math.round(number*100)/100;
    }
}