import java.util.Objects;

public class ArrayBag<T> {

    private static final int DEFAULT_CAPACITY = 25;

    private T[] bag;
    private int numberOfEntries;

    @SuppressWarnings("unchecked")
    public ArrayBag() {
        bag = (T[]) new Object[DEFAULT_CAPACITY];
        numberOfEntries = 0;
    }

    @SuppressWarnings("unchecked")
    public ArrayBag(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Capacity must be greater than zero.");
        }

        bag = (T[]) new Object[capacity];
        numberOfEntries = 0;
    }

    /**
     * Adds one item to the Bag.
     */
    public boolean add(T item) {
        ensureCapacity();

        bag[numberOfEntries] = item;
        numberOfEntries++;

        return true;
    }

    /**
     * Checks whether the item exists in the Bag.
     */
    public boolean contains(T item) {
        for (int index = 0; index < numberOfEntries; index++) {
            if (Objects.equals(bag[index], item)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the number of occurrences of an item.
     */
    public int frequencyOf(T item) {
        int frequency = 0;

        for (int index = 0; index < numberOfEntries; index++) {
            if (Objects.equals(bag[index], item)) {
                frequency++;
            }
        }

        return frequency;
    }

    /**
     * Removes one occurrence of an item.
     */
    public boolean remove(T item) {
        for (int index = 0; index < numberOfEntries; index++) {
            if (Objects.equals(bag[index], item)) {

                // Replace the removed item with the last item.
                bag[index] = bag[numberOfEntries - 1];

                // Remove the duplicate reference.
                bag[numberOfEntries - 1] = null;

                numberOfEntries--;

                return true;
            }
        }

        // Item was not found.
        return false;
    }

    /**
     * Returns the current number of stored items.
     */
    public int size() {
        return numberOfEntries;
    }

    /**
     * Checks whether the Bag is empty.
     */
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    /**
     * Increases the internal array size when it becomes full.
     */
    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (numberOfEntries == bag.length) {

            T[] oldBag = bag;

            bag = (T[]) new Object[oldBag.length * 2];

            for (int index = 0; index < oldBag.length; index++) {
                bag[index] = oldBag[index];
            }
        }
    }
}
