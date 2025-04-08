//-----------------------------------
// Assignment 3
// Question: Part 1: ArrayList & File I/O
// Written by: Zayden Kung'u (40311065) & Joseph Tilden (40317545)
//-----------------------------------

package Part2;

public class TradeRequest {
    private String requestID;
    private String originCountry;
    private String destinationCountry;
    private String productCategory;
    private int tradeValue;
    private int proposedTariff;

    public TradeRequest(String requestID, String originCountry, String destinationCountry, String productCategory, int tradeValue, int proposedTariff){
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
        return this.getOriginCountry();
    }

    public String getDestinationCountry(){
        return this.getDestinationCountry();
    }

    public String getProductCategory(){
        return this.productCategory;
    }

    public int getTradeValue(){
        return this.tradeValue;
    }

    public int getProposedTariff(){
        return this.getProposedTariff();
    }
}
