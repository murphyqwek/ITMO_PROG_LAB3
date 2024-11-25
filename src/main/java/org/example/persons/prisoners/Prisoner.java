package org.example.persons.prisoners;

import org.example.jails.cells.Cell;
import org.example.persons.Human;
import org.example.store.PrisonChest;
import org.example.store.RegularInventory;
import org.example.store.Store;

public abstract class Prisoner extends Human {
    private final int id;
    private final Sentence sentence;
    private int daysInPrison;
    protected PrisonChest prisonChest;

    public Prisoner(int id, Human human, Sentence sentence) {
        super(human.getName(), human.getAge());

        if(sentence == null) {
            throw new NullPointerException("sentence is null");
        }
        if(id < 0) {
            throw new IllegalArgumentException("id is negative");
        }

        this.id = id;
        this.sentence = sentence;
        this.setInventory(new RegularInventory(2));
        this.prisonChest = new PrisonChest();
    }

    public Prisoner(int id, String name, int age, Sentence sentence) {
        this(id, new Human(name, age), sentence);
    }

    public abstract void liveADay(Cell cell);

    public Sentence getSentence() {
        return this.sentence;
    }

    public int getDaysInPrison() {
        return this.daysInPrison;
    }

    public void increaseDaysInPrison() {
        setDaysInPrison(this.daysInPrison + 1);
    }

    public void setDaysInPrison(int daysInPrison) {
        if(daysInPrison < 0) {
            throw new IllegalArgumentException("daysInPrison is negative");
        }

        this.daysInPrison = daysInPrison;
    }

    public abstract void release();

    public Store[] getAllInventories() {
        return new Store[] { super.getInventory(), this.prisonChest };
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        Prisoner p = (Prisoner)o;
        return super.equals(o) && p.daysInPrison == daysInPrison && p.sentence.equals(sentence);
    }

    @Override
    public String toString() {
        return "Prisoner " + super.toString() + " sentence " + sentence.toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
