import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

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

        //Hypergraph hg = new Hypergraph().matrixInput(hypergraph);
        //Hypergraph hg = new Hypergraph().generateRandomHypergraph(15000,15000,5,10);
        //Graph graph = new Graph().transformToGraph(hg);

        //ShortestPath shortestPath = new ShortestPath(hg,hg.getVertices().get(0),hg.getVertices().get(hg.getVertices().size()-1),new ArrayList<>());
        //System.out.println("Our algorithm:" + shortestPath.getCost());
        //shortestPath.printPath();
        //shortestPath.printPath();
        //hg.printHypergraph();
        //KShortestPath kshortest = new KShortestPath(hg,hg.getVertices().get(0),hg.getVertices().get(hg.getVertices().size()-1),2);

        //kshortest.tempMethod();
        //ArrayList<Edge> temp = sp.getShortestPath();
        //Hypergraph hg2 = new Hypergraph().edgesInput(temp);
        //hg2.printHypergraph();
        long startTime = System.nanoTime();
        int counter = 0;
        for (int i = 0; i < 10000; i++) {
            System.out.println(i);
            HypergraphGenerator generator = new HypergraphGenerator(metaGraph3, 50,25,5,10,-1000);
            // 59 for graph < hyper, 303 for graph > hyper
            Hypergraph testGenerated = generator.getHypergraph();
            //testGenerated.printHypergraph();
            ShortestPath shortestPath = new ShortestPath(testGenerated,testGenerated.getSource(),testGenerated.getTarget(),new ArrayList<>());
            Graph graph = new Graph().transformToGraph(testGenerated,testGenerated.getSource().getId(),testGenerated.getTarget().getId());
            if(graph.getShortestDistance() != shortestPath.getCost()){
                counter++;
                //testGenerated.printHypergraph();
                System.out.println("Graph: " + graph.getShortestDistance());
                System.out.println("Hyper: " + shortestPath.getCost());
                break;
            }
        }
        System.out.println(counter);
        long stopTime = System.nanoTime();
        System.out.println((float) (stopTime - startTime)/1000000000);
    }

}
