package com.example.myproject;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class APIThread implements Runnable{
    private URL getEndpoint;

    public String getQuote() {
        return quote;
    }

    private String quote;
    @Override
    public void run() {
        try {
            URL obj = new URL(new String("https://api.quotable.io/random"));
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) { // success
//                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//
//                // print result
//                System.out.println(response.toString());
            InputStreamReader responseReader = new InputStreamReader(con.getInputStream());
            JsonReader jsonReader = new JsonReader(responseReader);
                jsonReader.beginObject();
                while(jsonReader.hasNext()) {
                    String key = jsonReader.nextName();
                    if(key.equals("content")) {
                        quote = jsonReader.nextString();
                        break;
                    } else {
                        jsonReader.skipValue();
                    }
                }
                jsonReader.close();
                con.disconnect();
            } else {
                System.out.println("GET request did not work.");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
