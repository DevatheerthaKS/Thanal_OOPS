package com.thanal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class EmergencyContacts extends JFrame implements ActionListener {

        private JComboBox<String> districtCombo;
        private JTextArea contactArea;
        private JButton showBtn, backBtn;

        private final Map<String, String[][]> districtHelplines = new HashMap<>();
        private final String[][] generalHelplines = {
                        { "Police", "100" },
                        { "Fire & Rescue", "101" },
                        { "Ambulance", "102" },
                        { "Disaster Helpline", "108" },
                        { "National Disaster Helpline", "1078" },
                        { "Women Helpline", "1091" },
                        { "Pink Patrol", "1515" }
        };

        public EmergencyContacts() {
                setTitle("Thanal - Emergency Contacts");
                setSize(900, 800);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setLayout(new BorderLayout(15, 15));
                getContentPane().setBackground(new Color(245, 255, 245));

                initDistrictContacts();

                JLabel title = new JLabel("Emergency Helplines", SwingConstants.CENTER);
                title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 30));
                title.setForeground(new Color(0, 102, 51));
                title.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
                add(title, BorderLayout.NORTH);

                JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
                mainPanel.setOpaque(false);

                JPanel generalPanel = new JPanel(new BorderLayout());
                generalPanel.setBackground(new Color(235, 255, 240));
                generalPanel.setBorder(BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(new Color(0, 153, 102), 2),
                                "General Emergency Contacts",
                                0, 0, new Font("Segoe UI", Font.BOLD, 18), new Color(0, 102, 51)));

                JPanel grid = new JPanel(new GridLayout(generalHelplines.length, 1, 10, 5));
                grid.setOpaque(false);
                for (String[] contact : generalHelplines) {
                        JLabel label = new JLabel(contact[0] + "  →  " + contact[1]);
                        label.setFont(new Font("Segoe UI", Font.PLAIN, 17));
                        label.setForeground(new Color(0, 80, 60));
                        grid.add(label);
                }
                generalPanel.add(grid, BorderLayout.CENTER);
                mainPanel.add(generalPanel);

                JPanel districtPanel = new JPanel(new BorderLayout(10, 10));
                districtPanel.setBackground(new Color(235, 255, 250));
                districtPanel.setBorder(BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(new Color(0, 153, 153), 2),
                                "District-wise Contacts",
                                0, 0, new Font("Segoe UI", Font.BOLD, 18), new Color(0, 102, 102)));

                JPanel selectionPanel = new JPanel();
                selectionPanel.setOpaque(false);
                JLabel selectLabel = new JLabel("Select District:");
                selectLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

                districtCombo = new JComboBox<>(districtHelplines.keySet().toArray(new String[0]));
                districtCombo.setSelectedIndex(-1);
                districtCombo.setFont(new Font("Segoe UI", Font.PLAIN, 15));

                showBtn = new JButton("Show Contacts");
                styleButton(showBtn, new Color(0, 153, 102));

                selectionPanel.add(selectLabel);
                selectionPanel.add(districtCombo);
                selectionPanel.add(showBtn);
                districtPanel.add(selectionPanel, BorderLayout.NORTH);

                contactArea = new JTextArea();
                contactArea.setEditable(false);
                contactArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                contactArea.setForeground(new Color(0, 51, 51));
                contactArea.setBackground(Color.WHITE);
                contactArea.setMargin(new Insets(10, 10, 10, 10));

                JScrollPane scrollPane = new JScrollPane(contactArea);
                scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102), 1));
                districtPanel.add(scrollPane, BorderLayout.CENTER);

                mainPanel.add(districtPanel);
                add(mainPanel, BorderLayout.CENTER);

                JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
                bottomPanel.setBackground(new Color(240, 255, 240));

                backBtn = new JButton("Back to Home");
                styleButton(backBtn, new Color(0, 120, 70));
                bottomPanel.add(backBtn);

                add(bottomPanel, BorderLayout.SOUTH);

                showBtn.addActionListener(this);
                backBtn.addActionListener(e -> {
                        dispose();
                        new Main().setVisible(true);
                });
        }

        private void styleButton(JButton button, Color bgColor) {
                button.setFont(new Font("Segoe UI", Font.BOLD, 15));
                button.setForeground(Color.WHITE);
                button.setBackground(bgColor);
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
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

        private void initDistrictContacts() {
                districtHelplines.put("Wayanad", new String[][] {
                                { "Police", "100" },
                                { "Ambulance", "102" },
                                { "Fire", "101" },
                                { "Health(DMO)", "04935 240390" },
                                { "Collectorate", "04936202251" },
                                { "KSRTC Sultan Bathery", "04936 220217" },
                                { "KSRTC Mananthavady", "04935 240640" },
                                { "KSRTC Kalpetta", "04936 203040" },
                                { "KSEB", "04935-240289" }
                });

                districtHelplines.put("Ernakulam", new String[][] {
                                { "Police", "100" },
                                { "Ambulance", "102" },
                                { "Fire", "101" },
                                { "KSRTC Aluva", "0484-2624242" },
                                { "KSRTC Angamali", "0484-2453050" },
                                { "KSRTC Ernakulam", "0484-2372033" },
                                { "KSRTC Kothamangalam", "0485-2862202" },
                                { "KSRTC Koothattukulam", "0485-2253444" },
                                { "KSRTC Moovattupuzha", "0485-2832321" },
                                { "KSRTC North paravur", "0484-2442373" },
                                { "KSRTC Perumbavoor", "0484-2523416" },
                                { "KSRTC Piravom", "0485-2265533" },
                                { "KSEB", "0484-2392248" },
                                { "Collectorate", "0484-24223001" },
                                { "Health(DMO)", "0484-2360802" }
                });

                districtHelplines.put("Idukki", new String[][] {
                                { "Police", "100" },
                                { "Ambulance", "102" },
                                { "Fire", "101" },
                                { "Collectorate", "+91 4862232242" },
                                { "Health(DMO)", "04862-233030" },
                                { "KSEB Kattappana Electrical Division", "04868272348" },
                                { "KSEB Thodupuzha Electrical Division", "04862220140" },
                                { "KSRTC Thodupuzha", "04862 222388" },
                                { "KSRTC Munnar", "04865 230201" },
                                { "KSRTC Moolamattom", "04862 252045" },
                                { "KSRTC Kattappana", "04862 252333" },
                                { "KSRTC Idukki", "04862 232244" },
                                { "KSRTC Vandiperiyar", "04862 252733" }
                });

                districtHelplines.put("Kasaragod", new String[][] {
                                { "Police", "100" },
                                { "Ambulance", "102" },
                                { "Fire", "101" },
                                { "Health(DMO)", "0467 220 3118" },
                                { "KSEB", "04998-225622" },
                                { "KSRTC", "0499-4230677" },
                                { "Collectorate", "+91-4994-256400" }
                });

                districtHelplines.put("Kannur", new String[][] {
                                { "Police", "100" },
                                { "Ambulance", "102" },
                                { "KSRTC", "0497-2707777" },
                                { "KSEB", "0497-2707777" },
                                { "Health(DMO)", "0497 270 0194" },
                                { "Collectorate", "0497 270 0243" }
                });

                districtHelplines.put("Kozhikode", new String[][] {
                                { "Police", "100" },
                                { "Ambulance", "102" },
                                { "Fire", "101" },
                                { "Health(DMO)", "04935 240390" },
                                { "Collectorate", "04936202251" },
                                { "KSRTC Vadakara", "0496-2523377" },
                                { "KSRTC Thottilpalam", "0496-2566200" },
                                { "KSRTC Thiruvambady", "0495-2254500" },
                                { "KSRTC Thamarassery", "0495-2222217" },
                                { "KSRTC Kozhikode", "0495-2723796" },
                                { "KSEB", "04935-240289" }
                });

                districtHelplines.put("Malappuram", new String[][] {
                                { "Police", "100" },
                                { "Ambulance", "102" },
                                { "Fire", "101" },
                                { "Health(DMO)", "0483 273 7857" },
                                { "Collectorate", "0483 273 4922" },
                                { "KSEB Malappuram Office", "0483-2734913" },
                                { "KSEB Manjeri Circle Office", "0483-2766848" },
                                { "KSRTC", "0483-2734950" }
                });

                districtHelplines.put("Palakkad", new String[][] {
                                { "Police", "100" },
                                { "Ambulance", "102" },
                                { "Fire", "101" },
                                { "Health(DMO)", "0491 250 5264" },
                                { "Collectorate", "0491 250 5309" },
                                { "KSEB", "9496001912" },
                                { "KSRTC Bus Stand", "0491-2520098" },
                                { "Vadakkencherry", "04922255001" },
                                { "Mannarkkad", "04924225150" },
                                { "Chittur", "04923227488" }
                });

                districtHelplines.put("Thrissur", new String[][] {
                                { "Police", "100" },
                                { "Ambulance", "102" },
                                { "Fire", "101" },
                                { "Collectorate", "0487 236 1020" },
                                { "Health(DMO)", "0487 233 3242" },
                                { "KSRTC", "0487 2421150" },
                                { "KSRTC Thrissur Town", "0487 2556450" },
                                { "Puthukkad", "0480 2751648" },
                                { "Mala", "0480 2890438" },
                                { "Kodungallur", "0480 2803155" },
                                { "Chalakudy", "0480 2701638" },
                                { "KSEB", "0487-2607337" }
                });

                districtHelplines.put("Kottayam", new String[][] {
                                { "Police", "100" },
                                { "Ambulance", "102" },
                                { "Fire", "101" },
                                { "KSRTC", "+91 4812562908" },
                                { "KSEB", "+91 4812568050" },
                                { "Collectorate", "9447029007" },
                                { "Health(DMO)", "0481 256 2778" }
                });

                districtHelplines.put("Alappuzha", new String[][] {
                                { "Police", "100" },
                                { "Ambulance", "102" },
                                { "Fire", "101" },
                                { "KSRTC", "0477-2252501" },
                                { "KSEB", "0477-2245436" },
                                { "Collectorate", "0477 225 1720" },
                                { "Health(DMO)", "0477 225 1650" }
                });

                districtHelplines.put("Pathanamthitta", new String[][] {
                                { "Police", "100" },
                                { "Ambulance", "102" },
                                { "Fire", "101" },
                                { "Pathanamthitta District Transport Officer", "" },
                                { "Health(DMO)", "0468-2228220, 994610547" },
                                { "Collectorate", "0468 222 2505" },
                                { "KSEB", "0468 222 2337" },
                                { "KSRTC DTO Pathanamthitta", "0468 2229213" },
                                { "Adoor", "04734 224767, 224764" },
                                { "Konni", "0468 2244555" },
                                { "Chengannur", "0479 2452213, 2452352" },
                                { "Mallappally", "0469 2785080" },
                                { "Pandalam", "04734 255800" },
                                { "Pamba", "04735 203445" },
                                { "Ranni", "04735 225253" },
                                { "Thiruvalla", "0469 2601345, 2602945" }
                });

                districtHelplines.put("Kollam", new String[][] {
                                { "Police", "100" },
                                { "Chengannur", "0479 2452213, 2452352" },
                                { "Ambulance", "102" },
                                { "Fire", "101" },
                                { "Traffic alert", "1099" },
                                { "Highway Alert", "9846100100" },
                                { "Rail Alert", "9846200100" },
                                { "Computer Emergency Response Team", "0471-2725646" },
                                { "Kerala Police Message Centre", "9497900000" }
                });

                districtHelplines.put("Thiruvananthapuram", new String[][] {
                                { "Police", "100" },
                                { "Ambulance", "102" },
                                { "Fire", "101" },
                                { "Control room", "0471-2730067" },
                                { "Media Center", "0471-2730087" },
                                { "Disaster Management Services", "0471-2730045" },
                                { "Railway Police Alert", "9846200100" },
                                { "Highway Alert", "9846100100" },
                                { "Road Accident Emergency Service", "108" },
                                { "Children In Difficult Situation", "1098" }
                });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                String selectedDistrict = (String) districtCombo.getSelectedItem();
                if (selectedDistrict == null) {
                        JOptionPane.showMessageDialog(this, "Please select a district!", "Warning",
                                        JOptionPane.WARNING_MESSAGE);
                        return;
                }

                String[][] contacts = districtHelplines.get(selectedDistrict);
                StringBuilder sb = new StringBuilder();
                sb.append("Helplines for ").append(selectedDistrict).append(":\n\n");
                for (String[] contact : contacts) {
                        sb.append("• ").append(contact[0]).append("  →  ").append(contact[1]).append("\n");
                }

                contactArea.setText(sb.toString());
        }

        public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> new EmergencyContacts().setVisible(true));
        }
}
