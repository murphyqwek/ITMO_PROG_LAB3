package org.example.service;

public class ArrayService {

    public static int getFirstEmptySlot(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public static void removeObejct(Object[] array, Object toRemove) {
        if(array == null) {
            throw new IllegalArgumentException("Array must not be null");
        }

        for(int i = 0; i < array.length; i++) {
            if(array[i].equals(toRemove)) {
                array[i] = null;
                return;
            }
        }
    }

    public static int countNotNullObjects(Object[] array) {
        int result = 0;

        for (Object o : array) {
            if (o != null) {
                result++;
            }
        }

        return result;
    }

    public static void clearArray(Object[] array, int index) {
        if(index < 0){
            throw new IllegalArgumentException("Index must be greater than zero");
        }

        if(index >= array.length) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        array[index] = null;
    }

    public static void clearAllArray(Object[] array) {
        for(int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }

    public static boolean isContains(Object[] array, Object toFind) {
        for(Object o : array){
            if(o != null && o.equals(toFind)) {
                return true;
            }
        }
        return false;
    }

    public static Object[] getArrayOfNotNullObjects(Object[] array) {
        int countNotNullObject = countNotNullObjects(array);

        Object[] result = new Object[countNotNullObject];

        int j = 0;
        for(int i = 0; i < countNotNullObject; i++) {
            if(array[i] != null) {
                result[j] = array[i];
                j++;
            }
        }

        return result;
    }
}
