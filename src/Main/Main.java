package Main;

import UIPackage.*;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String Cookie = GetCookie.getCookie();
        System.out.println(Cookie);
        new MainFrame(Cookie);
        System.out.println("end");





//       String URL = "https://www.styd.cn/m/e74abd6e/default/search?date=2024-10-30&shop_id=612773420&type=1";
//       String UserAgent = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Mobile Safari/537.36";
//        String Cookie = GetCookie.getCookie();
//        Request request = new Request(Cookie, UserAgent, URL,"GET");
//        String response = request.request();
//      System.out.println(response);
//      List<CurseInfo> info = new GetCurseInfo(response).getInfo();
//          for(CurseInfo i : info) {
//            i.printlnInfo();
//        }
//        BookCurse bookCurse = new BookCurse(info,"游泳","14:00",Cookie);
//       String bookStatus = bookCurse.GetMemberCard();
//        System.out.println(bookStatus);
//        if (!bookStatus.contains("Error")) {
//            bookCurse.OrderCurse(bookStatus);
//        }
//

//        MainAPI api = new MainAPI();
//        api.Run("游泳","2024-10-28","14:00",GetCookie.getCookie());
    }


}