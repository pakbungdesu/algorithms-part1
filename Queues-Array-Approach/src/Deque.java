
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] data;
    private int size;
    private int front;
    private int back;

    // construct an empty deque
    public Deque() {
        this.data = (Item[]) new Object[2];
        this.size = 0;
        this.front = 0;
        this.back = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    private void resize(int capacity) {
        if (capacity < 2) return; // ensure minimum capacity to avoid excessive shrinking
        Item[] newData = (Item[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            newData[i] = this.data[(this.front + i) % this.data.length];
        }
        this.data = newData;
        this.front = 0;
        this.back = this.size;
    }


    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null item not allowed");
        }
        if (this.size == this.data.length) {
            resize(2 * this.data.length); // double capacity only when full
        }
        this.front = (this.front - 1 + this.data.length) % this.data.length;
        this.data[this.front] = item;
        this.size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null item not allowed");
        }
        if (this.size == this.data.length) {
            resize(2 * this.data.length); // double capacity only when full
        }
        this.data[this.back] = item;
        this.back = (this.back + 1) % this.data.length;
        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = this.data[this.front];
        this.data[this.front] = null; // avoid memory leaks
        this.front = (this.front + 1) % this.data.length;
        this.size--;
        if (this.size > 0 && this.size <= this.data.length / 4) {
            resize(this.data.length / 2);
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        this.back = (this.back - 1 + this.data.length) % this.data.length;
        Item item = this.data[this.back];
        this.data[this.back] = null; // avoid memory leaks
        this.size--;
        if (this.size > 0 && this.size <= this.data.length / 4) {
            resize(this.data.length / 2);
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return this.current < size;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more items to return");
                }
                Item item = data[(front + this.current) % data.length];
                this.current++;
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove() not supported");
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Test cases
        Deque<String> deque = new Deque<>();
        deque.addFirst("A");
        deque.addLast("B");
        deque.addFirst("C");

        StdOut.println("Size: " + deque.size()); // 3
        for(String chr: deque){
            StdOut.println(chr);
        }

        StdOut.println("Remove first: " + deque.removeFirst()); // C
        for(String chr: deque){
            StdOut.println(chr);
        }

        StdOut.println("Remove last: " + deque.removeLast()); // B
        for(String chr: deque){
            StdOut.println(chr);
        }

        StdOut.println("Size: " + deque.size());
    }
}
