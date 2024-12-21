
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    private static class Node<Item>{
        private Item item;
        private Node<Item> next;
    }

    // construct an empty randomized queue
    public RandomizedQueue(){
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return first == null && last == null;
    }

    // return the number of items on the randomized queue
    public int size(){
        return size;
    }

    // add the item
    public void enqueue(Item item){
        if (item == null) {
            throw new IllegalArgumentException("Method with null argument");
        }

        Node<Item> newLast = new Node<>();
        newLast.item = item;

        if (isEmpty()) {
            first = newLast;
        } else {
            last.next = newLast;
        }
        last = newLast;
        size++; // increment size
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        Item item;
        if (size == 1) {
            // Single item case
            item = first.item;
            first = null;
            last = null;
        } else {
            // Random index to remove
            int rand = StdRandom.uniformInt(size); // Index in range [0, size)
            Node<Item> current = first;
            Node<Item> previous = null;

            // Traverse to the random node
            for (int i = 0; i < rand; i++) {
                previous = current;
                current = current.next;
            }

            // Remove the node
            item = current.item;
            if (previous == null) {
                // Removing the first node
                first = first.next;
            } else {
                previous.next = current.next;
            }

            // Update last if needed
            if (current == last) {
                last = previous;
            }
        }
        size--; // Decrement size
        return item;
    }


    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        Item item;
        if (size == 1) {
            // Same as first item
            item = last.item;
        } else {
            int rand = StdRandom.uniformInt(size); // Index in range [0, size)
            Node<Item> current = first;

            // Traverse to the random node
            for (int i = 0; i < rand; i++) {
                current = current.next;
            }
            item = current.item;
        }
        return item;
    }

    @Override
    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        // copy items into an array
        private final Item[] shuffledItems = (Item[]) new Object[size];
        private int index;
    
        public RandomizedIterator() {
            Node<Item> current = first;
            int i = 0;
            while (current != null) {
                shuffledItems[i++] = current.item;
                current = current.next;
            }
    
            // shuffle the array
            StdRandom.shuffle(shuffledItems);
    
            // initialize index
            index = 0;
        }
    
        @Override
        public boolean hasNext() {
            return index < shuffledItems.length;
        }
    
        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            return shuffledItems[index++];
        }
    
        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() not supported for stacks");
        }
    }

    // unit testing (required)
    public static void main(String[] args){

        // test 1: public method
        RandomizedQueue<String> q1 = new RandomizedQueue<>();
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
        for(String l: letters){
            q1.enqueue(l);
        }

        StdOut.println("Test isEmpty(): " + q1.isEmpty());
        StdOut.println("Test size(): " + q1.size());
        StdOut.println("Test iterator after enqueue: ");

        for (String string : q1) {
            StdOut.println(string);
        }

        String item1 = q1.sample();
        StdOut.println("Test sample(): " + item1);

        String item2 = q1.dequeue();
        StdOut.println("Test dequeue(): " + item2);

        StdOut.println("Test iterator before dequeue: ");

        for (String s : q1) {
            StdOut.println(s);
        }

        // test2: try-except
        RandomizedQueue<String> q2 = new RandomizedQueue<>();
        try {
            q2.enqueue(null);
            StdOut.println("Test enqueue() exception passed? " + false);
        } catch (IllegalArgumentException e) {
            StdOut.println("Test enqueue() exception passed? " + true);
        }

        try {
            q2.dequeue();
            StdOut.println("Test dequeue() exception passed? " + false);
        } catch (NoSuchElementException e) {
            StdOut.println("Test dequeue() exception passed? " + true);
        }

        try {
            q2.sample();
            StdOut.println("Test sample() exception passed? " + false);
        } catch (NoSuchElementException e) {
            StdOut.println("Test sample() exception passed? " + true);
        }

        Iterator<String> it3 = q2.iterator();
        try {
            it3.next();
            StdOut.println("Test next() exception passed? " + false);
        } catch (NoSuchElementException e) {
            StdOut.println("Test next() exception passed? " + true);
        }

        try {
            it3.remove();
            StdOut.println("Test remove() exception passed? " + false);
        } catch (UnsupportedOperationException e) {
            StdOut.println("Test remove() exception passed? " + true);
        }
    }
}