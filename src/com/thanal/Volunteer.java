package com.thanal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Volunteer extends JFrame implements ActionListener {

    private JTextField nameField, addressField, phoneField, expertiseField;
    private JButton registerBtn, backBtn;

    private Connection conn;

    public Volunteer() {
        setTitle("Thanal - Volunteer Registration");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Database connection
        try {
            conn = DBConnection.getConnection(); // make sure DBConnection class exists
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Connection Error: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        JLabel title = new JLabel("🤝 Volunteer Registration", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        JLabel nameLabel = new JLabel("👤 Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameField = new JTextField();
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        JLabel addressLabel = new JLabel("🏠 Address:");
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        addressField = new JTextField();
        formPanel.add(addressLabel);
        formPanel.add(addressField);

        JLabel phoneLabel = new JLabel("📞 Phone Number:");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        phoneField = new JTextField();
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        JLabel expertiseLabel = new JLabel("💡 Expertise:");
        expertiseLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        expertiseField = new JTextField();
        formPanel.add(expertiseLabel);
        formPanel.add(expertiseField);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        registerBtn = new JButton("✅ Register Volunteer");
        backBtn = new JButton("⬅ Back to Home");

        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));

        registerBtn.addActionListener(this);
        backBtn.addActionListener(e -> {
            dispose();
            new Main().setVisible(true);
        });

        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String phoneStr = phoneField.getText().trim();
        String expertise = expertiseField.getText().trim();

        if (name.isEmpty() || address.isEmpty() || phoneStr.isEmpty() || expertise.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            long phone = Long.parseLong(phoneStr);

            // Insert into DB
            String sql = "INSERT INTO volunteers (name, address, phone, expertise) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setLong(3, phone);
            stmt.setString(4, expertise);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Volunteer Registered Successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Clear fields
                nameField.setText("");
                addressField.setText("");
                phoneField.setText("");
                expertiseField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Phone Number!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Volunteer().setVisible(true));
    }
}
