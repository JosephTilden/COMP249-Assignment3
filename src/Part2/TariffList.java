package Part2;

import java.util.NoSuchElementException;

public class TariffList implements TariffPolicy {
    private TariffNode head = null;
    private int size;


    public TariffList() {}

    public TariffList(TariffList other) {}

    public int getSize() {
        return size;
    }

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

    /**
     * Creates a new node with the previous head as its link, then makes that the new head of the list
     * @param tariff value for the new node
     */
    public void addToStart(Tariff tariff) {
        head = new TariffNode(tariff, head);
    }

    // TODO implement
    public TariffNode find(String origin, String destination, String category) {}

    public TariffNode find(Tariff tariff) {
        return find(tariff.getOriginCountry(), tariff.getDestinationCountry(), tariff.getProductCategory());
    }

    /**
     * Inserts a new node in front of the given index, with the given tariff data. The new node points
     * to the old one at the given index, and it is pointed to by the node before it, if it exists.
     * @param tariff the value of the new node
     * @param index the new node will be inserted right before this. Ex: at 0, new node is inserted before head
     * @throws NoSuchElementException if the index is out of bounds
     */
    public void insertAtIndex(Tariff tariff, int index) throws NoSuchElementException {
        if (index == 0)
            addToStart(tariff);
        else if (index < 0 || index >= size) {
            throw new NoSuchElementException("Index out of bounds in Linked List.\nIndex: " + index + "\nSize: " + size);
        }
        else {
            TariffNode target = find(tariff);
            // TODO finish insertion for regular indexes
        }
    }

    public void deleteFromIndex(int index) throws NoSuchElementException {}

    public void deleteFromStart() {}

    public void replaceAtIndex(Tariff tariff, int index) {}

    public boolean contains(String origin, String destination, String category) {}

    public String evaluateTrade(double proposedTariff, double minimumTariff) {}

    public boolean equals(TariffList other) {}

}
