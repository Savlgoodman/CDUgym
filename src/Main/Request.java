package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {
    private String Cookie = "";
    private String UserAgent = "";
    private final String url;
    private String Method = "";
    private String data = "";



    public Request(String Cookie, String UserAgent, String url,String Method) {
        this.Cookie = Cookie;
        this.UserAgent = UserAgent;
        this.url = url;
        this.Method = Method;
    }

    public Request(String Cookie, String UserAgent, String url,String Method,String data) {
        this.Cookie = Cookie;
        this.UserAgent = UserAgent;
        this.url = url;
        this.Method = Method;
        this.data = data;
    }

    public Request(String url){
        this.url = url;
    }
    public String request() throws IOException {
        URL url1 = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod(Method);
        conn.setRequestProperty("User-Agent", UserAgent);
        conn.setRequestProperty("Cookie",Cookie);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
    public String requestWithData() throws IOException{
        URL url1 = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod(Method);
        conn.setRequestProperty("User-Agent", UserAgent);
        conn.setRequestProperty("Cookie",Cookie);
        conn.setDoOutput(true);
        conn.getOutputStream().write(data.getBytes());
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public ImageIcon getIcon() throws IOException {
        URL url1 = new URL(url);
        InputStream in = url1.openStream();
        ImageIcon icon = new ImageIcon(ImageIO.read(in));
        in.close();
        return icon;
    }
}
