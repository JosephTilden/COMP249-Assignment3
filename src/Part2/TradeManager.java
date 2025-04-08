package Part2;

import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;


public class TradeManager {
    //Tariff Objects are simply the RULES
    //TradeRequests are the actual actions

    public static void main(String[] args) {
        TariffList tariffList1 = new TariffList(); //List used to add the initial tariffs
        TariffList tariffList2 = new TariffList(); //Not sure what the second is for yet
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
                /*
                 * ZAYDEN CODES CONTINUES HERE
                 */

            }

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
       