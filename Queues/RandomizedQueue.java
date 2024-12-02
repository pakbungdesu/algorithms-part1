
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
            last = newLast;
        } else {
            last.next = newLast;
            last = newLast;
        }
        size++; // increment size
    }

    // remove and return a random item
    public Item dequeue(){
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        
        Item item = null;
        if(size == 1){
            item = last.item;
        } else {
            int p = 1;
            Node<Item> current = first;
            Node<Item> before = null;
            int rand = StdRandom.uniformInt(1, size+1); // random an item

            while (current != null) {
                if(p == rand){ // find a random item
                    if(before == null){ // if it is the first item
                        first = current.next;
                    } else { // in the middle
                        before.next = current.next; 
                    }
                    item = current.item;
                    break;
                }
                before = current;
                current = current.next;
                p++;
            }
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        Item item = null;
        if(size == 1){
            item = last.item;
        } else {
            int p = 1;
            int rand = StdRandom.uniformInt(1, size+1); // random an item
            Node<Item> current = first;
            while (current != null) {
                if(p == rand){ // find a random item
                    item = current.item;
                    break;
                }
                current = current.next;
                p++;
            }
        }
        return item;
    }

    @Override
    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item>{
        private Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            Item item = current.item;
            current = current.next;
            return item;
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
        String[] cars = {"Volvo", "BMW", "Ford", "Mazda", "Toyota", "Honda", "Suzuki", "Nisson", "Benz"};
        for(String c: cars){
            q1.enqueue(c);
        }

        StdOut.println("Test isEmpty(): " + q1.isEmpty());
        StdOut.println("Test size(): " + q1.size());
        StdOut.println("Test iterator after enqueue: ");
        Iterator<String> it1 = q1.iterator();
        
        while(it1.hasNext()){
            StdOut.println(it1.next());
        }

        String item1 = q1.sample();
        StdOut.println("Test sample(): " + item1);

        String item2 = q1.dequeue();
        StdOut.println("Test dequeue(): " + item2);

        StdOut.println("Test iterator before dequeue: ");
        Iterator<String> it2 = q1.iterator();
        
        while(it2.hasNext()){
            StdOut.println(it2.next());
        }

        // test2: try-except
        RandomizedQueue<String> q2 = new RandomizedQueue<>();
        try {
            q2.enqueue(null);
            StdOut.println("Test enqueue() exception passed? " + false);
        } catch (Exception e) {
            boolean res = e instanceof IllegalArgumentException;
            StdOut.println("Test enqueue() exception passed? " + res);
        }

        try {
            q2.dequeue();
            StdOut.println("Test dequeue() exception passed? " + false);
        } catch (Exception e) {
            boolean res = e instanceof NoSuchElementException;
            StdOut.println("Test dequeue() exception passed? " + res);
        }

        try {
            q2.sample();
            StdOut.println("Test sample() exception passed? " + false);
        } catch (Exception e) {
            boolean res = e instanceof NoSuchElementException;
            StdOut.println("Test sample() exception passed? " + res);
        }

        Iterator<String> it3 = q2.iterator();
        try {
            it3.next();
            StdOut.println("Test next() exception passed? " + false);
        } catch (Exception e) {
            boolean res = e instanceof NoSuchElementException;
            StdOut.println("Test next() exception passed? " + res);
        }

        try {
            it3.remove();
            StdOut.println("Test remove() exception passed? " + false);
        } catch (Exception e) {
            boolean res = e instanceof UnsupportedOperationException;
            StdOut.println("Test remove() exception passed? " + res);
        }
    }
}