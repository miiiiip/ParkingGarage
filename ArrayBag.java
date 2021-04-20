import java.util.Random;

/**
 * This class is the blueprint for an ADT bag as discussed in "Data Structures and Abstractions"
 * @author Mason Eischens
 */

public final class ArrayBag<T> implements BagInterface<T>{
    private T[] bag;
    private int numberOfEntries;
    private final static int DEFAULT_CAPACITY = 10; // default capacity set to 10 because only 10 cars can fit on each floor
    private boolean integrityOK;
    private final static int MAX_CAPACITY = 10000;

    public ArrayBag(int capacity){
        if (capacity <= MAX_CAPACITY){
            this.integrityOK = false;
            @SuppressWarnings("unchecked")
            T[] tempBag = (T[]) new Object[capacity];
            bag = tempBag;
            this.numberOfEntries = 0;
            this.integrityOK = true;}
        else{
            throw new IllegalStateException("Attempt to create a bag whose capacity is beyond the max.");
        }
    } // end constructor

    public ArrayBag(){
        this(DEFAULT_CAPACITY);
    } // end constructor

    /** Gets the current number of entries in the bag.
     * @return the integer number of entries in the bag
     */
    public int getSize(){
        checkIntegrity();
        return numberOfEntries;
    } // end getSize


    /** Checks whether or not the bag is empty.
     * @return true if the bag is empty, false otherwise
     */
    public boolean isEmpty(){
        checkIntegrity();
        boolean result = false;
        if (numberOfEntries == 0){ // checks for emptiness
            result = true;
        } // end if
        return result;
    } // end isEmpty

    /**
     * Determines if the object is corrupted, throws an exception if so.
     */
    private void checkIntegrity(){
        if (!integrityOK){
            throw new SecurityException("ArrayBag object is corrupt.");
        } // end if

    } // end checkIntegrity

    /** Adds a specified entry to the bag.
     * @param newEntry The object to be added
     * @return true if the addition is successful, false otherwise
     */
    public boolean add(T newEntry){
        checkIntegrity();
        boolean result = true;
        if (isArrayFull()){
            result = false;
        } // end if
        else {
            bag[numberOfEntries] =  newEntry;
            numberOfEntries++;
        } // end else
        return result;
    } // end add

    /**
     * Checks whether or not the array is at maximum capacity.
     * @return True if the bag is full or overfilled, false otherwise.
     */
    public boolean isArrayFull(){
        checkIntegrity();
        return numberOfEntries >= bag.length;
    } // end isArrayFull

    /** If possible, removes the first entry from the bag.
     * @return the removed entry, if the removal is successful, and null otherwise
     */
    public T remove(){
        checkIntegrity();
        T result = null;
        if (!isEmpty()){
            result = bag[0]; // sets the result to the first entry so it can be returned
            remove(bag[0]); // uses the second remove method to get rid of the first entry
        } // end if
        return result;
    } // end remove

    /** Removes the first instance of a specified entry from the bag if possible.
     * @param anEntry the entry to be removed.
     * @return true if the removal was successful, false otherwise
     */
    public boolean remove(T anEntry)

    {
        checkIntegrity();
        boolean result = false;
        for (int i = 0; i < numberOfEntries; i++){
            if (bag[i] == anEntry){
                @SuppressWarnings("unchecked")
                T[] tempBag = (T[]) new Object[bag.length]; // creates a new empty array
                for (int ii = 0; ii < numberOfEntries - 1; ii++){
                    if (ii == i){
                        tempBag[ii] = bag[numberOfEntries-1]; // replaces the removed element by the last item to maintain consecutiveness
                    } // end if
                    else {
                        tempBag[ii] = bag[ii];
                    } // end else
                } // end for loop
                bag = tempBag;
                result = true;
                numberOfEntries--;
                break;
            } // end if
        } // end for loop
        return result;
    } // end remove

