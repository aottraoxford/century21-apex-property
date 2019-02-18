package com.century21.util;

import com.century21.exception.CustomRuntimeException;
import com.century21.repository.api_save_noti.SaveNoti;
import com.mashape.unirest.http.Unirest;
import org.springframework.context.annotation.Configuration;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Configuration
public class MyNotification {
    public void sendToAllSubscriber(String title,String message,String imageUrl,String token,String type,int refID){
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
                    +   "\"contents\": {\"en\": \""+message+"\"},";
            if(type.equalsIgnoreCase("project")) {
                strJsonBody+="\"data\": {" +
                        "\"project_id\": \"" + refID + "\"," +
                        "\"post\":\"project\"},";
            }else if(type.equalsIgnoreCase("event")){
                strJsonBody+="\"data\": {" +
                        "\"event_id\": \"" + refID + "\"," +
                        "\"post\":\"event\"},";
            }
             strJsonBody+="\"big_picture\": \" "+imageUrl+"\""
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
            if(token!=null) {
                String host = Url.host + "api/save-noti";
                SaveNoti saveNoti = new SaveNoti();
                saveNoti.setRefID(refID);
                if(type.equals("event"))
                    saveNoti.setType("event");
                else saveNoti.setType("project");
                saveNoti.setMessage(message);
                saveNoti.setTitle(title);
                Unirest.post(host)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("Authorization",  token)
                        .body(saveNoti)
                        .asJson();
            }
        } catch(Throwable t) {
            throw new CustomRuntimeException(httpResponse,jsonResponse);
        }
    }
}
