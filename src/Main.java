import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        int[][] hypergraph2 = {
                {-1,-1,0,0,0,0,0,0}, //1
                {1,0,0,-1,0,-1,0,0}, //2
                {0,1,-1,-1,0,0,0,0}, //3
                {0,0,0,1,1,-1,-1,0}, //4
                {0,0,1,0,-1,0,-1,0}, //5
                {0,0,0,0,0,1,0,-1}, //6
                {0,0,0,0,0,0,1,1}  //7
        };
        int[] edges = {1,1,1,1,1,1,2,1};
        Graph g = new Graph();
        //System.out.println(sbt.run(0,g.vertexLookup.length-1,new HashMap<>()));
        Pair<int[][], int[]> p = randomGenerator.metaGenerator(2500, 5,222L);
        g.edgesInput(p.getKey(),p.getValue());
        long startTime = System.nanoTime();
        SBT sbt = new SBT(g,function.sum);
        sbt.run(0,g.vertexLookup.length-1,new HashMap<>());

        //KShortestPath kShortestPath = new KShortestPath(g,0,g.vertexLookup.length-1,2,function.sum,false);
        long stopTime = System.nanoTime();
        System.out.println((float) (stopTime - startTime)/1000000000);


        int[] skip = {};
        for (int i = 0; i < 1; i++) {
            Graph g = new Graph();
            Pair<int[][], int[]> p = randomGenerator.metaGenerator(50, 1000,-5437924817408659363L);
            //Pair<int[][], int[]> p = randomGenerator.normalGenerator(70000,1000,100,2,true,-6606397267175329844L);
            g.edgesInput(p.getKey(),p.getValue());
            SBT sbt = new SBT(g,function.sum);
            long starttime = System.nanoTime();
            sbt.run(0,g.vertexLookup.length-1,skip);
            long endtime = System.nanoTime();
            System.out.println((float) (endtime - starttime)/1000000);
        }
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
