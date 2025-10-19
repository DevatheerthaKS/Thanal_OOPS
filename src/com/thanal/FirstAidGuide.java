package com.thanal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FirstAidGuide extends JFrame implements ActionListener {
    private JTextArea infoArea;
    private JButton burnsBtn, cutsBtn, fractureBtn, cprBtn, snakeBiteBtn,
            chokingBtn, heartAttackBtn, heatStrokeBtn, backBtn;

    public FirstAidGuide() {
        setTitle("Thanal - First Aid Guide");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("🩺 Thanal First Aid Guide", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        burnsBtn = new JButton("🔥 Burns");
        cutsBtn = new JButton("✂️ Cuts & Bleeding");
        fractureBtn = new JButton("🦴 Fracture");
        cprBtn = new JButton("❤️ CPR");
        snakeBiteBtn = new JButton("🐍 Snake Bite");
        chokingBtn = new JButton("😮‍💨 Choking");
        heartAttackBtn = new JButton("💔 Heart Attack");
        heatStrokeBtn = new JButton("🌡️ Heat Stroke");

        JButton[] buttons = { burnsBtn, cutsBtn, fractureBtn, cprBtn, snakeBiteBtn,
                chokingBtn, heartAttackBtn, heatStrokeBtn };

        for (JButton btn : buttons) {
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            btn.addActionListener(this);
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.WEST);

        // Info display area
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(infoArea);
        add(scrollPane, BorderLayout.CENTER);

        // Back button
        backBtn = new JButton("⬅ Back to Home");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.addActionListener(e -> {
            dispose();
            new Main().setVisible(true);
        });
        add(backBtn, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == burnsBtn) {
            infoArea.setText("""
                    🔥 First Aid for Burns:
                    • Cool the burn under running water for at least 10 minutes.
                    • Do not apply ice directly.
                    • Cover with a clean, non-fluffy cloth.
                    • Seek medical help if severe.
                    """);
        } else if (e.getSource() == cutsBtn) {
            infoArea.setText("""
                    ✂️ First Aid for Cuts & Bleeding:
                    • Wash hands before touching the wound.
                    • Apply gentle pressure with a clean cloth.
                    • Keep the wound raised if possible.
                    • Seek help if bleeding doesn't stop.
                    """);
        } else if (e.getSource() == fractureBtn) {
            infoArea.setText("""
                    🦴 First Aid for Fracture:
                    • Do not move the injured part unnecessarily.
                    • Immobilize with a splint or support.
                    • Apply a cold pack to reduce swelling.
                    • Get medical attention immediately.
                    """);
        } else if (e.getSource() == cprBtn) {
            infoArea.setText("""
                    ❤️ First Aid for CPR:
                    • Check if the person is responsive and breathing.
                    • Call for emergency help.
                    • Place heel of your hand on the chest, push hard & fast (100–120/min).
                    • Give rescue breaths if trained.
                    """);
        } else if (e.getSource() == snakeBiteBtn) {
            infoArea.setText("""
                    🐍 First Aid for Snake Bite:
                    • Keep the person calm and still.
                    • Do not try to suck out the venom.
                    • Keep the bitten limb immobilized at or below heart level.
                    • Get medical help immediately.
                    """);
        } else if (e.getSource() == chokingBtn) {
            infoArea.setText("""
                    😮‍💨 First Aid for Choking:
                    • Encourage the person to cough if possible.
                    • If unable to breathe, perform abdominal thrusts (Heimlich maneuver).
                    • Call emergency help if choking continues.
                    """);
        } else if (e.getSource() == heartAttackBtn) {
            infoArea.setText("""
                    💔 First Aid for Heart Attack:
                    • Call emergency services immediately.
                    • Keep the person calm and seated.
                    • Loosen tight clothing.
                    • Give aspirin if available and not allergic.
                    """);
        } else if (e.getSource() == heatStrokeBtn) {
            infoArea.setText("""
                    🌡️ First Aid for Heat Stroke:
                    • Move the person to a cool, shaded place.
                    • Remove excess clothing.
                    • Cool with wet cloths, fanning, or ice packs.
                    • Give sips of water if conscious.
                    • Seek medical attention urgently.
                    """);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FirstAidGuide().setVisible(true));
    }
}
