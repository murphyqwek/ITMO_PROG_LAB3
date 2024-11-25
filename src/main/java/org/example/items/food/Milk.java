package org.example.items.food;

public class Milk extends Food {
    public Milk() {
        super("Milk", 2, 10);
    }

    public Milk(int count) {
        super("Milk", 2, count, 10);
    }
}
