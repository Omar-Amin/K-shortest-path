import javafx.util.Pair;

import java.util.*;


public class Main {

    public static void main(String[] args) {
        //yenTest();
        //generateSeed();
        autoYenTest();
    }

    private static void generateSeed(){
        Graph g = new Graph();
        SBT sbt = new SBT(g,function.sum);
        ArrayList<Long> l = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Pair<int[][], int[]> p = randomGenerator.uniformGenerator(500000,1500,50,5,false);
            g.edgesInput(p.getKey(),p.getValue());
            while(sbt.run(0,g.vertexLookup.length-1,new HashMap<>()) == null){
                p = randomGenerator.uniformGenerator(500000,1500,50,5,false);
                g.edgesInput(p.getKey(),p.getValue());
            }
        }
    }

    public static void yenTest(){
        Pair<int[][], int[]> p = randomGenerator.uniformGenerator(40000,100,100,5,false,-1743747320800128377L);
        Graph g = new Graph();
        g.edgesInput(p.getKey(),p.getValue());
        SBT sbt = new SBT(g,function.sum);
        ArrayList<Integer> l = sbt.run(0,g.vertexLookup.length-1,new HashMap<>());
        System.out.println(g.getVertexCost(g.vertexLookup.length-1));
        String print = "";
        for (Integer i:l) {
            print += i + ", ";
        }
        System.out.println(print);
    }

/*    public static void test(){
        Graph g = new Graph();
        //System.out.println(sbt.run(0,g.vertexLookup.length-1,new HashMap<>()));
        //Pair<int[][], int[]> p = randomGenerator.uniformGenerator(50,10,2,2,false);
        long sum = 0L;
        Random rand = new Random(10);
        for (int i = 0; i < 1; i++) {
            //Pair<int[][], int[]> p = randomGenerator.metaGenerator(50, 100,rand.nextLong());
            Pair<int[][], int[]> p = randomGenerator.metaGenerator(50, 25,1289347L);
            g.edgesInput(p.getKey(),p.getValue());
            //System.out.println("Number of vertices: "+g.vertexLookup.length);
            //System.out.println(g.edgeLookup.length);
            //Pair<int[][], int[]> p = randomGenerator.metaGenerator(4, 5,3708433616387827147L);
            long startTime = System.nanoTime();
            SBT sbt = new SBT(g,function.sum);
            sbt.run(0,g.vertexLookup.length-1,new HashMap<>());
            //KShortestPath Yen = new KShortestPath(g);
            //System.out.println("From "+ Integer.toString(0) + ", to " + Integer.toString(g.vertexLookup.length-1));
            //Yen.run(0,g.vertexLookup.length-1,200,function.sum,false);
            long stopTime = System.nanoTime();
            sum += stopTime-startTime;
        }
        System.out.println((float) (sum/1)/1000000 + "ms");

        //ArrayList<ArrayList<Integer>> path = kShortestPath.getPaths();
    }*/

    public static void sbtTest(){
        Pair<int[][], int[]> p = randomGenerator.uniformGenerator(500000,3000,50,5,false, 2337978380381156462L);
        Graph g = new Graph();
        g.edgesInput(p.getKey(),p.getValue());

        SBT shortestPath = new SBT(g,function.sum);
        ArrayList<Integer> pi = shortestPath.run(0,g.vertexLookup.length-1, new HashMap<>());
        String print = "";
        Collections.sort(pi);
        for (Integer e: pi) {
            print += e + ", ";
        }
        System.out.println(print + shortestPath.getPathCost());
        System.out.println(pi.size());
    }

    private static void autoYenTest(){
        // ændre
        Pair<int[][], int[]> p = randomGenerator.metaGenerator(50, 50,1289347L);


        Graph h = new Graph();
        h.edgesInput(p.getKey(),p.getValue());
        int[] metasize = {200};
        int[] edgesize = {};
        for (int k :metasize) {
            float avg = 0;
            h.edgesInput(p.getKey(),p.getValue());
            for (int i = 1; i <= 1 ; i++) {
                KShortestPath yen = new KShortestPath(h);
                float startTime = System.nanoTime();
                yen.run(0,h.vertexLookup.length-1,k,function.sum,false);
                float stopTime = (System.nanoTime()-startTime)/1000000;
                avg += stopTime;
                String print = "";
                for(Object[] obj : yen.getPaths()){
                    ArrayList<Integer> path = (ArrayList<Integer>) obj[0];
                    for (Integer j: path
                         ) {
                        print += j + ", ";
                    }
                }
                System.out.println(print);
            }
            System.out.println("Average: " + (avg/5) + ", K = " + k);
        }
    }


}