import com.sun.javafx.image.IntPixelGetter;
import javafx.util.Pair;
import sun.misc.Queue;

import java.lang.reflect.Array;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.BlockingDeque;

public class ShortestPath {
    //Hypergraph er det samme som hyperpath
    //Class attributes
/*    private PriorityQueue<Vertex> pq = new PriorityQueue<>(new Comparator<Vertex>() {
        @Override
        public int compare(Vertex o1, Vertex o2) {
            return Integer.compare(o1.getCost(), o2.getCost());
        }
    });*/
    private minPQ pq = new minPQ();
    private ArrayList<Edge> path = new ArrayList<>();
    private int cost = 0;

    public void printPath(){
        System.out.println("Graph printed:");
        for (Edge edge :path) {
            System.out.println("Edge id: " + (edge.getId()+1));
            System.out.println("Cost of edge: " + edge.getCost());
        }
    }

    public ShortestPath(Hypergraph hypergraph, Vertex source, Vertex target, ArrayList<Edge> deletedEdges){
        SBT(hypergraph,source,target,deletedEdges);
    }

    private ArrayList<Edge> SBT(Hypergraph hypergraph, Vertex source, Vertex target,ArrayList<Edge> deletedEdges){
        for (Vertex vertex : hypergraph.getVertices()) {
            vertex.setCost(Integer.MAX_VALUE);
        }
        for (Edge edge : hypergraph.getEdges()) {
            edge.setKj(0);
        }
        source.setCost(0);
        pq.insert(source);
        while (pq.size() > 0) {
            //debugPrintQueue();
            Vertex u = pq.popMin(); //Retrieves and removes first element
            for (Edge edge : u.getOutgoing_edges()) { // FS(u) må være u's outgoing edges
                if(deletedEdges.contains(edge)){
                    continue;
                }
                edge.setKj(edge.getKj()+1);
                if (edge.getKj() >= edge.getTail().size()) {
                    int f = minCostFunction(edge); // Some cost function
                    Vertex y = edge.getHead();
                    if(y.getCost() > f){
                        // if pq doesn't contain head of current edge
                        y.setCost(f);
                        if(!pq.contains(y)){
                            pq.insert(y);
                        }else {
                            pq.decreaseValue(y.getId(),y.getCost());
                        }
                        y.setPredecessor(edge);
                    }
                }
            }
        }

        if(target.getId() != Integer.MAX_VALUE){
            cost = target.getCost();
            getPath(source, target); // Array of a path of edges, perhaps return it?
            return path;
        }

        return null;
    }

    /**
     * Function that takes in an edge and find the cost value for
     * the given edge, by getting the cost of all of its tail vertices
     * and adding its own cost.
     *
     * @param edge: The edge to find the cost
     * */
    private int costFunction(Edge edge){
        int edgeCost = edge.getCost();
        int edgeTailCost = 0;
        for (Vertex v : edge.getTail()) {
            edgeTailCost += v.getCost();
        }
        return edgeCost + edgeTailCost;
    }

    /**
     * Minimum cost function.
     * */
    private int minCostFunction(Edge edge){
        int edgeCost = edge.getCost();
        int edgeTailCost = Integer.MAX_VALUE;
        for (Vertex v : edge.getTail()) {
            if(v.getCost() < edgeTailCost){
                edgeTailCost = v.getCost();
            }
        }
        return edgeCost + edgeTailCost;
    }

    private final HashMap<Vertex,Integer> in = new HashMap<>();

    /**
     * This method gets path by topological sorting.
     * */
    private void getPath(Vertex source, Vertex target){
        PriorityQueue<Vertex> zeroIn = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        zeroIn.add(target);
        edgesInPath(source,target);
        path.add(target.getPredecessor());
        while (!zeroIn.isEmpty()){
            Vertex vertex = zeroIn.poll();
            if (vertex == source){
                continue;
            }
            for (Vertex tail : vertex.getPredecessor().getTail()) {
                // decrease each value for the vertex if the edge polled out
                // is a part of its outgoing edge
                in.put(tail,in.get(tail) - 1);
                if(in.get(tail) <= 0 && tail != source){
                    zeroIn.add(tail);
                    path.add(tail.getPredecessor());
                }
            }

        }
    }

    private final HashMap<Edge, Integer> edges = new HashMap<>();
    /**
     * Helper function that updates the "in" hashmap in order to
     * perform topological sorting. 
     * */
    private void edgesInPath(Vertex source, Vertex target){
        if(edges.get(target.getPredecessor()) != null){
            return;
        }
        edges.put(target.getPredecessor(),0);
        if (target == source) {
            return;
        }
        for (Vertex vertex : target.getPredecessor().getTail()) {
            // counts how many outgoing edges each vertex in the shortest path
            in.computeIfAbsent(vertex, t -> 0);
            in.put(vertex,in.get(vertex)+1);
            if (vertex.getPredecessor() != null) { // If the vertex doesn't have a predecessor it hasn't been visited.
                edgesInPath(source, vertex);
            }
        }
    }

    public Pair<ArrayList<Edge>, Integer> getShortestPath(){
        return new Pair<>(path,cost);
    }

    public void sortPath(){
        path.sort((o1, o2) -> o1.getId() - o2.getId());
    }

    public int getCost(){
        return cost;
    }

/*    private void debugPrintQueue(){
        ArrayList<Vertex> tmp = new ArrayList<>();
        while (pq.size() > 0){
            Vertex v = pq.poll();
            //System.out.println("Vertex id: " + (v.getId()+1));
            //System.out.println("Cost: " + v.getCost());
            tmp.add(v);
        }
        pq.addAll(tmp);
    }*/

}
