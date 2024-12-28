
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args){

        String inputTxt = args[0];
        int inputInt = Integer.parseInt(inputTxt);

        RandomizedQueue<String> q1 = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String txt = StdIn.readString();
            q1.enqueue(txt);
        }

        // print random permutations
        for (int i = 0; i < inputInt; i++) {
            String item = q1.dequeue();
            StdOut.println(item);
        }
    }
}

