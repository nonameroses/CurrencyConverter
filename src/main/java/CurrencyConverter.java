import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class CurrencyConverter {


    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        // Exchange Rate API, the last word after / must be a base currency to convert from
        String url_str = "https://v6.exchangerate-api.com/v6/f6ca023fbac9d74f583863e6/latest/" + currencyFrom();

        // Making a Request
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Convert to JSON
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonObject = root.getAsJsonObject();


        while (true) {
            System.out.println("Enter a currency to convert to");

            System.out.println("GBP || USD || EUR || NZD || JPY || CNY || BDT || AUD");
            // Taking input to convert to
            String currency = scanner.nextLine();

            // For loop to check every key in the API
            for (Map.Entry<String, JsonElement> entries : jsonObject.getAsJsonObject("conversion_rates").entrySet()) {
                // Checking if there is corresponding key to the input
                if (Objects.equals(entries.getKey(), currency.toUpperCase(Locale.ROOT))) {
                    // Showing translation value
                    System.out.println("Translation Ratio from " + entries.getValue() + " " + entries.getKey());
                    // Getting value as a double
                    double value = entries.getValue().getAsDouble();
                    System.out.println("Enter the amount you want to convert");
                    // Input for amount to convert
                    double amount = scanner.nextDouble();
                    // Calculation currency * amount
                    System.out.println(value * amount + " " + entries.getKey());
                    break;
                }

            }


        }
    }


    public static String currencyFrom() throws IOException {
        Scanner input = new Scanner(System.in);

        // Putting some currencies into a TreeMap
        System.out.println("Select currency convert from");
        Map<String, String> currencies = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        currencies.put("GBP", "British Pound");
        currencies.put("USD", "American Dollar");
        currencies.put("EUR", "Euro");
        currencies.put("NZD", "New Zealand Dollar");
        currencies.put("JPY", "Japanese Yen");
        currencies.put("CNY", "Chinese Yuan");
        currencies.put("BDT", "Bangladesh Taka");
        currencies.put("AUD", "Australian Dollar");

        while (true) {
            System.out.println("GBP || USD || EUR || NZD || JPY || CNY || BDT || AUD");
            // Selecting currency
            String currency = input.nextLine();
            // Checking if there is corresponding key to the input
            if (currencies.containsKey(currency)) {
                System.out.println("You picked " + currencies.get(currency));

                return currency;
            } else {
                System.out.println("Currency you wrote does not exists, please try again");
            }

        }

    }


}
