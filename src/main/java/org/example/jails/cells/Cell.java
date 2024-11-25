package org.example.jails.cells;

import org.example.exceptions.NoAvailableSlotsException;
import org.example.persons.prisoners.Prisoner;
import org.example.service.ArrayService;

import static org.example.service.ArrayService.*;

public abstract class Cell {
    private final int maxPrisonersCount;
    private Prisoner[] prisoners;

    public Cell(int maxPrisoners) {
        if (maxPrisoners <= 0) {
            throw new IllegalArgumentException("maxPrisoners must be greater than 0");
        }

        this.maxPrisonersCount = maxPrisoners;
        this.prisoners = new Prisoner[maxPrisonersCount];
    }

    public Cell(int maxPrisoners, Prisoner[] prisoners) {
        this(maxPrisoners);

        if(prisoners == null) {
            throw new IllegalArgumentException("Prisoners must not be null");
        }

        if(prisoners.length != maxPrisonersCount) {
            throw new IllegalArgumentException("Prisoners must have " + maxPrisonersCount + " prisoners");
        }

        this.prisoners = prisoners;
    }

    public Prisoner[] getPrisoners() {
        Object[] nNullObjects = ArrayService.getArrayOfNotNullObjects(this.prisoners);
        var prisoners = new Prisoner[nNullObjects.length];
        for(int i = 0; i < nNullObjects.length; i++) {
            prisoners[i] = (Prisoner) nNullObjects[i];
        }
        return prisoners;
    }

    public Prisoner[] getPrisonersList() {
        return prisoners;
    }

    public int getCurrentPrisonersCount() {
        return countNotNullObjects(this.prisoners);
    }

    public void addNewPrisoner(Prisoner prisoner) {
        if(prisoner == null) {
            throw new IllegalArgumentException("Prisoner must not be null");
        }

        int indexOfEmptySlot = getFirstEmptySlot(prisoners);

        if(indexOfEmptySlot == -1) {
            throw new NoAvailableSlotsException("Cell is full");
        }

        prisoners[indexOfEmptySlot] = prisoner;
    }

    public void removePrisoner(Prisoner prisoner) {
        removeObejct(this.prisoners, prisoner);
    }

    public boolean containsPrisoner(Prisoner prisoner) {
        if(prisoner == null) {
            throw new NullPointerException("prisoner is null");
        }

        for(int i = 0; i < prisoners.length; i++) {
            if(prisoners[i].equals(prisoner)) {
                return true;
            }
        }

        return false;
    }

    public abstract void workOneDay();
}
