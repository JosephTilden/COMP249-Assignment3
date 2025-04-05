public class Product {
    private String productName = "";
    private String country = "";
    private String category = "";
    private double initialPrice = 0;

    public Product(String productName, String country, String category, double initialPrice) {
        this.productName = productName;
        this.country = country;
        this.category = category;
        this.initialPrice = initialPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public int compareTo(Product other) {
        return this.productName.compareTo(other.productName);
    }
}
