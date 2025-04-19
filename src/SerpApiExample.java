import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class SerpApiExample {
    private static final String API_KEY = "API key";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter location: ");
        String location = scanner.nextLine();

        System.out.print("Enter company (optional): ");
        String company = scanner.nextLine();

        System.out.print("Enter social media (e.g., linkedin, facebook, instagram): ");
        String socialMedia = scanner.nextLine().toLowerCase().trim();

        // Construct query
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(name).append(" ").append(location);
        if (!company.isEmpty()) {
            queryBuilder.append(" ").append(company);
        }
        if (!socialMedia.isEmpty()) {
            queryBuilder.append(" site:").append(socialMedia).append(".com");
        }

        try {
            // Encode and build URL
            String query = java.net.URLEncoder.encode(queryBuilder.toString(), "UTF-8");
            String serpApiUrl = "https://serpapi.com/search.json?q=" + query + "&api_key=" + API_KEY;

            // Make HTTP request
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

            // Parse JSON response
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
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
