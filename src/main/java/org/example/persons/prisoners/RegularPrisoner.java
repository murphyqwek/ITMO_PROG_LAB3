package org.example.persons.prisoners;

import org.example.exceptions.NoAvailableSlotsException;
import org.example.jails.cells.Cell;
import org.example.jails.cells.SingleCell;
import org.example.persons.Human;
import org.example.persons.MentalState;
import org.example.persons.PhysicalState;
import org.example.exceptions.DeadPrisonerException;

import java.util.Random;

public class RegularPrisoner extends Prisoner {
    public RegularPrisoner(int id, Human human, Sentence sentence) {
        super(id, human, sentence);
    }

    public RegularPrisoner(int id, String name, int age, Sentence sentence) {
        super(id, name, age, sentence);
    }

    @Override
    public void liveADay(Cell cell) {
        if(getPhysicalState() == PhysicalState.Dead) {
            throw new DeadPrisonerException("prisoner is dead");
        }

        if(cell instanceof SingleCell) {
            setMentalState(MentalState.Depressed);
        }

        increaseDaysInPrison();
        setHunger(getHunger() - 10);

        var item = getInventory().getItems()[0];

        if(item != null) {
            Random rand = new Random();
            int choice = rand.nextInt(10);
            if(choice >= 7) {
                try {
                    getInventory().extractItem(0);
                    this.prisonChest.storeItem(item);
                    System.out.printf("Prisoner %s puts %s in prison chest\n", getName(), item.getName());
                }
                catch(NoAvailableSlotsException e) {
                    System.out.printf("Prisoner %s couldn't put %s in prison chest because it's full\n", getName(), item.getName());
                    getInventory().storeItem(item);
                }
            }
            else {
                try {
                    getInventory().extractItem(0);
                    this.prisonChest.storeItemHidden(item);
                    System.out.printf("Prisoner %s puts %s in hidden prison chest\n", getName(), item.getName());
                }
                catch(NoAvailableSlotsException e) {
                    System.out.printf("Prisoner %s couldn't put %s in hidden prison chest because it's full\n", getName(), item.getName());
                    getInventory().storeItem(item);
                }
            }

        }

        System.out.printf("Prisoner %s' inventory: %s\nPrison Chest: %s\n", getName(), getInventory(), this.prisonChest);
    }

    @Override
    public void release() {
        if(super.getPhysicalState() == PhysicalState.Dead)
            throw new DeadPrisonerException(super.getName() + "is dead");

        super.setMentalState(MentalState.Good);
    }
}
