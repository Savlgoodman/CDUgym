package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetCurseInfo {
    public String rawData;
    public GetCurseInfo(String RawData) {
        this.rawData = RawData;
    }
    public List<CurseInfo> getInfo() {
        List<CurseInfo> ListOfCuresInfo = new ArrayList<CurseInfo>();
        String[] rawInfo = rawData.split("</li>");
        for (String s : rawInfo) {
            //查找课程链接处id
            String curseID = findCurseID(s);
            if(curseID.equals("NULL")){
                continue;
            }
            //System.out.println(CurseID);
            //查找课程名
            String curseName = findCurseName(s);
            //System.out.println(curseName);
            //查找课程时间
            String curseTime = findCurseTime(s);
            //System.out.println(curseTime);
            //查找课程状态
            String curseStatus = findCurseStatus(s);
            //System.out.println(curseStatus);
            CurseInfo info = new CurseInfo(curseName, curseID, curseStatus, curseTime);
            ListOfCuresInfo.add(info);
        }
        return ListOfCuresInfo;
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

    private String findCurseID(String text) {
        String startDelimiter = "<a href=\\\\\""; //此处需要5个斜杠的原因：层层转义后，下次处理字符串时还需要转义，所以每层多两个斜杠
        String endDelimiter = " class=\\\\\"course_link\\\\\">";
        String result = findBetween(text, startDelimiter, endDelimiter);
        //System.out.println(result);
        Pattern pattern = Pattern.compile("预约已爆棚");
        Matcher matcher = pattern.matcher(result);
        if (matcher.find()) {
            result = "__FULL__";
        }else{
            Pattern pattern2 = Pattern.compile("id=(\\d+)");
            Matcher matcher2 = pattern2.matcher(result);
            if (matcher2.find()) {
                result = matcher2.group(1);
            }else{
                result = "NULL";
            }
        }
        return result;
    }

    private String findCurseName(String text) {
        String startDelimiter = "<p class=\\\\\"name\\\\\">\\\\n                  "; //此处需要5个斜杠的原因：层层转义后，下次处理字符串时还需要转义，所以每层多两个斜杠
        String endDelimiter = "</p>\\\\n";
        String result = findBetween(text, startDelimiter, endDelimiter);
        return result;
    }
    private String findCurseTime(String text) {
        String startDelimiter = "<p class=\\\\\"date\\\\\"><b class=\\\\\"t-text-color\\\\\">";
        String endDelimiter = "</b>\\\\n";
        String result = findBetween(text, startDelimiter, endDelimiter);
        return result;
    }
    private String findCurseStatus(String s) {
        String startDelimiter = "<i class=\\\\\"course_status  ";
        String endDelimiter = "\\\\n        ";
        String result = findBetween(s, startDelimiter, endDelimiter);
        return result;
    }


}
