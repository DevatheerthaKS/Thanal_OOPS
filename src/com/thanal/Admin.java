package com.thanal;

import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Admin implements ActionListener {

    private static JFrame frame;
    private static JTextField emailField, passwordField;
    private static JButton loginBtn;
    private static JLabel messageLabel;

    private static JTable volunteerTable, incidentTable;
    private static JTextField sectionField;
    private static JTextArea contentArea;
    private static JButton updateB3;

    // Database connection
    private Connection conn;

    public Admin() {
        try {
            conn = DBConnection.getConnection();
        } catch (Exception e) {
            System.out.println("DB Connection Error: " + e.getMessage());
        }
    }

    // ------------------ LOGIN ------------------
    public boolean loginAdmin(String email, String password) {
        try {
            String sql = "SELECT * FROM admins WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                messageLabel.setText("✅ Login successful! Welcome, " + rs.getString("name"));
                openDashboard();
                return true;
            } else {
                messageLabel.setText("❌ Invalid email or password!");
                return false;
            }
        } catch (SQLException e) {
            messageLabel.setText("❌ DB Error: " + e.getMessage());
            return false;
        }
    }

    // ------------------ ADMIN DASHBOARD ------------------
    private void openDashboard() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setSize(600, 600);

        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setBounds(200, 10, 200, 30);
        frame.add(titleLabel);

        // Volunteers Table
        JLabel volLabel = new JLabel("Registered Volunteers:");
        volLabel.setBounds(50, 50, 200, 25);
        frame.add(volLabel);

        volunteerTable = new JTable();
        JScrollPane volScroll = new JScrollPane(volunteerTable);
        volScroll.setBounds(50, 80, 500, 100);
        frame.add(volScroll);

        // Incidents Table
        JLabel incLabel = new JLabel("Reported Incidents:");
        incLabel.setBounds(50, 200, 200, 25);
        frame.add(incLabel);

        incidentTable = new JTable();
        JScrollPane incScroll = new JScrollPane(incidentTable);
        incScroll.setBounds(50, 230, 500, 100);
        frame.add(incScroll);

        // First Aid update
        JLabel sectionLabel = new JLabel("Section:");
        sectionLabel.setBounds(50, 350, 100, 25);
        frame.add(sectionLabel);

        sectionField = new JTextField();
        sectionField.setBounds(150, 350, 150, 25);
        frame.add(sectionField);

        JLabel contentLabel = new JLabel("Content:");
        contentLabel.setBounds(50, 390, 100, 25);
        frame.add(contentLabel);

        contentArea = new JTextArea();
        contentArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBounds(150, 390, 300, 100);
        frame.add(scrollPane);

        updateB3 = new JButton("Update First Aid");
        updateB3.setBounds(150, 510, 150, 30);
        updateB3.addActionListener(this);
        frame.add(updateB3);

        messageLabel = new JLabel("");
        messageLabel.setBounds(50, 550, 500, 30);
        frame.add(messageLabel);

        loadVolunteers();
        loadIncidents();

        frame.revalidate();
        frame.repaint();
    }

    // Load volunteers dynamically
    private void loadVolunteers() {
        try {
            // Select all volunteers, including approval status
            String sql = "SELECT volunteerId, name, address, phone, expertise, approved FROM volunteers";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
                    new String[] { "ID", "Name", "Address", "Phone", "Expertise", "Approved", "Action" }, 0);

            while (rs.next()) {
                int volunteerId = rs.getInt("volunteerId");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String expertise = rs.getString("expertise");
                boolean approved = rs.getBoolean("approved");

                model.addRow(new Object[] { volunteerId, name, address, phone, expertise, approved ? "Yes" : "No",
                        "Approve" });
            }

            volunteerTable.setModel(model);

            // Add mouse listener for clicking "Approve"
            volunteerTable.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int row = volunteerTable.rowAtPoint(evt.getPoint());
                    int col = volunteerTable.columnAtPoint(evt.getPoint());

                    if (col == 6) { // "Action" column
                        int volunteerId = (int) volunteerTable.getValueAt(row, 0);
                        approveVolunteer(volunteerId);
                    }
                }
            });

        } catch (SQLException e) {
            messageLabel.setText("❌ DB Error loading volunteers: " + e.getMessage());
        }
    }

    // Approve volunteer by setting approved = TRUE
    private void approveVolunteer(int volunteerId) {
        try {
            String sql = "UPDATE volunteers SET approved = TRUE WHERE volunteerId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, volunteerId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                messageLabel.setText("✅ Volunteer approved successfully!");
                loadVolunteers(); // refresh table
            }
        } catch (SQLException e) {
            messageLabel.setText("❌ DB Error approving volunteer: " + e.getMessage());
        }
    }

    // Load incidents dynamically
    private void loadIncidents() {
        try {
            String sql = "SELECT place, details, phone FROM reports"; // updated table & columns
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
                    new String[] { "Place", "Details", "Phone" }, 0);

            while (rs.next()) {
                String place = rs.getString("place");
                String details = rs.getString("details");
                String phone = rs.getString("phone");

                model.addRow(new Object[] { place, details, phone });
            }
            incidentTable.setModel(model);
        } catch (SQLException e) {
            messageLabel.setText("❌ DB Error loading reports: " + e.getMessage());
        }
    }

    // ------------------ ACTION EVENTS ------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            loginAdmin(emailField.getText(), passwordField.getText());
        } else if (e.getSource() == updateB3) {
            String section = sectionField.getText();
            String content = contentArea.getText();
            if (!section.isEmpty() && !content.isEmpty()) {
                try {
                    String sql = "INSERT INTO first_aid (section, content) VALUES (?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, section);
                    stmt.setString(2, content);
                    stmt.executeUpdate();
                    messageLabel.setText("✅ First aid updated for " + section);
                } catch (SQLException ex) {
                    messageLabel.setText("❌ DB Error: " + ex.getMessage());
                }
            } else {
                messageLabel.setText("⚠️ Fill both fields!");
            }
        }
    }

    // ------------------ MAIN ------------------
    public static void main(String[] args) {
        Admin admin = new Admin();

        frame = new JFrame("Admin Login");
        frame.setLayout(null);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 60, 100, 25);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 60, 150, 25);
        frame.add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 25);
        frame.add(passLabel);

        passwordField = new JTextField();
        passwordField.setBounds(150, 100, 150, 25);
        frame.add(passwordField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(150, 150, 100, 30);
        loginBtn.addActionListener(admin);
        frame.add(loginBtn);

        messageLabel = new JLabel("");
        messageLabel.setBounds(50, 200, 300, 25);
        frame.add(messageLabel);

        frame.setVisible(true);
    }
}
