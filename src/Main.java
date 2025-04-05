import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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



    }
}