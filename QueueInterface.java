/**
 * An interface that describes the the operations of a queue.
 */
public interface QueueInterface<T>{
    /**
     * adds a specified entry to the back of the queue
     * @param newEntry an object to be added
     */
    public void enqueue(T newEntry);

    /**
     * retrieves the entry at the front of a non-empty queue
     * @return the object at the front of the queue
     */
    public T getFront();

    /**
     * retrives and removes the entry at the front of a non-empty queue
     * @return the object at the front of the queue
     */
    public T dequeue();

    /**
     * detects whether the queue is empty
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty();

    /**
     * removes all entries from the queue
     */
    public void clear();
}
