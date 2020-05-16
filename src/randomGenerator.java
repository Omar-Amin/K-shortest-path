import javafx.util.Pair;

import java.util.*;

public class randomGenerator {
    public static Pair<int[][], int[]> normalGenerator(int amountEdges, int verticesWidth, int verticesHeight, int locality, boolean topological){
        return normalGenerator(amountEdges, verticesWidth, verticesHeight, locality, topological,-1L);
    }

    public static Pair<int[][], int[]> normalGenerator(int amountEdges, int verticesWidth, int verticesHeight, int locality, boolean topological, Long seed){
        int[][] graph = new int[amountEdges][];
        int[] cost = new int[amountEdges];
        Random rand = new Random();
        if(seed ==-1)
            seed = rand.nextLong();
        System.out.println("Running with seed: " + seed);
        rand = new Random(seed);
        for (int i = 0; i < amountEdges; i++) {
            int tail = rand.nextInt(3)+1;
            graph[i] = new int[tail+1];
            //Select a random vertex somewhere in the graph as head:
            int headRow = rand.nextInt(verticesHeight);
            int headCol = rand.nextInt(verticesWidth);
            int head = headRow*verticesWidth + headCol;
            if(topological){
                while(head == 0){
                    headRow = rand.nextInt(verticesHeight);
                    headCol = rand.nextInt(verticesWidth);
                    head = headRow*verticesWidth + headCol;
                }
            }
            graph[i][0] = head;
            for (int j = 1; j < graph[i].length; j++) {
                graph[i][j] = getLocalVertex(graph[i][0],verticesWidth,verticesHeight,locality,rand, topological);
            }
            cost[i] = rand.nextInt(11);
        }
        return new Pair<>(graph,cost);
    }

    static int ID = 0;
    public static Pair<int[][], int[]> metaGenerator(int metaSize, int metaEdgeSize){
        return metaGenerator(metaSize,metaEdgeSize, null);
    }
    public static Pair<int[][], int[]> metaGenerator(int metaSize, int metaEdgeSize, Long seed){
        ID = 0;
        Random rand;
        if (seed == null) {
            rand = new Random();
            seed = rand.nextLong();
            rand.setSeed(seed);
        } else rand = new Random(seed);
        int[] metaVertices = new int[metaSize];
        ArrayList<int[]> h2 = new ArrayList<>();
        ArrayList<Integer> cost = new ArrayList();
        //Generate meta graph
        ArrayList<int[]> metaGraph = generateMeta(metaSize, rand);
        System.out.println("Number of metaEdges: "+metaGraph.size());
        for (int[] metaEdge : metaGraph) {
            Pair<ArrayList<int[]>, ArrayList<Integer>> ret = generateh2(metaVertices[metaEdge[0]], metaEdgeSize, rand);
            int[] lastEdge = ret.getKey().get(ret.getKey().size() - 1);
            if (metaVertices[metaEdge[1]] == 0) metaVertices[metaEdge[1]] = lastEdge[0];
            else {
                lastEdge[0] = metaVertices[metaEdge[1]];
                ID--;
            }
            h2.addAll(ret.getKey());
            cost.addAll(ret.getValue());
        }
        System.out.println("Generated graph with seed: "+seed);
        return new Pair<>(convertListToArrArr(h2),convertListToArr(cost));
    }

    private static ArrayList<int[]> generateMeta(int metaSize, Random rand){
        ArrayList<int[]> metaArray = new ArrayList();
        for (int i = 0; i < metaSize-1; i++) {
            int[] ret;
            if(i == 0){
                //int amount = rand.nextInt(metaSize-3)+2; //Atleast two outgoing
                int amount = rand.nextInt(4)+2; //Atleast two outgoing
                ret = getRandom(i+1,metaSize, amount, rand);
            } else {
                //int amount = rand.nextInt(metaSize-i-1)+1
                int amount = rand.nextInt(Math.min(4,metaSize-i-1))+1; //Atleast one outgoing
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
        int maxTailSize = rand.nextInt(5)+1;
        for (int i = 1; i <= size; i++) {
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
        ID += size;

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

    private static int getLocalVertex(int vertex, int verticesWidth, int verticesHeight, int radius, Random rand, boolean topological){
        int col = vertex%verticesWidth;
        int row = vertex/verticesWidth;
        int fromCol = Math.max(0,col-radius);
        int fromRow = Math.max(0,row-radius);
        int toRow;
        int toCol = Math.min(verticesWidth - 1, col + radius);
        int newCol = rand.nextInt(toCol-fromCol+1)+fromCol;
        if(topological){
            toRow = Math.min(verticesHeight-1,fromRow+2*radius-(newCol-fromCol));
        } else {
            toRow = Math.min(verticesHeight - 1, row + radius);
        }
        int newRow = rand.nextInt(toRow-fromRow+1) + fromRow;
        if(newCol == col && newRow == row)
            return getLocalVertex(vertex, verticesWidth, verticesHeight, radius, rand, topological);
        return newRow*verticesWidth + newCol;
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

    /*
    OLD GENERATOR
    public HypergraphGenerator(){
    }


     * From a given metagraph, every edge is turned into a random hyperpath (hypergraph).
     * We later connect the hyperpaths into a hypergraph that looks like the metagraph.
     *
    public Hypergraph generateFromMetagraph(int[][] metaGraph,int maxNodes,int minNodes , int tailSize, int maxCost, int seed){
        Hypergraph hg = new Hypergraph().generateRandomHyperpath(maxNodes,minNodes,tailSize,maxCost, seed);
        ArrayList<Hypergraph> hyperEdges = new ArrayList<>();
        hyperEdges.add(hg);

        for (int i = 1; i < metaGraph[0].length; i++) {
            Hypergraph hypergraph = new Hypergraph();
            hypergraph.setAmountOfVertices(hyperEdges.get(i-1).getAmountOfVertices());
            hypergraph.setAmountOfEdges(hyperEdges.get(i-1).getAmountOfEdges());
            hypergraph.generateRandomHyperpath(maxNodes,minNodes , tailSize, maxCost, seed+i);
            hyperEdges.add(hypergraph);
        }

        Hypergraph h2 = new Hypergraph();
        h2.setAmountOfEdges(hyperEdges.get(hyperEdges.size()-1).getAmountOfEdges());
        h2.setAmountOfVertices(hyperEdges.get(hyperEdges.size()-1).getAmountOfVertices());
        return h2.connectHyperedges(metaGraph,hyperEdges);
    }

    public Hypergraph generateRandomHypergraph(int nodes, int tailSize, int maxCost, double p, int maxdepth, int seed){
        return new Hypergraph().generateRandomHypergraph(nodes,tailSize,maxCost,p, maxdepth, seed);
    }
    */
}