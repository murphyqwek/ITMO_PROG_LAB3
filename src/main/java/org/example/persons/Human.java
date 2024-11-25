package org.example.persons;

import org.example.exceptions.NoAvailableSlotsException;
import org.example.items.*;
import org.example.store.*;

public class Human {
    private final int MAXHP = 100;
    private final int MAXHUNGER = 100;

    private String name;
    private int age;
    private int hp;
    private int hunger;
    private MentalState mentalState;
    private PhysicalState physicalState;
    private Inventory inventory;

    public Human(String name, int age) {
        setName(name);
        setAge(age);

        setHP(MAXHP);
        setHunger(MAXHUNGER);
        setMentalState(MentalState.Good);
        setPhysicalState(PhysicalState.Good);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHP(int hp) {
        if(hp < 0)
            throw new IllegalArgumentException("HP cannot be negative");

        if(hp > MAXHP)
            hp = MAXHP;

        this.hp = hp;

        checkState();
    }

    public int getHP() {
        return hp;
    }

    public void setHunger(int hunger) {
        if(hunger < 0)
            hunger = 0;

        if(hunger > MAXHUNGER)
            hunger = MAXHUNGER;

        this.hunger = hunger;

        checkState();
    }

    public int getHunger() {
        return hunger;
    }

    public void getDamage(int damage) {
        if(damage > this.hp)
            damage = this.hp;

        this.hp -= damage;

        checkState();
    }

    public void getDamage(int damage, MentalState mentalState) {
        this.mentalState = mentalState;

        getDamage(damage);
    }

    public void getDamage(int damage, PhysicalState physicalState) {
        this.physicalState = physicalState;

        getDamage(damage);
    }

    public void getDamage(int damage, MentalState mentalState, PhysicalState physicalState) {
        this.mentalState = mentalState;
        this.physicalState = physicalState;

        getDamage(damage);
    }

    public void setPhysicalState(PhysicalState physicalState) {
        if(physicalState == PhysicalState.Dead) {
            System.out.printf("%s is dead :(\n", this.name);
        }
        this.physicalState = physicalState;
    }

    public PhysicalState getPhysicalState() {
        return physicalState;
    }

    public void setMentalState(MentalState mentalState) {
        this.mentalState = mentalState;
    }

    public MentalState getMentalState() {
        return mentalState;
    }

    public void setAge(int age) {
        if(age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }

        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public void checkState() {
        if(this.physicalState == PhysicalState.Dead) {
            return;
        }

        if(this.hp <= 0) {
            this.physicalState = PhysicalState.Dead;
            System.out.printf("%s dead :(\n", this.name);
        }

        if(this.hunger == 0) {
            this.physicalState = PhysicalState.Weak;
            this.mentalState = MentalState.NearDeath;
        }

        if(this.hp > 50 && this.hunger > 50) {
            this.physicalState = PhysicalState.Good;
            this.mentalState = MentalState.Good;
        }
    }

    public void eat(Eatable eatable) {
        if(eatable == null) {
            throw new IllegalArgumentException("Eatable cannot be null");
        }
        eatable.beEaten(this);
    }

    public void putItemIntoInventory(Item item) {
        if(item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        this.inventory.storeItem(item);

        System.out.printf("%s puts %s to inventory\n", this.name, item.getName());
    }

    public Item giveItem(int index) {
        var item = inventory.extractItem(index);
        System.out.println(this.getName() + " gives " + item.getName());
        return item;
    }


    public void giveItem(Human human, int itemIndex) {
        var item = this.inventory.extractItem(itemIndex);

        if(item == null) {
            throw new IllegalArgumentException("item Index cannot point to null object in inventory");
        }

        try {
            human.getItem(item);
            System.out.printf("%s gives item %s to %s\n", this.getName(), item.getName(), human.getName());
        }
        catch(NoAvailableSlotsException e) {
            System.out.printf("%s couldn't give item %s to %s because of full inventory\n", human.getName(), item.getName(), this.getName());
            this.inventory.storeItem(item);
        }
    }

    public void getItem(Item item) {
        this.inventory.storeItem(item);
    }

    public Item[] getAllItems(){
        return inventory.getItems();
    }

    public Inventory getInventory(){
        return inventory;
    }

    public void setInventory(Inventory newInventory) {
        if(newInventory == null) {
            throw new IllegalArgumentException("Inventory cannot be null");
        }
        this.inventory = newInventory;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Age: %s, HP: %d, Hugner: %d", name, age, hp, hunger);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if(o == null || getClass() != o.getClass()) {
            return false;
        }

        Human human = (Human) o;
        return human.getName().equals(getName()) &&
                human.getAge() == getAge() &&
                human.getHP() == getHP() &&
                human.getHunger() == getHunger() &&
                human.getMentalState() == getMentalState() &&
                human.getPhysicalState() == getPhysicalState();
    }
}