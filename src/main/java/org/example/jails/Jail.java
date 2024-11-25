package org.example.jails;

import org.example.exceptions.NoOfficerInJailException;
import org.example.items.Item;
import org.example.jails.cells.Cell;
import org.example.jails.distribution.officer.OfficerDistribtuionSystem;
import org.example.jails.distribution.prisoner.PrisonerDistributionSystem;
import org.example.persons.policemen.PrisonOfficer;
import org.example.persons.prisoners.Prisoner;
import org.example.service.ArrayService;
import org.example.store.Store;

public abstract class Jail {
    private final PrisonerDistributionSystem prisonerDistributionSystem;
    private final OfficerDistribtuionSystem officerDistribtuionSystem;
    private Item[] restrictedItems;

    public Jail(PrisonerDistributionSystem prisonerDistributionSystem, OfficerDistribtuionSystem officerDistribtuionSystem) {
        if (prisonerDistributionSystem == null) {
            throw new IllegalArgumentException("prisonerDistributionSystem must not be null");
        }

        if (officerDistribtuionSystem == null) {
            throw new IllegalArgumentException("officerDistribtuionSystem must not be null");
        }

        this.prisonerDistributionSystem = prisonerDistributionSystem;
        this.officerDistribtuionSystem = officerDistribtuionSystem;
        this.restrictedItems = new Item[0];
    }

    public Jail(PrisonerDistributionSystem distributionSystem, OfficerDistribtuionSystem officerDistribtuionSystem, Item[] restrictedItems) {
        this(distributionSystem, officerDistribtuionSystem);

        setRestrictedItems(restrictedItems);
    }

    public Prisoner[] getPrisoners() {
        return prisonerDistributionSystem.getPrisoners();
    }


    public Prisoner[] getDeadPrisoners() {
        return prisonerDistributionSystem.getDeadPrisoners();
    }

    public Prisoner[] getAlivePrisoners() {
        return prisonerDistributionSystem.getAlivePrisoners();
    }

    public int getAlivePrisonersCount() {
        return prisonerDistributionSystem.getAlivePrisonersCount();
    }

    public Cell[] getCells() {
        return prisonerDistributionSystem.getCells();
    }

    protected void addNewPrisoner(Prisoner prisoner) {
        prisonerDistributionSystem.addNewPrisoner(prisoner);
    }

    public void addNewPrisoner(Prisoner prisoner, PrisonOfficer officer) {
        if (!containsOfficer(officer)) {
            throw new NoOfficerInJailException("this officer doesn't work in the jail");
        }

        System.out.printf("Officer %s arrest %s for %d days\n", officer.getName(), prisoner.getName(), prisoner.getSentence().daysUntillFree());
        addNewPrisoner(prisoner);
    }

    public void addNewOfficer(PrisonOfficer officer) {
        officerDistribtuionSystem.addPrisonOfficer(officer);
    }

    public boolean containsOfficer(PrisonOfficer officer) {
        return officerDistribtuionSystem.containsOfficer(officer);
    }

    public boolean containsPrisoner(Prisoner prisoner) {
        return prisonerDistributionSystem.containsPrisoner(prisoner);
    }

    public void releasePrisoner(Prisoner prisoner, PrisonOfficer officer) {
        if (!officerDistribtuionSystem.containsOfficer(officer)) {
            throw new IllegalArgumentException("Officer " + officer.getName() + " does not work in this jail");
        }

        prisonerDistributionSystem.releasePrisoner(prisoner, officer.getName());
    }

    protected void releasePrisoner(Prisoner prisoner) {
        prisonerDistributionSystem.releasePrisoner(prisoner);
    }

    public Item[] getRestrictedItems() {
        return restrictedItems;
    }

    public void setRestrictedItems(Item[] restrictedItems) {
        if (restrictedItems == null) {
            throw new NullPointerException("restrictedItems");
        }

        this.restrictedItems = restrictedItems;
    }

    protected boolean checkPrisonerForRestrictedItems(Prisoner prisoner) {
        var stores = prisoner.getAllInventories();

        for (var store : stores) {
            if (checkStoreForRestrictedItems(store)) {
                return true;
            }
        }

        return false;
    }

    protected boolean checkStoreForRestrictedItems(Store store) {
        for (var item : store.getItems()) {
            if (item != null && checkIsItemRestricted(item)) {
                return true;
            }
        }

        return false;
    }

    protected boolean checkIsItemRestricted(Item item) {
        return ArrayService.isContains(this.restrictedItems, item);
    }

    protected void updateDeadPrisoners() {
        this.prisonerDistributionSystem.updateDeadPrisoners();
    }

    protected void updateAllCells() {
        var cells = getCells();

        for (var cell : cells) {
            System.out.println();
            cell.workOneDay();
            System.out.println();
        }
    }

    public abstract void workADay();

    protected abstract void giveDinner();

    protected abstract void shmon();

    protected abstract void punishForRestrictedItem(Prisoner prisoner);
}
