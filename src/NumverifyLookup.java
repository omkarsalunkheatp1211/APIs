import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class NumverifyLookup {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String apiKey = "API key";

        System.out.print("Enter mobile or landline number (with or without +91): ");
        String phoneNumber = scanner.nextLine().replaceAll("[^0-9+]", "");

        // Auto-add +91 if not already present
        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+91" + phoneNumber;
        }

        try {
            // Construct the API URL
            String apiUrl = "http://apilayer.net/api/validate?access_key=" + apiKey + "&number=" + phoneNumber;

            // Create the URL object
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int status = connection.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());

                boolean valid = jsonResponse.getBoolean("valid");
                String number = jsonResponse.optString("number", "N/A");
                String localFormat = jsonResponse.optString("local_format", "N/A");
                String internationalFormat = jsonResponse.optString("international_format", "N/A");
                String countryPrefix = jsonResponse.optString("country_prefix", "N/A");
                String countryCode = jsonResponse.optString("country_code", "N/A");
                String countryName = jsonResponse.optString("country_name", "N/A");
                String location = jsonResponse.optString("location", "N/A");
                String carrier = jsonResponse.optString("carrier", "N/A");
                String lineType = jsonResponse.optString("line_type", "Unknown");

                System.out.println("\nPhone Number Details:");
                System.out.println("Valid: " + valid);
                System.out.println("Number: " + number);
                System.out.println("Local Format: " + localFormat);
                System.out.println("International Format: " + internationalFormat);
                System.out.println("Country Prefix: " + countryPrefix);
                System.out.println("Country Code: " + countryCode);
                System.out.println("Country Name: " + countryName);
                System.out.println("Location: " + location);
                System.out.println("Carrier: " + carrier);
                System.out.println("Line Type: " + lineType);

            } else {
                System.out.println("Failed to fetch data. HTTP Error Code: " + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
