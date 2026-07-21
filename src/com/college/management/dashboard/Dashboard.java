package com.college.management.dashboard;

import javax.swing.*;

public abstract class Dashboard extends JPanel {
    protected int userId;

    public Dashboard(int userId) {
        this.userId = userId;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initializeDashboard();
    }

    // Method to be implemented by subclasses to set up specific UI components
    protected abstract void initializeDashboard();

    // Common method to show a message dialog, which can be reused by subclasses
    protected void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
