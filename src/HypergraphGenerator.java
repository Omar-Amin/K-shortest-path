import javafx.util.Pair;

import java.util.*;

public class HypergraphGenerator {
    static int ID = 0;
    public static Graph HypergraphGenerator(int metaSize, int metaEdgeSize){
        return HypergraphGenerator(metaSize,metaEdgeSize, null);
    }
    public static Graph HypergraphGenerator(int metaSize, int metaEdgeSize, Long seed){
        Random rand;
        if(seed == null) {
            rand = new Random();
            seed = rand.nextLong();
            rand.setSeed(seed);
        }
        else rand = new Random(seed);
        int[] metaVertices = new int[metaSize];
        ArrayList<int[]> h2 = new ArrayList<>();
        ArrayList<Integer> cost = new ArrayList();
        //Generate meta graph
        ArrayList<int[]> metaGraph = generateMeta(metaSize, rand);
        for (int[] metaEdge:metaGraph) {
            Pair<ArrayList<int[]>,ArrayList<Integer>> ret = generateh2(metaVertices[metaEdge[0]],metaEdgeSize, rand);
            int[] lastEdge = ret.getKey().get(ret.getKey().size()-1);
            if(metaVertices[metaEdge[1]] == 0) metaVertices[metaEdge[1]] = lastEdge[0];
            else {
                lastEdge[0] = metaVertices[metaEdge[1]];
                ID--;
            }
            h2.addAll(ret.getKey());
            cost.addAll(ret.getValue());
        }
        Graph g = new Graph();
        g.edgesInput(convertListToArrArr(h2),convertListToArr(cost));
        System.out.println("Generating random hypergraph with seed: " + seed);
        return g;
    }

    private static ArrayList<int[]> generateMeta(int metaSize, Random rand){
        ArrayList<int[]> metaArray = new ArrayList();
        for (int i = 0; i < metaSize-1; i++) {
            int[] ret;
            if(i == 0){
                int amount = rand.nextInt(metaSize-3)+2; //Atleast two outgoing
                ret = getRandom(i+1,metaSize, amount, rand);
            } else {
                int amount = rand.nextInt(metaSize-i-1)+1; //Atleast one outgoing
                ret = getRandom(i+1,metaSize, amount, rand);
            }
            for (int v: ret) {
                int[] metaEdge = new int[2];
                metaEdge[0] = i;
                metaEdge[1] = v;
                metaArray.add(metaEdge);
            }
        }
        return metaArray;
    }

    private static Pair<ArrayList<int[]>,ArrayList<Integer>> generateh2(int sourceIndex, int size, Random rand){
        ArrayList<Integer> cost = new ArrayList<>();
        ArrayList<int[]> h2 = new ArrayList<>();
        int vertices = (int) (rand.nextInt((int) ( size*0.5) ) + size*0.75);
        int maxTailSize = rand.nextInt(size/2)+1;
        for (int i = 1; i <= vertices; i++) {
            int tailSize = maxTailSize;
            if(i-maxTailSize < 0) tailSize = maxTailSize + (i-maxTailSize);
            int[] edge = new int[1+tailSize];
            edge[0] = ID+i;
            cost.add(rand.nextInt(100)+1);
            for (int j = 1; j <= tailSize; j++) {
                if(i-j == 0) edge[j] = sourceIndex;
                else edge[j] = ID+(i-j);
            }
            h2.add(edge);
        }
        ID += vertices;

        return new Pair<>(h2, cost);
    }

    private static int[] getRandom(int from, int to, int amount, Random rand){
        List<Integer> vertices = new ArrayList<>(to-from);
        for (int i = from; i < to; i++) {
            vertices.add(i);
        }
        int[] ret = new int[amount];
        Collections.shuffle(vertices, rand);
        for (int i = 0; i < amount; i++) {
            ret[i] = vertices.get(i);
        }
        return ret;
    }
    
    public static int[] convertListToArr(ArrayList<Integer> o1){
        int[] ret = new int[o1.size()];
        for (int i = 0; i < o1.size(); i++) {
            ret[i] = o1.get(i);
        }
        return ret;
    }

    public static int[][] convertListToArrArr(ArrayList<int[]> o1){
        int[][] ret = new int[o1.size()][];
        for (int i = 0; i < o1.size(); i++) {
            ret[i] = o1.get(i);
        }
        return ret;
    }
}
