package org.example.jails.cells;

import org.example.exceptions.NoAvailableSlotsException;
import org.example.items.prison.*;
import org.example.items.Item;
import org.example.persons.MentalState;
import org.example.persons.PhysicalState;
import org.example.persons.prisoners.Prisoner;
import java.util.Random;

public class SingleCell extends Cell{
    public SingleCell() {
        super(1);
    }

    public SingleCell(Prisoner prisoner) {
        super(1, new Prisoner[]{ prisoner });
    }

    @Override
    public void workOneDay() {
        var prisoners = getPrisoners();
        if(prisoners.length == 0) {
            return;
        }

        Prisoner prisoner = prisoners[0];
        if(prisoner.getPhysicalState() == PhysicalState.Dead) {
            return;
        }

        Random rand = new Random();

        Item item = switch (rand.nextInt(4)) {
            case 0:
                yield new SharpOfGlass();
            case 1:
                yield new Soap();
            case 2:
                yield new Sock();
            default:
                yield null;
        };

        if(item == null) {
            System.out.printf("Prisoner %s didn't find anything today\n", prisoner.getName());
        }
        else {
            try {
                System.out.printf("Prisoner %s found %s\n", prisoner.getName(), item.getName());
                prisoner.putItemIntoInventory(item);
            } catch (NoAvailableSlotsException e) {
                System.out.printf("Prisoner %s couldn't put %s into his inventory because it's full\n", prisoner.getName(), item.getName());
            }
        }

        prisoner.liveADay(this);
    }
}
