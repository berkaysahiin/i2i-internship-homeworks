import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.VoltTable;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {

            ClientConfig clientConfig = new ClientConfig();
            Client client = ClientFactory.createClient(clientConfig);

            client.createConnection("35.198.145.16", 32776);

            // SQL sorgusu ile tablodaki her şeyi seç
            String query = "SELECT * FROM users";
            ClientResponse response = client.callProcedure("@AdHoc", query);

            if (response.getStatus() == ClientResponse.SUCCESS) {
                VoltTable results = response.getResults()[0];
                while (results.advanceRow()) {
                    System.out.println("ID: " + results.getLong("USER_ID") +
                            ", NAME: " + results.getString("NAME"));
                }
            } else {
                System.out.println("Procedure call failed: " + response.getStatusString());
            }

            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

