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

    private class TariffNode {
        private Tariff value = null;
        private TariffNode link = null;

        public TariffNode() {
        }

        public TariffNode(Tariff value, TariffNode link) {
            this.value = value;
            this.link = link;
        }

        public TariffNode(TariffNode other) {}

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

        // TODO research
        public TariffNode clone() {}

        public boolean equals(TariffNode other) {}
    }

    public void addToStart(Tariff tariff) {}

    public void insertAtIndex(Tariff tariff, int index) throws NoSuchElementException {}

    public void deleteFromIndex(int index) throws NoSuchElementException {}

    public void deleteFromStart() {}

    public void replaceAtIndex(Tariff tariff, int index) {}

    public TariffNode find(String origin, String destination, String category) {}

    public boolean contains(String origin, String destination, String category) {}

    public String evaluateTrade(double proposedTariff, double minimumTariff) {}

    public boolean equals(TariffList other) {}

}
