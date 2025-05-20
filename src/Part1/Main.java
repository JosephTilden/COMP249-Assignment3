//-----------------------------------
// Assignment 3
// Question: Part 1: ArrayList & File I/O
// Written by: Zayden Kung'u (40311065) & Joseph Tilden (40317545)
//-----------------------------------


package Part1;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This program
 * 1. Reads trade data from a file
 * 2. Applies tariffs to the trade prices
 * 3. Sorts the products by product name, alphabetically
 * 4. Writes the new trade data to a file
 */
public class Main {

    public static double twoDecimalRound(double userDouble){
        return (double) Math.round(userDouble*100)/100;
    }

    /**
     * Applies tariff increases to trades (Product prices) based on table given in assigment
     * @param product holds trade data for target trade
     */
    public static void imposeTariffs(Product product) {
        String category = product.getCategory().toLowerCase();
        double initPrice = product.getPrice();
        double chinaMultiplier = 1.25;
        double usaMultiplier = 1.10;
        double japanMultiplier = 1.15;
        double indiaMultiplier = 1.05;
        double southKoreaMultiplier = 1.08;
        double saudiArabiaMultiplier = 1.12;
        double germanyMultiplier = 1.06;
        double bangladeshMultiplier = 1.04;
        double brazilMultiplier = 1.09;

        switch (product.getCountry().toLowerCase()) {
            case "china":
                product.setPrice(twoDecimalRound(initPrice*chinaMultiplier));
                break;
            case "usa":
                if (category.equals("electronics"))
                    product.setPrice(twoDecimalRound(initPrice*usaMultiplier));
                break;
            case "japan":
                if (category.equals("automobile"))
                    product.setPrice(twoDecimalRound(initPrice*japanMultiplier));
                break;
            case "india":
                if (category.equals("agriculture"))
                    product.setPrice(twoDecimalRound(initPrice*indiaMultiplier));
                break;
            case "south korea":
                if (category.equals("electronics"))
                    product.setPrice(twoDecimalRound(initPrice*southKoreaMultiplier));
                break;
            case "saudi arabia":
                if (category.equals("energy"))
                    product.setPrice(twoDecimalRound(initPrice*saudiArabiaMultiplier));
                break;
            case "germany":
                if (category.equals("manufacturing"))
                    product.setPrice(twoDecimalRound(initPrice*germanyMultiplier));
                break;
            case "bangladesh":
                if (category.equals("textile"))
                    product.setPrice(twoDecimalRound(initPrice*bangladeshMultiplier));
                break;
            case "brazil":
                if (category.equals("agriculture"))
                    product.setPrice(twoDecimalRound(initPrice*brazilMultiplier));
                break;
            default:
        }
    }

    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList<Product>();
        final String tradeData = "src/Part1/TradeData.txt";

        // Read da file
        Scanner scan = null;
        try {
            scan = new Scanner(new FileInputStream(tradeData));
            String trade;
            String[] tradeFields = {};
            Product product;
            do  {
                trade = scan.nextLine();
                tradeFields = trade.split(",");
                product = new Product(tradeFields[0], tradeFields[1],tradeFields[2], Double.parseDouble(tradeFields[3]));
                imposeTariffs(product);
                products.add(product);
            } while ((scan.hasNextLine()));

            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("There was an error reading the trade data file. More info:\n" + e.getMessage());
            System.exit(0);
        } catch (InputMismatchException e) {
            System.out.println("There is an improperly formatted line in the current file. Please resolve it. More details:\n" + e.getMessage());      // TODO not sure if this should exit the program?
            assert scan != null;
            scan.close();//Close the scanner regardless of the outcome
            System.exit(0);
        }

        /*
        I-I-I be poppin bottles....
         */

        // Write da file
        String updatedTradeData = "src/Part1/UpdatedTradeData.txt";
        try{
            PrintWriter outputStream = new PrintWriter(new FileOutputStream(updatedTradeData,false));
            outputStream.print("");//Clear the old content
            outputStream.close(); //USED TO WIPE THE CONTENTS OF THE FILE
            outputStream = new PrintWriter(new FileOutputStream(updatedTradeData, true));
            Product.sort(products);
            for (int i=0;i<products.size();i++){
                outputStream.println(products.get(i).toString());
            }
            outputStream.close();//FINALLY CLOSING THE PRINT WRITER
        } catch (FileNotFoundException e){
            System.out.print(e.getMessage());
            System.exit(0);
        }
    }
}