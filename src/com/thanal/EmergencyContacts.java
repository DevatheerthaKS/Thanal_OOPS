package com.thanal;

public class EmergencyContacts {
    private String[][] helplines = {
            { "Police", "100" },
            { "Fire & Rescue", "101" },
            { "Ambulance", "102" },
            { "Disaster Helpline", "108" },
            { "National Disaster Helpline", "1078" }
    };

    public void showContacts() {
        System.out.println("\n THANAL - Emergency Contact System ");
        System.out.println("Emergency Helplines:");
        for (int i = 0; i < helplines.length; i++) {
            System.out.println((i + 1) + ") " + helplines[i][0] + " - " + helplines[i][1]);
        }
    }
}
