/**
 * @author Kverchi
 * Date: 18.8.2018
 *
 * Implements a generic data type RandomizedQueue
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int queueSize;

    /**
     * Constructor creates an empty randomized queue
     */
    public RandomizedQueue(){
        items = (Item[]) new Object[2];
        queueSize = 0;
    }

    /**
     * Checks if randomized queue is empty
     * @return result
     */
    public boolean isEmpty(){
        return queueSize == 0;
    }

    /**
     * Returns the number of items on the randomized queue
     * @return the number of items
     */
    public int size(){
        return queueSize;
    }

    /**
     * Adds the item
     * @param item
     */
    public void enqueue(Item item){
        if(item == null) {
            throw new IllegalArgumentException("The item must not be null");
        }
        if(queueSize == items.length) {
            resize(2 * items.length);
        }
        items[queueSize++] = item;
    }

    /**
     * Removes and returns a random item
     * @return random item
     */
    public Item dequeue(){
        if(isEmpty()) {
            throw new NoSuchElementException("The randomized queue is empty.");
        }
        int randomItemIndex = StdRandom.uniform(queueSize);
        Item item = items[randomItemIndex];
        queueSize--;
        items[randomItemIndex] = items[queueSize];
        items[queueSize] = null;
        if(queueSize > 0 && queueSize == items.length/4) {
             resize(items.length/2);
        }
        return item;
    }

    /**
     * Returns a random item (but does not remove it)
     * @return random item
     */
    public Item sample(){
        if(isEmpty()) {
            throw new NoSuchElementException("The randomized queue is empty.");
        }
        int randomItemIndex = StdRandom.uniform(queueSize);
        Item item = items[randomItemIndex];
        return item;
    }
    private void resize(int capacity) {
        if(capacity < queueSize) {
            return;
        }
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < queueSize; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    /**
     * Returns an independent iterator over items in random order
     * @return iterator
     */
    @Override
    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }
    /**
     * Iterator implementation for randomized queue
     */
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int iteratorQueueSize;
        private Item[] iteratorItems;

        public RandomizedQueueIterator() {
            iteratorItems = (Item[]) new Object[queueSize];
            for (int i = 0; i < queueSize; i++) {
                iteratorItems[i] = items[i];
            }
            StdRandom.shuffle(iteratorItems);
            iteratorQueueSize = 0;
        }
        @Override
        public boolean hasNext() {
            return iteratorQueueSize < iteratorItems.length;
        }
        @Override
        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException("The randomized queue is empty.");
            }
            return iteratorItems[iteratorQueueSize++];
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args){

    }
}
