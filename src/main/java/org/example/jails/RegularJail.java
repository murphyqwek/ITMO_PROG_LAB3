package org.example.jails;

import org.example.exceptions.DeadPrisonerException;
import org.example.items.Item;
import org.example.items.food.Bread;
import org.example.items.food.DeadMouse;
import org.example.items.food.Food;
import org.example.items.food.Milk;
import org.example.jails.distribution.officer.OfficerDistribtuionSystem;
import org.example.jails.distribution.prisoner.RegularPrisonerDistributionSystem;
import org.example.persons.PhysicalState;
import org.example.persons.prisoners.Prisoner;

import java.util.Random;

public class RegularJail extends Jail {
    public RegularJail() {
        super(new RegularPrisonerDistributionSystem(), new OfficerDistribtuionSystem(3));
    }

    public RegularJail(Item[] restrictedItems) {
        super(new RegularPrisonerDistributionSystem(), new OfficerDistribtuionSystem(3), restrictedItems);
    }

    @Override
    public void workADay() {
        System.out.println("New day in prison!");
        updateAllCells();
        updateDeadPrisoners();
        if(getAlivePrisonersCount() == 0) {
            System.out.println("No alive prisoners :(");
            return;
        }
        System.out.println("Dinner!");
        giveDinner();
        updateDeadPrisoners();
        System.out.println("\nSHMON!!!!!");
        shmon();
        updateDeadPrisoners();
        System.out.println("\nChecking for releasing");
        checkForReleasing();
        System.out.println();
    }

    private void checkForReleasing() {
        for(var prisoner : getAlivePrisoners()) {
            checkForReleasing(prisoner);
        }
    }

    private void checkForReleasing(Prisoner prisoner) {
        if(prisoner.getPhysicalState() == PhysicalState.Dead) {
            throw new DeadPrisonerException("prisoner is dead");
        }

        var sentece = prisoner.getSentence();
        int daysInPrison = prisoner.getDaysInPrison();
        if(sentece.daysUntillFree() == daysInPrison) {
            System.out.printf("Prisoner %s's sentence was served\n", prisoner.getName());
            clearInventories(prisoner);
            releasePrisoner(prisoner);
        }
    }

    @Override
    protected void giveDinner() {
        var prisoners = getAlivePrisoners();

        Random rnd = new Random();
        for(var prisoner : prisoners) {
            giveDinner(prisoner);
            System.out.println();
        }
    }

    protected void giveDinner(Prisoner prisoner) {
        if (prisoner == null) {
            throw new NullPointerException("Prisoner is null");
        }
        if (prisoner.getPhysicalState() == PhysicalState.Dead) {
            throw new DeadPrisonerException("Prisoner is dead");
        }

        Random rnd = new Random();

        Food food = switch (rnd.nextInt(3)) {
            case 0 -> new Bread();
            case 1 -> new DeadMouse();
            case 2 -> new Milk();
            default -> new Bread();
        };

        System.out.printf("Prisoner %s gets %s on dinner\n", prisoner.getName(), food.getName());
        prisoner.eat(food);
    }

    @Override
    protected void shmon() {
        Random rnd = new Random();
        for(var prisoner : getAlivePrisoners()) {
            System.out.printf("\nPrisoner %s gets shmotting\n", prisoner.getName());
            if(checkPrisonerForRestrictedItems(prisoner) && rnd.nextInt(5) <= 3) {
                clearInventories(prisoner);
                System.out.printf("Found restricted item while shmotting %s. We took all he's items\n", prisoner.getName());
                punishForRestrictedItem(prisoner);
            }
            else {
                System.out.printf("Prisoner %s is clear\n", prisoner.getName());
            }
        }

        System.out.println();
    }

    private void clearInventories(Prisoner prisoner) {
        for(var inv : prisoner.getAllInventories()) {
            inv.clearAll();
        }
    }

    @Override
    protected void punishForRestrictedItem(Prisoner prisoner) {
        System.out.printf("Prisoner %s gets punished\n", prisoner.getName());
        prisoner.getDamage(10);
        if(prisoner.getPhysicalState() != PhysicalState.Dead) {
            prisoner.setPhysicalState(PhysicalState.Weak);
            System.out.println(prisoner.toString());
        }
        else {
            System.out.printf("The punish was too serious: Prisoner %s died during the punishment :(\n", prisoner.getName());
        }
    }
}
