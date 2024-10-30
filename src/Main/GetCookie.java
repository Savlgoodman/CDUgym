package Main;

import java.io.FileReader;

public class GetCookie {
    
    public static String getCookie() {
        String cookie = "";
        String FILEPATH = "D:\\Code center\\CDUgym";
        try(FileReader reader = new FileReader(FILEPATH+"\\src\\cookie.txt")) {
            int c;
            StringBuilder sb = new StringBuilder();
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
            cookie = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookie;
    }
}
