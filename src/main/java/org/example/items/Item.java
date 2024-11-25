package org.example.items;

public abstract class Item {
    private final int maxCount;
    private int count;
    private final String name;

    public Item(String name, int maxCount) {
        if(maxCount <= 0)
            throw new IllegalArgumentException("maxCount must be greater than 0");

        this.maxCount = maxCount;
        this.name = name;
        this.count = 1;
    }

    public Item(String name, int maxCount, int count) {
        this(name, maxCount);

        setCount(count);
    }

    public void setCount(int count) {
        if(count > this.maxCount)
            throw new IllegalArgumentException("Count is greater than Max Count");
        if(count < 0)
            throw new IllegalArgumentException("Count is less than 0");

        this.count = count;
    }

    public void addCount(int count) {
        setCount(count + this.getCount());
    }

    public int getCount() {
        return this.count;
    }

    public String getName() {
        return this.name;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Count: %d", name, count);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null)
            return false;

        if(o instanceof Item other) {
            return this.name.equals(other.name) && this.maxCount == other.maxCount;
        }

        if(o instanceof String str) {
            return this.name.equals(str);
        }

        return false;
    }

    @Override
    public int hashCode() {
        String stringToHash = String.format("%s %d %d", this.name, this.count, this.maxCount);
        return stringToHash.hashCode();
    }
}
