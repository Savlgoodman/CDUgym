package Main;

public class CurseInfo {
    public String name;
    public String curseID;
    public String status;
    public String time;

    public CurseInfo(String Name, String CurseID, String Status, String Time) {
        this.name = Name;
        this.curseID = CurseID;
        this.status = Status;
        this.time = Time;
    }
    public void printlnInfo() {
        System.out.println("--------------------------");
        System.out.println("课程名：" + name);
        System.out.println("课程ID：" + curseID);
        System.out.println("课程状态：" + status);
        System.out.println("课程时间：" + time);
        System.out.println("--------------------------");
    }
}
