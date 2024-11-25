package org.example.items.food;

import org.example.persons.Human;

public class DeadMouse extends Food {

    public DeadMouse() {
        super("Dead Mouse", 2, -10);
    }

    public DeadMouse(int count) {
        super("Dead Mouse", 2, count, -10);
    }

    @Override
    public void beEaten(Human human) {
        int DAMAGE = 15;
        System.out.println("Dead Mouse was not very good: -" + DAMAGE + " hp to " + human.getName());
        human.getDamage(DAMAGE);
        super.beEaten(human);
    }
}
