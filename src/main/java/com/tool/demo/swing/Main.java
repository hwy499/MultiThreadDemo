package com.tool.demo.swing;

import com.alee.laf.WebLookAndFeel;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取主窗体
        MainFrame frame = MainFrame.getInstance();
        // 初始化参数
        frame.initDefaultParams();

    }
}
