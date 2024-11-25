package org.example.jails.distribution.prisoner;

import org.example.exceptions.NoAvailableSlotsException;
import org.example.exceptions.NoPrisonerInThisJailException;
import org.example.jails.cells.Cell;
import org.example.jails.cells.SingleCell;
import org.example.persons.prisoners.Prisoner;

public class RegularPrisonerDistributionSystem extends PrisonerDistributionSystem {
    public RegularPrisonerDistributionSystem() {
        super(10, 10);
    }

    @Override
    public void addNewPrisoner(Prisoner prisoner) {
        int index = getFirstEmptyCellSlot();

        if(index == -1) {
            throw new NoAvailableSlotsException("No empty cells");
        }

        SingleCell cell = new SingleCell(prisoner);
        addNewPrisonerToPrisonersList(prisoner);
        addNewCell(index, cell);

        System.out.printf("New Prisoner %s was imprison to a new single cell\n", prisoner.getName());
    }

    @Override
    public void releasePrisoner(Prisoner prisoner) {
        if(!containsPrisoner(prisoner)) {
            throw new NoPrisonerInThisJailException("prisoner is not in the jail");
        }

        int prisonerIndex = getPrisonerIndex(prisoner);
        int cellIndex = getPrisonerCellIndex(prisoner);
        Cell cell = getCells()[cellIndex];

        releasePrisoner(prisonerIndex, cellIndex);

        System.out.printf("Prisoner %s was released\n", prisoner.getName());
    }

    @Override
    protected void releasePrisoner(int prisonerIndex, int cellIndex) {
        var prisoner = getPrisoners()[prisonerIndex];

        removePrisoner(prisoner);
        prisoner.release();
    }

    @Override
    public void releasePrisoner(Prisoner prisoner, String officerName) {
        if(!containsPrisoner(prisoner)) {
            throw new NoPrisonerInThisJailException("prisoner is not in the jail");
        }

        int prisonerIndex = getPrisonerIndex(prisoner);
        int cellIndex = getPrisonerCellIndex(prisoner);
        Cell cell = getCells()[cellIndex];

        releasePrisoner(prisonerIndex, cellIndex);

        System.out.printf("Prisoner %s was released by officer %s\n", prisoner.getName(), officerName);
    }
}
