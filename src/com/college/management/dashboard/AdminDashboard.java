package com.college.management.dashboard;

import com.college.management.database.DatabaseConnection;
import com.college.management.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminDashboard extends Dashboard {
    public AdminDashboard(int userId) {
        super(userId);  // Call the constructor of the base class
    }

    @Override
    protected void initializeDashboard() {
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton manageStudentsButton = new JButton("Manage Students");
        JButton manageStaffButton = new JButton("Manage Staff");
        JButton viewReportsButton = new JButton("View Reports");

        manageStudentsButton.addActionListener(e -> manageStudents());
        manageStaffButton.addActionListener(e -> manageStaff());
        viewReportsButton.addActionListener(e -> viewReports());

        add(manageStudentsButton);
        add(manageStaffButton);
        add(viewReportsButton);
    }

    private void manageStudents() {
        JFrame manageStudentsFrame = new JFrame("Manage Students");
        manageStudentsFrame.setSize(500, 300);
        manageStudentsFrame.setLocationRelativeTo(this);

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Student ID", "Username", "Class", "Section", "Action"}, 0);
        JTable table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), model));

        String sql = "SELECT user_id, username, class, section FROM users WHERE role = 'student'";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("user_id");
                String username = rs.getString("username");
                String studentClass = rs.getString("class");
                String section = rs.getString("section");

                JButton editButton = new JButton("Edit");
                editButton.addActionListener(e -> editStudent(studentId));

                model.addRow(new Object[]{studentId, username, studentClass, section, editButton});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading students.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        manageStudentsFrame.add(new JScrollPane(table));
        manageStudentsFrame.setVisible(true);
    }

    private void editStudent(int studentId) {
        String newUsername = JOptionPane.showInputDialog(this, "Enter new username:");
        String newClass = JOptionPane.showInputDialog(this, "Enter new class:");
        String newSection = JOptionPane.showInputDialog(this, "Enter new section:");

        String updateSql = "UPDATE users SET username = ?, class = ?, section = ? WHERE user_id = ? AND role = 'student'";

        if (studentId == 0) {
            String password = JOptionPane.showInputDialog(this, "Enter password:");
            String insertSql = "INSERT INTO users (username, password, role, class, section) VALUES (?, ?, 'student', ?, ?)";

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

                pstmt.setString(1, newUsername);
                pstmt.setString(2, password);
                pstmt.setString(3, newClass);
                pstmt.setString(4, newSection);

                int updatedRows = pstmt.executeUpdate();

                if (updatedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Student added successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error adding new student.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding student.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(updateSql)) {

                pstmt.setString(1, newUsername);
                pstmt.setString(2, newClass);
                pstmt.setString(3, newSection);
                pstmt.setInt(4, studentId);

                int updatedRows = pstmt.executeUpdate();

                if (updatedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Student details updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error updating student details.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating student.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void manageStaff() {
        JFrame manageStaffFrame = new JFrame("Manage Staff");
        manageStaffFrame.setSize(500, 300);
        manageStaffFrame.setLocationRelativeTo(this);

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Staff ID", "Username", "Action"}, 0);
        JTable table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), model));

        String sql = "SELECT user_id, username FROM users WHERE role = 'staff'";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int staffId = rs.getInt("user_id");
                String username = rs.getString("username");

                JButton editButton = new JButton("Edit");
                editButton.addActionListener(e -> editStaff(staffId));

                model.addRow(new Object[]{staffId, username, editButton});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading staff.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        manageStaffFrame.add(new JScrollPane(table)); // Ensuring scrollable table
        manageStaffFrame.setVisible(true);
    }

    private void editStaff(int staffId) {
        String newUsername = JOptionPane.showInputDialog(this, "Enter new username:");

        if (staffId == 0) {
            String password = JOptionPane.showInputDialog(this, "Enter password:");
            String insertSql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'staff')";

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

                pstmt.setString(1, newUsername);
                pstmt.setString(2, password);

                int updatedRows = pstmt.executeUpdate();

                if (updatedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Staff added successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error adding new staff.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding staff.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewReports() {
        JFrame reportFrame = new JFrame("Reports");
        reportFrame.setSize(500, 300);
        reportFrame.setLocationRelativeTo(this);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel attendancePanel = new JPanel();
        attendancePanel.add(new JScrollPane(generateAttendanceReport()));  // Adding JScrollPane to the table
        tabbedPane.addTab("Attendance Report", attendancePanel);

        JPanel performancePanel = new JPanel();
        performancePanel.add(new JScrollPane(generatePerformanceReport()));  // Adding JScrollPane to the table
        tabbedPane.addTab("Performance Report", performancePanel);

        reportFrame.add(tabbedPane);
        reportFrame.setVisible(true);
    }

    private JTable generateAttendanceReport() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Student ID", "Class", "Section", "Attendance Status"}, 0);
        JTable table = new JTable(model);

        String sql = "SELECT student_id, class, section, status FROM attendance";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String studentClass = rs.getString("class");
                String section = rs.getString("section");
                String status = rs.getString("status");

                model.addRow(new Object[]{studentId, studentClass, section, status});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading attendance data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return table;
    }

    private JTable generatePerformanceReport() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Student ID", "Name", "Subject", "Completed Assignments", "Grade"}, 0);
        JTable table = new JTable(model);

        String sql = "SELECT student_id, subject, count(*) as completed_assignments FROM assignments WHERE status = 'Done' GROUP BY student_id, subject";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String subject = rs.getString("subject");
                int completedAssignments = rs.getInt("completed_assignments");

                // Grade could be determined based on performance or left as a placeholder
                String grade = "A";  // Placeholder logic for grade

                model.addRow(new Object[]{studentId, "Student " + studentId, subject, completedAssignments, grade});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading performance data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return table;
    }
}
