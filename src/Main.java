import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        int[][] hypergraph = {
                {-1, -1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, -1, -1, 0, -1, 0, -1, 0, 0},
                {0, 1, -1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, -1, -1, -1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, -1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, -1, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, -1, 0},
                {0, 0, 0, 0, 0, 0, 1, 1, 1, -1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}};
        int[] edgePrice = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] skip = {};
        int count = 0;
        for (int i = 0; i < 1; i++) {
            Graph g = HypergraphGenerator.HypergraphGenerator(100, 1000,121453412454L);
            SBT sbt = new SBT(g,function.min);
            System.out.println("SBT running");
            sbt.run(0,g.vertexLookup.length-1,skip);
            Graph dijk = g.convertToNormalGraph();
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
