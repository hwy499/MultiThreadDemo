package com.tool.demo.swing;

import com.tool.demo.core.service.FileDownloader;
import com.tool.demo.core.service.impl.MultiThreadFileDownloader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;

public class MainJPanel extends JPanel implements ActionListener {

    private JButton downLoad = null;
    /**
     * 选择文件
     */
    private JButton chooseFile = null;

    // 用户接受文件的URL
    JTextField filePath = null;
    // 保存路径
    JTextField savePath = null;
    // 文件选择器
    private JFileChooser fileChooser = null;

    public MainJPanel() {
        int left = 100;
        int width = MainFrame.width - 2 * left;
        System.out.println(width);
        this.setLayout(null);
        filePath = new JTextField();
        filePath.setToolTipText("请输入你要下载的文件路径");
        filePath.setBounds(left, 100, width, 50);
        this.add(filePath);
        // 保存路径
        savePath = new JTextField();
        savePath.setBounds(left, 160, width - 200, 50);
        this.add(savePath);
        // 选择存放目录按钮
        chooseFile = new JButton("选择存放目录");
        chooseFile.setBounds(left + width - 200, 160, 200, 50);
        chooseFile.addActionListener(this);
        this.add(chooseFile);

        downLoad = new JButton("开始下载");
        downLoad.setBounds(left, 220, width, 50);
        downLoad.addActionListener(this);
        this.add(downLoad);

        fileChooser = new JFileChooser(new File("."));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn == chooseFile) {
            // 只能选择目录
            fileChooser.setFileSelectionMode(1);
            //打开文件选择器对话框
            int status = fileChooser.showOpenDialog(this);
            if (status == 1) {
                JOptionPane.showMessageDialog(null, "没有选择到目录", "标题", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                File f = fileChooser.getSelectedFile();// f为选择到的目录
                savePath.setText(f.getAbsolutePath());
            }
        }

        if (btn == downLoad) {
            // 获取网络连接
            String fileURL = filePath.getText();
            // 获取保存路径
            String saveURL = savePath.getText();
            if ("".equals(fileURL) || "".equals(saveURL)) {
                int n = JOptionPane.showConfirmDialog(null, "链接或保存路径为空", "标题", JOptionPane.YES_NO_OPTION);
                return;
            }
            FileDownloader downloader = new MultiThreadFileDownloader(120);
            saveURL = saveURL + "\\temp.exe";
            try {
                downloader.downloadFile(fileURL, saveURL);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
