package com.seahorse;

import com.seahorse.view.MainMenu;
import java.awt.Desktop;
import java.io.File;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        String versionStr = System.getProperty("java.version");String[] parts = versionStr.split("\\.");
        int major = Integer.parseInt(parts[0]);
        System.out.println(major);
        if (major < 23) {
            int result = JOptionPane.showConfirmDialog(
                null,
                "Ứng dụng yêu cầu Java 23, bạn có muốn cài đặt?",
                null,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
    
            if (result == JOptionPane.OK_OPTION) {
                runInstaller("./jdk-23.0.2_windows-x64_bin.exe");
                System.exit(0);
            } else {
                System.exit(0);
            }
        }
        else {
            // runInstaller("./jdk-23.0.2_windows-x64_bin.exe");
            javax.swing.SwingUtilities.invokeLater(MainMenu::new);
        }
    }

    public static void runInstaller(String filePath) {
        try {
            File installer = new File(filePath);
            if (!installer.exists()) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy file cài đặt tại:\n" + filePath, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Desktop.getDesktop().open(installer);

            System.out.println("Đã khởi động trình cài đặt Java 23.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi chạy file cài đặt.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}