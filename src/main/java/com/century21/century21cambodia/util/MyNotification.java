package com.century21.century21cambodia.util;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import org.springframework.context.annotation.Configuration;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Configuration
public class MyNotification {
    public void sendToAllSubscriber(String title,String message,String imageUrl){
        String jsonResponse=""; int httpResponse=500;
        try {

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic ODYzOTJlMTYtNGMzNi00MjUzLTg2MWEtMmI0NDU1YzQzYmM3");
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    +   "\"app_id\": \"6ed2f44c-9a7a-4a25-89b9-73c19b0ae705\","
                    +   "\"included_segments\": [\"All\"],"
                    +   "\"headings\":{\"en\" : \""+title+"\"},"
                    +   "\"contents\": {\"en\": \""+message+"\"},"
                    +   "\"big_picture\": \" "+imageUrl+"\""
                    + "}";

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            httpResponse = con.getResponseCode();

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            System.out.println(jsonResponse);
        } catch(Throwable t) {
            throw new CustomRuntimeException(httpResponse,jsonResponse);
        }

    }
}
