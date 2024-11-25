package org.example.items.prison;

import org.example.items.Item;

public class Sock extends Item {
    public Sock() {
        super("Sock", 4);
    }

    public Sock(int count) {
        super("Sock", 4, count);
    }
}
