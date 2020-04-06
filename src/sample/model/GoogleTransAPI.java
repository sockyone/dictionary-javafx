package sample.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleTransAPI {
    public static String translate(String transText) throws IOException {
        StringBuilder text = new StringBuilder();
        for (int i=0;i<transText.length();i++) {
            char c = transText.charAt(i);
            if (c != ' ') text.append(c);
            else text.append("%20");
        }

        String url = "http://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=vi&dt=t&q=";
        url = url + text;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.addRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine())!=null) {
            response.append(inputLine+"\n");
        }
        System.out.println(response);
        Pattern ptn = Pattern.compile("\"(.*)\",\"(.*)\",");
        Matcher matcher = ptn.matcher(response);
        matcher.find();
        return matcher.group(1);
    }
}
