package edu.ewubd.quizzler;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.utils.URLEncodedUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@SuppressWarnings("ALL")
public class JSONParser {
    private String TAG = "JSONParser";
    private static JSONParser instance = new JSONParser();
    private JSONParser() {}
    public static JSONParser getInstance() {
        return instance;
    }
    public String makeHttpRequest(String url, String method, List<NameValuePair> params) {

        HttpURLConnection http = null;
        InputStream is = null;
        String data = "";
        try {
            if (method.equals("POST")) {
                if(params != null) {
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;
                }
            }
            System.out.println("@JSONParser-"+": "+ url);
            URL urlc = new URL(url);
            http = (HttpURLConnection) urlc.openConnection();
            http.connect();
            is = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            http.disconnect();
        } catch (Exception e) {
        }
        return null;
    }
}