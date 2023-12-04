package com.app.coding;

import com.app.coding.ui.UserManager;

import javax.swing.*;

public class AppStart extends JFrame {

    // Точка старта программы
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("User Manager");
            frame.setContentPane(new UserManager().getjPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
