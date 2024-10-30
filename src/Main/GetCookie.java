package Main;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GetCookie {
    
    public static String getCookie() throws IOException {
        String cookie = "";
        String FILEPATH = System.getProperty("user.dir");

        File file = new File(FILEPATH+ "\\cookie.txt");
        if (!file.exists()) {
            System.out.println("cookie.txt不存在");
            boolean create = file.createNewFile();
            if(!create){
                System.out.println("cookie.txt创建失败");
            }
            return cookie;
        }

        try(FileReader reader = new FileReader(FILEPATH+ "\\cookie.txt")) {
            System.out.println(FILEPATH+ "\\cookie.txt");
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
