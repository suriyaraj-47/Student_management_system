package com.college.management.dashboard;

import com.college.management.database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StaffDashboard extends Dashboard {

    public StaffDashboard(int userId) {
        super(userId);
    }

    @Override
    protected void initializeDashboard() {
        JLabel titleLabel = new JLabel("Staff Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);

        JButton markAttendanceButton = new JButton("Mark Attendance");
        JButton assignAssignmentsButton = new JButton("Assign Assignments");

        markAttendanceButton.addActionListener(e -> markAttendance());
        assignAssignmentsButton.addActionListener(e -> assignAssignments());

        add(markAttendanceButton);
        add(assignAssignmentsButton);
    }

    private void markAttendance() {
        String className = JOptionPane.showInputDialog(this, "Enter Class:");
        String section = JOptionPane.showInputDialog(this, "Enter Section:");
        String subject = JOptionPane.showInputDialog(this, "Enter Subject:");
        String date = JOptionPane.showInputDialog(this, "Enter Date (YYYY-MM-DD):");

        ArrayList<String> studentIds = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT user_id FROM users WHERE class = ? AND section = ? AND role = 'student'")) {
            stmt.setString(1, className);
            stmt.setString(2, section);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                studentIds.add(rs.getString("user_id"));
            }

        } catch (SQLException e) {
            showMessage("Error fetching students.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (String studentId : studentIds) {
            String status = JOptionPane.showInputDialog(this, "Mark attendance for Student ID " + studentId + " (present/absent):");

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement insertStmt = conn.prepareStatement(
                         "INSERT INTO attendance (student_id, subject, status, date, class, section) VALUES (?, ?, ?, ?, ?, ?)")) {
                insertStmt.setInt(1, Integer.parseInt(studentId));
                insertStmt.setString(2, subject);
                insertStmt.setString(3, status);
                insertStmt.setString(4, date);
                insertStmt.setString(5, className);
                insertStmt.setString(6, section);
                insertStmt.executeUpdate();

            } catch (SQLException e) {
                showMessage("Error marking attendance for Student ID " + studentId, "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        showMessage("Attendance marked successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void assignAssignments() {
        String className = JOptionPane.showInputDialog(this, "Enter Class:");
        String section = JOptionPane.showInputDialog(this, "Enter Section:");
        String subject = JOptionPane.showInputDialog(this, "Enter Subject:");
        String title = JOptionPane.showInputDialog(this, "Enter Assignment Title:");
        String description = JOptionPane.showInputDialog(this, "Enter Description:");
        String dueDate = JOptionPane.showInputDialog(this, "Enter Due Date (YYYY-MM-DD):");

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO assignments (subject, title, description, due_date, status, class, section) " +
                             "VALUES (?, ?, ?, ?, 'Pending', ?, ?)")) {
            stmt.setString(1, subject);
            stmt.setString(2, title);
            stmt.setString(3, description);
            stmt.setString(4, dueDate);
            stmt.setString(5, className);
            stmt.setString(6, section);
            stmt.executeUpdate();

            showMessage("Assignment assigned successfully to the entire class and section.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            showMessage("Error assigning assignment.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
