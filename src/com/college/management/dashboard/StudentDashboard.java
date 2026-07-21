package com.college.management.dashboard;

import com.college.management.database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDashboard extends Dashboard {

    public StudentDashboard(int userId) {
        super(userId);
    }

    @Override
    protected void initializeDashboard() {
        JLabel titleLabel = new JLabel("Student Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);

        JButton attendanceButton = new JButton("View Attendance");
        JButton assignmentsButton = new JButton("View Assignments");
        JButton timetableButton = new JButton("View Timetable");
        JButton profileButton = new JButton("View Profile");

        attendanceButton.addActionListener(e -> viewAttendance());
        assignmentsButton.addActionListener(e -> viewAssignments());
        timetableButton.addActionListener(e -> viewTimetable());
        profileButton.addActionListener(e -> viewProfile());

        add(attendanceButton);
        add(assignmentsButton);
        add(timetableButton);
        add(profileButton);
    }

    private void viewAttendance() {
        StringBuilder attendanceDetails = new StringBuilder("Attendance:\n");

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT subject, status, date FROM attendance WHERE student_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                attendanceDetails.append("Subject: ").append(rs.getString("subject"))
                        .append(", Status: ").append(rs.getString("status"))
                        .append(", Date: ").append(rs.getString("date")).append("\n");
            }

            showMessage(attendanceDetails.toString(), "Attendance", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            showMessage("Error retrieving attendance data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAssignments() {
        StringBuilder assignmentDetails = new StringBuilder("Assignments:\n");

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT subject, title, description, due_date, status FROM assignments WHERE student_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                assignmentDetails.append("Subject: ").append(rs.getString("subject"))
                        .append(", Title: ").append(rs.getString("title"))
                        .append(", Due Date: ").append(rs.getString("due_date"))
                        .append(", Status: ").append(rs.getString("status"))
                        .append("\nDescription: ").append(rs.getString("description")).append("\n\n");
            }

            showMessage(assignmentDetails.toString(), "Assignments", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            showMessage("Error retrieving assignment data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewTimetable() {
        StringBuilder timetableDetails = new StringBuilder("Timetable:\n");

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT day, time_slot, subject, location FROM timetable WHERE class = (SELECT class FROM users WHERE user_id = ?) AND section = (SELECT section FROM users WHERE user_id = ?)")) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                timetableDetails.append("Day: ").append(rs.getString("day"))
                        .append(", Time Slot: ").append(rs.getString("time_slot"))
                        .append(", Subject: ").append(rs.getString("subject"))
                        .append(", Location: ").append(rs.getString("location")).append("\n");
            }

            showMessage(timetableDetails.toString(), "Timetable", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            showMessage("Error retrieving timetable data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewProfile() {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT username, class, section FROM users WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String profileDetails = "Username: " + rs.getString("username") +
                        "\nClass: " + rs.getString("class") +
                        "\nSection: " + rs.getString("section");

                showMessage(profileDetails, "Student Profile", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            showMessage("Error retrieving profile data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
