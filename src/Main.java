import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        int[][] hypergraph = {
                {-1,-1,0,0,0,0,0,0,0,0},
                {1,0,-1,-1,0,-1,0,-1,0,0},
                {0,1,-1,0,0,0,0,0,0,0},
                {0,0,1,-1,-1,-1,0,0,0,0},
                {0,0,0,1,0,0,-1,0,0,0},
                {0,0,0,0,1,0,0,-1,0,0},
                {0,0,0,0,0,1,0,0,-1,0},
                {0,0,0,0,0,0,1,1,1,-1},
                {0,0,0,0,0,0,0,0,0,1}};

        Hypergraph hg = new Hypergraph().matrixInput(hypergraph);
        //hg.printHypergraph();
        ShortestPath sp = new ShortestPath(hg,hg.getVertices().get(0),hg.getVertices().get(hg.getVertices().size()-1));
        //sp.printPath();
        ArrayList<Edge> temp = sp.getShortestPath();
        Hypergraph hg2 = new Hypergraph().edgesInput(temp);
        hg2.printHypergraph();

    }

}
