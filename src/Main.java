import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        int[][] hypergraph = {
                {-1,-1, 0, 0, 0, 0, 0, 0, 0, 0},
                { 1, 0,-1,-1, 0,-1, 0,-1, 0, 0},
                { 0, 1,-1, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 1,-1,-1,-1, 0, 0, 0, 0},
                { 0, 0, 0, 1, 0, 0,-1, 0, 0, 0},
                { 0, 0, 0, 0, 1, 0, 0,-1, 0, 0},
                { 0, 0, 0, 0, 0, 1, 0, 0,-1, 0},
                { 0, 0, 0, 0, 0, 0, 1, 1, 1,-1},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}};
        int[] edgePrice = {1,1,1,1,1,1,1,1,1,1};
        Graph g = new Graph();
        g.matrixInput(hypergraph,edgePrice);
        SBT sbt = new SBT(g,function.sum);
        int[] skip = new int[0];
        System.out.println(sbt.run(0, 8,skip));
    }
}
