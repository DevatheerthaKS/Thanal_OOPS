package com.thanal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener {

    private JButton userPortalBtn, adminPanelBtn, reportIncidentBtn, volunteerSectionBtn, firstAidBtn, exitBtn;

    public Main() {
        setTitle("Thanal - Home Page");
        setLayout(null);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(220, 255, 220)); // light green

        JLabel title = new JLabel("Welcome to Thanal App", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(100, 30, 300, 40);
        add(title);

        userPortalBtn = new JButton("👤 User Portal");
        userPortalBtn.setBounds(150, 90, 200, 30);
        userPortalBtn.addActionListener(this);

        adminPanelBtn = new JButton("🧑‍💼 Admin Panel");
        adminPanelBtn.setBounds(150, 130, 200, 30);
        adminPanelBtn.addActionListener(this);

        reportIncidentBtn = new JButton("💬 Report Incident");
        reportIncidentBtn.setBounds(150, 170, 200, 30);
        reportIncidentBtn.addActionListener(this);

        volunteerSectionBtn = new JButton("🧾 Volunteer Section");
        volunteerSectionBtn.setBounds(150, 210, 200, 30);
        volunteerSectionBtn.addActionListener(this);

        firstAidBtn = new JButton("📖 First Aid Guide");
        firstAidBtn.setBounds(150, 250, 200, 30);
        firstAidBtn.addActionListener(this);

        exitBtn = new JButton("🚪 Exit");
        exitBtn.setBounds(150, 290, 200, 30);
        exitBtn.addActionListener(this);

        add(userPortalBtn);
        add(adminPanelBtn);
        add(reportIncidentBtn);
        add(volunteerSectionBtn);
        add(firstAidBtn);
        add(exitBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userPortalBtn) {
            JOptionPane.showMessageDialog(this, "Opening User Portal...");
            User.main(new String[] {}); // Open User UI
        } else if (e.getSource() == adminPanelBtn) {
            JOptionPane.showMessageDialog(this, "Opening Admin Panel...");
            Admin.main(new String[] {}); // Open Admin UI
        } else if (e.getSource() == reportIncidentBtn) {
            JOptionPane.showMessageDialog(this, "Opening Report Incident Page...");
            Report.main(new String[] {}); // (You'll create ReportUI.java)
        } else if (e.getSource() == volunteerSectionBtn) {
            JOptionPane.showMessageDialog(this, "Opening Volunteer Section...");
            Volunteer.main(new String[] {}); // (You'll create VolunteerUI.java)
        } else if (e.getSource() == firstAidBtn) {
            JOptionPane.showMessageDialog(this, "Opening First Aid Guide...");
            FirstAidGuide.main(new String[] {}); // (You'll create FirstAidUI.java)
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
