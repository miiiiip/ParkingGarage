/**
 * Blueprint for the ADT Linked Queue
 * @param <T>
 */
public class LinkedQueue<T> implements QueueInterface<T>{

    private Node firstNode; // node at the end of the queue
    private Node lastNode; // node at the front of the queue

    public LinkedQueue(){
        this.firstNode = null;
        this.lastNode = null;
    } // end constructor

    private class Node {
        private T data;
        private Node next;

        private Node (T data, Node next){
            this.data = data;
            this.next = next;
        } // end constructor

        // mutators/accessors
        public T getData(){
            return this.data;
        }

        public void setData(T data){
            this.data = data;
        }

        public Node getNext(){
            return this.next;
        }

        public void setNext(Node next){
            this.next = next;
        }
    } // end Node class

    /**
     * adds a specified entry to the back of the queue
     * @param newEntry an object to be added
     */
    public void enqueue(T newEntry){
        Node newNode = new Node(newEntry, null);

        if (isEmpty()){
            firstNode = newNode;
        }
        else {
            lastNode.setNext(newNode);
        }
        lastNode = newNode;
    }

    /**
     * retrieves the entry at the front of a non-empty queue
     * @return the object at the front of the queue
     */
    public T getFront(){
        if (isEmpty()){
            throw new EmptyQueueException();
        }
        else {
            return firstNode.getData();
        }
    }

    /**
     * retrives and removes the entry at the front of a non-empty queue
     * @return the object at the front of the queue
     */
    public T dequeue(){
        T front = getFront(); // might throw exception, assumes firstNode != null
        firstNode = firstNode.getNext();

        if (firstNode == null){
            lastNode = null;
        } // end if
        return front;
    } // end dequeue

    /**
     * detects whether the queue is empty
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty(){
        return firstNode == null;
    } // end isEmpty

    /**
     * removes all entries from the queue
     */
    public void clear(){
        firstNode = null;
        lastNode = null;
    } // end clear
} // end LinkedQueue
