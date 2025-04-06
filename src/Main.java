import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList<Product>();
        final String tradeData = "./TradeData";

        // Read da file
        try {
            Scanner scan = new Scanner(new FileInputStream(tradeData));
            /*
            Scan line by line
            if a line is incomplete ignore it <-- mismatch
            Put all fields into constructor
             */


            /*
            Check for tariffs and modify of needed
            Add new product object to arrayList
             */

        } catch (FileNotFoundException e) {
            // DO STUFF
        }



        /*
        I-I-I be poppin bottles....
         */
        try{
            PrintWriter outputStream = new PrintWriter(new FileOutputStream("UpdatedTradeData.txt"));
            outputStream.close(); //USED TO WIPE THE CONTENTS OF THE FILE
            outputStream = new PrintWriter(new FileOutputStream("UpdatedTradeData.txt", true));
            Product.sort(products);
            for (int i=0;i<products.size();i++){
                outputStream.println(products.get(i).toString());
            }
            outputStream.close();//FINALLY CLOSING THE PRINT WRITER
        } catch (FileNotFoundException e){
            System.out.print(e.getMessage());
        }



    }
}