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
        Graph g = HypergraphGenerator.HypergraphGenerator(5, 5, (long) 0);
        SBT sbt = new SBT(g,function.sum);
        System.out.println(sbt.run(0,18,skip));
    }
}
