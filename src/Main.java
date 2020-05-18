import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) {
        //yenTest();
        autoTestYen();
        //test();
    }

    public static void yenTest(){
        Pair<int[][], int[]> p = randomGenerator.metaGenerator(50, 25,1289347L);
        Hypergraph h = new Hypergraph().arraylistInput(p.getKey(),p.getValue());
        Long startTime = System.nanoTime();
        KShortestPath yen = new KShortestPath(h);
        yen.run(h.getSource(),h.getTarget(),200,function.sum,false);
        for (Hyperpath py: yen.getPaths()) {
            String print = "";
            for (Edge e:py.getPath()) {
                print += e.getId() + ", ";
            }
            print += py.getCost();
            System.out.println(print);
        }
        Long stopTime = System.nanoTime();
        System.out.println((float) (stopTime-startTime)/1000000 + "ms");
    }

    public static void sbtTest(){
        Pair<int[][], int[]> p = randomGenerator.normalGenerator(500000,3000,50,5,false, 2337978380381156462L);
        Hypergraph h = new Hypergraph().arraylistInput(p.getKey(),p.getValue());
        ShortestPath shortestPath = new ShortestPath(function.sum);
        Hyperpath pi = shortestPath.SBT(h,h.getSource(),h.getTarget(), new HashMap<>(),false);
        ArrayList<Integer> i = new ArrayList<>();
        for (Edge e:pi.getPath()) {
            i.add(e.getId());
        }
        String print = "";
        i.add(0);
        Collections.sort(i);
        for (Integer e: i) {
            print += e + ", ";
        }
        System.out.println(print + pi.getCost());
        System.out.println(pi.getPath().size());
    }

    private static void autoTestYen(){
        Pair<int[][], int[]> p = randomGenerator.normalGenerator(40000,100,100,5,false,-1743747320800128377L);

        Hypergraph h = new Hypergraph().arraylistInput(p.getKey(),p.getValue());
        int[] s = {15};
        for (int k :s) {
            float avg = 0;
            for (int i = 1; i <= 5; i++) {
                KShortestPath kshortest = new KShortestPath(h);
                float startTime = System.nanoTime();
                kshortest.run(h.getSource(),h.getTarget(),k,function.sum,false);
                float stopTime = (System.nanoTime()-startTime)/1000000;
                avg += stopTime;
            }
            System.out.println("Average: " + (avg/5) + ", K = " + k);
        }
    }


}
