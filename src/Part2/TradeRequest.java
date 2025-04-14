//-----------------------------------
// Assignment 3
// Question: Part 1: ArrayList & File I/O
// Written by: Zayden Kung'u (40311065) & Joseph Tilden (40317545)
//-----------------------------------

package Part2;

/**
 * Holds data from trade requests. Used to compare with minimum tariffs for trades.
 */
public class TradeRequest {
    private String requestID;
    private String originCountry;
    private String destinationCountry;
    private String productCategory;
    private double tradeValue;
    private double proposedTariff;

    public TradeRequest(String requestID, String originCountry, String destinationCountry, String productCategory, double tradeValue, double proposedTariff){
        this.requestID=requestID;
        this.originCountry=originCountry;
        this.destinationCountry=destinationCountry;
        this.productCategory=productCategory;
        this.tradeValue=tradeValue;
        this.proposedTariff=proposedTariff;
    }

    public void setRequestID(String requestId){
        this.requestID=requestId;
    }

    public void setOriginCountry(String originCountry){
        this.originCountry=originCountry;
    }

    public void setDestinationCountry(String destinationCountry){
        this.destinationCountry=destinationCountry;
    }

    public void setProductCategory(String productCategory){
        this.productCategory=productCategory;
    }

    public void setTradeValue(int tradeValue){
        this.tradeValue=tradeValue;
    }

    public void setProposedTariff(int proposedTariff){
        this.proposedTariff=proposedTariff;
    }

    public String getRequestID(){
        return this.requestID;
    }

    public String getOriginCountry(){
        return this.originCountry;
    }

    public String getDestinationCountry(){
        return this.destinationCountry;
    }

    public String getProductCategory(){
        return this.productCategory;
    }

    public double getTradeValue(){
        return this.tradeValue;
    }

    public double getProposedTariff(){
        return this.proposedTariff;
    }

    public String toString() {
        return String.format("Request %s from %s to %s: %s, worth $%f.2 with %f.2 a percent Tariff", requestID, originCountry, destinationCountry, productCategory, tradeValue, proposedTariff);
    }
}
