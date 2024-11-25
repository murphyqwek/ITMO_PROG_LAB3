package org.example.jails.distribution.prisoner;

import org.example.exceptions.CellSlotIsTakenException;
import org.example.exceptions.NoAvailableSlotsException;
import org.example.exceptions.NoPrisonerInThisJailException;
import org.example.jails.cells.Cell;
import org.example.persons.PhysicalState;
import org.example.persons.prisoners.Prisoner;
import org.example.service.ArrayService;

import java.util.ConcurrentModificationException;

public abstract class PrisonerDistributionSystem {
    private final Prisoner[] prisoners;
    private final Prisoner[] deadPrisoners;
    private final Cell[] cells;

    public PrisonerDistributionSystem(int prisonerCount, int cellCount) {
        if (prisonerCount <= 0) {
            throw new IllegalArgumentException("Prisoner count must be greater than 0");
        }
        if (cellCount <= 0) {
            throw new IllegalArgumentException("Cell count must be greater than 0");
        }

        prisoners = new Prisoner[prisonerCount];
        deadPrisoners = new Prisoner[prisonerCount];
        cells = new Cell[cellCount];
    }

    public PrisonerDistributionSystem(Cell[] cells) {
        if (cells == null || cells.length == 0) {
            throw new IllegalArgumentException("Cells cannot be null or empty");
        }

        this.cells = cells;
        this.prisoners = new Prisoner[cells.length];
        this.deadPrisoners = new Prisoner[cells.length];

        int i = 0;
        int j = 0;
        for (Cell cell : cells) {
            for (Prisoner prisoner : cell.getPrisoners()) {
                prisoners[j] = prisoner;
                j++;
                if (prisoner.getPhysicalState() == PhysicalState.Dead) {
                    deadPrisoners[i] = prisoner;
                    i++;
                }
            }
        }
    }

    public Prisoner[] getPrisoners() {
        Object[] nNullObjects = ArrayService.getArrayOfNotNullObjects(this.prisoners);
        Prisoner[] prisoners = new Prisoner[nNullObjects.length];
        for (int i = 0; i < prisoners.length; i++) {
            prisoners[i] = (Prisoner) nNullObjects[i];
        }
        return prisoners;
    }

    public Prisoner[] getPrisonersList() {
        return prisoners;
    }

    public void removePrisoner(Prisoner prisoner) {
        int prisonerIndex = getPrisonerIndex(prisoner);

        if (prisonerIndex == -1) {
            throw new NoPrisonerInThisJailException("prisoner is not in jail");
        }

        removeDeadPrisoner(prisoner);

        int cellIndex = getPrisonerCellIndex(prisoner);
        var cell = getCell(cellIndex);
        cell.removePrisoner(prisoner);

        if (cell.getCurrentPrisonersCount() == 0) {
            emptyCell(cellIndex);
        }

        this.prisoners[prisonerIndex] = null;
    }

    public boolean containsPrisoner(Prisoner prisoner) {
        return ArrayService.isContains(getPrisoners(), prisoner);
    }

    protected void addNewPrisonerToPrisonersList(Prisoner prisoner) {
        if (prisoner == null) {
            throw new NullPointerException("prisoner is null");
        }

        int index = ArrayService.getFirstEmptySlot(prisoners);

        if (index == -1) {
            throw new NoAvailableSlotsException("Full jail");
        }

        prisoners[index] = prisoner;
    }

    protected int getPrisonerIndex(Prisoner prisoner) {
        for (int i = 0; i < prisoners.length; i++) {
            if (prisoners[i].equals(prisoner)) {
                return i;
            }
        }

        return -1;
    }

    public int getPrisonersCount() {
        return getPrisoners().length;
    }

    public Prisoner[] getAlivePrisoners() {
        int alivePrisonersCount = getPrisonersCount() - getDeadPrisonersCount();

        Prisoner[] alivePrisoners = new Prisoner[alivePrisonersCount];

        int i = 0;
        for (var prisoner : getPrisoners()) {
            if (prisoner.getPhysicalState() != PhysicalState.Dead) {
                alivePrisoners[i] = prisoner;
            }
        }

        return alivePrisoners;
    }

