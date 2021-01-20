package com.tool.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static int width;
    public static int height;

    private SystemTray systemTray;

    private static class SingletonInstance {
        private static final MainFrame INSTANCE = new MainFrame("文件下载器");
    }


    private MainFrame(String title) {
        super(title);

    }

    /**
     * 初始化默认参数
     */
    public void initDefaultParams() {
        width = 960;
        height = 540;
        // 居中显示
        this.centerDisplay(width, height);
        // 设置关闭方式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainJPanel jPanel = new MainJPanel();
        this.setContentPane(jPanel);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * 居中显示
     *
     * @param width  窗体宽度
     * @param height 窗体高度
     */
    private void centerDisplay(int width, int height) {
        this.setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
    }

    /**
     * 居中显示
     */
    private void centerDisplay() {
        int width = screenWidth >> 1;
        int height = screenHeight >> 1;
        System.out.println(width);
        System.out.println(height);
        this.setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
    }


    public static MainFrame getInstance() {
        return SingletonInstance.INSTANCE;
    }


}
