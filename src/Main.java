import javafx.util.Pair;

import java.util.ArrayList;


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
                {-2,-2,-2,-2,-2,-2,1,1}  //7
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

        Hypergraph hg = new Hypergraph().matrixInput(hypergraph2);
        //Hypergraph hg = new Hypergraph().generateRandomHypergraph(15000,15000,5,10);
        //Graph graph = new Graph().transformToGraph(hg);

        //ShortestPath shortestPath = new ShortestPath(hg,hg.getVertices().get(0),hg.getVertices().get(hg.getVertices().size()-1),new ArrayList<>());
        //System.out.println("Our algorithm:" + shortestPath.getCost());
        //shortestPath.printPath();
        //shortestPath.printPath();
        //hg.printHypergraph();

        HypergraphGenerator generator = new HypergraphGenerator();
        Hypergraph testGenerated = generator.generateRandomHypergraph(125,5,5,1,20, 100);
        long startTime = System.nanoTime();
        KShortestPath kshortest = new KShortestPath(testGenerated,testGenerated.getSource(),testGenerated.getTarget(),100);
        long stopTime = System.nanoTime();
        System.out.println((float) (stopTime - startTime)/1000000000);

        //kshortest.tempMethod();
        //ArrayList<Edge> temp = sp.getShortestPath();
        //Hypergraph hg2 = new Hypergraph().edgesInput(temp);
        //hg2.printHypergraph();

        //testWithRandom(metaGraph3);

        //testWithRandomHyper();
    }

    private static void testWithRandomHyper(){
        long startTime = System.nanoTime();
        ShortestPath shortestPath = new ShortestPath();
        HypergraphGenerator generator = new HypergraphGenerator();
        for (int i = 0; i < 10; i++) {
            System.out.println("Iteration: " + i);
            Hypergraph testGenerated = generator.generateRandomHypergraph(1000,5,5,0.7,20, -1);
            Hyperpath shortest = shortestPath.SBT(testGenerated,testGenerated.getSource(),testGenerated.getTarget(),new ArrayList<>());
            if(shortest != null){
                System.out.println(shortest.getCost());
                System.out.println(shortest.getPath().size());
            }
        }
        long stopTime = System.nanoTime();
        System.out.println((float) (stopTime - startTime)/1000000000);
    }

    private static void testWithRandom(int[][] metaGraph){
        long startTime = System.nanoTime();
        int counter = 0;
        ShortestPath shortestPath = new ShortestPath();
        HypergraphGenerator generator = new HypergraphGenerator();
        for (int i = 0; i < 10000; i++) {
            System.out.println(i);
            Hypergraph testGenerated = generator.generateFromMetagraph(metaGraph, 50,25,5,10,-1000);
            Hyperpath shortest = shortestPath.SBT(testGenerated,testGenerated.getSource(),testGenerated.getTarget(),new ArrayList<>());
            Graph graph = new Graph().transformToGraph(testGenerated);
            graph.runDijkstra(testGenerated.getSource().getId(),testGenerated.getTarget().getId());
            if(graph.getShortestDistance() != shortest.getCost()){
                counter++;
                //testGenerated.printHypergraph();
                System.out.println("Graph: " + graph.getShortestDistance());
                System.out.println("Hyper: " + shortest.getCost());
                break;
            }
        }

        System.out.println(counter);
        long stopTime = System.nanoTime();
        System.out.println((float) (stopTime - startTime)/1000000000);
    }

}
