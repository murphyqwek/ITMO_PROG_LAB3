package org.example.store;

import org.example.items.Item;

public class RegularInventory extends Inventory {
    public RegularInventory(int size) {
        super(size);
    }

    public RegularInventory(int size, Item[] items) {
        super(size, items);
    }
}
