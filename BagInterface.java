/**
 * An interface that describes the the operations of a bag of objects.
 */

public interface BagInterface<T> {
    /** Gets the current number of entries in the bag.
     * @return the integer number of entries in the bag
     */
    public int getSize();

    /** Checks whether or not the bag is empty.
     * @returns true if the bag is empty, false otherwise
     */
    public boolean isEmpty();

    /**
     * Checks whether or not the array is at maximum capacity.
     * @return True if the bag is full or overfilled, false otherwise.
     */
    public boolean isArrayFull();

    /** Adds a specified entry to the bag.
     * @param newEntry The object to be added
     * @return true if the addition is successful, false otherwise
     */
    public boolean add(T newEntry);

    /** If possible, removes one unspecified entry from the bag.
     * @return the removed entry, if the removal is successful, and null otherwise
     */
    public T remove();

    /** Removes a specified entry from the bag if possible.
     * @param anEntry the entry to be removed.
     * @return true if the removal was successful, false otherwise
     */
    public boolean remove(T anEntry);

    /** Removes all entries from the bag
     */
    public void clear();

    /** Counts the number of times a given entry appears in the bag.
     * @param anEntry The entry to be counted.
     * @return the integer number of times the entry appears
     */
    public int getFrequencyOf(T anEntry);

    /** Tests whether or not the bag contains an entry.
     * @param anEntry the entry to find.
     * @return true if the bag contains the entry, false otherwise
     */
    public boolean contains(T anEntry);

    /** Retrieves all entries in the bag.
     * @return an array containing all the entries in the bag
     * Note: if the bag is empty, the returned array will be empty.
     */
    public T[] toArray();

    /**
     * Checks to see if the bag is full
     * @return  true if the bag is full, false otherwise
     */
    public boolean isFull();

    /**
     * Return but does not remove the object at a specified index
     * @param index   The index of the desired object
     * @return The object at the specified index
     */
    public T getObject(int index);

    /** Gets the current number of entries in this bag
     * @return The integer number of entries currently in the bag.
     */
    public int getCurrentSize();

    /**
     * Replaces and returns a specified object currently in
     * the bag with a given object
     * @param anEntry The entry that will be swapped out.
     * @param replacement The object that will replace the specified entry.
     * @return The object that was replaced.
     */
    public T replace(T anEntry, T replacement);
}

