package com.college.management.dashboard;

import javax.swing.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame(int userId, String role) {
        setTitle("Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if ("student".equals(role)) {
            panel.add(new StudentDashboard(userId));
        } else if ("staff".equals(role)) {
            panel.add(new StaffDashboard(userId));
        } else if ("admin".equals(role)) {
            panel.add(new AdminDashboard(userId));
        }

        add(panel);
        setVisible(true);
    }
}
