import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {

    private static final String NUMVERIFY_API_KEY = "NUMVERIFY_API_KEY";
    private static final String SERP_API_KEY = "SERP_API_KEY";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Lookup Phone Number (Numverify)");
            System.out.println("2. Search Person on Social Media (SerpApi)");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("Exiting program.");
                break;
            }

            switch (choice) {
                case 1:
                    lookupPhoneNumber(scanner);
                    break;
                case 2:
                    searchSocialMedia(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void lookupPhoneNumber(Scanner scanner) {
        try {
            System.out.print("Enter mobile or landline number (with or without +91): ");
            String phoneNumber = scanner.nextLine().replaceAll("[^0-9+]", "");

            if (!phoneNumber.startsWith("+")) {
                phoneNumber = "+91" + phoneNumber;
            }

            String apiUrl = "http://apilayer.net/api/validate?access_key=" + NUMVERIFY_API_KEY + "&number=" + phoneNumber;

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

                System.out.println("\nPhone Number Details:");
                System.out.println("Valid: " + jsonResponse.getBoolean("valid"));
                System.out.println("Number: " + jsonResponse.optString("number", "N/A"));
                System.out.println("Local Format: " + jsonResponse.optString("local_format", "N/A"));
                System.out.println("International Format: " + jsonResponse.optString("international_format", "N/A"));
                System.out.println("Country Prefix: " + jsonResponse.optString("country_prefix", "N/A"));
                System.out.println("Country Code: " + jsonResponse.optString("country_code", "N/A"));
                System.out.println("Country Name: " + jsonResponse.optString("country_name", "N/A"));
                System.out.println("Location: " + jsonResponse.optString("location", "N/A"));
                System.out.println("Carrier: " + jsonResponse.optString("carrier", "N/A"));
                System.out.println("Line Type: " + jsonResponse.optString("line_type", "Unknown"));

            } else {
                System.out.println("Failed to fetch data. HTTP Error Code: " + status);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchSocialMedia(Scanner scanner) {
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter location: ");
            String location = scanner.nextLine();

            System.out.print("Enter company (optional): ");
            String company = scanner.nextLine();

            System.out.print("Enter social media (e.g., linkedin, facebook, instagram): ");
            String socialMedia = scanner.nextLine().toLowerCase().trim();

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(name).append(" ").append(location);
            if (!company.isEmpty()) queryBuilder.append(" ").append(company);
            if (!socialMedia.isEmpty()) queryBuilder.append(" site:").append(socialMedia).append(".com");

            String query = URLEncoder.encode(queryBuilder.toString(), "UTF-8");
            String serpApiUrl = "https://serpapi.com/search.json?q=" + query + "&api_key=" + SERP_API_KEY;

            URL url = new URL(serpApiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray results = json.getJSONArray("organic_results");

            System.out.println("\nTop Results:");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                System.out.println((i + 1) + ". " + result.getString("title"));
                System.out.println("   Link: " + result.getString("link"));
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
