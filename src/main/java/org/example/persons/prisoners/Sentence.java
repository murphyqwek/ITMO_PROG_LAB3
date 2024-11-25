package org.example.persons.prisoners;

public record Sentence(int daysUntillFree) {

    public Sentence(int daysUntillFree) {
        if(daysUntillFree <= 0) {
            daysUntillFree = -1;
        }

        this.daysUntillFree = daysUntillFree;
    }


    @Override
    public String toString() {
        if(daysUntillFree <= 0)
            return "Live sentence";

        return daysUntillFree + " days sentence";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }

        if(o instanceof Sentence sentence1) {
            return  daysUntillFree == sentence1.daysUntillFree;
        }

        if(o instanceof Integer sentence1) {
            return sentence1.equals(daysUntillFree);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return daysUntillFree;
    }
}
