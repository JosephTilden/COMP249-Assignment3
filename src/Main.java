import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void imposeTariffs(Product product) {
        /*
        TODO Swtich for country and then conditionals within that
         */

    }

    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList<Product>();
        final String tradeData = "./TradeData";

        // Read da file
        try {
            Scanner scan = new Scanner(new FileInputStream(tradeData));
            String trade;
            String[] tradeFields = {};
            Product product;
            while (scan.hasNextLine()) {
                trade = scan.nextLine();
                tradeFields = trade.split(",");
                product = new Product(tradeFields[0], tradeFields[1],tradeFields[2], Double.parseDouble(tradeFields[3]));
                imposeTariffs(product);
                products.add(product);
            }
        } catch (FileNotFoundException e) {
            // TODO STUFF
        }



        /*
        I-I-I be poppin bottles....
         */



    }
}