import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Graph g = new Graph();
        //System.out.println(sbt.run(0,g.vertexLookup.length-1,new HashMap<>()));
        // langsommere
        Pair<int[][], int[]> p = randomGenerator.normalGenerator(10,10,2,2,false,1222L);
        // hurtigere
        //Pair<int[][], int[]> p = randomGenerator.metaGenerator(2500, 5,222L);
        //Pair<int[][], int[]> p = randomGenerator.metaGenerator(4, 5,3708433616387827147L);
        for (int[] a:p.getKey()) {
            System.out.println(Arrays.toString(a));
        }
        g.edgesInput(p.getKey(),p.getValue());
        System.out.println(g.vertexLookup.length);
        long startTime = System.nanoTime();
        //SBT sbt = new SBT(g,function.sum);
        //sbt.run(0,g.vertexLookup.length-1, new HashMap<>());
        KShortestPath kShortestPath = new KShortestPath(g,0,g.vertexLookup.length-1,100,function.sum,false);
        long stopTime = System.nanoTime();
        System.gc();
        System.out.println((float) (stopTime - startTime)/1000000000);
        ArrayList<ArrayList<Integer>> path = kShortestPath.getPaths();
    }

    public void dijkVsbt(){
        int[] skip = {};
        int count = 0;
        for (int i = 0; i < 1; i++) {
            Graph g = new Graph();
            Pair<int[][], int[]> p = randomGenerator.metaGenerator(50, 1000,121453412454L);
            g.edgesInput(p.getKey(),p.getValue());
            SBT sbt = new SBT(g,function.sum);
            System.out.println("SBT running");
            //sbt.run(0,g.vertexLookup.length-1,new HashMap<>());
            Graph dijk = g.convertToNormalGraph();
            System.out.println(g.getVertexCost(g.vertexLookup.length-1));
            Dijkstra s = new Dijkstra(dijk);
            System.out.println("Dijkstra Running");
            s.run(0,dijk.vertexLookup.length-1);
            if(g.getVertexCost(g.vertexLookup.length-1) != dijk.getVertexCost(dijk.vertexLookup.length-1)) {
                System.out.println("Failed :(" + g.getVertexCost(g.vertexLookup.length - 1) + " & " + dijk.getVertexCost(dijk.vertexLookup.length - 1));
                System.out.println(g.seed);
                count++;
            }
        }
        System.out.println(count);
    }
}
