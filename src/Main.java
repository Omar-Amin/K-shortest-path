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
        Pair<int[][], int[]> p = randomGenerator.normalGenerator(70000,1000,100,2,true,-6606397267175329844L);
        Hypergraph h = new Hypergraph().arraylistInput(p.getKey(),p.getValue());
        Hyperpath shortest = shortestPath.SBT(h,h.getSource(),h.getTarget(),new HashMap<>());
    }
}
