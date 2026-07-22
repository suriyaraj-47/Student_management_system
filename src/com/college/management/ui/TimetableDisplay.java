package com.college.management.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class TimetableDisplay {

    public JTable createTimetableTable(String className, String section) {
        String[] columnNames = {"Day", "Time Slot", "Subject", "Location", "Teacher Name"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/shriramgopalakrishnan/IdeaProjects/JavaMiniProject/college_management.db")) {
            String query = "SELECT day, time_slot, subject, location, teacher_name FROM timetable WHERE class = ? AND section = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, className);
                stmt.setString(2, section);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Vector<String> row = new Vector<>();
                    row.add(rs.getString("day"));
                    row.add(rs.getString("time_slot"));
                    row.add(rs.getString("subject"));
                    row.add(rs.getString("location"));
                    row.add(rs.getString("teacher_name"));
                    model.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JTable timetableTable = new JTable(model);
        timetableTable.setFillsViewportHeight(true);
        return timetableTable;
    }

    public void displayTimetable(String className, String section) {
        JFrame frame = new JFrame("Timetable");
        JTable table = createTimetableTable(className, section);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
