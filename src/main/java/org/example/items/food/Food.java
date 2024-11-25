package org.example.items.food;

import org.example.items.Eatable;
import org.example.items.Item;
import org.example.persons.Human;

public abstract class Food extends Item implements Eatable {
    protected int hunger;

    public Food(String name, int maxCount, int hunger) {
        super(name, maxCount);

        this.hunger = hunger;
    }

    public Food(String name, int maxCount, int count, int hunger) {
        super(name, maxCount, count);

        this.hunger = hunger;
    }

    public int getHunger() {
        return this.hunger;
    }

    @Override
    public void beEaten(Human human) {
        human.setHunger(human.getHunger() + hunger);
        System.out.printf("%s ate %s. Hunger: %d, HP: %d\n", human.getName(), this.getName(), human.getHunger(), human.getHP());
    }

    @Override
    public String toString() {
        return super.toString() + " Hunger: " + String.valueOf(hunger);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null)
            return false;

        if(obj instanceof Food food)
            return this.getName().equals(food.getName()) &&
                    this.getMaxCount() == food.getMaxCount() &&
                    this.hunger == food.hunger;

        return false;
    }

    @Override
    public int hashCode() {
        String stringToHash = String.format("%s %d %d %d", this.getName(), this.getCount(), this.getMaxCount(), this.getHunger());
        return stringToHash.hashCode();
    }
}
