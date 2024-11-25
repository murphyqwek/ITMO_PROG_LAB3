package org.example;

import org.example.items.Item;
import org.example.items.prison.SharpOfGlass;
import org.example.jails.RegularJail;
import org.example.persons.Human;
import org.example.persons.policemen.PrisonOfficer;

public class Main {
    public static void main(String[] args) {
        RegularJail jail = new RegularJail(new Item[]{ new SharpOfGlass() });
        PrisonOfficer officer = new PrisonOfficer(0, "Klimenkov", 45, jail);

        Human andrey = new Human("Andrey", 18);
        andrey = officer.arrest(andrey);

        for(int i = 0; i < 8; i++) {
            jail.workADay();
        }
    }
}