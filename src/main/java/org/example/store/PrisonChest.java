package org.example.store;

import org.example.items.Item;

public class PrisonChest implements Store {
    private final Inventory openInventory;
    private final Inventory hiddenInventory;

    public PrisonChest() {
        openInventory = new RegularInventory(10);
        hiddenInventory = new RegularInventory(2);
    }

    public PrisonChest(Inventory openInventory, Inventory closedInventory) {
        this.openInventory = openInventory;
        this.hiddenInventory = closedInventory;
    }

    @Override
    public void storeItem(Item item) {
        openInventory.storeItem(item);
    }

    @Override
    public Item extractItem(Item item) {
        return openInventory.extractItem(item);
    }

    @Override
    public Item extractItem(int index) {
        return openInventory.extractItem(index);
    }

    @Override
    public int getItemCount(Item item) {
        return openInventory.getItemCount(item);
    }

    @Override
    public int getItemsCount() {
        return openInventory.getItemsCount();
    }

    @Override
    public boolean containsItem(String Name) {
        return openInventory.containsItem(Name);
    }

    @Override
    public Item getItem(int index) {
        return openInventory.getItem(index);
    }

    @Override
    public Item[] getItems() {
        return openInventory.getItems();
    }

    @Override
    public int getStoreSize() {
        return openInventory.getStoreSize();
    }

    @Override
    public void clearAll() {
        openInventory.clearAll();
    }

    @Override
    public void clearSlot(int index) {
        openInventory.clearSlot(index);
    }

    public void storeItemHidden(Item item) {
        hiddenInventory.storeItem(item);
    }

    public Item extractItemHidden(Item item) {
        return hiddenInventory.extractItem(item);
    }

    public Item extractItemHidden(int index) {
        return hiddenInventory.extractItem(index);
    }

    public int getItemCountHidden(Item item) {
        return hiddenInventory.getItemCount(item);
    }

    public int getItemsCountHidden() {
        return hiddenInventory.getItemsCount();
    }

    public boolean containsItemHidden(String Name) {
        return hiddenInventory.containsItem(Name);
    }

    public Item getItemHidden(int index) {
        return hiddenInventory.getItem(index);
    }

    public Item[] getItemsHidden() {
        return hiddenInventory.getItems();
    }

    public int getStoreSizeHidden() {
        return hiddenInventory.getStoreSize();
    }

    public void clearHiddenAll() {
        hiddenInventory.clearAll();
    }

    public void clearHiddenSlot(int index) {
        hiddenInventory.clearSlot(index);
    }

    @Override
    public String toString(){
        return "Open: " + openInventory.toString() + "; Hidden " + hiddenInventory.toString();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) {
            return true;
        }

        if(!(o instanceof PrisonChest other)) {
            return false;
        }

        return openInventory.equals(other.openInventory) && hiddenInventory.equals(other.hiddenInventory);
    }

    @Override
    public int hashCode() {
        return openInventory.hashCode() + hiddenInventory.hashCode();
    }
}
