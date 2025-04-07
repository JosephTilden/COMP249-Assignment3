public class Tariff{
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

}