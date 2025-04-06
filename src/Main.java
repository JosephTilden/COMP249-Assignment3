import java.io.*;
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
            scan.close();
        } catch (FileNotFoundException e) {
            // TODO STUFF
        }



        /*
        I-I-I be poppin bottles....
         */
        try{
            PrintWriter outputStream = new PrintWriter(new FileOutputStream("UpdatedTradeData.txt",false));
            outputStream.print("");//Clear the old content
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