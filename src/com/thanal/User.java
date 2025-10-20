package com.thanal;

import java.awt.*;
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
    private static JButton registerBtn, loginBtn, backBtn;
    private static JLabel messageLabel;

    public User(int userId, String name, String email, long phoneNumber, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

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

            JOptionPane.showMessageDialog(frame, "User " + name + " registered successfully!");
            messageLabel.setText("Now login with your credentials.");
        } catch (SQLIntegrityConstraintViolationException e) {
            messageLabel.setText("Email already registered!");
        } catch (SQLException e) {
            messageLabel.setText(" Database error: " + e.getMessage());
        }
    }

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
                JOptionPane.showMessageDialog(frame, " Login successful! Welcome " + userName + "!");
                frame.dispose();
                new Main().setVisible(true);
                return true;
            } else {
                messageLabel.setText(" Invalid email or password!");
                return false;
            }
        } catch (SQLException e) {
            messageLabel.setText(" Database error: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        User gui = new User(0, "", "", 0, "");

        frame = new JFrame("Thanal - User Login & Registration");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(220, 255, 220));
        frame.setLayout(null);

        JLabel title = new JLabel("👤 Thanal User Portal", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBounds(150, 30, 400, 40);
        title.setForeground(new Color(0, 90, 0));
        frame.add(title);

        int labelX = 150, fieldX = 300, y = 100, gap = 60;

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(labelX, y, 150, 60);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        frame.add(nameLabel);

        nameField = createTextField(fieldX, y);
        frame.add(nameField);

        y += gap;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(labelX, y, 150, 30);
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        frame.add(emailLabel);

        emailField = createTextField(fieldX, y);
        frame.add(emailField);

        y += gap;
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(labelX, y, 150, 30);
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        frame.add(phoneLabel);

        phoneField = createTextField(fieldX, y);
        frame.add(phoneField);

        y += gap;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(labelX, y, 150, 30);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        frame.add(passLabel);

        passwordField = new JPasswordField();
        styleField(passwordField);
        passwordField.setBounds(fieldX, y, 220, 35);
        frame.add(passwordField);

        registerBtn = createButton(" Register", new Color(0, 150, 0));
        registerBtn.setBounds(180, y + 70, 150, 45);
        registerBtn.addActionListener(gui);
        frame.add(registerBtn);

        loginBtn = createButton(" Login", new Color(0, 120, 180));
        loginBtn.setBounds(370, y + 70, 150, 45);
        loginBtn.addActionListener(gui);
        frame.add(loginBtn);

        backBtn = createButton("⬅ Back", new Color(120, 120, 120));
        backBtn.setBounds(280, y + 130, 150, 40);
        backBtn.addActionListener(e -> {
            frame.dispose();
            new Main().setVisible(true);
        });
        frame.add(backBtn);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setBounds(100, y + 190, 500, 30);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        messageLabel.setForeground(new Color(0, 80, 0));
        frame.add(messageLabel);

        frame.setVisible(true);
    }

    private static JTextField createTextField(int x, int y) {
        JTextField field = new JTextField();
        styleField(field);
        field.setBounds(x, y, 240, 42); // Increased height from 35 → 42 and width a bit
        return field;
    }

    private static void styleField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 17)); // slightly bigger font
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 0), 2, true),
                BorderFactory.createEmptyBorder(5, 8, 5, 8))); // reduced padding to fit descenders properly
        field.setCaretColor(new Color(0, 100, 0)); // green blinking cursor for aesthetics
    }

    private static JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
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
                    messageLabel.setText(" Please fill all fields!");
                    return;
                }
                long phone = Long.parseLong(phoneText);
                register(name, email, phone, password);
            } catch (NumberFormatException ex) {
                messageLabel.setText(" Invalid phone number!");
            }
        } else if (e.getSource() == loginBtn) {
            if (email.isEmpty() || password.isEmpty()) {
                messageLabel.setText(" Please enter email and password!");
                return;
            }
            login(email, password);
        }
    }
}
