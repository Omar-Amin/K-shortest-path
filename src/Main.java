import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) {
        int[][] hypergraph = {
                {-1,-1,-2,-2,-2,-2,-2,-2,-2,-2,-2}, //1
                {1,-2,-1,-1,-2,-1,-2,-2,-1,-2,-2}, //2
                {-2,1,-1,-2,-2,-2,-1,-2,-2,-2,-2}, //3
                {-2,-2,1,-1,-1,-1,-2,-2,-2,-2,-2}, //4
                {-2,-2,-2,1,-2,-2,-2,-1,-2,-2,-2}, //5
                {-2,-2,-2,-2,1,-2,-2,-2,-1,-2,-2}, //6
                {-2,-2,-2,-2,-2,1,1,-2,-2,-1,-2}, //7
                {-2,-2,-2,-2,-2,-2,-2,1,1,1,-1}, //8
                {-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,1}}; //9
/*          int[][] hypergraph = {
                {-1,-1,-2,-2,-2,-2,-2,-2,-2,-2,-2}, //1
                {1,-2,-1,-1,-2,-1,-2,-2,-1,-2,-2}, //2
                {-2,1,-1,-2,-2,-2,-1,-2,-2,-2,-2}, //3
                {-2,-2,2,-1,-1,-1,-2,-2,-2,-2,-2}, //4
                {-2,-2,-2,1,-2,-2,-2,-1,-2,-2,-2}, //5
                {-2,-2,-2,-2,2,-2,-2,-2,-1,-2,-2}, //6
                {-2,-2,-2,-2,-2,3,4,-2,-2,-1,-2}, //7
                {-2,-2,-2,-2,-2,-2,-2,4,3,1,-1}, //8
                {-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,1}}; //9*/
        int[][] hypergraph3 = {
                {-1,-1,-2,-2,-2,-2,-2,-2,-2,-2},
                {1,-2,-1,-1,-2,-1,-2,-1,-2,-2},
                {-2,1,-1,-2,-2,-2,-2,-2,-2,-2},
                {-2,-2,1,-1,-1,-1,-2,-2,-2,-2},
                {-2,-2,-2,1,-2,-2,-1,-2,-2,-2},
                {-2,-2,-2,-2,1,-2,-2,-1,-2,-2},
                {-2,-2,-2,-2,-2,1,-2,-2,-1,-2},
                {-2,-2,-2,-2,-2,-2,1,1,1,-1},
                {-2,-2,-2,-2,-2,-2,-2,-2,-2,1}};
        int[][] hypergraph2 = {
                {-1,-1,-2,-2,-2,-2,-2,-2}, //1
                {1,-2,-2,-1,-2,-1,-2,-2}, //2
                {-2,1,-1,-1,-2,-2,-2,-2}, //3
                {-2,-2,-2,1,1,-1,-1,-2}, //4
                {-2,-2,1,-2,-1,-2,-1,-2}, //5
                {-2,-2,-2,-2,-2,1,-2,-1}, //6
                {-2,-2,-2,-2,-2,-2,2,1}  //7
        };
        int[][] metaGraph = {
                {-1,-1,0,0},
                {1,0,-1,0},
                {0,1,0,-1},
                {0,0,1,1}
        };

        int[][] metaGraph2 = {
                {-1,-1,0,0,0,0,0}, //1
                {1,0,-1,0,0,0,0}, //2
                {0,1,0,-1,-1,0,0}, //3
                {0,0,1,1,0,-1,0}, //4
                {0,0,0,0,1,0,-1}, //5
                {0,0,0,0,0,1,1} //6
        };

        int[][] metaGraph3 = {
                {-1,-1,0,0,0,0,0,0,0,0,0,0}, //1
                {1,0,-1,0,0,-1,0,0,0,0,0,0}, //2
                {0,1,0,-1,-1,0,0,0,0,0,0,0}, //3
                {0,0,1,1,0,0,-1,-1,-1,0,0,0}, //4
                {0,0,0,0,0,1,1,0,0,0,-1,0}, //5
                {0,0,0,0,0,0,0,0,1,-1,0,0}, //6
                {0,0,0,0,1,0,0,0,0,0,0,-1}, //7
                {0,0,0,0,0,0,0,1,0,1,1,1}}; //8

        //Hypergraph hg = new Hypergraph().generateRandomHypergraph(15000,15000,5,10);
        //Graph graph = new Graph().transformToGraph(hg);

        //ShortestPath shortestPath = new ShortestPath(hg,hg.getVertices().get(0),hg.getVertices().get(hg.getVertices().size()-1),new ArrayList<>());
        //System.out.println("Our algorithm:" + shortestPath.getCost());
        //shortestPath.printPath();
        //shortestPath.printPath();
        //hg.printHypergraph();

        //Pair<int[][], int[]> p = randomGenerator.normalGenerator(10000,25,25,2,false,1222L);
        //Pair<int[][], int[]> p = randomGenerator.metaGenerator(2500, 5,222L);
        Pair<int[][], int[]> p = randomGenerator.metaGenerator(200, 5,222L);
        Hypergraph h = new Hypergraph().arraylistInput(p.getKey(),p.getValue());
        long startTime = System.nanoTime();
        //ShortestPath shortestPath = new ShortestPath(function.sum);
        //shortestPath.SBT(h,h.getSource(),h.getTarget(), new HashMap<>(),false);
        KShortestPath kshortest = new KShortestPath(h,h.getSource(),h.getTarget(),100,function.sum,false);
        long stopTime = System.nanoTime();
        System.gc();
        System.out.println((float) (stopTime - startTime)/1000000000);
        ArrayList<Hyperpath> hyperpaths = kshortest.getPaths();
        System.out.println(hyperpaths);
        //kshortest.tempMethod();
        //ArrayList<Edge> temp = sp.getShortestPath();
        //Hypergraph hg2 = new Hypergraph().edgesInput(temp);
        //hg2.printHypergraph();

        //testWithRandom(metaGraph3);

        //testWithRandomHyper();
    }



}
