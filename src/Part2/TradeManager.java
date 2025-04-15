//-----------------------------------
// Assignment 3
// Question: Part 1: ArrayList & File I/O
// Written by: Zayden Kung'u (40311065) & Joseph Tilden (40317545)
//-----------------------------------

package Part2;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
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
        ArrayList<TradeRequest> allTradeRequests = new ArrayList<TradeRequest>();
        Scanner reader = null;
        String currentLine;
        try {
            // READING TARIFFS FILE
            System.out.println("\n========= Reading Tariffs File =========\n\n");
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
            System.out.println("\n\n========= Evaluating Trade Requests =========\n\n");
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
        System.out.println("\n========= Search for a Tariff =========\n\n");
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

        // METHOD TESTING
        System.out.println("\n\n========= Demonstration of Methods =========\n\n");

        System.out.println("--- Clone & equals methods for TariffNode & Tariff ---");
        TariffList.TariffNode ogTariffNode = tariffList1.find("Bangladesh", "India", "Energy");
        TariffList.TariffNode clonedTariffNode = ogTariffNode.clone();
        Tariff ogTariff = ogTariffNode.getValue();
        Tariff clonedTariff = ogTariffNode.clone().getValue();
        System.out.println("TariffNode equals cloned TariffNode: " + ogTariffNode.equals(clonedTariffNode));
        System.out.println("Both point to the same memory location: " + (ogTariffNode == clonedTariffNode));
        System.out.println("Tariff equals cloned Tariff: " + ogTariff.equals(clonedTariff));
        System.out.println("Both point to the same memory location: " + (ogTariff == clonedTariff));

        System.out.println("\n--- Copy constructor and equals method for TariffList ---");
        TariffList copyList = new TariffList(tariffList1);
        System.out.println("List equals copied List : " + tariffList1.equals(copyList));
        System.out.println("Both point to the same memory location: " + (tariffList1 == copyList));

        System.out.println("\n--- Inserting ---");
        System.out.printf("There are %d tariffs in Tariffs.txt\n", copyList.getSize());
        tariffList1.displayAll();

        System.out.println("\nInserting at index 3:");
        Tariff tariff1 = new Tariff("Brazil", "USA", "SECONDHAND DIAPERS", 22);
        Tariff tariff2 = new Tariff("Brazil", "USA", "VINTAGE TANDEM UNICYCLES", 2);
        Tariff tariff3 = new Tariff("Brazil", "USA", "MUTANT TURTLES (ADOLESCENT)", 99);
        copyList.insertAtIndex(tariff3, 3);
        copyList.displayAll();
        System.out.printf("There are %d tariffs in Tariffs.txt\n", copyList.getSize());

        System.out.println("\nInserting out of bounds: ");
        try {
            copyList.insertAtIndex(tariff1, 7);
            copyList.displayAll();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- Deleting ---");
        System.out.println("From index 1: ");
        copyList.deleteFromIndex(1);
        copyList.displayAll();
        System.out.printf("There are %d tariffs in Tariffs.txt\n", copyList.getSize());

        System.out.println("\nFrom an empty list: ");
        TariffList emptyList = new TariffList();
        emptyList.displayAll();
        emptyList.deleteFromStart();

        System.out.println("\n--- Replacing ---");
        System.out.println("At index 3: ");
        copyList.replaceAtIndex(tariff2, 3);
        copyList.displayAll();

        System.out.println("\nReplacing out of bounds: ");
        copyList.replaceAtIndex(tariff2, 7);
        System.out.println("(Just returns)");

        reader.close();
    }
}