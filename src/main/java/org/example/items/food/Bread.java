package org.example.items.food;

public class Bread extends Food {
    public Bread() {
        super("Bread", 3, 15);
    }

    public Bread(int count) {
        super("Bread", 3, count, 15);
    }
}
