package Main;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookCurse {
    private final String BOOKURLPRE = "https://www.styd.cn/m/e74abd6e/course/order?id=";
    private String targetCurseID = "NULL";
    private final String UserAgent = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Mobile Safari/537.36";
    private final List<CurseInfo> listOfCurseInfo;
    private final String cookie;

    public BookCurse(List<CurseInfo> List, String targetName, String targetCurseTime, String cookie) {
        this.listOfCurseInfo = List;
        this.cookie = cookie;

        for (CurseInfo info : listOfCurseInfo) {
            if (info.name.contains(targetName)) {
                if (info.time.contains(targetCurseTime)) {
                    if(!info.curseID.equals("__FULL__")){
                        targetCurseID=info.curseID;
                        break;
                    }
                }
            }
        }
    }
    private String findBetween(String text, String start, String end) {
        String patternString = start + "(.*?)" + end;
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "NULL";
    }

    public String GetMemberCard() throws IOException {
        String memberCardID;
        String cardCatID;
        String curseID;
        if (targetCurseID.equals("NULL")) {
            return "Error: Curse full or not found";
        }
        Request req = new Request(cookie, UserAgent, BOOKURLPRE + targetCurseID, "GET");
        //System.out.println(BOOKURLPRE+targetCurseID);
        String bookPageDetail = req.request();
        System.out.println(bookPageDetail);
        //获取member_card_id
        String startDelimiter = "card_id=\\\"";
        String endDelimiter = "\\\"";
        memberCardID = findBetween(bookPageDetail, startDelimiter, endDelimiter);
        //获取card_cat_id
        startDelimiter = "cat_id=\\\"";
        cardCatID = findBetween(bookPageDetail, startDelimiter, endDelimiter);
        //获取curse_id     有个误解：之前的curse其实在后续下单的时候叫class_id，而不是curse_id
        startDelimiter = "course_id\\\" value=\\\"";
        curseID = findBetween(bookPageDetail, startDelimiter, endDelimiter);
        if(memberCardID.equals("NULL")||cardCatID.equals("NULL")||curseID.equals("NULL")){
            return "Error: memberCardID or cardCatID or curseID is NULL";
        }
        String returnValue = memberCardID + ";" + cardCatID + ";" + curseID;
        return returnValue;
    }

    public String OrderCurse(String raw_data) throws IOException {
        //System.out.println("!!!!!!");
        List<String> list = List.of(raw_data.split(";"));
        String oderURL = "https://www.styd.cn/m/e74abd6e/course/order_confirm";
        String memberCardID = list.get(0);
        String cardCatID = list.get(1);
        String curseID = list.get(2);
        String order_data = "member_card_id="+memberCardID+"&card_cat_id="+cardCatID+"&course_id="+curseID+"&class_id="+targetCurseID+"&note=&time_from_stamp=0&time_to_stamp=0&quantity=1&is_waiting=";
        Request req = new Request(cookie, UserAgent, oderURL, "POST", order_data);
        //System.out.println(order_data);
        String res = req.requestWithData();
        System.out.println("order res:"+res);
        return res;


    }
}
