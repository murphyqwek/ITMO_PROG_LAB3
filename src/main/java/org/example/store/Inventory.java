package org.example.store;

import org.example.exceptions.NoAvailableSlotsException;
import org.example.items.Item;
import org.example.service.ArrayService;

import static org.example.service.ArrayService.*;

public abstract class Inventory implements Store {
    private final int size;
    private Item[] items;

    public Inventory(int size) {
        if(size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }

        this.size = size;
        this.items = new Item[size];
    }

    public Inventory(int size, Item[] items) {
        this(size);

        if(items == null) {
            throw new IllegalArgumentException("Items cannot be null");
        }

        if(items.length > size) {
            throw new IllegalArgumentException("Item[] size must be less than " + size);
        }

        this.items = items;
    }

    protected int getFirstEmptySlot() {
        return ArrayService.getFirstEmptySlot(items);
    }

    protected int getIndexOfUnfullItem(Item item) {
        for(int i = 0; i < size; i++) {
            if(items[i] != null && items[i].equals(item)) {
                if(items[i].getCount() + item.getCount() <= item.getMaxCount()) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void storeItem(Item item) {
        int index = getIndexOfUnfullItem(item);

        if(index != -1) {
            items[index].addCount(item.getCount());
            return;
        }

        index = getFirstEmptySlot();

        if(index == -1){
            throw new NoAvailableSlotsException("Inventory is full");
        }

        items[index] = item;
    }

    @Override
    public Item extractItem(Item item) {
        for(int i = 0; i < size; i++) {
            if(items[i] != null && items[i].equals(item)) {
                return extractItem(i);
            }
        }

        return null;
    }

    @Override
    public Item extractItem(int index) {
        var item = getItem(index);
        items[index] = null;

        return item;
    }

    @Override
    public int getItemCount(Item item) {
        int result = 0;

        for(int i = 0; i < size; i++) {
            if(items[i] != null && items[i].equals(item)) {
                result += items[i].getCount();
            }
        }

        return result;
    }

    @Override
    public int getItemsCount() {
        int result = 0;

        for(int i = 0; i < size; i++) {
            if(items[i] != null) {
                result += items[i].getCount();
            }
        }

        return result;
    }

    @Override
    public boolean containsItem(String Name) {
        for(int i = 0; i < size; i++) {
            if(items[i] != null && items[i].getName().equals(Name)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Item getItem(int index) {
        if(index < 0) {
            throw new IllegalArgumentException("Index must be greater than 0");
        }
        if(index >= size) {
            throw new IllegalArgumentException("Index must be less than size of the inventory");
        }

        return items[index];
    }

    @Override
    public Item[] getItems() {
        return items;
    }

    @Override
    public int getStoreSize() {
        return size;
    }

    @Override
    public void clearAll() {
        clearAllArray(items);
    }

    @Override
    public void clearSlot(int index) {
        clearArray(items, index);
    }

    @Override
    public String toString() {
        String output = "Items: ";
        for(Item item: items) {
            if(item != null) {
                output += item.toString() + "; ";
            }
        }

        return output;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }

        if(o == null || getClass() != o.getClass()) {
            return false;
        }

        Inventory that = (Inventory) o;

        if(size != that.size) {
            return false;
        }

        var thatItems = that.items;
        for(int i = 0; i < size; i++) {
            if(items[i] == null && thatItems[i] != null ||
                    items[i] != null && thatItems[i] == null) {
                return false;
            }

            if(items[i] == null) {
                continue;
            }

            if(!items[i].equals(thatItems[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;

        for(int i = 0; i < size; i++) {
            if(items[i] != null) {
                result += items[i].hashCode();
            }
        }

        return result;
    }
}
