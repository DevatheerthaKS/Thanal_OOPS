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
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("📢 Report an Incident", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        JLabel placeLabel = new JLabel("📍 Location:");
        placeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        placeField = new JTextField();
        formPanel.add(placeLabel);
        formPanel.add(placeField);

        JLabel detailsLabel = new JLabel("📝 Details:");
        detailsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        detailsArea = new JTextArea(4, 20);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        formPanel.add(detailsLabel);
        formPanel.add(scrollPane);

        JLabel phoneLabel = new JLabel("📞 Phone Number:");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        phoneField = new JTextField();
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        submitBtn = new JButton("✅ Submit Report");
        backBtn = new JButton("⬅ Back to Home");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));

        submitBtn.addActionListener(this);
        backBtn.addActionListener(e -> {
            dispose();
            new Main().setVisible(true);
        });

        buttonPanel.add(submitBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String place = placeField.getText().trim();
        String details = detailsArea.getText().trim();
        String phoneStr = phoneField.getText().trim();

        if (place.isEmpty() || details.isEmpty() || phoneStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            long phoneNumber = Long.parseLong(phoneStr);

            // ✅ Insert data into database
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO reports (place, details, phone) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, place);
                stmt.setString(2, details);
                stmt.setLong(3, phoneNumber);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "✅ Incident Report Submitted Successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    placeField.setText("");
                    detailsArea.setText("");
                    phoneField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ Failed to submit report. Try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "❌ Database Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Report().setVisible(true));
    }
}
