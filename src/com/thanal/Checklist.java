package com.thanal;

public class Checklist {

    public void displayChecklist() {
        System.out.println("\n Emergency Checklist ");
        String[] items = { "Water", "Torch", "First Aid Kit", "Emergency Numbers" };
        for (int i = 0; i < items.length; i++) {
            System.out.println((i + 1) + ". " + items[i]);
        }
    }
}
