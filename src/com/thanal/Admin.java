package com.thanal;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

public class Admin implements ActionListener {

    private static JFrame frame;
    private static JTextField emailField, passwordField;
    private static JButton loginBtn;
    private static JLabel messageLabel;

    private static JTable volunteerTable, incidentTable;
    private Connection conn;

    public Admin() {
        try {
            conn = DBConnection.getConnection();
        } catch (Exception e) {
            System.out.println("DB Connection Error: " + e.getMessage());
        }
    }

    // Login Dashboard
    public boolean loginAdmin(String email, String password) {
        try {
            String sql = "SELECT * FROM admins WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                messageLabel.setText(" Login successful! Welcome, " + rs.getString("name"));
                openDashboard();
                return true;
            } else {
                messageLabel.setText("Invalid email or password!");
                return false;
            }
        } catch (SQLException e) {
            messageLabel.setText(" DB Error: " + e.getMessage());
            return false;
        }
    }

    // ADMIN DASHBOARD
    private void openDashboard() {
        frame.getContentPane().removeAll();
        frame.repaint();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(new Color(240, 250, 255));
        frame.setLayout(null);

        JLabel titleLabel = new JLabel(" Thanal Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(0, 70, 120));
        titleLabel.setBounds((int) (screenSize.getWidth() / 2 - 300), 20, 600, 50);
        frame.add(titleLabel);

        // Volunteer Panel
        JPanel volunteerPanel = new JPanel();
        volunteerPanel.setLayout(null);
        volunteerPanel.setBackground(new Color(220, 255, 230));
        volunteerPanel.setBounds(200, 100, (int) screenSize.getWidth() - 400, 350);
        volunteerPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                " Registered Volunteers",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 18)));
        frame.add(volunteerPanel);

        volunteerTable = new JTable();
        JScrollPane volScroll = new JScrollPane(volunteerTable);
        volScroll.setBounds(20, 40, volunteerPanel.getWidth() - 40, 270);
        volunteerPanel.add(volScroll);

        // Incident Panel
        JPanel incidentPanel = new JPanel();
        incidentPanel.setLayout(null);
        incidentPanel.setBackground(new Color(255, 240, 240));
        incidentPanel.setBounds(200, 480, (int) screenSize.getWidth() - 400, 350);
        incidentPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                " Reported Incidents",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 18)));
        frame.add(incidentPanel);

        incidentTable = new JTable();
        JScrollPane incScroll = new JScrollPane(incidentTable);
        incScroll.setBounds(20, 40, incidentPanel.getWidth() - 40, 270);
        incidentPanel.add(incScroll);

        loadVolunteers();
        loadIncidents();

        frame.revalidate();
        frame.repaint();
    }

    private void loadVolunteers() {
        try {
            String sql = "SELECT volunteerId, name, address, phone, expertise, approved FROM volunteers";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
                    new String[] { "ID", "Name", "Address", "Phone", "Expertise", "Status", "Action" }, 0);

            while (rs.next()) {
                int volunteerId = rs.getInt("volunteerId");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String expertise = rs.getString("expertise");
                boolean approved = rs.getBoolean("approved");
                String status = approved ? " Approved" : " Pending";

                model.addRow(new Object[] { volunteerId, name, address, phone, expertise, status,
                        approved ? "—" : "Approve" });
            }

            volunteerTable.setModel(model);
            volunteerTable.setRowHeight(30);
            volunteerTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            volunteerTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
            volunteerTable.getTableHeader().setBackground(new Color(150, 200, 255));

            volunteerTable.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    int row = volunteerTable.rowAtPoint(evt.getPoint());
                    int col = volunteerTable.columnAtPoint(evt.getPoint());
                    if (col == 6) {
                        String action = (String) volunteerTable.getValueAt(row, col);
                        if ("Approve".equals(action)) {
                            int volunteerId = (int) volunteerTable.getValueAt(row, 0);
                            approveVolunteer(volunteerId);
                        }
                    }
                }
            });

            volunteerTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    String status = (String) table.getValueAt(row, 5);
                    if (status.contains("Approved")) {
                        c.setBackground(new Color(210, 255, 210));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                    return c;
                }
            });

        } catch (SQLException e) {
            messageLabel.setText(" Error loading volunteers: " + e.getMessage());
        }
    }

    // APPROVE VOLUNTEER
    private void approveVolunteer(int volunteerId) {
        try {
            String sql = "UPDATE volunteers SET approved = TRUE WHERE volunteerId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, volunteerId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(frame, "Volunteer approved successfully!");
                loadVolunteers();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, " Error approving volunteer: " + e.getMessage());
        }
    }

    private void loadIncidents() {
        try {
            String sql = "SELECT place, details, phone FROM reports";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
                    new String[] { "Place", "Details", "Phone" }, 0);

            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("place"),
                        rs.getString("details"),
                        rs.getString("phone")
                });
            }

            incidentTable.setModel(model);
            incidentTable.setRowHeight(30);
            incidentTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            incidentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
            incidentTable.getTableHeader().setBackground(new Color(255, 180, 180));

            incidentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (row % 2 == 0)
                        c.setBackground(new Color(255, 245, 245));
                    else
                        c.setBackground(new Color(255, 230, 230));
                    return c;
                }
            });

        } catch (SQLException e) {
            messageLabel.setText(" Error loading incidents: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            loginAdmin(emailField.getText(), passwordField.getText());
        }
    }

    // Main
    public static void main(String[] args) {
        Admin admin = new Admin();

        frame = new JFrame("Thanal Admin Login");
        frame.setLayout(null);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(230, 245, 255));

        JLabel title = new JLabel("🌿 Thanal Admin Portal", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(100, 30, 400, 40);
        title.setForeground(new Color(0, 90, 150));
        frame.add(title);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        emailLabel.setBounds(100, 100, 100, 25);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailField.setBounds(220, 100, 250, 30);
        frame.add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passLabel.setBounds(100, 150, 100, 25);
        frame.add(passLabel);

        passwordField = new JTextField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBounds(220, 150, 250, 30);
        frame.add(passwordField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(250, 220, 120, 40);
        loginBtn.setBackground(new Color(0, 120, 215));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.addActionListener(admin);
        frame.add(loginBtn);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setBounds(100, 280, 400, 25);
        frame.add(messageLabel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
