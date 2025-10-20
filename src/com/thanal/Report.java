package com.thanal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Report extends JFrame implements ActionListener {
    private JTextField placeField, phoneField;
    private JTextArea detailsArea;
    private JButton submitBtn, backBtn;

    public Report() {
        setTitle("Thanal - Report an Incident");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(220, 255, 220));
        setLayout(new BorderLayout(20, 20));

        JLabel title = new JLabel("Report an Incident", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(new Color(0, 90, 0));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 15, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        formPanel.setBackground(new Color(220, 255, 220));

        JLabel placeLabel = new JLabel("Location:");
        placeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        placeField = new JTextField();
        styleTextField(placeField);

        JLabel detailsLabel = new JLabel("Incident Details:");
        detailsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        detailsArea = new JTextArea(3, 20);
        detailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 0), 2, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        phoneField = new JTextField();
        styleTextField(phoneField);

        formPanel.add(placeLabel);
        formPanel.add(placeField);
        formPanel.add(detailsLabel);
        formPanel.add(new JScrollPane(detailsArea));
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setBackground(new Color(200, 245, 200));

        submitBtn = new JButton("Submit Report");
        styleButton(submitBtn, new Color(0, 150, 0));

        backBtn = new JButton("Back to Home");
        styleButton(backBtn, new Color(50, 100, 50));

        submitBtn.addActionListener(this);
        backBtn.addActionListener(e -> {
            dispose();
            new Main().setVisible(true);
        });

        buttonPanel.add(submitBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 0), 2, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
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
        String place = placeField.getText().trim();
        String details = detailsArea.getText().trim();
        String phoneStr = phoneField.getText().trim();

        if (place.isEmpty() || details.isEmpty() || phoneStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            long phoneNumber = Long.parseLong(phoneStr);

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO reports (place, details, phone) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, place);
                stmt.setString(2, details);
                stmt.setLong(3, phoneNumber);

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Incident Report Submitted Successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    placeField.setText("");
                    detailsArea.setText("");
                    phoneField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to submit report. Try again.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Report().setVisible(true));
    }
}