    // EXERCISE #3 //
    /**
     * Removes all entries from the bag
     */
    public void clear(){
        checkIntegrity();
        @SuppressWarnings("unchecked")
        T[] tempBag = (T[]) new Object[bag.length]; // creates a new empty array
        bag = tempBag; // replaces the current bag by the new empty bag
        numberOfEntries = 0; // resets the entries counter
    } // end clear

    /** Counts the number of times a given entry appears in the bag.
     * @param anEntry The entry to be counted.
     * @return the integer number of times the entry appears
     */
    public int getFrequencyOf(T anEntry){
        checkIntegrity();
        int counter = 0;
        for (int i = 0; i < numberOfEntries; i++){
            if (bag[i] == anEntry){
                counter++;
            } // end if
        } // end for loop
        return counter;
    } // end getFrequencyOf

    /** Tests whether or not the bag contains a specified entry.
     * @param anEntry the entry to find.
     * @return true if the bag contains the entry, false otherwise
     */
    public boolean contains(T anEntry){
        checkIntegrity();
        boolean result = false;
        for (int i = 0; i < numberOfEntries; i++){
            if (bag[i] == anEntry){
                result = true;
                break;
            } // end if
        } // end for loop
        return result;
    } // end contains

    /** Retrieves all entries in the bag.
     * @return an array containing all the entries in the bag
     * Note: if the bag is empty, the returned array will be empty.
     */
    public T[] toArray(){
        checkIntegrity();
        @SuppressWarnings("unchecked")
        T[] result = (T[])new Object[numberOfEntries];
        for (int i = 0; i < numberOfEntries; i++){
            result[i] = bag[i];
        } // end for loop
        return result;
    } // end toArray

    // EXERCISE #2 //
    /**
     * Replaces a specified entry with the first instance of given one
     * in the bag, returning the removed object.
     * @param toBeAdded the object to be added in the place of the specified object
     * @param toBeRemoved the object to be removed
     * @return the removed object
     */
    public T replace(T toBeAdded, T toBeRemoved){
        checkIntegrity();
        T result = null;
        if (contains(toBeRemoved)){
            for (int i = 0; i < numberOfEntries; i++){
                if (bag[i] == toBeRemoved){
                    result = bag[i];
                    bag[i] = toBeAdded;
                    break;
                } // end if
            } // end for loop
        } // end if
        return result;
    }// end replace

    //EXERCISE #4//
    // If I simply replaced my original remove method with this implementation, it would make it impossible to remove
    // a specific object or an object at a specific index
    /**
     * Removes a random object from a non-empty bag.
     * @return the removed object
     */
    public T randRemove(){
        checkIntegrity();
        T result = null;
        Random rand = new Random();
        if (!isEmpty()){
            int index = rand.nextInt(numberOfEntries);
            result = bag[index];
            remove(bag[index]);
        } // end if
        return result;
    } // end randRemove

    //EXERCISE #5//
    /**
     * Removes all instances of a given object from the bag.
     * @param anEntry the object to be removed
     */
    public void removeAll(T anEntry){
        checkIntegrity();
        while (contains(anEntry)) {
            remove(anEntry);
        } // end while
    } // end removeAll

    /**
     * Returns but does not remove the object at a specified index
     * @param givenIndex   The index of the desired object
     * @return The object at the specified index
     */
    public T getObject(int givenIndex)
    {
        T result = null;
        if (!isEmpty() && (givenIndex >= 0))
        {
            result = bag[givenIndex]; // entry to remove
        } // end if
        return result;
    } // end getObject

    // Returns true if the bag is full, false otherwise.
    public boolean isFull()
    {
        return numberOfEntries >= bag.length;
    } // end isArrayFull

    /**
     * Gets the current size of the bag.
     * @return The number of entries in the bag.
     */
    public int getCurrentSize()
    {
        return numberOfEntries;
    } // end getCurrentSize

}

