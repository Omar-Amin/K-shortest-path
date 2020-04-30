import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) {
        testWithRandomHyper();
    }

    private static void testWithRandomHyper(){
        ShortestPath shortestPath = new ShortestPath();
        //Pair<int[][], int[]> p = randomGenerator.metaGenerator(50, 1000,121453412454L);
        //Hypergraph h = new Hypergraph().arraylistInput(p.getKey(),p.getValue());
        Pair<int[][], int[]> p = randomGenerator.normalGenerator(1000,4,4,2,true);
        Hypergraph h = new Hypergraph().arraylistInput(p.getKey(),p.getValue());
        long startTime = System.nanoTime();
        Hyperpath shortest = shortestPath.SBT(h,h.getSource(),h.getTarget(),new HashMap<>());
        long stopTime = System.nanoTime();
        if(shortest != null){
            System.out.println(shortest.getCost());
        }
        System.out.println((float) (stopTime - startTime)/1000000000);
    }
}
