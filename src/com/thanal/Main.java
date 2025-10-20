package com.thanal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener {

    private JButton userPortalBtn, adminPanelBtn, reportIncidentBtn, volunteerSectionBtn,
            firstAidBtn, contactsBtn, checklistBtn, exitBtn;

    public Main() {
        setTitle("Thanal - Home Page");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("Welcome to Thanal App", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(0, 128, 0));
        title.setBounds(0, 40, 1000, 60);
        add(title);

        Color buttonColor = new Color(230, 255, 230);
        Color hoverColor = new Color(200, 245, 200);

        userPortalBtn = createButton("User Portal", 120, 150, 300, 70, buttonColor, hoverColor);
        adminPanelBtn = createButton("Admin Panel", 580, 150, 300, 70, buttonColor, hoverColor);
        reportIncidentBtn = createButton("Report Incident", 120, 250, 300, 70, buttonColor, hoverColor);
        volunteerSectionBtn = createButton("Volunteer Section", 580, 250, 300, 70, buttonColor, hoverColor);
        firstAidBtn = createButton("First Aid Guide", 120, 350, 300, 70, buttonColor, hoverColor);
        contactsBtn = createButton("Emergency Contacts", 580, 350, 300, 70, buttonColor, hoverColor);
        checklistBtn = createButton("Checklist", 120, 450, 300, 70, buttonColor, hoverColor);
        exitBtn = createButton("Exit", 580, 450, 300, 70, new Color(255, 230, 230), new Color(255, 200, 200));

        add(userPortalBtn);
        add(adminPanelBtn);
        add(reportIncidentBtn);
        add(volunteerSectionBtn);
        add(firstAidBtn);
        add(contactsBtn);
        add(checklistBtn);
        add(exitBtn);

        JLabel footer = new JLabel("Thanal © 2025 | Connecting Safety and Care", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footer.setForeground(new Color(34, 139, 34));
        footer.setBounds(0, 620, 1000, 30);
        add(footer);

        setVisible(true);
    }

    private JButton createButton(String text, int x, int y, int width, int height, Color bgColor, Color hoverColor) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, width, height);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(144, 238, 144), 2, true));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });

        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userPortalBtn) {
            User.main(new String[] {});
        } else if (e.getSource() == adminPanelBtn) {
            Admin.main(new String[] {});
        } else if (e.getSource() == reportIncidentBtn) {
            Report.main(new String[] {});
        } else if (e.getSource() == volunteerSectionBtn) {
            Volunteer.main(new String[] {});
        } else if (e.getSource() == firstAidBtn) {
            FirstAidGuide.main(new String[] {});
        } else if (e.getSource() == contactsBtn) {
            EmergencyContacts.main(new String[] {});
        } else if (e.getSource() == checklistBtn) {
            Checklist.main(new String[] {});
        } else if (e.getSource() == exitBtn) {
            int confirm = JOptionPane.showConfirmDialog(this, "Exit Thanal App?", "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
