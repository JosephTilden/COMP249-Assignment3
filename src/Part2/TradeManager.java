package Part2;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;


public class TradeManager {
    //Tariff Objects are simply the RULES
    //TradeRequests are the actual actions
    public static double twoDigitValue(double userDouble){
        return Math.round(userDouble*100)/100;
    }
    public static void main(String[] args) {
        System.out.println("Welcome to Joe and Zayden's Thugnificent Tariff Management System\n");
        TariffList tariffList1 = new TariffList(); //List used to add the initial tariffs
        TariffList tariffList2 = new TariffList(); //Not sure what the second is for yet
        ArrayList<TradeRequest> allTradeRequests = new ArrayList<TradeRequest>();
        Scanner reader = null;
        String currentLine;
        try {
            //FIRST FILE READ
            reader = new Scanner(new FileReader("./Tariff.txt"));
            while (reader.hasNextLine()){//Ensure there are lines to read
                currentLine = reader.nextLine();
                String[] tariffAttributesData = currentLine.split(" ");
                //All tariff have the same format: Destination(index:0) Origin(index:1) Product(index:2) Minimum(index:3)
                String tempDestinationCountry = tariffAttributesData[0];
                String tempOriginCountry = tariffAttributesData[1];
                String tempProductCategory = tariffAttributesData[2];
                if (tariffList1.contains(tempOriginCountry,tempDestinationCountry,tempProductCategory)){//Checking whether the tariff already exists
                    continue;//Not adding it to the list if it does
                }
                double tempMinimumTariff = Double.parseDouble(tariffAttributesData[3]);
                tariffList1.addToStart(new Tariff(tempDestinationCountry,tempOriginCountry,tempProductCategory,tempMinimumTariff)); 
            }
            reader = new Scanner (new FileReader("./TariffRequests.txt"));
            while (reader.hasNextLine()){//Ensure there are lines to read
                currentLine = reader.nextLine();
                String[] tradeRequestsAttributes = currentLine.split(" ");
                String tempRequestID = tradeRequestsAttributes[0];
                String tempOriginCountry = tradeRequestsAttributes[1];
                String tempDestinationCountry = tradeRequestsAttributes[2];
                String tempProductCategory = tradeRequestsAttributes[3];
                int tempTradeVaue = Integer.parseInt(tradeRequestsAttributes[4]);
                int tempProposedTariff = Integer.parseInt(tradeRequestsAttributes[5]);
                TradeRequest tempTradeRequest = new TradeRequest(tempRequestID, tempOriginCountry, tempDestinationCountry, tempProductCategory, tempProposedTariff, tempProposedTariff);
                allTradeRequests.add(tempTradeRequest);
                if (!tariffList1.find(tempOriginCountry,tempDestinationCountry,tempProductCategory)==null){
                    double possibleMatchMinimum = tariffList1.find(tempOriginCountry,tempDestinationCountry,tempProductCategory).getValue().getMinimumTariff();//Getting the node's value (Tariff object), and accessing the tariff object's minimum tariff
                    if((tempProposedTariff>possibleMatchMinimum||tempProposedTariff-possibleMatchMinimum>0.000001)){//if proposition is equal or greater than tariff
                        System.out.println(tempRequestID+" - Accepted.");
                        System.out.println("Proposed tariff ("+tempProposedTariff+"%) meets or exceeds the minimum requirement ("+possibleMatchMinimum+").\n");
                    }else{
                        double lowestAdjustedMinimum = possibleMatchMinimum - possibleMatchMinimum*0.2;//0.2 represents the 20% buffer of an acceptable tariff & lowestAdjustedMinimum is the smallest percentage the country would accept
                        if(tempProposedTariff>lowestAdjustedMinimum||tempProposedTariff-lowestAdjustedMinimum>0.000001){//if proposition conditionally accepted
                            double surchage = twoDigitValue(tempTradeVaue * ((possibleMatchMinimum-tempProposedTariff)/100));//Surcharge = TradeValue * ((Minimum Tariff - ProposedTariff)/100)
                            System.out.println(tempRequestID+" - Conditionally Accepted.");
                            System.out.println("Proposed tariff ("+tempProposedTariff+"%) is within 20% of the required minimum tariff ("+possibleMatchMinimum+").");
                            System.out.println("A surcharge of $"+surchage+" is applied.\n");
                        }else{//Outright rejection
                            System.out.println(tempRequestID+" - Conditionally Accepted.");
                            System.out.println("Proposed tariff ("+tempProposedTariff+"%) is more than 20% below the required minimum tariff ("+possibleMatchMinimum+").\n");
                        }
                    }
                }else{//No tariff matches the request
                    System.out.println(tempRequestID+" - No tariffs found.\n");
                }                              
            }//FINISHED READING ALL TRADE REQUESTS 

        } catch (FileNotFoundException e){//If the file is not found for some reason
            e.getMessage();
            System.exit(0);//Close entire system
        }catch( InputMismatchException e){
            e.getMessage();
        } catch (Exception e){//If there an unaacounted for exception
            e.getMessage();
            System.exit(0);//Close entire system
        } finally{
            reader.close();//Close the scanner regardless of the outcome
        }
    }
}
       