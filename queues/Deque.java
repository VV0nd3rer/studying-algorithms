/**
 * @author Kverchi
 * Date: 18.8.2018
 *
 * Implements a generic data type Deque.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int dequeSize;

    private class Node {
        Item item;
        Node previous;
        Node next;
    }
    /**
     * Constructor creates an empty deque
     */
    public Deque() {
        dequeSize = 0;
        first = null;
        last = null;
    }
    /**
     * Checks if the deque is empty
     * @return result of checking
     */
    public boolean isEmpty() {
        return dequeSize == 0;
    }

    /**
     * Number of items on the deque
     * @return the number of items on the deque
     */
    public int size() {
        return dequeSize;
    }

    /**
     * Adds the item to the front
     * @param item deque element
     */
    public void addFirst(Item item) {
        if(item == null) {
            throw new IllegalArgumentException("The item must not be null.");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if(isEmpty()) {
            last = first;
        } else {
            Node nextNode = first.next;
            nextNode.previous = first;
        }
        dequeSize++;
    }

    /**
     * Adds the item to the end
     * @param item deque element
     */
    public void addLast(Item item){
        if(item == null) {
            throw new IllegalArgumentException("The item must not be null.");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.previous = oldLast;
        last.next = null;
        if(isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        dequeSize++;
    }

    /**
     * Removes and returns the item from the front
     * @return item
     */
    public Item removeFirst(){
        if(isEmpty()) {
            throw new NoSuchElementException("The deque is empty");
        }
        Item item = first.item;
        dequeSize--;
        if(isEmpty()) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.previous = null;
        }
        return item;
    }

    /**
     * Removes and returns the item from the end
     * @return item
     */
    public Item removeLast(){
        if(isEmpty()){
            throw new NoSuchElementException("The deque is empty.");
        }
        Item item = last.item;
        dequeSize--;
        if(isEmpty()) {
            first = null;
            last = null;
        } else {
            last = last.previous;
            last.next = null;
        }
        return item;
    }

    /**
     * Returns an iterator over items in order from front to end
     * @return iterator
     */
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }

    /**
     * Iterator implementation for deque
     */
    private class DequeIterator implements Iterator<Item> {
        private Node currentNode = first;
        @Override
        public boolean hasNext() {
            return currentNode != null;
        }
        @Override
        public Item next() {
            if(isEmpty()){
                throw new NoSuchElementException("The deque is empty.");
            }
            Item item = currentNode.item;
            currentNode = currentNode.next;
            return item;
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args){
    }
}