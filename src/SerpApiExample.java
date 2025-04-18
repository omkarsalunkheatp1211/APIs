import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class SerpApiExample {
    private static final String API_KEY = "API_Key";

    public static void main(String[] args) {
        try {
            String query = "Omkar Salunkhe Sangli linkedin site:linkedin.com";
            String serpApiUrl = "https://serpapi.com/search.json?q=" +
                    java.net.URLEncoder.encode(query, "UTF-8") +
                    "&api_key=" + API_KEY;

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

            System.out.println("Top Results:");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                System.out.println((i + 1) + ". " + result.getString("title"));
                System.out.println("   Link: " + result.getString("link"));
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
