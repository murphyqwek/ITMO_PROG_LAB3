package org.example.jails.distribution.officer;

import org.example.exceptions.NoAvailableSlotsException;
import org.example.persons.policemen.PrisonOfficer;
import org.example.service.ArrayService;

public class OfficerDistribtuionSystem {
    private final PrisonOfficer[] officers;

    public OfficerDistribtuionSystem(int prisonOfficerCount) {
        if(prisonOfficerCount <= 0) {
            throw new IllegalArgumentException("Prison officer count must be greater than 0");
        }

        officers = new PrisonOfficer[prisonOfficerCount];
    }

    public OfficerDistribtuionSystem(PrisonOfficer[] officers) {
        if(officers == null || officers.length == 0) {
            throw new IllegalArgumentException("Officer array must not be null or empty");
        }

        this.officers = officers;
    }

    protected int getFirstEmptySlot() {
        return ArrayService.getFirstEmptySlot(this.officers);
    }

    public void addPrisonOfficer(PrisonOfficer officer) {
        if(officer == null) {
            throw new IllegalArgumentException("Officer must not be null");
        }

        if(containsOfficer(officer)){
            return;
        }

        int index = getFirstEmptySlot();

        if(index == -1){
            throw new NoAvailableSlotsException("Officers' slots are full");
        }

        this.officers[index] = officer;
    }

    public boolean containsOfficer(PrisonOfficer officer) {
        return ArrayService.isContains(this.officers, officer);
    }

    public PrisonOfficer[] getOfficers() {
        Object[] nNullObject = ArrayService.getArrayOfNotNullObjects(this.officers);
        var officers = new PrisonOfficer[nNullObject.length];
        for(int i = 0; i < officers.length; i++) {
            officers[i] = (PrisonOfficer) nNullObject[i];
        }
        return officers;
    }

    public PrisonOfficer[] getOfficersList()
    {
        return officers;
    }
}
