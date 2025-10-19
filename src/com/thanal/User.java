package com.thanal;

import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class User implements ActionListener {

    private int userId;
    private String name;
    private String email;
    private long phoneNumber;
    private String password;

    private static JFrame frame;
    private static JTextField nameField, emailField, phoneField;
    private static JPasswordField passwordField;
    private static JButton registerBtn, loginBtn;
    private static JLabel messageLabel;

    public User(int userId, String name, String email, long phoneNumber, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // ✅ Register user in database
    public void register(String name, String email, long phoneNumber, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO users (name, email, phoneNumber, password) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setLong(3, phoneNumber);
            stmt.setString(4, password);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(frame, "✅ User " + name + " registered successfully!");
            messageLabel.setText("Now login with your credentials.");
        } catch (SQLIntegrityConstraintViolationException e) {
            messageLabel.setText("⚠️ Email already registered!");
        } catch (SQLException e) {
            messageLabel.setText("❌ Database error: " + e.getMessage());
        }
    }

    // ✅ Login user from database
    public boolean login(String email, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userName = rs.getString("name");
                JOptionPane.showMessageDialog(frame, "✅ Login successful! Welcome " + userName + "!");

                // Close login window and open main page
                frame.dispose();
                Main.main(new String[] {}); // 👈 Go to Home Page
                return true;
            } else {
                messageLabel.setText("❌ Invalid email or password!");
                return false;
            }
        } catch (SQLException e) {
            messageLabel.setText("❌ Database error: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        User gui = new User(0, "", "", 0, "");

        frame = new JFrame("User Registration & Login");
        frame.setLayout(null);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 40, 100, 25);
        nameField = new JTextField();
        nameField.setBounds(150, 40, 150, 25);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 80, 100, 25);
        emailField = new JTextField();
        emailField.setBounds(150, 80, 150, 25);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 120, 100, 25);
        phoneField = new JTextField();
        phoneField.setBounds(150, 120, 150, 25);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 160, 100, 25);
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 160, 150, 25);

        registerBtn = new JButton("Register");
        registerBtn.setBounds(50, 210, 100, 30);
        registerBtn.addActionListener(gui);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(200, 210, 100, 30);
        loginBtn.addActionListener(gui);

        messageLabel = new JLabel("");
        messageLabel.setBounds(50, 260, 300, 25);

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(phoneLabel);
        frame.add(phoneField);
        frame.add(passLabel);
        frame.add(passwordField);
        frame.add(registerBtn);
        frame.add(loginBtn);
        frame.add(messageLabel);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phoneText = phoneField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (e.getSource() == registerBtn) {
            try {
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phoneText.isEmpty()) {
                    messageLabel.setText("⚠️ Please fill all fields!");
                    return;
                }

                long phone = Long.parseLong(phoneText);
                register(name, email, phone, password);
            } catch (NumberFormatException ex) {
                messageLabel.setText("⚠️ Invalid phone number!");
            }
        } else if (e.getSource() == loginBtn) {
            if (email.isEmpty() || password.isEmpty()) {
                messageLabel.setText("⚠️ Please enter email and password!");
                return;
            }
            login(email, password);
        }
    }
}
