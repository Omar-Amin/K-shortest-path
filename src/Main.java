import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Graph g = new Graph();
        Pair<int[][], int[]> p = randomGenerator.metaGenerator(50, 100,1289347L);
        g.edgesInput(p.getKey(),p.getValue());
        SBT sbt = new SBT(g,function.sum);
        ArrayList<Integer> l = sbt.run(0,g.vertexLookup.length-1,new HashMap<>());
        System.out.println(g.getVertexCost(g.vertexLookup.length-1));
        String print = "";
        for (Integer i:l) {
            print += i + ", ";
        }
        System.out.println(print);
    }

    public static void test(){
        Graph g = new Graph();
        //System.out.println(sbt.run(0,g.vertexLookup.length-1,new HashMap<>()));
        //Pair<int[][], int[]> p = randomGenerator.normalGenerator(50,10,2,2,false);
        long sum = 0L;
        Random rand = new Random(10);
        for (int i = 0; i < 1; i++) {
            //Pair<int[][], int[]> p = randomGenerator.metaGenerator(50, 100,rand.nextLong());
            Pair<int[][], int[]> p = randomGenerator.metaGenerator(50, 25,1289347L);
            g.edgesInput(p.getKey(),p.getValue());
            //System.out.println("Number of vertices: "+g.vertexLookup.length);
            //System.out.println(g.edgeLookup.length);
            //Pair<int[][], int[]> p = randomGenerator.metaGenerator(4, 5,3708433616387827147L);
            long startTime = System.nanoTime();
            SBT sbt = new SBT(g,function.sum);
            sbt.run(0,g.vertexLookup.length-1,new HashMap<>());
            //KShortestPath Yen = new KShortestPath(g);
            //System.out.println("From "+ Integer.toString(0) + ", to " + Integer.toString(g.vertexLookup.length-1));
            //Yen.run(0,g.vertexLookup.length-1,200,function.sum,false);
            long stopTime = System.nanoTime();
            sum += stopTime-startTime;
        }
        System.out.println((float) (sum/1)/1000000 + "ms");

        //ArrayList<ArrayList<Integer>> path = kShortestPath.getPaths();
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
