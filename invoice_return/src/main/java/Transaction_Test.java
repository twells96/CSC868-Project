import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Tim
 */
public class Transaction_Test {
    public static void main (String[] args) throws FileNotFoundException{
        //read in products
        //map for holding product info. Key String with List String values
        HashMap<String, List<Object>> product_info_storage = new HashMap<String, List<Object>>();
        File product_file = new File("C:\\Users\\lemon\\Documents\\NetBeansProjects\\invoice_return\\src\\main\\java\\products.txt");
        File customer_file = new File("C:\\Users\\lemon\\Documents\\NetBeansProjects\\invoice_return\\src\\main\\java\\customers.txt");
        Scanner product_file_reader = new Scanner(product_file);
        Scanner custom_file_reader = new Scanner(customer_file);
        while(product_file_reader.hasNextLine())
        {
            //get a line
            String product_reader_token = product_file_reader.nextLine();
            //separate by comma
            String[] separated_tokens = product_reader_token.split(",");
            //here we need to turn 'Product_Name: Candy' into 'Candy'
            String[] product_upc_splitter = separated_tokens[0].split(":");
            String[] product_description_splitter = separated_tokens[1].split(": ");
            String[] product_price_splitter = separated_tokens[2].split(":");
            String product_upc = product_upc_splitter[1].replaceAll("\\s", "");
            String product_description = product_description_splitter[1];
            float product_price = Float.parseFloat(product_price_splitter[1].trim());
            //List creation
            List<Object> product_description_price = new ArrayList<Object>();
            //add everything else to the list
            product_description_price.add(product_price);
            product_description_price.add(product_description);
            //wait til end to add product name as key for list and put into map
            product_info_storage.put(product_upc, product_description_price);
        }
        //for (Map.Entry<String, List<Object>> entry : product_info_storage.entrySet()) {
            
        //System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        
        //}
        //read in client requests
        while(custom_file_reader.hasNextLine())
        {
            //get a line
            String customer_reader_token = custom_file_reader.nextLine();
            //separate by comma
            String[] separated_tokens = customer_reader_token.split(",");
            //  read in client name, id, which products they want and how many
            String[] customer_name_splitter = separated_tokens[0].split(":");
            String customer_name = customer_name_splitter[1].replaceAll("\\s", "");
            //get customer upc
            String[] customer_upc_splitter = separated_tokens[1].split(":");
            String customer_upc = customer_upc_splitter[1].replaceAll("\\s", "");
            //get date and time
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            //  product if in supply. Refuse client request if product is not in supply
            if (product_info_storage.containsKey(customer_upc))
        {
            String[] customer_quantity_splitter = separated_tokens[2].split(":");
            Integer customer_quantity = Integer.parseInt(customer_quantity_splitter[1].trim());
            //create invoice
            //  invoice contains client name, product name, product price
            //  maybe add date and time if easy and invoice id.
            //return/print invoice
            float total = Float.parseFloat(product_info_storage.get(customer_upc).get(0).toString()) * customer_quantity;
            String invoice = "STORE NAME" + "\n" + customer_name + "\t" + dtf.format(now) + "\n" + product_info_storage.get(customer_upc).get(1) + " " + customer_quantity + " $" + product_info_storage.get(customer_upc).get(0) + "\n" + "------" + "\n" + "Total " + " $" + total + "\n";
            System.out.println(invoice);
        }
            else {
                //set invoice message to "sorry we don't have that product ____name :("
                String invoice = "sorry we don't have that product, " + customer_name + " :(" + "\n";
                System.out.println(invoice);
            }
        }
    }
}
