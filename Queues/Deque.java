
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    private static class Node<Item>{
        private Item item;
        private Node<Item> next;
    }

    // construct an empty q1
    public Deque(){
        size = 0;
    }

    // is the q1 empty?
    public boolean isEmpty(){ 
        return first == null && last == null;
    }

    // return the number of items on the q1
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if (item == null) {
            throw new IllegalArgumentException("Method with null argument");
        }

        Node<Item> newFirst = new Node<>();
        newFirst.item = item;

        if (isEmpty()) {
            first = newFirst;
            last = newFirst;
        } else {
            newFirst.next = first;
            first = newFirst;
        }

        size++; // increment size
    }

    // add the item to the back
    public void addLast(Item item){
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

    // remove and return the item from the front
    public Item removeFirst(){
        if(isEmpty()){
            throw new NoSuchElementException("Stack is empty");
        }

        if(size == 1){
            Item item = first.item;
            first = null;
            last = null;
            size--;
            return item;
        } else {
            Node<Item> res = first;
            first = first.next;
            size--;
            return res.item;
        }
    }

    // remove and return the item from the back
    public Item removeLast(){   
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        if(size == 1){
            Item item = last.item;
            first = null;
            last = null;
            size--;
            return item;
        } else {
            Node<Item> current = first;
            while (current.next != last) { // find the second last
                current = current.next;
            }
            Item item = current.next.item; // save the last item
            last = current; // update last
            last.next = null; // remove the old last
            size--;
            return item;
        }
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

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
        Deque<String> q1 = new Deque<>();
        q1.addFirst("Hello");
        q1.addLast("World");
        q1.addFirst("!"); 

        StdOut.println("Test isEmpty(): " + q1.isEmpty());
        StdOut.println("Test size(): " + q1.size());

        Iterator<String> it1 = q1.iterator();
        StdOut.println("Test iterator after enqueue: ");
        while(it1.hasNext()){
            StdOut.println(it1.next());
        }

        StdOut.println("Test removeFirst(): " + q1.removeFirst()); // !
        StdOut.println("Test removeLast(): " + q1.removeLast()); // World
        StdOut.println("Test isEmpty(): " + q1.isEmpty()); // false
        StdOut.println("Test size(): " + q1.size()); // 1

        // test 2: try-except
        Deque<String> q2 = new Deque<>();
        try {
            q2.addFirst(null);
            StdOut.println("Test addFirst() exception passed? " + false);
        } catch (Exception e) {
            boolean res = e instanceof IllegalArgumentException;
            StdOut.println("Test addFirst() exception passed? " + res);
        }

        try {
            q2.addLast(null);
            StdOut.println("Test addLast() exception passed? " + false);
        } catch (Exception e) {
            boolean res = e instanceof IllegalArgumentException;
            StdOut.println("Test addLast() exception passed? " + res);
        }

        try {
            q2.removeFirst();
            StdOut.println("Test removeFirst() exception passed? " + false);
        } catch (Exception e) {
            boolean res = e instanceof NoSuchElementException;
            StdOut.println("Test removeFirst() exception passed? " + res);
        }

        try {
            q2.removeLast();
            StdOut.println("Test removeLast() exception passed? " + false);
        } catch (Exception e) {
            boolean res = e instanceof NoSuchElementException;
            StdOut.println("Test removeLast() exception passed? " + res);
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