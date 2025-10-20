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
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(235, 255, 240));

        JLabel title = new JLabel("Thanal First Aid Guide", JLabel.CENTER);
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        title.setForeground(new Color(0, 102, 102));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 15, 15));
        buttonPanel.setBackground(new Color(220, 250, 230));
        buttonPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 153, 102), 2),
                "Select an Emergency",
                0, 0, new Font("Segoe UI", Font.BOLD, 16),
                new Color(0, 102, 51)));

        burnsBtn = createStyledButton("Burns");
        cutsBtn = createStyledButton("Cuts & Bleeding");
        fractureBtn = createStyledButton("Fracture");
        cprBtn = createStyledButton("CPR");
        snakeBiteBtn = createStyledButton("Snake Bite");
        chokingBtn = createStyledButton("Choking");
        heartAttackBtn = createStyledButton("Heart Attack");
        heatStrokeBtn = createStyledButton("Heat Stroke");

        JButton[] buttons = { burnsBtn, cutsBtn, fractureBtn, cprBtn, snakeBiteBtn,
                chokingBtn, heartAttackBtn, heatStrokeBtn };

        for (JButton btn : buttons) {
            btn.addActionListener(this);
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.WEST);

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setForeground(new Color(0, 60, 60));
        infoArea.setBackground(new Color(250, 255, 250));
        infoArea.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 153, 102), 2),
                "First Aid Instructions",
                0, 0, new Font("Segoe UI", Font.BOLD, 16),
                new Color(0, 102, 51)));
        add(scrollPane, BorderLayout.CENTER);

        backBtn = new JButton("Back to Home");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.setBackground(new Color(0, 153, 102));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backBtn.addActionListener(e -> {
            dispose();
            new Main().setVisible(true);
        });
        add(backBtn, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(new Color(230, 255, 240));
        btn.setForeground(new Color(0, 102, 51));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(0, 153, 102), 1, true));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(200, 255, 225));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(230, 255, 240));
            }
        });

        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String header = "";
        String[] points = new String[0];

        if (e.getSource() == burnsBtn) {
            header = "FIRST AID FOR BURNS";
            points = new String[] {
                    "Cool the burn under clean running water for at least 10 minutes.",
                    "Do NOT apply ice directly to the burn.",
                    "Cover with a sterile, non-fluffy cloth or bandage.",
                    "Avoid bursting any blisters.",
                    "Seek medical help if the burn is large or deep."
            };
        } else if (e.getSource() == cutsBtn) {
            header = "FIRST AID FOR CUTS & BLEEDING";
            points = new String[] {
                    "Wash your hands before touching the wound.",
                    "Apply gentle pressure with a clean cloth or bandage.",
                    "Keep the injured area raised above heart level if possible.",
                    "Clean around the wound, but avoid alcohol or hydrogen peroxide.",
                    "Seek medical attention if bleeding doesn't stop within 10 minutes."
            };
        } else if (e.getSource() == fractureBtn) {
            header = "FIRST AID FOR FRACTURES";
            points = new String[] {
                    "Do NOT move the injured area unnecessarily.",
                    "Use a splint or cloth to support and immobilize the limb.",
                    "Apply a cold pack to reduce swelling.",
                    "Do not try to straighten bones yourself.",
                    "Call for emergency help immediately."
            };
        } else if (e.getSource() == cprBtn) {
            header = "FIRST AID FOR CPR (Cardiopulmonary Resuscitation)";
            points = new String[] {
                    "Check for responsiveness and breathing.",
                    "Call emergency services immediately.",
                    "Place the heel of one hand on the center of the chest and interlock fingers.",
                    "Push hard and fast at 100–120 compressions per minute.",
                    "Give rescue breaths if trained — 30 compressions + 2 breaths."
            };
        } else if (e.getSource() == snakeBiteBtn) {
            header = "FIRST AID FOR SNAKE BITE";
            points = new String[] {
                    "Keep the person calm and still to slow venom spread.",
                    "Do NOT suck out the venom or cut the bite area.",
                    "Keep the bitten limb immobilized and below heart level.",
                    "Remove tight clothing or jewelry near the bite.",
                    "Seek medical help immediately — try to remember the snake’s color/shape."
            };
        } else if (e.getSource() == chokingBtn) {
            header = "FIRST AID FOR CHOKING";
            points = new String[] {
                    "Encourage the person to cough if they can breathe.",
                    "If unable to breathe or speak, perform the Heimlich maneuver.",
                    "Stand behind, make a fist, and thrust inward & upward under the rib cage.",
                    "Repeat until the object is expelled or the person becomes unconscious.",
                    "Call emergency help immediately if choking continues."
            };
        } else if (e.getSource() == heartAttackBtn) {
            header = "FIRST AID FOR HEART ATTACK";
            points = new String[] {
                    "Call emergency services right away.",
                    "Keep the person calm and seated comfortably.",
                    "Loosen tight clothing for easier breathing.",
                    "If not allergic, give one regular aspirin (to chew).",
                    "If the person becomes unconscious, start CPR immediately."
            };
        } else if (e.getSource() == heatStrokeBtn) {
            header = "FIRST AID FOR HEAT STROKE";
            points = new String[] {
                    "Move the person to a cool, shaded area.",
                    "Remove excess or tight clothing.",
                    "Cool the body using wet cloths, fanning, or ice packs near the neck/armpits.",
                    "Give small sips of cool water if the person is conscious.",
                    "Seek medical attention immediately — heat stroke is life-threatening."
            };
        }

        StringBuilder formatted = new StringBuilder(header + "\n\n");
        for (int i = 0; i < points.length; i++) {
            formatted.append(points[i]).append("\n\n");
        }
        infoArea.setText(formatted.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FirstAidGuide().setVisible(true));
    }
}
