package com.century21.apexproperty.util;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.api_save_noti.SaveNoti;
import com.century21.apexproperty.repository.api_save_noti.SaveNotiRepo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Configuration
public class MyNotification {
    @Autowired
    private SaveNotiRepo saveNotiRepo;
    public void sendToAllSubscriber(String title,String message,String imageUrl,String type,int refID){
        String jsonResponse=""; int httpResponse=500;
        try {

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic ZjI5NTlkMzgtMzY4YS00OWIzLWIwY2UtMDY3MjRiMWIzNGZi");
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    +   "\"app_id\": \"bc3a4944-d4b8-499c-9db0-07c916166a23\","
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
            }else if(type.equalsIgnoreCase("property")){
                strJsonBody+="\"data\": {" +
                        "\"property_id\": \"" + refID + "\"," +
                        "\"post\":\"property\"},";
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
            SaveNoti saveNoti = new SaveNoti();
            saveNoti.setRefID(refID);
            if(type.equalsIgnoreCase("event"))
                saveNoti.setType("event");
            else if(type.equalsIgnoreCase("project"))
                saveNoti.setType("project");
            else if(type.equalsIgnoreCase("property"))
                saveNoti.setType("property");
            saveNoti.setMessage(message);
            saveNoti.setTitle(title);
            saveNotiRepo.SaveNoti(saveNoti,null);

        } catch(Throwable t) {
            throw new CustomRuntimeException(httpResponse,jsonResponse);
        }
    }
}
