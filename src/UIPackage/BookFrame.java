package UIPackage;
import Main.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookFrame extends JFrame {
    private final String Cookie;
    private String data;

    //创建日志显示区全局变量
    JTextArea logArea = new JTextArea(10, 30);
    JScrollPane scrollPane = new JScrollPane(logArea);

    public BookFrame(String Cookie) {
        this.Cookie = Cookie;
        this.setSize(300,300);
        initFrame();
        initComponents();
        this.setVisible(true);
    }
    private void runLoopBook(String tName,String tData,String tDataTime,int interval,int cycleTimes) {
        String cycleTime = Integer.toString(cycleTimes);
        final int[] currentCycle = {0};
        final boolean[] flag = {false};
        Timer timer = new Timer(interval*1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCycle[0]++;
                float leftTime = (float) ((cycleTimes - currentCycle[0]) * interval) / 60 ; //计算剩余时间e
                logArea.append("---------------第"+ currentCycle[0] +"/"+cycleTime+"次循环----剩余"+String.format("%.2f",leftTime)+"分钟-------------\n");
                try {
                    if(visitBOOKAPI(tName,tData,tDataTime,Cookie)){
                        flag[0] = true;
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if (currentCycle[0] == cycleTimes || flag[0]) {
                    ((Timer)e.getSource()).stop();

                }
            }
        });
        timer.start();

    }
    private boolean visitBOOKAPI(String tName,String tData,String tDataTime ,String Cookie) throws IOException {
        MainAPI api = new MainAPI();
        String printInfo = "";
        printInfo = api.Run(tName,tData,tDataTime,Cookie);
        logArea.append(printInfo);
        logArea.append("\n-------------------------\n");
        if(printInfo.contains("\\u9884\\u7ea6\\u6210\\u529f")){
            logArea.append("本次预约成功!!\n");
            return true;
        }else{
            return false;
        }
    }


    private void initFrame() {
        this.setSize(600,400);
        //this.setLayout(new GridBagLayout()); // 设置GridBagLayout
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    private void initComponents() {

        // 创建主面板，并使用 GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 创建日期选择器
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);

        // 创建查询按钮
        JButton queryButton = new JButton("查询");

        // 创建下拉选择框
        String[] options = {"健身晚", "健身午", "游泳午", "游泳晚" };
        JComboBox<String> comboBox = new JComboBox<>(options);

        //创建间隔时间输入框
        JLabel intervalLabel = new JLabel("间隔时间:");
        JTextField intervalField = new JTextField(5);
        intervalField.setText("10");

        // 创建循环次数输入框
        JLabel cycleLabel = new JLabel("循环次数:");
        JTextField cycleField = new JTextField(5);
        cycleField.setText("10");

        // 创建预约按钮
        JButton bookingButton = new JButton("预约");


        // 添加日期选择器
        JLabel dateLabel = new JLabel("选择日期:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(dateLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // 上、左、下、右内边距
        mainPanel.add(dateSpinner, gbc);

        // 添加查询按钮
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(queryButton, gbc);

        // 添加下拉选择框
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(comboBox, gbc);

        //添加间隔时间输入
        gbc.gridx = 0;
        gbc.gridy =3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(intervalLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(intervalField, gbc);

        // 添加循环次数输入
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(cycleLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(cycleField, gbc);

        // 添加预约按钮
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(bookingButton, gbc);

        // 添加日志显示区
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 23;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(scrollPane, gbc);

        // 设置内容面板
        setContentPane(mainPanel);
        //设置按钮监听
        bookingButton.addActionListener(e -> {
            MainAPI r = new MainAPI();
            Date selectedDate = (Date) dateSpinner.getValue();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String targetDate = sdf.format(selectedDate);
            String targetCurseName = (String) comboBox.getSelectedItem();
            int interval = Integer.parseInt(intervalField.getText());
            int cycle = Integer.parseInt(cycleField.getText());
            String tdataTime = "";
            if(targetCurseName.equals("健身晚")){
                tdataTime = "19:00";
                targetCurseName = "健身";
            } else if (targetCurseName.equals("健身午")) {
                tdataTime = "12:30";
                targetCurseName = "健身";
            }else if(targetCurseName.equals("游泳午")){
                tdataTime = "12:30";
                targetCurseName = "游泳";
            }else if(targetCurseName.equals("游泳晚")){
                tdataTime = "19:00";
                targetCurseName = "游泳";
            }
            runLoopBook(targetCurseName, targetDate, tdataTime, interval, cycle);
            bookingButton.setEnabled(false);
        });

        queryButton.addActionListener(e -> {
            logArea.append("这个按钮还没有加功能哦\n");
        });
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

}
