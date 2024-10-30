package UIPackage;
import Main.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainFrame extends JFrame {
    private final String FILEPATH = "D:\\Code center\\CDUgym";
    private String Cookie;
    JLabel icon = new JLabel();

    public MainFrame(String cookie) {
        initFrame();
        this.Cookie = cookie;
        initComponents();
        this.setVisible(true);
    }

    private void initFrame() {
        this.setSize(500, 350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout()); // 设置GridBagLayout
    }

    private boolean checkCookie(String Cookie) throws IOException {
        String userURL = "https://www.styd.cn/m/e74abd6e/user/default";
        String UserAgent = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Mobile Safari/537.36";
        if (Cookie == null) {
            JOptionPane.showMessageDialog(null, "请先输入Cookie!");
            return false;
        }
        Request request = new Request(Cookie, UserAgent, userURL, "GET");
        String response = request.request();
        // <img src="">正则查找
        String startDelimiter = "<img src=\\\"";
        String endDelimiter = "\\\">";
        String result = findBetween(response, startDelimiter, endDelimiter);
        if(result != null){
            JOptionPane.showMessageDialog(null, "Cookie校验成功!");
            Request req = new Request(result);
            ImageIcon iconImg = req.getIcon();
            Image img = iconImg.getImage();
            img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            iconImg = new ImageIcon(img);
            icon.setIcon(iconImg);
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Cookie校验失败!");
            return false;
        }
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 添加内边距

        //头像与昵称

        icon.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 占据两列
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        this.add(icon, gbc);
        ImageIcon pic = new ImageIcon(FILEPATH+"\\src\\icon.png");
        Image image = pic.getImage();
        Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        icon.setIcon(scaledIcon);

        // 文本输入框
        JLabel label1 = new JLabel("输入或更改COOKIE:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END; // 靠右对齐
        this.add(label1, gbc);

        JTextField textField = new JTextField(20);
        textField.setText(Cookie);
        //System.out.println("!"+Cookie);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // 占据两列
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        this.add(textField, gbc);

        // 日期选择
        JLabel label2 = new JLabel("日期选择:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // 占据一列
        gbc.fill = GridBagConstraints.NONE; // 不填充
        this.add(label2, gbc);

        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // 占据两列
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        this.add(dateSpinner, gbc);

        // 下拉选择框
        JLabel label3 = new JLabel("课程选择:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1; // 占据一列
        gbc.fill = GridBagConstraints.NONE; // 不填充
        this.add(label3, gbc);

        String[] items = { "健身晚", "健身午", "游泳晚","游泳午" };
        JComboBox<String> comboBox = new JComboBox<>(items);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // 占据两列
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        this.add(comboBox, gbc);

        // 按钮
        JButton button1 = new JButton("登录cookie");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1; // 占据一列
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        this.add(button1, gbc);

        JButton button2 = new JButton("开始抢课");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1; // 占据一列
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        this.add(button2, gbc);
        button2.setEnabled(false);

        JButton button3 = new JButton("按钮3");
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1; // 占据一列
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        this.add(button3, gbc);

        JButton button4 = new JButton("按钮4");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3; // 占据三列
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        this.add(button4, gbc);

        // 添加 登录 按钮点击事件
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean res = false;
                try {
                    res = checkCookie(textField.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if(res) {
                    button2.setEnabled(true);
                    Cookie = textField.getText();
                }else{
                    JOptionPane.showMessageDialog(MainFrame.this, "Cookie校验失败");
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new BookFrame(Cookie);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.this, "按钮3被点击了");
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.this, "按钮4被点击了");
            }
        });

        // 添加日期选择框的监听器
        dateSpinner.addChangeListener(e -> {
            Date selectedDate = (Date) dateSpinner.getValue();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Selected date: " + sdf.format(selectedDate));
        });

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = textField.getText();
                System.out.println("Inserted text: " + text);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = textField.getText();
                System.out.println("Removed text: " + text);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String text = textField.getText();
                System.out.println("Changed text: " + text);   
            }
        });

        // 添加下拉选择框的监听器
        // combox 事件监听
        comboBox.addActionListener(e -> {
            String selectedItem = (String) comboBox.getSelectedItem();
            System.out.println("Selected item: " + selectedItem);
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