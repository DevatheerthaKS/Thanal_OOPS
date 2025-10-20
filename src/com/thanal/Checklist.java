package com.thanal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Checklist extends JFrame implements ActionListener {

    private JCheckBox[] itemBoxes;
    private JButton confirmBtn, closeBtn;
    private String[] items = {
            "Water", "Torch / Flashlight", "First Aid Kit", "Emergency Numbers",
            "Battery / Power Bank", "Food / Snacks", "Blankets / Clothes",
            "Medicines", "Important Documents", "Phone / Charger", "Map / GPS"
    };

    public Checklist() {
        setTitle("Emergency Checklist - Thanal");
        setSize(650, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(230, 255, 240));

        JLabel title = new JLabel("Emergency Checklist", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(50, 20, 350, 40);
        title.setForeground(new Color(0, 100, 0));
        add(title);

        itemBoxes = new JCheckBox[items.length];
        int yPos = 80;
        for (int i = 0; i < items.length; i++) {
            itemBoxes[i] = new JCheckBox(items[i]);
            itemBoxes[i].setBounds(50, yPos, 350, 25);
            itemBoxes[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            itemBoxes[i].setBackground(new Color(230, 255, 240));
            add(itemBoxes[i]);
            yPos += 35;
        }

        confirmBtn = new JButton("Confirm Selected");
        confirmBtn.setBounds(50, yPos, 160, 35);
        confirmBtn.setBackground(new Color(144, 238, 144));
        confirmBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        confirmBtn.addActionListener(this);
        add(confirmBtn);

        closeBtn = new JButton("Close");
        closeBtn.setBounds(240, yPos, 160, 35);
        closeBtn.setBackground(new Color(255, 182, 193));
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        closeBtn.addActionListener(this);
        add(closeBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmBtn) {
            StringBuilder confirmedItems = new StringBuilder("You have confirmed:\n");
            boolean anySelected = false;
            for (JCheckBox box : itemBoxes) {
                if (box.isSelected()) {
                    confirmedItems.append("- ").append(box.getText()).append("\n");
                    anySelected = true;
                }
            }
            if (!anySelected) {
                confirmedItems = new StringBuilder("No items selected!");
            }
            JOptionPane.showMessageDialog(this, confirmedItems.toString());
        } else if (e.getSource() == closeBtn) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new Checklist();
    }
}
