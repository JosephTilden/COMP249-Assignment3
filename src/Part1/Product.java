//-----------------------------------
// Assignment 3
// Question: Part 1: ArrayList & File I/O
// Written by: Zayden Kung'u (40311065) & Joseph Tilden (40317545)
//-----------------------------------

package Part1;

import java.util.ArrayList;

/**
 * Instances of Product are to hold data for a given trade in TradeData.txt
 */
public class Product {
    private String productName = "";
    private String country = "";
    private String category = "";
    private double price = 0;

    public static void sort(ArrayList<Product> list){
        for(int i=0;i<list.size();i++){
            Product lowest = list.get(i);
            int lowestFormerIndex=i;
            for (int j=i;j<list.size();j++){
                if (list.get(j).compareTo(lowest)<0){
                    lowest = list.get(j); //GET NEW SMALLEST ELEMENT
                    lowestFormerIndex=j;
                }
            }
            Product temp = list.get(i); //ASSUMED SMALLEST ELEMENT
            list.remove(i);//REMOVING ASSUMED SMALLEST ELEMENT
            list.add(i,lowest);//ADDING TRUE SMALLEST ELEMENT TO LOWEST INDEX
            list.remove(lowestFormerIndex);//REMOVING TRUE SMALLEST ELEMENT FROM FORMER INDEX
            list.add(lowestFormerIndex,temp);//ADDING ASSUMED SMALLEST ELEMENT TO NEW INDEX
        }
    }

    public Product(String productName, String country, String category, double price) {
        this.productName = productName;
        this.country = country;
        this.category = category;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int compareTo(Product other) {
        return this.productName.compareTo(other.productName);
    }

    public String toString(){
        return productName+","+country+","+category+","+ price;
    }
}
