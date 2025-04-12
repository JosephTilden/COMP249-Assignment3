//-----------------------------------
// Assignment 3
// Question: Part 1: ArrayList & File I/O
// Written by: Zayden Kung'u (40311065) & Joseph Tilden (40317545)
//-----------------------------------

package Part2;

/**
 * Holds the minimum tariff information for specific kinds of trades between certain countries.
 */
public class Tariff implements Cloneable {
    private String destinationCountry;
    private String originCountry;
    private String productCategory;
    private double minimumTariff;

    public Tariff(String destinationCountry,String originCountry, String productCategory, double minimumTariff){
        this.destinationCountry=destinationCountry;
        this.originCountry=originCountry;
        this.productCategory=productCategory;
        this.minimumTariff=minimumTariff;
    }

    public Tariff(Tariff otherTariff){
        this.destinationCountry=otherTariff.getDestinationCountry();
        this.minimumTariff=otherTariff.getMinimumTariff();
        this.originCountry=otherTariff.getOriginCountry();
        this.productCategory=otherTariff.getProductCategory();
    }

    public String getDestinationCountry(){
        return this.destinationCountry;
    }

    public double getMinimumTariff(){
        return this.minimumTariff;
    }

    public String getOriginCountry(){
        return this.originCountry;
    }

    public String getProductCategory(){
        return this.productCategory;
    }

    public void setDestinationCountry(String DestinationCountry){
        this.destinationCountry=DestinationCountry;
    }

    public void setMinimumTariff(double minimumTariff){
        this.minimumTariff=minimumTariff;
    }

    public void setOriginCountry(String originCountry){
        this.originCountry=originCountry;
    }

    public void setProductCategory(String productCategory){
        this.productCategory=productCategory;
    }

    /**
     * Non mutable instance variables implementation
     * @return a deep copy of a Tariff
     */
    public Tariff clone(){
        try {
            return (Tariff) super.clone();
        } catch (CloneNotSupportedException e) {
            return null; // Never happens
        }
    }

    public String toString(){
        return this.destinationCountry+","+this.originCountry+","+this.productCategory+","+this.minimumTariff;
    }

    public boolean equals(Tariff otherTariff){
        if (Math.abs(this.minimumTariff-otherTariff.getMinimumTariff())<0.0000001){
            return (this.destinationCountry.equals(otherTariff.getDestinationCountry())&&this.originCountry.equals(otherTariff.getOriginCountry())&&this.productCategory.equals(otherTariff.getProductCategory()));
        }else{
            return false;
        }
    }
}