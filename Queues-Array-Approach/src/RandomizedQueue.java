
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] data;
    private int size;
    private int front;
    private int back;

    // construct an empty randomized queue
    public RandomizedQueue(){
        this.data = (Item[]) new Object[2];
        this.size = 0;
        this.front = 0;
        this.back = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return this.size;
    }

    private void resize(int capacity) {
        Item[] newData = (Item[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            newData[i] = this.data[(this.front + i) % this.data.length];
        }
        this.data = newData;
        this.front = 0;
        this.back = this.size;
    }

    // add the item
    public void enqueue(Item item){
        if (item == null) {
            throw new IllegalArgumentException("Null item not allowed");
        }
        if (this.size == this.data.length) {
            resize(2 * this.data.length);
        }
        this.data[this.back] = item;
        this.back = (this.back + 1) % this.data.length;
        this.size++;
    }


    // remove and return a random item
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        int rand = StdRandom.uniformInt(this.size);
        int actualIndex = (this.front + rand) % this.data.length;
        Item randomItem = this.data[actualIndex];

        // rearrange items after removal
        if (actualIndex != this.front) {
            // move the item at the back to the `actualIndex` for efficient removal
            int lastIndex = (this.back - 1 + this.data.length) % this.data.length;
            this.data[actualIndex] = this.data[lastIndex];
            this.data[lastIndex] = null;
            this.back = lastIndex;
        } else {
            this.data[this.front] = null;
            this.front = (this.front + 1) % this.data.length;
        }

        this.size--;
        if (this.size > 0 && this.size <= this.data.length / 4) {
            resize(this.data.length / 2);
        }

        return randomItem;
    }


    // return a random item (but do not remove it)
    public Item sample(){
        if (this.isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        int rand = StdRandom.uniformInt(this.size); // Random index within the current size
        return this.data[(this.front + rand) % this.data.length];
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<>() {
            private final Item[] shuffledItems;
            private int current = 0;

            // constructor to shuffle items
            {
                this.shuffledItems = (Item[]) new Object[size];
                for (int i = 0; i < size; i++) {
                    this.shuffledItems[i] = data[(front + i) % data.length];
                }
                StdRandom.shuffle(this.shuffledItems); // Shuffle items randomly
            }

            @Override
            public boolean hasNext() {
                return this.current < this.shuffledItems.length;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more items to return");
                }
                return this.shuffledItems[this.current++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove() not supported");
            }
        };
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

        for (String s : q1) {
            StdOut.println(s);
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
