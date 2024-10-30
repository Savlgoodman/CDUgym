package Main;

import java.io.IOException;
import java.util.List;

public class MainAPI {
    private final String URLofCurse_pre = "https://www.styd.cn/m/e74abd6e/default/search?date=";
    private final String URLofCurse_next = "&shop_id=612773420&type=1";


    public String Run(String tName,String tData,String tDataTime,String Cookie) throws IOException {
        StringBuilder returnString = new StringBuilder();
        String URLofCurse = URLofCurse_pre + tData + URLofCurse_next;
        String UserAgent = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Mobile Safari/537.36";
        Request request = new Request(Cookie, UserAgent, URLofCurse,"GET");
        String response = request.request();
        System.out.println(response); // 输出
        List<CurseInfo> info = new GetCurseInfo(response).getInfo();
        String temp = "";
        for(CurseInfo i : info) {
            temp = String.format("%-20s---%-15s---%-11s---%-13s\n",i.name,i.curseID,i.time,i.status);
            returnString.append(temp);
            System.out.println(temp);
        }
        BookCurse bookCurse = new BookCurse(info,tName,tDataTime,Cookie);

        String bookStatus = bookCurse.GetMemberCard();
        System.out.println(bookStatus);
        if(!bookStatus.contains("Error")){
            String res = bookCurse.OrderCurse(bookStatus);
            returnString.append("\n").append(res);
            System.out.println(res);
        }else{
            returnString.append("\n").append(bookStatus);
        }
        return returnString.toString();
    }


}
