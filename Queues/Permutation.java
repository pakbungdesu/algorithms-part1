
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args){
        RandomizedQueue<String> q1 = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String txt = StdIn.readString();
            q1.enqueue(txt);
        }

        Iterator<String> it = q1.iterator();
        while(it.hasNext()){
            String item = q1.sample(); // uniformly random and remove from que
            StdOut.println(item);
            it.next();
        }
    }
}
