//-----------------------------------
// Assignment 3
// Question: Part 1: ArrayList & File I/O
// Written by: Zayden Kung'u (40311065) & Joseph Tilden (40317545)
//-----------------------------------

package Part2;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Contains the main method which reads the tariffs and trade requests files, storing their data into lists.
 * While reading the trade requests, it evaluates the proposed tariffs and outputs the outcome in the console. Later,
 * the remaining TariffList methods that have not been used are demonstrated to show that they work.
 */
public class TradeManager {
    //Tariff Objects are simply the RULES
    //TradeRequests are the actual actions

    public static double twoDecimalRound(double userDouble){
        return (double) Math.round(userDouble*100)/100;
    }
    public static void main(String[] args) {
        System.out.println("Welcome to Joe and Zayden's Thugnificent Tariff Management System 🗣️\n");
        TariffList tariffList1 = new TariffList(); //List used to add the initial tariffs
        TariffList tariffList2 = new TariffList(); //For copy constructor
        TariffList tariffList3 = new TariffList(); //For clone method
        ArrayList<TradeRequest> allTradeRequests = new ArrayList<TradeRequest>();
        Scanner reader = null;
        String currentLine;
        try {
            // READING TARIFFS FILE
            System.out.println("========= Reading Tariffs File =========\n");
            reader = new Scanner(new FileReader("src/Part2/Tariffs.txt"));
            while (reader.hasNextLine()){//Ensure there are lines to read
                currentLine = reader.nextLine();
                String[] tariffAttributesData = currentLine.split(" ");
                //All tariff have the same format: Destination(index:0) Origin(index:1) Product(index:2) Minimum(index:3)
                String tempDestinationCountry = tariffAttributesData[0];
                String tempOriginCountry = tariffAttributesData[1];
                String tempProductCategory = tariffAttributesData[2];

                if (!(tariffList1.contains(tempOriginCountry,tempDestinationCountry,tempProductCategory))){//Checking whether the tariff already exists
                    double tempMinimumTariff = Double.parseDouble(tariffAttributesData[3]);
                    tariffList1.addToStart(new Tariff(tempDestinationCountry,tempOriginCountry,tempProductCategory,tempMinimumTariff));
                }
            }
            // READING THE TRADE REQUESTS FILE
            System.out.println("\n========= Evaluating Trade Requests =========\n");
            reader = new Scanner (new FileReader("src/Part2/TradeRequests.txt"));
            while (reader.hasNextLine()){//Ensure there are lines to read
                currentLine = reader.nextLine(); // Create the trade request object
                String[] tradeRequestsAttributes = currentLine.split(" ");
                String tempRequestID = tradeRequestsAttributes[0];
                String tempOriginCountry = tradeRequestsAttributes[1];
                String tempDestinationCountry = tradeRequestsAttributes[2];
                String tempProductCategory = tradeRequestsAttributes[3];
                double tempTradeValue = Double.parseDouble(tradeRequestsAttributes[4]);
                double tempProposedTariff = Double.parseDouble(tradeRequestsAttributes[5]);

                TradeRequest tempTradeRequest = new TradeRequest(tempRequestID, tempOriginCountry, tempDestinationCountry, tempProductCategory, tempTradeValue, tempProposedTariff);
                allTradeRequests.add(tempTradeRequest);

                // Compare trade request to minimums
                if (tariffList1.contains(tempOriginCountry, tempDestinationCountry, tempProductCategory)) {     // Just to demonstrate contains method. Could be more efficient by using find once
                    double targetMinimum = tariffList1.find(tempOriginCountry,tempDestinationCountry,tempProductCategory).getValue().getMinimumTariff(); //Getting the node's value (Tariff object), and accessing the tariff object's minimum tariff
                    System.out.println("Evaluating...\n" + tempTradeRequest);
                    String tradeEvaluation = tariffList1.evaluateTrade(tempProposedTariff, targetMinimum);
                    System.out.println("==> " + tradeEvaluation);
                    // Surcharge calculation
                    if (tradeEvaluation.contains("CONDITIONALLY ACCEPTED")) {
                        double surcharge = twoDecimalRound(tempTradeValue * ((targetMinimum - tempProposedTariff) / 100));
                        System.out.printf("==> A surcharge of $%.2f is applied.\n\n", surcharge);
                    } else {
                        System.out.println();
                    }
                } else {//No tariff matches the request
                    System.out.println(tempRequestID+" - No tariffs found.\n");
                }                              
            }//FINISHED READING ALL TRADE REQUESTS
            reader.close();
        } catch (FileNotFoundException e){//If any of the files are not found for some reason
            System.out.println("Invalid file name given. More details:\n" + e.getMessage());
            System.exit(0);
        }catch(InputMismatchException e) {
            System.out.println("There is an improperly formatted line in the current file. More details:\n" + e.getMessage());      // TODO not sure if this should exit the program?
            assert reader != null;
            reader.close();//Close the scanner regardless of the outcome
            System.exit(0);
        }


        // USER SEARCH FOR TARIFFS
        System.out.println("========= Search for a Tariff =========\n");
        reader = new Scanner(System.in);
        Tariff targetTariff = null;
        while (true) {
            System.out.print("""
                    To search for a tariff, enter the origin, destination, and category seperated by spaces.
                    To continue to the method demonstration, enter C.
                    >""");
            String origin = reader.next();
            if (origin.equalsIgnoreCase("C")) {
                break;
            }
            String destination = reader.next();
            String category = reader.next();
            if (tariffList1.contains(origin, destination, category)) {
                targetTariff = tariffList1.find(origin, destination, category).getValue();
                System.out.println("Tariff found: " + targetTariff + "\n");
            } else {
                System.out.println("The tariff you are searching for does not exist.\n");
            }
        }

        // TARIFF LIST METHOD TESTING
        System.out.println("\n========= Demonstration of Methods =========\n");
        /*
        Methods to test:


         */

    }
}