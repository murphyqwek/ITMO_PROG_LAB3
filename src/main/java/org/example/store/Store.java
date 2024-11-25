package org.example.store;

import org.example.items.Item;

public interface Store {
    void storeItem(Item item);
    Item extractItem(Item item);
    Item extractItem(int index);

    int getItemCount(Item item);
    int getItemsCount();

    boolean containsItem(String Name);

    Item getItem(int index);
    Item[] getItems();

    int getStoreSize();

    void clearAll();
    void clearSlot(int index);
}
