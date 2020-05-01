import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        int[] skip = {};
        for (int i = 0; i < 1; i++) {
            Graph g = new Graph();
            //Pair<int[][], int[]> p = randomGenerator.metaGenerator(50, 1000,121453412454L);
            Pair<int[][], int[]> p = randomGenerator.normalGenerator(70000,1000,100,2,false,-6606397267175329844L);
            g.edgesInput(p.getKey(),p.getValue());
            SBT sbt = new SBT(g,function.sum);
            sbt.run(0,g.vertexLookup.length-1,skip);
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
            sbt.run(0,g.vertexLookup.length-1,skip);
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
