package pl.mr_electronics.showusers.model.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public abstract class RestClient {
    ExecutorService executor = Executors.newSingleThreadExecutor();


    public void sendGet(String url) {

        executor.execute(() -> {
            try {
                URL urlEndpoint = new URL(url);
                HttpsURLConnection connection = (HttpsURLConnection) urlEndpoint.openConnection();
                connection.setRequestProperty("User-Agent", "showusers-app-v0.1");

                if (connection.getResponseCode() == 200) {
                    // Success
                    InputStream responseBody = connection.getInputStream();
                    InputStreamReader responseBodyReader =
                            new InputStreamReader(responseBody, StandardCharsets.UTF_8);
                    BufferedReader reader = new BufferedReader(responseBodyReader);
                    StringBuilder total = new StringBuilder();
                    for (String line; (line = reader.readLine()) != null; ) {
                        total.append(line).append('\n');
                    }
                    responseMessage(total.toString());
                } else {
                    responseError("Error. Response code: " + connection.getResponseCode());
                }

                connection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
                responseError(e.getMessage());
            }
        });

    }


    public abstract void responseMessage(String msg);

    public abstract void responseError(String log);
}
