package lv.venta.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Translate {
	
	/*
	public static String translate(String langFrom, String langTo, String text) throws IOException {
        
		String apiKey = "AIzaSyBlYOjlzJujaFAi0mnIMbjKWzzK3Yo9dnQ";
		String urlStr = "https://translation.googleapis.com/language/translate/v2?key=" + apiKey +
		                "&q=" + URLEncoder.encode(text, "UTF-8") +
		                "&target=" + langTo +
		                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
    */
    
}
