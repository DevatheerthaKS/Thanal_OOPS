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
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(20, 20));

        try {
            conn = DBConnection.getConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Connection Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        JLabel title = new JLabel("Volunteer Registration", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(0, 90, 0));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = createTextField();
        formPanel.add(nameField, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        formPanel.add(addressLabel, gbc);

        gbc.gridx = 1;
        addressField = createTextField();
        formPanel.add(addressField, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        formPanel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        phoneField = createTextField();
        formPanel.add(phoneField, gbc);

        // Expertise
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel expertiseLabel = new JLabel("Expertise:");
        expertiseLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        formPanel.add(expertiseLabel, gbc);

        gbc.gridx = 1;
        expertiseField = createTextField();
        formPanel.add(expertiseField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 25));
        buttonPanel.setBackground(new Color(235, 255, 235));

        registerBtn = new JButton("Register Volunteer");
        styleButton(registerBtn, new Color(0, 150, 0));

        backBtn = new JButton("Back to Home");
        styleButton(backBtn, new Color(70, 120, 70));

        registerBtn.addActionListener(this);
        backBtn.addActionListener(e -> {
            dispose();
            new Main().setVisible(true);
        });

        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setPreferredSize(new Dimension(350, 45)); // Ensures enough height for descenders
        field.setBackground(Color.WHITE);
        field.setMargin(new Insets(8, 10, 8, 10)); // Proper vertical padding for j/g/p/y
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 0), 2, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return field;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 17));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String phoneStr = phoneField.getText().trim();
        String expertise = expertiseField.getText().trim();

        if (name.isEmpty() || address.isEmpty() || phoneStr.isEmpty() || expertise.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            long phone = Long.parseLong(phoneStr);

            String sql = "INSERT INTO volunteers (name, address, phone, expertise) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setLong(3, phone);
            stmt.setString(4, expertise);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this,
                        "Volunteer Registered Successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                addressField.setText("");
                phoneField.setText("");
                expertiseField.setText("");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid Phone Number!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Volunteer().setVisible(true));
    }
}
