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
        for (int i = 0; i < 1000; i++) {
            Graph g = HypergraphGenerator.HypergraphGenerator(10, 10);
            SBT sbt = new SBT(g,function.min);
            System.out.println("SBT running "+ g.vertexLookup.length);
            sbt.run(0,g.vertexLookup.length-1,skip);
            Graph dijk = g.convertToNormalGraph();
            System.out.println("Dijkstra running");
            Dijkstra s = new Dijkstra(dijk);
            s.run(0,dijk.vertexLookup.length-1);
            if(g.getVertexCost(g.vertexLookup.length-1) != g.getVertexCost(g.vertexLookup.length-1))
                System.out.println("Fuck!");

            System.gc();
        }
    }
}