    public int getAlivePrisonersCount() {
        return getAlivePrisoners().length;
    }

    public Prisoner[] getDeadPrisoners() {
        Object[] nNullObjects = ArrayService.getArrayOfNotNullObjects(this.deadPrisoners);
        Prisoner[] deads = new Prisoner[nNullObjects.length];
        for (int i = 0; i < deads.length; i++) {
            deads[i] = (Prisoner) nNullObjects[i];
        }
        return deads;
    }

    public int getDeadPrisonersCount() {
        return getDeadPrisoners().length;
    }

    public void addDeadPrisoner(Prisoner prisoner) {
        if (prisoner == null) {
            throw new NullPointerException("Prisoner cannot be null");
        }

        if (prisoner.getPhysicalState() != PhysicalState.Dead) {
            throw new IllegalArgumentException("Prisoner must be dead");
        }

        int index = ArrayService.getFirstEmptySlot(this.deadPrisoners);

        if (index == -1) {
            throw new NoAvailableSlotsException("DeadPrisoners are full");
        }

        this.deadPrisoners[index] = prisoner;
    }

    public void updateDeadPrisoners() {
        var prisoners = getPrisoners();
        for (var prisoner : prisoners) {
            if (prisoner.getPhysicalState() == PhysicalState.Dead &&
                    !ArrayService.isContains(this.deadPrisoners, prisoner)) {

                try {
                    addDeadPrisoner(prisoner);
                } catch (NoAvailableSlotsException e) {
                    System.out.printf("No place for %s's dead body. We throw him away\n", prisoner.getName());
                    removePrisoner(prisoner);
                }
            }
        }
    }

    protected void removeDeadPrisoner(Prisoner prisoner) {
        for (int i = 0; i < deadPrisoners.length; i++) {
            if (deadPrisoners[i] != null && deadPrisoners[i].equals(prisoner)) {
                deadPrisoners[i] = null;
                return;
            }
        }
    }

    public Cell[] getCells() {
        Object[] nNullObjects = ArrayService.getArrayOfNotNullObjects(cells);
        Cell[] cells = new Cell[nNullObjects.length];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = (Cell) nNullObjects[i];
        }
        return cells;
    }

    protected int getFirstEmptyCellSlot() {
        return ArrayService.getFirstEmptySlot(cells);
    }

    public void addNewCell(int index, Cell cell) {
        if (index < 0 || index >= this.cells.length) {
            throw new IllegalArgumentException("index out of bounds");
        }

        if (cell == null) {
            throw new NullPointerException("cell pointer is null");
        }

        if (cells[index] != null) {
            throw new CellSlotIsTakenException("cell already exists");
        }

        this.cells[index] = cell;
    }

    public Cell getCell(int index) {
        if (index < 0 || index >= cells.length) {
            throw new IllegalArgumentException("index out of bounds");
        }

        return cells[index];
    }

    public void emptyCell(int index) {
        if (index < 0 || index >= this.cells.length) {
            throw new IllegalArgumentException("index out of bounds");
        }

        this.cells[index] = null;
    }

    protected int getPrisonerCellIndex(Prisoner prisoner) {
        if (prisoner == null) {
            throw new NullPointerException("prisoner is null");
        }

        if (!containsPrisoner(prisoner)) {
            throw new NoPrisonerInThisJailException("prisoner is not in the jail");
        }

        for (int i = 0; i < this.cells.length; i++) {
            if (this.cells[i] != null && this.cells[i].containsPrisoner(prisoner)) {
                return i;
            }
        }

        throw new ConcurrentModificationException("prisoner is not in any cells, but he is in the prisoner list");
    }

    public abstract void addNewPrisoner(Prisoner prisoner);

    public abstract void releasePrisoner(Prisoner prisoner);

    protected abstract void releasePrisoner(int prisonerIndex, int cellIndex);

    public abstract void releasePrisoner(Prisoner prisoner, String officerName);
}
